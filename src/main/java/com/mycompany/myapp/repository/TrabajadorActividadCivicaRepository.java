package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TrabajadorActividadCivica;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TrabajadorActividadCivica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrabajadorActividadCivicaRepository extends JpaRepository<TrabajadorActividadCivica, Long> {

}
