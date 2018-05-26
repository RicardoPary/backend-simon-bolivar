package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimonBolivarApp;

import com.mycompany.myapp.domain.PersonaReunion;
import com.mycompany.myapp.repository.PersonaReunionRepository;
import com.mycompany.myapp.service.PersonaReunionService;
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
 * Test class for the PersonaReunionResource REST controller.
 *
 * @see PersonaReunionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimonBolivarApp.class)
public class PersonaReunionResourceIntTest {

    private static final Long DEFAULT_ID_PERSONA = 1L;
    private static final Long UPDATED_ID_PERSONA = 2L;

    private static final Long DEFAULT_ID_REUNION = 1L;
    private static final Long UPDATED_ID_REUNION = 2L;

    private static final String DEFAULT_FECHA = "AAAAAAAAAA";
    private static final String UPDATED_FECHA = "BBBBBBBBBB";

    @Autowired
    private PersonaReunionRepository personaReunionRepository;

    @Autowired
    private PersonaReunionService personaReunionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonaReunionMockMvc;

    private PersonaReunion personaReunion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonaReunionResource personaReunionResource = new PersonaReunionResource(personaReunionService);
        this.restPersonaReunionMockMvc = MockMvcBuilders.standaloneSetup(personaReunionResource)
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
    public static PersonaReunion createEntity(EntityManager em) {
        PersonaReunion personaReunion = new PersonaReunion()
            .idPersona(DEFAULT_ID_PERSONA)
            .idReunion(DEFAULT_ID_REUNION)
            .fecha(DEFAULT_FECHA);
        return personaReunion;
    }

    @Before
    public void initTest() {
        personaReunion = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonaReunion() throws Exception {
        int databaseSizeBeforeCreate = personaReunionRepository.findAll().size();

        // Create the PersonaReunion
        restPersonaReunionMockMvc.perform(post("/api/persona-reunions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personaReunion)))
            .andExpect(status().isCreated());

        // Validate the PersonaReunion in the database
        List<PersonaReunion> personaReunionList = personaReunionRepository.findAll();
        assertThat(personaReunionList).hasSize(databaseSizeBeforeCreate + 1);
        PersonaReunion testPersonaReunion = personaReunionList.get(personaReunionList.size() - 1);
        assertThat(testPersonaReunion.getIdPersona()).isEqualTo(DEFAULT_ID_PERSONA);
        assertThat(testPersonaReunion.getIdReunion()).isEqualTo(DEFAULT_ID_REUNION);
        assertThat(testPersonaReunion.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createPersonaReunionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personaReunionRepository.findAll().size();

        // Create the PersonaReunion with an existing ID
        personaReunion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonaReunionMockMvc.perform(post("/api/persona-reunions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personaReunion)))
            .andExpect(status().isBadRequest());

        // Validate the PersonaReunion in the database
        List<PersonaReunion> personaReunionList = personaReunionRepository.findAll();
        assertThat(personaReunionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonaReunions() throws Exception {
        // Initialize the database
        personaReunionRepository.saveAndFlush(personaReunion);

        // Get all the personaReunionList
        restPersonaReunionMockMvc.perform(get("/api/persona-reunions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personaReunion.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPersona").value(hasItem(DEFAULT_ID_PERSONA.intValue())))
            .andExpect(jsonPath("$.[*].idReunion").value(hasItem(DEFAULT_ID_REUNION.intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }

    @Test
    @Transactional
    public void getPersonaReunion() throws Exception {
        // Initialize the database
        personaReunionRepository.saveAndFlush(personaReunion);

        // Get the personaReunion
        restPersonaReunionMockMvc.perform(get("/api/persona-reunions/{id}", personaReunion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personaReunion.getId().intValue()))
            .andExpect(jsonPath("$.idPersona").value(DEFAULT_ID_PERSONA.intValue()))
            .andExpect(jsonPath("$.idReunion").value(DEFAULT_ID_REUNION.intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonaReunion() throws Exception {
        // Get the personaReunion
        restPersonaReunionMockMvc.perform(get("/api/persona-reunions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonaReunion() throws Exception {
        // Initialize the database
        personaReunionService.save(personaReunion);

        int databaseSizeBeforeUpdate = personaReunionRepository.findAll().size();

        // Update the personaReunion
        PersonaReunion updatedPersonaReunion = personaReunionRepository.findOne(personaReunion.getId());
        // Disconnect from session so that the updates on updatedPersonaReunion are not directly saved in db
        em.detach(updatedPersonaReunion);
        updatedPersonaReunion
            .idPersona(UPDATED_ID_PERSONA)
            .idReunion(UPDATED_ID_REUNION)
            .fecha(UPDATED_FECHA);

        restPersonaReunionMockMvc.perform(put("/api/persona-reunions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonaReunion)))
            .andExpect(status().isOk());

        // Validate the PersonaReunion in the database
        List<PersonaReunion> personaReunionList = personaReunionRepository.findAll();
        assertThat(personaReunionList).hasSize(databaseSizeBeforeUpdate);
        PersonaReunion testPersonaReunion = personaReunionList.get(personaReunionList.size() - 1);
        assertThat(testPersonaReunion.getIdPersona()).isEqualTo(UPDATED_ID_PERSONA);
        assertThat(testPersonaReunion.getIdReunion()).isEqualTo(UPDATED_ID_REUNION);
        assertThat(testPersonaReunion.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonaReunion() throws Exception {
        int databaseSizeBeforeUpdate = personaReunionRepository.findAll().size();

        // Create the PersonaReunion

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonaReunionMockMvc.perform(put("/api/persona-reunions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personaReunion)))
            .andExpect(status().isCreated());

        // Validate the PersonaReunion in the database
        List<PersonaReunion> personaReunionList = personaReunionRepository.findAll();
        assertThat(personaReunionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonaReunion() throws Exception {
        // Initialize the database
        personaReunionService.save(personaReunion);

        int databaseSizeBeforeDelete = personaReunionRepository.findAll().size();

        // Get the personaReunion
        restPersonaReunionMockMvc.perform(delete("/api/persona-reunions/{id}", personaReunion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonaReunion> personaReunionList = personaReunionRepository.findAll();
        assertThat(personaReunionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonaReunion.class);
        PersonaReunion personaReunion1 = new PersonaReunion();
        personaReunion1.setId(1L);
        PersonaReunion personaReunion2 = new PersonaReunion();
        personaReunion2.setId(personaReunion1.getId());
        assertThat(personaReunion1).isEqualTo(personaReunion2);
        personaReunion2.setId(2L);
        assertThat(personaReunion1).isNotEqualTo(personaReunion2);
        personaReunion1.setId(null);
        assertThat(personaReunion1).isNotEqualTo(personaReunion2);
    }
}
