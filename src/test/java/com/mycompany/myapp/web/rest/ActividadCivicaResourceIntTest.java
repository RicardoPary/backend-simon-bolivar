package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimonBolivarApp;

import com.mycompany.myapp.domain.ActividadCivica;
import com.mycompany.myapp.repository.ActividadCivicaRepository;
import com.mycompany.myapp.service.ActividadCivicaService;
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
 * Test class for the ActividadCivicaResource REST controller.
 *
 * @see ActividadCivicaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimonBolivarApp.class)
public class ActividadCivicaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_CRONOGRAMA = "AAAAAAAAAA";
    private static final String UPDATED_CRONOGRAMA = "BBBBBBBBBB";

    private static final String DEFAULT_FECHA = "AAAAAAAAAA";
    private static final String UPDATED_FECHA = "BBBBBBBBBB";

    @Autowired
    private ActividadCivicaRepository actividadCivicaRepository;

    @Autowired
    private ActividadCivicaService actividadCivicaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActividadCivicaMockMvc;

    private ActividadCivica actividadCivica;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActividadCivicaResource actividadCivicaResource = new ActividadCivicaResource(actividadCivicaService);
        this.restActividadCivicaMockMvc = MockMvcBuilders.standaloneSetup(actividadCivicaResource)
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
    public static ActividadCivica createEntity(EntityManager em) {
        ActividadCivica actividadCivica = new ActividadCivica()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .cronograma(DEFAULT_CRONOGRAMA)
            .fecha(DEFAULT_FECHA);
        return actividadCivica;
    }

    @Before
    public void initTest() {
        actividadCivica = createEntity(em);
    }

    @Test
    @Transactional
    public void createActividadCivica() throws Exception {
        int databaseSizeBeforeCreate = actividadCivicaRepository.findAll().size();

        // Create the ActividadCivica
        restActividadCivicaMockMvc.perform(post("/api/actividad-civicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actividadCivica)))
            .andExpect(status().isCreated());

        // Validate the ActividadCivica in the database
        List<ActividadCivica> actividadCivicaList = actividadCivicaRepository.findAll();
        assertThat(actividadCivicaList).hasSize(databaseSizeBeforeCreate + 1);
        ActividadCivica testActividadCivica = actividadCivicaList.get(actividadCivicaList.size() - 1);
        assertThat(testActividadCivica.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testActividadCivica.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testActividadCivica.getCronograma()).isEqualTo(DEFAULT_CRONOGRAMA);
        assertThat(testActividadCivica.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createActividadCivicaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actividadCivicaRepository.findAll().size();

        // Create the ActividadCivica with an existing ID
        actividadCivica.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActividadCivicaMockMvc.perform(post("/api/actividad-civicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actividadCivica)))
            .andExpect(status().isBadRequest());

        // Validate the ActividadCivica in the database
        List<ActividadCivica> actividadCivicaList = actividadCivicaRepository.findAll();
        assertThat(actividadCivicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllActividadCivicas() throws Exception {
        // Initialize the database
        actividadCivicaRepository.saveAndFlush(actividadCivica);

        // Get all the actividadCivicaList
        restActividadCivicaMockMvc.perform(get("/api/actividad-civicas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actividadCivica.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].cronograma").value(hasItem(DEFAULT_CRONOGRAMA.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }

    @Test
    @Transactional
    public void getActividadCivica() throws Exception {
        // Initialize the database
        actividadCivicaRepository.saveAndFlush(actividadCivica);

        // Get the actividadCivica
        restActividadCivicaMockMvc.perform(get("/api/actividad-civicas/{id}", actividadCivica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(actividadCivica.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.cronograma").value(DEFAULT_CRONOGRAMA.toString()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActividadCivica() throws Exception {
        // Get the actividadCivica
        restActividadCivicaMockMvc.perform(get("/api/actividad-civicas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActividadCivica() throws Exception {
        // Initialize the database
        actividadCivicaService.save(actividadCivica);

        int databaseSizeBeforeUpdate = actividadCivicaRepository.findAll().size();

        // Update the actividadCivica
        ActividadCivica updatedActividadCivica = actividadCivicaRepository.findOne(actividadCivica.getId());
        // Disconnect from session so that the updates on updatedActividadCivica are not directly saved in db
        em.detach(updatedActividadCivica);
        updatedActividadCivica
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .cronograma(UPDATED_CRONOGRAMA)
            .fecha(UPDATED_FECHA);

        restActividadCivicaMockMvc.perform(put("/api/actividad-civicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedActividadCivica)))
            .andExpect(status().isOk());

        // Validate the ActividadCivica in the database
        List<ActividadCivica> actividadCivicaList = actividadCivicaRepository.findAll();
        assertThat(actividadCivicaList).hasSize(databaseSizeBeforeUpdate);
        ActividadCivica testActividadCivica = actividadCivicaList.get(actividadCivicaList.size() - 1);
        assertThat(testActividadCivica.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testActividadCivica.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testActividadCivica.getCronograma()).isEqualTo(UPDATED_CRONOGRAMA);
        assertThat(testActividadCivica.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingActividadCivica() throws Exception {
        int databaseSizeBeforeUpdate = actividadCivicaRepository.findAll().size();

        // Create the ActividadCivica

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActividadCivicaMockMvc.perform(put("/api/actividad-civicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actividadCivica)))
            .andExpect(status().isCreated());

        // Validate the ActividadCivica in the database
        List<ActividadCivica> actividadCivicaList = actividadCivicaRepository.findAll();
        assertThat(actividadCivicaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteActividadCivica() throws Exception {
        // Initialize the database
        actividadCivicaService.save(actividadCivica);

        int databaseSizeBeforeDelete = actividadCivicaRepository.findAll().size();

        // Get the actividadCivica
        restActividadCivicaMockMvc.perform(delete("/api/actividad-civicas/{id}", actividadCivica.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ActividadCivica> actividadCivicaList = actividadCivicaRepository.findAll();
        assertThat(actividadCivicaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActividadCivica.class);
        ActividadCivica actividadCivica1 = new ActividadCivica();
        actividadCivica1.setId(1L);
        ActividadCivica actividadCivica2 = new ActividadCivica();
        actividadCivica2.setId(actividadCivica1.getId());
        assertThat(actividadCivica1).isEqualTo(actividadCivica2);
        actividadCivica2.setId(2L);
        assertThat(actividadCivica1).isNotEqualTo(actividadCivica2);
        actividadCivica1.setId(null);
        assertThat(actividadCivica1).isNotEqualTo(actividadCivica2);
    }
}
