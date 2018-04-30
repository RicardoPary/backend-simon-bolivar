package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Familiar;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Familiar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FamiliarRepository extends JpaRepository<Familiar, Long> {

}
