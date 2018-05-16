package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Inscrito;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Inscrito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InscritoRepository extends JpaRepository<Inscrito, Long> {

}
