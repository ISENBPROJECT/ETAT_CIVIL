package com.consulat.sn.etatcivil.service;


import com.consulat.sn.etatcivil.service.dto.DeclarationExtraitDTO;
import com.consulat.sn.etatcivil.service.dto.DeclarationExtraitRechercheDTO;

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

    List<DeclarationExtraitRechercheDTO> findExtraitByCriteria(DeclarationExtraitDTO declarationExtraitDTO);

    void supprimerActesImprimer(String acteNaissance, String transcriptionNaissance);
    /**
     * Get the "id" declarationExtrait.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DeclarationExtraitRechercheDTO findOne(Long id);

    DeclarationExtraitRechercheDTO update(DeclarationExtraitRechercheDTO declarationExtraitDTO);
    String printExtraitNaissance(Long idExtrait);
}
