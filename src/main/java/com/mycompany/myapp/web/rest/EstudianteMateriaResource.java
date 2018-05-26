package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.EstudianteMateria;
import com.mycompany.myapp.service.EstudianteMateriaService;
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
 * REST controller for managing EstudianteMateria.
 */
@RestController
@RequestMapping("/api")
public class EstudianteMateriaResource {

    private final Logger log = LoggerFactory.getLogger(EstudianteMateriaResource.class);

    private static final String ENTITY_NAME = "estudianteMateria";

    private final EstudianteMateriaService estudianteMateriaService;

    public EstudianteMateriaResource(EstudianteMateriaService estudianteMateriaService) {
        this.estudianteMateriaService = estudianteMateriaService;
    }

    /**
     * POST  /estudiante-materias : Create a new estudianteMateria.
     *
     * @param estudianteMateria the estudianteMateria to create
     * @return the ResponseEntity with status 201 (Created) and with body the new estudianteMateria, or with status 400 (Bad Request) if the estudianteMateria has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/estudiante-materias")
    @Timed
    public ResponseEntity<EstudianteMateria> createEstudianteMateria(@RequestBody EstudianteMateria estudianteMateria) throws URISyntaxException {
        log.debug("REST request to save EstudianteMateria : {}", estudianteMateria);
        if (estudianteMateria.getId() != null) {
            throw new BadRequestAlertException("A new estudianteMateria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstudianteMateria result = estudianteMateriaService.save(estudianteMateria);
        return ResponseEntity.created(new URI("/api/estudiante-materias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /estudiante-materias : Updates an existing estudianteMateria.
     *
     * @param estudianteMateria the estudianteMateria to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated estudianteMateria,
     * or with status 400 (Bad Request) if the estudianteMateria is not valid,
     * or with status 500 (Internal Server Error) if the estudianteMateria couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/estudiante-materias")
    @Timed
    public ResponseEntity<EstudianteMateria> updateEstudianteMateria(@RequestBody EstudianteMateria estudianteMateria) throws URISyntaxException {
        log.debug("REST request to update EstudianteMateria : {}", estudianteMateria);
        if (estudianteMateria.getId() == null) {
            return createEstudianteMateria(estudianteMateria);
        }
        EstudianteMateria result = estudianteMateriaService.save(estudianteMateria);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, estudianteMateria.getId().toString()))
            .body(result);
    }

    /**
     * GET  /estudiante-materias : get all the estudianteMaterias.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of estudianteMaterias in body
     */
    @GetMapping("/estudiante-materias")
    @Timed
    public ResponseEntity<List<EstudianteMateria>> getAllEstudianteMaterias(Pageable pageable) {
        log.debug("REST request to get a page of EstudianteMaterias");
        Page<EstudianteMateria> page = estudianteMateriaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/estudiante-materias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /estudiante-materias/:id : get the "id" estudianteMateria.
     *
     * @param id the id of the estudianteMateria to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the estudianteMateria, or with status 404 (Not Found)
     */
    @GetMapping("/estudiante-materias/{id}")
    @Timed
    public ResponseEntity<EstudianteMateria> getEstudianteMateria(@PathVariable Long id) {
        log.debug("REST request to get EstudianteMateria : {}", id);
        EstudianteMateria estudianteMateria = estudianteMateriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(estudianteMateria));
    }

    /**
     * DELETE  /estudiante-materias/:id : delete the "id" estudianteMateria.
     *
     * @param id the id of the estudianteMateria to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/estudiante-materias/{id}")
    @Timed
    public ResponseEntity<Void> deleteEstudianteMateria(@PathVariable Long id) {
        log.debug("REST request to delete EstudianteMateria : {}", id);
        estudianteMateriaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
