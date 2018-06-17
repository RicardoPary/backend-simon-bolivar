package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.EstudianteCurso;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EstudianteCurso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstudianteCursoRepository extends JpaRepository<EstudianteCurso, Long> {

}
