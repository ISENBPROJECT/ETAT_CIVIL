package com.consulat.sn.etatcivil.repository;

import com.consulat.sn.etatcivil.domain.RegistreNaissance;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RegistreNaissance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegistreNaissanceRepository extends JpaRepository<RegistreNaissance,Long> {

    RegistreNaissance findFirstByOrderByIdDesc();
}
