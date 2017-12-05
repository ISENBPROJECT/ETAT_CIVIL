package com.consulat.sn.etatcivil.service.impl;

import com.consulat.sn.etatcivil.domain.Pays;
import com.consulat.sn.etatcivil.repository.PaysRepository;
import com.consulat.sn.etatcivil.service.PaysService;
import com.consulat.sn.etatcivil.service.dto.PaysDTO;
import com.consulat.sn.etatcivil.service.mapper.PaysMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Pays.
 */
@Service
@Transactional
public class PaysServiceImpl implements PaysService {

    private final Logger log = LoggerFactory.getLogger(PaysServiceImpl.class);

    private final PaysRepository paysRepository;

    private final PaysMapper paysMapper;

    public PaysServiceImpl(PaysRepository paysRepository, PaysMapper paysMapper) {
        this.paysRepository = paysRepository;
        this.paysMapper = paysMapper;
    }

    /**
     * Save a pays.
     *
     * @param paysDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PaysDTO save(PaysDTO paysDTO) {
        log.debug("Request to save Pays : {}", paysDTO);
        Pays pays = paysMapper.toEntity(paysDTO);
        pays = paysRepository.save(pays);
        return paysMapper.toDto(pays);
    }

    /**
     * Get all the pays.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PaysDTO> findAll() {
        log.debug("Request to get all Pays");
        return paysRepository.findAll().stream()
            .map(paysMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one pays by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PaysDTO findOne(Long id) {
        log.debug("Request to get Pays : {}", id);
        Pays pays = paysRepository.findOne(id);
        return paysMapper.toDto(pays);
    }

    /**
     * Delete the  pays by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pays : {}", id);
        paysRepository.delete(id);
    }

    @Override
    public PaysDTO findByNom(String paysNaissanceEnfant) {
        log.debug("Request to get Pays : {}", paysNaissanceEnfant);
        Pays pays = paysRepository.findByNom(paysNaissanceEnfant);

        if (null == pays) {
            Pays paysEntity = new Pays();
            paysEntity.setNom(paysNaissanceEnfant);
            pays = paysRepository.save(paysEntity);
        }
        return paysMapper.toDto(pays);
    }
}
