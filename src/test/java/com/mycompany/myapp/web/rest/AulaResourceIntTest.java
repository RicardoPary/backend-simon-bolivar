package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimonBolivarApp;

import com.mycompany.myapp.domain.Aula;
import com.mycompany.myapp.repository.AulaRepository;
import com.mycompany.myapp.service.AulaService;
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
 * Test class for the AulaResource REST controller.
 *
 * @see AulaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimonBolivarApp.class)
public class AulaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private AulaService aulaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAulaMockMvc;

    private Aula aula;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AulaResource aulaResource = new AulaResource(aulaService);
        this.restAulaMockMvc = MockMvcBuilders.standaloneSetup(aulaResource)
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
    public static Aula createEntity(EntityManager em) {
        Aula aula = new Aula()
            .nombre(DEFAULT_NOMBRE)
            .sigla(DEFAULT_SIGLA)
            .tipo(DEFAULT_TIPO)
            .descripcion(DEFAULT_DESCRIPCION);
        return aula;
    }

    @Before
    public void initTest() {
        aula = createEntity(em);
    }

    @Test
    @Transactional
    public void createAula() throws Exception {
        int databaseSizeBeforeCreate = aulaRepository.findAll().size();

        // Create the Aula
        restAulaMockMvc.perform(post("/api/aulas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aula)))
            .andExpect(status().isCreated());

        // Validate the Aula in the database
        List<Aula> aulaList = aulaRepository.findAll();
        assertThat(aulaList).hasSize(databaseSizeBeforeCreate + 1);
        Aula testAula = aulaList.get(aulaList.size() - 1);
        assertThat(testAula.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAula.getSigla()).isEqualTo(DEFAULT_SIGLA);
        assertThat(testAula.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testAula.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createAulaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aulaRepository.findAll().size();

        // Create the Aula with an existing ID
        aula.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAulaMockMvc.perform(post("/api/aulas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aula)))
            .andExpect(status().isBadRequest());

        // Validate the Aula in the database
        List<Aula> aulaList = aulaRepository.findAll();
        assertThat(aulaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAulas() throws Exception {
        // Initialize the database
        aulaRepository.saveAndFlush(aula);

        // Get all the aulaList
        restAulaMockMvc.perform(get("/api/aulas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aula.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getAula() throws Exception {
        // Initialize the database
        aulaRepository.saveAndFlush(aula);

        // Get the aula
        restAulaMockMvc.perform(get("/api/aulas/{id}", aula.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aula.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAula() throws Exception {
        // Get the aula
        restAulaMockMvc.perform(get("/api/aulas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAula() throws Exception {
        // Initialize the database
        aulaService.save(aula);

        int databaseSizeBeforeUpdate = aulaRepository.findAll().size();

        // Update the aula
        Aula updatedAula = aulaRepository.findOne(aula.getId());
        // Disconnect from session so that the updates on updatedAula are not directly saved in db
        em.detach(updatedAula);
        updatedAula
            .nombre(UPDATED_NOMBRE)
            .sigla(UPDATED_SIGLA)
            .tipo(UPDATED_TIPO)
            .descripcion(UPDATED_DESCRIPCION);

        restAulaMockMvc.perform(put("/api/aulas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAula)))
            .andExpect(status().isOk());

        // Validate the Aula in the database
        List<Aula> aulaList = aulaRepository.findAll();
        assertThat(aulaList).hasSize(databaseSizeBeforeUpdate);
        Aula testAula = aulaList.get(aulaList.size() - 1);
        assertThat(testAula.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAula.getSigla()).isEqualTo(UPDATED_SIGLA);
        assertThat(testAula.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testAula.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingAula() throws Exception {
        int databaseSizeBeforeUpdate = aulaRepository.findAll().size();

        // Create the Aula

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAulaMockMvc.perform(put("/api/aulas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aula)))
            .andExpect(status().isCreated());

        // Validate the Aula in the database
        List<Aula> aulaList = aulaRepository.findAll();
        assertThat(aulaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAula() throws Exception {
        // Initialize the database
        aulaService.save(aula);

        int databaseSizeBeforeDelete = aulaRepository.findAll().size();

        // Get the aula
        restAulaMockMvc.perform(delete("/api/aulas/{id}", aula.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Aula> aulaList = aulaRepository.findAll();
        assertThat(aulaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aula.class);
        Aula aula1 = new Aula();
        aula1.setId(1L);
        Aula aula2 = new Aula();
        aula2.setId(aula1.getId());
        assertThat(aula1).isEqualTo(aula2);
        aula2.setId(2L);
        assertThat(aula1).isNotEqualTo(aula2);
        aula1.setId(null);
        assertThat(aula1).isNotEqualTo(aula2);
    }
}
