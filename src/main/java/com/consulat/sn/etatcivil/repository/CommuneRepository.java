package com.consulat.sn.etatcivil.repository;

import com.consulat.sn.etatcivil.domain.Commune;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Commune entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommuneRepository extends JpaRepository<Commune,Long> {
    
}
