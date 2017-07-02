package com.consulat.sn.etatcivil.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.consulat.sn.etatcivil.service.PersonneService;
import com.consulat.sn.etatcivil.web.rest.util.HeaderUtil;
import com.consulat.sn.etatcivil.service.dto.PersonneDTO;
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
 * REST controller for managing Personne.
 */
@RestController
@RequestMapping("/api")
public class PersonneResource {

    private final Logger log = LoggerFactory.getLogger(PersonneResource.class);

    private static final String ENTITY_NAME = "personne";

    private final PersonneService personneService;

    public PersonneResource(PersonneService personneService) {
        this.personneService = personneService;
    }

    /**
     * POST  /personnes : Create a new personne.
     *
     * @param personneDTO the personneDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personneDTO, or with status 400 (Bad Request) if the personne has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/personnes")
    @Timed
    public ResponseEntity<PersonneDTO> createPersonne(@Valid @RequestBody PersonneDTO personneDTO) throws URISyntaxException {
        log.debug("REST request to save Personne : {}", personneDTO);
        if (personneDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new personne cannot already have an ID")).body(null);
        }
        PersonneDTO result = personneService.save(personneDTO);
        return ResponseEntity.created(new URI("/api/personnes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /personnes : Updates an existing personne.
     *
     * @param personneDTO the personneDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personneDTO,
     * or with status 400 (Bad Request) if the personneDTO is not valid,
     * or with status 500 (Internal Server Error) if the personneDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/personnes")
    @Timed
    public ResponseEntity<PersonneDTO> updatePersonne(@Valid @RequestBody PersonneDTO personneDTO) throws URISyntaxException {
        log.debug("REST request to update Personne : {}", personneDTO);
        if (personneDTO.getId() == null) {
            return createPersonne(personneDTO);
        }
        PersonneDTO result = personneService.save(personneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /personnes : get all the personnes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of personnes in body
     */
    @GetMapping("/personnes")
    @Timed
    public List<PersonneDTO> getAllPersonnes() {
        log.debug("REST request to get all Personnes");
        return personneService.findAll();
    }

    /**
     * GET  /personnes/:id : get the "id" personne.
     *
     * @param id the id of the personneDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personneDTO, or with status 404 (Not Found)
     */
    @GetMapping("/personnes/{id}")
    @Timed
    public ResponseEntity<PersonneDTO> getPersonne(@PathVariable Long id) {
        log.debug("REST request to get Personne : {}", id);
        PersonneDTO personneDTO = personneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(personneDTO));
    }

    /**
     * DELETE  /personnes/:id : delete the "id" personne.
     *
     * @param id the id of the personneDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/personnes/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonne(@PathVariable Long id) {
        log.debug("REST request to delete Personne : {}", id);
        personneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
