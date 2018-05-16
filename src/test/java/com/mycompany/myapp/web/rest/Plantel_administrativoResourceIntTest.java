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
 * Test class for the Plantel_administrativoResource REST controller.
 *
 * @see Plantel_administrativoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimonBolivarApp.class)
public class Plantel_administrativoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private Plantel_administrativoRepository plantel_administrativoRepository;

    @Autowired
    private Plantel_administrativoService plantel_administrativoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlantel_administrativoMockMvc;

    private Plantel_administrativo plantel_administrativo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Plantel_administrativoResource plantel_administrativoResource = new Plantel_administrativoResource(plantel_administrativoService);
        this.restPlantel_administrativoMockMvc = MockMvcBuilders.standaloneSetup(plantel_administrativoResource)
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
    public static Plantel_administrativo createEntity(EntityManager em) {
        Plantel_administrativo plantel_administrativo = new Plantel_administrativo()
            .nombre(DEFAULT_NOMBRE);
        return plantel_administrativo;
    }

    @Before
    public void initTest() {
        plantel_administrativo = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlantel_administrativo() throws Exception {
        int databaseSizeBeforeCreate = plantel_administrativoRepository.findAll().size();

        // Create the Plantel_administrativo
        restPlantel_administrativoMockMvc.perform(post("/api/plantel-administrativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plantel_administrativo)))
            .andExpect(status().isCreated());

        // Validate the Plantel_administrativo in the database
        List<Plantel_administrativo> plantel_administrativoList = plantel_administrativoRepository.findAll();
        assertThat(plantel_administrativoList).hasSize(databaseSizeBeforeCreate + 1);
        Plantel_administrativo testPlantel_administrativo = plantel_administrativoList.get(plantel_administrativoList.size() - 1);
        assertThat(testPlantel_administrativo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createPlantel_administrativoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plantel_administrativoRepository.findAll().size();

        // Create the Plantel_administrativo with an existing ID
        plantel_administrativo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlantel_administrativoMockMvc.perform(post("/api/plantel-administrativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plantel_administrativo)))
            .andExpect(status().isBadRequest());

        // Validate the Plantel_administrativo in the database
        List<Plantel_administrativo> plantel_administrativoList = plantel_administrativoRepository.findAll();
        assertThat(plantel_administrativoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPlantel_administrativos() throws Exception {
        // Initialize the database
        plantel_administrativoRepository.saveAndFlush(plantel_administrativo);

        // Get all the plantel_administrativoList
        restPlantel_administrativoMockMvc.perform(get("/api/plantel-administrativos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plantel_administrativo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getPlantel_administrativo() throws Exception {
        // Initialize the database
        plantel_administrativoRepository.saveAndFlush(plantel_administrativo);

        // Get the plantel_administrativo
        restPlantel_administrativoMockMvc.perform(get("/api/plantel-administrativos/{id}", plantel_administrativo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(plantel_administrativo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlantel_administrativo() throws Exception {
        // Get the plantel_administrativo
        restPlantel_administrativoMockMvc.perform(get("/api/plantel-administrativos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlantel_administrativo() throws Exception {
        // Initialize the database
        plantel_administrativoService.save(plantel_administrativo);

        int databaseSizeBeforeUpdate = plantel_administrativoRepository.findAll().size();

        // Update the plantel_administrativo
        Plantel_administrativo updatedPlantel_administrativo = plantel_administrativoRepository.findOne(plantel_administrativo.getId());
        // Disconnect from session so that the updates on updatedPlantel_administrativo are not directly saved in db
        em.detach(updatedPlantel_administrativo);
        updatedPlantel_administrativo
            .nombre(UPDATED_NOMBRE);

        restPlantel_administrativoMockMvc.perform(put("/api/plantel-administrativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlantel_administrativo)))
            .andExpect(status().isOk());

        // Validate the Plantel_administrativo in the database
        List<Plantel_administrativo> plantel_administrativoList = plantel_administrativoRepository.findAll();
        assertThat(plantel_administrativoList).hasSize(databaseSizeBeforeUpdate);
        Plantel_administrativo testPlantel_administrativo = plantel_administrativoList.get(plantel_administrativoList.size() - 1);
        assertThat(testPlantel_administrativo.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingPlantel_administrativo() throws Exception {
        int databaseSizeBeforeUpdate = plantel_administrativoRepository.findAll().size();

        // Create the Plantel_administrativo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlantel_administrativoMockMvc.perform(put("/api/plantel-administrativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plantel_administrativo)))
            .andExpect(status().isCreated());

        // Validate the Plantel_administrativo in the database
        List<Plantel_administrativo> plantel_administrativoList = plantel_administrativoRepository.findAll();
        assertThat(plantel_administrativoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlantel_administrativo() throws Exception {
        // Initialize the database
        plantel_administrativoService.save(plantel_administrativo);

        int databaseSizeBeforeDelete = plantel_administrativoRepository.findAll().size();

        // Get the plantel_administrativo
        restPlantel_administrativoMockMvc.perform(delete("/api/plantel-administrativos/{id}", plantel_administrativo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Plantel_administrativo> plantel_administrativoList = plantel_administrativoRepository.findAll();
        assertThat(plantel_administrativoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Plantel_administrativo.class);
        Plantel_administrativo plantel_administrativo1 = new Plantel_administrativo();
        plantel_administrativo1.setId(1L);
        Plantel_administrativo plantel_administrativo2 = new Plantel_administrativo();
        plantel_administrativo2.setId(plantel_administrativo1.getId());
        assertThat(plantel_administrativo1).isEqualTo(plantel_administrativo2);
        plantel_administrativo2.setId(2L);
        assertThat(plantel_administrativo1).isNotEqualTo(plantel_administrativo2);
        plantel_administrativo1.setId(null);
        assertThat(plantel_administrativo1).isNotEqualTo(plantel_administrativo2);
    }
}
