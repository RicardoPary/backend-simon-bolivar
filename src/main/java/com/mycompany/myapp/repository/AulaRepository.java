package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Aula;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Aula entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {
    @Query("select distinct aula from Aula aula left join fetch aula.materias")
    List<Aula> findAllWithEagerRelationships();

    @Query("select aula from Aula aula left join fetch aula.materias where aula.id =:id")
    Aula findOneWithEagerRelationships(@Param("id") Long id);

}
