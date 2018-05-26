package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.TrabajadorActividadCivica;
import com.mycompany.myapp.service.TrabajadorActividadCivicaService;
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
 * REST controller for managing TrabajadorActividadCivica.
 */
@RestController
@RequestMapping("/api")
public class TrabajadorActividadCivicaResource {

    private final Logger log = LoggerFactory.getLogger(TrabajadorActividadCivicaResource.class);

    private static final String ENTITY_NAME = "trabajadorActividadCivica";

    private final TrabajadorActividadCivicaService trabajadorActividadCivicaService;

    public TrabajadorActividadCivicaResource(TrabajadorActividadCivicaService trabajadorActividadCivicaService) {
        this.trabajadorActividadCivicaService = trabajadorActividadCivicaService;
    }

    /**
     * POST  /trabajador-actividad-civicas : Create a new trabajadorActividadCivica.
     *
     * @param trabajadorActividadCivica the trabajadorActividadCivica to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trabajadorActividadCivica, or with status 400 (Bad Request) if the trabajadorActividadCivica has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trabajador-actividad-civicas")
    @Timed
    public ResponseEntity<TrabajadorActividadCivica> createTrabajadorActividadCivica(@RequestBody TrabajadorActividadCivica trabajadorActividadCivica) throws URISyntaxException {
        log.debug("REST request to save TrabajadorActividadCivica : {}", trabajadorActividadCivica);
        if (trabajadorActividadCivica.getId() != null) {
            throw new BadRequestAlertException("A new trabajadorActividadCivica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrabajadorActividadCivica result = trabajadorActividadCivicaService.save(trabajadorActividadCivica);
        return ResponseEntity.created(new URI("/api/trabajador-actividad-civicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trabajador-actividad-civicas : Updates an existing trabajadorActividadCivica.
     *
     * @param trabajadorActividadCivica the trabajadorActividadCivica to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trabajadorActividadCivica,
     * or with status 400 (Bad Request) if the trabajadorActividadCivica is not valid,
     * or with status 500 (Internal Server Error) if the trabajadorActividadCivica couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trabajador-actividad-civicas")
    @Timed
    public ResponseEntity<TrabajadorActividadCivica> updateTrabajadorActividadCivica(@RequestBody TrabajadorActividadCivica trabajadorActividadCivica) throws URISyntaxException {
        log.debug("REST request to update TrabajadorActividadCivica : {}", trabajadorActividadCivica);
        if (trabajadorActividadCivica.getId() == null) {
            return createTrabajadorActividadCivica(trabajadorActividadCivica);
        }
        TrabajadorActividadCivica result = trabajadorActividadCivicaService.save(trabajadorActividadCivica);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, trabajadorActividadCivica.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trabajador-actividad-civicas : get all the trabajadorActividadCivicas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of trabajadorActividadCivicas in body
     */
    @GetMapping("/trabajador-actividad-civicas")
    @Timed
    public ResponseEntity<List<TrabajadorActividadCivica>> getAllTrabajadorActividadCivicas(Pageable pageable) {
        log.debug("REST request to get a page of TrabajadorActividadCivicas");
        Page<TrabajadorActividadCivica> page = trabajadorActividadCivicaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trabajador-actividad-civicas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trabajador-actividad-civicas/:id : get the "id" trabajadorActividadCivica.
     *
     * @param id the id of the trabajadorActividadCivica to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trabajadorActividadCivica, or with status 404 (Not Found)
     */
    @GetMapping("/trabajador-actividad-civicas/{id}")
    @Timed
    public ResponseEntity<TrabajadorActividadCivica> getTrabajadorActividadCivica(@PathVariable Long id) {
        log.debug("REST request to get TrabajadorActividadCivica : {}", id);
        TrabajadorActividadCivica trabajadorActividadCivica = trabajadorActividadCivicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(trabajadorActividadCivica));
    }

    /**
     * DELETE  /trabajador-actividad-civicas/:id : delete the "id" trabajadorActividadCivica.
     *
     * @param id the id of the trabajadorActividadCivica to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trabajador-actividad-civicas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTrabajadorActividadCivica(@PathVariable Long id) {
        log.debug("REST request to delete TrabajadorActividadCivica : {}", id);
        trabajadorActividadCivicaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
