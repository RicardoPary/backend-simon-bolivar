package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Aula;
import com.mycompany.myapp.service.AulaService;
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
 * REST controller for managing Aula.
 */
@RestController
@RequestMapping("/api")
public class AulaResource {

    private final Logger log = LoggerFactory.getLogger(AulaResource.class);

    private static final String ENTITY_NAME = "aula";

    private final AulaService aulaService;

    public AulaResource(AulaService aulaService) {
        this.aulaService = aulaService;
    }

    /**
     * POST  /aulas : Create a new aula.
     *
     * @param aula the aula to create
     * @return the ResponseEntity with status 201 (Created) and with body the new aula, or with status 400 (Bad Request) if the aula has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/aulas")
    @Timed
    public ResponseEntity<Aula> createAula(@RequestBody Aula aula) throws URISyntaxException {
        log.debug("REST request to save Aula : {}", aula);
        if (aula.getId() != null) {
            throw new BadRequestAlertException("A new aula cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Aula result = aulaService.save(aula);
        return ResponseEntity.created(new URI("/api/aulas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aulas : Updates an existing aula.
     *
     * @param aula the aula to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated aula,
     * or with status 400 (Bad Request) if the aula is not valid,
     * or with status 500 (Internal Server Error) if the aula couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/aulas")
    @Timed
    public ResponseEntity<Aula> updateAula(@RequestBody Aula aula) throws URISyntaxException {
        log.debug("REST request to update Aula : {}", aula);
        if (aula.getId() == null) {
            return createAula(aula);
        }
        Aula result = aulaService.save(aula);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, aula.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aulas : get all the aulas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of aulas in body
     */
    @GetMapping("/aulas")
    @Timed
    public ResponseEntity<List<Aula>> getAllAulas(Pageable pageable) {
        log.debug("REST request to get a page of Aulas");
        Page<Aula> page = aulaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/aulas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /aulas/:id : get the "id" aula.
     *
     * @param id the id of the aula to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the aula, or with status 404 (Not Found)
     */
    @GetMapping("/aulas/{id}")
    @Timed
    public ResponseEntity<Aula> getAula(@PathVariable Long id) {
        log.debug("REST request to get Aula : {}", id);
        Aula aula = aulaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(aula));
    }

    /**
     * DELETE  /aulas/:id : delete the "id" aula.
     *
     * @param id the id of the aula to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/aulas/{id}")
    @Timed
    public ResponseEntity<Void> deleteAula(@PathVariable Long id) {
        log.debug("REST request to delete Aula : {}", id);
        aulaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
