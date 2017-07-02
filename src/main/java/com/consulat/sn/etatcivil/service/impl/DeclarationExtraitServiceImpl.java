package com.consulat.sn.etatcivil.service.impl;

import com.consulat.sn.etatcivil.domain.User;
import com.consulat.sn.etatcivil.domain.enumeration.Genre;
import com.consulat.sn.etatcivil.service.*;
import com.consulat.sn.etatcivil.service.dto.*;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    private final PersonneService personneService;
    private final PieceJointeService pieceJointeService;
    private final RegistreNaissanceService registreNaissanceService;
    private final UserService userService;
    public Integer newRegistreNumero = 0;
    private Long enfantId = null;
    private Long pereId = null;
    private Long mereId = null;
    private final VilleService villeService;

    // private final DeclarationExtraitSearchRepository declarationExtraitSearchRepository;
    public DeclarationExtraitServiceImpl(PieceJointeService pieceJointeService, PersonneService personneService, ExtraitService extraitService,
                                         RegistreNaissanceService registreNaissanceService, UserService userService, VilleService villeService) {
        this.extraitService = extraitService;
        this.personneService = personneService;
        this.pieceJointeService = pieceJointeService;
        this.registreNaissanceService = registreNaissanceService;
        this.userService = userService;
        this.villeService = villeService;
    }

    /**
     * Save a declarationExtrait.
     *
     * @param declarationExtraitDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DeclarationExtraitDTO save(DeclarationExtraitDTO declarationExtraitDTO) {

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
            extraitDTO.setLieuDeclarationId(declarationExtraitDTO.getLieuDeclarationId().getId());
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
            printExtraitNaissance(extraitDTO.getId());
        }
        log.debug("Request to save DeclarationExtrait : {}", declarationExtraitDTO);
        // ExtraitDTO declarationExtrait = declarationExtraitMapper.toExtrait(declarationExtraitDTO);

        return declarationExtraitDTO;
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

        personneService.isPersonneExist("kaze", "sjiozehi");

        enfant.setNom(declarationExtraitDTO.getNomEnfant());
        enfant.setPrenom(declarationExtraitDTO.getPrenomEnfant());
        enfant.setDateNaissance(declarationExtraitDTO.getDateNaissanceEnfant());
        enfant.setGenre(declarationExtraitDTO.getGenreEnfant());
        enfant.setLieuNaissanceId(declarationExtraitDTO.getLieuNaissanceEnfantId().getId());
        enfant.setAdresseId(declarationExtraitDTO.getAdresseMereId().getId());


        mere.setNom(declarationExtraitDTO.getNomMere());
        mere.setPrenom(declarationExtraitDTO.getPrenomMere());
        //mere.setDateNaissance(fromLocalDate(declarationExtraitDTO.getDateNaissanceMere()));
        mere.setGenre(Genre.FEMININ);
        mere.setAdresseId(declarationExtraitDTO.getAdresseMereId().getId());
        mere.setFonction(declarationExtraitDTO.getFonctionMere());
        mere.setLieuNaissanceId(declarationExtraitDTO.getLieuNaissanceMereId().getId());


        pere.setNom(declarationExtraitDTO.getNomPere());
        pere.setPrenom(declarationExtraitDTO.getPrenomPere());
        pere.setDateNaissance(declarationExtraitDTO.getDateNaissancePere());
        pere.setGenre(Genre.MASCULIN);
        pere.setAdresseId(declarationExtraitDTO.getAdressePereId().getId());
        pere.setFonction(declarationExtraitDTO.getFonctionPere());
        pere.setLieuNaissanceId(declarationExtraitDTO.getLieuNaissancePereId().getId());

        pere = personneService.save(pere);
        mere = personneService.save(mere);
        if (null != pere.getId() && null != mere.getId()) {
            enfant.setPereId(pere.getId());
            enfant.setMereId(mere.getId());
            enfant = personneService.save(enfant);
            enfantId = enfant.getId();
            pereId = pere.getId();
            mereId = mere.getId();
            result = true;

        }
        if (null == pereId && null != mereId) {
            personneService.delete(mereId);
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


    public String printExtraitNaissance(Long idExtrait) {

        ExtraitDTO extraitDTO = extraitService.findOne(idExtrait);
        PersonneDTO enfant = personneService.findOne(extraitDTO.getEnfantId());
        PersonneDTO mere = personneService.findOne(extraitDTO.getMereId());
        PersonneDTO pere = personneService.findOne(extraitDTO.getPereId());
        VilleDTO lieuDeclaration = villeService.findOne(extraitDTO.getLieuDeclarationId());
        VilleDTO lieuNaissanceEnfant = villeService.findOne(enfant.getLieuNaissanceId());

        String acteNaissance = "";
        if (null != extraitDTO && null != enfant && null != mere && null != pere) {
            PdfReader pdfTemplate;
            acteNaissance = enfant.getPrenom() + "_" + enfant.getNom()
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
    }public static Date local(LocalDate date) {
        Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault())
            .toInstant();
        //return ZonedDateTime.from(TemporalAccessor.class);
        return null;
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

