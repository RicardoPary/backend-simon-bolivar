package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Contrato;
import com.mycompany.myapp.repository.ContratoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Contrato.
 */
@Service
@Transactional
public class ContratoService {

    private final Logger log = LoggerFactory.getLogger(ContratoService.class);

    private final ContratoRepository contratoRepository;

    public ContratoService(ContratoRepository contratoRepository) {
        this.contratoRepository = contratoRepository;
    }

    /**
     * Save a contrato.
     *
     * @param contrato the entity to save
     * @return the persisted entity
     */
    public Contrato save(Contrato contrato) {
        log.debug("Request to save Contrato : {}", contrato);
        return contratoRepository.save(contrato);
    }

    /**
     * Get all the contratoes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Contrato> findAll(Pageable pageable) {
        log.debug("Request to get all Contratoes");
        return contratoRepository.findAll(pageable);
    }

    /**
     * Get one contrato by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Contrato findOne(Long id) {
        log.debug("Request to get Contrato : {}", id);
        return contratoRepository.findOne(id);
    }

    /**
     * Delete the contrato by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Contrato : {}", id);
        contratoRepository.delete(id);
    }
}
