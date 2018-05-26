package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.EstudianteMateria;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EstudianteMateria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstudianteMateriaRepository extends JpaRepository<EstudianteMateria, Long> {

}
