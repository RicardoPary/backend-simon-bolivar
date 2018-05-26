package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Trabajador;
import com.mycompany.myapp.repository.TrabajadorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Trabajador.
 */
@Service
@Transactional
public class TrabajadorService {

    private final Logger log = LoggerFactory.getLogger(TrabajadorService.class);

    private final TrabajadorRepository trabajadorRepository;

    public TrabajadorService(TrabajadorRepository trabajadorRepository) {
        this.trabajadorRepository = trabajadorRepository;
    }

    /**
     * Save a trabajador.
     *
     * @param trabajador the entity to save
     * @return the persisted entity
     */
    public Trabajador save(Trabajador trabajador) {
        log.debug("Request to save Trabajador : {}", trabajador);
        return trabajadorRepository.save(trabajador);
    }

    /**
     * Get all the trabajadors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Trabajador> findAll(Pageable pageable) {
        log.debug("Request to get all Trabajadors");
        return trabajadorRepository.findAll(pageable);
    }

    /**
     * Get one trabajador by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Trabajador findOne(Long id) {
        log.debug("Request to get Trabajador : {}", id);
        return trabajadorRepository.findOne(id);
    }

    /**
     * Delete the trabajador by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Trabajador : {}", id);
        trabajadorRepository.delete(id);
    }
}
