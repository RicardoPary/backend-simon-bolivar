package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Nota;
import com.mycompany.myapp.repository.NotaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Nota.
 */
@Service
@Transactional
public class NotaService {

    private final Logger log = LoggerFactory.getLogger(NotaService.class);

    private final NotaRepository notaRepository;

    public NotaService(NotaRepository notaRepository) {
        this.notaRepository = notaRepository;
    }

    /**
     * Save a nota.
     *
     * @param nota the entity to save
     * @return the persisted entity
     */
    public Nota save(Nota nota) {
        log.debug("Request to save Nota : {}", nota);
        return notaRepository.save(nota);
    }

    /**
     * Get all the notas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Nota> findAll(Pageable pageable) {
        log.debug("Request to get all Notas");
        return notaRepository.findAll(pageable);
    }

    /**
     * Get one nota by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Nota findOne(Long id) {
        log.debug("Request to get Nota : {}", id);
        return notaRepository.findOne(id);
    }

    /**
     * Delete the nota by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Nota : {}", id);
        notaRepository.delete(id);
    }
}
