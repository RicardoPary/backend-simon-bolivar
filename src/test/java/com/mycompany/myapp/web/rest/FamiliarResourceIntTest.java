package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimonBolivarApp;

import com.mycompany.myapp.domain.Familiar;
import com.mycompany.myapp.repository.FamiliarRepository;
import com.mycompany.myapp.service.FamiliarService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FamiliarResource REST controller.
 *
 * @see FamiliarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimonBolivarApp.class)
public class FamiliarResourceIntTest {

    private static final String DEFAULT_PARENTESCO = "AAAAAAAAAA";
    private static final String UPDATED_PARENTESCO = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_PERSONA = 1L;
    private static final Long UPDATED_ID_PERSONA = 2L;

    @Autowired
    private FamiliarRepository familiarRepository;

    @Autowired
    private FamiliarService familiarService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFamiliarMockMvc;

    private Familiar familiar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FamiliarResource familiarResource = new FamiliarResource(familiarService);
        this.restFamiliarMockMvc = MockMvcBuilders.standaloneSetup(familiarResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Familiar createEntity(EntityManager em) {
        Familiar familiar = new Familiar()
            .parentesco(DEFAULT_PARENTESCO)
            .idPersona(DEFAULT_ID_PERSONA);
        return familiar;
    }

    @Before
    public void initTest() {
        familiar = createEntity(em);
    }

    @Test
    @Transactional
    public void createFamiliar() throws Exception {
        int databaseSizeBeforeCreate = familiarRepository.findAll().size();

        // Create the Familiar
        restFamiliarMockMvc.perform(post("/api/familiars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familiar)))
            .andExpect(status().isCreated());

        // Validate the Familiar in the database
        List<Familiar> familiarList = familiarRepository.findAll();
        assertThat(familiarList).hasSize(databaseSizeBeforeCreate + 1);
        Familiar testFamiliar = familiarList.get(familiarList.size() - 1);
        assertThat(testFamiliar.getParentesco()).isEqualTo(DEFAULT_PARENTESCO);
        assertThat(testFamiliar.getIdPersona()).isEqualTo(DEFAULT_ID_PERSONA);
    }

    @Test
    @Transactional
    public void createFamiliarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = familiarRepository.findAll().size();

        // Create the Familiar with an existing ID
        familiar.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFamiliarMockMvc.perform(post("/api/familiars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familiar)))
            .andExpect(status().isBadRequest());

        // Validate the Familiar in the database
        List<Familiar> familiarList = familiarRepository.findAll();
        assertThat(familiarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFamiliars() throws Exception {
        // Initialize the database
        familiarRepository.saveAndFlush(familiar);

        // Get all the familiarList
        restFamiliarMockMvc.perform(get("/api/familiars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familiar.getId().intValue())))
            .andExpect(jsonPath("$.[*].parentesco").value(hasItem(DEFAULT_PARENTESCO.toString())))
            .andExpect(jsonPath("$.[*].idPersona").value(hasItem(DEFAULT_ID_PERSONA.intValue())));
    }

    @Test
    @Transactional
    public void getFamiliar() throws Exception {
        // Initialize the database
        familiarRepository.saveAndFlush(familiar);

        // Get the familiar
        restFamiliarMockMvc.perform(get("/api/familiars/{id}", familiar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(familiar.getId().intValue()))
            .andExpect(jsonPath("$.parentesco").value(DEFAULT_PARENTESCO.toString()))
            .andExpect(jsonPath("$.idPersona").value(DEFAULT_ID_PERSONA.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFamiliar() throws Exception {
        // Get the familiar
        restFamiliarMockMvc.perform(get("/api/familiars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFamiliar() throws Exception {
        // Initialize the database
        familiarService.save(familiar);

        int databaseSizeBeforeUpdate = familiarRepository.findAll().size();

        // Update the familiar
        Familiar updatedFamiliar = familiarRepository.findOne(familiar.getId());
        // Disconnect from session so that the updates on updatedFamiliar are not directly saved in db
        em.detach(updatedFamiliar);
        updatedFamiliar
            .parentesco(UPDATED_PARENTESCO)
            .idPersona(UPDATED_ID_PERSONA);

        restFamiliarMockMvc.perform(put("/api/familiars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFamiliar)))
            .andExpect(status().isOk());

        // Validate the Familiar in the database
        List<Familiar> familiarList = familiarRepository.findAll();
        assertThat(familiarList).hasSize(databaseSizeBeforeUpdate);
        Familiar testFamiliar = familiarList.get(familiarList.size() - 1);
        assertThat(testFamiliar.getParentesco()).isEqualTo(UPDATED_PARENTESCO);
        assertThat(testFamiliar.getIdPersona()).isEqualTo(UPDATED_ID_PERSONA);
    }

    @Test
    @Transactional
    public void updateNonExistingFamiliar() throws Exception {
        int databaseSizeBeforeUpdate = familiarRepository.findAll().size();

        // Create the Familiar

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFamiliarMockMvc.perform(put("/api/familiars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familiar)))
            .andExpect(status().isCreated());

        // Validate the Familiar in the database
        List<Familiar> familiarList = familiarRepository.findAll();
        assertThat(familiarList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFamiliar() throws Exception {
        // Initialize the database
        familiarService.save(familiar);

        int databaseSizeBeforeDelete = familiarRepository.findAll().size();

        // Get the familiar
        restFamiliarMockMvc.perform(delete("/api/familiars/{id}", familiar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Familiar> familiarList = familiarRepository.findAll();
        assertThat(familiarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Familiar.class);
        Familiar familiar1 = new Familiar();
        familiar1.setId(1L);
        Familiar familiar2 = new Familiar();
        familiar2.setId(familiar1.getId());
        assertThat(familiar1).isEqualTo(familiar2);
        familiar2.setId(2L);
        assertThat(familiar1).isNotEqualTo(familiar2);
        familiar1.setId(null);
        assertThat(familiar1).isNotEqualTo(familiar2);
    }
}
