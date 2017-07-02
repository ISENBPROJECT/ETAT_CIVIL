package com.consulat.sn.etatcivil.service.impl;

import com.consulat.sn.etatcivil.service.RegistreNaissanceService;
import com.consulat.sn.etatcivil.domain.RegistreNaissance;
import com.consulat.sn.etatcivil.repository.RegistreNaissanceRepository;
import com.consulat.sn.etatcivil.service.dto.RegistreNaissanceDTO;
import com.consulat.sn.etatcivil.service.mapper.RegistreNaissanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing RegistreNaissance.
 */
@Service
@Transactional
public class RegistreNaissanceServiceImpl implements RegistreNaissanceService{

    private final Logger log = LoggerFactory.getLogger(RegistreNaissanceServiceImpl.class);

    private final RegistreNaissanceRepository registreNaissanceRepository;

    private final RegistreNaissanceMapper registreNaissanceMapper;

    public RegistreNaissanceServiceImpl(RegistreNaissanceRepository registreNaissanceRepository, RegistreNaissanceMapper registreNaissanceMapper) {
        this.registreNaissanceRepository = registreNaissanceRepository;
        this.registreNaissanceMapper = registreNaissanceMapper;
    }

    /**
     * Save a registreNaissance.
     *
     * @param registreNaissanceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RegistreNaissanceDTO save(RegistreNaissanceDTO registreNaissanceDTO) {
        log.debug("Request to save RegistreNaissance : {}", registreNaissanceDTO);
        RegistreNaissance registreNaissance = registreNaissanceMapper.toEntity(registreNaissanceDTO);
        registreNaissance = registreNaissanceRepository.save(registreNaissance);
        return registreNaissanceMapper.toDto(registreNaissance);
    }

    /**
     *  Get all the registreNaissances.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RegistreNaissanceDTO> findAll() {
        log.debug("Request to get all RegistreNaissances");
        return registreNaissanceRepository.findAll().stream()
            .map(registreNaissanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one registreNaissance by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RegistreNaissanceDTO findOne(Long id) {
        log.debug("Request to get RegistreNaissance : {}", id);
        RegistreNaissance registreNaissance = registreNaissanceRepository.findOne(id);
        return registreNaissanceMapper.toDto(registreNaissance);
    }

    /**
     *  Delete the  registreNaissance by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RegistreNaissance : {}", id);
        registreNaissanceRepository.delete(id);
    }
}
