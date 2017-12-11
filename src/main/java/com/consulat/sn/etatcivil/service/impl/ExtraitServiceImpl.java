package com.consulat.sn.etatcivil.service.impl;

import com.consulat.sn.etatcivil.domain.Extrait;
import com.consulat.sn.etatcivil.repository.ExtraitRepository;
import com.consulat.sn.etatcivil.service.ExtraitService;
import com.consulat.sn.etatcivil.service.dto.DeclarationExtraitDTO;
import com.consulat.sn.etatcivil.service.dto.DeclarationExtraitRechercheDTO;
import com.consulat.sn.etatcivil.service.dto.ExtraitDTO;
import com.consulat.sn.etatcivil.service.mapper.DeclarationExtraitMapper;
import com.consulat.sn.etatcivil.service.mapper.ExtraitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Extrait.
 */
@Service
@Transactional
public class ExtraitServiceImpl implements ExtraitService {

    private final Logger log = LoggerFactory.getLogger(ExtraitServiceImpl.class);

    private final ExtraitRepository extraitRepository;

    private final ExtraitMapper extraitMapper;

    private final DeclarationExtraitMapper declarationExtraitMapper;

    @PersistenceContext
    public EntityManager entityManager;

    public ExtraitServiceImpl(ExtraitRepository extraitRepository, ExtraitMapper extraitMapper, DeclarationExtraitMapper declarationExtraitMapper) {
        this.extraitRepository = extraitRepository;
        this.extraitMapper = extraitMapper;
        this.declarationExtraitMapper = declarationExtraitMapper;
    }

    /**
     * Save a extrait.
     *
     * @param extraitDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ExtraitDTO save(ExtraitDTO extraitDTO) {
        log.debug("Request to save Extrait : {}", extraitDTO);
        Extrait extrait = extraitMapper.toEntity(extraitDTO);
        extrait = extraitRepository.save(extrait);
        return extraitMapper.toDto(extrait);
    }

    /**
     * Get all the extraits.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ExtraitDTO> findAll() {
        log.debug("Request to get all Extraits");
        return extraitRepository.findAll().stream()
            .map(extraitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one extrait by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ExtraitDTO findOne(Long id) {
        log.debug("Request to get Extrait : {}", id);
        Extrait extrait = extraitRepository.findOne(id);
        return extraitMapper.toDto(extrait);
    }

    /**
     * Delete the  extrait by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Extrait : {}", id);
        extraitRepository.delete(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DeclarationExtraitRechercheDTO> findExtraitByCriteria(DeclarationExtraitDTO declarationExtraitDTO) {

        String nom = "%" + declarationExtraitDTO.getNomEnfant() + "%";
        String prenom = "%" + declarationExtraitDTO.getPrenomEnfant() + "%";
        String numeroRegistre = declarationExtraitDTO.getNumeroRegistre();
        LocalDate dateNaissanceEnfant = declarationExtraitDTO.getDateNaissanceEnfant();

        if (null != declarationExtraitDTO.getNumeroRegistre()) {
            return extraitRepository.findByNumeroRegistre(declarationExtraitDTO.getNumeroRegistre()).stream()
                .map(declarationExtraitMapper::toEntity)
                .collect(Collectors.toCollection(LinkedList::new));
        } else {
            return extraitRepository.findExtraitByCriteria(numeroRegistre, nom,
                prenom, declarationExtraitDTO.getDateNaissanceEnfant()).stream()
                .map(declarationExtraitMapper::toEntity)
                .collect(Collectors.toCollection(LinkedList::new));
        }

    }


    @Override
    @Transactional
    public Boolean findExistantExtrait(Long enfant, Long mere, Long pere) {
        Boolean result = false;

        Query query = entityManager.createNamedQuery("Extrait.isDeclarationExist");
        query.setParameter("idEnfant", enfant);
        query.setParameter("idMere", mere);
        query.setParameter("idPere", pere);
        Extrait extrait = (Extrait) query.getSingleResult();
        if (null != extrait.getId()) {
            result = true;
        }
        return result;
    }

    @Override
    public DeclarationExtraitRechercheDTO findExtraitById(Long id) {
        Extrait extrait = extraitRepository.findOne(id);
        return declarationExtraitMapper.toEntity(extrait);
    }

}
