package com.consulat.sn.etatcivil.repository;

import com.consulat.sn.etatcivil.domain.Personne;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


/**
 * Spring Data JPA repository for the Personne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonneRepository extends JpaRepository<Personne,Long> {

    Personne findByNomAndPrenom(String nom, String prenom);

    List<Personne> findByNomAndPrenomAndDateNaissance(String nom, String prenom, LocalDate dateNaissance);

    List<Personne> findByNomAndPrenomAndDateNaissanceAndNumeroCarteIdentite(String nom, String prenom, LocalDate dateNaissance, String numeroIdentite);
}
