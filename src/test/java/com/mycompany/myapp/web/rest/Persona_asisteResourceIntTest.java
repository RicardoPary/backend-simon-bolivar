package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimonBolivarApp;

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
 * Test class for the Persona_asisteResource REST controller.
 *
 * @see Persona_asisteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimonBolivarApp.class)
public class Persona_asisteResourceIntTest {

    private static final Long DEFAULT_ID_PERSONA = 1L;
    private static final Long UPDATED_ID_PERSONA = 2L;

    private static final Integer DEFAULT_ID_REUNION = 1;
    private static final Integer UPDATED_ID_REUNION = 2;

    private static final String DEFAULT_FECHA = "AAAAAAAAAA";
    private static final String UPDATED_FECHA = "BBBBBBBBBB";

    @Autowired
    private Persona_asisteRepository persona_asisteRepository;

    @Autowired
    private Persona_asisteService persona_asisteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersona_asisteMockMvc;

    private Persona_asiste persona_asiste;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Persona_asisteResource persona_asisteResource = new Persona_asisteResource(persona_asisteService);
        this.restPersona_asisteMockMvc = MockMvcBuilders.standaloneSetup(persona_asisteResource)
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
    public static Persona_asiste createEntity(EntityManager em) {
        Persona_asiste persona_asiste = new Persona_asiste()
            .idPersona(DEFAULT_ID_PERSONA)
            .idReunion(DEFAULT_ID_REUNION)
            .fecha(DEFAULT_FECHA);
        return persona_asiste;
    }

    @Before
    public void initTest() {
        persona_asiste = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersona_asiste() throws Exception {
        int databaseSizeBeforeCreate = persona_asisteRepository.findAll().size();

        // Create the Persona_asiste
        restPersona_asisteMockMvc.perform(post("/api/persona-asistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona_asiste)))
            .andExpect(status().isCreated());

        // Validate the Persona_asiste in the database
        List<Persona_asiste> persona_asisteList = persona_asisteRepository.findAll();
        assertThat(persona_asisteList).hasSize(databaseSizeBeforeCreate + 1);
        Persona_asiste testPersona_asiste = persona_asisteList.get(persona_asisteList.size() - 1);
        assertThat(testPersona_asiste.getIdPersona()).isEqualTo(DEFAULT_ID_PERSONA);
        assertThat(testPersona_asiste.getIdReunion()).isEqualTo(DEFAULT_ID_REUNION);
        assertThat(testPersona_asiste.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createPersona_asisteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = persona_asisteRepository.findAll().size();

        // Create the Persona_asiste with an existing ID
        persona_asiste.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersona_asisteMockMvc.perform(post("/api/persona-asistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona_asiste)))
            .andExpect(status().isBadRequest());

        // Validate the Persona_asiste in the database
        List<Persona_asiste> persona_asisteList = persona_asisteRepository.findAll();
        assertThat(persona_asisteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersona_asistes() throws Exception {
        // Initialize the database
        persona_asisteRepository.saveAndFlush(persona_asiste);

        // Get all the persona_asisteList
        restPersona_asisteMockMvc.perform(get("/api/persona-asistes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(persona_asiste.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPersona").value(hasItem(DEFAULT_ID_PERSONA.intValue())))
            .andExpect(jsonPath("$.[*].idReunion").value(hasItem(DEFAULT_ID_REUNION)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }

    @Test
    @Transactional
    public void getPersona_asiste() throws Exception {
        // Initialize the database
        persona_asisteRepository.saveAndFlush(persona_asiste);

        // Get the persona_asiste
        restPersona_asisteMockMvc.perform(get("/api/persona-asistes/{id}", persona_asiste.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(persona_asiste.getId().intValue()))
            .andExpect(jsonPath("$.idPersona").value(DEFAULT_ID_PERSONA.intValue()))
            .andExpect(jsonPath("$.idReunion").value(DEFAULT_ID_REUNION))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersona_asiste() throws Exception {
        // Get the persona_asiste
        restPersona_asisteMockMvc.perform(get("/api/persona-asistes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersona_asiste() throws Exception {
        // Initialize the database
        persona_asisteService.save(persona_asiste);

        int databaseSizeBeforeUpdate = persona_asisteRepository.findAll().size();

        // Update the persona_asiste
        Persona_asiste updatedPersona_asiste = persona_asisteRepository.findOne(persona_asiste.getId());
        // Disconnect from session so that the updates on updatedPersona_asiste are not directly saved in db
        em.detach(updatedPersona_asiste);
        updatedPersona_asiste
            .idPersona(UPDATED_ID_PERSONA)
            .idReunion(UPDATED_ID_REUNION)
            .fecha(UPDATED_FECHA);

        restPersona_asisteMockMvc.perform(put("/api/persona-asistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersona_asiste)))
            .andExpect(status().isOk());

        // Validate the Persona_asiste in the database
        List<Persona_asiste> persona_asisteList = persona_asisteRepository.findAll();
        assertThat(persona_asisteList).hasSize(databaseSizeBeforeUpdate);
        Persona_asiste testPersona_asiste = persona_asisteList.get(persona_asisteList.size() - 1);
        assertThat(testPersona_asiste.getIdPersona()).isEqualTo(UPDATED_ID_PERSONA);
        assertThat(testPersona_asiste.getIdReunion()).isEqualTo(UPDATED_ID_REUNION);
        assertThat(testPersona_asiste.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingPersona_asiste() throws Exception {
        int databaseSizeBeforeUpdate = persona_asisteRepository.findAll().size();

        // Create the Persona_asiste

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersona_asisteMockMvc.perform(put("/api/persona-asistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona_asiste)))
            .andExpect(status().isCreated());

        // Validate the Persona_asiste in the database
        List<Persona_asiste> persona_asisteList = persona_asisteRepository.findAll();
        assertThat(persona_asisteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersona_asiste() throws Exception {
        // Initialize the database
        persona_asisteService.save(persona_asiste);

        int databaseSizeBeforeDelete = persona_asisteRepository.findAll().size();

        // Get the persona_asiste
        restPersona_asisteMockMvc.perform(delete("/api/persona-asistes/{id}", persona_asiste.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Persona_asiste> persona_asisteList = persona_asisteRepository.findAll();
        assertThat(persona_asisteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Persona_asiste.class);
        Persona_asiste persona_asiste1 = new Persona_asiste();
        persona_asiste1.setId(1L);
        Persona_asiste persona_asiste2 = new Persona_asiste();
        persona_asiste2.setId(persona_asiste1.getId());
        assertThat(persona_asiste1).isEqualTo(persona_asiste2);
        persona_asiste2.setId(2L);
        assertThat(persona_asiste1).isNotEqualTo(persona_asiste2);
        persona_asiste1.setId(null);
        assertThat(persona_asiste1).isNotEqualTo(persona_asiste2);
    }
}
