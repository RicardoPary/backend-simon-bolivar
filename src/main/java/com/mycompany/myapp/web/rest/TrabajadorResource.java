package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Trabajador;
import com.mycompany.myapp.service.TrabajadorService;
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
 * REST controller for managing Trabajador.
 */
@RestController
@RequestMapping("/api")
public class TrabajadorResource {

    private final Logger log = LoggerFactory.getLogger(TrabajadorResource.class);

    private static final String ENTITY_NAME = "trabajador";

    private final TrabajadorService trabajadorService;

    public TrabajadorResource(TrabajadorService trabajadorService) {
        this.trabajadorService = trabajadorService;
    }

    /**
     * POST  /trabajadors : Create a new trabajador.
     *
     * @param trabajador the trabajador to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trabajador, or with status 400 (Bad Request) if the trabajador has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trabajadors")
    @Timed
    public ResponseEntity<Trabajador> createTrabajador(@RequestBody Trabajador trabajador) throws URISyntaxException {
        log.debug("REST request to save Trabajador : {}", trabajador);
        if (trabajador.getId() != null) {
            throw new BadRequestAlertException("A new trabajador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Trabajador result = trabajadorService.save(trabajador);
        return ResponseEntity.created(new URI("/api/trabajadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trabajadors : Updates an existing trabajador.
     *
     * @param trabajador the trabajador to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trabajador,
     * or with status 400 (Bad Request) if the trabajador is not valid,
     * or with status 500 (Internal Server Error) if the trabajador couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trabajadors")
    @Timed
    public ResponseEntity<Trabajador> updateTrabajador(@RequestBody Trabajador trabajador) throws URISyntaxException {
        log.debug("REST request to update Trabajador : {}", trabajador);
        if (trabajador.getId() == null) {
            return createTrabajador(trabajador);
        }
        Trabajador result = trabajadorService.save(trabajador);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, trabajador.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trabajadors : get all the trabajadors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of trabajadors in body
     */
    @GetMapping("/trabajadors")
    @Timed
    public ResponseEntity<List<Trabajador>> getAllTrabajadors(Pageable pageable) {
        log.debug("REST request to get a page of Trabajadors");
        Page<Trabajador> page = trabajadorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trabajadors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trabajadors/:id : get the "id" trabajador.
     *
     * @param id the id of the trabajador to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trabajador, or with status 404 (Not Found)
     */
    @GetMapping("/trabajadors/{id}")
    @Timed
    public ResponseEntity<Trabajador> getTrabajador(@PathVariable Long id) {
        log.debug("REST request to get Trabajador : {}", id);
        Trabajador trabajador = trabajadorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(trabajador));
    }

    /**
     * DELETE  /trabajadors/:id : delete the "id" trabajador.
     *
     * @param id the id of the trabajador to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trabajadors/{id}")
    @Timed
    public ResponseEntity<Void> deleteTrabajador(@PathVariable Long id) {
        log.debug("REST request to delete Trabajador : {}", id);
        trabajadorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
