package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Reunion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Reunion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReunionRepository extends JpaRepository<Reunion, Long> {
    @Query("select distinct reunion from Reunion reunion left join fetch reunion.personas")
    List<Reunion> findAllWithEagerRelationships();

    @Query("select reunion from Reunion reunion left join fetch reunion.personas where reunion.id =:id")
    Reunion findOneWithEagerRelationships(@Param("id") Long id);

}
