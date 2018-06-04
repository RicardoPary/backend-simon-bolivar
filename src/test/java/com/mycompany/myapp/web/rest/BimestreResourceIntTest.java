package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimonBolivarApp;

import com.mycompany.myapp.domain.Bimestre;
import com.mycompany.myapp.repository.BimestreRepository;
import com.mycompany.myapp.service.BimestreService;
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
 * Test class for the BimestreResource REST controller.
 *
 * @see BimestreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimonBolivarApp.class)
public class BimestreResourceIntTest {

    private static final Long DEFAULT_ID_ESTUDIANTE = 1L;
    private static final Long UPDATED_ID_ESTUDIANTE = 2L;

    private static final Long DEFAULT_ID_DOCENTE = 1L;
    private static final Long UPDATED_ID_DOCENTE = 2L;

    private static final String DEFAULT_BIMESTRE = "AAAAAAAAAA";
    private static final String UPDATED_BIMESTRE = "BBBBBBBBBB";

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
        final BimestreResource bimestreResource = new BimestreResource(bimestreService);
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
            .idEstudiante(DEFAULT_ID_ESTUDIANTE)
            .idDocente(DEFAULT_ID_DOCENTE)
            .bimestre(DEFAULT_BIMESTRE)
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
        assertThat(testBimestre.getIdEstudiante()).isEqualTo(DEFAULT_ID_ESTUDIANTE);
        assertThat(testBimestre.getIdDocente()).isEqualTo(DEFAULT_ID_DOCENTE);
        assertThat(testBimestre.getBimestre()).isEqualTo(DEFAULT_BIMESTRE);
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
            .andExpect(jsonPath("$.[*].idEstudiante").value(hasItem(DEFAULT_ID_ESTUDIANTE.intValue())))
            .andExpect(jsonPath("$.[*].idDocente").value(hasItem(DEFAULT_ID_DOCENTE.intValue())))
            .andExpect(jsonPath("$.[*].bimestre").value(hasItem(DEFAULT_BIMESTRE.toString())))
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
            .andExpect(jsonPath("$.idEstudiante").value(DEFAULT_ID_ESTUDIANTE.intValue()))
            .andExpect(jsonPath("$.idDocente").value(DEFAULT_ID_DOCENTE.intValue()))
            .andExpect(jsonPath("$.bimestre").value(DEFAULT_BIMESTRE.toString()))
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
            .idEstudiante(UPDATED_ID_ESTUDIANTE)
            .idDocente(UPDATED_ID_DOCENTE)
            .bimestre(UPDATED_BIMESTRE)
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
        assertThat(testBimestre.getIdEstudiante()).isEqualTo(UPDATED_ID_ESTUDIANTE);
        assertThat(testBimestre.getIdDocente()).isEqualTo(UPDATED_ID_DOCENTE);
        assertThat(testBimestre.getBimestre()).isEqualTo(UPDATED_BIMESTRE);
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
