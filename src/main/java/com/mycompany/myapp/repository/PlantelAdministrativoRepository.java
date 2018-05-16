package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PlantelAdministrativo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PlantelAdministrativo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlantelAdministrativoRepository extends JpaRepository<PlantelAdministrativo, Long> {

}
