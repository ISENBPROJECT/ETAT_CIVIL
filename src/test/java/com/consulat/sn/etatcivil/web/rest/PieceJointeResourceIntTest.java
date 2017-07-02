package com.consulat.sn.etatcivil.web.rest;

import com.consulat.sn.etatcivil.EtatCivilApp;

import com.consulat.sn.etatcivil.domain.PieceJointe;
import com.consulat.sn.etatcivil.domain.Extrait;
import com.consulat.sn.etatcivil.repository.PieceJointeRepository;
import com.consulat.sn.etatcivil.service.PieceJointeService;
import com.consulat.sn.etatcivil.service.dto.PieceJointeDTO;
import com.consulat.sn.etatcivil.service.mapper.PieceJointeMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PieceJointeResource REST controller.
 *
 * @see PieceJointeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EtatCivilApp.class)
public class PieceJointeResourceIntTest {

    private static final byte[] DEFAULT_COPIE_LITERALE = TestUtil.createByteArray(8192, "0");
    private static final byte[] UPDATED_COPIE_LITERALE = TestUtil.createByteArray(25165824, "1");
    private static final String DEFAULT_COPIE_LITERALE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COPIE_LITERALE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_COPIE_CARTE = TestUtil.createByteArray(8192, "0");
    private static final byte[] UPDATED_COPIE_CARTE = TestUtil.createByteArray(25165824, "1");
    private static final String DEFAULT_COPIE_CARTE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COPIE_CARTE_CONTENT_TYPE = "image/png";

    @Autowired
    private PieceJointeRepository pieceJointeRepository;

    @Autowired
    private PieceJointeMapper pieceJointeMapper;

    @Autowired
    private PieceJointeService pieceJointeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPieceJointeMockMvc;

    private PieceJointe pieceJointe;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PieceJointeResource pieceJointeResource = new PieceJointeResource(pieceJointeService);
        this.restPieceJointeMockMvc = MockMvcBuilders.standaloneSetup(pieceJointeResource)
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
    public static PieceJointe createEntity(EntityManager em) {
        PieceJointe pieceJointe = new PieceJointe()
            .copieLiterale(DEFAULT_COPIE_LITERALE)
            .copieLiteraleContentType(DEFAULT_COPIE_LITERALE_CONTENT_TYPE)
            .copieCarte(DEFAULT_COPIE_CARTE)
            .copieCarteContentType(DEFAULT_COPIE_CARTE_CONTENT_TYPE);
        // Add required entity
        Extrait declaration = ExtraitResourceIntTest.createEntity(em);
        em.persist(declaration);
        em.flush();
        pieceJointe.setDeclaration(declaration);
        return pieceJointe;
    }

    @Before
    public void initTest() {
        pieceJointe = createEntity(em);
    }

    @Test
    @Transactional
    public void createPieceJointe() throws Exception {
        int databaseSizeBeforeCreate = pieceJointeRepository.findAll().size();

        // Create the PieceJointe
        PieceJointeDTO pieceJointeDTO = pieceJointeMapper.toDto(pieceJointe);
        restPieceJointeMockMvc.perform(post("/api/piece-jointes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceJointeDTO)))
            .andExpect(status().isCreated());

        // Validate the PieceJointe in the database
        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeCreate + 1);
        PieceJointe testPieceJointe = pieceJointeList.get(pieceJointeList.size() - 1);
        assertThat(testPieceJointe.getCopieLiterale()).isEqualTo(DEFAULT_COPIE_LITERALE);
        assertThat(testPieceJointe.getCopieLiteraleContentType()).isEqualTo(DEFAULT_COPIE_LITERALE_CONTENT_TYPE);
        assertThat(testPieceJointe.getCopieCarte()).isEqualTo(DEFAULT_COPIE_CARTE);
        assertThat(testPieceJointe.getCopieCarteContentType()).isEqualTo(DEFAULT_COPIE_CARTE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createPieceJointeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pieceJointeRepository.findAll().size();

        // Create the PieceJointe with an existing ID
        pieceJointe.setId(1L);
        PieceJointeDTO pieceJointeDTO = pieceJointeMapper.toDto(pieceJointe);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPieceJointeMockMvc.perform(post("/api/piece-jointes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceJointeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCopieLiteraleIsRequired() throws Exception {
        int databaseSizeBeforeTest = pieceJointeRepository.findAll().size();
        // set the field null
        pieceJointe.setCopieLiterale(null);

        // Create the PieceJointe, which fails.
        PieceJointeDTO pieceJointeDTO = pieceJointeMapper.toDto(pieceJointe);

        restPieceJointeMockMvc.perform(post("/api/piece-jointes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceJointeDTO)))
            .andExpect(status().isBadRequest());

        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCopieCarteIsRequired() throws Exception {
        int databaseSizeBeforeTest = pieceJointeRepository.findAll().size();
        // set the field null
        pieceJointe.setCopieCarte(null);

        // Create the PieceJointe, which fails.
        PieceJointeDTO pieceJointeDTO = pieceJointeMapper.toDto(pieceJointe);

        restPieceJointeMockMvc.perform(post("/api/piece-jointes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceJointeDTO)))
            .andExpect(status().isBadRequest());

        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPieceJointes() throws Exception {
        // Initialize the database
        pieceJointeRepository.saveAndFlush(pieceJointe);

        // Get all the pieceJointeList
        restPieceJointeMockMvc.perform(get("/api/piece-jointes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pieceJointe.getId().intValue())))
            .andExpect(jsonPath("$.[*].copieLiteraleContentType").value(hasItem(DEFAULT_COPIE_LITERALE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].copieLiterale").value(hasItem(Base64Utils.encodeToString(DEFAULT_COPIE_LITERALE))))
            .andExpect(jsonPath("$.[*].copieCarteContentType").value(hasItem(DEFAULT_COPIE_CARTE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].copieCarte").value(hasItem(Base64Utils.encodeToString(DEFAULT_COPIE_CARTE))));
    }

    @Test
    @Transactional
    public void getPieceJointe() throws Exception {
        // Initialize the database
        pieceJointeRepository.saveAndFlush(pieceJointe);

        // Get the pieceJointe
        restPieceJointeMockMvc.perform(get("/api/piece-jointes/{id}", pieceJointe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pieceJointe.getId().intValue()))
            .andExpect(jsonPath("$.copieLiteraleContentType").value(DEFAULT_COPIE_LITERALE_CONTENT_TYPE))
            .andExpect(jsonPath("$.copieLiterale").value(Base64Utils.encodeToString(DEFAULT_COPIE_LITERALE)))
            .andExpect(jsonPath("$.copieCarteContentType").value(DEFAULT_COPIE_CARTE_CONTENT_TYPE))
            .andExpect(jsonPath("$.copieCarte").value(Base64Utils.encodeToString(DEFAULT_COPIE_CARTE)));
    }

    @Test
    @Transactional
    public void getNonExistingPieceJointe() throws Exception {
        // Get the pieceJointe
        restPieceJointeMockMvc.perform(get("/api/piece-jointes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePieceJointe() throws Exception {
        // Initialize the database
        pieceJointeRepository.saveAndFlush(pieceJointe);
        int databaseSizeBeforeUpdate = pieceJointeRepository.findAll().size();

        // Update the pieceJointe
        PieceJointe updatedPieceJointe = pieceJointeRepository.findOne(pieceJointe.getId());
        updatedPieceJointe
            .copieLiterale(UPDATED_COPIE_LITERALE)
            .copieLiteraleContentType(UPDATED_COPIE_LITERALE_CONTENT_TYPE)
            .copieCarte(UPDATED_COPIE_CARTE)
            .copieCarteContentType(UPDATED_COPIE_CARTE_CONTENT_TYPE);
        PieceJointeDTO pieceJointeDTO = pieceJointeMapper.toDto(updatedPieceJointe);

        restPieceJointeMockMvc.perform(put("/api/piece-jointes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceJointeDTO)))
            .andExpect(status().isOk());

        // Validate the PieceJointe in the database
        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeUpdate);
        PieceJointe testPieceJointe = pieceJointeList.get(pieceJointeList.size() - 1);
        assertThat(testPieceJointe.getCopieLiterale()).isEqualTo(UPDATED_COPIE_LITERALE);
        assertThat(testPieceJointe.getCopieLiteraleContentType()).isEqualTo(UPDATED_COPIE_LITERALE_CONTENT_TYPE);
        assertThat(testPieceJointe.getCopieCarte()).isEqualTo(UPDATED_COPIE_CARTE);
        assertThat(testPieceJointe.getCopieCarteContentType()).isEqualTo(UPDATED_COPIE_CARTE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPieceJointe() throws Exception {
        int databaseSizeBeforeUpdate = pieceJointeRepository.findAll().size();

        // Create the PieceJointe
        PieceJointeDTO pieceJointeDTO = pieceJointeMapper.toDto(pieceJointe);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPieceJointeMockMvc.perform(put("/api/piece-jointes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceJointeDTO)))
            .andExpect(status().isCreated());

        // Validate the PieceJointe in the database
        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePieceJointe() throws Exception {
        // Initialize the database
        pieceJointeRepository.saveAndFlush(pieceJointe);
        int databaseSizeBeforeDelete = pieceJointeRepository.findAll().size();

        // Get the pieceJointe
        restPieceJointeMockMvc.perform(delete("/api/piece-jointes/{id}", pieceJointe.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PieceJointe.class);
        PieceJointe pieceJointe1 = new PieceJointe();
        pieceJointe1.setId(1L);
        PieceJointe pieceJointe2 = new PieceJointe();
        pieceJointe2.setId(pieceJointe1.getId());
        assertThat(pieceJointe1).isEqualTo(pieceJointe2);
        pieceJointe2.setId(2L);
        assertThat(pieceJointe1).isNotEqualTo(pieceJointe2);
        pieceJointe1.setId(null);
        assertThat(pieceJointe1).isNotEqualTo(pieceJointe2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PieceJointeDTO.class);
        PieceJointeDTO pieceJointeDTO1 = new PieceJointeDTO();
        pieceJointeDTO1.setId(1L);
        PieceJointeDTO pieceJointeDTO2 = new PieceJointeDTO();
        assertThat(pieceJointeDTO1).isNotEqualTo(pieceJointeDTO2);
        pieceJointeDTO2.setId(pieceJointeDTO1.getId());
        assertThat(pieceJointeDTO1).isEqualTo(pieceJointeDTO2);
        pieceJointeDTO2.setId(2L);
        assertThat(pieceJointeDTO1).isNotEqualTo(pieceJointeDTO2);
        pieceJointeDTO1.setId(null);
        assertThat(pieceJointeDTO1).isNotEqualTo(pieceJointeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pieceJointeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pieceJointeMapper.fromId(null)).isNull();
    }
}
