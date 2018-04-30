package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Materia;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Materia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {

}
