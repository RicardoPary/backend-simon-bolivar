package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Bimestre;
import com.mycompany.myapp.repository.BimestreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Bimestre.
 */
@Service
@Transactional
public class BimestreService {

    private final Logger log = LoggerFactory.getLogger(BimestreService.class);

    private final BimestreRepository bimestreRepository;

    public BimestreService(BimestreRepository bimestreRepository) {
        this.bimestreRepository = bimestreRepository;
    }

    /**
     * Save a bimestre.
     *
     * @param bimestre the entity to save
     * @return the persisted entity
     */
    public Bimestre save(Bimestre bimestre) {
        log.debug("Request to save Bimestre : {}", bimestre);
        return bimestreRepository.save(bimestre);
    }

    /**
     * Get all the bimestres.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Bimestre> findAll(Pageable pageable) {
        log.debug("Request to get all Bimestres");
        return bimestreRepository.findAll(pageable);
    }

    /**
     * Get one bimestre by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Bimestre findOne(Long id) {
        log.debug("Request to get Bimestre : {}", id);
        return bimestreRepository.findOne(id);
    }

    /**
     * Delete the bimestre by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Bimestre : {}", id);
        bimestreRepository.delete(id);
    }
}
