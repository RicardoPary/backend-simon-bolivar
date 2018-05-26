package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimonBolivarApp;

import com.mycompany.myapp.domain.AulaMateria;
import com.mycompany.myapp.repository.AulaMateriaRepository;
import com.mycompany.myapp.service.AulaMateriaService;
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
 * Test class for the AulaMateriaResource REST controller.
 *
 * @see AulaMateriaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimonBolivarApp.class)
public class AulaMateriaResourceIntTest {

    private static final Long DEFAULT_ID_AULA = 1L;
    private static final Long UPDATED_ID_AULA = 2L;

    private static final Long DEFAULT_ID_MATERIA = 1L;
    private static final Long UPDATED_ID_MATERIA = 2L;

    private static final String DEFAULT_HORARIO = "AAAAAAAAAA";
    private static final String UPDATED_HORARIO = "BBBBBBBBBB";

    @Autowired
    private AulaMateriaRepository aulaMateriaRepository;

    @Autowired
    private AulaMateriaService aulaMateriaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAulaMateriaMockMvc;

    private AulaMateria aulaMateria;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AulaMateriaResource aulaMateriaResource = new AulaMateriaResource(aulaMateriaService);
        this.restAulaMateriaMockMvc = MockMvcBuilders.standaloneSetup(aulaMateriaResource)
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
    public static AulaMateria createEntity(EntityManager em) {
        AulaMateria aulaMateria = new AulaMateria()
            .idAula(DEFAULT_ID_AULA)
            .idMateria(DEFAULT_ID_MATERIA)
            .horario(DEFAULT_HORARIO);
        return aulaMateria;
    }

    @Before
    public void initTest() {
        aulaMateria = createEntity(em);
    }

    @Test
    @Transactional
    public void createAulaMateria() throws Exception {
        int databaseSizeBeforeCreate = aulaMateriaRepository.findAll().size();

        // Create the AulaMateria
        restAulaMateriaMockMvc.perform(post("/api/aula-materias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aulaMateria)))
            .andExpect(status().isCreated());

        // Validate the AulaMateria in the database
        List<AulaMateria> aulaMateriaList = aulaMateriaRepository.findAll();
        assertThat(aulaMateriaList).hasSize(databaseSizeBeforeCreate + 1);
        AulaMateria testAulaMateria = aulaMateriaList.get(aulaMateriaList.size() - 1);
        assertThat(testAulaMateria.getIdAula()).isEqualTo(DEFAULT_ID_AULA);
        assertThat(testAulaMateria.getIdMateria()).isEqualTo(DEFAULT_ID_MATERIA);
        assertThat(testAulaMateria.getHorario()).isEqualTo(DEFAULT_HORARIO);
    }

    @Test
    @Transactional
    public void createAulaMateriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aulaMateriaRepository.findAll().size();

        // Create the AulaMateria with an existing ID
        aulaMateria.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAulaMateriaMockMvc.perform(post("/api/aula-materias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aulaMateria)))
            .andExpect(status().isBadRequest());

        // Validate the AulaMateria in the database
        List<AulaMateria> aulaMateriaList = aulaMateriaRepository.findAll();
        assertThat(aulaMateriaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAulaMaterias() throws Exception {
        // Initialize the database
        aulaMateriaRepository.saveAndFlush(aulaMateria);

        // Get all the aulaMateriaList
        restAulaMateriaMockMvc.perform(get("/api/aula-materias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aulaMateria.getId().intValue())))
            .andExpect(jsonPath("$.[*].idAula").value(hasItem(DEFAULT_ID_AULA.intValue())))
            .andExpect(jsonPath("$.[*].idMateria").value(hasItem(DEFAULT_ID_MATERIA.intValue())))
            .andExpect(jsonPath("$.[*].horario").value(hasItem(DEFAULT_HORARIO.toString())));
    }

    @Test
    @Transactional
    public void getAulaMateria() throws Exception {
        // Initialize the database
        aulaMateriaRepository.saveAndFlush(aulaMateria);

        // Get the aulaMateria
        restAulaMateriaMockMvc.perform(get("/api/aula-materias/{id}", aulaMateria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aulaMateria.getId().intValue()))
            .andExpect(jsonPath("$.idAula").value(DEFAULT_ID_AULA.intValue()))
            .andExpect(jsonPath("$.idMateria").value(DEFAULT_ID_MATERIA.intValue()))
            .andExpect(jsonPath("$.horario").value(DEFAULT_HORARIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAulaMateria() throws Exception {
        // Get the aulaMateria
        restAulaMateriaMockMvc.perform(get("/api/aula-materias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAulaMateria() throws Exception {
        // Initialize the database
        aulaMateriaService.save(aulaMateria);

        int databaseSizeBeforeUpdate = aulaMateriaRepository.findAll().size();

        // Update the aulaMateria
        AulaMateria updatedAulaMateria = aulaMateriaRepository.findOne(aulaMateria.getId());
        // Disconnect from session so that the updates on updatedAulaMateria are not directly saved in db
        em.detach(updatedAulaMateria);
        updatedAulaMateria
            .idAula(UPDATED_ID_AULA)
            .idMateria(UPDATED_ID_MATERIA)
            .horario(UPDATED_HORARIO);

        restAulaMateriaMockMvc.perform(put("/api/aula-materias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAulaMateria)))
            .andExpect(status().isOk());

        // Validate the AulaMateria in the database
        List<AulaMateria> aulaMateriaList = aulaMateriaRepository.findAll();
        assertThat(aulaMateriaList).hasSize(databaseSizeBeforeUpdate);
        AulaMateria testAulaMateria = aulaMateriaList.get(aulaMateriaList.size() - 1);
        assertThat(testAulaMateria.getIdAula()).isEqualTo(UPDATED_ID_AULA);
        assertThat(testAulaMateria.getIdMateria()).isEqualTo(UPDATED_ID_MATERIA);
        assertThat(testAulaMateria.getHorario()).isEqualTo(UPDATED_HORARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingAulaMateria() throws Exception {
        int databaseSizeBeforeUpdate = aulaMateriaRepository.findAll().size();

        // Create the AulaMateria

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAulaMateriaMockMvc.perform(put("/api/aula-materias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aulaMateria)))
            .andExpect(status().isCreated());

        // Validate the AulaMateria in the database
        List<AulaMateria> aulaMateriaList = aulaMateriaRepository.findAll();
        assertThat(aulaMateriaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAulaMateria() throws Exception {
        // Initialize the database
        aulaMateriaService.save(aulaMateria);

        int databaseSizeBeforeDelete = aulaMateriaRepository.findAll().size();

        // Get the aulaMateria
        restAulaMateriaMockMvc.perform(delete("/api/aula-materias/{id}", aulaMateria.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AulaMateria> aulaMateriaList = aulaMateriaRepository.findAll();
        assertThat(aulaMateriaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AulaMateria.class);
        AulaMateria aulaMateria1 = new AulaMateria();
        aulaMateria1.setId(1L);
        AulaMateria aulaMateria2 = new AulaMateria();
        aulaMateria2.setId(aulaMateria1.getId());
        assertThat(aulaMateria1).isEqualTo(aulaMateria2);
        aulaMateria2.setId(2L);
        assertThat(aulaMateria1).isNotEqualTo(aulaMateria2);
        aulaMateria1.setId(null);
        assertThat(aulaMateria1).isNotEqualTo(aulaMateria2);
    }
}
