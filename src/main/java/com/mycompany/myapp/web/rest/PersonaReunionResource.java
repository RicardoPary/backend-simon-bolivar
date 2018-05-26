package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.PersonaReunion;
import com.mycompany.myapp.service.PersonaReunionService;
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
 * REST controller for managing PersonaReunion.
 */
@RestController
@RequestMapping("/api")
public class PersonaReunionResource {

    private final Logger log = LoggerFactory.getLogger(PersonaReunionResource.class);

    private static final String ENTITY_NAME = "personaReunion";

    private final PersonaReunionService personaReunionService;

    public PersonaReunionResource(PersonaReunionService personaReunionService) {
        this.personaReunionService = personaReunionService;
    }

    /**
     * POST  /persona-reunions : Create a new personaReunion.
     *
     * @param personaReunion the personaReunion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personaReunion, or with status 400 (Bad Request) if the personaReunion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/persona-reunions")
    @Timed
    public ResponseEntity<PersonaReunion> createPersonaReunion(@RequestBody PersonaReunion personaReunion) throws URISyntaxException {
        log.debug("REST request to save PersonaReunion : {}", personaReunion);
        if (personaReunion.getId() != null) {
            throw new BadRequestAlertException("A new personaReunion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonaReunion result = personaReunionService.save(personaReunion);
        return ResponseEntity.created(new URI("/api/persona-reunions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /persona-reunions : Updates an existing personaReunion.
     *
     * @param personaReunion the personaReunion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personaReunion,
     * or with status 400 (Bad Request) if the personaReunion is not valid,
     * or with status 500 (Internal Server Error) if the personaReunion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/persona-reunions")
    @Timed
    public ResponseEntity<PersonaReunion> updatePersonaReunion(@RequestBody PersonaReunion personaReunion) throws URISyntaxException {
        log.debug("REST request to update PersonaReunion : {}", personaReunion);
        if (personaReunion.getId() == null) {
            return createPersonaReunion(personaReunion);
        }
        PersonaReunion result = personaReunionService.save(personaReunion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personaReunion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /persona-reunions : get all the personaReunions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personaReunions in body
     */
    @GetMapping("/persona-reunions")
    @Timed
    public ResponseEntity<List<PersonaReunion>> getAllPersonaReunions(Pageable pageable) {
        log.debug("REST request to get a page of PersonaReunions");
        Page<PersonaReunion> page = personaReunionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/persona-reunions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /persona-reunions/:id : get the "id" personaReunion.
     *
     * @param id the id of the personaReunion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personaReunion, or with status 404 (Not Found)
     */
    @GetMapping("/persona-reunions/{id}")
    @Timed
    public ResponseEntity<PersonaReunion> getPersonaReunion(@PathVariable Long id) {
        log.debug("REST request to get PersonaReunion : {}", id);
        PersonaReunion personaReunion = personaReunionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(personaReunion));
    }

    /**
     * DELETE  /persona-reunions/:id : delete the "id" personaReunion.
     *
     * @param id the id of the personaReunion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/persona-reunions/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonaReunion(@PathVariable Long id) {
        log.debug("REST request to delete PersonaReunion : {}", id);
        personaReunionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
