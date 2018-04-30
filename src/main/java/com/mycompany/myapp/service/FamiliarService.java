package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Familiar;
import com.mycompany.myapp.repository.FamiliarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Familiar.
 */
@Service
@Transactional
public class FamiliarService {

    private final Logger log = LoggerFactory.getLogger(FamiliarService.class);

    private final FamiliarRepository familiarRepository;

    public FamiliarService(FamiliarRepository familiarRepository) {
        this.familiarRepository = familiarRepository;
    }

    /**
     * Save a familiar.
     *
     * @param familiar the entity to save
     * @return the persisted entity
     */
    public Familiar save(Familiar familiar) {
        log.debug("Request to save Familiar : {}", familiar);
        return familiarRepository.save(familiar);
    }

    /**
     * Get all the familiars.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Familiar> findAll(Pageable pageable) {
        log.debug("Request to get all Familiars");
        return familiarRepository.findAll(pageable);
    }

    /**
     * Get one familiar by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Familiar findOne(Long id) {
        log.debug("Request to get Familiar : {}", id);
        return familiarRepository.findOne(id);
    }

    /**
     * Delete the familiar by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Familiar : {}", id);
        familiarRepository.delete(id);
    }
}
