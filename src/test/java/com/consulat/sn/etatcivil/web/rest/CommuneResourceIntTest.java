package com.consulat.sn.etatcivil.web.rest;

import com.consulat.sn.etatcivil.EtatCivilApp;

import com.consulat.sn.etatcivil.domain.Commune;
import com.consulat.sn.etatcivil.domain.Ville;
import com.consulat.sn.etatcivil.repository.CommuneRepository;
import com.consulat.sn.etatcivil.service.CommuneService;
import com.consulat.sn.etatcivil.service.dto.CommuneDTO;
import com.consulat.sn.etatcivil.service.mapper.CommuneMapper;
import com.consulat.sn.etatcivil.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CommuneResource REST controller.
 *
 * @see CommuneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EtatCivilApp.class)
public class CommuneResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private CommuneRepository communeRepository;

    @Autowired
    private CommuneMapper communeMapper;

    @Autowired
    private CommuneService communeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommuneMockMvc;

    private Commune commune;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CommuneResource communeResource = new CommuneResource(communeService);
        this.restCommuneMockMvc = MockMvcBuilders.standaloneSetup(communeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commune createEntity(EntityManager em) {
        Commune commune = new Commune()
            .nom(DEFAULT_NOM);
        // Add required entity
        Ville ville = VilleResourceIntTest.createEntity(em);
        em.persist(ville);
        em.flush();
        commune.setVille(ville);
        return commune;
    }

    @Before
    public void initTest() {
        commune = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommune() throws Exception {
        int databaseSizeBeforeCreate = communeRepository.findAll().size();

        // Create the Commune
        CommuneDTO communeDTO = communeMapper.toDto(commune);
        restCommuneMockMvc.perform(post("/api/communes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communeDTO)))
            .andExpect(status().isCreated());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeCreate + 1);
        Commune testCommune = communeList.get(communeList.size() - 1);
        assertThat(testCommune.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    public void createCommuneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = communeRepository.findAll().size();

        // Create the Commune with an existing ID
        commune.setId(1L);
        CommuneDTO communeDTO = communeMapper.toDto(commune);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommuneMockMvc.perform(post("/api/communes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = communeRepository.findAll().size();
        // set the field null
        commune.setNom(null);

        // Create the Commune, which fails.
        CommuneDTO communeDTO = communeMapper.toDto(commune);

        restCommuneMockMvc.perform(post("/api/communes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communeDTO)))
            .andExpect(status().isBadRequest());

        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommunes() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        // Get all the communeList
        restCommuneMockMvc.perform(get("/api/communes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commune.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())));
    }

    @Test
    @Transactional
    public void getCommune() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        // Get the commune
        restCommuneMockMvc.perform(get("/api/communes/{id}", commune.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commune.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommune() throws Exception {
        // Get the commune
        restCommuneMockMvc.perform(get("/api/communes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommune() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);
        int databaseSizeBeforeUpdate = communeRepository.findAll().size();

        // Update the commune
        Commune updatedCommune = communeRepository.findOne(commune.getId());
        updatedCommune
            .nom(UPDATED_NOM);
        CommuneDTO communeDTO = communeMapper.toDto(updatedCommune);

        restCommuneMockMvc.perform(put("/api/communes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communeDTO)))
            .andExpect(status().isOk());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeUpdate);
        Commune testCommune = communeList.get(communeList.size() - 1);
        assertThat(testCommune.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    public void updateNonExistingCommune() throws Exception {
        int databaseSizeBeforeUpdate = communeRepository.findAll().size();

        // Create the Commune
        CommuneDTO communeDTO = communeMapper.toDto(commune);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommuneMockMvc.perform(put("/api/communes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communeDTO)))
            .andExpect(status().isCreated());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCommune() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);
        int databaseSizeBeforeDelete = communeRepository.findAll().size();

        // Get the commune
        restCommuneMockMvc.perform(delete("/api/communes/{id}", commune.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Commune.class);
        Commune commune1 = new Commune();
        commune1.setId(1L);
        Commune commune2 = new Commune();
        commune2.setId(commune1.getId());
        assertThat(commune1).isEqualTo(commune2);
        commune2.setId(2L);
        assertThat(commune1).isNotEqualTo(commune2);
        commune1.setId(null);
        assertThat(commune1).isNotEqualTo(commune2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommuneDTO.class);
        CommuneDTO communeDTO1 = new CommuneDTO();
        communeDTO1.setId(1L);
        CommuneDTO communeDTO2 = new CommuneDTO();
        assertThat(communeDTO1).isNotEqualTo(communeDTO2);
        communeDTO2.setId(communeDTO1.getId());
        assertThat(communeDTO1).isEqualTo(communeDTO2);
        communeDTO2.setId(2L);
        assertThat(communeDTO1).isNotEqualTo(communeDTO2);
        communeDTO1.setId(null);
        assertThat(communeDTO1).isNotEqualTo(communeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(communeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(communeMapper.fromId(null)).isNull();
    }
}
