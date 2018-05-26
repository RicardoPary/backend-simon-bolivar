package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.AulaMateria;
import com.mycompany.myapp.service.AulaMateriaService;
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
 * REST controller for managing AulaMateria.
 */
@RestController
@RequestMapping("/api")
public class AulaMateriaResource {

    private final Logger log = LoggerFactory.getLogger(AulaMateriaResource.class);

    private static final String ENTITY_NAME = "aulaMateria";

    private final AulaMateriaService aulaMateriaService;

    public AulaMateriaResource(AulaMateriaService aulaMateriaService) {
        this.aulaMateriaService = aulaMateriaService;
    }

    /**
     * POST  /aula-materias : Create a new aulaMateria.
     *
     * @param aulaMateria the aulaMateria to create
     * @return the ResponseEntity with status 201 (Created) and with body the new aulaMateria, or with status 400 (Bad Request) if the aulaMateria has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/aula-materias")
    @Timed
    public ResponseEntity<AulaMateria> createAulaMateria(@RequestBody AulaMateria aulaMateria) throws URISyntaxException {
        log.debug("REST request to save AulaMateria : {}", aulaMateria);
        if (aulaMateria.getId() != null) {
            throw new BadRequestAlertException("A new aulaMateria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AulaMateria result = aulaMateriaService.save(aulaMateria);
        return ResponseEntity.created(new URI("/api/aula-materias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aula-materias : Updates an existing aulaMateria.
     *
     * @param aulaMateria the aulaMateria to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated aulaMateria,
     * or with status 400 (Bad Request) if the aulaMateria is not valid,
     * or with status 500 (Internal Server Error) if the aulaMateria couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/aula-materias")
    @Timed
    public ResponseEntity<AulaMateria> updateAulaMateria(@RequestBody AulaMateria aulaMateria) throws URISyntaxException {
        log.debug("REST request to update AulaMateria : {}", aulaMateria);
        if (aulaMateria.getId() == null) {
            return createAulaMateria(aulaMateria);
        }
        AulaMateria result = aulaMateriaService.save(aulaMateria);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, aulaMateria.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aula-materias : get all the aulaMaterias.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of aulaMaterias in body
     */
    @GetMapping("/aula-materias")
    @Timed
    public ResponseEntity<List<AulaMateria>> getAllAulaMaterias(Pageable pageable) {
        log.debug("REST request to get a page of AulaMaterias");
        Page<AulaMateria> page = aulaMateriaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/aula-materias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /aula-materias/:id : get the "id" aulaMateria.
     *
     * @param id the id of the aulaMateria to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the aulaMateria, or with status 404 (Not Found)
     */
    @GetMapping("/aula-materias/{id}")
    @Timed
    public ResponseEntity<AulaMateria> getAulaMateria(@PathVariable Long id) {
        log.debug("REST request to get AulaMateria : {}", id);
        AulaMateria aulaMateria = aulaMateriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(aulaMateria));
    }

    /**
     * DELETE  /aula-materias/:id : delete the "id" aulaMateria.
     *
     * @param id the id of the aulaMateria to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/aula-materias/{id}")
    @Timed
    public ResponseEntity<Void> deleteAulaMateria(@PathVariable Long id) {
        log.debug("REST request to delete AulaMateria : {}", id);
        aulaMateriaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
