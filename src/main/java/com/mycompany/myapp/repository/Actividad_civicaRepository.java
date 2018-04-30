package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Actividad_civica;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Actividad_civica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Actividad_civicaRepository extends JpaRepository<Actividad_civica, Long> {
    @Query("select distinct actividad_civica from Actividad_civica actividad_civica left join fetch actividad_civica.docentes left join fetch actividad_civica.plantel_administrativos")
    List<Actividad_civica> findAllWithEagerRelationships();

    @Query("select actividad_civica from Actividad_civica actividad_civica left join fetch actividad_civica.docentes left join fetch actividad_civica.plantel_administrativos where actividad_civica.id =:id")
    Actividad_civica findOneWithEagerRelationships(@Param("id") Long id);

}
