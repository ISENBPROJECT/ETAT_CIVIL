package com.consulat.sn.etatcivil.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.consulat.sn.etatcivil.service.DeclarationExtraitService;
import com.consulat.sn.etatcivil.service.dto.DeclarationExtraitDTO;
import com.consulat.sn.etatcivil.service.dto.DeclarationExtraitRechercheDTO;
import com.consulat.sn.etatcivil.web.rest.util.HeaderUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for managing DeclarationExtrait.
 */
@RestController
@RequestMapping("/api")
public class DeclarationExtraitResource {
    private static final String ENTITY_NAME = "declarationExtrait";
    private final Logger log = LoggerFactory.getLogger(DeclarationExtraitResource.class);
    private final DeclarationExtraitService declarationExtraitService;
    private static String UNDEFINED = "undefined";
    private String [] datePattern = {"yyyy-MM-dd"};
    final org.joda.time.format.DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MMM-dd");
    public DeclarationExtraitResource(DeclarationExtraitService declarationExtraitService) {
        this.declarationExtraitService = declarationExtraitService;
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
        if (declarationExtraitDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new declarationExtrait cannot already have an ID")).body(null);
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
    public ResponseEntity<DeclarationExtraitDTO> updateDeclarationExtrait(@Valid @RequestBody DeclarationExtraitDTO declarationExtraitDTO) throws URISyntaxException {
        log.debug("REST request to update DeclarationExtrait : {}", declarationExtraitDTO);
        if (declarationExtraitDTO.getId() == null) {
            return createDeclarationExtrait(declarationExtraitDTO);
        }
        DeclarationExtraitDTO result = declarationExtraitService.save(declarationExtraitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, declarationExtraitDTO.getId().toString()))
            .body(result);
    }

    @RequestMapping(value = "/declaration-extraits",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DeclarationExtraitDTO> searchDeclarationRecherche(@RequestBody DeclarationExtraitRechercheDTO declarationExtraitDTO) {
        log.debug("REST request to search DeclarationNaissance");

        return declarationExtraitService.findExtraitByCriteria(null);
    }
/*
    *//**
     * GET  /declaration-extraits : get all the declarationExtraits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of declarationExtraits in body
     *//*
    @GetMapping("/declaration-extraits")
    @Timed
    public List<DeclarationExtraitDTO> getAllDeclarationExtraits() {
        log.debug("REST request to get all DeclarationExtraits");
        return declarationExtraitService.findAll();
    }

    *//**
     * GET  /declaration-extraits/:id : get the "id" declarationExtrait.
     *
     * @param id the id of the declarationExtraitDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the declarationExtraitDTO, or with status 404 (Not Found)
     *//*
    @GetMapping("/declaration-extraits/{id}")
    @Timed
    public ResponseEntity<DeclarationExtraitDTO> getDeclarationExtrait(@PathVariable Long id) {
        log.debug("REST request to get DeclarationExtrait : {}", id);
        DeclarationExtraitDTO declarationExtraitDTO = declarationExtraitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(declarationExtraitDTO));
    }

    *//**
     * DELETE  /declaration-extraits/:id : delete the "id" declarationExtrait.
     *
     * @param id the id of the declarationExtraitDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     *//*
    @DeleteMapping("/declaration-extraits/{id}")
    @Timed
    public ResponseEntity<Void> deleteDeclarationExtrait(@PathVariable Long id) {
        log.debug("REST request to delete DeclarationExtrait : {}", id);
        declarationExtraitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    *//**
     * SEARCH  /_search/declaration-extraits?query=:query : search for the declarationExtrait corresponding
     * to the query.
     *
     * @param query the query of the declarationExtrait search
     * @return the result of the search
     *//*
    @GetMapping("/_search/declaration-extraits")
    @Timed
    public List<DeclarationExtraitDTO> searchDeclarationExtraits(@RequestParam String query) {
        log.debug("REST request to search DeclarationExtraits for query {}", query);
        return declarationExtraitService.search(query);
    }*/
}
