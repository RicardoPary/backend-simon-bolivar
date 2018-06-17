package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Estudiante;
import com.mycompany.myapp.domain.extras.EstudianteDTO;
import com.mycompany.myapp.repository.EstudianteRepository;
import com.mycompany.myapp.service.EstudianteService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Estudiante.
 */
@RestController
@RequestMapping("/api")
public class EstudianteResource {

    private final Logger log = LoggerFactory.getLogger(EstudianteResource.class);

    private static final String ENTITY_NAME = "estudiante";

    private final EstudianteService estudianteService;

    private final EstudianteRepository estudianteRepository;

    public EstudianteResource(EstudianteService estudianteService, EstudianteRepository estudianteRepository) {

        this.estudianteService = estudianteService;
        this.estudianteRepository = estudianteRepository;
    }

    /**
     * POST  /estudiantes : Create a new estudiante.
     *
     * @param estudiante the estudiante to create
     * @return the ResponseEntity with status 201 (Created) and with body the new estudiante, or with status 400 (Bad Request) if the estudiante has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/estudiantes")
    @Timed
    public ResponseEntity<Estudiante> createEstudiante(@RequestBody Estudiante estudiante) throws URISyntaxException {
        log.debug("REST request to save Estudiante : {}", estudiante);
        if (estudiante.getId() != null) {
            throw new BadRequestAlertException("A new estudiante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Estudiante result = estudianteService.save(estudiante);
        return ResponseEntity.created(new URI("/api/estudiantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /estudiantes : Updates an existing estudiante.
     *
     * @param estudiante the estudiante to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated estudiante,
     * or with status 400 (Bad Request) if the estudiante is not valid,
     * or with status 500 (Internal Server Error) if the estudiante couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/estudiantes")
    @Timed
    public ResponseEntity<Estudiante> updateEstudiante(@RequestBody Estudiante estudiante) throws URISyntaxException {
        log.debug("REST request to update Estudiante : {}", estudiante);
        if (estudiante.getId() == null) {
            return createEstudiante(estudiante);
        }
        Estudiante result = estudianteService.save(estudiante);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, estudiante.getId().toString()))
            .body(result);
    }

    /**
     * GET  /estudiantes : get all the estudiantes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of estudiantes in body
     */
    @GetMapping("/estudiantes")
    @Timed
    public ResponseEntity<List<Estudiante>> getAllEstudiantes(Pageable pageable) {
        log.debug("REST request to get a page of Estudiantes");
        Page<Estudiante> page = estudianteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/estudiantes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /estudiantes/:id : get the "id" estudiante.
     *
     * @param id the id of the estudiante to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the estudiante, or with status 404 (Not Found)
     */
    @GetMapping("/estudiantes/{id}")
    @Timed
    public ResponseEntity<Estudiante> getEstudiante(@PathVariable Long id) {
        log.debug("REST request to get Estudiante : {}", id);
        Estudiante estudiante = estudianteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(estudiante));
    }

    /**
     * DELETE  /estudiantes/:id : delete the "id" estudiante.
     *
     * @param id the id of the estudiante to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/estudiantes/{id}")
    @Timed
    public ResponseEntity<Void> deleteEstudiante(@PathVariable Long id) {
        log.debug("REST request to delete Estudiante : {}", id);
        estudianteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }








    /**
     * GET  /estudiantes : get all the estudiantes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of estudiantes in body
     */
    /*@GetMapping("/estudiantes/all")
    @Timed
    public ResponseEntity<List<EstudianteDTO>> getAllEstudiantesPersonas(Pageable pageable) {
        log.debug("REST request to get a page of Estudiantes");
        Page<EstudianteDTO> page = estudianteRepository.findAllStudents(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/estudiantes/all");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }*/
}
