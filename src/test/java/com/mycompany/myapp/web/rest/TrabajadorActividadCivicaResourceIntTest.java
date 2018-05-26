package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimonBolivarApp;

import com.mycompany.myapp.domain.TrabajadorActividadCivica;
import com.mycompany.myapp.repository.TrabajadorActividadCivicaRepository;
import com.mycompany.myapp.service.TrabajadorActividadCivicaService;
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
 * Test class for the TrabajadorActividadCivicaResource REST controller.
 *
 * @see TrabajadorActividadCivicaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimonBolivarApp.class)
public class TrabajadorActividadCivicaResourceIntTest {

    private static final Long DEFAULT_ID_TRABAJADOR = 1L;
    private static final Long UPDATED_ID_TRABAJADOR = 2L;

    @Autowired
    private TrabajadorActividadCivicaRepository trabajadorActividadCivicaRepository;

    @Autowired
    private TrabajadorActividadCivicaService trabajadorActividadCivicaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTrabajadorActividadCivicaMockMvc;

    private TrabajadorActividadCivica trabajadorActividadCivica;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrabajadorActividadCivicaResource trabajadorActividadCivicaResource = new TrabajadorActividadCivicaResource(trabajadorActividadCivicaService);
        this.restTrabajadorActividadCivicaMockMvc = MockMvcBuilders.standaloneSetup(trabajadorActividadCivicaResource)
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
    public static TrabajadorActividadCivica createEntity(EntityManager em) {
        TrabajadorActividadCivica trabajadorActividadCivica = new TrabajadorActividadCivica()
            .idTrabajador(DEFAULT_ID_TRABAJADOR);
        return trabajadorActividadCivica;
    }

    @Before
    public void initTest() {
        trabajadorActividadCivica = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrabajadorActividadCivica() throws Exception {
        int databaseSizeBeforeCreate = trabajadorActividadCivicaRepository.findAll().size();

        // Create the TrabajadorActividadCivica
        restTrabajadorActividadCivicaMockMvc.perform(post("/api/trabajador-actividad-civicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trabajadorActividadCivica)))
            .andExpect(status().isCreated());

        // Validate the TrabajadorActividadCivica in the database
        List<TrabajadorActividadCivica> trabajadorActividadCivicaList = trabajadorActividadCivicaRepository.findAll();
        assertThat(trabajadorActividadCivicaList).hasSize(databaseSizeBeforeCreate + 1);
        TrabajadorActividadCivica testTrabajadorActividadCivica = trabajadorActividadCivicaList.get(trabajadorActividadCivicaList.size() - 1);
        assertThat(testTrabajadorActividadCivica.getIdTrabajador()).isEqualTo(DEFAULT_ID_TRABAJADOR);
    }

    @Test
    @Transactional
    public void createTrabajadorActividadCivicaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trabajadorActividadCivicaRepository.findAll().size();

        // Create the TrabajadorActividadCivica with an existing ID
        trabajadorActividadCivica.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrabajadorActividadCivicaMockMvc.perform(post("/api/trabajador-actividad-civicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trabajadorActividadCivica)))
            .andExpect(status().isBadRequest());

        // Validate the TrabajadorActividadCivica in the database
        List<TrabajadorActividadCivica> trabajadorActividadCivicaList = trabajadorActividadCivicaRepository.findAll();
        assertThat(trabajadorActividadCivicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTrabajadorActividadCivicas() throws Exception {
        // Initialize the database
        trabajadorActividadCivicaRepository.saveAndFlush(trabajadorActividadCivica);

        // Get all the trabajadorActividadCivicaList
        restTrabajadorActividadCivicaMockMvc.perform(get("/api/trabajador-actividad-civicas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trabajadorActividadCivica.getId().intValue())))
            .andExpect(jsonPath("$.[*].idTrabajador").value(hasItem(DEFAULT_ID_TRABAJADOR.intValue())));
    }

    @Test
    @Transactional
    public void getTrabajadorActividadCivica() throws Exception {
        // Initialize the database
        trabajadorActividadCivicaRepository.saveAndFlush(trabajadorActividadCivica);

        // Get the trabajadorActividadCivica
        restTrabajadorActividadCivicaMockMvc.perform(get("/api/trabajador-actividad-civicas/{id}", trabajadorActividadCivica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trabajadorActividadCivica.getId().intValue()))
            .andExpect(jsonPath("$.idTrabajador").value(DEFAULT_ID_TRABAJADOR.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTrabajadorActividadCivica() throws Exception {
        // Get the trabajadorActividadCivica
        restTrabajadorActividadCivicaMockMvc.perform(get("/api/trabajador-actividad-civicas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrabajadorActividadCivica() throws Exception {
        // Initialize the database
        trabajadorActividadCivicaService.save(trabajadorActividadCivica);

        int databaseSizeBeforeUpdate = trabajadorActividadCivicaRepository.findAll().size();

        // Update the trabajadorActividadCivica
        TrabajadorActividadCivica updatedTrabajadorActividadCivica = trabajadorActividadCivicaRepository.findOne(trabajadorActividadCivica.getId());
        // Disconnect from session so that the updates on updatedTrabajadorActividadCivica are not directly saved in db
        em.detach(updatedTrabajadorActividadCivica);
        updatedTrabajadorActividadCivica
            .idTrabajador(UPDATED_ID_TRABAJADOR);

        restTrabajadorActividadCivicaMockMvc.perform(put("/api/trabajador-actividad-civicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrabajadorActividadCivica)))
            .andExpect(status().isOk());

        // Validate the TrabajadorActividadCivica in the database
        List<TrabajadorActividadCivica> trabajadorActividadCivicaList = trabajadorActividadCivicaRepository.findAll();
        assertThat(trabajadorActividadCivicaList).hasSize(databaseSizeBeforeUpdate);
        TrabajadorActividadCivica testTrabajadorActividadCivica = trabajadorActividadCivicaList.get(trabajadorActividadCivicaList.size() - 1);
        assertThat(testTrabajadorActividadCivica.getIdTrabajador()).isEqualTo(UPDATED_ID_TRABAJADOR);
    }

    @Test
    @Transactional
    public void updateNonExistingTrabajadorActividadCivica() throws Exception {
        int databaseSizeBeforeUpdate = trabajadorActividadCivicaRepository.findAll().size();

        // Create the TrabajadorActividadCivica

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTrabajadorActividadCivicaMockMvc.perform(put("/api/trabajador-actividad-civicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trabajadorActividadCivica)))
            .andExpect(status().isCreated());

        // Validate the TrabajadorActividadCivica in the database
        List<TrabajadorActividadCivica> trabajadorActividadCivicaList = trabajadorActividadCivicaRepository.findAll();
        assertThat(trabajadorActividadCivicaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTrabajadorActividadCivica() throws Exception {
        // Initialize the database
        trabajadorActividadCivicaService.save(trabajadorActividadCivica);

        int databaseSizeBeforeDelete = trabajadorActividadCivicaRepository.findAll().size();

        // Get the trabajadorActividadCivica
        restTrabajadorActividadCivicaMockMvc.perform(delete("/api/trabajador-actividad-civicas/{id}", trabajadorActividadCivica.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TrabajadorActividadCivica> trabajadorActividadCivicaList = trabajadorActividadCivicaRepository.findAll();
        assertThat(trabajadorActividadCivicaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrabajadorActividadCivica.class);
        TrabajadorActividadCivica trabajadorActividadCivica1 = new TrabajadorActividadCivica();
        trabajadorActividadCivica1.setId(1L);
        TrabajadorActividadCivica trabajadorActividadCivica2 = new TrabajadorActividadCivica();
        trabajadorActividadCivica2.setId(trabajadorActividadCivica1.getId());
        assertThat(trabajadorActividadCivica1).isEqualTo(trabajadorActividadCivica2);
        trabajadorActividadCivica2.setId(2L);
        assertThat(trabajadorActividadCivica1).isNotEqualTo(trabajadorActividadCivica2);
        trabajadorActividadCivica1.setId(null);
        assertThat(trabajadorActividadCivica1).isNotEqualTo(trabajadorActividadCivica2);
    }
}
