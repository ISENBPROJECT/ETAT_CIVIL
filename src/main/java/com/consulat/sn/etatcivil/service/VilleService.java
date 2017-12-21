package com.consulat.sn.etatcivil.service;

import com.consulat.sn.etatcivil.service.dto.PaysDTO;
import com.consulat.sn.etatcivil.service.dto.VilleDTO;
import java.util.List;

/**
 * Service Interface for managing Ville.
 */
public interface VilleService {

    /**
     * Save a ville.
     *
     * @param villeDTO the entity to save
     * @return the persisted entity
     */
    VilleDTO save(VilleDTO villeDTO);

    /**
     *  Get all the villes.
     *
     *  @return the list of entities
     */
    List<VilleDTO> findAll();

    /**
     *  Get the "id" ville.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    VilleDTO findOne(Long id);

    /**
     *  Delete the "id" ville.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    VilleDTO findByNom(String lieuNaissanceEnfant);

    VilleDTO findByNomAndPaysId(String lieuNaissanceEnfant, PaysDTO paysDTO, String adressCompl);
}
