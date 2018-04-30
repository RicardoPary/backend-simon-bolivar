package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Tema;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Tema entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TemaRepository extends JpaRepository<Tema, Long> {

}
