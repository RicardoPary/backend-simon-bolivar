package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Libreta;
import com.mycompany.myapp.repository.LibretaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Libreta.
 */
@Service
@Transactional
public class LibretaService {

    private final Logger log = LoggerFactory.getLogger(LibretaService.class);

    private final LibretaRepository libretaRepository;

    public LibretaService(LibretaRepository libretaRepository) {
        this.libretaRepository = libretaRepository;
    }

    /**
     * Save a libreta.
     *
     * @param libreta the entity to save
     * @return the persisted entity
     */
    public Libreta save(Libreta libreta) {
        log.debug("Request to save Libreta : {}", libreta);
        return libretaRepository.save(libreta);
    }

    /**
     * Get all the libretas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Libreta> findAll(Pageable pageable) {
        log.debug("Request to get all Libretas");
        return libretaRepository.findAll(pageable);
    }

    /**
     * Get one libreta by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Libreta findOne(Long id) {
        log.debug("Request to get Libreta : {}", id);
        return libretaRepository.findOne(id);
    }

    /**
     * Delete the libreta by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Libreta : {}", id);
        libretaRepository.delete(id);
    }
}
