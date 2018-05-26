package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.EstudianteMateria;
import com.mycompany.myapp.repository.EstudianteMateriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing EstudianteMateria.
 */
@Service
@Transactional
public class EstudianteMateriaService {

    private final Logger log = LoggerFactory.getLogger(EstudianteMateriaService.class);

    private final EstudianteMateriaRepository estudianteMateriaRepository;

    public EstudianteMateriaService(EstudianteMateriaRepository estudianteMateriaRepository) {
        this.estudianteMateriaRepository = estudianteMateriaRepository;
    }

    /**
     * Save a estudianteMateria.
     *
     * @param estudianteMateria the entity to save
     * @return the persisted entity
     */
    public EstudianteMateria save(EstudianteMateria estudianteMateria) {
        log.debug("Request to save EstudianteMateria : {}", estudianteMateria);
        return estudianteMateriaRepository.save(estudianteMateria);
    }

    /**
     * Get all the estudianteMaterias.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EstudianteMateria> findAll(Pageable pageable) {
        log.debug("Request to get all EstudianteMaterias");
        return estudianteMateriaRepository.findAll(pageable);
    }

    /**
     * Get one estudianteMateria by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public EstudianteMateria findOne(Long id) {
        log.debug("Request to get EstudianteMateria : {}", id);
        return estudianteMateriaRepository.findOne(id);
    }

    /**
     * Delete the estudianteMateria by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EstudianteMateria : {}", id);
        estudianteMateriaRepository.delete(id);
    }
}
