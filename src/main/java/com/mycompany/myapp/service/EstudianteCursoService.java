package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.EstudianteCurso;
import com.mycompany.myapp.domain.extras.InscripcionDTO;
import com.mycompany.myapp.repository.EstudianteCursoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing EstudianteCurso.
 */
@Service
@Transactional
public class EstudianteCursoService {

    private final Logger log = LoggerFactory.getLogger(EstudianteCursoService.class);

    private final EstudianteCursoRepository estudianteCursoRepository;

    public EstudianteCursoService(EstudianteCursoRepository estudianteCursoRepository) {
        this.estudianteCursoRepository = estudianteCursoRepository;
    }

    /**
     * Save a estudianteCurso.
     *
     * @param estudianteCurso the entity to save
     * @return the persisted entity
     */
    public EstudianteCurso save(EstudianteCurso estudianteCurso) {
        log.debug("Request to save EstudianteCurso : {}", estudianteCurso);
        return estudianteCursoRepository.save(estudianteCurso);
    }

    /**
     * Get all the estudianteCursos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EstudianteCurso> findAll(Pageable pageable) {
        log.debug("Request to get all EstudianteCursos");
        return estudianteCursoRepository.findAll(pageable);
    }

    /**
     * Get one estudianteCurso by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public EstudianteCurso findOne(Long id) {
        log.debug("Request to get EstudianteCurso : {}", id);
        return estudianteCursoRepository.findOne(id);
    }

    /**
     * Delete the estudianteCurso by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EstudianteCurso : {}", id);
        estudianteCursoRepository.delete(id);
    }



    @Transactional(readOnly = true)
    public Page<InscripcionDTO> getAllInscripciones(Pageable pageable) {
        return estudianteCursoRepository.findAll(pageable).map(InscripcionDTO::new);
    }
}
