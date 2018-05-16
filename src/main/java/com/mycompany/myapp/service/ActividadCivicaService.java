package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ActividadCivica;
import com.mycompany.myapp.repository.ActividadCivicaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ActividadCivica.
 */
@Service
@Transactional
public class ActividadCivicaService {

    private final Logger log = LoggerFactory.getLogger(ActividadCivicaService.class);

    private final ActividadCivicaRepository actividadCivicaRepository;

    public ActividadCivicaService(ActividadCivicaRepository actividadCivicaRepository) {
        this.actividadCivicaRepository = actividadCivicaRepository;
    }

    /**
     * Save a actividadCivica.
     *
     * @param actividadCivica the entity to save
     * @return the persisted entity
     */
    public ActividadCivica save(ActividadCivica actividadCivica) {
        log.debug("Request to save ActividadCivica : {}", actividadCivica);
        return actividadCivicaRepository.save(actividadCivica);
    }

    /**
     * Get all the actividadCivicas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ActividadCivica> findAll(Pageable pageable) {
        log.debug("Request to get all ActividadCivicas");
        return actividadCivicaRepository.findAll(pageable);
    }

    /**
     * Get one actividadCivica by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ActividadCivica findOne(Long id) {
        log.debug("Request to get ActividadCivica : {}", id);
        return actividadCivicaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the actividadCivica by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ActividadCivica : {}", id);
        actividadCivicaRepository.delete(id);
    }
}
