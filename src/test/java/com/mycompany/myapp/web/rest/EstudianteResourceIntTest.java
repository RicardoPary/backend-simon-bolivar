package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimonBolivarApp;

import com.mycompany.myapp.domain.Estudiante;
import com.mycompany.myapp.repository.EstudianteRepository;
import com.mycompany.myapp.service.EstudianteService;
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
 * Test class for the EstudianteResource REST controller.
 *
 * @see EstudianteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimonBolivarApp.class)
public class EstudianteResourceIntTest {

    private static final Long DEFAULT_CI = 1L;
    private static final Long UPDATED_CI = 2L;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_PATERNO = "AAAAAAAAAA";
    private static final String UPDATED_PATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_MATERNO = "AAAAAAAAAA";
    private static final String UPDATED_MATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_GENERO = "AAAAAAAAAA";
    private static final String UPDATED_GENERO = "BBBBBBBBBB";

    private static final String DEFAULT_FECHA_NACIMIENTO = "AAAAAAAAAA";
    private static final String UPDATED_FECHA_NACIMIENTO = "BBBBBBBBBB";

    private static final String DEFAULT_NACIONALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_NACIONALIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final Long DEFAULT_TELEFONO = 1L;
    private static final Long UPDATED_TELEFONO = 2L;

    private static final String DEFAULT_MATRICULA = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULA = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEstudianteMockMvc;

    private Estudiante estudiante;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstudianteResource estudianteResource = new EstudianteResource(estudianteService);
        this.restEstudianteMockMvc = MockMvcBuilders.standaloneSetup(estudianteResource)
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
    public static Estudiante createEntity(EntityManager em) {
        Estudiante estudiante = new Estudiante()
            .ci(DEFAULT_CI)
            .nombre(DEFAULT_NOMBRE)
            .paterno(DEFAULT_PATERNO)
            .materno(DEFAULT_MATERNO)
            .genero(DEFAULT_GENERO)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .nacionalidad(DEFAULT_NACIONALIDAD)
            .direccion(DEFAULT_DIRECCION)
            .telefono(DEFAULT_TELEFONO)
            .matricula(DEFAULT_MATRICULA)
            .tipo(DEFAULT_TIPO);
        return estudiante;
    }

    @Before
    public void initTest() {
        estudiante = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstudiante() throws Exception {
        int databaseSizeBeforeCreate = estudianteRepository.findAll().size();

        // Create the Estudiante
        restEstudianteMockMvc.perform(post("/api/estudiantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estudiante)))
            .andExpect(status().isCreated());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeCreate + 1);
        Estudiante testEstudiante = estudianteList.get(estudianteList.size() - 1);
        assertThat(testEstudiante.getCi()).isEqualTo(DEFAULT_CI);
        assertThat(testEstudiante.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEstudiante.getPaterno()).isEqualTo(DEFAULT_PATERNO);
        assertThat(testEstudiante.getMaterno()).isEqualTo(DEFAULT_MATERNO);
        assertThat(testEstudiante.getGenero()).isEqualTo(DEFAULT_GENERO);
        assertThat(testEstudiante.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testEstudiante.getNacionalidad()).isEqualTo(DEFAULT_NACIONALIDAD);
        assertThat(testEstudiante.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testEstudiante.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testEstudiante.getMatricula()).isEqualTo(DEFAULT_MATRICULA);
        assertThat(testEstudiante.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void createEstudianteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estudianteRepository.findAll().size();

        // Create the Estudiante with an existing ID
        estudiante.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstudianteMockMvc.perform(post("/api/estudiantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estudiante)))
            .andExpect(status().isBadRequest());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEstudiantes() throws Exception {
        // Initialize the database
        estudianteRepository.saveAndFlush(estudiante);

        // Get all the estudianteList
        restEstudianteMockMvc.perform(get("/api/estudiantes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estudiante.getId().intValue())))
            .andExpect(jsonPath("$.[*].ci").value(hasItem(DEFAULT_CI.intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].paterno").value(hasItem(DEFAULT_PATERNO.toString())))
            .andExpect(jsonPath("$.[*].materno").value(hasItem(DEFAULT_MATERNO.toString())))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO.toString())))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].nacionalidad").value(hasItem(DEFAULT_NACIONALIDAD.toString())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.intValue())))
            .andExpect(jsonPath("$.[*].matricula").value(hasItem(DEFAULT_MATRICULA.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }

    @Test
    @Transactional
    public void getEstudiante() throws Exception {
        // Initialize the database
        estudianteRepository.saveAndFlush(estudiante);

        // Get the estudiante
        restEstudianteMockMvc.perform(get("/api/estudiantes/{id}", estudiante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estudiante.getId().intValue()))
            .andExpect(jsonPath("$.ci").value(DEFAULT_CI.intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.paterno").value(DEFAULT_PATERNO.toString()))
            .andExpect(jsonPath("$.materno").value(DEFAULT_MATERNO.toString()))
            .andExpect(jsonPath("$.genero").value(DEFAULT_GENERO.toString()))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.nacionalidad").value(DEFAULT_NACIONALIDAD.toString()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.intValue()))
            .andExpect(jsonPath("$.matricula").value(DEFAULT_MATRICULA.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEstudiante() throws Exception {
        // Get the estudiante
        restEstudianteMockMvc.perform(get("/api/estudiantes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstudiante() throws Exception {
        // Initialize the database
        estudianteService.save(estudiante);

        int databaseSizeBeforeUpdate = estudianteRepository.findAll().size();

        // Update the estudiante
        Estudiante updatedEstudiante = estudianteRepository.findOne(estudiante.getId());
        // Disconnect from session so that the updates on updatedEstudiante are not directly saved in db
        em.detach(updatedEstudiante);
        updatedEstudiante
            .ci(UPDATED_CI)
            .nombre(UPDATED_NOMBRE)
            .paterno(UPDATED_PATERNO)
            .materno(UPDATED_MATERNO)
            .genero(UPDATED_GENERO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .nacionalidad(UPDATED_NACIONALIDAD)
            .direccion(UPDATED_DIRECCION)
            .telefono(UPDATED_TELEFONO)
            .matricula(UPDATED_MATRICULA)
            .tipo(UPDATED_TIPO);

        restEstudianteMockMvc.perform(put("/api/estudiantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEstudiante)))
            .andExpect(status().isOk());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
        Estudiante testEstudiante = estudianteList.get(estudianteList.size() - 1);
        assertThat(testEstudiante.getCi()).isEqualTo(UPDATED_CI);
        assertThat(testEstudiante.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEstudiante.getPaterno()).isEqualTo(UPDATED_PATERNO);
        assertThat(testEstudiante.getMaterno()).isEqualTo(UPDATED_MATERNO);
        assertThat(testEstudiante.getGenero()).isEqualTo(UPDATED_GENERO);
        assertThat(testEstudiante.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testEstudiante.getNacionalidad()).isEqualTo(UPDATED_NACIONALIDAD);
        assertThat(testEstudiante.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testEstudiante.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testEstudiante.getMatricula()).isEqualTo(UPDATED_MATRICULA);
        assertThat(testEstudiante.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void updateNonExistingEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = estudianteRepository.findAll().size();

        // Create the Estudiante

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEstudianteMockMvc.perform(put("/api/estudiantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estudiante)))
            .andExpect(status().isCreated());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEstudiante() throws Exception {
        // Initialize the database
        estudianteService.save(estudiante);

        int databaseSizeBeforeDelete = estudianteRepository.findAll().size();

        // Get the estudiante
        restEstudianteMockMvc.perform(delete("/api/estudiantes/{id}", estudiante.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Estudiante.class);
        Estudiante estudiante1 = new Estudiante();
        estudiante1.setId(1L);
        Estudiante estudiante2 = new Estudiante();
        estudiante2.setId(estudiante1.getId());
        assertThat(estudiante1).isEqualTo(estudiante2);
        estudiante2.setId(2L);
        assertThat(estudiante1).isNotEqualTo(estudiante2);
        estudiante1.setId(null);
        assertThat(estudiante1).isNotEqualTo(estudiante2);
    }
}
