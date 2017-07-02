package com.consulat.sn.etatcivil.service.impl;

import com.consulat.sn.etatcivil.service.VilleService;
import com.consulat.sn.etatcivil.domain.Ville;
import com.consulat.sn.etatcivil.repository.VilleRepository;
import com.consulat.sn.etatcivil.service.dto.VilleDTO;
import com.consulat.sn.etatcivil.service.mapper.VilleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Ville.
 */
@Service
@Transactional
public class VilleServiceImpl implements VilleService{

    private final Logger log = LoggerFactory.getLogger(VilleServiceImpl.class);

    private final VilleRepository villeRepository;

    private final VilleMapper villeMapper;

    public VilleServiceImpl(VilleRepository villeRepository, VilleMapper villeMapper) {
        this.villeRepository = villeRepository;
        this.villeMapper = villeMapper;
    }

    /**
     * Save a ville.
     *
     * @param villeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VilleDTO save(VilleDTO villeDTO) {
        log.debug("Request to save Ville : {}", villeDTO);
        Ville ville = villeMapper.toEntity(villeDTO);
        ville = villeRepository.save(ville);
        return villeMapper.toDto(ville);
    }

    /**
     *  Get all the villes.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<VilleDTO> findAll() {
        log.debug("Request to get all Villes");
        return villeRepository.findAll().stream()
            .map(villeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one ville by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public VilleDTO findOne(Long id) {
        log.debug("Request to get Ville : {}", id);
        Ville ville = villeRepository.findOne(id);
        return villeMapper.toDto(ville);
    }

    /**
     *  Delete the  ville by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ville : {}", id);
        villeRepository.delete(id);
    }
}
