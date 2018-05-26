package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PersonaReunion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PersonaReunion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonaReunionRepository extends JpaRepository<PersonaReunion, Long> {

}
