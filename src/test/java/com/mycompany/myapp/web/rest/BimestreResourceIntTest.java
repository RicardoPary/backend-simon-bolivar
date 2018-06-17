package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimonBolivarApp;

import com.mycompany.myapp.domain.Bimestre;
import com.mycompany.myapp.domain.Estudiante;
import com.mycompany.myapp.repository.BimestreRepository;
import com.mycompany.myapp.service.BimestreService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.BimestreCriteria;
import com.mycompany.myapp.service.BimestreQueryService;

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
 * Test class for the BimestreResource REST controller.
 *
 * @see BimestreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimonBolivarApp.class)
public class BimestreResourceIntTest {

    private static final Long DEFAULT_ID_CURSO = 1L;
    private static final Long UPDATED_ID_CURSO = 2L;

    private static final Long DEFAULT_ID_DOCENTE = 1L;
    private static final Long UPDATED_ID_DOCENTE = 2L;

    private static final Integer DEFAULT_BIMESTRE = 1;
    private static final Integer UPDATED_BIMESTRE = 2;

    private static final String DEFAULT_PARALELO = "AAAAAAAAAA";
    private static final String UPDATED_PARALELO = "BBBBBBBBBB";

    private static final Integer DEFAULT_GESTION = 1;
    private static final Integer UPDATED_GESTION = 2;

    private static final Long DEFAULT_ID_MATERIA = 1L;
    private static final Long UPDATED_ID_MATERIA = 2L;

    private static final String DEFAULT_OBSERVACION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACION = "BBBBBBBBBB";

    private static final String DEFAULT_INDICADOR_CUALITATIVO = "AAAAAAAAAA";
    private static final String UPDATED_INDICADOR_CUALITATIVO = "BBBBBBBBBB";

    private static final Double DEFAULT_NOTA_BIMESTRAL_FINAL = 1D;
    private static final Double UPDATED_NOTA_BIMESTRAL_FINAL = 2D;

    private static final Double DEFAULT_PROMEDIO_AUTOEVALUACION = 1D;
    private static final Double UPDATED_PROMEDIO_AUTOEVALUACION = 2D;

    private static final Double DEFAULT_AUTOEVALUACION_DECIR = 1D;
    private static final Double UPDATED_AUTOEVALUACION_DECIR = 2D;

    private static final Double DEFAULT_AUTOEVALUACION_SER = 1D;
    private static final Double UPDATED_AUTOEVALUACION_SER = 2D;

    private static final Double DEFAULT_DECIR_PROMEDIO = 1D;
    private static final Double UPDATED_DECIR_PROMEDIO = 2D;

    private static final Double DEFAULT_HACER_PROMEDIO = 1D;
    private static final Double UPDATED_HACER_PROMEDIO = 2D;

    private static final Double DEFAULT_SABER_PROMEDIO = 1D;
    private static final Double UPDATED_SABER_PROMEDIO = 2D;

    private static final Double DEFAULT_SER_PROMEDIO = 1D;
    private static final Double UPDATED_SER_PROMEDIO = 2D;

    private static final Integer DEFAULT_NOTA_SER_1 = 1;
    private static final Integer UPDATED_NOTA_SER_1 = 2;

    private static final Integer DEFAULT_NOTA_SER_2 = 1;
    private static final Integer UPDATED_NOTA_SER_2 = 2;

    private static final Integer DEFAULT_NOTA_SER_3 = 1;
    private static final Integer UPDATED_NOTA_SER_3 = 2;

    private static final Integer DEFAULT_NOTA_SER_4 = 1;
    private static final Integer UPDATED_NOTA_SER_4 = 2;

    private static final Integer DEFAULT_NOTA_SER_5 = 1;
    private static final Integer UPDATED_NOTA_SER_5 = 2;

    private static final Integer DEFAULT_NOTA_SER_6 = 1;
    private static final Integer UPDATED_NOTA_SER_6 = 2;

    private static final Integer DEFAULT_NOTA_SABER_1 = 1;
    private static final Integer UPDATED_NOTA_SABER_1 = 2;

    private static final Integer DEFAULT_NOTA_SABER_2 = 1;
    private static final Integer UPDATED_NOTA_SABER_2 = 2;

    private static final Integer DEFAULT_NOTA_SABER_3 = 1;
    private static final Integer UPDATED_NOTA_SABER_3 = 2;

    private static final Integer DEFAULT_NOTA_SABER_4 = 1;
    private static final Integer UPDATED_NOTA_SABER_4 = 2;

    private static final Integer DEFAULT_NOTA_SABER_5 = 1;
    private static final Integer UPDATED_NOTA_SABER_5 = 2;

    private static final Integer DEFAULT_NOTA_SABER_6 = 1;
    private static final Integer UPDATED_NOTA_SABER_6 = 2;

    private static final Integer DEFAULT_NOTA_HACER_1 = 1;
    private static final Integer UPDATED_NOTA_HACER_1 = 2;

    private static final Integer DEFAULT_NOTA_HACER_2 = 1;
    private static final Integer UPDATED_NOTA_HACER_2 = 2;

    private static final Integer DEFAULT_NOTA_HACER_3 = 1;
    private static final Integer UPDATED_NOTA_HACER_3 = 2;

    private static final Integer DEFAULT_NOTA_HACER_4 = 1;
    private static final Integer UPDATED_NOTA_HACER_4 = 2;

    private static final Integer DEFAULT_NOTA_HACER_5 = 1;
    private static final Integer UPDATED_NOTA_HACER_5 = 2;

    private static final Integer DEFAULT_NOTA_HACER_6 = 1;
    private static final Integer UPDATED_NOTA_HACER_6 = 2;

    private static final Integer DEFAULT_NOTA_DECIR_1 = 1;
    private static final Integer UPDATED_NOTA_DECIR_1 = 2;

    private static final Integer DEFAULT_NOTA_DECIR_2 = 1;
    private static final Integer UPDATED_NOTA_DECIR_2 = 2;

    private static final Integer DEFAULT_NOTA_DECIR_3 = 1;
    private static final Integer UPDATED_NOTA_DECIR_3 = 2;

    private static final Integer DEFAULT_NOTA_DECIR_4 = 1;
    private static final Integer UPDATED_NOTA_DECIR_4 = 2;

    private static final Integer DEFAULT_NOTA_DECIR_5 = 1;
    private static final Integer UPDATED_NOTA_DECIR_5 = 2;

    private static final Integer DEFAULT_NOTA_DECIR_6 = 1;
    private static final Integer UPDATED_NOTA_DECIR_6 = 2;

    @Autowired
    private BimestreRepository bimestreRepository;

    @Autowired
    private BimestreService bimestreService;

    @Autowired
    private BimestreQueryService bimestreQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBimestreMockMvc;

    private Bimestre bimestre;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BimestreResource bimestreResource = new BimestreResource(bimestreService, bimestreQueryService);
        this.restBimestreMockMvc = MockMvcBuilders.standaloneSetup(bimestreResource)
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
    public static Bimestre createEntity(EntityManager em) {
        Bimestre bimestre = new Bimestre()
            .idCurso(DEFAULT_ID_CURSO)
            .idDocente(DEFAULT_ID_DOCENTE)
            .bimestre(DEFAULT_BIMESTRE)
            .paralelo(DEFAULT_PARALELO)
            .gestion(DEFAULT_GESTION)
            .idMateria(DEFAULT_ID_MATERIA)
            .observacion(DEFAULT_OBSERVACION)
            .indicadorCualitativo(DEFAULT_INDICADOR_CUALITATIVO)
            .notaBimestralFinal(DEFAULT_NOTA_BIMESTRAL_FINAL)
            .promedioAutoevaluacion(DEFAULT_PROMEDIO_AUTOEVALUACION)
            .autoevaluacionDecir(DEFAULT_AUTOEVALUACION_DECIR)
            .autoevaluacionSer(DEFAULT_AUTOEVALUACION_SER)
            .decirPromedio(DEFAULT_DECIR_PROMEDIO)
            .hacerPromedio(DEFAULT_HACER_PROMEDIO)
            .saberPromedio(DEFAULT_SABER_PROMEDIO)
            .serPromedio(DEFAULT_SER_PROMEDIO)
            .notaSer1(DEFAULT_NOTA_SER_1)
            .notaSer2(DEFAULT_NOTA_SER_2)
            .notaSer3(DEFAULT_NOTA_SER_3)
            .notaSer4(DEFAULT_NOTA_SER_4)
            .notaSer5(DEFAULT_NOTA_SER_5)
            .notaSer6(DEFAULT_NOTA_SER_6)
            .notaSaber1(DEFAULT_NOTA_SABER_1)
            .notaSaber2(DEFAULT_NOTA_SABER_2)
            .notaSaber3(DEFAULT_NOTA_SABER_3)
            .notaSaber4(DEFAULT_NOTA_SABER_4)
            .notaSaber5(DEFAULT_NOTA_SABER_5)
            .notaSaber6(DEFAULT_NOTA_SABER_6)
            .notaHacer1(DEFAULT_NOTA_HACER_1)
            .notaHacer2(DEFAULT_NOTA_HACER_2)
            .notaHacer3(DEFAULT_NOTA_HACER_3)
            .notaHacer4(DEFAULT_NOTA_HACER_4)
            .notaHacer5(DEFAULT_NOTA_HACER_5)
            .notaHacer6(DEFAULT_NOTA_HACER_6)
            .notaDecir1(DEFAULT_NOTA_DECIR_1)
            .notaDecir2(DEFAULT_NOTA_DECIR_2)
            .notaDecir3(DEFAULT_NOTA_DECIR_3)
            .notaDecir4(DEFAULT_NOTA_DECIR_4)
            .notaDecir5(DEFAULT_NOTA_DECIR_5)
            .notaDecir6(DEFAULT_NOTA_DECIR_6);
        return bimestre;
    }

    @Before
    public void initTest() {
        bimestre = createEntity(em);
    }

    @Test
    @Transactional
    public void createBimestre() throws Exception {
        int databaseSizeBeforeCreate = bimestreRepository.findAll().size();

        // Create the Bimestre
        restBimestreMockMvc.perform(post("/api/bimestres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bimestre)))
            .andExpect(status().isCreated());

        // Validate the Bimestre in the database
        List<Bimestre> bimestreList = bimestreRepository.findAll();
        assertThat(bimestreList).hasSize(databaseSizeBeforeCreate + 1);
        Bimestre testBimestre = bimestreList.get(bimestreList.size() - 1);
        assertThat(testBimestre.getIdCurso()).isEqualTo(DEFAULT_ID_CURSO);
        assertThat(testBimestre.getIdDocente()).isEqualTo(DEFAULT_ID_DOCENTE);
        assertThat(testBimestre.getBimestre()).isEqualTo(DEFAULT_BIMESTRE);
        assertThat(testBimestre.getParalelo()).isEqualTo(DEFAULT_PARALELO);
        assertThat(testBimestre.getGestion()).isEqualTo(DEFAULT_GESTION);
        assertThat(testBimestre.getIdMateria()).isEqualTo(DEFAULT_ID_MATERIA);
        assertThat(testBimestre.getObservacion()).isEqualTo(DEFAULT_OBSERVACION);
        assertThat(testBimestre.getIndicadorCualitativo()).isEqualTo(DEFAULT_INDICADOR_CUALITATIVO);
        assertThat(testBimestre.getNotaBimestralFinal()).isEqualTo(DEFAULT_NOTA_BIMESTRAL_FINAL);
        assertThat(testBimestre.getPromedioAutoevaluacion()).isEqualTo(DEFAULT_PROMEDIO_AUTOEVALUACION);
        assertThat(testBimestre.getAutoevaluacionDecir()).isEqualTo(DEFAULT_AUTOEVALUACION_DECIR);
        assertThat(testBimestre.getAutoevaluacionSer()).isEqualTo(DEFAULT_AUTOEVALUACION_SER);
        assertThat(testBimestre.getDecirPromedio()).isEqualTo(DEFAULT_DECIR_PROMEDIO);
        assertThat(testBimestre.getHacerPromedio()).isEqualTo(DEFAULT_HACER_PROMEDIO);
        assertThat(testBimestre.getSaberPromedio()).isEqualTo(DEFAULT_SABER_PROMEDIO);
        assertThat(testBimestre.getSerPromedio()).isEqualTo(DEFAULT_SER_PROMEDIO);
        assertThat(testBimestre.getNotaSer1()).isEqualTo(DEFAULT_NOTA_SER_1);
        assertThat(testBimestre.getNotaSer2()).isEqualTo(DEFAULT_NOTA_SER_2);
        assertThat(testBimestre.getNotaSer3()).isEqualTo(DEFAULT_NOTA_SER_3);
        assertThat(testBimestre.getNotaSer4()).isEqualTo(DEFAULT_NOTA_SER_4);
        assertThat(testBimestre.getNotaSer5()).isEqualTo(DEFAULT_NOTA_SER_5);
        assertThat(testBimestre.getNotaSer6()).isEqualTo(DEFAULT_NOTA_SER_6);
        assertThat(testBimestre.getNotaSaber1()).isEqualTo(DEFAULT_NOTA_SABER_1);
        assertThat(testBimestre.getNotaSaber2()).isEqualTo(DEFAULT_NOTA_SABER_2);
        assertThat(testBimestre.getNotaSaber3()).isEqualTo(DEFAULT_NOTA_SABER_3);
        assertThat(testBimestre.getNotaSaber4()).isEqualTo(DEFAULT_NOTA_SABER_4);
        assertThat(testBimestre.getNotaSaber5()).isEqualTo(DEFAULT_NOTA_SABER_5);
        assertThat(testBimestre.getNotaSaber6()).isEqualTo(DEFAULT_NOTA_SABER_6);
        assertThat(testBimestre.getNotaHacer1()).isEqualTo(DEFAULT_NOTA_HACER_1);
        assertThat(testBimestre.getNotaHacer2()).isEqualTo(DEFAULT_NOTA_HACER_2);
        assertThat(testBimestre.getNotaHacer3()).isEqualTo(DEFAULT_NOTA_HACER_3);
        assertThat(testBimestre.getNotaHacer4()).isEqualTo(DEFAULT_NOTA_HACER_4);
        assertThat(testBimestre.getNotaHacer5()).isEqualTo(DEFAULT_NOTA_HACER_5);
        assertThat(testBimestre.getNotaHacer6()).isEqualTo(DEFAULT_NOTA_HACER_6);
        assertThat(testBimestre.getNotaDecir1()).isEqualTo(DEFAULT_NOTA_DECIR_1);
        assertThat(testBimestre.getNotaDecir2()).isEqualTo(DEFAULT_NOTA_DECIR_2);
        assertThat(testBimestre.getNotaDecir3()).isEqualTo(DEFAULT_NOTA_DECIR_3);
        assertThat(testBimestre.getNotaDecir4()).isEqualTo(DEFAULT_NOTA_DECIR_4);
        assertThat(testBimestre.getNotaDecir5()).isEqualTo(DEFAULT_NOTA_DECIR_5);
        assertThat(testBimestre.getNotaDecir6()).isEqualTo(DEFAULT_NOTA_DECIR_6);
    }

    @Test
    @Transactional
    public void createBimestreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bimestreRepository.findAll().size();

        // Create the Bimestre with an existing ID
        bimestre.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBimestreMockMvc.perform(post("/api/bimestres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bimestre)))
            .andExpect(status().isBadRequest());

        // Validate the Bimestre in the database
        List<Bimestre> bimestreList = bimestreRepository.findAll();
        assertThat(bimestreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBimestres() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList
        restBimestreMockMvc.perform(get("/api/bimestres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bimestre.getId().intValue())))
            .andExpect(jsonPath("$.[*].idCurso").value(hasItem(DEFAULT_ID_CURSO.intValue())))
            .andExpect(jsonPath("$.[*].idDocente").value(hasItem(DEFAULT_ID_DOCENTE.intValue())))
            .andExpect(jsonPath("$.[*].bimestre").value(hasItem(DEFAULT_BIMESTRE)))
            .andExpect(jsonPath("$.[*].paralelo").value(hasItem(DEFAULT_PARALELO.toString())))
            .andExpect(jsonPath("$.[*].gestion").value(hasItem(DEFAULT_GESTION)))
            .andExpect(jsonPath("$.[*].idMateria").value(hasItem(DEFAULT_ID_MATERIA.intValue())))
            .andExpect(jsonPath("$.[*].observacion").value(hasItem(DEFAULT_OBSERVACION.toString())))
            .andExpect(jsonPath("$.[*].indicadorCualitativo").value(hasItem(DEFAULT_INDICADOR_CUALITATIVO.toString())))
            .andExpect(jsonPath("$.[*].notaBimestralFinal").value(hasItem(DEFAULT_NOTA_BIMESTRAL_FINAL.doubleValue())))
            .andExpect(jsonPath("$.[*].promedioAutoevaluacion").value(hasItem(DEFAULT_PROMEDIO_AUTOEVALUACION.doubleValue())))
            .andExpect(jsonPath("$.[*].autoevaluacionDecir").value(hasItem(DEFAULT_AUTOEVALUACION_DECIR.doubleValue())))
            .andExpect(jsonPath("$.[*].autoevaluacionSer").value(hasItem(DEFAULT_AUTOEVALUACION_SER.doubleValue())))
            .andExpect(jsonPath("$.[*].decirPromedio").value(hasItem(DEFAULT_DECIR_PROMEDIO.doubleValue())))
            .andExpect(jsonPath("$.[*].hacerPromedio").value(hasItem(DEFAULT_HACER_PROMEDIO.doubleValue())))
            .andExpect(jsonPath("$.[*].saberPromedio").value(hasItem(DEFAULT_SABER_PROMEDIO.doubleValue())))
            .andExpect(jsonPath("$.[*].serPromedio").value(hasItem(DEFAULT_SER_PROMEDIO.doubleValue())))
            .andExpect(jsonPath("$.[*].notaSer1").value(hasItem(DEFAULT_NOTA_SER_1)))
            .andExpect(jsonPath("$.[*].notaSer2").value(hasItem(DEFAULT_NOTA_SER_2)))
            .andExpect(jsonPath("$.[*].notaSer3").value(hasItem(DEFAULT_NOTA_SER_3)))
            .andExpect(jsonPath("$.[*].notaSer4").value(hasItem(DEFAULT_NOTA_SER_4)))
            .andExpect(jsonPath("$.[*].notaSer5").value(hasItem(DEFAULT_NOTA_SER_5)))
            .andExpect(jsonPath("$.[*].notaSer6").value(hasItem(DEFAULT_NOTA_SER_6)))
            .andExpect(jsonPath("$.[*].notaSaber1").value(hasItem(DEFAULT_NOTA_SABER_1)))
            .andExpect(jsonPath("$.[*].notaSaber2").value(hasItem(DEFAULT_NOTA_SABER_2)))
            .andExpect(jsonPath("$.[*].notaSaber3").value(hasItem(DEFAULT_NOTA_SABER_3)))
            .andExpect(jsonPath("$.[*].notaSaber4").value(hasItem(DEFAULT_NOTA_SABER_4)))
            .andExpect(jsonPath("$.[*].notaSaber5").value(hasItem(DEFAULT_NOTA_SABER_5)))
            .andExpect(jsonPath("$.[*].notaSaber6").value(hasItem(DEFAULT_NOTA_SABER_6)))
            .andExpect(jsonPath("$.[*].notaHacer1").value(hasItem(DEFAULT_NOTA_HACER_1)))
            .andExpect(jsonPath("$.[*].notaHacer2").value(hasItem(DEFAULT_NOTA_HACER_2)))
            .andExpect(jsonPath("$.[*].notaHacer3").value(hasItem(DEFAULT_NOTA_HACER_3)))
            .andExpect(jsonPath("$.[*].notaHacer4").value(hasItem(DEFAULT_NOTA_HACER_4)))
            .andExpect(jsonPath("$.[*].notaHacer5").value(hasItem(DEFAULT_NOTA_HACER_5)))
            .andExpect(jsonPath("$.[*].notaHacer6").value(hasItem(DEFAULT_NOTA_HACER_6)))
            .andExpect(jsonPath("$.[*].notaDecir1").value(hasItem(DEFAULT_NOTA_DECIR_1)))
            .andExpect(jsonPath("$.[*].notaDecir2").value(hasItem(DEFAULT_NOTA_DECIR_2)))
            .andExpect(jsonPath("$.[*].notaDecir3").value(hasItem(DEFAULT_NOTA_DECIR_3)))
            .andExpect(jsonPath("$.[*].notaDecir4").value(hasItem(DEFAULT_NOTA_DECIR_4)))
            .andExpect(jsonPath("$.[*].notaDecir5").value(hasItem(DEFAULT_NOTA_DECIR_5)))
            .andExpect(jsonPath("$.[*].notaDecir6").value(hasItem(DEFAULT_NOTA_DECIR_6)));
    }

    @Test
    @Transactional
    public void getBimestre() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get the bimestre
        restBimestreMockMvc.perform(get("/api/bimestres/{id}", bimestre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bimestre.getId().intValue()))
            .andExpect(jsonPath("$.idCurso").value(DEFAULT_ID_CURSO.intValue()))
            .andExpect(jsonPath("$.idDocente").value(DEFAULT_ID_DOCENTE.intValue()))
            .andExpect(jsonPath("$.bimestre").value(DEFAULT_BIMESTRE))
            .andExpect(jsonPath("$.paralelo").value(DEFAULT_PARALELO.toString()))
            .andExpect(jsonPath("$.gestion").value(DEFAULT_GESTION))
            .andExpect(jsonPath("$.idMateria").value(DEFAULT_ID_MATERIA.intValue()))
            .andExpect(jsonPath("$.observacion").value(DEFAULT_OBSERVACION.toString()))
            .andExpect(jsonPath("$.indicadorCualitativo").value(DEFAULT_INDICADOR_CUALITATIVO.toString()))
            .andExpect(jsonPath("$.notaBimestralFinal").value(DEFAULT_NOTA_BIMESTRAL_FINAL.doubleValue()))
            .andExpect(jsonPath("$.promedioAutoevaluacion").value(DEFAULT_PROMEDIO_AUTOEVALUACION.doubleValue()))
            .andExpect(jsonPath("$.autoevaluacionDecir").value(DEFAULT_AUTOEVALUACION_DECIR.doubleValue()))
            .andExpect(jsonPath("$.autoevaluacionSer").value(DEFAULT_AUTOEVALUACION_SER.doubleValue()))
            .andExpect(jsonPath("$.decirPromedio").value(DEFAULT_DECIR_PROMEDIO.doubleValue()))
            .andExpect(jsonPath("$.hacerPromedio").value(DEFAULT_HACER_PROMEDIO.doubleValue()))
            .andExpect(jsonPath("$.saberPromedio").value(DEFAULT_SABER_PROMEDIO.doubleValue()))
            .andExpect(jsonPath("$.serPromedio").value(DEFAULT_SER_PROMEDIO.doubleValue()))
            .andExpect(jsonPath("$.notaSer1").value(DEFAULT_NOTA_SER_1))
            .andExpect(jsonPath("$.notaSer2").value(DEFAULT_NOTA_SER_2))
            .andExpect(jsonPath("$.notaSer3").value(DEFAULT_NOTA_SER_3))
            .andExpect(jsonPath("$.notaSer4").value(DEFAULT_NOTA_SER_4))
            .andExpect(jsonPath("$.notaSer5").value(DEFAULT_NOTA_SER_5))
            .andExpect(jsonPath("$.notaSer6").value(DEFAULT_NOTA_SER_6))
            .andExpect(jsonPath("$.notaSaber1").value(DEFAULT_NOTA_SABER_1))
            .andExpect(jsonPath("$.notaSaber2").value(DEFAULT_NOTA_SABER_2))
            .andExpect(jsonPath("$.notaSaber3").value(DEFAULT_NOTA_SABER_3))
            .andExpect(jsonPath("$.notaSaber4").value(DEFAULT_NOTA_SABER_4))
            .andExpect(jsonPath("$.notaSaber5").value(DEFAULT_NOTA_SABER_5))
            .andExpect(jsonPath("$.notaSaber6").value(DEFAULT_NOTA_SABER_6))
            .andExpect(jsonPath("$.notaHacer1").value(DEFAULT_NOTA_HACER_1))
            .andExpect(jsonPath("$.notaHacer2").value(DEFAULT_NOTA_HACER_2))
            .andExpect(jsonPath("$.notaHacer3").value(DEFAULT_NOTA_HACER_3))
            .andExpect(jsonPath("$.notaHacer4").value(DEFAULT_NOTA_HACER_4))
            .andExpect(jsonPath("$.notaHacer5").value(DEFAULT_NOTA_HACER_5))
            .andExpect(jsonPath("$.notaHacer6").value(DEFAULT_NOTA_HACER_6))
            .andExpect(jsonPath("$.notaDecir1").value(DEFAULT_NOTA_DECIR_1))
            .andExpect(jsonPath("$.notaDecir2").value(DEFAULT_NOTA_DECIR_2))
            .andExpect(jsonPath("$.notaDecir3").value(DEFAULT_NOTA_DECIR_3))
            .andExpect(jsonPath("$.notaDecir4").value(DEFAULT_NOTA_DECIR_4))
            .andExpect(jsonPath("$.notaDecir5").value(DEFAULT_NOTA_DECIR_5))
            .andExpect(jsonPath("$.notaDecir6").value(DEFAULT_NOTA_DECIR_6));
    }

    @Test
    @Transactional
    public void getAllBimestresByIdCursoIsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where idCurso equals to DEFAULT_ID_CURSO
        defaultBimestreShouldBeFound("idCurso.equals=" + DEFAULT_ID_CURSO);

        // Get all the bimestreList where idCurso equals to UPDATED_ID_CURSO
        defaultBimestreShouldNotBeFound("idCurso.equals=" + UPDATED_ID_CURSO);
    }

    @Test
    @Transactional
    public void getAllBimestresByIdCursoIsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where idCurso in DEFAULT_ID_CURSO or UPDATED_ID_CURSO
        defaultBimestreShouldBeFound("idCurso.in=" + DEFAULT_ID_CURSO + "," + UPDATED_ID_CURSO);

        // Get all the bimestreList where idCurso equals to UPDATED_ID_CURSO
        defaultBimestreShouldNotBeFound("idCurso.in=" + UPDATED_ID_CURSO);
    }

    @Test
    @Transactional
    public void getAllBimestresByIdCursoIsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where idCurso is not null
        defaultBimestreShouldBeFound("idCurso.specified=true");

        // Get all the bimestreList where idCurso is null
        defaultBimestreShouldNotBeFound("idCurso.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByIdCursoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where idCurso greater than or equals to DEFAULT_ID_CURSO
        defaultBimestreShouldBeFound("idCurso.greaterOrEqualThan=" + DEFAULT_ID_CURSO);

        // Get all the bimestreList where idCurso greater than or equals to UPDATED_ID_CURSO
        defaultBimestreShouldNotBeFound("idCurso.greaterOrEqualThan=" + UPDATED_ID_CURSO);
    }

    @Test
    @Transactional
    public void getAllBimestresByIdCursoIsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where idCurso less than or equals to DEFAULT_ID_CURSO
        defaultBimestreShouldNotBeFound("idCurso.lessThan=" + DEFAULT_ID_CURSO);

        // Get all the bimestreList where idCurso less than or equals to UPDATED_ID_CURSO
        defaultBimestreShouldBeFound("idCurso.lessThan=" + UPDATED_ID_CURSO);
    }


    @Test
    @Transactional
    public void getAllBimestresByIdDocenteIsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where idDocente equals to DEFAULT_ID_DOCENTE
        defaultBimestreShouldBeFound("idDocente.equals=" + DEFAULT_ID_DOCENTE);

        // Get all the bimestreList where idDocente equals to UPDATED_ID_DOCENTE
        defaultBimestreShouldNotBeFound("idDocente.equals=" + UPDATED_ID_DOCENTE);
    }

    @Test
    @Transactional
    public void getAllBimestresByIdDocenteIsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where idDocente in DEFAULT_ID_DOCENTE or UPDATED_ID_DOCENTE
        defaultBimestreShouldBeFound("idDocente.in=" + DEFAULT_ID_DOCENTE + "," + UPDATED_ID_DOCENTE);

        // Get all the bimestreList where idDocente equals to UPDATED_ID_DOCENTE
        defaultBimestreShouldNotBeFound("idDocente.in=" + UPDATED_ID_DOCENTE);
    }

    @Test
    @Transactional
    public void getAllBimestresByIdDocenteIsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where idDocente is not null
        defaultBimestreShouldBeFound("idDocente.specified=true");

        // Get all the bimestreList where idDocente is null
        defaultBimestreShouldNotBeFound("idDocente.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByIdDocenteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where idDocente greater than or equals to DEFAULT_ID_DOCENTE
        defaultBimestreShouldBeFound("idDocente.greaterOrEqualThan=" + DEFAULT_ID_DOCENTE);

        // Get all the bimestreList where idDocente greater than or equals to UPDATED_ID_DOCENTE
        defaultBimestreShouldNotBeFound("idDocente.greaterOrEqualThan=" + UPDATED_ID_DOCENTE);
    }

    @Test
    @Transactional
    public void getAllBimestresByIdDocenteIsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where idDocente less than or equals to DEFAULT_ID_DOCENTE
        defaultBimestreShouldNotBeFound("idDocente.lessThan=" + DEFAULT_ID_DOCENTE);

        // Get all the bimestreList where idDocente less than or equals to UPDATED_ID_DOCENTE
        defaultBimestreShouldBeFound("idDocente.lessThan=" + UPDATED_ID_DOCENTE);
    }


    @Test
    @Transactional
    public void getAllBimestresByBimestreIsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where bimestre equals to DEFAULT_BIMESTRE
        defaultBimestreShouldBeFound("bimestre.equals=" + DEFAULT_BIMESTRE);

        // Get all the bimestreList where bimestre equals to UPDATED_BIMESTRE
        defaultBimestreShouldNotBeFound("bimestre.equals=" + UPDATED_BIMESTRE);
    }

    @Test
    @Transactional
    public void getAllBimestresByBimestreIsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where bimestre in DEFAULT_BIMESTRE or UPDATED_BIMESTRE
        defaultBimestreShouldBeFound("bimestre.in=" + DEFAULT_BIMESTRE + "," + UPDATED_BIMESTRE);

        // Get all the bimestreList where bimestre equals to UPDATED_BIMESTRE
        defaultBimestreShouldNotBeFound("bimestre.in=" + UPDATED_BIMESTRE);
    }

    @Test
    @Transactional
    public void getAllBimestresByBimestreIsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where bimestre is not null
        defaultBimestreShouldBeFound("bimestre.specified=true");

        // Get all the bimestreList where bimestre is null
        defaultBimestreShouldNotBeFound("bimestre.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByBimestreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where bimestre greater than or equals to DEFAULT_BIMESTRE
        defaultBimestreShouldBeFound("bimestre.greaterOrEqualThan=" + DEFAULT_BIMESTRE);

        // Get all the bimestreList where bimestre greater than or equals to UPDATED_BIMESTRE
        defaultBimestreShouldNotBeFound("bimestre.greaterOrEqualThan=" + UPDATED_BIMESTRE);
    }

    @Test
    @Transactional
    public void getAllBimestresByBimestreIsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where bimestre less than or equals to DEFAULT_BIMESTRE
        defaultBimestreShouldNotBeFound("bimestre.lessThan=" + DEFAULT_BIMESTRE);

        // Get all the bimestreList where bimestre less than or equals to UPDATED_BIMESTRE
        defaultBimestreShouldBeFound("bimestre.lessThan=" + UPDATED_BIMESTRE);
    }


    @Test
    @Transactional
    public void getAllBimestresByParaleloIsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where paralelo equals to DEFAULT_PARALELO
        defaultBimestreShouldBeFound("paralelo.equals=" + DEFAULT_PARALELO);

        // Get all the bimestreList where paralelo equals to UPDATED_PARALELO
        defaultBimestreShouldNotBeFound("paralelo.equals=" + UPDATED_PARALELO);
    }

    @Test
    @Transactional
    public void getAllBimestresByParaleloIsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where paralelo in DEFAULT_PARALELO or UPDATED_PARALELO
        defaultBimestreShouldBeFound("paralelo.in=" + DEFAULT_PARALELO + "," + UPDATED_PARALELO);

        // Get all the bimestreList where paralelo equals to UPDATED_PARALELO
        defaultBimestreShouldNotBeFound("paralelo.in=" + UPDATED_PARALELO);
    }

    @Test
    @Transactional
    public void getAllBimestresByParaleloIsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where paralelo is not null
        defaultBimestreShouldBeFound("paralelo.specified=true");

        // Get all the bimestreList where paralelo is null
        defaultBimestreShouldNotBeFound("paralelo.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByGestionIsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where gestion equals to DEFAULT_GESTION
        defaultBimestreShouldBeFound("gestion.equals=" + DEFAULT_GESTION);

        // Get all the bimestreList where gestion equals to UPDATED_GESTION
        defaultBimestreShouldNotBeFound("gestion.equals=" + UPDATED_GESTION);
    }

    @Test
    @Transactional
    public void getAllBimestresByGestionIsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where gestion in DEFAULT_GESTION or UPDATED_GESTION
        defaultBimestreShouldBeFound("gestion.in=" + DEFAULT_GESTION + "," + UPDATED_GESTION);

        // Get all the bimestreList where gestion equals to UPDATED_GESTION
        defaultBimestreShouldNotBeFound("gestion.in=" + UPDATED_GESTION);
    }

    @Test
    @Transactional
    public void getAllBimestresByGestionIsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where gestion is not null
        defaultBimestreShouldBeFound("gestion.specified=true");

        // Get all the bimestreList where gestion is null
        defaultBimestreShouldNotBeFound("gestion.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByGestionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where gestion greater than or equals to DEFAULT_GESTION
        defaultBimestreShouldBeFound("gestion.greaterOrEqualThan=" + DEFAULT_GESTION);

        // Get all the bimestreList where gestion greater than or equals to UPDATED_GESTION
        defaultBimestreShouldNotBeFound("gestion.greaterOrEqualThan=" + UPDATED_GESTION);
    }

    @Test
    @Transactional
    public void getAllBimestresByGestionIsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where gestion less than or equals to DEFAULT_GESTION
        defaultBimestreShouldNotBeFound("gestion.lessThan=" + DEFAULT_GESTION);

        // Get all the bimestreList where gestion less than or equals to UPDATED_GESTION
        defaultBimestreShouldBeFound("gestion.lessThan=" + UPDATED_GESTION);
    }


    @Test
    @Transactional
    public void getAllBimestresByIdMateriaIsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where idMateria equals to DEFAULT_ID_MATERIA
        defaultBimestreShouldBeFound("idMateria.equals=" + DEFAULT_ID_MATERIA);

        // Get all the bimestreList where idMateria equals to UPDATED_ID_MATERIA
        defaultBimestreShouldNotBeFound("idMateria.equals=" + UPDATED_ID_MATERIA);
    }

    @Test
    @Transactional
    public void getAllBimestresByIdMateriaIsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where idMateria in DEFAULT_ID_MATERIA or UPDATED_ID_MATERIA
        defaultBimestreShouldBeFound("idMateria.in=" + DEFAULT_ID_MATERIA + "," + UPDATED_ID_MATERIA);

        // Get all the bimestreList where idMateria equals to UPDATED_ID_MATERIA
        defaultBimestreShouldNotBeFound("idMateria.in=" + UPDATED_ID_MATERIA);
    }

    @Test
    @Transactional
    public void getAllBimestresByIdMateriaIsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where idMateria is not null
        defaultBimestreShouldBeFound("idMateria.specified=true");

        // Get all the bimestreList where idMateria is null
        defaultBimestreShouldNotBeFound("idMateria.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByIdMateriaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where idMateria greater than or equals to DEFAULT_ID_MATERIA
        defaultBimestreShouldBeFound("idMateria.greaterOrEqualThan=" + DEFAULT_ID_MATERIA);

        // Get all the bimestreList where idMateria greater than or equals to UPDATED_ID_MATERIA
        defaultBimestreShouldNotBeFound("idMateria.greaterOrEqualThan=" + UPDATED_ID_MATERIA);
    }

    @Test
    @Transactional
    public void getAllBimestresByIdMateriaIsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where idMateria less than or equals to DEFAULT_ID_MATERIA
        defaultBimestreShouldNotBeFound("idMateria.lessThan=" + DEFAULT_ID_MATERIA);

        // Get all the bimestreList where idMateria less than or equals to UPDATED_ID_MATERIA
        defaultBimestreShouldBeFound("idMateria.lessThan=" + UPDATED_ID_MATERIA);
    }


    @Test
    @Transactional
    public void getAllBimestresByObservacionIsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where observacion equals to DEFAULT_OBSERVACION
        defaultBimestreShouldBeFound("observacion.equals=" + DEFAULT_OBSERVACION);

        // Get all the bimestreList where observacion equals to UPDATED_OBSERVACION
        defaultBimestreShouldNotBeFound("observacion.equals=" + UPDATED_OBSERVACION);
    }

    @Test
    @Transactional
    public void getAllBimestresByObservacionIsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where observacion in DEFAULT_OBSERVACION or UPDATED_OBSERVACION
        defaultBimestreShouldBeFound("observacion.in=" + DEFAULT_OBSERVACION + "," + UPDATED_OBSERVACION);

        // Get all the bimestreList where observacion equals to UPDATED_OBSERVACION
        defaultBimestreShouldNotBeFound("observacion.in=" + UPDATED_OBSERVACION);
    }

    @Test
    @Transactional
    public void getAllBimestresByObservacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where observacion is not null
        defaultBimestreShouldBeFound("observacion.specified=true");

        // Get all the bimestreList where observacion is null
        defaultBimestreShouldNotBeFound("observacion.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByIndicadorCualitativoIsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where indicadorCualitativo equals to DEFAULT_INDICADOR_CUALITATIVO
        defaultBimestreShouldBeFound("indicadorCualitativo.equals=" + DEFAULT_INDICADOR_CUALITATIVO);

        // Get all the bimestreList where indicadorCualitativo equals to UPDATED_INDICADOR_CUALITATIVO
        defaultBimestreShouldNotBeFound("indicadorCualitativo.equals=" + UPDATED_INDICADOR_CUALITATIVO);
    }

    @Test
    @Transactional
    public void getAllBimestresByIndicadorCualitativoIsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where indicadorCualitativo in DEFAULT_INDICADOR_CUALITATIVO or UPDATED_INDICADOR_CUALITATIVO
        defaultBimestreShouldBeFound("indicadorCualitativo.in=" + DEFAULT_INDICADOR_CUALITATIVO + "," + UPDATED_INDICADOR_CUALITATIVO);

        // Get all the bimestreList where indicadorCualitativo equals to UPDATED_INDICADOR_CUALITATIVO
        defaultBimestreShouldNotBeFound("indicadorCualitativo.in=" + UPDATED_INDICADOR_CUALITATIVO);
    }

    @Test
    @Transactional
    public void getAllBimestresByIndicadorCualitativoIsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where indicadorCualitativo is not null
        defaultBimestreShouldBeFound("indicadorCualitativo.specified=true");

        // Get all the bimestreList where indicadorCualitativo is null
        defaultBimestreShouldNotBeFound("indicadorCualitativo.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaBimestralFinalIsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaBimestralFinal equals to DEFAULT_NOTA_BIMESTRAL_FINAL
        defaultBimestreShouldBeFound("notaBimestralFinal.equals=" + DEFAULT_NOTA_BIMESTRAL_FINAL);

        // Get all the bimestreList where notaBimestralFinal equals to UPDATED_NOTA_BIMESTRAL_FINAL
        defaultBimestreShouldNotBeFound("notaBimestralFinal.equals=" + UPDATED_NOTA_BIMESTRAL_FINAL);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaBimestralFinalIsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaBimestralFinal in DEFAULT_NOTA_BIMESTRAL_FINAL or UPDATED_NOTA_BIMESTRAL_FINAL
        defaultBimestreShouldBeFound("notaBimestralFinal.in=" + DEFAULT_NOTA_BIMESTRAL_FINAL + "," + UPDATED_NOTA_BIMESTRAL_FINAL);

        // Get all the bimestreList where notaBimestralFinal equals to UPDATED_NOTA_BIMESTRAL_FINAL
        defaultBimestreShouldNotBeFound("notaBimestralFinal.in=" + UPDATED_NOTA_BIMESTRAL_FINAL);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaBimestralFinalIsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaBimestralFinal is not null
        defaultBimestreShouldBeFound("notaBimestralFinal.specified=true");

        // Get all the bimestreList where notaBimestralFinal is null
        defaultBimestreShouldNotBeFound("notaBimestralFinal.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByPromedioAutoevaluacionIsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where promedioAutoevaluacion equals to DEFAULT_PROMEDIO_AUTOEVALUACION
        defaultBimestreShouldBeFound("promedioAutoevaluacion.equals=" + DEFAULT_PROMEDIO_AUTOEVALUACION);

        // Get all the bimestreList where promedioAutoevaluacion equals to UPDATED_PROMEDIO_AUTOEVALUACION
        defaultBimestreShouldNotBeFound("promedioAutoevaluacion.equals=" + UPDATED_PROMEDIO_AUTOEVALUACION);
    }

    @Test
    @Transactional
    public void getAllBimestresByPromedioAutoevaluacionIsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where promedioAutoevaluacion in DEFAULT_PROMEDIO_AUTOEVALUACION or UPDATED_PROMEDIO_AUTOEVALUACION
        defaultBimestreShouldBeFound("promedioAutoevaluacion.in=" + DEFAULT_PROMEDIO_AUTOEVALUACION + "," + UPDATED_PROMEDIO_AUTOEVALUACION);

        // Get all the bimestreList where promedioAutoevaluacion equals to UPDATED_PROMEDIO_AUTOEVALUACION
        defaultBimestreShouldNotBeFound("promedioAutoevaluacion.in=" + UPDATED_PROMEDIO_AUTOEVALUACION);
    }

    @Test
    @Transactional
    public void getAllBimestresByPromedioAutoevaluacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where promedioAutoevaluacion is not null
        defaultBimestreShouldBeFound("promedioAutoevaluacion.specified=true");

        // Get all the bimestreList where promedioAutoevaluacion is null
        defaultBimestreShouldNotBeFound("promedioAutoevaluacion.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByAutoevaluacionDecirIsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where autoevaluacionDecir equals to DEFAULT_AUTOEVALUACION_DECIR
        defaultBimestreShouldBeFound("autoevaluacionDecir.equals=" + DEFAULT_AUTOEVALUACION_DECIR);

        // Get all the bimestreList where autoevaluacionDecir equals to UPDATED_AUTOEVALUACION_DECIR
        defaultBimestreShouldNotBeFound("autoevaluacionDecir.equals=" + UPDATED_AUTOEVALUACION_DECIR);
    }

    @Test
    @Transactional
    public void getAllBimestresByAutoevaluacionDecirIsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where autoevaluacionDecir in DEFAULT_AUTOEVALUACION_DECIR or UPDATED_AUTOEVALUACION_DECIR
        defaultBimestreShouldBeFound("autoevaluacionDecir.in=" + DEFAULT_AUTOEVALUACION_DECIR + "," + UPDATED_AUTOEVALUACION_DECIR);

        // Get all the bimestreList where autoevaluacionDecir equals to UPDATED_AUTOEVALUACION_DECIR
        defaultBimestreShouldNotBeFound("autoevaluacionDecir.in=" + UPDATED_AUTOEVALUACION_DECIR);
    }

    @Test
    @Transactional
    public void getAllBimestresByAutoevaluacionDecirIsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where autoevaluacionDecir is not null
        defaultBimestreShouldBeFound("autoevaluacionDecir.specified=true");

        // Get all the bimestreList where autoevaluacionDecir is null
        defaultBimestreShouldNotBeFound("autoevaluacionDecir.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByAutoevaluacionSerIsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where autoevaluacionSer equals to DEFAULT_AUTOEVALUACION_SER
        defaultBimestreShouldBeFound("autoevaluacionSer.equals=" + DEFAULT_AUTOEVALUACION_SER);

        // Get all the bimestreList where autoevaluacionSer equals to UPDATED_AUTOEVALUACION_SER
        defaultBimestreShouldNotBeFound("autoevaluacionSer.equals=" + UPDATED_AUTOEVALUACION_SER);
    }

    @Test
    @Transactional
    public void getAllBimestresByAutoevaluacionSerIsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where autoevaluacionSer in DEFAULT_AUTOEVALUACION_SER or UPDATED_AUTOEVALUACION_SER
        defaultBimestreShouldBeFound("autoevaluacionSer.in=" + DEFAULT_AUTOEVALUACION_SER + "," + UPDATED_AUTOEVALUACION_SER);

        // Get all the bimestreList where autoevaluacionSer equals to UPDATED_AUTOEVALUACION_SER
        defaultBimestreShouldNotBeFound("autoevaluacionSer.in=" + UPDATED_AUTOEVALUACION_SER);
    }

    @Test
    @Transactional
    public void getAllBimestresByAutoevaluacionSerIsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where autoevaluacionSer is not null
        defaultBimestreShouldBeFound("autoevaluacionSer.specified=true");

        // Get all the bimestreList where autoevaluacionSer is null
        defaultBimestreShouldNotBeFound("autoevaluacionSer.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByDecirPromedioIsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where decirPromedio equals to DEFAULT_DECIR_PROMEDIO
        defaultBimestreShouldBeFound("decirPromedio.equals=" + DEFAULT_DECIR_PROMEDIO);

        // Get all the bimestreList where decirPromedio equals to UPDATED_DECIR_PROMEDIO
        defaultBimestreShouldNotBeFound("decirPromedio.equals=" + UPDATED_DECIR_PROMEDIO);
    }

    @Test
    @Transactional
    public void getAllBimestresByDecirPromedioIsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where decirPromedio in DEFAULT_DECIR_PROMEDIO or UPDATED_DECIR_PROMEDIO
        defaultBimestreShouldBeFound("decirPromedio.in=" + DEFAULT_DECIR_PROMEDIO + "," + UPDATED_DECIR_PROMEDIO);

        // Get all the bimestreList where decirPromedio equals to UPDATED_DECIR_PROMEDIO
        defaultBimestreShouldNotBeFound("decirPromedio.in=" + UPDATED_DECIR_PROMEDIO);
    }

    @Test
    @Transactional
    public void getAllBimestresByDecirPromedioIsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where decirPromedio is not null
        defaultBimestreShouldBeFound("decirPromedio.specified=true");

        // Get all the bimestreList where decirPromedio is null
        defaultBimestreShouldNotBeFound("decirPromedio.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByHacerPromedioIsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where hacerPromedio equals to DEFAULT_HACER_PROMEDIO
        defaultBimestreShouldBeFound("hacerPromedio.equals=" + DEFAULT_HACER_PROMEDIO);

        // Get all the bimestreList where hacerPromedio equals to UPDATED_HACER_PROMEDIO
        defaultBimestreShouldNotBeFound("hacerPromedio.equals=" + UPDATED_HACER_PROMEDIO);
    }

    @Test
    @Transactional
    public void getAllBimestresByHacerPromedioIsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where hacerPromedio in DEFAULT_HACER_PROMEDIO or UPDATED_HACER_PROMEDIO
        defaultBimestreShouldBeFound("hacerPromedio.in=" + DEFAULT_HACER_PROMEDIO + "," + UPDATED_HACER_PROMEDIO);

        // Get all the bimestreList where hacerPromedio equals to UPDATED_HACER_PROMEDIO
        defaultBimestreShouldNotBeFound("hacerPromedio.in=" + UPDATED_HACER_PROMEDIO);
    }

    @Test
    @Transactional
    public void getAllBimestresByHacerPromedioIsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where hacerPromedio is not null
        defaultBimestreShouldBeFound("hacerPromedio.specified=true");

        // Get all the bimestreList where hacerPromedio is null
        defaultBimestreShouldNotBeFound("hacerPromedio.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresBySaberPromedioIsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where saberPromedio equals to DEFAULT_SABER_PROMEDIO
        defaultBimestreShouldBeFound("saberPromedio.equals=" + DEFAULT_SABER_PROMEDIO);

        // Get all the bimestreList where saberPromedio equals to UPDATED_SABER_PROMEDIO
        defaultBimestreShouldNotBeFound("saberPromedio.equals=" + UPDATED_SABER_PROMEDIO);
    }

    @Test
    @Transactional
    public void getAllBimestresBySaberPromedioIsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where saberPromedio in DEFAULT_SABER_PROMEDIO or UPDATED_SABER_PROMEDIO
        defaultBimestreShouldBeFound("saberPromedio.in=" + DEFAULT_SABER_PROMEDIO + "," + UPDATED_SABER_PROMEDIO);

        // Get all the bimestreList where saberPromedio equals to UPDATED_SABER_PROMEDIO
        defaultBimestreShouldNotBeFound("saberPromedio.in=" + UPDATED_SABER_PROMEDIO);
    }

    @Test
    @Transactional
    public void getAllBimestresBySaberPromedioIsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where saberPromedio is not null
        defaultBimestreShouldBeFound("saberPromedio.specified=true");

        // Get all the bimestreList where saberPromedio is null
        defaultBimestreShouldNotBeFound("saberPromedio.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresBySerPromedioIsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where serPromedio equals to DEFAULT_SER_PROMEDIO
        defaultBimestreShouldBeFound("serPromedio.equals=" + DEFAULT_SER_PROMEDIO);

        // Get all the bimestreList where serPromedio equals to UPDATED_SER_PROMEDIO
        defaultBimestreShouldNotBeFound("serPromedio.equals=" + UPDATED_SER_PROMEDIO);
    }

    @Test
    @Transactional
    public void getAllBimestresBySerPromedioIsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where serPromedio in DEFAULT_SER_PROMEDIO or UPDATED_SER_PROMEDIO
        defaultBimestreShouldBeFound("serPromedio.in=" + DEFAULT_SER_PROMEDIO + "," + UPDATED_SER_PROMEDIO);

        // Get all the bimestreList where serPromedio equals to UPDATED_SER_PROMEDIO
        defaultBimestreShouldNotBeFound("serPromedio.in=" + UPDATED_SER_PROMEDIO);
    }

    @Test
    @Transactional
    public void getAllBimestresBySerPromedioIsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where serPromedio is not null
        defaultBimestreShouldBeFound("serPromedio.specified=true");

        // Get all the bimestreList where serPromedio is null
        defaultBimestreShouldNotBeFound("serPromedio.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSer1IsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer1 equals to DEFAULT_NOTA_SER_1
        defaultBimestreShouldBeFound("notaSer1.equals=" + DEFAULT_NOTA_SER_1);

        // Get all the bimestreList where notaSer1 equals to UPDATED_NOTA_SER_1
        defaultBimestreShouldNotBeFound("notaSer1.equals=" + UPDATED_NOTA_SER_1);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSer1IsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer1 in DEFAULT_NOTA_SER_1 or UPDATED_NOTA_SER_1
        defaultBimestreShouldBeFound("notaSer1.in=" + DEFAULT_NOTA_SER_1 + "," + UPDATED_NOTA_SER_1);

        // Get all the bimestreList where notaSer1 equals to UPDATED_NOTA_SER_1
        defaultBimestreShouldNotBeFound("notaSer1.in=" + UPDATED_NOTA_SER_1);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSer1IsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer1 is not null
        defaultBimestreShouldBeFound("notaSer1.specified=true");

        // Get all the bimestreList where notaSer1 is null
        defaultBimestreShouldNotBeFound("notaSer1.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSer1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer1 greater than or equals to DEFAULT_NOTA_SER_1
        defaultBimestreShouldBeFound("notaSer1.greaterOrEqualThan=" + DEFAULT_NOTA_SER_1);

        // Get all the bimestreList where notaSer1 greater than or equals to UPDATED_NOTA_SER_1
        defaultBimestreShouldNotBeFound("notaSer1.greaterOrEqualThan=" + UPDATED_NOTA_SER_1);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSer1IsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer1 less than or equals to DEFAULT_NOTA_SER_1
        defaultBimestreShouldNotBeFound("notaSer1.lessThan=" + DEFAULT_NOTA_SER_1);

        // Get all the bimestreList where notaSer1 less than or equals to UPDATED_NOTA_SER_1
        defaultBimestreShouldBeFound("notaSer1.lessThan=" + UPDATED_NOTA_SER_1);
    }


    @Test
    @Transactional
    public void getAllBimestresByNotaSer2IsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer2 equals to DEFAULT_NOTA_SER_2
        defaultBimestreShouldBeFound("notaSer2.equals=" + DEFAULT_NOTA_SER_2);

        // Get all the bimestreList where notaSer2 equals to UPDATED_NOTA_SER_2
        defaultBimestreShouldNotBeFound("notaSer2.equals=" + UPDATED_NOTA_SER_2);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSer2IsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer2 in DEFAULT_NOTA_SER_2 or UPDATED_NOTA_SER_2
        defaultBimestreShouldBeFound("notaSer2.in=" + DEFAULT_NOTA_SER_2 + "," + UPDATED_NOTA_SER_2);

        // Get all the bimestreList where notaSer2 equals to UPDATED_NOTA_SER_2
        defaultBimestreShouldNotBeFound("notaSer2.in=" + UPDATED_NOTA_SER_2);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSer2IsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer2 is not null
        defaultBimestreShouldBeFound("notaSer2.specified=true");

        // Get all the bimestreList where notaSer2 is null
        defaultBimestreShouldNotBeFound("notaSer2.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSer2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer2 greater than or equals to DEFAULT_NOTA_SER_2
        defaultBimestreShouldBeFound("notaSer2.greaterOrEqualThan=" + DEFAULT_NOTA_SER_2);

        // Get all the bimestreList where notaSer2 greater than or equals to UPDATED_NOTA_SER_2
        defaultBimestreShouldNotBeFound("notaSer2.greaterOrEqualThan=" + UPDATED_NOTA_SER_2);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSer2IsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer2 less than or equals to DEFAULT_NOTA_SER_2
        defaultBimestreShouldNotBeFound("notaSer2.lessThan=" + DEFAULT_NOTA_SER_2);

        // Get all the bimestreList where notaSer2 less than or equals to UPDATED_NOTA_SER_2
        defaultBimestreShouldBeFound("notaSer2.lessThan=" + UPDATED_NOTA_SER_2);
    }


    @Test
    @Transactional
    public void getAllBimestresByNotaSer3IsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer3 equals to DEFAULT_NOTA_SER_3
        defaultBimestreShouldBeFound("notaSer3.equals=" + DEFAULT_NOTA_SER_3);

        // Get all the bimestreList where notaSer3 equals to UPDATED_NOTA_SER_3
        defaultBimestreShouldNotBeFound("notaSer3.equals=" + UPDATED_NOTA_SER_3);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSer3IsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer3 in DEFAULT_NOTA_SER_3 or UPDATED_NOTA_SER_3
        defaultBimestreShouldBeFound("notaSer3.in=" + DEFAULT_NOTA_SER_3 + "," + UPDATED_NOTA_SER_3);

        // Get all the bimestreList where notaSer3 equals to UPDATED_NOTA_SER_3
        defaultBimestreShouldNotBeFound("notaSer3.in=" + UPDATED_NOTA_SER_3);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSer3IsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer3 is not null
        defaultBimestreShouldBeFound("notaSer3.specified=true");

        // Get all the bimestreList where notaSer3 is null
        defaultBimestreShouldNotBeFound("notaSer3.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSer3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer3 greater than or equals to DEFAULT_NOTA_SER_3
        defaultBimestreShouldBeFound("notaSer3.greaterOrEqualThan=" + DEFAULT_NOTA_SER_3);

        // Get all the bimestreList where notaSer3 greater than or equals to UPDATED_NOTA_SER_3
        defaultBimestreShouldNotBeFound("notaSer3.greaterOrEqualThan=" + UPDATED_NOTA_SER_3);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSer3IsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer3 less than or equals to DEFAULT_NOTA_SER_3
        defaultBimestreShouldNotBeFound("notaSer3.lessThan=" + DEFAULT_NOTA_SER_3);

        // Get all the bimestreList where notaSer3 less than or equals to UPDATED_NOTA_SER_3
        defaultBimestreShouldBeFound("notaSer3.lessThan=" + UPDATED_NOTA_SER_3);
    }


    @Test
    @Transactional
    public void getAllBimestresByNotaSer4IsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer4 equals to DEFAULT_NOTA_SER_4
        defaultBimestreShouldBeFound("notaSer4.equals=" + DEFAULT_NOTA_SER_4);

        // Get all the bimestreList where notaSer4 equals to UPDATED_NOTA_SER_4
        defaultBimestreShouldNotBeFound("notaSer4.equals=" + UPDATED_NOTA_SER_4);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSer4IsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer4 in DEFAULT_NOTA_SER_4 or UPDATED_NOTA_SER_4
        defaultBimestreShouldBeFound("notaSer4.in=" + DEFAULT_NOTA_SER_4 + "," + UPDATED_NOTA_SER_4);

        // Get all the bimestreList where notaSer4 equals to UPDATED_NOTA_SER_4
        defaultBimestreShouldNotBeFound("notaSer4.in=" + UPDATED_NOTA_SER_4);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSer4IsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer4 is not null
        defaultBimestreShouldBeFound("notaSer4.specified=true");

        // Get all the bimestreList where notaSer4 is null
        defaultBimestreShouldNotBeFound("notaSer4.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSer4IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer4 greater than or equals to DEFAULT_NOTA_SER_4
        defaultBimestreShouldBeFound("notaSer4.greaterOrEqualThan=" + DEFAULT_NOTA_SER_4);

        // Get all the bimestreList where notaSer4 greater than or equals to UPDATED_NOTA_SER_4
        defaultBimestreShouldNotBeFound("notaSer4.greaterOrEqualThan=" + UPDATED_NOTA_SER_4);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSer4IsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer4 less than or equals to DEFAULT_NOTA_SER_4
        defaultBimestreShouldNotBeFound("notaSer4.lessThan=" + DEFAULT_NOTA_SER_4);

        // Get all the bimestreList where notaSer4 less than or equals to UPDATED_NOTA_SER_4
        defaultBimestreShouldBeFound("notaSer4.lessThan=" + UPDATED_NOTA_SER_4);
    }


    @Test
    @Transactional
    public void getAllBimestresByNotaSer5IsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer5 equals to DEFAULT_NOTA_SER_5
        defaultBimestreShouldBeFound("notaSer5.equals=" + DEFAULT_NOTA_SER_5);

        // Get all the bimestreList where notaSer5 equals to UPDATED_NOTA_SER_5
        defaultBimestreShouldNotBeFound("notaSer5.equals=" + UPDATED_NOTA_SER_5);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSer5IsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer5 in DEFAULT_NOTA_SER_5 or UPDATED_NOTA_SER_5
        defaultBimestreShouldBeFound("notaSer5.in=" + DEFAULT_NOTA_SER_5 + "," + UPDATED_NOTA_SER_5);

        // Get all the bimestreList where notaSer5 equals to UPDATED_NOTA_SER_5
        defaultBimestreShouldNotBeFound("notaSer5.in=" + UPDATED_NOTA_SER_5);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSer5IsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer5 is not null
        defaultBimestreShouldBeFound("notaSer5.specified=true");

        // Get all the bimestreList where notaSer5 is null
        defaultBimestreShouldNotBeFound("notaSer5.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSer5IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer5 greater than or equals to DEFAULT_NOTA_SER_5
        defaultBimestreShouldBeFound("notaSer5.greaterOrEqualThan=" + DEFAULT_NOTA_SER_5);

        // Get all the bimestreList where notaSer5 greater than or equals to UPDATED_NOTA_SER_5
        defaultBimestreShouldNotBeFound("notaSer5.greaterOrEqualThan=" + UPDATED_NOTA_SER_5);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSer5IsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer5 less than or equals to DEFAULT_NOTA_SER_5
        defaultBimestreShouldNotBeFound("notaSer5.lessThan=" + DEFAULT_NOTA_SER_5);

        // Get all the bimestreList where notaSer5 less than or equals to UPDATED_NOTA_SER_5
        defaultBimestreShouldBeFound("notaSer5.lessThan=" + UPDATED_NOTA_SER_5);
    }


    @Test
    @Transactional
    public void getAllBimestresByNotaSer6IsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer6 equals to DEFAULT_NOTA_SER_6
        defaultBimestreShouldBeFound("notaSer6.equals=" + DEFAULT_NOTA_SER_6);

        // Get all the bimestreList where notaSer6 equals to UPDATED_NOTA_SER_6
        defaultBimestreShouldNotBeFound("notaSer6.equals=" + UPDATED_NOTA_SER_6);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSer6IsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer6 in DEFAULT_NOTA_SER_6 or UPDATED_NOTA_SER_6
        defaultBimestreShouldBeFound("notaSer6.in=" + DEFAULT_NOTA_SER_6 + "," + UPDATED_NOTA_SER_6);

        // Get all the bimestreList where notaSer6 equals to UPDATED_NOTA_SER_6
        defaultBimestreShouldNotBeFound("notaSer6.in=" + UPDATED_NOTA_SER_6);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSer6IsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer6 is not null
        defaultBimestreShouldBeFound("notaSer6.specified=true");

        // Get all the bimestreList where notaSer6 is null
        defaultBimestreShouldNotBeFound("notaSer6.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSer6IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer6 greater than or equals to DEFAULT_NOTA_SER_6
        defaultBimestreShouldBeFound("notaSer6.greaterOrEqualThan=" + DEFAULT_NOTA_SER_6);

        // Get all the bimestreList where notaSer6 greater than or equals to UPDATED_NOTA_SER_6
        defaultBimestreShouldNotBeFound("notaSer6.greaterOrEqualThan=" + UPDATED_NOTA_SER_6);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSer6IsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSer6 less than or equals to DEFAULT_NOTA_SER_6
        defaultBimestreShouldNotBeFound("notaSer6.lessThan=" + DEFAULT_NOTA_SER_6);

        // Get all the bimestreList where notaSer6 less than or equals to UPDATED_NOTA_SER_6
        defaultBimestreShouldBeFound("notaSer6.lessThan=" + UPDATED_NOTA_SER_6);
    }


    @Test
    @Transactional
    public void getAllBimestresByNotaSaber1IsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber1 equals to DEFAULT_NOTA_SABER_1
        defaultBimestreShouldBeFound("notaSaber1.equals=" + DEFAULT_NOTA_SABER_1);

        // Get all the bimestreList where notaSaber1 equals to UPDATED_NOTA_SABER_1
        defaultBimestreShouldNotBeFound("notaSaber1.equals=" + UPDATED_NOTA_SABER_1);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSaber1IsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber1 in DEFAULT_NOTA_SABER_1 or UPDATED_NOTA_SABER_1
        defaultBimestreShouldBeFound("notaSaber1.in=" + DEFAULT_NOTA_SABER_1 + "," + UPDATED_NOTA_SABER_1);

        // Get all the bimestreList where notaSaber1 equals to UPDATED_NOTA_SABER_1
        defaultBimestreShouldNotBeFound("notaSaber1.in=" + UPDATED_NOTA_SABER_1);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSaber1IsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber1 is not null
        defaultBimestreShouldBeFound("notaSaber1.specified=true");

        // Get all the bimestreList where notaSaber1 is null
        defaultBimestreShouldNotBeFound("notaSaber1.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSaber1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber1 greater than or equals to DEFAULT_NOTA_SABER_1
        defaultBimestreShouldBeFound("notaSaber1.greaterOrEqualThan=" + DEFAULT_NOTA_SABER_1);

        // Get all the bimestreList where notaSaber1 greater than or equals to UPDATED_NOTA_SABER_1
        defaultBimestreShouldNotBeFound("notaSaber1.greaterOrEqualThan=" + UPDATED_NOTA_SABER_1);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSaber1IsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber1 less than or equals to DEFAULT_NOTA_SABER_1
        defaultBimestreShouldNotBeFound("notaSaber1.lessThan=" + DEFAULT_NOTA_SABER_1);

        // Get all the bimestreList where notaSaber1 less than or equals to UPDATED_NOTA_SABER_1
        defaultBimestreShouldBeFound("notaSaber1.lessThan=" + UPDATED_NOTA_SABER_1);
    }


    @Test
    @Transactional
    public void getAllBimestresByNotaSaber2IsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber2 equals to DEFAULT_NOTA_SABER_2
        defaultBimestreShouldBeFound("notaSaber2.equals=" + DEFAULT_NOTA_SABER_2);

        // Get all the bimestreList where notaSaber2 equals to UPDATED_NOTA_SABER_2
        defaultBimestreShouldNotBeFound("notaSaber2.equals=" + UPDATED_NOTA_SABER_2);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSaber2IsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber2 in DEFAULT_NOTA_SABER_2 or UPDATED_NOTA_SABER_2
        defaultBimestreShouldBeFound("notaSaber2.in=" + DEFAULT_NOTA_SABER_2 + "," + UPDATED_NOTA_SABER_2);

        // Get all the bimestreList where notaSaber2 equals to UPDATED_NOTA_SABER_2
        defaultBimestreShouldNotBeFound("notaSaber2.in=" + UPDATED_NOTA_SABER_2);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSaber2IsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber2 is not null
        defaultBimestreShouldBeFound("notaSaber2.specified=true");

        // Get all the bimestreList where notaSaber2 is null
        defaultBimestreShouldNotBeFound("notaSaber2.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSaber2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber2 greater than or equals to DEFAULT_NOTA_SABER_2
        defaultBimestreShouldBeFound("notaSaber2.greaterOrEqualThan=" + DEFAULT_NOTA_SABER_2);

        // Get all the bimestreList where notaSaber2 greater than or equals to UPDATED_NOTA_SABER_2
        defaultBimestreShouldNotBeFound("notaSaber2.greaterOrEqualThan=" + UPDATED_NOTA_SABER_2);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSaber2IsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber2 less than or equals to DEFAULT_NOTA_SABER_2
        defaultBimestreShouldNotBeFound("notaSaber2.lessThan=" + DEFAULT_NOTA_SABER_2);

        // Get all the bimestreList where notaSaber2 less than or equals to UPDATED_NOTA_SABER_2
        defaultBimestreShouldBeFound("notaSaber2.lessThan=" + UPDATED_NOTA_SABER_2);
    }


    @Test
    @Transactional
    public void getAllBimestresByNotaSaber3IsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber3 equals to DEFAULT_NOTA_SABER_3
        defaultBimestreShouldBeFound("notaSaber3.equals=" + DEFAULT_NOTA_SABER_3);

        // Get all the bimestreList where notaSaber3 equals to UPDATED_NOTA_SABER_3
        defaultBimestreShouldNotBeFound("notaSaber3.equals=" + UPDATED_NOTA_SABER_3);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSaber3IsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber3 in DEFAULT_NOTA_SABER_3 or UPDATED_NOTA_SABER_3
        defaultBimestreShouldBeFound("notaSaber3.in=" + DEFAULT_NOTA_SABER_3 + "," + UPDATED_NOTA_SABER_3);

        // Get all the bimestreList where notaSaber3 equals to UPDATED_NOTA_SABER_3
        defaultBimestreShouldNotBeFound("notaSaber3.in=" + UPDATED_NOTA_SABER_3);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSaber3IsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber3 is not null
        defaultBimestreShouldBeFound("notaSaber3.specified=true");

        // Get all the bimestreList where notaSaber3 is null
        defaultBimestreShouldNotBeFound("notaSaber3.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSaber3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber3 greater than or equals to DEFAULT_NOTA_SABER_3
        defaultBimestreShouldBeFound("notaSaber3.greaterOrEqualThan=" + DEFAULT_NOTA_SABER_3);

        // Get all the bimestreList where notaSaber3 greater than or equals to UPDATED_NOTA_SABER_3
        defaultBimestreShouldNotBeFound("notaSaber3.greaterOrEqualThan=" + UPDATED_NOTA_SABER_3);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSaber3IsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber3 less than or equals to DEFAULT_NOTA_SABER_3
        defaultBimestreShouldNotBeFound("notaSaber3.lessThan=" + DEFAULT_NOTA_SABER_3);

        // Get all the bimestreList where notaSaber3 less than or equals to UPDATED_NOTA_SABER_3
        defaultBimestreShouldBeFound("notaSaber3.lessThan=" + UPDATED_NOTA_SABER_3);
    }


    @Test
    @Transactional
    public void getAllBimestresByNotaSaber4IsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber4 equals to DEFAULT_NOTA_SABER_4
        defaultBimestreShouldBeFound("notaSaber4.equals=" + DEFAULT_NOTA_SABER_4);

        // Get all the bimestreList where notaSaber4 equals to UPDATED_NOTA_SABER_4
        defaultBimestreShouldNotBeFound("notaSaber4.equals=" + UPDATED_NOTA_SABER_4);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSaber4IsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber4 in DEFAULT_NOTA_SABER_4 or UPDATED_NOTA_SABER_4
        defaultBimestreShouldBeFound("notaSaber4.in=" + DEFAULT_NOTA_SABER_4 + "," + UPDATED_NOTA_SABER_4);

        // Get all the bimestreList where notaSaber4 equals to UPDATED_NOTA_SABER_4
        defaultBimestreShouldNotBeFound("notaSaber4.in=" + UPDATED_NOTA_SABER_4);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSaber4IsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber4 is not null
        defaultBimestreShouldBeFound("notaSaber4.specified=true");

        // Get all the bimestreList where notaSaber4 is null
        defaultBimestreShouldNotBeFound("notaSaber4.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSaber4IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber4 greater than or equals to DEFAULT_NOTA_SABER_4
        defaultBimestreShouldBeFound("notaSaber4.greaterOrEqualThan=" + DEFAULT_NOTA_SABER_4);

        // Get all the bimestreList where notaSaber4 greater than or equals to UPDATED_NOTA_SABER_4
        defaultBimestreShouldNotBeFound("notaSaber4.greaterOrEqualThan=" + UPDATED_NOTA_SABER_4);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSaber4IsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber4 less than or equals to DEFAULT_NOTA_SABER_4
        defaultBimestreShouldNotBeFound("notaSaber4.lessThan=" + DEFAULT_NOTA_SABER_4);

        // Get all the bimestreList where notaSaber4 less than or equals to UPDATED_NOTA_SABER_4
        defaultBimestreShouldBeFound("notaSaber4.lessThan=" + UPDATED_NOTA_SABER_4);
    }


    @Test
    @Transactional
    public void getAllBimestresByNotaSaber5IsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber5 equals to DEFAULT_NOTA_SABER_5
        defaultBimestreShouldBeFound("notaSaber5.equals=" + DEFAULT_NOTA_SABER_5);

        // Get all the bimestreList where notaSaber5 equals to UPDATED_NOTA_SABER_5
        defaultBimestreShouldNotBeFound("notaSaber5.equals=" + UPDATED_NOTA_SABER_5);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSaber5IsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber5 in DEFAULT_NOTA_SABER_5 or UPDATED_NOTA_SABER_5
        defaultBimestreShouldBeFound("notaSaber5.in=" + DEFAULT_NOTA_SABER_5 + "," + UPDATED_NOTA_SABER_5);

        // Get all the bimestreList where notaSaber5 equals to UPDATED_NOTA_SABER_5
        defaultBimestreShouldNotBeFound("notaSaber5.in=" + UPDATED_NOTA_SABER_5);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSaber5IsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber5 is not null
        defaultBimestreShouldBeFound("notaSaber5.specified=true");

        // Get all the bimestreList where notaSaber5 is null
        defaultBimestreShouldNotBeFound("notaSaber5.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSaber5IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber5 greater than or equals to DEFAULT_NOTA_SABER_5
        defaultBimestreShouldBeFound("notaSaber5.greaterOrEqualThan=" + DEFAULT_NOTA_SABER_5);

        // Get all the bimestreList where notaSaber5 greater than or equals to UPDATED_NOTA_SABER_5
        defaultBimestreShouldNotBeFound("notaSaber5.greaterOrEqualThan=" + UPDATED_NOTA_SABER_5);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSaber5IsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber5 less than or equals to DEFAULT_NOTA_SABER_5
        defaultBimestreShouldNotBeFound("notaSaber5.lessThan=" + DEFAULT_NOTA_SABER_5);

        // Get all the bimestreList where notaSaber5 less than or equals to UPDATED_NOTA_SABER_5
        defaultBimestreShouldBeFound("notaSaber5.lessThan=" + UPDATED_NOTA_SABER_5);
    }


    @Test
    @Transactional
    public void getAllBimestresByNotaSaber6IsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber6 equals to DEFAULT_NOTA_SABER_6
        defaultBimestreShouldBeFound("notaSaber6.equals=" + DEFAULT_NOTA_SABER_6);

        // Get all the bimestreList where notaSaber6 equals to UPDATED_NOTA_SABER_6
        defaultBimestreShouldNotBeFound("notaSaber6.equals=" + UPDATED_NOTA_SABER_6);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSaber6IsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber6 in DEFAULT_NOTA_SABER_6 or UPDATED_NOTA_SABER_6
        defaultBimestreShouldBeFound("notaSaber6.in=" + DEFAULT_NOTA_SABER_6 + "," + UPDATED_NOTA_SABER_6);

        // Get all the bimestreList where notaSaber6 equals to UPDATED_NOTA_SABER_6
        defaultBimestreShouldNotBeFound("notaSaber6.in=" + UPDATED_NOTA_SABER_6);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSaber6IsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber6 is not null
        defaultBimestreShouldBeFound("notaSaber6.specified=true");

        // Get all the bimestreList where notaSaber6 is null
        defaultBimestreShouldNotBeFound("notaSaber6.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSaber6IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber6 greater than or equals to DEFAULT_NOTA_SABER_6
        defaultBimestreShouldBeFound("notaSaber6.greaterOrEqualThan=" + DEFAULT_NOTA_SABER_6);

        // Get all the bimestreList where notaSaber6 greater than or equals to UPDATED_NOTA_SABER_6
        defaultBimestreShouldNotBeFound("notaSaber6.greaterOrEqualThan=" + UPDATED_NOTA_SABER_6);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaSaber6IsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaSaber6 less than or equals to DEFAULT_NOTA_SABER_6
        defaultBimestreShouldNotBeFound("notaSaber6.lessThan=" + DEFAULT_NOTA_SABER_6);

        // Get all the bimestreList where notaSaber6 less than or equals to UPDATED_NOTA_SABER_6
        defaultBimestreShouldBeFound("notaSaber6.lessThan=" + UPDATED_NOTA_SABER_6);
    }


    @Test
    @Transactional
    public void getAllBimestresByNotaHacer1IsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer1 equals to DEFAULT_NOTA_HACER_1
        defaultBimestreShouldBeFound("notaHacer1.equals=" + DEFAULT_NOTA_HACER_1);

        // Get all the bimestreList where notaHacer1 equals to UPDATED_NOTA_HACER_1
        defaultBimestreShouldNotBeFound("notaHacer1.equals=" + UPDATED_NOTA_HACER_1);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaHacer1IsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer1 in DEFAULT_NOTA_HACER_1 or UPDATED_NOTA_HACER_1
        defaultBimestreShouldBeFound("notaHacer1.in=" + DEFAULT_NOTA_HACER_1 + "," + UPDATED_NOTA_HACER_1);

        // Get all the bimestreList where notaHacer1 equals to UPDATED_NOTA_HACER_1
        defaultBimestreShouldNotBeFound("notaHacer1.in=" + UPDATED_NOTA_HACER_1);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaHacer1IsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer1 is not null
        defaultBimestreShouldBeFound("notaHacer1.specified=true");

        // Get all the bimestreList where notaHacer1 is null
        defaultBimestreShouldNotBeFound("notaHacer1.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaHacer1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer1 greater than or equals to DEFAULT_NOTA_HACER_1
        defaultBimestreShouldBeFound("notaHacer1.greaterOrEqualThan=" + DEFAULT_NOTA_HACER_1);

        // Get all the bimestreList where notaHacer1 greater than or equals to UPDATED_NOTA_HACER_1
        defaultBimestreShouldNotBeFound("notaHacer1.greaterOrEqualThan=" + UPDATED_NOTA_HACER_1);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaHacer1IsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer1 less than or equals to DEFAULT_NOTA_HACER_1
        defaultBimestreShouldNotBeFound("notaHacer1.lessThan=" + DEFAULT_NOTA_HACER_1);

        // Get all the bimestreList where notaHacer1 less than or equals to UPDATED_NOTA_HACER_1
        defaultBimestreShouldBeFound("notaHacer1.lessThan=" + UPDATED_NOTA_HACER_1);
    }


    @Test
    @Transactional
    public void getAllBimestresByNotaHacer2IsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer2 equals to DEFAULT_NOTA_HACER_2
        defaultBimestreShouldBeFound("notaHacer2.equals=" + DEFAULT_NOTA_HACER_2);

        // Get all the bimestreList where notaHacer2 equals to UPDATED_NOTA_HACER_2
        defaultBimestreShouldNotBeFound("notaHacer2.equals=" + UPDATED_NOTA_HACER_2);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaHacer2IsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer2 in DEFAULT_NOTA_HACER_2 or UPDATED_NOTA_HACER_2
        defaultBimestreShouldBeFound("notaHacer2.in=" + DEFAULT_NOTA_HACER_2 + "," + UPDATED_NOTA_HACER_2);

        // Get all the bimestreList where notaHacer2 equals to UPDATED_NOTA_HACER_2
        defaultBimestreShouldNotBeFound("notaHacer2.in=" + UPDATED_NOTA_HACER_2);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaHacer2IsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer2 is not null
        defaultBimestreShouldBeFound("notaHacer2.specified=true");

        // Get all the bimestreList where notaHacer2 is null
        defaultBimestreShouldNotBeFound("notaHacer2.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaHacer2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer2 greater than or equals to DEFAULT_NOTA_HACER_2
        defaultBimestreShouldBeFound("notaHacer2.greaterOrEqualThan=" + DEFAULT_NOTA_HACER_2);

        // Get all the bimestreList where notaHacer2 greater than or equals to UPDATED_NOTA_HACER_2
        defaultBimestreShouldNotBeFound("notaHacer2.greaterOrEqualThan=" + UPDATED_NOTA_HACER_2);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaHacer2IsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer2 less than or equals to DEFAULT_NOTA_HACER_2
        defaultBimestreShouldNotBeFound("notaHacer2.lessThan=" + DEFAULT_NOTA_HACER_2);

        // Get all the bimestreList where notaHacer2 less than or equals to UPDATED_NOTA_HACER_2
        defaultBimestreShouldBeFound("notaHacer2.lessThan=" + UPDATED_NOTA_HACER_2);
    }


    @Test
    @Transactional
    public void getAllBimestresByNotaHacer3IsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer3 equals to DEFAULT_NOTA_HACER_3
        defaultBimestreShouldBeFound("notaHacer3.equals=" + DEFAULT_NOTA_HACER_3);

        // Get all the bimestreList where notaHacer3 equals to UPDATED_NOTA_HACER_3
        defaultBimestreShouldNotBeFound("notaHacer3.equals=" + UPDATED_NOTA_HACER_3);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaHacer3IsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer3 in DEFAULT_NOTA_HACER_3 or UPDATED_NOTA_HACER_3
        defaultBimestreShouldBeFound("notaHacer3.in=" + DEFAULT_NOTA_HACER_3 + "," + UPDATED_NOTA_HACER_3);

        // Get all the bimestreList where notaHacer3 equals to UPDATED_NOTA_HACER_3
        defaultBimestreShouldNotBeFound("notaHacer3.in=" + UPDATED_NOTA_HACER_3);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaHacer3IsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer3 is not null
        defaultBimestreShouldBeFound("notaHacer3.specified=true");

        // Get all the bimestreList where notaHacer3 is null
        defaultBimestreShouldNotBeFound("notaHacer3.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaHacer3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer3 greater than or equals to DEFAULT_NOTA_HACER_3
        defaultBimestreShouldBeFound("notaHacer3.greaterOrEqualThan=" + DEFAULT_NOTA_HACER_3);

        // Get all the bimestreList where notaHacer3 greater than or equals to UPDATED_NOTA_HACER_3
        defaultBimestreShouldNotBeFound("notaHacer3.greaterOrEqualThan=" + UPDATED_NOTA_HACER_3);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaHacer3IsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer3 less than or equals to DEFAULT_NOTA_HACER_3
        defaultBimestreShouldNotBeFound("notaHacer3.lessThan=" + DEFAULT_NOTA_HACER_3);

        // Get all the bimestreList where notaHacer3 less than or equals to UPDATED_NOTA_HACER_3
        defaultBimestreShouldBeFound("notaHacer3.lessThan=" + UPDATED_NOTA_HACER_3);
    }


    @Test
    @Transactional
    public void getAllBimestresByNotaHacer4IsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer4 equals to DEFAULT_NOTA_HACER_4
        defaultBimestreShouldBeFound("notaHacer4.equals=" + DEFAULT_NOTA_HACER_4);

        // Get all the bimestreList where notaHacer4 equals to UPDATED_NOTA_HACER_4
        defaultBimestreShouldNotBeFound("notaHacer4.equals=" + UPDATED_NOTA_HACER_4);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaHacer4IsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer4 in DEFAULT_NOTA_HACER_4 or UPDATED_NOTA_HACER_4
        defaultBimestreShouldBeFound("notaHacer4.in=" + DEFAULT_NOTA_HACER_4 + "," + UPDATED_NOTA_HACER_4);

        // Get all the bimestreList where notaHacer4 equals to UPDATED_NOTA_HACER_4
        defaultBimestreShouldNotBeFound("notaHacer4.in=" + UPDATED_NOTA_HACER_4);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaHacer4IsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer4 is not null
        defaultBimestreShouldBeFound("notaHacer4.specified=true");

        // Get all the bimestreList where notaHacer4 is null
        defaultBimestreShouldNotBeFound("notaHacer4.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaHacer4IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer4 greater than or equals to DEFAULT_NOTA_HACER_4
        defaultBimestreShouldBeFound("notaHacer4.greaterOrEqualThan=" + DEFAULT_NOTA_HACER_4);

        // Get all the bimestreList where notaHacer4 greater than or equals to UPDATED_NOTA_HACER_4
        defaultBimestreShouldNotBeFound("notaHacer4.greaterOrEqualThan=" + UPDATED_NOTA_HACER_4);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaHacer4IsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer4 less than or equals to DEFAULT_NOTA_HACER_4
        defaultBimestreShouldNotBeFound("notaHacer4.lessThan=" + DEFAULT_NOTA_HACER_4);

        // Get all the bimestreList where notaHacer4 less than or equals to UPDATED_NOTA_HACER_4
        defaultBimestreShouldBeFound("notaHacer4.lessThan=" + UPDATED_NOTA_HACER_4);
    }


    @Test
    @Transactional
    public void getAllBimestresByNotaHacer5IsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer5 equals to DEFAULT_NOTA_HACER_5
        defaultBimestreShouldBeFound("notaHacer5.equals=" + DEFAULT_NOTA_HACER_5);

        // Get all the bimestreList where notaHacer5 equals to UPDATED_NOTA_HACER_5
        defaultBimestreShouldNotBeFound("notaHacer5.equals=" + UPDATED_NOTA_HACER_5);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaHacer5IsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer5 in DEFAULT_NOTA_HACER_5 or UPDATED_NOTA_HACER_5
        defaultBimestreShouldBeFound("notaHacer5.in=" + DEFAULT_NOTA_HACER_5 + "," + UPDATED_NOTA_HACER_5);

        // Get all the bimestreList where notaHacer5 equals to UPDATED_NOTA_HACER_5
        defaultBimestreShouldNotBeFound("notaHacer5.in=" + UPDATED_NOTA_HACER_5);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaHacer5IsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer5 is not null
        defaultBimestreShouldBeFound("notaHacer5.specified=true");

        // Get all the bimestreList where notaHacer5 is null
        defaultBimestreShouldNotBeFound("notaHacer5.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaHacer5IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer5 greater than or equals to DEFAULT_NOTA_HACER_5
        defaultBimestreShouldBeFound("notaHacer5.greaterOrEqualThan=" + DEFAULT_NOTA_HACER_5);

        // Get all the bimestreList where notaHacer5 greater than or equals to UPDATED_NOTA_HACER_5
        defaultBimestreShouldNotBeFound("notaHacer5.greaterOrEqualThan=" + UPDATED_NOTA_HACER_5);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaHacer5IsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer5 less than or equals to DEFAULT_NOTA_HACER_5
        defaultBimestreShouldNotBeFound("notaHacer5.lessThan=" + DEFAULT_NOTA_HACER_5);

        // Get all the bimestreList where notaHacer5 less than or equals to UPDATED_NOTA_HACER_5
        defaultBimestreShouldBeFound("notaHacer5.lessThan=" + UPDATED_NOTA_HACER_5);
    }


    @Test
    @Transactional
    public void getAllBimestresByNotaHacer6IsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer6 equals to DEFAULT_NOTA_HACER_6
        defaultBimestreShouldBeFound("notaHacer6.equals=" + DEFAULT_NOTA_HACER_6);

        // Get all the bimestreList where notaHacer6 equals to UPDATED_NOTA_HACER_6
        defaultBimestreShouldNotBeFound("notaHacer6.equals=" + UPDATED_NOTA_HACER_6);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaHacer6IsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer6 in DEFAULT_NOTA_HACER_6 or UPDATED_NOTA_HACER_6
        defaultBimestreShouldBeFound("notaHacer6.in=" + DEFAULT_NOTA_HACER_6 + "," + UPDATED_NOTA_HACER_6);

        // Get all the bimestreList where notaHacer6 equals to UPDATED_NOTA_HACER_6
        defaultBimestreShouldNotBeFound("notaHacer6.in=" + UPDATED_NOTA_HACER_6);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaHacer6IsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer6 is not null
        defaultBimestreShouldBeFound("notaHacer6.specified=true");

        // Get all the bimestreList where notaHacer6 is null
        defaultBimestreShouldNotBeFound("notaHacer6.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaHacer6IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer6 greater than or equals to DEFAULT_NOTA_HACER_6
        defaultBimestreShouldBeFound("notaHacer6.greaterOrEqualThan=" + DEFAULT_NOTA_HACER_6);

        // Get all the bimestreList where notaHacer6 greater than or equals to UPDATED_NOTA_HACER_6
        defaultBimestreShouldNotBeFound("notaHacer6.greaterOrEqualThan=" + UPDATED_NOTA_HACER_6);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaHacer6IsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaHacer6 less than or equals to DEFAULT_NOTA_HACER_6
        defaultBimestreShouldNotBeFound("notaHacer6.lessThan=" + DEFAULT_NOTA_HACER_6);

        // Get all the bimestreList where notaHacer6 less than or equals to UPDATED_NOTA_HACER_6
        defaultBimestreShouldBeFound("notaHacer6.lessThan=" + UPDATED_NOTA_HACER_6);
    }


    @Test
    @Transactional
    public void getAllBimestresByNotaDecir1IsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir1 equals to DEFAULT_NOTA_DECIR_1
        defaultBimestreShouldBeFound("notaDecir1.equals=" + DEFAULT_NOTA_DECIR_1);

        // Get all the bimestreList where notaDecir1 equals to UPDATED_NOTA_DECIR_1
        defaultBimestreShouldNotBeFound("notaDecir1.equals=" + UPDATED_NOTA_DECIR_1);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaDecir1IsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir1 in DEFAULT_NOTA_DECIR_1 or UPDATED_NOTA_DECIR_1
        defaultBimestreShouldBeFound("notaDecir1.in=" + DEFAULT_NOTA_DECIR_1 + "," + UPDATED_NOTA_DECIR_1);

        // Get all the bimestreList where notaDecir1 equals to UPDATED_NOTA_DECIR_1
        defaultBimestreShouldNotBeFound("notaDecir1.in=" + UPDATED_NOTA_DECIR_1);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaDecir1IsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir1 is not null
        defaultBimestreShouldBeFound("notaDecir1.specified=true");

        // Get all the bimestreList where notaDecir1 is null
        defaultBimestreShouldNotBeFound("notaDecir1.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaDecir1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir1 greater than or equals to DEFAULT_NOTA_DECIR_1
        defaultBimestreShouldBeFound("notaDecir1.greaterOrEqualThan=" + DEFAULT_NOTA_DECIR_1);

        // Get all the bimestreList where notaDecir1 greater than or equals to UPDATED_NOTA_DECIR_1
        defaultBimestreShouldNotBeFound("notaDecir1.greaterOrEqualThan=" + UPDATED_NOTA_DECIR_1);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaDecir1IsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir1 less than or equals to DEFAULT_NOTA_DECIR_1
        defaultBimestreShouldNotBeFound("notaDecir1.lessThan=" + DEFAULT_NOTA_DECIR_1);

        // Get all the bimestreList where notaDecir1 less than or equals to UPDATED_NOTA_DECIR_1
        defaultBimestreShouldBeFound("notaDecir1.lessThan=" + UPDATED_NOTA_DECIR_1);
    }


    @Test
    @Transactional
    public void getAllBimestresByNotaDecir2IsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir2 equals to DEFAULT_NOTA_DECIR_2
        defaultBimestreShouldBeFound("notaDecir2.equals=" + DEFAULT_NOTA_DECIR_2);

        // Get all the bimestreList where notaDecir2 equals to UPDATED_NOTA_DECIR_2
        defaultBimestreShouldNotBeFound("notaDecir2.equals=" + UPDATED_NOTA_DECIR_2);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaDecir2IsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir2 in DEFAULT_NOTA_DECIR_2 or UPDATED_NOTA_DECIR_2
        defaultBimestreShouldBeFound("notaDecir2.in=" + DEFAULT_NOTA_DECIR_2 + "," + UPDATED_NOTA_DECIR_2);

        // Get all the bimestreList where notaDecir2 equals to UPDATED_NOTA_DECIR_2
        defaultBimestreShouldNotBeFound("notaDecir2.in=" + UPDATED_NOTA_DECIR_2);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaDecir2IsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir2 is not null
        defaultBimestreShouldBeFound("notaDecir2.specified=true");

        // Get all the bimestreList where notaDecir2 is null
        defaultBimestreShouldNotBeFound("notaDecir2.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaDecir2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir2 greater than or equals to DEFAULT_NOTA_DECIR_2
        defaultBimestreShouldBeFound("notaDecir2.greaterOrEqualThan=" + DEFAULT_NOTA_DECIR_2);

        // Get all the bimestreList where notaDecir2 greater than or equals to UPDATED_NOTA_DECIR_2
        defaultBimestreShouldNotBeFound("notaDecir2.greaterOrEqualThan=" + UPDATED_NOTA_DECIR_2);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaDecir2IsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir2 less than or equals to DEFAULT_NOTA_DECIR_2
        defaultBimestreShouldNotBeFound("notaDecir2.lessThan=" + DEFAULT_NOTA_DECIR_2);

        // Get all the bimestreList where notaDecir2 less than or equals to UPDATED_NOTA_DECIR_2
        defaultBimestreShouldBeFound("notaDecir2.lessThan=" + UPDATED_NOTA_DECIR_2);
    }


    @Test
    @Transactional
    public void getAllBimestresByNotaDecir3IsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir3 equals to DEFAULT_NOTA_DECIR_3
        defaultBimestreShouldBeFound("notaDecir3.equals=" + DEFAULT_NOTA_DECIR_3);

        // Get all the bimestreList where notaDecir3 equals to UPDATED_NOTA_DECIR_3
        defaultBimestreShouldNotBeFound("notaDecir3.equals=" + UPDATED_NOTA_DECIR_3);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaDecir3IsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir3 in DEFAULT_NOTA_DECIR_3 or UPDATED_NOTA_DECIR_3
        defaultBimestreShouldBeFound("notaDecir3.in=" + DEFAULT_NOTA_DECIR_3 + "," + UPDATED_NOTA_DECIR_3);

        // Get all the bimestreList where notaDecir3 equals to UPDATED_NOTA_DECIR_3
        defaultBimestreShouldNotBeFound("notaDecir3.in=" + UPDATED_NOTA_DECIR_3);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaDecir3IsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir3 is not null
        defaultBimestreShouldBeFound("notaDecir3.specified=true");

        // Get all the bimestreList where notaDecir3 is null
        defaultBimestreShouldNotBeFound("notaDecir3.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaDecir3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir3 greater than or equals to DEFAULT_NOTA_DECIR_3
        defaultBimestreShouldBeFound("notaDecir3.greaterOrEqualThan=" + DEFAULT_NOTA_DECIR_3);

        // Get all the bimestreList where notaDecir3 greater than or equals to UPDATED_NOTA_DECIR_3
        defaultBimestreShouldNotBeFound("notaDecir3.greaterOrEqualThan=" + UPDATED_NOTA_DECIR_3);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaDecir3IsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir3 less than or equals to DEFAULT_NOTA_DECIR_3
        defaultBimestreShouldNotBeFound("notaDecir3.lessThan=" + DEFAULT_NOTA_DECIR_3);

        // Get all the bimestreList where notaDecir3 less than or equals to UPDATED_NOTA_DECIR_3
        defaultBimestreShouldBeFound("notaDecir3.lessThan=" + UPDATED_NOTA_DECIR_3);
    }


    @Test
    @Transactional
    public void getAllBimestresByNotaDecir4IsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir4 equals to DEFAULT_NOTA_DECIR_4
        defaultBimestreShouldBeFound("notaDecir4.equals=" + DEFAULT_NOTA_DECIR_4);

        // Get all the bimestreList where notaDecir4 equals to UPDATED_NOTA_DECIR_4
        defaultBimestreShouldNotBeFound("notaDecir4.equals=" + UPDATED_NOTA_DECIR_4);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaDecir4IsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir4 in DEFAULT_NOTA_DECIR_4 or UPDATED_NOTA_DECIR_4
        defaultBimestreShouldBeFound("notaDecir4.in=" + DEFAULT_NOTA_DECIR_4 + "," + UPDATED_NOTA_DECIR_4);

        // Get all the bimestreList where notaDecir4 equals to UPDATED_NOTA_DECIR_4
        defaultBimestreShouldNotBeFound("notaDecir4.in=" + UPDATED_NOTA_DECIR_4);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaDecir4IsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir4 is not null
        defaultBimestreShouldBeFound("notaDecir4.specified=true");

        // Get all the bimestreList where notaDecir4 is null
        defaultBimestreShouldNotBeFound("notaDecir4.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaDecir4IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir4 greater than or equals to DEFAULT_NOTA_DECIR_4
        defaultBimestreShouldBeFound("notaDecir4.greaterOrEqualThan=" + DEFAULT_NOTA_DECIR_4);

        // Get all the bimestreList where notaDecir4 greater than or equals to UPDATED_NOTA_DECIR_4
        defaultBimestreShouldNotBeFound("notaDecir4.greaterOrEqualThan=" + UPDATED_NOTA_DECIR_4);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaDecir4IsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir4 less than or equals to DEFAULT_NOTA_DECIR_4
        defaultBimestreShouldNotBeFound("notaDecir4.lessThan=" + DEFAULT_NOTA_DECIR_4);

        // Get all the bimestreList where notaDecir4 less than or equals to UPDATED_NOTA_DECIR_4
        defaultBimestreShouldBeFound("notaDecir4.lessThan=" + UPDATED_NOTA_DECIR_4);
    }


    @Test
    @Transactional
    public void getAllBimestresByNotaDecir5IsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir5 equals to DEFAULT_NOTA_DECIR_5
        defaultBimestreShouldBeFound("notaDecir5.equals=" + DEFAULT_NOTA_DECIR_5);

        // Get all the bimestreList where notaDecir5 equals to UPDATED_NOTA_DECIR_5
        defaultBimestreShouldNotBeFound("notaDecir5.equals=" + UPDATED_NOTA_DECIR_5);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaDecir5IsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir5 in DEFAULT_NOTA_DECIR_5 or UPDATED_NOTA_DECIR_5
        defaultBimestreShouldBeFound("notaDecir5.in=" + DEFAULT_NOTA_DECIR_5 + "," + UPDATED_NOTA_DECIR_5);

        // Get all the bimestreList where notaDecir5 equals to UPDATED_NOTA_DECIR_5
        defaultBimestreShouldNotBeFound("notaDecir5.in=" + UPDATED_NOTA_DECIR_5);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaDecir5IsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir5 is not null
        defaultBimestreShouldBeFound("notaDecir5.specified=true");

        // Get all the bimestreList where notaDecir5 is null
        defaultBimestreShouldNotBeFound("notaDecir5.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaDecir5IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir5 greater than or equals to DEFAULT_NOTA_DECIR_5
        defaultBimestreShouldBeFound("notaDecir5.greaterOrEqualThan=" + DEFAULT_NOTA_DECIR_5);

        // Get all the bimestreList where notaDecir5 greater than or equals to UPDATED_NOTA_DECIR_5
        defaultBimestreShouldNotBeFound("notaDecir5.greaterOrEqualThan=" + UPDATED_NOTA_DECIR_5);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaDecir5IsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir5 less than or equals to DEFAULT_NOTA_DECIR_5
        defaultBimestreShouldNotBeFound("notaDecir5.lessThan=" + DEFAULT_NOTA_DECIR_5);

        // Get all the bimestreList where notaDecir5 less than or equals to UPDATED_NOTA_DECIR_5
        defaultBimestreShouldBeFound("notaDecir5.lessThan=" + UPDATED_NOTA_DECIR_5);
    }


    @Test
    @Transactional
    public void getAllBimestresByNotaDecir6IsEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir6 equals to DEFAULT_NOTA_DECIR_6
        defaultBimestreShouldBeFound("notaDecir6.equals=" + DEFAULT_NOTA_DECIR_6);

        // Get all the bimestreList where notaDecir6 equals to UPDATED_NOTA_DECIR_6
        defaultBimestreShouldNotBeFound("notaDecir6.equals=" + UPDATED_NOTA_DECIR_6);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaDecir6IsInShouldWork() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir6 in DEFAULT_NOTA_DECIR_6 or UPDATED_NOTA_DECIR_6
        defaultBimestreShouldBeFound("notaDecir6.in=" + DEFAULT_NOTA_DECIR_6 + "," + UPDATED_NOTA_DECIR_6);

        // Get all the bimestreList where notaDecir6 equals to UPDATED_NOTA_DECIR_6
        defaultBimestreShouldNotBeFound("notaDecir6.in=" + UPDATED_NOTA_DECIR_6);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaDecir6IsNullOrNotNull() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir6 is not null
        defaultBimestreShouldBeFound("notaDecir6.specified=true");

        // Get all the bimestreList where notaDecir6 is null
        defaultBimestreShouldNotBeFound("notaDecir6.specified=false");
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaDecir6IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir6 greater than or equals to DEFAULT_NOTA_DECIR_6
        defaultBimestreShouldBeFound("notaDecir6.greaterOrEqualThan=" + DEFAULT_NOTA_DECIR_6);

        // Get all the bimestreList where notaDecir6 greater than or equals to UPDATED_NOTA_DECIR_6
        defaultBimestreShouldNotBeFound("notaDecir6.greaterOrEqualThan=" + UPDATED_NOTA_DECIR_6);
    }

    @Test
    @Transactional
    public void getAllBimestresByNotaDecir6IsLessThanSomething() throws Exception {
        // Initialize the database
        bimestreRepository.saveAndFlush(bimestre);

        // Get all the bimestreList where notaDecir6 less than or equals to DEFAULT_NOTA_DECIR_6
        defaultBimestreShouldNotBeFound("notaDecir6.lessThan=" + DEFAULT_NOTA_DECIR_6);

        // Get all the bimestreList where notaDecir6 less than or equals to UPDATED_NOTA_DECIR_6
        defaultBimestreShouldBeFound("notaDecir6.lessThan=" + UPDATED_NOTA_DECIR_6);
    }


    @Test
    @Transactional
    public void getAllBimestresByEstudianteIsEqualToSomething() throws Exception {
        // Initialize the database
        Estudiante estudiante = EstudianteResourceIntTest.createEntity(em);
        em.persist(estudiante);
        em.flush();
        bimestre.setEstudiante(estudiante);
        bimestreRepository.saveAndFlush(bimestre);
        Long estudianteId = estudiante.getId();

        // Get all the bimestreList where estudiante equals to estudianteId
        defaultBimestreShouldBeFound("estudianteId.equals=" + estudianteId);

        // Get all the bimestreList where estudiante equals to estudianteId + 1
        defaultBimestreShouldNotBeFound("estudianteId.equals=" + (estudianteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultBimestreShouldBeFound(String filter) throws Exception {
        restBimestreMockMvc.perform(get("/api/bimestres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bimestre.getId().intValue())))
            .andExpect(jsonPath("$.[*].idCurso").value(hasItem(DEFAULT_ID_CURSO.intValue())))
            .andExpect(jsonPath("$.[*].idDocente").value(hasItem(DEFAULT_ID_DOCENTE.intValue())))
            .andExpect(jsonPath("$.[*].bimestre").value(hasItem(DEFAULT_BIMESTRE)))
            .andExpect(jsonPath("$.[*].paralelo").value(hasItem(DEFAULT_PARALELO.toString())))
            .andExpect(jsonPath("$.[*].gestion").value(hasItem(DEFAULT_GESTION)))
            .andExpect(jsonPath("$.[*].idMateria").value(hasItem(DEFAULT_ID_MATERIA.intValue())))
            .andExpect(jsonPath("$.[*].observacion").value(hasItem(DEFAULT_OBSERVACION.toString())))
            .andExpect(jsonPath("$.[*].indicadorCualitativo").value(hasItem(DEFAULT_INDICADOR_CUALITATIVO.toString())))
            .andExpect(jsonPath("$.[*].notaBimestralFinal").value(hasItem(DEFAULT_NOTA_BIMESTRAL_FINAL.doubleValue())))
            .andExpect(jsonPath("$.[*].promedioAutoevaluacion").value(hasItem(DEFAULT_PROMEDIO_AUTOEVALUACION.doubleValue())))
            .andExpect(jsonPath("$.[*].autoevaluacionDecir").value(hasItem(DEFAULT_AUTOEVALUACION_DECIR.doubleValue())))
            .andExpect(jsonPath("$.[*].autoevaluacionSer").value(hasItem(DEFAULT_AUTOEVALUACION_SER.doubleValue())))
            .andExpect(jsonPath("$.[*].decirPromedio").value(hasItem(DEFAULT_DECIR_PROMEDIO.doubleValue())))
            .andExpect(jsonPath("$.[*].hacerPromedio").value(hasItem(DEFAULT_HACER_PROMEDIO.doubleValue())))
            .andExpect(jsonPath("$.[*].saberPromedio").value(hasItem(DEFAULT_SABER_PROMEDIO.doubleValue())))
            .andExpect(jsonPath("$.[*].serPromedio").value(hasItem(DEFAULT_SER_PROMEDIO.doubleValue())))
            .andExpect(jsonPath("$.[*].notaSer1").value(hasItem(DEFAULT_NOTA_SER_1)))
            .andExpect(jsonPath("$.[*].notaSer2").value(hasItem(DEFAULT_NOTA_SER_2)))
            .andExpect(jsonPath("$.[*].notaSer3").value(hasItem(DEFAULT_NOTA_SER_3)))
            .andExpect(jsonPath("$.[*].notaSer4").value(hasItem(DEFAULT_NOTA_SER_4)))
            .andExpect(jsonPath("$.[*].notaSer5").value(hasItem(DEFAULT_NOTA_SER_5)))
            .andExpect(jsonPath("$.[*].notaSer6").value(hasItem(DEFAULT_NOTA_SER_6)))
            .andExpect(jsonPath("$.[*].notaSaber1").value(hasItem(DEFAULT_NOTA_SABER_1)))
            .andExpect(jsonPath("$.[*].notaSaber2").value(hasItem(DEFAULT_NOTA_SABER_2)))
            .andExpect(jsonPath("$.[*].notaSaber3").value(hasItem(DEFAULT_NOTA_SABER_3)))
            .andExpect(jsonPath("$.[*].notaSaber4").value(hasItem(DEFAULT_NOTA_SABER_4)))
            .andExpect(jsonPath("$.[*].notaSaber5").value(hasItem(DEFAULT_NOTA_SABER_5)))
            .andExpect(jsonPath("$.[*].notaSaber6").value(hasItem(DEFAULT_NOTA_SABER_6)))
            .andExpect(jsonPath("$.[*].notaHacer1").value(hasItem(DEFAULT_NOTA_HACER_1)))
            .andExpect(jsonPath("$.[*].notaHacer2").value(hasItem(DEFAULT_NOTA_HACER_2)))
            .andExpect(jsonPath("$.[*].notaHacer3").value(hasItem(DEFAULT_NOTA_HACER_3)))
            .andExpect(jsonPath("$.[*].notaHacer4").value(hasItem(DEFAULT_NOTA_HACER_4)))
            .andExpect(jsonPath("$.[*].notaHacer5").value(hasItem(DEFAULT_NOTA_HACER_5)))
            .andExpect(jsonPath("$.[*].notaHacer6").value(hasItem(DEFAULT_NOTA_HACER_6)))
            .andExpect(jsonPath("$.[*].notaDecir1").value(hasItem(DEFAULT_NOTA_DECIR_1)))
            .andExpect(jsonPath("$.[*].notaDecir2").value(hasItem(DEFAULT_NOTA_DECIR_2)))
            .andExpect(jsonPath("$.[*].notaDecir3").value(hasItem(DEFAULT_NOTA_DECIR_3)))
            .andExpect(jsonPath("$.[*].notaDecir4").value(hasItem(DEFAULT_NOTA_DECIR_4)))
            .andExpect(jsonPath("$.[*].notaDecir5").value(hasItem(DEFAULT_NOTA_DECIR_5)))
            .andExpect(jsonPath("$.[*].notaDecir6").value(hasItem(DEFAULT_NOTA_DECIR_6)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultBimestreShouldNotBeFound(String filter) throws Exception {
        restBimestreMockMvc.perform(get("/api/bimestres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingBimestre() throws Exception {
        // Get the bimestre
        restBimestreMockMvc.perform(get("/api/bimestres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBimestre() throws Exception {
        // Initialize the database
        bimestreService.save(bimestre);

        int databaseSizeBeforeUpdate = bimestreRepository.findAll().size();

        // Update the bimestre
        Bimestre updatedBimestre = bimestreRepository.findOne(bimestre.getId());
        // Disconnect from session so that the updates on updatedBimestre are not directly saved in db
        em.detach(updatedBimestre);
        updatedBimestre
            .idCurso(UPDATED_ID_CURSO)
            .idDocente(UPDATED_ID_DOCENTE)
            .bimestre(UPDATED_BIMESTRE)
            .paralelo(UPDATED_PARALELO)
            .gestion(UPDATED_GESTION)
            .idMateria(UPDATED_ID_MATERIA)
            .observacion(UPDATED_OBSERVACION)
            .indicadorCualitativo(UPDATED_INDICADOR_CUALITATIVO)
            .notaBimestralFinal(UPDATED_NOTA_BIMESTRAL_FINAL)
            .promedioAutoevaluacion(UPDATED_PROMEDIO_AUTOEVALUACION)
            .autoevaluacionDecir(UPDATED_AUTOEVALUACION_DECIR)
            .autoevaluacionSer(UPDATED_AUTOEVALUACION_SER)
            .decirPromedio(UPDATED_DECIR_PROMEDIO)
            .hacerPromedio(UPDATED_HACER_PROMEDIO)
            .saberPromedio(UPDATED_SABER_PROMEDIO)
            .serPromedio(UPDATED_SER_PROMEDIO)
            .notaSer1(UPDATED_NOTA_SER_1)
            .notaSer2(UPDATED_NOTA_SER_2)
            .notaSer3(UPDATED_NOTA_SER_3)
            .notaSer4(UPDATED_NOTA_SER_4)
            .notaSer5(UPDATED_NOTA_SER_5)
            .notaSer6(UPDATED_NOTA_SER_6)
            .notaSaber1(UPDATED_NOTA_SABER_1)
            .notaSaber2(UPDATED_NOTA_SABER_2)
            .notaSaber3(UPDATED_NOTA_SABER_3)
            .notaSaber4(UPDATED_NOTA_SABER_4)
            .notaSaber5(UPDATED_NOTA_SABER_5)
            .notaSaber6(UPDATED_NOTA_SABER_6)
            .notaHacer1(UPDATED_NOTA_HACER_1)
            .notaHacer2(UPDATED_NOTA_HACER_2)
            .notaHacer3(UPDATED_NOTA_HACER_3)
            .notaHacer4(UPDATED_NOTA_HACER_4)
            .notaHacer5(UPDATED_NOTA_HACER_5)
            .notaHacer6(UPDATED_NOTA_HACER_6)
            .notaDecir1(UPDATED_NOTA_DECIR_1)
            .notaDecir2(UPDATED_NOTA_DECIR_2)
            .notaDecir3(UPDATED_NOTA_DECIR_3)
            .notaDecir4(UPDATED_NOTA_DECIR_4)
            .notaDecir5(UPDATED_NOTA_DECIR_5)
            .notaDecir6(UPDATED_NOTA_DECIR_6);

        restBimestreMockMvc.perform(put("/api/bimestres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBimestre)))
            .andExpect(status().isOk());

        // Validate the Bimestre in the database
        List<Bimestre> bimestreList = bimestreRepository.findAll();
        assertThat(bimestreList).hasSize(databaseSizeBeforeUpdate);
        Bimestre testBimestre = bimestreList.get(bimestreList.size() - 1);
        assertThat(testBimestre.getIdCurso()).isEqualTo(UPDATED_ID_CURSO);
        assertThat(testBimestre.getIdDocente()).isEqualTo(UPDATED_ID_DOCENTE);
        assertThat(testBimestre.getBimestre()).isEqualTo(UPDATED_BIMESTRE);
        assertThat(testBimestre.getParalelo()).isEqualTo(UPDATED_PARALELO);
        assertThat(testBimestre.getGestion()).isEqualTo(UPDATED_GESTION);
        assertThat(testBimestre.getIdMateria()).isEqualTo(UPDATED_ID_MATERIA);
        assertThat(testBimestre.getObservacion()).isEqualTo(UPDATED_OBSERVACION);
        assertThat(testBimestre.getIndicadorCualitativo()).isEqualTo(UPDATED_INDICADOR_CUALITATIVO);
        assertThat(testBimestre.getNotaBimestralFinal()).isEqualTo(UPDATED_NOTA_BIMESTRAL_FINAL);
        assertThat(testBimestre.getPromedioAutoevaluacion()).isEqualTo(UPDATED_PROMEDIO_AUTOEVALUACION);
        assertThat(testBimestre.getAutoevaluacionDecir()).isEqualTo(UPDATED_AUTOEVALUACION_DECIR);
        assertThat(testBimestre.getAutoevaluacionSer()).isEqualTo(UPDATED_AUTOEVALUACION_SER);
        assertThat(testBimestre.getDecirPromedio()).isEqualTo(UPDATED_DECIR_PROMEDIO);
        assertThat(testBimestre.getHacerPromedio()).isEqualTo(UPDATED_HACER_PROMEDIO);
        assertThat(testBimestre.getSaberPromedio()).isEqualTo(UPDATED_SABER_PROMEDIO);
        assertThat(testBimestre.getSerPromedio()).isEqualTo(UPDATED_SER_PROMEDIO);
        assertThat(testBimestre.getNotaSer1()).isEqualTo(UPDATED_NOTA_SER_1);
        assertThat(testBimestre.getNotaSer2()).isEqualTo(UPDATED_NOTA_SER_2);
        assertThat(testBimestre.getNotaSer3()).isEqualTo(UPDATED_NOTA_SER_3);
        assertThat(testBimestre.getNotaSer4()).isEqualTo(UPDATED_NOTA_SER_4);
        assertThat(testBimestre.getNotaSer5()).isEqualTo(UPDATED_NOTA_SER_5);
        assertThat(testBimestre.getNotaSer6()).isEqualTo(UPDATED_NOTA_SER_6);
        assertThat(testBimestre.getNotaSaber1()).isEqualTo(UPDATED_NOTA_SABER_1);
        assertThat(testBimestre.getNotaSaber2()).isEqualTo(UPDATED_NOTA_SABER_2);
        assertThat(testBimestre.getNotaSaber3()).isEqualTo(UPDATED_NOTA_SABER_3);
        assertThat(testBimestre.getNotaSaber4()).isEqualTo(UPDATED_NOTA_SABER_4);
        assertThat(testBimestre.getNotaSaber5()).isEqualTo(UPDATED_NOTA_SABER_5);
        assertThat(testBimestre.getNotaSaber6()).isEqualTo(UPDATED_NOTA_SABER_6);
        assertThat(testBimestre.getNotaHacer1()).isEqualTo(UPDATED_NOTA_HACER_1);
        assertThat(testBimestre.getNotaHacer2()).isEqualTo(UPDATED_NOTA_HACER_2);
        assertThat(testBimestre.getNotaHacer3()).isEqualTo(UPDATED_NOTA_HACER_3);
        assertThat(testBimestre.getNotaHacer4()).isEqualTo(UPDATED_NOTA_HACER_4);
        assertThat(testBimestre.getNotaHacer5()).isEqualTo(UPDATED_NOTA_HACER_5);
        assertThat(testBimestre.getNotaHacer6()).isEqualTo(UPDATED_NOTA_HACER_6);
        assertThat(testBimestre.getNotaDecir1()).isEqualTo(UPDATED_NOTA_DECIR_1);
        assertThat(testBimestre.getNotaDecir2()).isEqualTo(UPDATED_NOTA_DECIR_2);
        assertThat(testBimestre.getNotaDecir3()).isEqualTo(UPDATED_NOTA_DECIR_3);
        assertThat(testBimestre.getNotaDecir4()).isEqualTo(UPDATED_NOTA_DECIR_4);
        assertThat(testBimestre.getNotaDecir5()).isEqualTo(UPDATED_NOTA_DECIR_5);
        assertThat(testBimestre.getNotaDecir6()).isEqualTo(UPDATED_NOTA_DECIR_6);
    }

    @Test
    @Transactional
    public void updateNonExistingBimestre() throws Exception {
        int databaseSizeBeforeUpdate = bimestreRepository.findAll().size();

        // Create the Bimestre

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBimestreMockMvc.perform(put("/api/bimestres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bimestre)))
            .andExpect(status().isCreated());

        // Validate the Bimestre in the database
        List<Bimestre> bimestreList = bimestreRepository.findAll();
        assertThat(bimestreList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBimestre() throws Exception {
        // Initialize the database
        bimestreService.save(bimestre);

        int databaseSizeBeforeDelete = bimestreRepository.findAll().size();

        // Get the bimestre
        restBimestreMockMvc.perform(delete("/api/bimestres/{id}", bimestre.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bimestre> bimestreList = bimestreRepository.findAll();
        assertThat(bimestreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bimestre.class);
        Bimestre bimestre1 = new Bimestre();
        bimestre1.setId(1L);
        Bimestre bimestre2 = new Bimestre();
        bimestre2.setId(bimestre1.getId());
        assertThat(bimestre1).isEqualTo(bimestre2);
        bimestre2.setId(2L);
        assertThat(bimestre1).isNotEqualTo(bimestre2);
        bimestre1.setId(null);
        assertThat(bimestre1).isNotEqualTo(bimestre2);
    }
}
