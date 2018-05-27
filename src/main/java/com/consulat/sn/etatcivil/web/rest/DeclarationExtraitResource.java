package com.consulat.sn.etatcivil.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.consulat.sn.etatcivil.service.DeclarationExtraitService;
import com.consulat.sn.etatcivil.service.ExtraitService;
import com.consulat.sn.etatcivil.service.PersonneService;
import com.consulat.sn.etatcivil.service.RegistreNaissanceService;
import com.consulat.sn.etatcivil.service.dto.DeclarationExtraitDTO;
import com.consulat.sn.etatcivil.service.dto.DeclarationExtraitRechercheDTO;
import com.consulat.sn.etatcivil.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DeclarationExtrait.
 */
@RestController
@RequestMapping("/api")
public class DeclarationExtraitResource {
    private static final String ENTITY_NAME = "declarationExtrait";
    private final Logger log = LoggerFactory.getLogger(DeclarationExtraitResource.class);

    private final DeclarationExtraitService declarationExtraitService;

    private final RegistreNaissanceService registreNaissanceService;
    @Autowired
    ServletContext context;

    public DeclarationExtraitResource(DeclarationExtraitService declarationExtraitService, PersonneService personneService, ExtraitService extraitService, RegistreNaissanceService registreNaissanceService) {
        this.declarationExtraitService = declarationExtraitService;
        this.registreNaissanceService = registreNaissanceService;
    }

    /**
     * POST  /declaration-extraits : Create a new declarationExtrait.
     *
     * @param declarationExtraitDTO the declarationExtraitDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new declarationExtraitDTO, or with status 400 (Bad Request) if the declarationExtrait has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/declaration-extraits")
    @Timed
    public ResponseEntity<DeclarationExtraitDTO> createDeclarationExtrait(@Valid @RequestBody DeclarationExtraitDTO declarationExtraitDTO) throws URISyntaxException {
        log.debug("REST request to save DeclarationExtrait : {}", declarationExtraitDTO);
/*
        List<PersonneDTO> enfantDTOs = personneService.finddByNomPrenomDateNaissance(declarationExtraitDTO.getNomEnfant(), declarationExtraitDTO.getPrenomEnfant(), declarationExtraitDTO.getDateNaissanceEnfant());
        List<PersonneDTO> mereDTOs = new ArrayList<>();
        List<PersonneDTO> pereDTOs = new ArrayList<>();
        //uniquement si les deux parents ont des cartes d'identité
        if (null != declarationExtraitDTO.getNumeroIdentiteMere() && null != declarationExtraitDTO.getNumeroIdentitePere()) {

            mereDTOs = personneService.finddByNomPrenomDateNaissanceAndNumeroCarteIdentite(declarationExtraitDTO.getNomMere(), declarationExtraitDTO.getPrenomMere(), declarationExtraitDTO.getDateNaissanceMere(), declarationExtraitDTO.getNumeroIdentiteMere());
            pereDTOs = personneService.finddByNomPrenomDateNaissanceAndNumeroCarteIdentite(declarationExtraitDTO.getNomPere(), declarationExtraitDTO.getPrenomPere(), declarationExtraitDTO.getDateNaissancePere(), declarationExtraitDTO.getNumeroIdentitePere());
        }

        Boolean isDeclarationExist = false;
        Long tableauIdPersonnesTrouvees[];

        if (null != mereDTOs.get(0).getId() && null != pereDTOs.get(0).getId() && null != enfantDTOs.get(i).getId()) {
            isDeclarationExist = extraitService.findExistantExtrait(enfantDTO.getId(), mereDTO.getId(), pereDTO.getId());
        }

        if (isDeclarationExist) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlertExistenceDeclaration(ENTITY_NAME, "declarationexist", "Cette enfant a déjà été déclaré")).body(null);
        }*/
        if (null != declarationExtraitDTO.getNumeroRegistre()) {

            boolean isNumeroExist = registreNaissanceService.isNumeroExist(declarationExtraitDTO.getNumeroRegistre());
            if (isNumeroExist) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlertExistenceDeclaration(ENTITY_NAME, "declarationexist", "Cette enfant a déjà été déclaré")).body(null);
            }
        }
        DeclarationExtraitDTO result = declarationExtraitService.save(declarationExtraitDTO);
        return ResponseEntity.created(new URI("/api/extraits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /declaration-extraits : Updates an existing declarationExtrait.
     *
     * @param declarationExtraitDTO the declarationExtraitDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated declarationExtraitDTO,
     * or with status 400 (Bad Request) if the declarationExtraitDTO is not valid,
     * or with status 500 (Internal Server Error) if the declarationExtraitDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/declaration-extraits")
    @Timed
    public ResponseEntity<DeclarationExtraitDTO> updateDeclarationExtrait(@RequestBody DeclarationExtraitRechercheDTO declarationExtraitDTO) throws URISyntaxException {
        log.debug("REST request to update DeclarationExtrait : {}", declarationExtraitDTO);

        if (null != declarationExtraitDTO) {
            declarationExtraitService.update(declarationExtraitDTO);
        }

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, declarationExtraitDTO.getNumeroRegistre()))
            .body(null);
    }

    @RequestMapping(value = "/declaration-extraits-recherche",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DeclarationExtraitRechercheDTO> searchDeclarationRecherche(@RequestBody DeclarationExtraitDTO declarationExtraitDTO) {
        log.debug("REST request to search DeclarationNaissance");

        return declarationExtraitService.findExtraitByCriteria(declarationExtraitDTO);
    }


    /**
     * GET  /declaration-extraits/:id : get the "id" declarationExtrait.
     *
     * @param id the id of the declarationExtraitDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the declarationExtraitDTO, or with status 404 (Not Found)
     */
    @GetMapping("/declaration-extraits/{id}")
    @Timed
    public ResponseEntity<DeclarationExtraitRechercheDTO> getDeclarationExtrait(@PathVariable Long id) {
        log.debug("REST request to get DeclarationExtrait : {}", id);


        DeclarationExtraitRechercheDTO declarationExtraitDTO = declarationExtraitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(declarationExtraitDTO));
    }

    @RequestMapping(value = "/declaration-extraits-print", method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public DeclarationExtraitRechercheDTO printDeclarationPrint(@RequestBody DeclarationExtraitDTO declarationExtraitDTO) {
        log.debug("REST request to search DeclarationNaissance");
        String nomFichier = "";
        DeclarationExtraitRechercheDTO extrait = new DeclarationExtraitRechercheDTO();
        if (null != declarationExtraitDTO && declarationExtraitDTO.getPrintExtrait()) {
            nomFichier = declarationExtraitService.printExtraitNaissance(declarationExtraitDTO.getId());
            extrait.setNomExtrait(nomFichier);
        }else if (null != declarationExtraitDTO && !declarationExtraitDTO.getPrintExtrait()){
            nomFichier = declarationExtraitService.printTranscriptionNaissance(declarationExtraitDTO.getId());
            extrait.setNomExtrait(nomFichier);
        }
        return extrait;
    }
}
