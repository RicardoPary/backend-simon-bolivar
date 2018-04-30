package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Nivel;
import com.mycompany.myapp.service.NivelService;
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
 * REST controller for managing Nivel.
 */
@RestController
@RequestMapping("/api")
public class NivelResource {

    private final Logger log = LoggerFactory.getLogger(NivelResource.class);

    private static final String ENTITY_NAME = "nivel";

    private final NivelService nivelService;

    public NivelResource(NivelService nivelService) {
        this.nivelService = nivelService;
    }

    /**
     * POST  /nivels : Create a new nivel.
     *
     * @param nivel the nivel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nivel, or with status 400 (Bad Request) if the nivel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nivels")
    @Timed
    public ResponseEntity<Nivel> createNivel(@RequestBody Nivel nivel) throws URISyntaxException {
        log.debug("REST request to save Nivel : {}", nivel);
        if (nivel.getId() != null) {
            throw new BadRequestAlertException("A new nivel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Nivel result = nivelService.save(nivel);
        return ResponseEntity.created(new URI("/api/nivels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nivels : Updates an existing nivel.
     *
     * @param nivel the nivel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nivel,
     * or with status 400 (Bad Request) if the nivel is not valid,
     * or with status 500 (Internal Server Error) if the nivel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nivels")
    @Timed
    public ResponseEntity<Nivel> updateNivel(@RequestBody Nivel nivel) throws URISyntaxException {
        log.debug("REST request to update Nivel : {}", nivel);
        if (nivel.getId() == null) {
            return createNivel(nivel);
        }
        Nivel result = nivelService.save(nivel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nivel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nivels : get all the nivels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of nivels in body
     */
    @GetMapping("/nivels")
    @Timed
    public ResponseEntity<List<Nivel>> getAllNivels(Pageable pageable) {
        log.debug("REST request to get a page of Nivels");
        Page<Nivel> page = nivelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/nivels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /nivels/:id : get the "id" nivel.
     *
     * @param id the id of the nivel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nivel, or with status 404 (Not Found)
     */
    @GetMapping("/nivels/{id}")
    @Timed
    public ResponseEntity<Nivel> getNivel(@PathVariable Long id) {
        log.debug("REST request to get Nivel : {}", id);
        Nivel nivel = nivelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(nivel));
    }

    /**
     * DELETE  /nivels/:id : delete the "id" nivel.
     *
     * @param id the id of the nivel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nivels/{id}")
    @Timed
    public ResponseEntity<Void> deleteNivel(@PathVariable Long id) {
        log.debug("REST request to delete Nivel : {}", id);
        nivelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
