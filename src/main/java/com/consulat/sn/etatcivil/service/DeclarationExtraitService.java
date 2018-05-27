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

    /**
     * Get the "id" declarationExtrait.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DeclarationExtraitRechercheDTO findOne(Long id);

    /**
     * permet de mettre à jour l'extrait
     *
     * @param declarationExtraitDTO le dto
     * @return l'extrait modifié
     */
    DeclarationExtraitRechercheDTO update(DeclarationExtraitRechercheDTO declarationExtraitDTO);

    /**
     * permet d'imprimer un extrait de naissance
     *
     * @param idExtrait id de l'extrait
     * @return le nom de l'extrait à imprimer
     */
    String printExtraitNaissance(Long idExtrait);

    /**
     * permet d'imprimer la transcription de naissance
     *
     * @param idExtrait id de l'extrait en question
     * @return le nom de la transcription
     */
    String printTranscriptionNaissance(Long idExtrait);
}
