package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimonBolivarApp;

import com.mycompany.myapp.domain.Contrato;
import com.mycompany.myapp.repository.ContratoRepository;
import com.mycompany.myapp.service.ContratoService;
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
 * Test class for the ContratoResource REST controller.
 *
 * @see ContratoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimonBolivarApp.class)
public class ContratoResourceIntTest {

    private static final String DEFAULT_FECHA_INICIO = "AAAAAAAAAA";
    private static final String UPDATED_FECHA_INICIO = "BBBBBBBBBB";

    private static final String DEFAULT_FECHA_FIN = "AAAAAAAAAA";
    private static final String UPDATED_FECHA_FIN = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final Double DEFAULT_SUELDO = 1D;
    private static final Double UPDATED_SUELDO = 2D;

    private static final String DEFAULT_VACACIONES = "AAAAAAAAAA";
    private static final String UPDATED_VACACIONES = "BBBBBBBBBB";

    private static final String DEFAULT_JORNADA_DIARIA = "AAAAAAAAAA";
    private static final String UPDATED_JORNADA_DIARIA = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_DOCENTE = 1L;
    private static final Long UPDATED_ID_DOCENTE = 2L;

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContratoMockMvc;

    private Contrato contrato;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContratoResource contratoResource = new ContratoResource(contratoService);
        this.restContratoMockMvc = MockMvcBuilders.standaloneSetup(contratoResource)
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
    public static Contrato createEntity(EntityManager em) {
        Contrato contrato = new Contrato()
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN)
            .tipo(DEFAULT_TIPO)
            .sueldo(DEFAULT_SUELDO)
            .vacaciones(DEFAULT_VACACIONES)
            .jornadaDiaria(DEFAULT_JORNADA_DIARIA)
            .idDocente(DEFAULT_ID_DOCENTE);
        return contrato;
    }

    @Before
    public void initTest() {
        contrato = createEntity(em);
    }

    @Test
    @Transactional
    public void createContrato() throws Exception {
        int databaseSizeBeforeCreate = contratoRepository.findAll().size();

        // Create the Contrato
        restContratoMockMvc.perform(post("/api/contratoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contrato)))
            .andExpect(status().isCreated());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeCreate + 1);
        Contrato testContrato = contratoList.get(contratoList.size() - 1);
        assertThat(testContrato.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testContrato.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
        assertThat(testContrato.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testContrato.getSueldo()).isEqualTo(DEFAULT_SUELDO);
        assertThat(testContrato.getVacaciones()).isEqualTo(DEFAULT_VACACIONES);
        assertThat(testContrato.getJornadaDiaria()).isEqualTo(DEFAULT_JORNADA_DIARIA);
        assertThat(testContrato.getIdDocente()).isEqualTo(DEFAULT_ID_DOCENTE);
    }

    @Test
    @Transactional
    public void createContratoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contratoRepository.findAll().size();

        // Create the Contrato with an existing ID
        contrato.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContratoMockMvc.perform(post("/api/contratoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contrato)))
            .andExpect(status().isBadRequest());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllContratoes() throws Exception {
        // Initialize the database
        contratoRepository.saveAndFlush(contrato);

        // Get all the contratoList
        restContratoMockMvc.perform(get("/api/contratoes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contrato.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].sueldo").value(hasItem(DEFAULT_SUELDO.doubleValue())))
            .andExpect(jsonPath("$.[*].vacaciones").value(hasItem(DEFAULT_VACACIONES.toString())))
            .andExpect(jsonPath("$.[*].jornadaDiaria").value(hasItem(DEFAULT_JORNADA_DIARIA.toString())))
            .andExpect(jsonPath("$.[*].idDocente").value(hasItem(DEFAULT_ID_DOCENTE.intValue())));
    }

    @Test
    @Transactional
    public void getContrato() throws Exception {
        // Initialize the database
        contratoRepository.saveAndFlush(contrato);

        // Get the contrato
        restContratoMockMvc.perform(get("/api/contratoes/{id}", contrato.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contrato.getId().intValue()))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.sueldo").value(DEFAULT_SUELDO.doubleValue()))
            .andExpect(jsonPath("$.vacaciones").value(DEFAULT_VACACIONES.toString()))
            .andExpect(jsonPath("$.jornadaDiaria").value(DEFAULT_JORNADA_DIARIA.toString()))
            .andExpect(jsonPath("$.idDocente").value(DEFAULT_ID_DOCENTE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingContrato() throws Exception {
        // Get the contrato
        restContratoMockMvc.perform(get("/api/contratoes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContrato() throws Exception {
        // Initialize the database
        contratoService.save(contrato);

        int databaseSizeBeforeUpdate = contratoRepository.findAll().size();

        // Update the contrato
        Contrato updatedContrato = contratoRepository.findOne(contrato.getId());
        // Disconnect from session so that the updates on updatedContrato are not directly saved in db
        em.detach(updatedContrato);
        updatedContrato
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .tipo(UPDATED_TIPO)
            .sueldo(UPDATED_SUELDO)
            .vacaciones(UPDATED_VACACIONES)
            .jornadaDiaria(UPDATED_JORNADA_DIARIA)
            .idDocente(UPDATED_ID_DOCENTE);

        restContratoMockMvc.perform(put("/api/contratoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContrato)))
            .andExpect(status().isOk());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeUpdate);
        Contrato testContrato = contratoList.get(contratoList.size() - 1);
        assertThat(testContrato.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testContrato.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testContrato.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testContrato.getSueldo()).isEqualTo(UPDATED_SUELDO);
        assertThat(testContrato.getVacaciones()).isEqualTo(UPDATED_VACACIONES);
        assertThat(testContrato.getJornadaDiaria()).isEqualTo(UPDATED_JORNADA_DIARIA);
        assertThat(testContrato.getIdDocente()).isEqualTo(UPDATED_ID_DOCENTE);
    }

    @Test
    @Transactional
    public void updateNonExistingContrato() throws Exception {
        int databaseSizeBeforeUpdate = contratoRepository.findAll().size();

        // Create the Contrato

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContratoMockMvc.perform(put("/api/contratoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contrato)))
            .andExpect(status().isCreated());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContrato() throws Exception {
        // Initialize the database
        contratoService.save(contrato);

        int databaseSizeBeforeDelete = contratoRepository.findAll().size();

        // Get the contrato
        restContratoMockMvc.perform(delete("/api/contratoes/{id}", contrato.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contrato.class);
        Contrato contrato1 = new Contrato();
        contrato1.setId(1L);
        Contrato contrato2 = new Contrato();
        contrato2.setId(contrato1.getId());
        assertThat(contrato1).isEqualTo(contrato2);
        contrato2.setId(2L);
        assertThat(contrato1).isNotEqualTo(contrato2);
        contrato1.setId(null);
        assertThat(contrato1).isNotEqualTo(contrato2);
    }
}
