package com.consulat.sn.etatcivil.service;

import com.consulat.sn.etatcivil.service.dto.PaysDTO;
import java.util.List;

/**
 * Service Interface for managing Pays.
 */
public interface PaysService {

    /**
     * Save a pays.
     *
     * @param paysDTO the entity to save
     * @return the persisted entity
     */
    PaysDTO save(PaysDTO paysDTO);

    /**
     *  Get all the pays.
     *
     *  @return the list of entities
     */
    List<PaysDTO> findAll();

    /**
     *  Get the "id" pays.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PaysDTO findOne(Long id);

    /**
     *  Delete the "id" pays.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    PaysDTO findByNom(String paysNaissanceEnfant);
}
