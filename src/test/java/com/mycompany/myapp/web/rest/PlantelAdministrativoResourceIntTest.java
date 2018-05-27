package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimonBolivarApp;

import com.mycompany.myapp.domain.PlantelAdministrativo;
import com.mycompany.myapp.repository.PlantelAdministrativoRepository;
import com.mycompany.myapp.service.PlantelAdministrativoService;
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
 * Test class for the PlantelAdministrativoResource REST controller.
 *
 * @see PlantelAdministrativoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimonBolivarApp.class)
public class PlantelAdministrativoResourceIntTest {

    private static final String DEFAULT_CARGO = "AAAAAAAAAA";
    private static final String UPDATED_CARGO = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_PERSONA = 1L;
    private static final Long UPDATED_ID_PERSONA = 2L;

    private static final Long DEFAULT_ID_TRABAJADOR = 1L;
    private static final Long UPDATED_ID_TRABAJADOR = 2L;

    @Autowired
    private PlantelAdministrativoRepository plantelAdministrativoRepository;

    @Autowired
    private PlantelAdministrativoService plantelAdministrativoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlantelAdministrativoMockMvc;

    private PlantelAdministrativo plantelAdministrativo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlantelAdministrativoResource plantelAdministrativoResource = new PlantelAdministrativoResource(plantelAdministrativoService);
        this.restPlantelAdministrativoMockMvc = MockMvcBuilders.standaloneSetup(plantelAdministrativoResource)
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
    public static PlantelAdministrativo createEntity(EntityManager em) {
        PlantelAdministrativo plantelAdministrativo = new PlantelAdministrativo()
            .cargo(DEFAULT_CARGO)
            .idPersona(DEFAULT_ID_PERSONA)
            .idTrabajador(DEFAULT_ID_TRABAJADOR);
        return plantelAdministrativo;
    }

    @Before
    public void initTest() {
        plantelAdministrativo = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlantelAdministrativo() throws Exception {
        int databaseSizeBeforeCreate = plantelAdministrativoRepository.findAll().size();

        // Create the PlantelAdministrativo
        restPlantelAdministrativoMockMvc.perform(post("/api/plantel-administrativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plantelAdministrativo)))
            .andExpect(status().isCreated());

        // Validate the PlantelAdministrativo in the database
        List<PlantelAdministrativo> plantelAdministrativoList = plantelAdministrativoRepository.findAll();
        assertThat(plantelAdministrativoList).hasSize(databaseSizeBeforeCreate + 1);
        PlantelAdministrativo testPlantelAdministrativo = plantelAdministrativoList.get(plantelAdministrativoList.size() - 1);
        assertThat(testPlantelAdministrativo.getCargo()).isEqualTo(DEFAULT_CARGO);
        assertThat(testPlantelAdministrativo.getIdPersona()).isEqualTo(DEFAULT_ID_PERSONA);
        assertThat(testPlantelAdministrativo.getIdTrabajador()).isEqualTo(DEFAULT_ID_TRABAJADOR);
    }

    @Test
    @Transactional
    public void createPlantelAdministrativoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plantelAdministrativoRepository.findAll().size();

        // Create the PlantelAdministrativo with an existing ID
        plantelAdministrativo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlantelAdministrativoMockMvc.perform(post("/api/plantel-administrativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plantelAdministrativo)))
            .andExpect(status().isBadRequest());

        // Validate the PlantelAdministrativo in the database
        List<PlantelAdministrativo> plantelAdministrativoList = plantelAdministrativoRepository.findAll();
        assertThat(plantelAdministrativoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPlantelAdministrativos() throws Exception {
        // Initialize the database
        plantelAdministrativoRepository.saveAndFlush(plantelAdministrativo);

        // Get all the plantelAdministrativoList
        restPlantelAdministrativoMockMvc.perform(get("/api/plantel-administrativos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plantelAdministrativo.getId().intValue())))
            .andExpect(jsonPath("$.[*].cargo").value(hasItem(DEFAULT_CARGO.toString())))
            .andExpect(jsonPath("$.[*].idPersona").value(hasItem(DEFAULT_ID_PERSONA.intValue())))
            .andExpect(jsonPath("$.[*].idTrabajador").value(hasItem(DEFAULT_ID_TRABAJADOR.intValue())));
    }

    @Test
    @Transactional
    public void getPlantelAdministrativo() throws Exception {
        // Initialize the database
        plantelAdministrativoRepository.saveAndFlush(plantelAdministrativo);

        // Get the plantelAdministrativo
        restPlantelAdministrativoMockMvc.perform(get("/api/plantel-administrativos/{id}", plantelAdministrativo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(plantelAdministrativo.getId().intValue()))
            .andExpect(jsonPath("$.cargo").value(DEFAULT_CARGO.toString()))
            .andExpect(jsonPath("$.idPersona").value(DEFAULT_ID_PERSONA.intValue()))
            .andExpect(jsonPath("$.idTrabajador").value(DEFAULT_ID_TRABAJADOR.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPlantelAdministrativo() throws Exception {
        // Get the plantelAdministrativo
        restPlantelAdministrativoMockMvc.perform(get("/api/plantel-administrativos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlantelAdministrativo() throws Exception {
        // Initialize the database
        plantelAdministrativoService.save(plantelAdministrativo);

        int databaseSizeBeforeUpdate = plantelAdministrativoRepository.findAll().size();

        // Update the plantelAdministrativo
        PlantelAdministrativo updatedPlantelAdministrativo = plantelAdministrativoRepository.findOne(plantelAdministrativo.getId());
        // Disconnect from session so that the updates on updatedPlantelAdministrativo are not directly saved in db
        em.detach(updatedPlantelAdministrativo);
        updatedPlantelAdministrativo
            .cargo(UPDATED_CARGO)
            .idPersona(UPDATED_ID_PERSONA)
            .idTrabajador(UPDATED_ID_TRABAJADOR);

        restPlantelAdministrativoMockMvc.perform(put("/api/plantel-administrativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlantelAdministrativo)))
            .andExpect(status().isOk());

        // Validate the PlantelAdministrativo in the database
        List<PlantelAdministrativo> plantelAdministrativoList = plantelAdministrativoRepository.findAll();
        assertThat(plantelAdministrativoList).hasSize(databaseSizeBeforeUpdate);
        PlantelAdministrativo testPlantelAdministrativo = plantelAdministrativoList.get(plantelAdministrativoList.size() - 1);
        assertThat(testPlantelAdministrativo.getCargo()).isEqualTo(UPDATED_CARGO);
        assertThat(testPlantelAdministrativo.getIdPersona()).isEqualTo(UPDATED_ID_PERSONA);
        assertThat(testPlantelAdministrativo.getIdTrabajador()).isEqualTo(UPDATED_ID_TRABAJADOR);
    }

    @Test
    @Transactional
    public void updateNonExistingPlantelAdministrativo() throws Exception {
        int databaseSizeBeforeUpdate = plantelAdministrativoRepository.findAll().size();

        // Create the PlantelAdministrativo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlantelAdministrativoMockMvc.perform(put("/api/plantel-administrativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plantelAdministrativo)))
            .andExpect(status().isCreated());

        // Validate the PlantelAdministrativo in the database
        List<PlantelAdministrativo> plantelAdministrativoList = plantelAdministrativoRepository.findAll();
        assertThat(plantelAdministrativoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlantelAdministrativo() throws Exception {
        // Initialize the database
        plantelAdministrativoService.save(plantelAdministrativo);

        int databaseSizeBeforeDelete = plantelAdministrativoRepository.findAll().size();

        // Get the plantelAdministrativo
        restPlantelAdministrativoMockMvc.perform(delete("/api/plantel-administrativos/{id}", plantelAdministrativo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PlantelAdministrativo> plantelAdministrativoList = plantelAdministrativoRepository.findAll();
        assertThat(plantelAdministrativoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlantelAdministrativo.class);
        PlantelAdministrativo plantelAdministrativo1 = new PlantelAdministrativo();
        plantelAdministrativo1.setId(1L);
        PlantelAdministrativo plantelAdministrativo2 = new PlantelAdministrativo();
        plantelAdministrativo2.setId(plantelAdministrativo1.getId());
        assertThat(plantelAdministrativo1).isEqualTo(plantelAdministrativo2);
        plantelAdministrativo2.setId(2L);
        assertThat(plantelAdministrativo1).isNotEqualTo(plantelAdministrativo2);
        plantelAdministrativo1.setId(null);
        assertThat(plantelAdministrativo1).isNotEqualTo(plantelAdministrativo2);
    }
}
