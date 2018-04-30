package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Curso;
import com.mycompany.myapp.repository.CursoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Curso.
 */
@Service
@Transactional
public class CursoService {

    private final Logger log = LoggerFactory.getLogger(CursoService.class);

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    /**
     * Save a curso.
     *
     * @param curso the entity to save
     * @return the persisted entity
     */
    public Curso save(Curso curso) {
        log.debug("Request to save Curso : {}", curso);
        return cursoRepository.save(curso);
    }

    /**
     * Get all the cursos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Curso> findAll(Pageable pageable) {
        log.debug("Request to get all Cursos");
        return cursoRepository.findAll(pageable);
    }

    /**
     * Get one curso by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Curso findOne(Long id) {
        log.debug("Request to get Curso : {}", id);
        return cursoRepository.findOne(id);
    }

    /**
     * Delete the curso by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Curso : {}", id);
        cursoRepository.delete(id);
    }
}
