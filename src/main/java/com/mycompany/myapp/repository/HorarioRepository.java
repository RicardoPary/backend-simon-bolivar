package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Horario;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Horario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

}
