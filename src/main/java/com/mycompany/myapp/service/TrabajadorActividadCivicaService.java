package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.TrabajadorActividadCivica;
import com.mycompany.myapp.repository.TrabajadorActividadCivicaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TrabajadorActividadCivica.
 */
@Service
@Transactional
public class TrabajadorActividadCivicaService {

    private final Logger log = LoggerFactory.getLogger(TrabajadorActividadCivicaService.class);

    private final TrabajadorActividadCivicaRepository trabajadorActividadCivicaRepository;

    public TrabajadorActividadCivicaService(TrabajadorActividadCivicaRepository trabajadorActividadCivicaRepository) {
        this.trabajadorActividadCivicaRepository = trabajadorActividadCivicaRepository;
    }

    /**
     * Save a trabajadorActividadCivica.
     *
     * @param trabajadorActividadCivica the entity to save
     * @return the persisted entity
     */
    public TrabajadorActividadCivica save(TrabajadorActividadCivica trabajadorActividadCivica) {
        log.debug("Request to save TrabajadorActividadCivica : {}", trabajadorActividadCivica);
        return trabajadorActividadCivicaRepository.save(trabajadorActividadCivica);
    }

    /**
     * Get all the trabajadorActividadCivicas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TrabajadorActividadCivica> findAll(Pageable pageable) {
        log.debug("Request to get all TrabajadorActividadCivicas");
        return trabajadorActividadCivicaRepository.findAll(pageable);
    }

    /**
     * Get one trabajadorActividadCivica by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TrabajadorActividadCivica findOne(Long id) {
        log.debug("Request to get TrabajadorActividadCivica : {}", id);
        return trabajadorActividadCivicaRepository.findOne(id);
    }

    /**
     * Delete the trabajadorActividadCivica by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TrabajadorActividadCivica : {}", id);
        trabajadorActividadCivicaRepository.delete(id);
    }
}
