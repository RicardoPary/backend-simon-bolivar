package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.AulaMateria;
import com.mycompany.myapp.repository.AulaMateriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing AulaMateria.
 */
@Service
@Transactional
public class AulaMateriaService {

    private final Logger log = LoggerFactory.getLogger(AulaMateriaService.class);

    private final AulaMateriaRepository aulaMateriaRepository;

    public AulaMateriaService(AulaMateriaRepository aulaMateriaRepository) {
        this.aulaMateriaRepository = aulaMateriaRepository;
    }

    /**
     * Save a aulaMateria.
     *
     * @param aulaMateria the entity to save
     * @return the persisted entity
     */
    public AulaMateria save(AulaMateria aulaMateria) {
        log.debug("Request to save AulaMateria : {}", aulaMateria);
        return aulaMateriaRepository.save(aulaMateria);
    }

    /**
     * Get all the aulaMaterias.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AulaMateria> findAll(Pageable pageable) {
        log.debug("Request to get all AulaMaterias");
        return aulaMateriaRepository.findAll(pageable);
    }

    /**
     * Get one aulaMateria by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public AulaMateria findOne(Long id) {
        log.debug("Request to get AulaMateria : {}", id);
        return aulaMateriaRepository.findOne(id);
    }

    /**
     * Delete the aulaMateria by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AulaMateria : {}", id);
        aulaMateriaRepository.delete(id);
    }
}
