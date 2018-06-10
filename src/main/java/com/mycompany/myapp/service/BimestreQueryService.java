package com.mycompany.myapp.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mycompany.myapp.domain.Bimestre;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.BimestreRepository;
import com.mycompany.myapp.service.dto.BimestreCriteria;


/**
 * Service for executing complex queries for Bimestre entities in the database.
 * The main input is a {@link BimestreCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Bimestre} or a {@link Page} of {@link Bimestre} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BimestreQueryService extends QueryService<Bimestre> {

    private final Logger log = LoggerFactory.getLogger(BimestreQueryService.class);


    private final BimestreRepository bimestreRepository;

    public BimestreQueryService(BimestreRepository bimestreRepository) {
        this.bimestreRepository = bimestreRepository;
    }

    /**
     * Return a {@link List} of {@link Bimestre} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Bimestre> findByCriteria(BimestreCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Bimestre> specification = createSpecification(criteria);
        return bimestreRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Bimestre} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Bimestre> findByCriteria(BimestreCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Bimestre> specification = createSpecification(criteria);
        return bimestreRepository.findAll(specification, page);
    }

    /**
     * Function to convert BimestreCriteria to a {@link Specifications}
     */
    private Specifications<Bimestre> createSpecification(BimestreCriteria criteria) {
        Specifications<Bimestre> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getIdEstudiante() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdEstudiante(), Bimestre_.idEstudiante));
            }
            if (criteria.getIdDocente() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdDocente(), Bimestre_.idDocente));
            }
            if (criteria.getBimestre() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBimestre(), Bimestre_.bimestre));
            }
            if (criteria.getParalelo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParalelo(), Bimestre_.paralelo));
            }
            if (criteria.getGestion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGestion(), Bimestre_.gestion));
            }
            if (criteria.getIdMateria() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdMateria(), Bimestre_.idMateria));
            }
        }
        return specification;
    }

}
