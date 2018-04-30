package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Reunion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Reunion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReunionRepository extends JpaRepository<Reunion, Long> {

}
