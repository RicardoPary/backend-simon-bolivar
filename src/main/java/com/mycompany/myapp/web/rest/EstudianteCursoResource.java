package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.EstudianteCurso;
import com.mycompany.myapp.service.EstudianteCursoService;
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
 * REST controller for managing EstudianteCurso.
 */
@RestController
@RequestMapping("/api")
public class EstudianteCursoResource {

    private final Logger log = LoggerFactory.getLogger(EstudianteCursoResource.class);

    private static final String ENTITY_NAME = "estudianteCurso";

    private final EstudianteCursoService estudianteCursoService;

    public EstudianteCursoResource(EstudianteCursoService estudianteCursoService) {
        this.estudianteCursoService = estudianteCursoService;
    }

    /**
     * POST  /estudiante-cursos : Create a new estudianteCurso.
     *
     * @param estudianteCurso the estudianteCurso to create
     * @return the ResponseEntity with status 201 (Created) and with body the new estudianteCurso, or with status 400 (Bad Request) if the estudianteCurso has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/estudiante-cursos")
    @Timed
    public ResponseEntity<EstudianteCurso> createEstudianteCurso(@RequestBody EstudianteCurso estudianteCurso) throws URISyntaxException {
        log.debug("REST request to save EstudianteCurso : {}", estudianteCurso);
        if (estudianteCurso.getId() != null) {
            throw new BadRequestAlertException("A new estudianteCurso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstudianteCurso result = estudianteCursoService.save(estudianteCurso);
        return ResponseEntity.created(new URI("/api/estudiante-cursos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /estudiante-cursos : Updates an existing estudianteCurso.
     *
     * @param estudianteCurso the estudianteCurso to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated estudianteCurso,
     * or with status 400 (Bad Request) if the estudianteCurso is not valid,
     * or with status 500 (Internal Server Error) if the estudianteCurso couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/estudiante-cursos")
    @Timed
    public ResponseEntity<EstudianteCurso> updateEstudianteCurso(@RequestBody EstudianteCurso estudianteCurso) throws URISyntaxException {
        log.debug("REST request to update EstudianteCurso : {}", estudianteCurso);
        if (estudianteCurso.getId() == null) {
            return createEstudianteCurso(estudianteCurso);
        }
        EstudianteCurso result = estudianteCursoService.save(estudianteCurso);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, estudianteCurso.getId().toString()))
            .body(result);
    }

    /**
     * GET  /estudiante-cursos : get all the estudianteCursos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of estudianteCursos in body
     */
    @GetMapping("/estudiante-cursos")
    @Timed
    public ResponseEntity<List<EstudianteCurso>> getAllEstudianteCursos(Pageable pageable) {
        log.debug("REST request to get a page of EstudianteCursos");
        Page<EstudianteCurso> page = estudianteCursoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/estudiante-cursos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /estudiante-cursos/:id : get the "id" estudianteCurso.
     *
     * @param id the id of the estudianteCurso to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the estudianteCurso, or with status 404 (Not Found)
     */
    @GetMapping("/estudiante-cursos/{id}")
    @Timed
    public ResponseEntity<EstudianteCurso> getEstudianteCurso(@PathVariable Long id) {
        log.debug("REST request to get EstudianteCurso : {}", id);
        EstudianteCurso estudianteCurso = estudianteCursoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(estudianteCurso));
    }

    /**
     * DELETE  /estudiante-cursos/:id : delete the "id" estudianteCurso.
     *
     * @param id the id of the estudianteCurso to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/estudiante-cursos/{id}")
    @Timed
    public ResponseEntity<Void> deleteEstudianteCurso(@PathVariable Long id) {
        log.debug("REST request to delete EstudianteCurso : {}", id);
        estudianteCursoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
