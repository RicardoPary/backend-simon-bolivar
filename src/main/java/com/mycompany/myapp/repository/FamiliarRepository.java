package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Familiar;
import com.mycompany.myapp.domain.extras.DocenteDTO;
import com.mycompany.myapp.domain.extras.FamiliarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Familiar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FamiliarRepository extends JpaRepository<Familiar, Long> {
    @Query("select new com.mycompany.myapp.domain.extras.FamiliarDTO(p.id, p.ci, p.nombre, p.paterno, p.materno, p.genero, p.fechaNacimiento, p.nacionalidad, p.direccion, p.telefono, f.idEstudiante, f.parentesco) from Persona p, Familiar f where f.idPersona = p.id")
    Page<FamiliarDTO> findAllStudents(Pageable pageable);
}
