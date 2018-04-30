package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Aula;
import com.mycompany.myapp.repository.AulaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Aula.
 */
@Service
@Transactional
public class AulaService {

    private final Logger log = LoggerFactory.getLogger(AulaService.class);

    private final AulaRepository aulaRepository;

    public AulaService(AulaRepository aulaRepository) {
        this.aulaRepository = aulaRepository;
    }

    /**
     * Save a aula.
     *
     * @param aula the entity to save
     * @return the persisted entity
     */
    public Aula save(Aula aula) {
        log.debug("Request to save Aula : {}", aula);
        return aulaRepository.save(aula);
    }

    /**
     * Get all the aulas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Aula> findAll(Pageable pageable) {
        log.debug("Request to get all Aulas");
        return aulaRepository.findAll(pageable);
    }

    /**
     * Get one aula by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Aula findOne(Long id) {
        log.debug("Request to get Aula : {}", id);
        return aulaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the aula by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Aula : {}", id);
        aulaRepository.delete(id);
    }
}
