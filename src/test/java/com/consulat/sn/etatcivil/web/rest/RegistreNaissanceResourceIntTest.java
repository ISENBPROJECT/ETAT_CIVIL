package com.consulat.sn.etatcivil.web.rest;

import com.consulat.sn.etatcivil.EtatCivilApp;

import com.consulat.sn.etatcivil.domain.RegistreNaissance;
import com.consulat.sn.etatcivil.domain.Extrait;
import com.consulat.sn.etatcivil.repository.RegistreNaissanceRepository;
import com.consulat.sn.etatcivil.service.RegistreNaissanceService;
import com.consulat.sn.etatcivil.service.dto.RegistreNaissanceDTO;
import com.consulat.sn.etatcivil.service.mapper.RegistreNaissanceMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RegistreNaissanceResource REST controller.
 *
 * @see RegistreNaissanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EtatCivilApp.class)
public class RegistreNaissanceResourceIntTest {

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final LocalDate DEFAULT_ANNEE_REGISTRE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANNEE_REGISTRE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private RegistreNaissanceRepository registreNaissanceRepository;

    @Autowired
    private RegistreNaissanceMapper registreNaissanceMapper;

    @Autowired
    private RegistreNaissanceService registreNaissanceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRegistreNaissanceMockMvc;

    private RegistreNaissance registreNaissance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RegistreNaissanceResource registreNaissanceResource = new RegistreNaissanceResource(registreNaissanceService);
        this.restRegistreNaissanceMockMvc = MockMvcBuilders.standaloneSetup(registreNaissanceResource)
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
    public static RegistreNaissance createEntity(EntityManager em) {
        RegistreNaissance registreNaissance = new RegistreNaissance()
            .numero(DEFAULT_NUMERO)
            .anneeRegistre(DEFAULT_ANNEE_REGISTRE);
        // Add required entity
        Extrait extrait = ExtraitResourceIntTest.createEntity(em);
        em.persist(extrait);
        em.flush();
        registreNaissance.setExtrait(extrait);
        return registreNaissance;
    }

    @Before
    public void initTest() {
        registreNaissance = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegistreNaissance() throws Exception {
        int databaseSizeBeforeCreate = registreNaissanceRepository.findAll().size();

        // Create the RegistreNaissance
        RegistreNaissanceDTO registreNaissanceDTO = registreNaissanceMapper.toDto(registreNaissance);
        restRegistreNaissanceMockMvc.perform(post("/api/registre-naissances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registreNaissanceDTO)))
            .andExpect(status().isCreated());

        // Validate the RegistreNaissance in the database
        List<RegistreNaissance> registreNaissanceList = registreNaissanceRepository.findAll();
        assertThat(registreNaissanceList).hasSize(databaseSizeBeforeCreate + 1);
        RegistreNaissance testRegistreNaissance = registreNaissanceList.get(registreNaissanceList.size() - 1);
        assertThat(testRegistreNaissance.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testRegistreNaissance.getAnneeRegistre()).isEqualTo(DEFAULT_ANNEE_REGISTRE);
    }

    @Test
    @Transactional
    public void createRegistreNaissanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = registreNaissanceRepository.findAll().size();

        // Create the RegistreNaissance with an existing ID
        registreNaissance.setId(1L);
        RegistreNaissanceDTO registreNaissanceDTO = registreNaissanceMapper.toDto(registreNaissance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegistreNaissanceMockMvc.perform(post("/api/registre-naissances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registreNaissanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RegistreNaissance> registreNaissanceList = registreNaissanceRepository.findAll();
        assertThat(registreNaissanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = registreNaissanceRepository.findAll().size();
        // set the field null
        registreNaissance.setNumero(null);

        // Create the RegistreNaissance, which fails.
        RegistreNaissanceDTO registreNaissanceDTO = registreNaissanceMapper.toDto(registreNaissance);

        restRegistreNaissanceMockMvc.perform(post("/api/registre-naissances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registreNaissanceDTO)))
            .andExpect(status().isBadRequest());

        List<RegistreNaissance> registreNaissanceList = registreNaissanceRepository.findAll();
        assertThat(registreNaissanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnneeRegistreIsRequired() throws Exception {
        int databaseSizeBeforeTest = registreNaissanceRepository.findAll().size();
        // set the field null
        registreNaissance.setAnneeRegistre(null);

        // Create the RegistreNaissance, which fails.
        RegistreNaissanceDTO registreNaissanceDTO = registreNaissanceMapper.toDto(registreNaissance);

        restRegistreNaissanceMockMvc.perform(post("/api/registre-naissances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registreNaissanceDTO)))
            .andExpect(status().isBadRequest());

        List<RegistreNaissance> registreNaissanceList = registreNaissanceRepository.findAll();
        assertThat(registreNaissanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegistreNaissances() throws Exception {
        // Initialize the database
        registreNaissanceRepository.saveAndFlush(registreNaissance);

        // Get all the registreNaissanceList
        restRegistreNaissanceMockMvc.perform(get("/api/registre-naissances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registreNaissance.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].anneeRegistre").value(hasItem(DEFAULT_ANNEE_REGISTRE.toString())));
    }

    @Test
    @Transactional
    public void getRegistreNaissance() throws Exception {
        // Initialize the database
        registreNaissanceRepository.saveAndFlush(registreNaissance);

        // Get the registreNaissance
        restRegistreNaissanceMockMvc.perform(get("/api/registre-naissances/{id}", registreNaissance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(registreNaissance.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.anneeRegistre").value(DEFAULT_ANNEE_REGISTRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRegistreNaissance() throws Exception {
        // Get the registreNaissance
        restRegistreNaissanceMockMvc.perform(get("/api/registre-naissances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegistreNaissance() throws Exception {
        // Initialize the database
        registreNaissanceRepository.saveAndFlush(registreNaissance);
        int databaseSizeBeforeUpdate = registreNaissanceRepository.findAll().size();

        // Update the registreNaissance
        RegistreNaissance updatedRegistreNaissance = registreNaissanceRepository.findOne(registreNaissance.getId());
        updatedRegistreNaissance
            .numero(UPDATED_NUMERO)
            .anneeRegistre(UPDATED_ANNEE_REGISTRE);
        RegistreNaissanceDTO registreNaissanceDTO = registreNaissanceMapper.toDto(updatedRegistreNaissance);

        restRegistreNaissanceMockMvc.perform(put("/api/registre-naissances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registreNaissanceDTO)))
            .andExpect(status().isOk());

        // Validate the RegistreNaissance in the database
        List<RegistreNaissance> registreNaissanceList = registreNaissanceRepository.findAll();
        assertThat(registreNaissanceList).hasSize(databaseSizeBeforeUpdate);
        RegistreNaissance testRegistreNaissance = registreNaissanceList.get(registreNaissanceList.size() - 1);
        assertThat(testRegistreNaissance.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testRegistreNaissance.getAnneeRegistre()).isEqualTo(UPDATED_ANNEE_REGISTRE);
    }

    @Test
    @Transactional
    public void updateNonExistingRegistreNaissance() throws Exception {
        int databaseSizeBeforeUpdate = registreNaissanceRepository.findAll().size();

        // Create the RegistreNaissance
        RegistreNaissanceDTO registreNaissanceDTO = registreNaissanceMapper.toDto(registreNaissance);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRegistreNaissanceMockMvc.perform(put("/api/registre-naissances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registreNaissanceDTO)))
            .andExpect(status().isCreated());

        // Validate the RegistreNaissance in the database
        List<RegistreNaissance> registreNaissanceList = registreNaissanceRepository.findAll();
        assertThat(registreNaissanceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRegistreNaissance() throws Exception {
        // Initialize the database
        registreNaissanceRepository.saveAndFlush(registreNaissance);
        int databaseSizeBeforeDelete = registreNaissanceRepository.findAll().size();

        // Get the registreNaissance
        restRegistreNaissanceMockMvc.perform(delete("/api/registre-naissances/{id}", registreNaissance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RegistreNaissance> registreNaissanceList = registreNaissanceRepository.findAll();
        assertThat(registreNaissanceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegistreNaissance.class);
        RegistreNaissance registreNaissance1 = new RegistreNaissance();
        registreNaissance1.setId(1L);
        RegistreNaissance registreNaissance2 = new RegistreNaissance();
        registreNaissance2.setId(registreNaissance1.getId());
        assertThat(registreNaissance1).isEqualTo(registreNaissance2);
        registreNaissance2.setId(2L);
        assertThat(registreNaissance1).isNotEqualTo(registreNaissance2);
        registreNaissance1.setId(null);
        assertThat(registreNaissance1).isNotEqualTo(registreNaissance2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegistreNaissanceDTO.class);
        RegistreNaissanceDTO registreNaissanceDTO1 = new RegistreNaissanceDTO();
        registreNaissanceDTO1.setId(1L);
        RegistreNaissanceDTO registreNaissanceDTO2 = new RegistreNaissanceDTO();
        assertThat(registreNaissanceDTO1).isNotEqualTo(registreNaissanceDTO2);
        registreNaissanceDTO2.setId(registreNaissanceDTO1.getId());
        assertThat(registreNaissanceDTO1).isEqualTo(registreNaissanceDTO2);
        registreNaissanceDTO2.setId(2L);
        assertThat(registreNaissanceDTO1).isNotEqualTo(registreNaissanceDTO2);
        registreNaissanceDTO1.setId(null);
        assertThat(registreNaissanceDTO1).isNotEqualTo(registreNaissanceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(registreNaissanceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(registreNaissanceMapper.fromId(null)).isNull();
    }
}
