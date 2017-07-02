package com.consulat.sn.etatcivil.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.consulat.sn.etatcivil.service.PieceJointeService;
import com.consulat.sn.etatcivil.web.rest.util.HeaderUtil;
import com.consulat.sn.etatcivil.service.dto.PieceJointeDTO;
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
 * REST controller for managing PieceJointe.
 */
@RestController
@RequestMapping("/api")
public class PieceJointeResource {

    private final Logger log = LoggerFactory.getLogger(PieceJointeResource.class);

    private static final String ENTITY_NAME = "pieceJointe";

    private final PieceJointeService pieceJointeService;

    public PieceJointeResource(PieceJointeService pieceJointeService) {
        this.pieceJointeService = pieceJointeService;
    }

    /**
     * POST  /piece-jointes : Create a new pieceJointe.
     *
     * @param pieceJointeDTO the pieceJointeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pieceJointeDTO, or with status 400 (Bad Request) if the pieceJointe has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/piece-jointes")
    @Timed
    public ResponseEntity<PieceJointeDTO> createPieceJointe(@Valid @RequestBody PieceJointeDTO pieceJointeDTO) throws URISyntaxException {
        log.debug("REST request to save PieceJointe : {}", pieceJointeDTO);
        if (pieceJointeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pieceJointe cannot already have an ID")).body(null);
        }
        PieceJointeDTO result = pieceJointeService.save(pieceJointeDTO);
        return ResponseEntity.created(new URI("/api/piece-jointes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /piece-jointes : Updates an existing pieceJointe.
     *
     * @param pieceJointeDTO the pieceJointeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pieceJointeDTO,
     * or with status 400 (Bad Request) if the pieceJointeDTO is not valid,
     * or with status 500 (Internal Server Error) if the pieceJointeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/piece-jointes")
    @Timed
    public ResponseEntity<PieceJointeDTO> updatePieceJointe(@Valid @RequestBody PieceJointeDTO pieceJointeDTO) throws URISyntaxException {
        log.debug("REST request to update PieceJointe : {}", pieceJointeDTO);
        if (pieceJointeDTO.getId() == null) {
            return createPieceJointe(pieceJointeDTO);
        }
        PieceJointeDTO result = pieceJointeService.save(pieceJointeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pieceJointeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /piece-jointes : get all the pieceJointes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pieceJointes in body
     */
    @GetMapping("/piece-jointes")
    @Timed
    public List<PieceJointeDTO> getAllPieceJointes() {
        log.debug("REST request to get all PieceJointes");
        return pieceJointeService.findAll();
    }

    /**
     * GET  /piece-jointes/:id : get the "id" pieceJointe.
     *
     * @param id the id of the pieceJointeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pieceJointeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/piece-jointes/{id}")
    @Timed
    public ResponseEntity<PieceJointeDTO> getPieceJointe(@PathVariable Long id) {
        log.debug("REST request to get PieceJointe : {}", id);
        PieceJointeDTO pieceJointeDTO = pieceJointeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pieceJointeDTO));
    }

    /**
     * DELETE  /piece-jointes/:id : delete the "id" pieceJointe.
     *
     * @param id the id of the pieceJointeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/piece-jointes/{id}")
    @Timed
    public ResponseEntity<Void> deletePieceJointe(@PathVariable Long id) {
        log.debug("REST request to delete PieceJointe : {}", id);
        pieceJointeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
