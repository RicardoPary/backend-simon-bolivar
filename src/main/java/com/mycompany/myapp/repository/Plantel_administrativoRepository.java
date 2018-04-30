package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Plantel_administrativo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Plantel_administrativo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Plantel_administrativoRepository extends JpaRepository<Plantel_administrativo, Long> {
    @Query("select distinct plantel_administrativo from Plantel_administrativo plantel_administrativo left join fetch plantel_administrativo.actividad_civicas")
    List<Plantel_administrativo> findAllWithEagerRelationships();

    @Query("select plantel_administrativo from Plantel_administrativo plantel_administrativo left join fetch plantel_administrativo.actividad_civicas where plantel_administrativo.id =:id")
    Plantel_administrativo findOneWithEagerRelationships(@Param("id") Long id);

}
