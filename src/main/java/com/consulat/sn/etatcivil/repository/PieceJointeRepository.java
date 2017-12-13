package com.consulat.sn.etatcivil.repository;

import com.consulat.sn.etatcivil.domain.PieceJointe;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PieceJointe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PieceJointeRepository extends JpaRepository<PieceJointe,Long> {

}
