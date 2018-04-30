package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Libreta;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Libreta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LibretaRepository extends JpaRepository<Libreta, Long> {

}
