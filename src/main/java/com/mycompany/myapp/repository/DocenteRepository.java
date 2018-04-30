package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Docente;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Docente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {
    @Query("select distinct docente from Docente docente left join fetch docente.materias left join fetch docente.actividad_civicas")
    List<Docente> findAllWithEagerRelationships();

    @Query("select docente from Docente docente left join fetch docente.materias left join fetch docente.actividad_civicas where docente.id =:id")
    Docente findOneWithEagerRelationships(@Param("id") Long id);

}
