package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Nivel;
import com.mycompany.myapp.repository.NivelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Nivel.
 */
@Service
@Transactional
public class NivelService {

    private final Logger log = LoggerFactory.getLogger(NivelService.class);

    private final NivelRepository nivelRepository;

    public NivelService(NivelRepository nivelRepository) {
        this.nivelRepository = nivelRepository;
    }

    /**
     * Save a nivel.
     *
     * @param nivel the entity to save
     * @return the persisted entity
     */
    public Nivel save(Nivel nivel) {
        log.debug("Request to save Nivel : {}", nivel);
        return nivelRepository.save(nivel);
    }

    /**
     * Get all the nivels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Nivel> findAll(Pageable pageable) {
        log.debug("Request to get all Nivels");
        return nivelRepository.findAll(pageable);
    }

    /**
     * Get one nivel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Nivel findOne(Long id) {
        log.debug("Request to get Nivel : {}", id);
        return nivelRepository.findOne(id);
    }

    /**
     * Delete the nivel by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Nivel : {}", id);
        nivelRepository.delete(id);
    }
}
