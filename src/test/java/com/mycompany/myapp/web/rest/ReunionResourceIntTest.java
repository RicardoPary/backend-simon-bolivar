package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimonBolivarApp;

import com.mycompany.myapp.domain.Reunion;
import com.mycompany.myapp.repository.ReunionRepository;
import com.mycompany.myapp.service.ReunionService;
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
 * Test class for the ReunionResource REST controller.
 *
 * @see ReunionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimonBolivarApp.class)
public class ReunionResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_FECHA = "AAAAAAAAAA";
    private static final String UPDATED_FECHA = "BBBBBBBBBB";

    private static final String DEFAULT_HORA = "AAAAAAAAAA";
    private static final String UPDATED_HORA = "BBBBBBBBBB";

    private static final String DEFAULT_LUGAR = "AAAAAAAAAA";
    private static final String UPDATED_LUGAR = "BBBBBBBBBB";

    private static final String DEFAULT_ORDEN_DIA = "AAAAAAAAAA";
    private static final String UPDATED_ORDEN_DIA = "BBBBBBBBBB";

    private static final String DEFAULT_DETALLE = "AAAAAAAAAA";
    private static final String UPDATED_DETALLE = "BBBBBBBBBB";

    @Autowired
    private ReunionRepository reunionRepository;

    @Autowired
    private ReunionService reunionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReunionMockMvc;

    private Reunion reunion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReunionResource reunionResource = new ReunionResource(reunionService);
        this.restReunionMockMvc = MockMvcBuilders.standaloneSetup(reunionResource)
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
    public static Reunion createEntity(EntityManager em) {
        Reunion reunion = new Reunion()
            .descripcion(DEFAULT_DESCRIPCION)
            .nombre(DEFAULT_NOMBRE)
            .fecha(DEFAULT_FECHA)
            .hora(DEFAULT_HORA)
            .lugar(DEFAULT_LUGAR)
            .ordenDia(DEFAULT_ORDEN_DIA)
            .detalle(DEFAULT_DETALLE);
        return reunion;
    }

    @Before
    public void initTest() {
        reunion = createEntity(em);
    }

    @Test
    @Transactional
    public void createReunion() throws Exception {
        int databaseSizeBeforeCreate = reunionRepository.findAll().size();

        // Create the Reunion
        restReunionMockMvc.perform(post("/api/reunions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reunion)))
            .andExpect(status().isCreated());

        // Validate the Reunion in the database
        List<Reunion> reunionList = reunionRepository.findAll();
        assertThat(reunionList).hasSize(databaseSizeBeforeCreate + 1);
        Reunion testReunion = reunionList.get(reunionList.size() - 1);
        assertThat(testReunion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testReunion.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testReunion.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testReunion.getHora()).isEqualTo(DEFAULT_HORA);
        assertThat(testReunion.getLugar()).isEqualTo(DEFAULT_LUGAR);
        assertThat(testReunion.getOrdenDia()).isEqualTo(DEFAULT_ORDEN_DIA);
        assertThat(testReunion.getDetalle()).isEqualTo(DEFAULT_DETALLE);
    }

    @Test
    @Transactional
    public void createReunionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reunionRepository.findAll().size();

        // Create the Reunion with an existing ID
        reunion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReunionMockMvc.perform(post("/api/reunions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reunion)))
            .andExpect(status().isBadRequest());

        // Validate the Reunion in the database
        List<Reunion> reunionList = reunionRepository.findAll();
        assertThat(reunionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReunions() throws Exception {
        // Initialize the database
        reunionRepository.saveAndFlush(reunion);

        // Get all the reunionList
        restReunionMockMvc.perform(get("/api/reunions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reunion.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].hora").value(hasItem(DEFAULT_HORA.toString())))
            .andExpect(jsonPath("$.[*].lugar").value(hasItem(DEFAULT_LUGAR.toString())))
            .andExpect(jsonPath("$.[*].ordenDia").value(hasItem(DEFAULT_ORDEN_DIA.toString())))
            .andExpect(jsonPath("$.[*].detalle").value(hasItem(DEFAULT_DETALLE.toString())));
    }

    @Test
    @Transactional
    public void getReunion() throws Exception {
        // Initialize the database
        reunionRepository.saveAndFlush(reunion);

        // Get the reunion
        restReunionMockMvc.perform(get("/api/reunions/{id}", reunion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reunion.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.hora").value(DEFAULT_HORA.toString()))
            .andExpect(jsonPath("$.lugar").value(DEFAULT_LUGAR.toString()))
            .andExpect(jsonPath("$.ordenDia").value(DEFAULT_ORDEN_DIA.toString()))
            .andExpect(jsonPath("$.detalle").value(DEFAULT_DETALLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReunion() throws Exception {
        // Get the reunion
        restReunionMockMvc.perform(get("/api/reunions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReunion() throws Exception {
        // Initialize the database
        reunionService.save(reunion);

        int databaseSizeBeforeUpdate = reunionRepository.findAll().size();

        // Update the reunion
        Reunion updatedReunion = reunionRepository.findOne(reunion.getId());
        // Disconnect from session so that the updates on updatedReunion are not directly saved in db
        em.detach(updatedReunion);
        updatedReunion
            .descripcion(UPDATED_DESCRIPCION)
            .nombre(UPDATED_NOMBRE)
            .fecha(UPDATED_FECHA)
            .hora(UPDATED_HORA)
            .lugar(UPDATED_LUGAR)
            .ordenDia(UPDATED_ORDEN_DIA)
            .detalle(UPDATED_DETALLE);

        restReunionMockMvc.perform(put("/api/reunions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReunion)))
            .andExpect(status().isOk());

        // Validate the Reunion in the database
        List<Reunion> reunionList = reunionRepository.findAll();
        assertThat(reunionList).hasSize(databaseSizeBeforeUpdate);
        Reunion testReunion = reunionList.get(reunionList.size() - 1);
        assertThat(testReunion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testReunion.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testReunion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testReunion.getHora()).isEqualTo(UPDATED_HORA);
        assertThat(testReunion.getLugar()).isEqualTo(UPDATED_LUGAR);
        assertThat(testReunion.getOrdenDia()).isEqualTo(UPDATED_ORDEN_DIA);
        assertThat(testReunion.getDetalle()).isEqualTo(UPDATED_DETALLE);
    }

    @Test
    @Transactional
    public void updateNonExistingReunion() throws Exception {
        int databaseSizeBeforeUpdate = reunionRepository.findAll().size();

        // Create the Reunion

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReunionMockMvc.perform(put("/api/reunions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reunion)))
            .andExpect(status().isCreated());

        // Validate the Reunion in the database
        List<Reunion> reunionList = reunionRepository.findAll();
        assertThat(reunionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReunion() throws Exception {
        // Initialize the database
        reunionService.save(reunion);

        int databaseSizeBeforeDelete = reunionRepository.findAll().size();

        // Get the reunion
        restReunionMockMvc.perform(delete("/api/reunions/{id}", reunion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Reunion> reunionList = reunionRepository.findAll();
        assertThat(reunionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reunion.class);
        Reunion reunion1 = new Reunion();
        reunion1.setId(1L);
        Reunion reunion2 = new Reunion();
        reunion2.setId(reunion1.getId());
        assertThat(reunion1).isEqualTo(reunion2);
        reunion2.setId(2L);
        assertThat(reunion1).isNotEqualTo(reunion2);
        reunion1.setId(null);
        assertThat(reunion1).isNotEqualTo(reunion2);
    }
}
