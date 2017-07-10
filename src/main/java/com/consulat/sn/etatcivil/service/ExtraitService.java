package com.consulat.sn.etatcivil.service;

import com.consulat.sn.etatcivil.domain.Extrait;
import com.consulat.sn.etatcivil.service.dto.DeclarationExtraitDTO;
import com.consulat.sn.etatcivil.service.dto.DeclarationExtraitRechercheDTO;
import com.consulat.sn.etatcivil.service.dto.ExtraitDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.StreamSupport;

/**
 * Service Interface for managing Extrait.
 */
public interface ExtraitService {

    /**
     * Save a extrait.
     *
     * @param extraitDTO the entity to save
     * @return the persisted entity
     */
    ExtraitDTO save(ExtraitDTO extraitDTO);

    /**
     *  Get all the extraits.
     *
     *  @return the list of entities
     */
    List<ExtraitDTO> findAll();

    /**
     *  Get the "id" extrait.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ExtraitDTO findOne(Long id);

    /**
     *  Delete the "id" extrait.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    List<DeclarationExtraitRechercheDTO> findExtraitByCriteria(DeclarationExtraitDTO declarationExtraitDTO);
}
