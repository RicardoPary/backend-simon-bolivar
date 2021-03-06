package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Materia;
import com.mycompany.myapp.repository.MateriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Materia.
 */
@Service
@Transactional
public class MateriaService {

    private final Logger log = LoggerFactory.getLogger(MateriaService.class);

    private final MateriaRepository materiaRepository;

    public MateriaService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    /**
     * Save a materia.
     *
     * @param materia the entity to save
     * @return the persisted entity
     */
    public Materia save(Materia materia) {
        log.debug("Request to save Materia : {}", materia);
        return materiaRepository.save(materia);
    }

    /**
     * Get all the materias.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Materia> findAll(Pageable pageable) {
        log.debug("Request to get all Materias");
        return materiaRepository.findAll(pageable);
    }

    /**
     * Get one materia by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Materia findOne(Long id) {
        log.debug("Request to get Materia : {}", id);
        return materiaRepository.findOne(id);
    }

    /**
     * Delete the materia by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Materia : {}", id);
        materiaRepository.delete(id);
    }





    /**
     * Get all the materias by idCurso.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Materia> findAllByIdCurso(Pageable pageable, Long id) {
        log.debug("Request to get all Materias");
        return materiaRepository.findAllByIdCurso(pageable, id);
    }

    /**
     * Get all the materias by idCurso.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Materia> findAllByIdCursoAndIdDocente(Pageable pageable, Long idCurso, Long idDocente) {
        log.debug("Request to get all Materias");
        return materiaRepository.findAllByIdCursoAndIdDocente(pageable, idCurso, idDocente);
    }
}
