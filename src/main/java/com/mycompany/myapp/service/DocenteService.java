package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Docente;
import com.mycompany.myapp.repository.DocenteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Docente.
 */
@Service
@Transactional
public class DocenteService {

    private final Logger log = LoggerFactory.getLogger(DocenteService.class);

    private final DocenteRepository docenteRepository;

    public DocenteService(DocenteRepository docenteRepository) {
        this.docenteRepository = docenteRepository;
    }

    /**
     * Save a docente.
     *
     * @param docente the entity to save
     * @return the persisted entity
     */
    public Docente save(Docente docente) {
        log.debug("Request to save Docente : {}", docente);
        return docenteRepository.save(docente);
    }

    /**
     * Get all the docentes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Docente> findAll(Pageable pageable) {
        log.debug("Request to get all Docentes");
        return docenteRepository.findAll(pageable);
    }

    /**
     * Get one docente by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Docente findOne(Long id) {
        log.debug("Request to get Docente : {}", id);
        return docenteRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the docente by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Docente : {}", id);
        docenteRepository.delete(id);
    }
}
