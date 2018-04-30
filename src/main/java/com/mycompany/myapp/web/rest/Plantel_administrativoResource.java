package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Plantel_administrativo;
import com.mycompany.myapp.service.Plantel_administrativoService;
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
 * REST controller for managing Plantel_administrativo.
 */
@RestController
@RequestMapping("/api")
public class Plantel_administrativoResource {

    private final Logger log = LoggerFactory.getLogger(Plantel_administrativoResource.class);

    private static final String ENTITY_NAME = "plantel_administrativo";

    private final Plantel_administrativoService plantel_administrativoService;

    public Plantel_administrativoResource(Plantel_administrativoService plantel_administrativoService) {
        this.plantel_administrativoService = plantel_administrativoService;
    }

    /**
     * POST  /plantel-administrativos : Create a new plantel_administrativo.
     *
     * @param plantel_administrativo the plantel_administrativo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new plantel_administrativo, or with status 400 (Bad Request) if the plantel_administrativo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/plantel-administrativos")
    @Timed
    public ResponseEntity<Plantel_administrativo> createPlantel_administrativo(@RequestBody Plantel_administrativo plantel_administrativo) throws URISyntaxException {
        log.debug("REST request to save Plantel_administrativo : {}", plantel_administrativo);
        if (plantel_administrativo.getId() != null) {
            throw new BadRequestAlertException("A new plantel_administrativo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Plantel_administrativo result = plantel_administrativoService.save(plantel_administrativo);
        return ResponseEntity.created(new URI("/api/plantel-administrativos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /plantel-administrativos : Updates an existing plantel_administrativo.
     *
     * @param plantel_administrativo the plantel_administrativo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated plantel_administrativo,
     * or with status 400 (Bad Request) if the plantel_administrativo is not valid,
     * or with status 500 (Internal Server Error) if the plantel_administrativo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/plantel-administrativos")
    @Timed
    public ResponseEntity<Plantel_administrativo> updatePlantel_administrativo(@RequestBody Plantel_administrativo plantel_administrativo) throws URISyntaxException {
        log.debug("REST request to update Plantel_administrativo : {}", plantel_administrativo);
        if (plantel_administrativo.getId() == null) {
            return createPlantel_administrativo(plantel_administrativo);
        }
        Plantel_administrativo result = plantel_administrativoService.save(plantel_administrativo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, plantel_administrativo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /plantel-administrativos : get all the plantel_administrativos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of plantel_administrativos in body
     */
    @GetMapping("/plantel-administrativos")
    @Timed
    public ResponseEntity<List<Plantel_administrativo>> getAllPlantel_administrativos(Pageable pageable) {
        log.debug("REST request to get a page of Plantel_administrativos");
        Page<Plantel_administrativo> page = plantel_administrativoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/plantel-administrativos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /plantel-administrativos/:id : get the "id" plantel_administrativo.
     *
     * @param id the id of the plantel_administrativo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the plantel_administrativo, or with status 404 (Not Found)
     */
    @GetMapping("/plantel-administrativos/{id}")
    @Timed
    public ResponseEntity<Plantel_administrativo> getPlantel_administrativo(@PathVariable Long id) {
        log.debug("REST request to get Plantel_administrativo : {}", id);
        Plantel_administrativo plantel_administrativo = plantel_administrativoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(plantel_administrativo));
    }

    /**
     * DELETE  /plantel-administrativos/:id : delete the "id" plantel_administrativo.
     *
     * @param id the id of the plantel_administrativo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/plantel-administrativos/{id}")
    @Timed
    public ResponseEntity<Void> deletePlantel_administrativo(@PathVariable Long id) {
        log.debug("REST request to delete Plantel_administrativo : {}", id);
        plantel_administrativoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
