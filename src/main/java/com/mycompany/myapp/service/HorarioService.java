package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Horario;
import com.mycompany.myapp.repository.HorarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Horario.
 */
@Service
@Transactional
public class HorarioService {

    private final Logger log = LoggerFactory.getLogger(HorarioService.class);

    private final HorarioRepository horarioRepository;

    public HorarioService(HorarioRepository horarioRepository) {
        this.horarioRepository = horarioRepository;
    }

    /**
     * Save a horario.
     *
     * @param horario the entity to save
     * @return the persisted entity
     */
    public Horario save(Horario horario) {
        log.debug("Request to save Horario : {}", horario);
        return horarioRepository.save(horario);
    }

    /**
     * Get all the horarios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Horario> findAll(Pageable pageable) {
        log.debug("Request to get all Horarios");
        return horarioRepository.findAll(pageable);
    }

    /**
     * Get one horario by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Horario findOne(Long id) {
        log.debug("Request to get Horario : {}", id);
        return horarioRepository.findOne(id);
    }

    /**
     * Delete the horario by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Horario : {}", id);
        horarioRepository.delete(id);
    }
}
