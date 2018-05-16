package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Inscrito;
import com.mycompany.myapp.repository.InscritoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Inscrito.
 */
@Service
@Transactional
public class InscritoService {

    private final Logger log = LoggerFactory.getLogger(InscritoService.class);

    private final InscritoRepository inscritoRepository;

    public InscritoService(InscritoRepository inscritoRepository) {
        this.inscritoRepository = inscritoRepository;
    }

    /**
     * Save a inscrito.
     *
     * @param inscrito the entity to save
     * @return the persisted entity
     */
    public Inscrito save(Inscrito inscrito) {
        log.debug("Request to save Inscrito : {}", inscrito);
        return inscritoRepository.save(inscrito);
    }

    /**
     * Get all the inscritos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Inscrito> findAll(Pageable pageable) {
        log.debug("Request to get all Inscritos");
        return inscritoRepository.findAll(pageable);
    }

    /**
     * Get one inscrito by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Inscrito findOne(Long id) {
        log.debug("Request to get Inscrito : {}", id);
        return inscritoRepository.findOne(id);
    }

    /**
     * Delete the inscrito by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Inscrito : {}", id);
        inscritoRepository.delete(id);
    }
}
