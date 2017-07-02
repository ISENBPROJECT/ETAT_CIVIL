package com.consulat.sn.etatcivil.service;

import com.consulat.sn.etatcivil.service.dto.RegistreNaissanceDTO;
import java.util.List;

/**
 * Service Interface for managing RegistreNaissance.
 */
public interface RegistreNaissanceService {

    /**
     * Save a registreNaissance.
     *
     * @param registreNaissanceDTO the entity to save
     * @return the persisted entity
     */
    RegistreNaissanceDTO save(RegistreNaissanceDTO registreNaissanceDTO);

    /**
     *  Get all the registreNaissances.
     *
     *  @return the list of entities
     */
    List<RegistreNaissanceDTO> findAll();

    /**
     *  Get the "id" registreNaissance.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RegistreNaissanceDTO findOne(Long id);

    /**
     *  Delete the "id" registreNaissance.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
