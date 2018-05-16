package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Inscrito;
import com.mycompany.myapp.service.InscritoService;
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
 * REST controller for managing Inscrito.
 */
@RestController
@RequestMapping("/api")
public class InscritoResource {

    private final Logger log = LoggerFactory.getLogger(InscritoResource.class);

    private static final String ENTITY_NAME = "inscrito";

    private final InscritoService inscritoService;

    public InscritoResource(InscritoService inscritoService) {
        this.inscritoService = inscritoService;
    }

    /**
     * POST  /inscritos : Create a new inscrito.
     *
     * @param inscrito the inscrito to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inscrito, or with status 400 (Bad Request) if the inscrito has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inscritos")
    @Timed
    public ResponseEntity<Inscrito> createInscrito(@RequestBody Inscrito inscrito) throws URISyntaxException {
        log.debug("REST request to save Inscrito : {}", inscrito);
        if (inscrito.getId() != null) {
            throw new BadRequestAlertException("A new inscrito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Inscrito result = inscritoService.save(inscrito);
        return ResponseEntity.created(new URI("/api/inscritos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inscritos : Updates an existing inscrito.
     *
     * @param inscrito the inscrito to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inscrito,
     * or with status 400 (Bad Request) if the inscrito is not valid,
     * or with status 500 (Internal Server Error) if the inscrito couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inscritos")
    @Timed
    public ResponseEntity<Inscrito> updateInscrito(@RequestBody Inscrito inscrito) throws URISyntaxException {
        log.debug("REST request to update Inscrito : {}", inscrito);
        if (inscrito.getId() == null) {
            return createInscrito(inscrito);
        }
        Inscrito result = inscritoService.save(inscrito);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inscrito.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inscritos : get all the inscritos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of inscritos in body
     */
    @GetMapping("/inscritos")
    @Timed
    public ResponseEntity<List<Inscrito>> getAllInscritos(Pageable pageable) {
        log.debug("REST request to get a page of Inscritos");
        Page<Inscrito> page = inscritoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/inscritos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /inscritos/:id : get the "id" inscrito.
     *
     * @param id the id of the inscrito to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inscrito, or with status 404 (Not Found)
     */
    @GetMapping("/inscritos/{id}")
    @Timed
    public ResponseEntity<Inscrito> getInscrito(@PathVariable Long id) {
        log.debug("REST request to get Inscrito : {}", id);
        Inscrito inscrito = inscritoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(inscrito));
    }

    /**
     * DELETE  /inscritos/:id : delete the "id" inscrito.
     *
     * @param id the id of the inscrito to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inscritos/{id}")
    @Timed
    public ResponseEntity<Void> deleteInscrito(@PathVariable Long id) {
        log.debug("REST request to delete Inscrito : {}", id);
        inscritoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
