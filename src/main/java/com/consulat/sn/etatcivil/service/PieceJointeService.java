package com.consulat.sn.etatcivil.service;

import com.consulat.sn.etatcivil.service.dto.PieceJointeDTO;
import java.util.List;

/**
 * Service Interface for managing PieceJointe.
 */
public interface PieceJointeService {

    /**
     * Save a pieceJointe.
     *
     * @param pieceJointeDTO the entity to save
     * @return the persisted entity
     */
    PieceJointeDTO save(PieceJointeDTO pieceJointeDTO);

    /**
     *  Get all the pieceJointes.
     *
     *  @return the list of entities
     */
    List<PieceJointeDTO> findAll();

    /**
     *  Get the "id" pieceJointe.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PieceJointeDTO findOne(Long id);

    /**
     *  Delete the "id" pieceJointe.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    PieceJointeDTO update(PieceJointeDTO pieceJointeToUpdate);
}
