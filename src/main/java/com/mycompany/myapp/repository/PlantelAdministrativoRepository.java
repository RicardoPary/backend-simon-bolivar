package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PlantelAdministrativo;
import com.mycompany.myapp.domain.extras.FamiliarDTO;
import com.mycompany.myapp.domain.extras.PlantelAdministrativoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PlantelAdministrativo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlantelAdministrativoRepository extends JpaRepository<PlantelAdministrativo, Long> {
    @Query("select new com.mycompany.myapp.domain.extras.PlantelAdministrativoDTO(p.id, p.ci, p.nombre, p.paterno, p.materno, p.genero, p.fechaNacimiento, p.nacionalidad, p.direccion, p.telefono, a.idTrabajador, a.cargo) from Persona p, PlantelAdministrativo a where a.idPersona = p.id")
    Page<PlantelAdministrativoDTO> findAllStudents(Pageable pageable);
}
