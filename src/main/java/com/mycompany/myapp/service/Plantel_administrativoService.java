package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Plantel_administrativo;
import com.mycompany.myapp.repository.Plantel_administrativoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Plantel_administrativo.
 */
@Service
@Transactional
public class Plantel_administrativoService {

    private final Logger log = LoggerFactory.getLogger(Plantel_administrativoService.class);

    private final Plantel_administrativoRepository plantel_administrativoRepository;

    public Plantel_administrativoService(Plantel_administrativoRepository plantel_administrativoRepository) {
        this.plantel_administrativoRepository = plantel_administrativoRepository;
    }

    /**
     * Save a plantel_administrativo.
     *
     * @param plantel_administrativo the entity to save
     * @return the persisted entity
     */
    public Plantel_administrativo save(Plantel_administrativo plantel_administrativo) {
        log.debug("Request to save Plantel_administrativo : {}", plantel_administrativo);
        return plantel_administrativoRepository.save(plantel_administrativo);
    }

    /**
     * Get all the plantel_administrativos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Plantel_administrativo> findAll(Pageable pageable) {
        log.debug("Request to get all Plantel_administrativos");
        return plantel_administrativoRepository.findAll(pageable);
    }

    /**
     * Get one plantel_administrativo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Plantel_administrativo findOne(Long id) {
        log.debug("Request to get Plantel_administrativo : {}", id);
        return plantel_administrativoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the plantel_administrativo by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Plantel_administrativo : {}", id);
        plantel_administrativoRepository.delete(id);
    }
}
