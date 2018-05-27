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

    private static final String DEFAULT_NOTAS_SER = "AAAAAAAAAA";
    private static final String UPDATED_NOTAS_SER = "BBBBBBBBBB";

    private static final String DEFAULT_NOTAS_SABER = "AAAAAAAAAA";
    private static final String UPDATED_NOTAS_SABER = "BBBBBBBBBB";

    private static final String DEFAULT_NOTAS_HACER = "AAAAAAAAAA";
    private static final String UPDATED_NOTAS_HACER = "BBBBBBBBBB";

    private static final String DEFAULT_NOTAS_DECIR = "AAAAAAAAAA";
    private static final String UPDATED_NOTAS_DECIR = "BBBBBBBBBB";

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
            .notasSer(DEFAULT_NOTAS_SER)
            .notasSaber(DEFAULT_NOTAS_SABER)
            .notasHacer(DEFAULT_NOTAS_HACER)
            .notasDecir(DEFAULT_NOTAS_DECIR);
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
        assertThat(testBimestre.getNotasSer()).isEqualTo(DEFAULT_NOTAS_SER);
        assertThat(testBimestre.getNotasSaber()).isEqualTo(DEFAULT_NOTAS_SABER);
        assertThat(testBimestre.getNotasHacer()).isEqualTo(DEFAULT_NOTAS_HACER);
        assertThat(testBimestre.getNotasDecir()).isEqualTo(DEFAULT_NOTAS_DECIR);
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
            .andExpect(jsonPath("$.[*].notasSer").value(hasItem(DEFAULT_NOTAS_SER.toString())))
            .andExpect(jsonPath("$.[*].notasSaber").value(hasItem(DEFAULT_NOTAS_SABER.toString())))
            .andExpect(jsonPath("$.[*].notasHacer").value(hasItem(DEFAULT_NOTAS_HACER.toString())))
            .andExpect(jsonPath("$.[*].notasDecir").value(hasItem(DEFAULT_NOTAS_DECIR.toString())));
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
            .andExpect(jsonPath("$.notasSer").value(DEFAULT_NOTAS_SER.toString()))
            .andExpect(jsonPath("$.notasSaber").value(DEFAULT_NOTAS_SABER.toString()))
            .andExpect(jsonPath("$.notasHacer").value(DEFAULT_NOTAS_HACER.toString()))
            .andExpect(jsonPath("$.notasDecir").value(DEFAULT_NOTAS_DECIR.toString()));
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
            .notasSer(UPDATED_NOTAS_SER)
            .notasSaber(UPDATED_NOTAS_SABER)
            .notasHacer(UPDATED_NOTAS_HACER)
            .notasDecir(UPDATED_NOTAS_DECIR);

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
        assertThat(testBimestre.getNotasSer()).isEqualTo(UPDATED_NOTAS_SER);
        assertThat(testBimestre.getNotasSaber()).isEqualTo(UPDATED_NOTAS_SABER);
        assertThat(testBimestre.getNotasHacer()).isEqualTo(UPDATED_NOTAS_HACER);
        assertThat(testBimestre.getNotasDecir()).isEqualTo(UPDATED_NOTAS_DECIR);
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
