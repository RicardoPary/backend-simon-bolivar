package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AulaMateria;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AulaMateria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AulaMateriaRepository extends JpaRepository<AulaMateria, Long> {

}
