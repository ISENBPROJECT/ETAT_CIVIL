package com.consulat.sn.etatcivil.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.consulat.sn.etatcivil.service.CommuneService;
import com.consulat.sn.etatcivil.web.rest.util.HeaderUtil;
import com.consulat.sn.etatcivil.service.dto.CommuneDTO;
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
 * REST controller for managing Commune.
 */
@RestController
@RequestMapping("/api")
public class CommuneResource {

    private final Logger log = LoggerFactory.getLogger(CommuneResource.class);

    private static final String ENTITY_NAME = "commune";

    private final CommuneService communeService;

    public CommuneResource(CommuneService communeService) {
        this.communeService = communeService;
    }

    /**
     * POST  /communes : Create a new commune.
     *
     * @param communeDTO the communeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new communeDTO, or with status 400 (Bad Request) if the commune has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/communes")
    @Timed
    public ResponseEntity<CommuneDTO> createCommune(@Valid @RequestBody CommuneDTO communeDTO) throws URISyntaxException {
        log.debug("REST request to save Commune : {}", communeDTO);
        if (communeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new commune cannot already have an ID")).body(null);
        }
        CommuneDTO result = communeService.save(communeDTO);
        return ResponseEntity.created(new URI("/api/communes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /communes : Updates an existing commune.
     *
     * @param communeDTO the communeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated communeDTO,
     * or with status 400 (Bad Request) if the communeDTO is not valid,
     * or with status 500 (Internal Server Error) if the communeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/communes")
    @Timed
    public ResponseEntity<CommuneDTO> updateCommune(@Valid @RequestBody CommuneDTO communeDTO) throws URISyntaxException {
        log.debug("REST request to update Commune : {}", communeDTO);
        if (communeDTO.getId() == null) {
            return createCommune(communeDTO);
        }
        CommuneDTO result = communeService.save(communeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, communeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /communes : get all the communes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of communes in body
     */
    @GetMapping("/communes")
    @Timed
    public List<CommuneDTO> getAllCommunes() {
        log.debug("REST request to get all Communes");
        return communeService.findAll();
    }

    /**
     * GET  /communes/:id : get the "id" commune.
     *
     * @param id the id of the communeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the communeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/communes/{id}")
    @Timed
    public ResponseEntity<CommuneDTO> getCommune(@PathVariable Long id) {
        log.debug("REST request to get Commune : {}", id);
        CommuneDTO communeDTO = communeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(communeDTO));
    }

    /**
     * DELETE  /communes/:id : delete the "id" commune.
     *
     * @param id the id of the communeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/communes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommune(@PathVariable Long id) {
        log.debug("REST request to delete Commune : {}", id);
        communeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
