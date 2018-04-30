package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Reunion;
import com.mycompany.myapp.repository.ReunionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Reunion.
 */
@Service
@Transactional
public class ReunionService {

    private final Logger log = LoggerFactory.getLogger(ReunionService.class);

    private final ReunionRepository reunionRepository;

    public ReunionService(ReunionRepository reunionRepository) {
        this.reunionRepository = reunionRepository;
    }

    /**
     * Save a reunion.
     *
     * @param reunion the entity to save
     * @return the persisted entity
     */
    public Reunion save(Reunion reunion) {
        log.debug("Request to save Reunion : {}", reunion);
        return reunionRepository.save(reunion);
    }

    /**
     * Get all the reunions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Reunion> findAll(Pageable pageable) {
        log.debug("Request to get all Reunions");
        return reunionRepository.findAll(pageable);
    }

    /**
     * Get one reunion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Reunion findOne(Long id) {
        log.debug("Request to get Reunion : {}", id);
        return reunionRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the reunion by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Reunion : {}", id);
        reunionRepository.delete(id);
    }
}
