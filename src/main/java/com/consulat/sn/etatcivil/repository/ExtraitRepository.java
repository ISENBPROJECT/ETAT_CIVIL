package com.consulat.sn.etatcivil.repository;

import com.consulat.sn.etatcivil.domain.Extrait;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the Extrait entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExtraitRepository extends JpaRepository<Extrait, Long> {

    @Query("select extrait from Extrait extrait where extrait.agent.login = ?#{principal.username}")
    List<Extrait> findByAgentIsCurrentUser();

    public final static String FIND_DECLARATION_NAISSANCE_BY_CRITERIA = "SELECT d " +
        "FROM Extrait d JOIN FETCH d.enfant e " +
        "WHERE e.nom like :nom and e.prenom like :prenom " +
        "and (d.numeroRegistre = :numeroRegistre or d.numeroRegistre is not null )" +
        "and (e.dateNaissance = :dateNaissance or e.dateNaissance is not null )";

    @Query(FIND_DECLARATION_NAISSANCE_BY_CRITERIA)
     List<Extrait> findExtraitByCriteria(@Param("numeroRegistre") String numeroRegistre, @Param("nom") String nom,
                                               @Param("prenom") String prenom, @Param("dateNaissance") LocalDate dateNaissance);

    /**
     * permet de rechercher un extrait par son enfant, mere, et pere
     *
     * @param enfant id enfant
     * @param mere   id mere
     * @param pere   id pere
     * @return un Extrait
     */
    Extrait findByEnfantAndMereAndPere(Long enfant, Long mere, Long pere);

    /**
     * recherche l'extrait par son numéro de registre
     *
     * @param numeroRegistre le numéro de registre
     * @return liste des extraits
     */
    List<Extrait> findByNumeroRegistre(String numeroRegistre);
}
