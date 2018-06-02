package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Docente;
import com.mycompany.myapp.domain.extras.DocenteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;



/**
 * Spring Data JPA repository for the Docente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {

    @Query("select new com.mycompany.myapp.domain.extras.DocenteDTO(p.id, p.ci, p.nombre, p.paterno, p.materno, p.genero, p.fechaNacimiento, p.nacionalidad, p.direccion, p.telefono, d.grado) from Persona p, Docente d where d.idPersona = p.id")
    Page<DocenteDTO> findAllStudents(Pageable pageable);

}
