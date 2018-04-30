package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Estudiante;
import com.mycompany.myapp.repository.EstudianteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Estudiante.
 */
@Service
@Transactional
public class EstudianteService {

    private final Logger log = LoggerFactory.getLogger(EstudianteService.class);

    private final EstudianteRepository estudianteRepository;

    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    /**
     * Save a estudiante.
     *
     * @param estudiante the entity to save
     * @return the persisted entity
     */
    public Estudiante save(Estudiante estudiante) {
        log.debug("Request to save Estudiante : {}", estudiante);
        return estudianteRepository.save(estudiante);
    }

    /**
     * Get all the estudiantes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Estudiante> findAll(Pageable pageable) {
        log.debug("Request to get all Estudiantes");
        return estudianteRepository.findAll(pageable);
    }

    /**
     * Get one estudiante by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Estudiante findOne(Long id) {
        log.debug("Request to get Estudiante : {}", id);
        return estudianteRepository.findOne(id);
    }

    /**
     * Delete the estudiante by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Estudiante : {}", id);
        estudianteRepository.delete(id);
    }
}
