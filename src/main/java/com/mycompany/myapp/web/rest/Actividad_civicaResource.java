package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Actividad_civica;
import com.mycompany.myapp.service.Actividad_civicaService;
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
 * REST controller for managing Actividad_civica.
 */
@RestController
@RequestMapping("/api")
public class Actividad_civicaResource {

    private final Logger log = LoggerFactory.getLogger(Actividad_civicaResource.class);

    private static final String ENTITY_NAME = "actividad_civica";

    private final Actividad_civicaService actividad_civicaService;

    public Actividad_civicaResource(Actividad_civicaService actividad_civicaService) {
        this.actividad_civicaService = actividad_civicaService;
    }

    /**
     * POST  /actividad-civicas : Create a new actividad_civica.
     *
     * @param actividad_civica the actividad_civica to create
     * @return the ResponseEntity with status 201 (Created) and with body the new actividad_civica, or with status 400 (Bad Request) if the actividad_civica has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/actividad-civicas")
    @Timed
    public ResponseEntity<Actividad_civica> createActividad_civica(@RequestBody Actividad_civica actividad_civica) throws URISyntaxException {
        log.debug("REST request to save Actividad_civica : {}", actividad_civica);
        if (actividad_civica.getId() != null) {
            throw new BadRequestAlertException("A new actividad_civica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Actividad_civica result = actividad_civicaService.save(actividad_civica);
        return ResponseEntity.created(new URI("/api/actividad-civicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /actividad-civicas : Updates an existing actividad_civica.
     *
     * @param actividad_civica the actividad_civica to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated actividad_civica,
     * or with status 400 (Bad Request) if the actividad_civica is not valid,
     * or with status 500 (Internal Server Error) if the actividad_civica couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/actividad-civicas")
    @Timed
    public ResponseEntity<Actividad_civica> updateActividad_civica(@RequestBody Actividad_civica actividad_civica) throws URISyntaxException {
        log.debug("REST request to update Actividad_civica : {}", actividad_civica);
        if (actividad_civica.getId() == null) {
            return createActividad_civica(actividad_civica);
        }
        Actividad_civica result = actividad_civicaService.save(actividad_civica);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, actividad_civica.getId().toString()))
            .body(result);
    }

    /**
     * GET  /actividad-civicas : get all the actividad_civicas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of actividad_civicas in body
     */
    @GetMapping("/actividad-civicas")
    @Timed
    public ResponseEntity<List<Actividad_civica>> getAllActividad_civicas(Pageable pageable) {
        log.debug("REST request to get a page of Actividad_civicas");
        Page<Actividad_civica> page = actividad_civicaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/actividad-civicas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /actividad-civicas/:id : get the "id" actividad_civica.
     *
     * @param id the id of the actividad_civica to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the actividad_civica, or with status 404 (Not Found)
     */
    @GetMapping("/actividad-civicas/{id}")
    @Timed
    public ResponseEntity<Actividad_civica> getActividad_civica(@PathVariable Long id) {
        log.debug("REST request to get Actividad_civica : {}", id);
        Actividad_civica actividad_civica = actividad_civicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(actividad_civica));
    }

    /**
     * DELETE  /actividad-civicas/:id : delete the "id" actividad_civica.
     *
     * @param id the id of the actividad_civica to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/actividad-civicas/{id}")
    @Timed
    public ResponseEntity<Void> deleteActividad_civica(@PathVariable Long id) {
        log.debug("REST request to delete Actividad_civica : {}", id);
        actividad_civicaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
