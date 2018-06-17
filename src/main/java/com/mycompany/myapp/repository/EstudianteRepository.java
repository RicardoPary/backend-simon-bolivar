package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Estudiante;
import com.mycompany.myapp.domain.extras.EstudianteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Estudiante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {


    /*@Query("select * from Estudiante e")
    Page<EstudianteDTO> findAllStudents(Pageable pageable);
*/

}
