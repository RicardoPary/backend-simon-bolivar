package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.PlantelAdministrativo;
import com.mycompany.myapp.repository.PlantelAdministrativoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing PlantelAdministrativo.
 */
@Service
@Transactional
public class PlantelAdministrativoService {

    private final Logger log = LoggerFactory.getLogger(PlantelAdministrativoService.class);

    private final PlantelAdministrativoRepository plantelAdministrativoRepository;

    public PlantelAdministrativoService(PlantelAdministrativoRepository plantelAdministrativoRepository) {
        this.plantelAdministrativoRepository = plantelAdministrativoRepository;
    }

    /**
     * Save a plantelAdministrativo.
     *
     * @param plantelAdministrativo the entity to save
     * @return the persisted entity
     */
    public PlantelAdministrativo save(PlantelAdministrativo plantelAdministrativo) {
        log.debug("Request to save PlantelAdministrativo : {}", plantelAdministrativo);
        return plantelAdministrativoRepository.save(plantelAdministrativo);
    }

    /**
     * Get all the plantelAdministrativos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PlantelAdministrativo> findAll(Pageable pageable) {
        log.debug("Request to get all PlantelAdministrativos");
        return plantelAdministrativoRepository.findAll(pageable);
    }

    /**
     * Get one plantelAdministrativo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PlantelAdministrativo findOne(Long id) {
        log.debug("Request to get PlantelAdministrativo : {}", id);
        return plantelAdministrativoRepository.findOne(id);
    }

    /**
     * Delete the plantelAdministrativo by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PlantelAdministrativo : {}", id);
        plantelAdministrativoRepository.delete(id);
    }
}
