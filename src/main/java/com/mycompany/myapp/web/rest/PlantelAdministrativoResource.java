package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.PlantelAdministrativo;
import com.mycompany.myapp.domain.extras.PlantelAdministrativoDTO;
import com.mycompany.myapp.repository.PlantelAdministrativoRepository;
import com.mycompany.myapp.service.PlantelAdministrativoService;
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
 * REST controller for managing PlantelAdministrativo.
 */
@RestController
@RequestMapping("/api")
public class PlantelAdministrativoResource {

    private final Logger log = LoggerFactory.getLogger(PlantelAdministrativoResource.class);

    private static final String ENTITY_NAME = "plantelAdministrativo";

    private final PlantelAdministrativoService plantelAdministrativoService;

    private final PlantelAdministrativoRepository plantelAdministrativoRepository;



    public PlantelAdministrativoResource(PlantelAdministrativoService plantelAdministrativoService, PlantelAdministrativoRepository plantelAdministrativoRepository) {
        this.plantelAdministrativoService = plantelAdministrativoService;
        this.plantelAdministrativoRepository = plantelAdministrativoRepository;
    }

    /**
     * POST  /plantel-administrativos : Create a new plantelAdministrativo.
     *
     * @param plantelAdministrativo the plantelAdministrativo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new plantelAdministrativo, or with status 400 (Bad Request) if the plantelAdministrativo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/plantel-administrativos")
    @Timed
    public ResponseEntity<PlantelAdministrativo> createPlantelAdministrativo(@RequestBody PlantelAdministrativo plantelAdministrativo) throws URISyntaxException {
        log.debug("REST request to save PlantelAdministrativo : {}", plantelAdministrativo);
        if (plantelAdministrativo.getId() != null) {
            throw new BadRequestAlertException("A new plantelAdministrativo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlantelAdministrativo result = plantelAdministrativoService.save(plantelAdministrativo);
        return ResponseEntity.created(new URI("/api/plantel-administrativos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /plantel-administrativos : Updates an existing plantelAdministrativo.
     *
     * @param plantelAdministrativo the plantelAdministrativo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated plantelAdministrativo,
     * or with status 400 (Bad Request) if the plantelAdministrativo is not valid,
     * or with status 500 (Internal Server Error) if the plantelAdministrativo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/plantel-administrativos")
    @Timed
    public ResponseEntity<PlantelAdministrativo> updatePlantelAdministrativo(@RequestBody PlantelAdministrativo plantelAdministrativo) throws URISyntaxException {
        log.debug("REST request to update PlantelAdministrativo : {}", plantelAdministrativo);
        if (plantelAdministrativo.getId() == null) {
            return createPlantelAdministrativo(plantelAdministrativo);
        }
        PlantelAdministrativo result = plantelAdministrativoService.save(plantelAdministrativo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, plantelAdministrativo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /plantel-administrativos : get all the plantelAdministrativos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of plantelAdministrativos in body
     */
    @GetMapping("/plantel-administrativos")
    @Timed
    public ResponseEntity<List<PlantelAdministrativo>> getAllPlantelAdministrativos(Pageable pageable) {
        log.debug("REST request to get a page of PlantelAdministrativos");
        Page<PlantelAdministrativo> page = plantelAdministrativoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/plantel-administrativos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /plantel-administrativos/:id : get the "id" plantelAdministrativo.
     *
     * @param id the id of the plantelAdministrativo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the plantelAdministrativo, or with status 404 (Not Found)
     */
    @GetMapping("/plantel-administrativos/{id}")
    @Timed
    public ResponseEntity<PlantelAdministrativo> getPlantelAdministrativo(@PathVariable Long id) {
        log.debug("REST request to get PlantelAdministrativo : {}", id);
        PlantelAdministrativo plantelAdministrativo = plantelAdministrativoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(plantelAdministrativo));
    }

    /**
     * DELETE  /plantel-administrativos/:id : delete the "id" plantelAdministrativo.
     *
     * @param id the id of the plantelAdministrativo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/plantel-administrativos/{id}")
    @Timed
    public ResponseEntity<Void> deletePlantelAdministrativo(@PathVariable Long id) {
        log.debug("REST request to delete PlantelAdministrativo : {}", id);
        plantelAdministrativoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }




    /**
     * GET  /plantel-administrativos : get all the plantelAdministrativos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of plantelAdministrativos in body
     */
    @GetMapping("/plantel-administrativos/all")
    @Timed
    public ResponseEntity<List<PlantelAdministrativoDTO>> getAllPlantelAdministrativosDTO(Pageable pageable) {
        log.debug("REST request to get a page of PlantelAdministrativos");
        Page<PlantelAdministrativoDTO> page = plantelAdministrativoRepository.findAllStudents(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/plantel-administrativos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
