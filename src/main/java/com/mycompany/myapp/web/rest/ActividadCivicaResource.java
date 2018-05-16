package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.ActividadCivica;
import com.mycompany.myapp.service.ActividadCivicaService;
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
 * REST controller for managing ActividadCivica.
 */
@RestController
@RequestMapping("/api")
public class ActividadCivicaResource {

    private final Logger log = LoggerFactory.getLogger(ActividadCivicaResource.class);

    private static final String ENTITY_NAME = "actividadCivica";

    private final ActividadCivicaService actividadCivicaService;

    public ActividadCivicaResource(ActividadCivicaService actividadCivicaService) {
        this.actividadCivicaService = actividadCivicaService;
    }

    /**
     * POST  /actividad-civicas : Create a new actividadCivica.
     *
     * @param actividadCivica the actividadCivica to create
     * @return the ResponseEntity with status 201 (Created) and with body the new actividadCivica, or with status 400 (Bad Request) if the actividadCivica has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/actividad-civicas")
    @Timed
    public ResponseEntity<ActividadCivica> createActividadCivica(@RequestBody ActividadCivica actividadCivica) throws URISyntaxException {
        log.debug("REST request to save ActividadCivica : {}", actividadCivica);
        if (actividadCivica.getId() != null) {
            throw new BadRequestAlertException("A new actividadCivica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActividadCivica result = actividadCivicaService.save(actividadCivica);
        return ResponseEntity.created(new URI("/api/actividad-civicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /actividad-civicas : Updates an existing actividadCivica.
     *
     * @param actividadCivica the actividadCivica to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated actividadCivica,
     * or with status 400 (Bad Request) if the actividadCivica is not valid,
     * or with status 500 (Internal Server Error) if the actividadCivica couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/actividad-civicas")
    @Timed
    public ResponseEntity<ActividadCivica> updateActividadCivica(@RequestBody ActividadCivica actividadCivica) throws URISyntaxException {
        log.debug("REST request to update ActividadCivica : {}", actividadCivica);
        if (actividadCivica.getId() == null) {
            return createActividadCivica(actividadCivica);
        }
        ActividadCivica result = actividadCivicaService.save(actividadCivica);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, actividadCivica.getId().toString()))
            .body(result);
    }

    /**
     * GET  /actividad-civicas : get all the actividadCivicas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of actividadCivicas in body
     */
    @GetMapping("/actividad-civicas")
    @Timed
    public ResponseEntity<List<ActividadCivica>> getAllActividadCivicas(Pageable pageable) {
        log.debug("REST request to get a page of ActividadCivicas");
        Page<ActividadCivica> page = actividadCivicaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/actividad-civicas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /actividad-civicas/:id : get the "id" actividadCivica.
     *
     * @param id the id of the actividadCivica to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the actividadCivica, or with status 404 (Not Found)
     */
    @GetMapping("/actividad-civicas/{id}")
    @Timed
    public ResponseEntity<ActividadCivica> getActividadCivica(@PathVariable Long id) {
        log.debug("REST request to get ActividadCivica : {}", id);
        ActividadCivica actividadCivica = actividadCivicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(actividadCivica));
    }

    /**
     * DELETE  /actividad-civicas/:id : delete the "id" actividadCivica.
     *
     * @param id the id of the actividadCivica to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/actividad-civicas/{id}")
    @Timed
    public ResponseEntity<Void> deleteActividadCivica(@PathVariable Long id) {
        log.debug("REST request to delete ActividadCivica : {}", id);
        actividadCivicaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
