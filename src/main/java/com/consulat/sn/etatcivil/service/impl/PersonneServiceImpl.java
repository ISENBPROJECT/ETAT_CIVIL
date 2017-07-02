package com.consulat.sn.etatcivil.service.impl;

import com.consulat.sn.etatcivil.service.PersonneService;
import com.consulat.sn.etatcivil.domain.Personne;
import com.consulat.sn.etatcivil.repository.PersonneRepository;
import com.consulat.sn.etatcivil.service.dto.PersonneDTO;
import com.consulat.sn.etatcivil.service.mapper.PersonneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Personne.
 */
@Service
@Transactional
public class PersonneServiceImpl implements PersonneService{

    private final Logger log = LoggerFactory.getLogger(PersonneServiceImpl.class);

    private final PersonneRepository personneRepository;

    private final PersonneMapper personneMapper;

    public PersonneServiceImpl(PersonneRepository personneRepository, PersonneMapper personneMapper) {
        this.personneRepository = personneRepository;
        this.personneMapper = personneMapper;
    }

    /**
     * Save a personne.
     *
     * @param personneDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PersonneDTO save(PersonneDTO personneDTO) {
        log.debug("Request to save Personne : {}", personneDTO);
        Personne personne = personneMapper.toEntity(personneDTO);
        personne = personneRepository.save(personne);
        return personneMapper.toDto(personne);
    }

    /**
     *  Get all the personnes.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PersonneDTO> findAll() {
        log.debug("Request to get all Personnes");
        return personneRepository.findAll().stream()
            .map(personneMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one personne by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PersonneDTO findOne(Long id) {
        log.debug("Request to get Personne : {}", id);
        Personne personne = personneRepository.findOne(id);
        return personneMapper.toDto(personne);
    }

    /**
     *  Delete the  personne by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Personne : {}", id);
        personneRepository.delete(id);
    }

    @Override
    public PersonneDTO isPersonneExist(String nom, String prenom) {
        Personne personne = personneRepository.findByNomAndPrenom(nom, prenom);
        PersonneDTO personneDTO = personneMapper.toDto(personne);
        return personneDTO;
    }
}
