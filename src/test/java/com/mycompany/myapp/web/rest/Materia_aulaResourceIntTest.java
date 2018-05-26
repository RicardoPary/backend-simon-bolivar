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
 * Test class for the Materia_aulaResource REST controller.
 *
 * @see Materia_aulaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimonBolivarApp.class)
public class Materia_aulaResourceIntTest {

    private static final Long DEFAULT_ID_AULA = 1L;
    private static final Long UPDATED_ID_AULA = 2L;

    private static final Long DEFAULT_ID_MATERIA = 1L;
    private static final Long UPDATED_ID_MATERIA = 2L;

    @Autowired
    private Materia_aulaRepository materia_aulaRepository;

    @Autowired
    private Materia_aulaService materia_aulaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMateria_aulaMockMvc;

    private Materia_aula materia_aula;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Materia_aulaResource materia_aulaResource = new Materia_aulaResource(materia_aulaService);
        this.restMateria_aulaMockMvc = MockMvcBuilders.standaloneSetup(materia_aulaResource)
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
    public static Materia_aula createEntity(EntityManager em) {
        Materia_aula materia_aula = new Materia_aula()
            .idAula(DEFAULT_ID_AULA)
            .idMateria(DEFAULT_ID_MATERIA);
        return materia_aula;
    }

    @Before
    public void initTest() {
        materia_aula = createEntity(em);
    }

    @Test
    @Transactional
    public void createMateria_aula() throws Exception {
        int databaseSizeBeforeCreate = materia_aulaRepository.findAll().size();

        // Create the Materia_aula
        restMateria_aulaMockMvc.perform(post("/api/materia-aulas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materia_aula)))
            .andExpect(status().isCreated());

        // Validate the Materia_aula in the database
        List<Materia_aula> materia_aulaList = materia_aulaRepository.findAll();
        assertThat(materia_aulaList).hasSize(databaseSizeBeforeCreate + 1);
        Materia_aula testMateria_aula = materia_aulaList.get(materia_aulaList.size() - 1);
        assertThat(testMateria_aula.getIdAula()).isEqualTo(DEFAULT_ID_AULA);
        assertThat(testMateria_aula.getIdMateria()).isEqualTo(DEFAULT_ID_MATERIA);
    }

    @Test
    @Transactional
    public void createMateria_aulaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = materia_aulaRepository.findAll().size();

        // Create the Materia_aula with an existing ID
        materia_aula.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMateria_aulaMockMvc.perform(post("/api/materia-aulas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materia_aula)))
            .andExpect(status().isBadRequest());

        // Validate the Materia_aula in the database
        List<Materia_aula> materia_aulaList = materia_aulaRepository.findAll();
        assertThat(materia_aulaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMateria_aulas() throws Exception {
        // Initialize the database
        materia_aulaRepository.saveAndFlush(materia_aula);

        // Get all the materia_aulaList
        restMateria_aulaMockMvc.perform(get("/api/materia-aulas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materia_aula.getId().intValue())))
            .andExpect(jsonPath("$.[*].idAula").value(hasItem(DEFAULT_ID_AULA.intValue())))
            .andExpect(jsonPath("$.[*].idMateria").value(hasItem(DEFAULT_ID_MATERIA.intValue())));
    }

    @Test
    @Transactional
    public void getMateria_aula() throws Exception {
        // Initialize the database
        materia_aulaRepository.saveAndFlush(materia_aula);

        // Get the materia_aula
        restMateria_aulaMockMvc.perform(get("/api/materia-aulas/{id}", materia_aula.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(materia_aula.getId().intValue()))
            .andExpect(jsonPath("$.idAula").value(DEFAULT_ID_AULA.intValue()))
            .andExpect(jsonPath("$.idMateria").value(DEFAULT_ID_MATERIA.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMateria_aula() throws Exception {
        // Get the materia_aula
        restMateria_aulaMockMvc.perform(get("/api/materia-aulas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMateria_aula() throws Exception {
        // Initialize the database
        materia_aulaService.save(materia_aula);

        int databaseSizeBeforeUpdate = materia_aulaRepository.findAll().size();

        // Update the materia_aula
        Materia_aula updatedMateria_aula = materia_aulaRepository.findOne(materia_aula.getId());
        // Disconnect from session so that the updates on updatedMateria_aula are not directly saved in db
        em.detach(updatedMateria_aula);
        updatedMateria_aula
            .idAula(UPDATED_ID_AULA)
            .idMateria(UPDATED_ID_MATERIA);

        restMateria_aulaMockMvc.perform(put("/api/materia-aulas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMateria_aula)))
            .andExpect(status().isOk());

        // Validate the Materia_aula in the database
        List<Materia_aula> materia_aulaList = materia_aulaRepository.findAll();
        assertThat(materia_aulaList).hasSize(databaseSizeBeforeUpdate);
        Materia_aula testMateria_aula = materia_aulaList.get(materia_aulaList.size() - 1);
        assertThat(testMateria_aula.getIdAula()).isEqualTo(UPDATED_ID_AULA);
        assertThat(testMateria_aula.getIdMateria()).isEqualTo(UPDATED_ID_MATERIA);
    }

    @Test
    @Transactional
    public void updateNonExistingMateria_aula() throws Exception {
        int databaseSizeBeforeUpdate = materia_aulaRepository.findAll().size();

        // Create the Materia_aula

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMateria_aulaMockMvc.perform(put("/api/materia-aulas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materia_aula)))
            .andExpect(status().isCreated());

        // Validate the Materia_aula in the database
        List<Materia_aula> materia_aulaList = materia_aulaRepository.findAll();
        assertThat(materia_aulaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMateria_aula() throws Exception {
        // Initialize the database
        materia_aulaService.save(materia_aula);

        int databaseSizeBeforeDelete = materia_aulaRepository.findAll().size();

        // Get the materia_aula
        restMateria_aulaMockMvc.perform(delete("/api/materia-aulas/{id}", materia_aula.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Materia_aula> materia_aulaList = materia_aulaRepository.findAll();
        assertThat(materia_aulaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Materia_aula.class);
        Materia_aula materia_aula1 = new Materia_aula();
        materia_aula1.setId(1L);
        Materia_aula materia_aula2 = new Materia_aula();
        materia_aula2.setId(materia_aula1.getId());
        assertThat(materia_aula1).isEqualTo(materia_aula2);
        materia_aula2.setId(2L);
        assertThat(materia_aula1).isNotEqualTo(materia_aula2);
        materia_aula1.setId(null);
        assertThat(materia_aula1).isNotEqualTo(materia_aula2);
    }
}
