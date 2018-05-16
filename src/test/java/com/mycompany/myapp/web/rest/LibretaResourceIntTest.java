package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimonBolivarApp;

import com.mycompany.myapp.domain.Libreta;
import com.mycompany.myapp.repository.LibretaRepository;
import com.mycompany.myapp.service.LibretaService;
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
 * Test class for the LibretaResource REST controller.
 *
 * @see LibretaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimonBolivarApp.class)
public class LibretaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACION = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_ESTUDIANTE = 1L;
    private static final Long UPDATED_ID_ESTUDIANTE = 2L;

    @Autowired
    private LibretaRepository libretaRepository;

    @Autowired
    private LibretaService libretaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLibretaMockMvc;

    private Libreta libreta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LibretaResource libretaResource = new LibretaResource(libretaService);
        this.restLibretaMockMvc = MockMvcBuilders.standaloneSetup(libretaResource)
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
    public static Libreta createEntity(EntityManager em) {
        Libreta libreta = new Libreta()
            .nombre(DEFAULT_NOMBRE)
            .tipo(DEFAULT_TIPO)
            .observacion(DEFAULT_OBSERVACION)
            .idEstudiante(DEFAULT_ID_ESTUDIANTE);
        return libreta;
    }

    @Before
    public void initTest() {
        libreta = createEntity(em);
    }

    @Test
    @Transactional
    public void createLibreta() throws Exception {
        int databaseSizeBeforeCreate = libretaRepository.findAll().size();

        // Create the Libreta
        restLibretaMockMvc.perform(post("/api/libretas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libreta)))
            .andExpect(status().isCreated());

        // Validate the Libreta in the database
        List<Libreta> libretaList = libretaRepository.findAll();
        assertThat(libretaList).hasSize(databaseSizeBeforeCreate + 1);
        Libreta testLibreta = libretaList.get(libretaList.size() - 1);
        assertThat(testLibreta.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testLibreta.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testLibreta.getObservacion()).isEqualTo(DEFAULT_OBSERVACION);
        assertThat(testLibreta.getIdEstudiante()).isEqualTo(DEFAULT_ID_ESTUDIANTE);
    }

    @Test
    @Transactional
    public void createLibretaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = libretaRepository.findAll().size();

        // Create the Libreta with an existing ID
        libreta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibretaMockMvc.perform(post("/api/libretas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libreta)))
            .andExpect(status().isBadRequest());

        // Validate the Libreta in the database
        List<Libreta> libretaList = libretaRepository.findAll();
        assertThat(libretaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLibretas() throws Exception {
        // Initialize the database
        libretaRepository.saveAndFlush(libreta);

        // Get all the libretaList
        restLibretaMockMvc.perform(get("/api/libretas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(libreta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].observacion").value(hasItem(DEFAULT_OBSERVACION.toString())))
            .andExpect(jsonPath("$.[*].idEstudiante").value(hasItem(DEFAULT_ID_ESTUDIANTE.intValue())));
    }

    @Test
    @Transactional
    public void getLibreta() throws Exception {
        // Initialize the database
        libretaRepository.saveAndFlush(libreta);

        // Get the libreta
        restLibretaMockMvc.perform(get("/api/libretas/{id}", libreta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(libreta.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.observacion").value(DEFAULT_OBSERVACION.toString()))
            .andExpect(jsonPath("$.idEstudiante").value(DEFAULT_ID_ESTUDIANTE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLibreta() throws Exception {
        // Get the libreta
        restLibretaMockMvc.perform(get("/api/libretas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibreta() throws Exception {
        // Initialize the database
        libretaService.save(libreta);

        int databaseSizeBeforeUpdate = libretaRepository.findAll().size();

        // Update the libreta
        Libreta updatedLibreta = libretaRepository.findOne(libreta.getId());
        // Disconnect from session so that the updates on updatedLibreta are not directly saved in db
        em.detach(updatedLibreta);
        updatedLibreta
            .nombre(UPDATED_NOMBRE)
            .tipo(UPDATED_TIPO)
            .observacion(UPDATED_OBSERVACION)
            .idEstudiante(UPDATED_ID_ESTUDIANTE);

        restLibretaMockMvc.perform(put("/api/libretas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLibreta)))
            .andExpect(status().isOk());

        // Validate the Libreta in the database
        List<Libreta> libretaList = libretaRepository.findAll();
        assertThat(libretaList).hasSize(databaseSizeBeforeUpdate);
        Libreta testLibreta = libretaList.get(libretaList.size() - 1);
        assertThat(testLibreta.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testLibreta.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testLibreta.getObservacion()).isEqualTo(UPDATED_OBSERVACION);
        assertThat(testLibreta.getIdEstudiante()).isEqualTo(UPDATED_ID_ESTUDIANTE);
    }

    @Test
    @Transactional
    public void updateNonExistingLibreta() throws Exception {
        int databaseSizeBeforeUpdate = libretaRepository.findAll().size();

        // Create the Libreta

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLibretaMockMvc.perform(put("/api/libretas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libreta)))
            .andExpect(status().isCreated());

        // Validate the Libreta in the database
        List<Libreta> libretaList = libretaRepository.findAll();
        assertThat(libretaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLibreta() throws Exception {
        // Initialize the database
        libretaService.save(libreta);

        int databaseSizeBeforeDelete = libretaRepository.findAll().size();

        // Get the libreta
        restLibretaMockMvc.perform(delete("/api/libretas/{id}", libreta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Libreta> libretaList = libretaRepository.findAll();
        assertThat(libretaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Libreta.class);
        Libreta libreta1 = new Libreta();
        libreta1.setId(1L);
        Libreta libreta2 = new Libreta();
        libreta2.setId(libreta1.getId());
        assertThat(libreta1).isEqualTo(libreta2);
        libreta2.setId(2L);
        assertThat(libreta1).isNotEqualTo(libreta2);
        libreta1.setId(null);
        assertThat(libreta1).isNotEqualTo(libreta2);
    }
}
