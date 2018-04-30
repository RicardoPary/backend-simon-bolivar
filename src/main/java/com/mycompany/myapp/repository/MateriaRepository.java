package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Materia;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Materia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {
    @Query("select distinct materia from Materia materia left join fetch materia.docentes")
    List<Materia> findAllWithEagerRelationships();

    @Query("select materia from Materia materia left join fetch materia.docentes where materia.id =:id")
    Materia findOneWithEagerRelationships(@Param("id") Long id);

}
