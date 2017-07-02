package com.consulat.sn.etatcivil.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.consulat.sn.etatcivil.service.ExtraitService;
import com.consulat.sn.etatcivil.web.rest.util.HeaderUtil;
import com.consulat.sn.etatcivil.service.dto.ExtraitDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Extrait.
 */
@RestController
@RequestMapping("/api")
public class ExtraitResource {

    private final Logger log = LoggerFactory.getLogger(ExtraitResource.class);

    private static final String ENTITY_NAME = "extrait";

    private final ExtraitService extraitService;

    public ExtraitResource(ExtraitService extraitService) {
        this.extraitService = extraitService;
    }

    /**
     * POST  /extraits : Create a new extrait.
     *
     * @param extraitDTO the extraitDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new extraitDTO, or with status 400 (Bad Request) if the extrait has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/extraits")
    @Timed
    public ResponseEntity<ExtraitDTO> createExtrait(@Valid @RequestBody ExtraitDTO extraitDTO) throws URISyntaxException {
        log.debug("REST request to save Extrait : {}", extraitDTO);
        if (extraitDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new extrait cannot already have an ID")).body(null);
        }
        ExtraitDTO result = extraitService.save(extraitDTO);
        return ResponseEntity.created(new URI("/api/extraits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /extraits : Updates an existing extrait.
     *
     * @param extraitDTO the extraitDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated extraitDTO,
     * or with status 400 (Bad Request) if the extraitDTO is not valid,
     * or with status 500 (Internal Server Error) if the extraitDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/extraits")
    @Timed
    public ResponseEntity<ExtraitDTO> updateExtrait(@Valid @RequestBody ExtraitDTO extraitDTO) throws URISyntaxException {
        log.debug("REST request to update Extrait : {}", extraitDTO);
        if (extraitDTO.getId() == null) {
            return createExtrait(extraitDTO);
        }
        ExtraitDTO result = extraitService.save(extraitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, extraitDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /extraits : get all the extraits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of extraits in body
     */
    @GetMapping("/extraits")
    @Timed
    public List<ExtraitDTO> getAllExtraits() {
        log.debug("REST request to get all Extraits");
        return extraitService.findAll();
    }

    /**
     * GET  /extraits/:id : get the "id" extrait.
     *
     * @param id the id of the extraitDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the extraitDTO, or with status 404 (Not Found)
     */
    @GetMapping("/extraits/{id}")
    @Timed
    public ResponseEntity<ExtraitDTO> getExtrait(@PathVariable Long id) {
        log.debug("REST request to get Extrait : {}", id);
        ExtraitDTO extraitDTO = extraitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(extraitDTO));
    }

    /**
     * DELETE  /extraits/:id : delete the "id" extrait.
     *
     * @param id the id of the extraitDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/extraits/{id}")
    @Timed
    public ResponseEntity<Void> deleteExtrait(@PathVariable Long id) {
        log.debug("REST request to delete Extrait : {}", id);
        extraitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
