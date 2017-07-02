package com.consulat.sn.etatcivil.web.rest;

import com.consulat.sn.etatcivil.EtatCivilApp;

import com.consulat.sn.etatcivil.domain.Extrait;
import com.consulat.sn.etatcivil.domain.Ville;
import com.consulat.sn.etatcivil.domain.Personne;
import com.consulat.sn.etatcivil.domain.Personne;
import com.consulat.sn.etatcivil.domain.Personne;
import com.consulat.sn.etatcivil.domain.User;
import com.consulat.sn.etatcivil.repository.ExtraitRepository;
import com.consulat.sn.etatcivil.service.ExtraitService;
import com.consulat.sn.etatcivil.service.dto.ExtraitDTO;
import com.consulat.sn.etatcivil.service.mapper.ExtraitMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExtraitResource REST controller.
 *
 * @see ExtraitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EtatCivilApp.class)
public class ExtraitResourceIntTest {

    private static final String DEFAULT_NUMERO_REGISTRE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_REGISTRE = "BBBBBBBBBB";

    private static final String DEFAULT_MENTION = "AAAAAAAAAA";
    private static final String UPDATED_MENTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DECLARATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DECLARATION = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_VALIDATED = false;
    private static final Boolean UPDATED_VALIDATED = true;

    @Autowired
    private ExtraitRepository extraitRepository;

    @Autowired
    private ExtraitMapper extraitMapper;

    @Autowired
    private ExtraitService extraitService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExtraitMockMvc;

    private Extrait extrait;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExtraitResource extraitResource = new ExtraitResource(extraitService);
        this.restExtraitMockMvc = MockMvcBuilders.standaloneSetup(extraitResource)
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
    public static Extrait createEntity(EntityManager em) {
        Extrait extrait = new Extrait()
            .numeroRegistre(DEFAULT_NUMERO_REGISTRE)
            .mention(DEFAULT_MENTION)
            .dateDeclaration(DEFAULT_DATE_DECLARATION)
            .validated(DEFAULT_VALIDATED);
        // Add required entity
        Ville lieuDeclaration = VilleResourceIntTest.createEntity(em);
        em.persist(lieuDeclaration);
        em.flush();
        extrait.setLieuDeclaration(lieuDeclaration);
        // Add required entity
        Personne enfant = PersonneResourceIntTest.createEntity(em);
        em.persist(enfant);
        em.flush();
        extrait.setEnfant(enfant);
        // Add required entity
        Personne mere = PersonneResourceIntTest.createEntity(em);
        em.persist(mere);
        em.flush();
        extrait.setMere(mere);
        // Add required entity
        Personne pere = PersonneResourceIntTest.createEntity(em);
        em.persist(pere);
        em.flush();
        extrait.setPere(pere);
        // Add required entity
        User agent = UserResourceIntTest.createEntity(em);
        em.persist(agent);
        em.flush();
        extrait.setAgent(agent);
        return extrait;
    }

    @Before
    public void initTest() {
        extrait = createEntity(em);
    }

    @Test
    @Transactional
    public void createExtrait() throws Exception {
        int databaseSizeBeforeCreate = extraitRepository.findAll().size();

        // Create the Extrait
        ExtraitDTO extraitDTO = extraitMapper.toDto(extrait);
        restExtraitMockMvc.perform(post("/api/extraits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extraitDTO)))
            .andExpect(status().isCreated());

        // Validate the Extrait in the database
        List<Extrait> extraitList = extraitRepository.findAll();
        assertThat(extraitList).hasSize(databaseSizeBeforeCreate + 1);
        Extrait testExtrait = extraitList.get(extraitList.size() - 1);
        assertThat(testExtrait.getNumeroRegistre()).isEqualTo(DEFAULT_NUMERO_REGISTRE);
        assertThat(testExtrait.getMention()).isEqualTo(DEFAULT_MENTION);
        assertThat(testExtrait.getDateDeclaration()).isEqualTo(DEFAULT_DATE_DECLARATION);
        assertThat(testExtrait.isValidated()).isEqualTo(DEFAULT_VALIDATED);
    }

    @Test
    @Transactional
    public void createExtraitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = extraitRepository.findAll().size();

        // Create the Extrait with an existing ID
        extrait.setId(1L);
        ExtraitDTO extraitDTO = extraitMapper.toDto(extrait);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExtraitMockMvc.perform(post("/api/extraits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extraitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Extrait> extraitList = extraitRepository.findAll();
        assertThat(extraitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroRegistreIsRequired() throws Exception {
        int databaseSizeBeforeTest = extraitRepository.findAll().size();
        // set the field null
        extrait.setNumeroRegistre(null);

        // Create the Extrait, which fails.
        ExtraitDTO extraitDTO = extraitMapper.toDto(extrait);

        restExtraitMockMvc.perform(post("/api/extraits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extraitDTO)))
            .andExpect(status().isBadRequest());

        List<Extrait> extraitList = extraitRepository.findAll();
        assertThat(extraitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMentionIsRequired() throws Exception {
        int databaseSizeBeforeTest = extraitRepository.findAll().size();
        // set the field null
        extrait.setMention(null);

        // Create the Extrait, which fails.
        ExtraitDTO extraitDTO = extraitMapper.toDto(extrait);

        restExtraitMockMvc.perform(post("/api/extraits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extraitDTO)))
            .andExpect(status().isBadRequest());

        List<Extrait> extraitList = extraitRepository.findAll();
        assertThat(extraitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateDeclarationIsRequired() throws Exception {
        int databaseSizeBeforeTest = extraitRepository.findAll().size();
        // set the field null
        extrait.setDateDeclaration(null);

        // Create the Extrait, which fails.
        ExtraitDTO extraitDTO = extraitMapper.toDto(extrait);

        restExtraitMockMvc.perform(post("/api/extraits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extraitDTO)))
            .andExpect(status().isBadRequest());

        List<Extrait> extraitList = extraitRepository.findAll();
        assertThat(extraitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExtraits() throws Exception {
        // Initialize the database
        extraitRepository.saveAndFlush(extrait);

        // Get all the extraitList
        restExtraitMockMvc.perform(get("/api/extraits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(extrait.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroRegistre").value(hasItem(DEFAULT_NUMERO_REGISTRE.toString())))
            .andExpect(jsonPath("$.[*].mention").value(hasItem(DEFAULT_MENTION.toString())))
            .andExpect(jsonPath("$.[*].dateDeclaration").value(hasItem(DEFAULT_DATE_DECLARATION.toString())))
            .andExpect(jsonPath("$.[*].validated").value(hasItem(DEFAULT_VALIDATED.booleanValue())));
    }

    @Test
    @Transactional
    public void getExtrait() throws Exception {
        // Initialize the database
        extraitRepository.saveAndFlush(extrait);

        // Get the extrait
        restExtraitMockMvc.perform(get("/api/extraits/{id}", extrait.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(extrait.getId().intValue()))
            .andExpect(jsonPath("$.numeroRegistre").value(DEFAULT_NUMERO_REGISTRE.toString()))
            .andExpect(jsonPath("$.mention").value(DEFAULT_MENTION.toString()))
            .andExpect(jsonPath("$.dateDeclaration").value(DEFAULT_DATE_DECLARATION.toString()))
            .andExpect(jsonPath("$.validated").value(DEFAULT_VALIDATED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingExtrait() throws Exception {
        // Get the extrait
        restExtraitMockMvc.perform(get("/api/extraits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExtrait() throws Exception {
        // Initialize the database
        extraitRepository.saveAndFlush(extrait);
        int databaseSizeBeforeUpdate = extraitRepository.findAll().size();

        // Update the extrait
        Extrait updatedExtrait = extraitRepository.findOne(extrait.getId());
        updatedExtrait
            .numeroRegistre(UPDATED_NUMERO_REGISTRE)
            .mention(UPDATED_MENTION)
            .dateDeclaration(UPDATED_DATE_DECLARATION)
            .validated(UPDATED_VALIDATED);
        ExtraitDTO extraitDTO = extraitMapper.toDto(updatedExtrait);

        restExtraitMockMvc.perform(put("/api/extraits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extraitDTO)))
            .andExpect(status().isOk());

        // Validate the Extrait in the database
        List<Extrait> extraitList = extraitRepository.findAll();
        assertThat(extraitList).hasSize(databaseSizeBeforeUpdate);
        Extrait testExtrait = extraitList.get(extraitList.size() - 1);
        assertThat(testExtrait.getNumeroRegistre()).isEqualTo(UPDATED_NUMERO_REGISTRE);
        assertThat(testExtrait.getMention()).isEqualTo(UPDATED_MENTION);
        assertThat(testExtrait.getDateDeclaration()).isEqualTo(UPDATED_DATE_DECLARATION);
        assertThat(testExtrait.isValidated()).isEqualTo(UPDATED_VALIDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingExtrait() throws Exception {
        int databaseSizeBeforeUpdate = extraitRepository.findAll().size();

        // Create the Extrait
        ExtraitDTO extraitDTO = extraitMapper.toDto(extrait);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExtraitMockMvc.perform(put("/api/extraits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extraitDTO)))
            .andExpect(status().isCreated());

        // Validate the Extrait in the database
        List<Extrait> extraitList = extraitRepository.findAll();
        assertThat(extraitList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExtrait() throws Exception {
        // Initialize the database
        extraitRepository.saveAndFlush(extrait);
        int databaseSizeBeforeDelete = extraitRepository.findAll().size();

        // Get the extrait
        restExtraitMockMvc.perform(delete("/api/extraits/{id}", extrait.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Extrait> extraitList = extraitRepository.findAll();
        assertThat(extraitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Extrait.class);
        Extrait extrait1 = new Extrait();
        extrait1.setId(1L);
        Extrait extrait2 = new Extrait();
        extrait2.setId(extrait1.getId());
        assertThat(extrait1).isEqualTo(extrait2);
        extrait2.setId(2L);
        assertThat(extrait1).isNotEqualTo(extrait2);
        extrait1.setId(null);
        assertThat(extrait1).isNotEqualTo(extrait2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExtraitDTO.class);
        ExtraitDTO extraitDTO1 = new ExtraitDTO();
        extraitDTO1.setId(1L);
        ExtraitDTO extraitDTO2 = new ExtraitDTO();
        assertThat(extraitDTO1).isNotEqualTo(extraitDTO2);
        extraitDTO2.setId(extraitDTO1.getId());
        assertThat(extraitDTO1).isEqualTo(extraitDTO2);
        extraitDTO2.setId(2L);
        assertThat(extraitDTO1).isNotEqualTo(extraitDTO2);
        extraitDTO1.setId(null);
        assertThat(extraitDTO1).isNotEqualTo(extraitDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(extraitMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(extraitMapper.fromId(null)).isNull();
    }
}
