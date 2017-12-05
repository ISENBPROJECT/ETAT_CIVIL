package com.consulat.sn.etatcivil.repository;

import com.consulat.sn.etatcivil.domain.Pays;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Pays entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaysRepository extends JpaRepository<Pays,Long> {

    Pays findByNom(String paysNaissanceEnfant);
}
