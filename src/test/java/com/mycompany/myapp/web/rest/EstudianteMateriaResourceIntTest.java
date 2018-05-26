package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimonBolivarApp;

import com.mycompany.myapp.domain.EstudianteMateria;
import com.mycompany.myapp.repository.EstudianteMateriaRepository;
import com.mycompany.myapp.service.EstudianteMateriaService;
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
 * Test class for the EstudianteMateriaResource REST controller.
 *
 * @see EstudianteMateriaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimonBolivarApp.class)
public class EstudianteMateriaResourceIntTest {

    private static final Long DEFAULT_ID_ESTUDIANTE = 1L;
    private static final Long UPDATED_ID_ESTUDIANTE = 2L;

    private static final Long DEFAULT_ID_MATERIA = 1L;
    private static final Long UPDATED_ID_MATERIA = 2L;

    private static final Double DEFAULT_NOTA = 1D;
    private static final Double UPDATED_NOTA = 2D;

    private static final Long DEFAULT_ID_LIBRETA = 1L;
    private static final Long UPDATED_ID_LIBRETA = 2L;

    @Autowired
    private EstudianteMateriaRepository estudianteMateriaRepository;

    @Autowired
    private EstudianteMateriaService estudianteMateriaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEstudianteMateriaMockMvc;

    private EstudianteMateria estudianteMateria;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstudianteMateriaResource estudianteMateriaResource = new EstudianteMateriaResource(estudianteMateriaService);
        this.restEstudianteMateriaMockMvc = MockMvcBuilders.standaloneSetup(estudianteMateriaResource)
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
    public static EstudianteMateria createEntity(EntityManager em) {
        EstudianteMateria estudianteMateria = new EstudianteMateria()
            .idEstudiante(DEFAULT_ID_ESTUDIANTE)
            .idMateria(DEFAULT_ID_MATERIA)
            .nota(DEFAULT_NOTA)
            .idLibreta(DEFAULT_ID_LIBRETA);
        return estudianteMateria;
    }

    @Before
    public void initTest() {
        estudianteMateria = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstudianteMateria() throws Exception {
        int databaseSizeBeforeCreate = estudianteMateriaRepository.findAll().size();

        // Create the EstudianteMateria
        restEstudianteMateriaMockMvc.perform(post("/api/estudiante-materias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estudianteMateria)))
            .andExpect(status().isCreated());

        // Validate the EstudianteMateria in the database
        List<EstudianteMateria> estudianteMateriaList = estudianteMateriaRepository.findAll();
        assertThat(estudianteMateriaList).hasSize(databaseSizeBeforeCreate + 1);
        EstudianteMateria testEstudianteMateria = estudianteMateriaList.get(estudianteMateriaList.size() - 1);
        assertThat(testEstudianteMateria.getIdEstudiante()).isEqualTo(DEFAULT_ID_ESTUDIANTE);
        assertThat(testEstudianteMateria.getIdMateria()).isEqualTo(DEFAULT_ID_MATERIA);
        assertThat(testEstudianteMateria.getNota()).isEqualTo(DEFAULT_NOTA);
        assertThat(testEstudianteMateria.getIdLibreta()).isEqualTo(DEFAULT_ID_LIBRETA);
    }

    @Test
    @Transactional
    public void createEstudianteMateriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estudianteMateriaRepository.findAll().size();

        // Create the EstudianteMateria with an existing ID
        estudianteMateria.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstudianteMateriaMockMvc.perform(post("/api/estudiante-materias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estudianteMateria)))
            .andExpect(status().isBadRequest());

        // Validate the EstudianteMateria in the database
        List<EstudianteMateria> estudianteMateriaList = estudianteMateriaRepository.findAll();
        assertThat(estudianteMateriaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEstudianteMaterias() throws Exception {
        // Initialize the database
        estudianteMateriaRepository.saveAndFlush(estudianteMateria);

        // Get all the estudianteMateriaList
        restEstudianteMateriaMockMvc.perform(get("/api/estudiante-materias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estudianteMateria.getId().intValue())))
            .andExpect(jsonPath("$.[*].idEstudiante").value(hasItem(DEFAULT_ID_ESTUDIANTE.intValue())))
            .andExpect(jsonPath("$.[*].idMateria").value(hasItem(DEFAULT_ID_MATERIA.intValue())))
            .andExpect(jsonPath("$.[*].nota").value(hasItem(DEFAULT_NOTA.doubleValue())))
            .andExpect(jsonPath("$.[*].idLibreta").value(hasItem(DEFAULT_ID_LIBRETA.intValue())));
    }

    @Test
    @Transactional
    public void getEstudianteMateria() throws Exception {
        // Initialize the database
        estudianteMateriaRepository.saveAndFlush(estudianteMateria);

        // Get the estudianteMateria
        restEstudianteMateriaMockMvc.perform(get("/api/estudiante-materias/{id}", estudianteMateria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estudianteMateria.getId().intValue()))
            .andExpect(jsonPath("$.idEstudiante").value(DEFAULT_ID_ESTUDIANTE.intValue()))
            .andExpect(jsonPath("$.idMateria").value(DEFAULT_ID_MATERIA.intValue()))
            .andExpect(jsonPath("$.nota").value(DEFAULT_NOTA.doubleValue()))
            .andExpect(jsonPath("$.idLibreta").value(DEFAULT_ID_LIBRETA.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEstudianteMateria() throws Exception {
        // Get the estudianteMateria
        restEstudianteMateriaMockMvc.perform(get("/api/estudiante-materias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstudianteMateria() throws Exception {
        // Initialize the database
        estudianteMateriaService.save(estudianteMateria);

        int databaseSizeBeforeUpdate = estudianteMateriaRepository.findAll().size();

        // Update the estudianteMateria
        EstudianteMateria updatedEstudianteMateria = estudianteMateriaRepository.findOne(estudianteMateria.getId());
        // Disconnect from session so that the updates on updatedEstudianteMateria are not directly saved in db
        em.detach(updatedEstudianteMateria);
        updatedEstudianteMateria
            .idEstudiante(UPDATED_ID_ESTUDIANTE)
            .idMateria(UPDATED_ID_MATERIA)
            .nota(UPDATED_NOTA)
            .idLibreta(UPDATED_ID_LIBRETA);

        restEstudianteMateriaMockMvc.perform(put("/api/estudiante-materias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEstudianteMateria)))
            .andExpect(status().isOk());

        // Validate the EstudianteMateria in the database
        List<EstudianteMateria> estudianteMateriaList = estudianteMateriaRepository.findAll();
        assertThat(estudianteMateriaList).hasSize(databaseSizeBeforeUpdate);
        EstudianteMateria testEstudianteMateria = estudianteMateriaList.get(estudianteMateriaList.size() - 1);
        assertThat(testEstudianteMateria.getIdEstudiante()).isEqualTo(UPDATED_ID_ESTUDIANTE);
        assertThat(testEstudianteMateria.getIdMateria()).isEqualTo(UPDATED_ID_MATERIA);
        assertThat(testEstudianteMateria.getNota()).isEqualTo(UPDATED_NOTA);
        assertThat(testEstudianteMateria.getIdLibreta()).isEqualTo(UPDATED_ID_LIBRETA);
    }

    @Test
    @Transactional
    public void updateNonExistingEstudianteMateria() throws Exception {
        int databaseSizeBeforeUpdate = estudianteMateriaRepository.findAll().size();

        // Create the EstudianteMateria

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEstudianteMateriaMockMvc.perform(put("/api/estudiante-materias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estudianteMateria)))
            .andExpect(status().isCreated());

        // Validate the EstudianteMateria in the database
        List<EstudianteMateria> estudianteMateriaList = estudianteMateriaRepository.findAll();
        assertThat(estudianteMateriaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEstudianteMateria() throws Exception {
        // Initialize the database
        estudianteMateriaService.save(estudianteMateria);

        int databaseSizeBeforeDelete = estudianteMateriaRepository.findAll().size();

        // Get the estudianteMateria
        restEstudianteMateriaMockMvc.perform(delete("/api/estudiante-materias/{id}", estudianteMateria.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EstudianteMateria> estudianteMateriaList = estudianteMateriaRepository.findAll();
        assertThat(estudianteMateriaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstudianteMateria.class);
        EstudianteMateria estudianteMateria1 = new EstudianteMateria();
        estudianteMateria1.setId(1L);
        EstudianteMateria estudianteMateria2 = new EstudianteMateria();
        estudianteMateria2.setId(estudianteMateria1.getId());
        assertThat(estudianteMateria1).isEqualTo(estudianteMateria2);
        estudianteMateria2.setId(2L);
        assertThat(estudianteMateria1).isNotEqualTo(estudianteMateria2);
        estudianteMateria1.setId(null);
        assertThat(estudianteMateria1).isNotEqualTo(estudianteMateria2);
    }
}
