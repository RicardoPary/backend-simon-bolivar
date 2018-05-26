package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.PersonaReunion;
import com.mycompany.myapp.repository.PersonaReunionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing PersonaReunion.
 */
@Service
@Transactional
public class PersonaReunionService {

    private final Logger log = LoggerFactory.getLogger(PersonaReunionService.class);

    private final PersonaReunionRepository personaReunionRepository;

    public PersonaReunionService(PersonaReunionRepository personaReunionRepository) {
        this.personaReunionRepository = personaReunionRepository;
    }

    /**
     * Save a personaReunion.
     *
     * @param personaReunion the entity to save
     * @return the persisted entity
     */
    public PersonaReunion save(PersonaReunion personaReunion) {
        log.debug("Request to save PersonaReunion : {}", personaReunion);
        return personaReunionRepository.save(personaReunion);
    }

    /**
     * Get all the personaReunions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PersonaReunion> findAll(Pageable pageable) {
        log.debug("Request to get all PersonaReunions");
        return personaReunionRepository.findAll(pageable);
    }

    /**
     * Get one personaReunion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PersonaReunion findOne(Long id) {
        log.debug("Request to get PersonaReunion : {}", id);
        return personaReunionRepository.findOne(id);
    }

    /**
     * Delete the personaReunion by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonaReunion : {}", id);
        personaReunionRepository.delete(id);
    }
}
