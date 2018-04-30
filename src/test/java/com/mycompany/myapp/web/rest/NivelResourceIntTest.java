package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimonBolivarApp;

import com.mycompany.myapp.domain.Nivel;
import com.mycompany.myapp.repository.NivelRepository;
import com.mycompany.myapp.service.NivelService;
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
 * Test class for the NivelResource REST controller.
 *
 * @see NivelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimonBolivarApp.class)
public class NivelResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private NivelRepository nivelRepository;

    @Autowired
    private NivelService nivelService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNivelMockMvc;

    private Nivel nivel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NivelResource nivelResource = new NivelResource(nivelService);
        this.restNivelMockMvc = MockMvcBuilders.standaloneSetup(nivelResource)
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
    public static Nivel createEntity(EntityManager em) {
        Nivel nivel = new Nivel()
            .nombre(DEFAULT_NOMBRE);
        return nivel;
    }

    @Before
    public void initTest() {
        nivel = createEntity(em);
    }

    @Test
    @Transactional
    public void createNivel() throws Exception {
        int databaseSizeBeforeCreate = nivelRepository.findAll().size();

        // Create the Nivel
        restNivelMockMvc.perform(post("/api/nivels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nivel)))
            .andExpect(status().isCreated());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeCreate + 1);
        Nivel testNivel = nivelList.get(nivelList.size() - 1);
        assertThat(testNivel.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createNivelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nivelRepository.findAll().size();

        // Create the Nivel with an existing ID
        nivel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNivelMockMvc.perform(post("/api/nivels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nivel)))
            .andExpect(status().isBadRequest());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNivels() throws Exception {
        // Initialize the database
        nivelRepository.saveAndFlush(nivel);

        // Get all the nivelList
        restNivelMockMvc.perform(get("/api/nivels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nivel.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getNivel() throws Exception {
        // Initialize the database
        nivelRepository.saveAndFlush(nivel);

        // Get the nivel
        restNivelMockMvc.perform(get("/api/nivels/{id}", nivel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nivel.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNivel() throws Exception {
        // Get the nivel
        restNivelMockMvc.perform(get("/api/nivels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNivel() throws Exception {
        // Initialize the database
        nivelService.save(nivel);

        int databaseSizeBeforeUpdate = nivelRepository.findAll().size();

        // Update the nivel
        Nivel updatedNivel = nivelRepository.findOne(nivel.getId());
        // Disconnect from session so that the updates on updatedNivel are not directly saved in db
        em.detach(updatedNivel);
        updatedNivel
            .nombre(UPDATED_NOMBRE);

        restNivelMockMvc.perform(put("/api/nivels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNivel)))
            .andExpect(status().isOk());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeUpdate);
        Nivel testNivel = nivelList.get(nivelList.size() - 1);
        assertThat(testNivel.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingNivel() throws Exception {
        int databaseSizeBeforeUpdate = nivelRepository.findAll().size();

        // Create the Nivel

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNivelMockMvc.perform(put("/api/nivels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nivel)))
            .andExpect(status().isCreated());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNivel() throws Exception {
        // Initialize the database
        nivelService.save(nivel);

        int databaseSizeBeforeDelete = nivelRepository.findAll().size();

        // Get the nivel
        restNivelMockMvc.perform(delete("/api/nivels/{id}", nivel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nivel.class);
        Nivel nivel1 = new Nivel();
        nivel1.setId(1L);
        Nivel nivel2 = new Nivel();
        nivel2.setId(nivel1.getId());
        assertThat(nivel1).isEqualTo(nivel2);
        nivel2.setId(2L);
        assertThat(nivel1).isNotEqualTo(nivel2);
        nivel1.setId(null);
        assertThat(nivel1).isNotEqualTo(nivel2);
    }
}
