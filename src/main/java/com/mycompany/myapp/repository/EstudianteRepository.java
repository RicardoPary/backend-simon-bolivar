package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Estudiante;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Estudiante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    @Query("select distinct estudiante from Estudiante estudiante left join fetch estudiante.materias")
    List<Estudiante> findAllWithEagerRelationships();

    @Query("select estudiante from Estudiante estudiante left join fetch estudiante.materias where estudiante.id =:id")
    Estudiante findOneWithEagerRelationships(@Param("id") Long id);

}
