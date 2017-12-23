package com.consulat.sn.etatcivil.service.impl;

import com.consulat.sn.etatcivil.domain.User;
import com.consulat.sn.etatcivil.domain.enumeration.Genre;
import com.consulat.sn.etatcivil.repository.ExtraitRepository;
import com.consulat.sn.etatcivil.service.*;
import com.consulat.sn.etatcivil.service.dto.*;
import com.consulat.sn.etatcivil.service.mapper.ExtraitMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * Service Implementation for managing DeclarationExtrait.
 */
@Service
@Transactional
public class DeclarationExtraitServiceImpl implements DeclarationExtraitService {

    public static final Integer INITIAL_NUMERO = 0001;
    public static final String INTIAL_INSTITUTION = "/CGSB/";
    private final Logger log = LoggerFactory.getLogger(DeclarationExtraitServiceImpl.class);
    private final ExtraitService extraitService;
    private final PaysService paysService;
    private final PersonneService personneService;
    private final PieceJointeService pieceJointeService;
    private final RegistreNaissanceService registreNaissanceService;
    private final UserService userService;
    public Integer newRegistreNumero = 0;
    private Long enfantId = null;
    private Long pereId = null;
    private Long mereId = null;
    private final VilleService villeService;
    private final MailService mailService;
    private final ExtraitRepository extraitRepository;
    private final ExtraitMapper extraitMapper;

    public DeclarationExtraitServiceImpl(PaysService paysService, PieceJointeService pieceJointeService, PersonneService personneService, ExtraitService extraitService,
                                         RegistreNaissanceService registreNaissanceService, UserService userService, VilleService villeService, MailService mailService, ExtraitRepository extraitRepository, ExtraitMapper extraitMapper) {
        this.paysService = paysService;
        this.extraitService = extraitService;
        this.personneService = personneService;
        this.pieceJointeService = pieceJointeService;
        this.registreNaissanceService = registreNaissanceService;
        this.userService = userService;
        this.villeService = villeService;
        this.mailService = mailService;
        this.extraitRepository = extraitRepository;
        this.extraitMapper = extraitMapper;
    }

    /**
     * Save a declarationExtrait.
     *
     * @param declarationExtraitDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DeclarationExtraitDTO save(DeclarationExtraitDTO declarationExtraitDTO) {
        ;
        ExtraitDTO extraitDTO = new ExtraitDTO();


        //je créé les personne composant la famille(papa,maman et puis enfant à déclaré)
        Boolean isFamilyCreated = createFamily(declarationExtraitDTO);

        //si la famille est créée , je créé l'extrait
        if (isFamilyCreated) {
            extraitDTO.setDateDeclaration(LocalDate.now());
            extraitDTO.setMention(declarationExtraitDTO.getMention());
            extraitDTO.setNumeroRegistre(createNumeroRegistre());
            extraitDTO.setPereId(pereId);
            extraitDTO.setMereId(mereId);
            extraitDTO.setEnfantId(enfantId);
            extraitDTO.setValidated(false);
            PaysDTO paysDeclarationDto = paysService.findByNom(declarationExtraitDTO.getLieuDeclaration());
            VilleDTO lieuDeclarationDto = villeService.findByNomAndPaysId(declarationExtraitDTO.getLieuDeclaration(), paysDeclarationDto, null);
            extraitDTO.setLieuDeclarationId(lieuDeclarationDto.getId());
            extraitDTO.setAgentId(getCurrentUserId());

            extraitDTO = extraitService.save(extraitDTO);
        }

        //enregistrement des fichiers pieces jointes
        if (null != extraitDTO.getId()) {
            createPieceJointe(declarationExtraitDTO, extraitDTO.getId());

            //je créé le registre
            RegistreNaissanceDTO registreNaissanceDTO = new RegistreNaissanceDTO();
            registreNaissanceDTO.setAnneeRegistre(extraitDTO.getDateDeclaration());
            registreNaissanceDTO.setExtraitId(extraitDTO.getId());
            registreNaissanceDTO.setNumero(newRegistreNumero);

            registreNaissanceService.save(registreNaissanceDTO);

            declarationExtraitDTO.setId(extraitDTO.getId());

            //je créé l'extrait fichier
            try {

                printExtraitNaissance(extraitDTO.getId());
            } catch (Exception e) {
                e.getMessage();
            }


            try {
                creerTranscription(declarationExtraitDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.debug("Request to save DeclarationExtrait : {}", declarationExtraitDTO);
        // ExtraitDTO declarationExtrait = declarationExtraitMapper.toExtrait(declarationExtraitDTO);


        //envoyer le mail ici
        return declarationExtraitDTO;
    }

    private void viderDocuments() {
        File file = new File("src/main/webapp/app/documents/");
        try {
            File[] listeDesFichiers = file.listFiles();
            if (listeDesFichiers.length != 0) {
                FileUtils.cleanDirectory(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * recupere l'utilisateur courant
     *
     * @return id de l'utilisateur courant
     */
    private Long getCurrentUserId() {
        User currentUser = userService.getUserWithAuthorities();
        //TODO à revoir comme regle de gestion car non safe
        Long result = 1L;
        if (null != currentUser.getId()) {
            result = currentUser.getId();
        }
        return result;
    }

    /**
     * créé un numéro de registre
     *
     * @return le nouveau numero
     */
    private String createNumeroRegistre() {

        RegistreNaissanceDTO existantRegistreNaissance = registreNaissanceService.findFirstByOrderByIdDesc();
        RegistreNaissanceDTO registreNaissance = new RegistreNaissanceDTO();

        DateTime now = new DateTime();
        int currentYear = now.getYear();
        int lastRegisterRecordYear = 0;
        if (null != existantRegistreNaissance) {
            lastRegisterRecordYear = existantRegistreNaissance.getAnneeRegistre().getYear();
        }
        String newNumeroRegistre = null;

        //1er cas, si on est dans la même année
        if (currentYear == lastRegisterRecordYear && null != existantRegistreNaissance) {
            registreNaissance.setNumero(existantRegistreNaissance.getNumero() + 1);
            registreNaissance.setAnneeRegistre(existantRegistreNaissance.getAnneeRegistre());
            newNumeroRegistre = createCompleteNumeroRegistre(registreNaissance);
        }

        //2eme cas, si on n'est pas dans la même année (annéé suivante)
        if (currentYear > lastRegisterRecordYear) {
            registreNaissance.setNumero(INITIAL_NUMERO);
            registreNaissance.setAnneeRegistre(LocalDate.now());
            newNumeroRegistre = createCompleteNumeroRegistre(registreNaissance);
        }
        newRegistreNumero = registreNaissance.getNumero();
        return newNumeroRegistre;
    }

    private String createCompleteNumeroRegistre(RegistreNaissanceDTO registreNaissance) {
        String numero = registreNaissance.getNumero().toString();
        String annee = String.valueOf(registreNaissance.getAnneeRegistre().getYear());
        return numero.concat(INTIAL_INSTITUTION).concat(annee);
    }


    /**
     * créé la famille pour l'extrait de naissance
     *
     * @param declarationExtraitDTO la déclaration
     * @return oui ou non
     */
    private Boolean createFamily(DeclarationExtraitDTO declarationExtraitDTO) {

        Boolean result = false;
        //pere
        PersonneDTO pere = new PersonneDTO();

        //mere
        PersonneDTO mere = new PersonneDTO();

        //enfant
        PersonneDTO enfant = new PersonneDTO();

        enfant.setNom(declarationExtraitDTO.getNomEnfant());
        enfant.setPrenom(declarationExtraitDTO.getPrenomEnfant());
        enfant.setDateNaissance(declarationExtraitDTO.getDateNaissanceEnfant());
        enfant.setGenre(declarationExtraitDTO.getGenreEnfant());


        //je verifie si le pays existe sinon je le crée
        PaysDTO paysNaissanceEnfant = paysService.findByNom(declarationExtraitDTO.getPaysNaissanceEnfant());

        PaysDTO paysNaissanceMere = paysService.findByNom(declarationExtraitDTO.getPaysNaissanceMere());
        PaysDTO paysResidenceMere = paysService.findByNom(declarationExtraitDTO.getPaysResidenceMere());


        VilleDTO lieuNaissanceEnfant = villeService.findByNomAndPaysId(declarationExtraitDTO.getLieuNaissanceEnfant(), paysNaissanceEnfant, null);

        VilleDTO lieuNaissanceMere = villeService.findByNomAndPaysId(declarationExtraitDTO.getLieuNaissanceMere(), paysNaissanceMere, null);
        VilleDTO villeResidenceMere = villeService.findByNomAndPaysId(declarationExtraitDTO.getLieuResidenceMere(), paysResidenceMere, declarationExtraitDTO.getAdresseComplMere());

        if (null != lieuNaissanceEnfant.getId()) {
            enfant.setLieuNaissanceId(lieuNaissanceEnfant.getId());
        }
        if (null != villeResidenceMere.getId()) {
            enfant.setVilleResidence(villeResidenceMere.getNom());
            enfant.setAdresseId(villeResidenceMere.getId());
            enfant.setPaysResidence(paysResidenceMere.getNom());
        }


        enfant.setPaysNaissance(paysNaissanceEnfant.getNom());
        enfant.setVilleNaissance(lieuNaissanceEnfant.getNom());


        mere.setNom(declarationExtraitDTO.getNomMere());
        mere.setPrenom(declarationExtraitDTO.getPrenomMere());
        mere.setDateNaissance(declarationExtraitDTO.getDateNaissanceMere());
        mere.setNumeroPassport(declarationExtraitDTO.getNumeroPassportMere());
        mere.setNumeroCarteIdentite(declarationExtraitDTO.getNumeroIdentiteMere());
        mere.setGenre(Genre.FEMININ);
        mere.setAdresseId(villeResidenceMere.getId());
        mere.setFonction(declarationExtraitDTO.getFonctionMere());
        mere.setLieuNaissanceId(lieuNaissanceMere.getId());
        mere.setPaysResidence(paysResidenceMere.getNom());
        mere.setPaysNaissance(paysNaissanceMere.getNom());
        mere.setVilleNaissance(lieuNaissanceMere.getNom());
        mere.setVilleResidence(villeResidenceMere.getNom());
        mere.setAdresseCompl(declarationExtraitDTO.getAdresseComplMere());

        if (null != declarationExtraitDTO.getPaysNaissancePere()) {
            PaysDTO paysNaissancePere = paysService.findByNom(declarationExtraitDTO.getPaysNaissancePere());
            PaysDTO paysResidencePere = paysService.findByNom(declarationExtraitDTO.getPaysResidencePere());

            VilleDTO lieuNaissancePere = villeService.findByNomAndPaysId(declarationExtraitDTO.getLieuNaissancePere(), paysNaissancePere, null);
            VilleDTO villeResidencePere = villeService.findByNomAndPaysId(declarationExtraitDTO.getLieuResidencePere(), paysResidencePere, declarationExtraitDTO.getAdresseComplPere());


            pere.setNom(declarationExtraitDTO.getNomPere());
            pere.setPrenom(declarationExtraitDTO.getPrenomPere());
            pere.setDateNaissance(declarationExtraitDTO.getDateNaissancePere());
            pere.setNumeroPassport(declarationExtraitDTO.getNumeroPassportPere());
            pere.setNumeroCarteIdentite(declarationExtraitDTO.getNumeroIdentitePere());
            pere.setGenre(Genre.MASCULIN);
            pere.setAdresseId(villeResidencePere.getId());
            pere.setFonction(declarationExtraitDTO.getFonctionPere());
            pere.setLieuNaissanceId(lieuNaissancePere.getId());
            pere.setPaysResidence(paysResidencePere.getNom());
            pere.setPaysNaissance(paysNaissancePere.getNom());
            pere.setVilleNaissance(lieuNaissancePere.getNom());
            pere.setVilleResidence(villeResidencePere.getNom());
            pere.setAdresseCompl(declarationExtraitDTO.getAdresseComplPere());

            pere = personneService.save(pere);
        }

        mere = personneService.save(mere);
        if (null != mere.getId()) {
            enfant.setPereId(pere.getId());
            enfant.setMereId(mere.getId());
            enfant = personneService.save(enfant);
            enfantId = enfant.getId();
            if (null != pere.getId()) {
                pereId = pere.getId();
            }
            mereId = mere.getId();
            result = true;
        }

        return result;
    }

    private void createPieceJointe(DeclarationExtraitDTO declarationExtraitDTO, Long idExtrait) {

        PieceJointeDTO pieceJointeDTO = new PieceJointeDTO();
        //piece jointe A
        pieceJointeDTO.setCopieLiterale(declarationExtraitDTO.getCopieLiterale());
        pieceJointeDTO.setCopieCarteContentType(declarationExtraitDTO.getCopieCarteContentType());

        //piece jointe B
        pieceJointeDTO.setCopieCarte(declarationExtraitDTO.getCopieCarte());
        pieceJointeDTO.setCopieLiteraleContentType(declarationExtraitDTO.getCopieLiteraleContentType());
        pieceJointeDTO.setDeclarationId(idExtrait);

        pieceJointeService.save(pieceJointeDTO);
    }

    @Override
    public String printExtraitNaissance(Long idExtrait) {

        ExtraitDTO extraitDTO = extraitService.findOne(idExtrait);
        PersonneDTO enfant = personneService.findOne(extraitDTO.getEnfantId());
        PersonneDTO mere = personneService.findOne(extraitDTO.getMereId());
        PersonneDTO pere = new PersonneDTO();
        if (null != extraitDTO.getPereId()) {
            pere = personneService.findOne(extraitDTO.getPereId());
        } else {
            pere.setPrenom("xxx");
        }
        VilleDTO lieuDeclaration = villeService.findOne(extraitDTO.getLieuDeclarationId());
        VilleDTO lieuNaissanceEnfant = villeService.findOne(enfant.getLieuNaissanceId());

        String acteNaissance = "";
        if (null != extraitDTO && null != enfant && null != mere && null != pere) {
            PdfReader pdfTemplate;
            acteNaissance = idExtrait + enfant.getPrenom() + "_" + enfant.getNom()
                + "_acte_naissance.pdf";
            // SimpleDateFormat dateDeclaration = null;
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            DateFormat format_fr = DateFormat.getDateInstance(DateFormat.FULL, Locale.FRENCH);
            try {

                pdfTemplate = new PdfReader("template_extrait_naissance.pdf");
                FileOutputStream fileOutputStream = new FileOutputStream("src/main/webapp/app/documents/" + acteNaissance);

                //ByteArrayOutputStream out = new ByteArrayOutputStream();
                PdfStamper stamper = new PdfStamper(pdfTemplate, fileOutputStream);
                stamper.setFormFlattening(true);
                stamper.getAcroFields().setField("annee", Integer.toString(year));
                stamper.getAcroFields().setField("registre", extraitDTO.getNumeroRegistre());
                stamper.getAcroFields().setField("dateNaissance", format_fr.format(fromLocalDate(LocalDate.from(enfant.getDateNaissance()))));
                stamper.getAcroFields().setField("lieu", lieuDeclaration.getNom());
                stamper.getAcroFields().setField("lieuNaissance", lieuNaissanceEnfant.getNom());
                stamper.getAcroFields().setField("sexe", enfant.getGenre().toString());
                stamper.getAcroFields().setField("prenom", enfant.getPrenom());
                stamper.getAcroFields().setField("nom", enfant.getNom());
                stamper.getAcroFields().setField("prenomPere", pere.getPrenom());
                stamper.getAcroFields().setField("nomMere", mere.getPrenom() + " " + mere.getNom());
                stamper.getAcroFields().setField("mentionMarginale", extraitDTO.getMention());
                stamper.getAcroFields().setField("dateDeclaration", format_fr.format(new Date()));
                stamper.close();
                pdfTemplate.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        return acteNaissance;
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public static Date fromLocalDate(LocalDate date) {
        Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault())
            .toInstant();
        return Date.from(instant);
    }

    public static Date local(LocalDate date) {
        Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault())
            .toInstant();
        //return ZonedDateTime.from(TemporalAccessor.class);
        return null;
    }

    /**
     * @param declarationExtraitDTO
     * @throws IOException
     * @throws DocumentException Cette fonction permet de créer le fichier de transcription de naissance
     */
    public void creerTranscription(DeclarationExtraitDTO declarationExtraitDTO)
        throws IOException, DocumentException {
        User user = userService.getUserWithAuthorities(3L);
        String FILE = declarationExtraitDTO.getId() + declarationExtraitDTO.getPrenomEnfant() + "_" + declarationExtraitDTO.getNomEnfant()
            + "_transcription_naissance.pdf";
        Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
        Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        DateFormat format_fr = DateFormat.getDateInstance(DateFormat.FULL, Locale.FRENCH);
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("src/main/webapp/app/documents/" + FILE));
        document.open();
        String personne_qui_transcrit = "Monsieur " + user.getFirstName() + "  " + user.getLastName(); //"Monsieur Abdourahmane KOITA"; /// c'est le user qui s'est connecté
        String fonction = "Consul Général de la République du Sénégal à Bordeaux"; //fonction du user
        String dateNaissancePere;
        String lieuNaissancePere = "xxx";
        String fonctionPere = "xxx";
        String fonctionMere = "Ménagère";
        String nomPere = declarationExtraitDTO.getNomPere();
        String prenomPere = declarationExtraitDTO.getPrenomPere();
        if (prenomPere.equals("xxx")) {
            dateNaissancePere = "xxx";
            nomPere = "xxx";
        } else {
            lieuNaissancePere = declarationExtraitDTO.getLieuNaissancePere();
            dateNaissancePere = format_fr.format(fromLocalDate(declarationExtraitDTO.getDateNaissancePere()));
            if (null !=declarationExtraitDTO.getFonctionPere()){
            fonctionPere = declarationExtraitDTO.getFonctionPere();}else {
                fonctionPere="Sans emploi";
            }
        }
        if (null != declarationExtraitDTO.getFonctionMere()) {
            fonctionMere = declarationExtraitDTO.getFonctionMere();
        }

        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Transcription de l’acte de naissance", catFont));
        addEmptyLine(preface, 2);
        preface.add(new Paragraph("Année :" + year, smallBold));
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Le " + format_fr.format(fromLocalDate(declarationExtraitDTO.getDateNaissanceEnfant())) + " est né(e) à "
            + declarationExtraitDTO.getLieuNaissanceEnfant() + ", "
            + declarationExtraitDTO.getPrenomEnfant() + " " + declarationExtraitDTO.getNomEnfant()
            + ", de " + declarationExtraitDTO.getPrenomPere() + " " + nomPere
            + " né le : " + dateNaissancePere
            + " à " + lieuNaissancePere + ", " + fonctionPere
            + " et de " + declarationExtraitDTO.getPrenomMere() + " " + declarationExtraitDTO.getNomMere() + ","
            + " née le : " + format_fr.format(fromLocalDate(declarationExtraitDTO.getDateNaissanceMere()))
            + " à " + declarationExtraitDTO.getLieuNaissanceMere() + ", " + fonctionMere + "."));
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Transcrit le " + format_fr.format(new Date()) + ", par Nous, " + personne_qui_transcrit + ", " + fonction
            + ", Officier de l’état-civil sur la foi de l’acte de naissance authentique, ci-contre, dressé par la Mairie de "
            + declarationExtraitDTO.getLieuDeclaration() + "."));
        document.add(preface);
        document.close();

    }

    @Transactional(readOnly = true)
    public List<DeclarationExtraitRechercheDTO> findExtraitByCriteria(DeclarationExtraitDTO declarationNaissanceDTO) {
        log.debug("Request to get DeclarationNaissances by criteria");
        List<DeclarationExtraitRechercheDTO> result = extraitService.findExtraitByCriteria(declarationNaissanceDTO);
        return result;
    }

    @Override
    public void supprimerActesImprimer(String acteNaissance, String transcriptionNaissance) {
        File documents = new File("src/main/webapp/app/documents/");
        if (documents.isDirectory()) {
            for (File file : documents.listFiles()) {
                if (file.getName().equals(acteNaissance) || file.getName().equals(transcriptionNaissance)) {
                    file.delete();
                }
            }
        }
    }

    /**
     * Get one declarationExtrait by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DeclarationExtraitRechercheDTO findOne(Long id) {
        log.debug("Request to get DeclarationExtrait : {}", id);
        DeclarationExtraitRechercheDTO extrait = extraitService.findExtraitById(id);
        extrait.setAgent(null);
        return extrait;
    }

    @Override
    public DeclarationExtraitRechercheDTO update(DeclarationExtraitRechercheDTO declarationExtraitDTO) {

        DeclarationExtraitRechercheDTO declarationExtraitUpdatedDTO = new DeclarationExtraitRechercheDTO();

        PersonneDTO enfantToUpdate = personneService.findOne(declarationExtraitDTO.getEnfant().getId());
        PersonneDTO mereToUpdate = personneService.findOne(declarationExtraitDTO.getMere().getId());
        PersonneDTO pereToUpdate = personneService.findOne(declarationExtraitDTO.getPere().getId());

        Set<PieceJointeDTO> piecesJointes = declarationExtraitDTO.getPiecesJointes();
        for (PieceJointeDTO pieceJointe : piecesJointes) {
            PieceJointeDTO pieceJointeToUpdate = pieceJointeService.findOne(pieceJointe.getId());

            pieceJointeToUpdate.setCopieCarte(pieceJointe.getCopieCarte());
            pieceJointeToUpdate.setCopieCarteContentType(pieceJointe.getCopieCarteContentType());

            pieceJointeToUpdate.setCopieLiterale(pieceJointe.getCopieLiterale());
            pieceJointeToUpdate.setCopieLiteraleContentType(pieceJointe.getCopieLiteraleContentType());

            pieceJointeService.update(pieceJointeToUpdate);
        }

        //modif enfant
        enfantToUpdate.setNom(declarationExtraitDTO.getEnfant().getNom());
        enfantToUpdate.setPrenom(declarationExtraitDTO.getEnfant().getPrenom());
        enfantToUpdate.setDateNaissance(declarationExtraitDTO.getEnfant().getDateNaissance());
        enfantToUpdate.setGenre(declarationExtraitDTO.getEnfant().getGenre());

        mereToUpdate.setNom(declarationExtraitDTO.getMere().getNom());
        mereToUpdate.setPrenom(declarationExtraitDTO.getMere().getPrenom());
        mereToUpdate.setDateNaissance(declarationExtraitDTO.getMere().getDateNaissance());
        mereToUpdate.setNumeroPassport(declarationExtraitDTO.getMere().getNumeroPassport());
        mereToUpdate.setNumeroCarteIdentite(declarationExtraitDTO.getMere().getNumeroCarteIdentite());
        mereToUpdate.setFonction(declarationExtraitDTO.getMere().getFonction());

        //je verifie si le pays existe sinon je le crée
        PaysDTO paysNaissanceEnfant = paysService.findByNom(declarationExtraitDTO.getEnfant().getPaysNaissance());

        PaysDTO paysNaissanceMere = paysService.findByNom(declarationExtraitDTO.getMere().getPaysNaissance());
        PaysDTO paysResidenceMere = paysService.findByNom(declarationExtraitDTO.getMere().getPaysResidence());

        VilleDTO lieuNaissanceEnfant = villeService.findByNomAndPaysId(declarationExtraitDTO.getEnfant().getVilleNaissance(), paysNaissanceEnfant, null);

        VilleDTO lieuNaissanceMere = villeService.findByNomAndPaysId(declarationExtraitDTO.getMere().getVilleNaissance(), paysNaissanceMere, null);
        VilleDTO villeResidenceMere = villeService.findByNomAndPaysId(declarationExtraitDTO.getMere().getVilleResidence(), paysResidenceMere, declarationExtraitDTO.getAdresseComplMere());


        enfantToUpdate.setLieuNaissanceId(lieuNaissanceEnfant.getId());
        enfantToUpdate.setVilleNaissance(lieuNaissanceEnfant.getNom());

        mereToUpdate.setAdresseId(villeResidenceMere.getId());
        mereToUpdate.setLieuNaissanceId(lieuNaissanceMere.getId());
        mereToUpdate.setPaysResidence(paysResidenceMere.getNom());
        mereToUpdate.setPaysNaissance(paysNaissanceMere.getNom());
        mereToUpdate.setVilleNaissance(lieuNaissanceMere.getNom());
        mereToUpdate.setVilleResidence(villeResidenceMere.getNom());
        mereToUpdate.setAdresseCompl(declarationExtraitDTO.getAdresseComplMere());

        //gestion des enfants sans pere
        if (null != declarationExtraitDTO.getPere()) {


            PaysDTO paysNaissancePere = paysService.findByNom(declarationExtraitDTO.getPere().getPaysNaissance());
            PaysDTO paysResidencePere = paysService.findByNom(declarationExtraitDTO.getPere().getPaysResidence());

            VilleDTO lieuNaissancePere = villeService.findByNomAndPaysId(declarationExtraitDTO.getPere().getVilleNaissance(), paysNaissancePere, null);
            VilleDTO villeResidencePere = villeService.findByNomAndPaysId(declarationExtraitDTO.getPere().getVilleResidence(), paysResidencePere, declarationExtraitDTO.getAdresseComplPere());

            pereToUpdate.setNom(declarationExtraitDTO.getPere().getNom());
            pereToUpdate.setPrenom(declarationExtraitDTO.getPere().getPrenom());
            pereToUpdate.setDateNaissance(declarationExtraitDTO.getPere().getDateNaissance());
            pereToUpdate.setFonction(declarationExtraitDTO.getPere().getFonction());
            pereToUpdate.setNumeroCarteIdentite(declarationExtraitDTO.getPere().getNumeroCarteIdentite());
            pereToUpdate.setNumeroPassport(declarationExtraitDTO.getPere().getNumeroPassport());
            pereToUpdate.setAdresseCompl(declarationExtraitDTO.getAdresseComplPere());

            pereToUpdate.setAdresseId(villeResidencePere.getId());
            pereToUpdate.setLieuNaissanceId(lieuNaissancePere.getId());
            pereToUpdate.setPaysResidence(paysResidencePere.getNom());
            pereToUpdate.setPaysNaissance(paysNaissancePere.getNom());
            pereToUpdate.setVilleNaissance(lieuNaissancePere.getNom());
            pereToUpdate.setVilleResidence(villeResidencePere.getNom());

            enfantToUpdate = personneService.update(enfantToUpdate);
            mereToUpdate = personneService.update(mereToUpdate);
            pereToUpdate = personneService.update(pereToUpdate);
            declarationExtraitUpdatedDTO.setPere(pereToUpdate);
        }

        declarationExtraitUpdatedDTO.setEnfant(enfantToUpdate);
        declarationExtraitUpdatedDTO.setMere(mereToUpdate);


        return declarationExtraitUpdatedDTO;
    }
    /*
     *//**
     * Get all the declarationExtraits.
     *
     * @return the list of entities
     *//*
    @Override
    @Transactional(readOnly = true)
    public List<DeclarationExtraitDTO> findAll() {
        log.debug("Request to get all DeclarationExtraits");
        List<DeclarationExtraitDTO> result = declarationExtraitRepository.findAll().stream()
            .map(declarationExtraitMapper::declarationExtraitToDeclarationExtraitDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    *//**
     * Get one declarationExtrait by id.
     *
     * @param id the id of the entity
     * @return the entity
     *//*
    @Override
    @Transactional(readOnly = true)
    public DeclarationExtraitDTO findOne(Long id) {
        log.debug("Request to get DeclarationExtrait : {}", id);
        DeclarationExtrait declarationExtrait = declarationExtraitRepository.findOne(id);
        DeclarationExtraitDTO declarationExtraitDTO = declarationExtraitMapper.declarationExtraitToDeclarationExtraitDTO(declarationExtrait);
        return declarationExtraitDTO;
    }

    *//**
     * Delete the  declarationExtrait by id.
     *
     * @param id the id of the entity
     *//*
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeclarationExtrait : {}", id);
        declarationExtraitRepository.delete(id);
        declarationExtraitSearchRepository.delete(id);
    }

    *//**
     * Search for the declarationExtrait corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     *//*
    @Override
    @Transactional(readOnly = true)
    public List<DeclarationExtraitDTO> search(String query) {
        log.debug("Request to search DeclarationExtraits for query {}", query);
        return StreamSupport
            .stream(declarationExtraitSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(declarationExtraitMapper::declarationExtraitToDeclarationExtraitDTO)
            .collect(Collectors.toList());
    }*/
}

