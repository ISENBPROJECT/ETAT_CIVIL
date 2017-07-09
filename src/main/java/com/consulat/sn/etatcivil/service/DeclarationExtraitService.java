package com.consulat.sn.etatcivil.service;


import com.consulat.sn.etatcivil.service.dto.DeclarationExtraitDTO;

import java.util.List;

/**
 * Service Interface for managing Extrait.
 */
public interface DeclarationExtraitService {

    /**
     * Save a declarationExtrait.
     *
     * @param declarationExtraitDTO the entity to save
     * @return the persisted entity
     */
    DeclarationExtraitDTO save(DeclarationExtraitDTO declarationExtraitDTO);

    List<DeclarationExtraitDTO> findExtraitByCriteria(DeclarationExtraitDTO declarationNaissanceDTO);

  /*  *//**
     * Get all the declarationExtraits.
     *
     * @return the list of entities
     *//*
    List<DeclarationExtraitDTO> findAll();

    *//**
     * Get the "id" declarationExtrait.
     *
     * @param id the id of the entity
     * @return the entity
     *//*
    DeclarationExtraitDTO findOne(Long id);

    *//**
     * Delete the "id" declarationExtrait.
     *
     * @param id the id of the entity
     *//*
    void delete(Long id);

    *//**
     * Search for the declarationExtrait corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     *//*
    List<DeclarationExtraitDTO> search(String query);
*/
}
