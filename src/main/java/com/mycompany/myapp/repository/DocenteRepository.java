package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Docente;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Docente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {

}
