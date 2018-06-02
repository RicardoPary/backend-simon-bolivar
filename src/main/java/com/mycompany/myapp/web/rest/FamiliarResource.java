package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Familiar;
import com.mycompany.myapp.domain.extras.FamiliarDTO;
import com.mycompany.myapp.repository.FamiliarRepository;
import com.mycompany.myapp.service.FamiliarService;
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
 * REST controller for managing Familiar.
 */
@RestController
@RequestMapping("/api")
public class FamiliarResource {

    private final Logger log = LoggerFactory.getLogger(FamiliarResource.class);

    private static final String ENTITY_NAME = "familiar";

    private final FamiliarService familiarService;

    private final FamiliarRepository familiarRepository;

    public FamiliarResource(FamiliarService familiarService, FamiliarRepository familiarRepository) {
        this.familiarService = familiarService;
        this.familiarRepository = familiarRepository;
    }

    /**
     * POST  /familiars : Create a new familiar.
     *
     * @param familiar the familiar to create
     * @return the ResponseEntity with status 201 (Created) and with body the new familiar, or with status 400 (Bad Request) if the familiar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/familiars")
    @Timed
    public ResponseEntity<Familiar> createFamiliar(@RequestBody Familiar familiar) throws URISyntaxException {
        log.debug("REST request to save Familiar : {}", familiar);
        if (familiar.getId() != null) {
            throw new BadRequestAlertException("A new familiar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Familiar result = familiarService.save(familiar);
        return ResponseEntity.created(new URI("/api/familiars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /familiars : Updates an existing familiar.
     *
     * @param familiar the familiar to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated familiar,
     * or with status 400 (Bad Request) if the familiar is not valid,
     * or with status 500 (Internal Server Error) if the familiar couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/familiars")
    @Timed
    public ResponseEntity<Familiar> updateFamiliar(@RequestBody Familiar familiar) throws URISyntaxException {
        log.debug("REST request to update Familiar : {}", familiar);
        if (familiar.getId() == null) {
            return createFamiliar(familiar);
        }
        Familiar result = familiarService.save(familiar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, familiar.getId().toString()))
            .body(result);
    }

    /**
     * GET  /familiars : get all the familiars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of familiars in body
     */
    @GetMapping("/familiars")
    @Timed
    public ResponseEntity<List<Familiar>> getAllFamiliars(Pageable pageable) {
        log.debug("REST request to get a page of Familiars");
        Page<Familiar> page = familiarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/familiars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /familiars/:id : get the "id" familiar.
     *
     * @param id the id of the familiar to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the familiar, or with status 404 (Not Found)
     */
    @GetMapping("/familiars/{id}")
    @Timed
    public ResponseEntity<Familiar> getFamiliar(@PathVariable Long id) {
        log.debug("REST request to get Familiar : {}", id);
        Familiar familiar = familiarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(familiar));
    }

    /**
     * DELETE  /familiars/:id : delete the "id" familiar.
     *
     * @param id the id of the familiar to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/familiars/{id}")
    @Timed
    public ResponseEntity<Void> deleteFamiliar(@PathVariable Long id) {
        log.debug("REST request to delete Familiar : {}", id);
        familiarService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }




    /**
     * GET  /familiars : get all the familiars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of familiars in body
     */
    @GetMapping("/familiars/all")
    @Timed
    public ResponseEntity<List<FamiliarDTO>> getAllFamiliarsDTO(Pageable pageable) {
        log.debug("REST request to get a page of Familiars");
        Page<FamiliarDTO> page = familiarRepository.findAllStudents(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/familiars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

    }

}
