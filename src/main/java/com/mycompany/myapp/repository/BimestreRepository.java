package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Bimestre;
import com.mycompany.myapp.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Bimestre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BimestreRepository extends JpaRepository<Bimestre, Long> {
    Page<Bimestre> findAllByIdMateriaAndIdDocente(Pageable pageable, Long idMateria, Long idDocente);
}
