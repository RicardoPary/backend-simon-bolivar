package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Actividad_civica;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Actividad_civica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Actividad_civicaRepository extends JpaRepository<Actividad_civica, Long> {

}
