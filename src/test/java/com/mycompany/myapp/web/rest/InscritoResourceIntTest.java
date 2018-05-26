package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimonBolivarApp;

import com.mycompany.myapp.domain.Inscrito;
import com.mycompany.myapp.repository.InscritoRepository;
import com.mycompany.myapp.service.InscritoService;
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
 * Test class for the InscritoResource REST controller.
 *
 * @see InscritoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimonBolivarApp.class)
public class InscritoResourceIntTest {

    private static final Long DEFAULT_ID_ESTUDIANTE = 1L;
    private static final Long UPDATED_ID_ESTUDIANTE = 2L;

    private static final Long DEFAULT_ID_MATERIA = 1L;
    private static final Long UPDATED_ID_MATERIA = 2L;

    private static final Double DEFAULT_NOTA = 1D;
    private static final Double UPDATED_NOTA = 2D;

    private static final Long DEFAULT_ID_LIBRETA = 1L;
    private static final Long UPDATED_ID_LIBRETA = 2L;

    @Autowired
    private InscritoRepository inscritoRepository;

    @Autowired
    private InscritoService inscritoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInscritoMockMvc;

    private Inscrito inscrito;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InscritoResource inscritoResource = new InscritoResource(inscritoService);
        this.restInscritoMockMvc = MockMvcBuilders.standaloneSetup(inscritoResource)
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
    public static Inscrito createEntity(EntityManager em) {
        Inscrito inscrito = new Inscrito()
            .idEstudiante(DEFAULT_ID_ESTUDIANTE)
            .idMateria(DEFAULT_ID_MATERIA)
            .nota(DEFAULT_NOTA)
            .idLibreta(DEFAULT_ID_LIBRETA);
        return inscrito;
    }

    @Before
    public void initTest() {
        inscrito = createEntity(em);
    }

    @Test
    @Transactional
    public void createInscrito() throws Exception {
        int databaseSizeBeforeCreate = inscritoRepository.findAll().size();

        // Create the Inscrito
        restInscritoMockMvc.perform(post("/api/inscritos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inscrito)))
            .andExpect(status().isCreated());

        // Validate the Inscrito in the database
        List<Inscrito> inscritoList = inscritoRepository.findAll();
        assertThat(inscritoList).hasSize(databaseSizeBeforeCreate + 1);
        Inscrito testInscrito = inscritoList.get(inscritoList.size() - 1);
        assertThat(testInscrito.getIdEstudiante()).isEqualTo(DEFAULT_ID_ESTUDIANTE);
        assertThat(testInscrito.getIdMateria()).isEqualTo(DEFAULT_ID_MATERIA);
        assertThat(testInscrito.getNota()).isEqualTo(DEFAULT_NOTA);
        assertThat(testInscrito.getIdLibreta()).isEqualTo(DEFAULT_ID_LIBRETA);
    }

    @Test
    @Transactional
    public void createInscritoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inscritoRepository.findAll().size();

        // Create the Inscrito with an existing ID
        inscrito.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInscritoMockMvc.perform(post("/api/inscritos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inscrito)))
            .andExpect(status().isBadRequest());

        // Validate the Inscrito in the database
        List<Inscrito> inscritoList = inscritoRepository.findAll();
        assertThat(inscritoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInscritos() throws Exception {
        // Initialize the database
        inscritoRepository.saveAndFlush(inscrito);

        // Get all the inscritoList
        restInscritoMockMvc.perform(get("/api/inscritos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inscrito.getId().intValue())))
            .andExpect(jsonPath("$.[*].idEstudiante").value(hasItem(DEFAULT_ID_ESTUDIANTE.intValue())))
            .andExpect(jsonPath("$.[*].idMateria").value(hasItem(DEFAULT_ID_MATERIA.intValue())))
            .andExpect(jsonPath("$.[*].nota").value(hasItem(DEFAULT_NOTA.doubleValue())))
            .andExpect(jsonPath("$.[*].idLibreta").value(hasItem(DEFAULT_ID_LIBRETA.intValue())));
    }

    @Test
    @Transactional
    public void getInscrito() throws Exception {
        // Initialize the database
        inscritoRepository.saveAndFlush(inscrito);

        // Get the inscrito
        restInscritoMockMvc.perform(get("/api/inscritos/{id}", inscrito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inscrito.getId().intValue()))
            .andExpect(jsonPath("$.idEstudiante").value(DEFAULT_ID_ESTUDIANTE.intValue()))
            .andExpect(jsonPath("$.idMateria").value(DEFAULT_ID_MATERIA.intValue()))
            .andExpect(jsonPath("$.nota").value(DEFAULT_NOTA.doubleValue()))
            .andExpect(jsonPath("$.idLibreta").value(DEFAULT_ID_LIBRETA.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInscrito() throws Exception {
        // Get the inscrito
        restInscritoMockMvc.perform(get("/api/inscritos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInscrito() throws Exception {
        // Initialize the database
        inscritoService.save(inscrito);

        int databaseSizeBeforeUpdate = inscritoRepository.findAll().size();

        // Update the inscrito
        Inscrito updatedInscrito = inscritoRepository.findOne(inscrito.getId());
        // Disconnect from session so that the updates on updatedInscrito are not directly saved in db
        em.detach(updatedInscrito);
        updatedInscrito
            .idEstudiante(UPDATED_ID_ESTUDIANTE)
            .idMateria(UPDATED_ID_MATERIA)
            .nota(UPDATED_NOTA)
            .idLibreta(UPDATED_ID_LIBRETA);

        restInscritoMockMvc.perform(put("/api/inscritos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInscrito)))
            .andExpect(status().isOk());

        // Validate the Inscrito in the database
        List<Inscrito> inscritoList = inscritoRepository.findAll();
        assertThat(inscritoList).hasSize(databaseSizeBeforeUpdate);
        Inscrito testInscrito = inscritoList.get(inscritoList.size() - 1);
        assertThat(testInscrito.getIdEstudiante()).isEqualTo(UPDATED_ID_ESTUDIANTE);
        assertThat(testInscrito.getIdMateria()).isEqualTo(UPDATED_ID_MATERIA);
        assertThat(testInscrito.getNota()).isEqualTo(UPDATED_NOTA);
        assertThat(testInscrito.getIdLibreta()).isEqualTo(UPDATED_ID_LIBRETA);
    }

    @Test
    @Transactional
    public void updateNonExistingInscrito() throws Exception {
        int databaseSizeBeforeUpdate = inscritoRepository.findAll().size();

        // Create the Inscrito

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInscritoMockMvc.perform(put("/api/inscritos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inscrito)))
            .andExpect(status().isCreated());

        // Validate the Inscrito in the database
        List<Inscrito> inscritoList = inscritoRepository.findAll();
        assertThat(inscritoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInscrito() throws Exception {
        // Initialize the database
        inscritoService.save(inscrito);

        int databaseSizeBeforeDelete = inscritoRepository.findAll().size();

        // Get the inscrito
        restInscritoMockMvc.perform(delete("/api/inscritos/{id}", inscrito.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Inscrito> inscritoList = inscritoRepository.findAll();
        assertThat(inscritoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inscrito.class);
        Inscrito inscrito1 = new Inscrito();
        inscrito1.setId(1L);
        Inscrito inscrito2 = new Inscrito();
        inscrito2.setId(inscrito1.getId());
        assertThat(inscrito1).isEqualTo(inscrito2);
        inscrito2.setId(2L);
        assertThat(inscrito1).isNotEqualTo(inscrito2);
        inscrito1.setId(null);
        assertThat(inscrito1).isNotEqualTo(inscrito2);
    }
}
