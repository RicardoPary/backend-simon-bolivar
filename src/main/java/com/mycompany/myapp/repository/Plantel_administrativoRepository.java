package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Plantel_administrativo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Plantel_administrativo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Plantel_administrativoRepository extends JpaRepository<Plantel_administrativo, Long> {

}
