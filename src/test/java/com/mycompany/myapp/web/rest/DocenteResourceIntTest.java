package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimonBolivarApp;

import com.mycompany.myapp.domain.Docente;
import com.mycompany.myapp.repository.DocenteRepository;
import com.mycompany.myapp.service.DocenteService;
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
 * Test class for the DocenteResource REST controller.
 *
 * @see DocenteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimonBolivarApp.class)
public class DocenteResourceIntTest {

    private static final String DEFAULT_GRADO = "AAAAAAAAAA";
    private static final String UPDATED_GRADO = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_PERSONA = 1L;
    private static final Long UPDATED_ID_PERSONA = 2L;

    private static final Long DEFAULT_ID_TRABAJADOR = 1L;
    private static final Long UPDATED_ID_TRABAJADOR = 2L;

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private DocenteService docenteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDocenteMockMvc;

    private Docente docente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocenteResource docenteResource = new DocenteResource(docenteService);
        this.restDocenteMockMvc = MockMvcBuilders.standaloneSetup(docenteResource)
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
    public static Docente createEntity(EntityManager em) {
        Docente docente = new Docente()
            .grado(DEFAULT_GRADO)
            .idPersona(DEFAULT_ID_PERSONA)
            .idTrabajador(DEFAULT_ID_TRABAJADOR);
        return docente;
    }

    @Before
    public void initTest() {
        docente = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocente() throws Exception {
        int databaseSizeBeforeCreate = docenteRepository.findAll().size();

        // Create the Docente
        restDocenteMockMvc.perform(post("/api/docentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docente)))
            .andExpect(status().isCreated());

        // Validate the Docente in the database
        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeCreate + 1);
        Docente testDocente = docenteList.get(docenteList.size() - 1);
        assertThat(testDocente.getGrado()).isEqualTo(DEFAULT_GRADO);
        assertThat(testDocente.getIdPersona()).isEqualTo(DEFAULT_ID_PERSONA);
        assertThat(testDocente.getIdTrabajador()).isEqualTo(DEFAULT_ID_TRABAJADOR);
    }

    @Test
    @Transactional
    public void createDocenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = docenteRepository.findAll().size();

        // Create the Docente with an existing ID
        docente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocenteMockMvc.perform(post("/api/docentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docente)))
            .andExpect(status().isBadRequest());

        // Validate the Docente in the database
        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDocentes() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get all the docenteList
        restDocenteMockMvc.perform(get("/api/docentes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(docente.getId().intValue())))
            .andExpect(jsonPath("$.[*].grado").value(hasItem(DEFAULT_GRADO.toString())))
            .andExpect(jsonPath("$.[*].idPersona").value(hasItem(DEFAULT_ID_PERSONA.intValue())))
            .andExpect(jsonPath("$.[*].idTrabajador").value(hasItem(DEFAULT_ID_TRABAJADOR.intValue())));
    }

    @Test
    @Transactional
    public void getDocente() throws Exception {
        // Initialize the database
        docenteRepository.saveAndFlush(docente);

        // Get the docente
        restDocenteMockMvc.perform(get("/api/docentes/{id}", docente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(docente.getId().intValue()))
            .andExpect(jsonPath("$.grado").value(DEFAULT_GRADO.toString()))
            .andExpect(jsonPath("$.idPersona").value(DEFAULT_ID_PERSONA.intValue()))
            .andExpect(jsonPath("$.idTrabajador").value(DEFAULT_ID_TRABAJADOR.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDocente() throws Exception {
        // Get the docente
        restDocenteMockMvc.perform(get("/api/docentes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocente() throws Exception {
        // Initialize the database
        docenteService.save(docente);

        int databaseSizeBeforeUpdate = docenteRepository.findAll().size();

        // Update the docente
        Docente updatedDocente = docenteRepository.findOne(docente.getId());
        // Disconnect from session so that the updates on updatedDocente are not directly saved in db
        em.detach(updatedDocente);
        updatedDocente
            .grado(UPDATED_GRADO)
            .idPersona(UPDATED_ID_PERSONA)
            .idTrabajador(UPDATED_ID_TRABAJADOR);

        restDocenteMockMvc.perform(put("/api/docentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocente)))
            .andExpect(status().isOk());

        // Validate the Docente in the database
        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeUpdate);
        Docente testDocente = docenteList.get(docenteList.size() - 1);
        assertThat(testDocente.getGrado()).isEqualTo(UPDATED_GRADO);
        assertThat(testDocente.getIdPersona()).isEqualTo(UPDATED_ID_PERSONA);
        assertThat(testDocente.getIdTrabajador()).isEqualTo(UPDATED_ID_TRABAJADOR);
    }

    @Test
    @Transactional
    public void updateNonExistingDocente() throws Exception {
        int databaseSizeBeforeUpdate = docenteRepository.findAll().size();

        // Create the Docente

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDocenteMockMvc.perform(put("/api/docentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docente)))
            .andExpect(status().isCreated());

        // Validate the Docente in the database
        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDocente() throws Exception {
        // Initialize the database
        docenteService.save(docente);

        int databaseSizeBeforeDelete = docenteRepository.findAll().size();

        // Get the docente
        restDocenteMockMvc.perform(delete("/api/docentes/{id}", docente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Docente> docenteList = docenteRepository.findAll();
        assertThat(docenteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Docente.class);
        Docente docente1 = new Docente();
        docente1.setId(1L);
        Docente docente2 = new Docente();
        docente2.setId(docente1.getId());
        assertThat(docente1).isEqualTo(docente2);
        docente2.setId(2L);
        assertThat(docente1).isNotEqualTo(docente2);
        docente1.setId(null);
        assertThat(docente1).isNotEqualTo(docente2);
    }
}
