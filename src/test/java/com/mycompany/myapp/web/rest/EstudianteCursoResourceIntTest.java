package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimonBolivarApp;

import com.mycompany.myapp.domain.EstudianteCurso;
import com.mycompany.myapp.repository.EstudianteCursoRepository;
import com.mycompany.myapp.service.EstudianteCursoService;
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
 * Test class for the EstudianteCursoResource REST controller.
 *
 * @see EstudianteCursoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimonBolivarApp.class)
public class EstudianteCursoResourceIntTest {

    private static final Long DEFAULT_ID_ESTUDIANTE = 1L;
    private static final Long UPDATED_ID_ESTUDIANTE = 2L;

    private static final Long DEFAULT_ID_CURSO = 1L;
    private static final Long UPDATED_ID_CURSO = 2L;

    private static final String DEFAULT_PARALELO = "AAAAAAAAAA";
    private static final String UPDATED_PARALELO = "BBBBBBBBBB";

    private static final Integer DEFAULT_GESTION = 1;
    private static final Integer UPDATED_GESTION = 2;

    @Autowired
    private EstudianteCursoRepository estudianteCursoRepository;

    @Autowired
    private EstudianteCursoService estudianteCursoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEstudianteCursoMockMvc;

    private EstudianteCurso estudianteCurso;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstudianteCursoResource estudianteCursoResource = new EstudianteCursoResource(estudianteCursoService);
        this.restEstudianteCursoMockMvc = MockMvcBuilders.standaloneSetup(estudianteCursoResource)
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
    public static EstudianteCurso createEntity(EntityManager em) {
        EstudianteCurso estudianteCurso = new EstudianteCurso()
            .idEstudiante(DEFAULT_ID_ESTUDIANTE)
            .idCurso(DEFAULT_ID_CURSO)
            .paralelo(DEFAULT_PARALELO)
            .gestion(DEFAULT_GESTION);
        return estudianteCurso;
    }

    @Before
    public void initTest() {
        estudianteCurso = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstudianteCurso() throws Exception {
        int databaseSizeBeforeCreate = estudianteCursoRepository.findAll().size();

        // Create the EstudianteCurso
        restEstudianteCursoMockMvc.perform(post("/api/estudiante-cursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estudianteCurso)))
            .andExpect(status().isCreated());

        // Validate the EstudianteCurso in the database
        List<EstudianteCurso> estudianteCursoList = estudianteCursoRepository.findAll();
        assertThat(estudianteCursoList).hasSize(databaseSizeBeforeCreate + 1);
        EstudianteCurso testEstudianteCurso = estudianteCursoList.get(estudianteCursoList.size() - 1);
        assertThat(testEstudianteCurso.getIdEstudiante()).isEqualTo(DEFAULT_ID_ESTUDIANTE);
        assertThat(testEstudianteCurso.getIdCurso()).isEqualTo(DEFAULT_ID_CURSO);
        assertThat(testEstudianteCurso.getParalelo()).isEqualTo(DEFAULT_PARALELO);
        assertThat(testEstudianteCurso.getGestion()).isEqualTo(DEFAULT_GESTION);
    }

    @Test
    @Transactional
    public void createEstudianteCursoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estudianteCursoRepository.findAll().size();

        // Create the EstudianteCurso with an existing ID
        estudianteCurso.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstudianteCursoMockMvc.perform(post("/api/estudiante-cursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estudianteCurso)))
            .andExpect(status().isBadRequest());

        // Validate the EstudianteCurso in the database
        List<EstudianteCurso> estudianteCursoList = estudianteCursoRepository.findAll();
        assertThat(estudianteCursoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEstudianteCursos() throws Exception {
        // Initialize the database
        estudianteCursoRepository.saveAndFlush(estudianteCurso);

        // Get all the estudianteCursoList
        restEstudianteCursoMockMvc.perform(get("/api/estudiante-cursos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estudianteCurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].idEstudiante").value(hasItem(DEFAULT_ID_ESTUDIANTE.intValue())))
            .andExpect(jsonPath("$.[*].idCurso").value(hasItem(DEFAULT_ID_CURSO.intValue())))
            .andExpect(jsonPath("$.[*].paralelo").value(hasItem(DEFAULT_PARALELO.toString())))
            .andExpect(jsonPath("$.[*].gestion").value(hasItem(DEFAULT_GESTION)));
    }

    @Test
    @Transactional
    public void getEstudianteCurso() throws Exception {
        // Initialize the database
        estudianteCursoRepository.saveAndFlush(estudianteCurso);

        // Get the estudianteCurso
        restEstudianteCursoMockMvc.perform(get("/api/estudiante-cursos/{id}", estudianteCurso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estudianteCurso.getId().intValue()))
            .andExpect(jsonPath("$.idEstudiante").value(DEFAULT_ID_ESTUDIANTE.intValue()))
            .andExpect(jsonPath("$.idCurso").value(DEFAULT_ID_CURSO.intValue()))
            .andExpect(jsonPath("$.paralelo").value(DEFAULT_PARALELO.toString()))
            .andExpect(jsonPath("$.gestion").value(DEFAULT_GESTION));
    }

    @Test
    @Transactional
    public void getNonExistingEstudianteCurso() throws Exception {
        // Get the estudianteCurso
        restEstudianteCursoMockMvc.perform(get("/api/estudiante-cursos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstudianteCurso() throws Exception {
        // Initialize the database
        estudianteCursoService.save(estudianteCurso);

        int databaseSizeBeforeUpdate = estudianteCursoRepository.findAll().size();

        // Update the estudianteCurso
        EstudianteCurso updatedEstudianteCurso = estudianteCursoRepository.findOne(estudianteCurso.getId());
        // Disconnect from session so that the updates on updatedEstudianteCurso are not directly saved in db
        em.detach(updatedEstudianteCurso);
        updatedEstudianteCurso
            .idEstudiante(UPDATED_ID_ESTUDIANTE)
            .idCurso(UPDATED_ID_CURSO)
            .paralelo(UPDATED_PARALELO)
            .gestion(UPDATED_GESTION);

        restEstudianteCursoMockMvc.perform(put("/api/estudiante-cursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEstudianteCurso)))
            .andExpect(status().isOk());

        // Validate the EstudianteCurso in the database
        List<EstudianteCurso> estudianteCursoList = estudianteCursoRepository.findAll();
        assertThat(estudianteCursoList).hasSize(databaseSizeBeforeUpdate);
        EstudianteCurso testEstudianteCurso = estudianteCursoList.get(estudianteCursoList.size() - 1);
        assertThat(testEstudianteCurso.getIdEstudiante()).isEqualTo(UPDATED_ID_ESTUDIANTE);
        assertThat(testEstudianteCurso.getIdCurso()).isEqualTo(UPDATED_ID_CURSO);
        assertThat(testEstudianteCurso.getParalelo()).isEqualTo(UPDATED_PARALELO);
        assertThat(testEstudianteCurso.getGestion()).isEqualTo(UPDATED_GESTION);
    }

    @Test
    @Transactional
    public void updateNonExistingEstudianteCurso() throws Exception {
        int databaseSizeBeforeUpdate = estudianteCursoRepository.findAll().size();

        // Create the EstudianteCurso

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEstudianteCursoMockMvc.perform(put("/api/estudiante-cursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estudianteCurso)))
            .andExpect(status().isCreated());

        // Validate the EstudianteCurso in the database
        List<EstudianteCurso> estudianteCursoList = estudianteCursoRepository.findAll();
        assertThat(estudianteCursoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEstudianteCurso() throws Exception {
        // Initialize the database
        estudianteCursoService.save(estudianteCurso);

        int databaseSizeBeforeDelete = estudianteCursoRepository.findAll().size();

        // Get the estudianteCurso
        restEstudianteCursoMockMvc.perform(delete("/api/estudiante-cursos/{id}", estudianteCurso.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EstudianteCurso> estudianteCursoList = estudianteCursoRepository.findAll();
        assertThat(estudianteCursoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstudianteCurso.class);
        EstudianteCurso estudianteCurso1 = new EstudianteCurso();
        estudianteCurso1.setId(1L);
        EstudianteCurso estudianteCurso2 = new EstudianteCurso();
        estudianteCurso2.setId(estudianteCurso1.getId());
        assertThat(estudianteCurso1).isEqualTo(estudianteCurso2);
        estudianteCurso2.setId(2L);
        assertThat(estudianteCurso1).isNotEqualTo(estudianteCurso2);
        estudianteCurso1.setId(null);
        assertThat(estudianteCurso1).isNotEqualTo(estudianteCurso2);
    }
}
