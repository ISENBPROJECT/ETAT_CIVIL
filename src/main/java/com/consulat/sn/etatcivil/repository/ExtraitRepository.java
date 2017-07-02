package com.consulat.sn.etatcivil.repository;

import com.consulat.sn.etatcivil.domain.Extrait;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Extrait entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExtraitRepository extends JpaRepository<Extrait,Long> {

    @Query("select extrait from Extrait extrait where extrait.agent.login = ?#{principal.username}")
    List<Extrait> findByAgentIsCurrentUser();
    
}
