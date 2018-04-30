package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Actividad_civica;
import com.mycompany.myapp.repository.Actividad_civicaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Actividad_civica.
 */
@Service
@Transactional
public class Actividad_civicaService {

    private final Logger log = LoggerFactory.getLogger(Actividad_civicaService.class);

    private final Actividad_civicaRepository actividad_civicaRepository;

    public Actividad_civicaService(Actividad_civicaRepository actividad_civicaRepository) {
        this.actividad_civicaRepository = actividad_civicaRepository;
    }

    /**
     * Save a actividad_civica.
     *
     * @param actividad_civica the entity to save
     * @return the persisted entity
     */
    public Actividad_civica save(Actividad_civica actividad_civica) {
        log.debug("Request to save Actividad_civica : {}", actividad_civica);
        return actividad_civicaRepository.save(actividad_civica);
    }

    /**
     * Get all the actividad_civicas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Actividad_civica> findAll(Pageable pageable) {
        log.debug("Request to get all Actividad_civicas");
        return actividad_civicaRepository.findAll(pageable);
    }

    /**
     * Get one actividad_civica by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Actividad_civica findOne(Long id) {
        log.debug("Request to get Actividad_civica : {}", id);
        return actividad_civicaRepository.findOne(id);
    }

    /**
     * Delete the actividad_civica by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Actividad_civica : {}", id);
        actividad_civicaRepository.delete(id);
    }
}
