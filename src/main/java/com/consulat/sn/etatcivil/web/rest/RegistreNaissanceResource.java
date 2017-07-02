package com.consulat.sn.etatcivil.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.consulat.sn.etatcivil.service.RegistreNaissanceService;
import com.consulat.sn.etatcivil.web.rest.util.HeaderUtil;
import com.consulat.sn.etatcivil.service.dto.RegistreNaissanceDTO;
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
 * REST controller for managing RegistreNaissance.
 */
@RestController
@RequestMapping("/api")
public class RegistreNaissanceResource {

    private final Logger log = LoggerFactory.getLogger(RegistreNaissanceResource.class);

    private static final String ENTITY_NAME = "registreNaissance";

    private final RegistreNaissanceService registreNaissanceService;

    public RegistreNaissanceResource(RegistreNaissanceService registreNaissanceService) {
        this.registreNaissanceService = registreNaissanceService;
    }

    /**
     * POST  /registre-naissances : Create a new registreNaissance.
     *
     * @param registreNaissanceDTO the registreNaissanceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new registreNaissanceDTO, or with status 400 (Bad Request) if the registreNaissance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/registre-naissances")
    @Timed
    public ResponseEntity<RegistreNaissanceDTO> createRegistreNaissance(@Valid @RequestBody RegistreNaissanceDTO registreNaissanceDTO) throws URISyntaxException {
        log.debug("REST request to save RegistreNaissance : {}", registreNaissanceDTO);
        if (registreNaissanceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new registreNaissance cannot already have an ID")).body(null);
        }
        RegistreNaissanceDTO result = registreNaissanceService.save(registreNaissanceDTO);
        return ResponseEntity.created(new URI("/api/registre-naissances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /registre-naissances : Updates an existing registreNaissance.
     *
     * @param registreNaissanceDTO the registreNaissanceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated registreNaissanceDTO,
     * or with status 400 (Bad Request) if the registreNaissanceDTO is not valid,
     * or with status 500 (Internal Server Error) if the registreNaissanceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/registre-naissances")
    @Timed
    public ResponseEntity<RegistreNaissanceDTO> updateRegistreNaissance(@Valid @RequestBody RegistreNaissanceDTO registreNaissanceDTO) throws URISyntaxException {
        log.debug("REST request to update RegistreNaissance : {}", registreNaissanceDTO);
        if (registreNaissanceDTO.getId() == null) {
            return createRegistreNaissance(registreNaissanceDTO);
        }
        RegistreNaissanceDTO result = registreNaissanceService.save(registreNaissanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, registreNaissanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /registre-naissances : get all the registreNaissances.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of registreNaissances in body
     */
    @GetMapping("/registre-naissances")
    @Timed
    public List<RegistreNaissanceDTO> getAllRegistreNaissances() {
        log.debug("REST request to get all RegistreNaissances");
        return registreNaissanceService.findAll();
    }

    /**
     * GET  /registre-naissances/:id : get the "id" registreNaissance.
     *
     * @param id the id of the registreNaissanceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the registreNaissanceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/registre-naissances/{id}")
    @Timed
    public ResponseEntity<RegistreNaissanceDTO> getRegistreNaissance(@PathVariable Long id) {
        log.debug("REST request to get RegistreNaissance : {}", id);
        RegistreNaissanceDTO registreNaissanceDTO = registreNaissanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(registreNaissanceDTO));
    }

    /**
     * DELETE  /registre-naissances/:id : delete the "id" registreNaissance.
     *
     * @param id the id of the registreNaissanceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/registre-naissances/{id}")
    @Timed
    public ResponseEntity<Void> deleteRegistreNaissance(@PathVariable Long id) {
        log.debug("REST request to delete RegistreNaissance : {}", id);
        registreNaissanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
