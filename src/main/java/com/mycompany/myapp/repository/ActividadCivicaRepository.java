package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ActividadCivica;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the ActividadCivica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActividadCivicaRepository extends JpaRepository<ActividadCivica, Long> {
    @Query("select distinct actividad_civica from ActividadCivica actividad_civica left join fetch actividad_civica.docentes left join fetch actividad_civica.plantelAdministrativos")
    List<ActividadCivica> findAllWithEagerRelationships();

    @Query("select actividad_civica from ActividadCivica actividad_civica left join fetch actividad_civica.docentes left join fetch actividad_civica.plantelAdministrativos where actividad_civica.id =:id")
    ActividadCivica findOneWithEagerRelationships(@Param("id") Long id);

}
