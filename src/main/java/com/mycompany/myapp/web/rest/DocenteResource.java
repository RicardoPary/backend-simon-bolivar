package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Docente;
import com.mycompany.myapp.service.DocenteService;
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
 * REST controller for managing Docente.
 */
@RestController
@RequestMapping("/api")
public class DocenteResource {

    private final Logger log = LoggerFactory.getLogger(DocenteResource.class);

    private static final String ENTITY_NAME = "docente";

    private final DocenteService docenteService;

    public DocenteResource(DocenteService docenteService) {
        this.docenteService = docenteService;
    }

    /**
     * POST  /docentes : Create a new docente.
     *
     * @param docente the docente to create
     * @return the ResponseEntity with status 201 (Created) and with body the new docente, or with status 400 (Bad Request) if the docente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/docentes")
    @Timed
    public ResponseEntity<Docente> createDocente(@RequestBody Docente docente) throws URISyntaxException {
        log.debug("REST request to save Docente : {}", docente);
        if (docente.getId() != null) {
            throw new BadRequestAlertException("A new docente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Docente result = docenteService.save(docente);
        return ResponseEntity.created(new URI("/api/docentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /docentes : Updates an existing docente.
     *
     * @param docente the docente to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated docente,
     * or with status 400 (Bad Request) if the docente is not valid,
     * or with status 500 (Internal Server Error) if the docente couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/docentes")
    @Timed
    public ResponseEntity<Docente> updateDocente(@RequestBody Docente docente) throws URISyntaxException {
        log.debug("REST request to update Docente : {}", docente);
        if (docente.getId() == null) {
            return createDocente(docente);
        }
        Docente result = docenteService.save(docente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, docente.getId().toString()))
            .body(result);
    }

    /**
     * GET  /docentes : get all the docentes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of docentes in body
     */
    @GetMapping("/docentes")
    @Timed
    public ResponseEntity<List<Docente>> getAllDocentes(Pageable pageable) {
        log.debug("REST request to get a page of Docentes");
        Page<Docente> page = docenteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/docentes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /docentes/:id : get the "id" docente.
     *
     * @param id the id of the docente to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the docente, or with status 404 (Not Found)
     */
    @GetMapping("/docentes/{id}")
    @Timed
    public ResponseEntity<Docente> getDocente(@PathVariable Long id) {
        log.debug("REST request to get Docente : {}", id);
        Docente docente = docenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(docente));
    }

    /**
     * DELETE  /docentes/:id : delete the "id" docente.
     *
     * @param id the id of the docente to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/docentes/{id}")
    @Timed
    public ResponseEntity<Void> deleteDocente(@PathVariable Long id) {
        log.debug("REST request to delete Docente : {}", id);
        docenteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
