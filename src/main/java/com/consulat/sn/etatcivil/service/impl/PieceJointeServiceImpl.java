package com.consulat.sn.etatcivil.service.impl;

import com.consulat.sn.etatcivil.service.PieceJointeService;
import com.consulat.sn.etatcivil.domain.PieceJointe;
import com.consulat.sn.etatcivil.repository.PieceJointeRepository;
import com.consulat.sn.etatcivil.service.dto.PieceJointeDTO;
import com.consulat.sn.etatcivil.service.mapper.PieceJointeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing PieceJointe.
 */
@Service
@Transactional
public class PieceJointeServiceImpl implements PieceJointeService{

    private final Logger log = LoggerFactory.getLogger(PieceJointeServiceImpl.class);

    private final PieceJointeRepository pieceJointeRepository;

    private final PieceJointeMapper pieceJointeMapper;

    public PieceJointeServiceImpl(PieceJointeRepository pieceJointeRepository, PieceJointeMapper pieceJointeMapper) {
        this.pieceJointeRepository = pieceJointeRepository;
        this.pieceJointeMapper = pieceJointeMapper;
    }

    /**
     * Save a pieceJointe.
     *
     * @param pieceJointeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PieceJointeDTO save(PieceJointeDTO pieceJointeDTO) {
        log.debug("Request to save PieceJointe : {}", pieceJointeDTO);
        PieceJointe pieceJointe = pieceJointeMapper.toEntity(pieceJointeDTO);
        pieceJointe = pieceJointeRepository.save(pieceJointe);
        return pieceJointeMapper.toDto(pieceJointe);
    }

    /**
     *  Get all the pieceJointes.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PieceJointeDTO> findAll() {
        log.debug("Request to get all PieceJointes");
        return pieceJointeRepository.findAll().stream()
            .map(pieceJointeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one pieceJointe by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PieceJointeDTO findOne(Long id) {
        log.debug("Request to get PieceJointe : {}", id);
        PieceJointe pieceJointe = pieceJointeRepository.findOne(id);
        return pieceJointeMapper.toDto(pieceJointe);
    }

    /**
     *  Delete the  pieceJointe by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PieceJointe : {}", id);
        pieceJointeRepository.delete(id);
    }

    @Override
    public PieceJointeDTO update(PieceJointeDTO pieceJointeToUpdate) {
        PieceJointe pieceJointe = pieceJointeMapper.toEntity(pieceJointeToUpdate);
        pieceJointe = pieceJointeRepository.save(pieceJointe);
        return pieceJointeMapper.toDto(pieceJointe);
    }
}
