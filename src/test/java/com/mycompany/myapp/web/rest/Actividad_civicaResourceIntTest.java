package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimonBolivarApp;

import com.mycompany.myapp.domain.Actividad_civica;
import com.mycompany.myapp.repository.Actividad_civicaRepository;
import com.mycompany.myapp.service.Actividad_civicaService;
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
 * Test class for the Actividad_civicaResource REST controller.
 *
 * @see Actividad_civicaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimonBolivarApp.class)
public class Actividad_civicaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private Actividad_civicaRepository actividad_civicaRepository;

    @Autowired
    private Actividad_civicaService actividad_civicaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActividad_civicaMockMvc;

    private Actividad_civica actividad_civica;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Actividad_civicaResource actividad_civicaResource = new Actividad_civicaResource(actividad_civicaService);
        this.restActividad_civicaMockMvc = MockMvcBuilders.standaloneSetup(actividad_civicaResource)
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
    public static Actividad_civica createEntity(EntityManager em) {
        Actividad_civica actividad_civica = new Actividad_civica()
            .nombre(DEFAULT_NOMBRE);
        return actividad_civica;
    }

    @Before
    public void initTest() {
        actividad_civica = createEntity(em);
    }

    @Test
    @Transactional
    public void createActividad_civica() throws Exception {
        int databaseSizeBeforeCreate = actividad_civicaRepository.findAll().size();

        // Create the Actividad_civica
        restActividad_civicaMockMvc.perform(post("/api/actividad-civicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actividad_civica)))
            .andExpect(status().isCreated());

        // Validate the Actividad_civica in the database
        List<Actividad_civica> actividad_civicaList = actividad_civicaRepository.findAll();
        assertThat(actividad_civicaList).hasSize(databaseSizeBeforeCreate + 1);
        Actividad_civica testActividad_civica = actividad_civicaList.get(actividad_civicaList.size() - 1);
        assertThat(testActividad_civica.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createActividad_civicaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actividad_civicaRepository.findAll().size();

        // Create the Actividad_civica with an existing ID
        actividad_civica.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActividad_civicaMockMvc.perform(post("/api/actividad-civicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actividad_civica)))
            .andExpect(status().isBadRequest());

        // Validate the Actividad_civica in the database
        List<Actividad_civica> actividad_civicaList = actividad_civicaRepository.findAll();
        assertThat(actividad_civicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllActividad_civicas() throws Exception {
        // Initialize the database
        actividad_civicaRepository.saveAndFlush(actividad_civica);

        // Get all the actividad_civicaList
        restActividad_civicaMockMvc.perform(get("/api/actividad-civicas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actividad_civica.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getActividad_civica() throws Exception {
        // Initialize the database
        actividad_civicaRepository.saveAndFlush(actividad_civica);

        // Get the actividad_civica
        restActividad_civicaMockMvc.perform(get("/api/actividad-civicas/{id}", actividad_civica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(actividad_civica.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActividad_civica() throws Exception {
        // Get the actividad_civica
        restActividad_civicaMockMvc.perform(get("/api/actividad-civicas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActividad_civica() throws Exception {
        // Initialize the database
        actividad_civicaService.save(actividad_civica);

        int databaseSizeBeforeUpdate = actividad_civicaRepository.findAll().size();

        // Update the actividad_civica
        Actividad_civica updatedActividad_civica = actividad_civicaRepository.findOne(actividad_civica.getId());
        // Disconnect from session so that the updates on updatedActividad_civica are not directly saved in db
        em.detach(updatedActividad_civica);
        updatedActividad_civica
            .nombre(UPDATED_NOMBRE);

        restActividad_civicaMockMvc.perform(put("/api/actividad-civicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedActividad_civica)))
            .andExpect(status().isOk());

        // Validate the Actividad_civica in the database
        List<Actividad_civica> actividad_civicaList = actividad_civicaRepository.findAll();
        assertThat(actividad_civicaList).hasSize(databaseSizeBeforeUpdate);
        Actividad_civica testActividad_civica = actividad_civicaList.get(actividad_civicaList.size() - 1);
        assertThat(testActividad_civica.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingActividad_civica() throws Exception {
        int databaseSizeBeforeUpdate = actividad_civicaRepository.findAll().size();

        // Create the Actividad_civica

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActividad_civicaMockMvc.perform(put("/api/actividad-civicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actividad_civica)))
            .andExpect(status().isCreated());

        // Validate the Actividad_civica in the database
        List<Actividad_civica> actividad_civicaList = actividad_civicaRepository.findAll();
        assertThat(actividad_civicaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteActividad_civica() throws Exception {
        // Initialize the database
        actividad_civicaService.save(actividad_civica);

        int databaseSizeBeforeDelete = actividad_civicaRepository.findAll().size();

        // Get the actividad_civica
        restActividad_civicaMockMvc.perform(delete("/api/actividad-civicas/{id}", actividad_civica.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Actividad_civica> actividad_civicaList = actividad_civicaRepository.findAll();
        assertThat(actividad_civicaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Actividad_civica.class);
        Actividad_civica actividad_civica1 = new Actividad_civica();
        actividad_civica1.setId(1L);
        Actividad_civica actividad_civica2 = new Actividad_civica();
        actividad_civica2.setId(actividad_civica1.getId());
        assertThat(actividad_civica1).isEqualTo(actividad_civica2);
        actividad_civica2.setId(2L);
        assertThat(actividad_civica1).isNotEqualTo(actividad_civica2);
        actividad_civica1.setId(null);
        assertThat(actividad_civica1).isNotEqualTo(actividad_civica2);
    }
}
