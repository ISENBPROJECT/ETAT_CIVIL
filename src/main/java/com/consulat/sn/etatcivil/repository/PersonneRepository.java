package com.consulat.sn.etatcivil.repository;

import com.consulat.sn.etatcivil.domain.Personne;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Personne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonneRepository extends JpaRepository<Personne,Long> {
    
}
