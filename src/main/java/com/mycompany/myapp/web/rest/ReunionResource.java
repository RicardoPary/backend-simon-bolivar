package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Reunion;
import com.mycompany.myapp.service.ReunionService;
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
 * REST controller for managing Reunion.
 */
@RestController
@RequestMapping("/api")
public class ReunionResource {

    private final Logger log = LoggerFactory.getLogger(ReunionResource.class);

    private static final String ENTITY_NAME = "reunion";

    private final ReunionService reunionService;

    public ReunionResource(ReunionService reunionService) {
        this.reunionService = reunionService;
    }

    /**
     * POST  /reunions : Create a new reunion.
     *
     * @param reunion the reunion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reunion, or with status 400 (Bad Request) if the reunion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reunions")
    @Timed
    public ResponseEntity<Reunion> createReunion(@RequestBody Reunion reunion) throws URISyntaxException {
        log.debug("REST request to save Reunion : {}", reunion);
        if (reunion.getId() != null) {
            throw new BadRequestAlertException("A new reunion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Reunion result = reunionService.save(reunion);
        return ResponseEntity.created(new URI("/api/reunions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reunions : Updates an existing reunion.
     *
     * @param reunion the reunion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reunion,
     * or with status 400 (Bad Request) if the reunion is not valid,
     * or with status 500 (Internal Server Error) if the reunion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reunions")
    @Timed
    public ResponseEntity<Reunion> updateReunion(@RequestBody Reunion reunion) throws URISyntaxException {
        log.debug("REST request to update Reunion : {}", reunion);
        if (reunion.getId() == null) {
            return createReunion(reunion);
        }
        Reunion result = reunionService.save(reunion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reunion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reunions : get all the reunions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of reunions in body
     */
    @GetMapping("/reunions")
    @Timed
    public ResponseEntity<List<Reunion>> getAllReunions(Pageable pageable) {
        log.debug("REST request to get a page of Reunions");
        Page<Reunion> page = reunionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reunions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /reunions/:id : get the "id" reunion.
     *
     * @param id the id of the reunion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reunion, or with status 404 (Not Found)
     */
    @GetMapping("/reunions/{id}")
    @Timed
    public ResponseEntity<Reunion> getReunion(@PathVariable Long id) {
        log.debug("REST request to get Reunion : {}", id);
        Reunion reunion = reunionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reunion));
    }

    /**
     * DELETE  /reunions/:id : delete the "id" reunion.
     *
     * @param id the id of the reunion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reunions/{id}")
    @Timed
    public ResponseEntity<Void> deleteReunion(@PathVariable Long id) {
        log.debug("REST request to delete Reunion : {}", id);
        reunionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
