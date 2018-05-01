package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SimonBolivarApp;

import com.mycompany.myapp.domain.Persona;
import com.mycompany.myapp.repository.PersonaRepository;
import com.mycompany.myapp.service.PersonaService;
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
 * Test class for the PersonaResource REST controller.
 *
 * @see PersonaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimonBolivarApp.class)
public class PersonaResourceIntTest {

    private static final Long DEFAULT_CI = 1L;
    private static final Long UPDATED_CI = 2L;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_PATERNO = "AAAAAAAAAA";
    private static final String UPDATED_PATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_MATERNO = "AAAAAAAAAA";
    private static final String UPDATED_MATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_GENERO = "AAAAAAAAAA";
    private static final String UPDATED_GENERO = "BBBBBBBBBB";

    private static final String DEFAULT_FECHA_NACIMIENTO = "AAAAAAAAAA";
    private static final String UPDATED_FECHA_NACIMIENTO = "BBBBBBBBBB";

    private static final String DEFAULT_NACIONALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_NACIONALIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final Long DEFAULT_TELEFONO = 1L;
    private static final Long UPDATED_TELEFONO = 2L;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private PersonaService personaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonaMockMvc;

    private Persona persona;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonaResource personaResource = new PersonaResource(personaService);
        this.restPersonaMockMvc = MockMvcBuilders.standaloneSetup(personaResource)
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
    public static Persona createEntity(EntityManager em) {
        Persona persona = new Persona()
            .ci(DEFAULT_CI)
            .nombre(DEFAULT_NOMBRE)
            .paterno(DEFAULT_PATERNO)
            .materno(DEFAULT_MATERNO)
            .genero(DEFAULT_GENERO)
            .fecha_nacimiento(DEFAULT_FECHA_NACIMIENTO)
            .nacionalidad(DEFAULT_NACIONALIDAD)
            .direccion(DEFAULT_DIRECCION)
            .telefono(DEFAULT_TELEFONO);
        return persona;
    }

    @Before
    public void initTest() {
        persona = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersona() throws Exception {
        int databaseSizeBeforeCreate = personaRepository.findAll().size();

        // Create the Persona
        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isCreated());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeCreate + 1);
        Persona testPersona = personaList.get(personaList.size() - 1);
        assertThat(testPersona.getCi()).isEqualTo(DEFAULT_CI);
        assertThat(testPersona.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPersona.getPaterno()).isEqualTo(DEFAULT_PATERNO);
        assertThat(testPersona.getMaterno()).isEqualTo(DEFAULT_MATERNO);
        assertThat(testPersona.getGenero()).isEqualTo(DEFAULT_GENERO);
        assertThat(testPersona.getFecha_nacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testPersona.getNacionalidad()).isEqualTo(DEFAULT_NACIONALIDAD);
        assertThat(testPersona.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testPersona.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
    }

    @Test
    @Transactional
    public void createPersonaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personaRepository.findAll().size();

        // Create the Persona with an existing ID
        persona.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonas() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList
        restPersonaMockMvc.perform(get("/api/personas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(persona.getId().intValue())))
            .andExpect(jsonPath("$.[*].ci").value(hasItem(DEFAULT_CI.intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].paterno").value(hasItem(DEFAULT_PATERNO.toString())))
            .andExpect(jsonPath("$.[*].materno").value(hasItem(DEFAULT_MATERNO.toString())))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO.toString())))
            .andExpect(jsonPath("$.[*].fecha_nacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].nacionalidad").value(hasItem(DEFAULT_NACIONALIDAD.toString())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.intValue())));
    }

    @Test
    @Transactional
    public void getPersona() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get the persona
        restPersonaMockMvc.perform(get("/api/personas/{id}", persona.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(persona.getId().intValue()))
            .andExpect(jsonPath("$.ci").value(DEFAULT_CI.intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.paterno").value(DEFAULT_PATERNO.toString()))
            .andExpect(jsonPath("$.materno").value(DEFAULT_MATERNO.toString()))
            .andExpect(jsonPath("$.genero").value(DEFAULT_GENERO.toString()))
            .andExpect(jsonPath("$.fecha_nacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.nacionalidad").value(DEFAULT_NACIONALIDAD.toString()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersona() throws Exception {
        // Get the persona
        restPersonaMockMvc.perform(get("/api/personas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersona() throws Exception {
        // Initialize the database
        personaService.save(persona);

        int databaseSizeBeforeUpdate = personaRepository.findAll().size();

        // Update the persona
        Persona updatedPersona = personaRepository.findOne(persona.getId());
        // Disconnect from session so that the updates on updatedPersona are not directly saved in db
        em.detach(updatedPersona);
        updatedPersona
            .ci(UPDATED_CI)
            .nombre(UPDATED_NOMBRE)
            .paterno(UPDATED_PATERNO)
            .materno(UPDATED_MATERNO)
            .genero(UPDATED_GENERO)
            .fecha_nacimiento(UPDATED_FECHA_NACIMIENTO)
            .nacionalidad(UPDATED_NACIONALIDAD)
            .direccion(UPDATED_DIRECCION)
            .telefono(UPDATED_TELEFONO);

        restPersonaMockMvc.perform(put("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersona)))
            .andExpect(status().isOk());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeUpdate);
        Persona testPersona = personaList.get(personaList.size() - 1);
        assertThat(testPersona.getCi()).isEqualTo(UPDATED_CI);
        assertThat(testPersona.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPersona.getPaterno()).isEqualTo(UPDATED_PATERNO);
        assertThat(testPersona.getMaterno()).isEqualTo(UPDATED_MATERNO);
        assertThat(testPersona.getGenero()).isEqualTo(UPDATED_GENERO);
        assertThat(testPersona.getFecha_nacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testPersona.getNacionalidad()).isEqualTo(UPDATED_NACIONALIDAD);
        assertThat(testPersona.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testPersona.getTelefono()).isEqualTo(UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void updateNonExistingPersona() throws Exception {
        int databaseSizeBeforeUpdate = personaRepository.findAll().size();

        // Create the Persona

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonaMockMvc.perform(put("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isCreated());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersona() throws Exception {
        // Initialize the database
        personaService.save(persona);

        int databaseSizeBeforeDelete = personaRepository.findAll().size();

        // Get the persona
        restPersonaMockMvc.perform(delete("/api/personas/{id}", persona.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Persona.class);
        Persona persona1 = new Persona();
        persona1.setId(1L);
        Persona persona2 = new Persona();
        persona2.setId(persona1.getId());
        assertThat(persona1).isEqualTo(persona2);
        persona2.setId(2L);
        assertThat(persona1).isNotEqualTo(persona2);
        persona1.setId(null);
        assertThat(persona1).isNotEqualTo(persona2);
    }
}
