package com.consulat.sn.etatcivil.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Extrait.
 */
@Entity
@Table(name = "extrait")
@NamedQueries({
    @NamedQuery(name = "Extrait.isDeclarationExist",
        query = "SELECT extrait FROM Extrait extrait where extrait.enfant.id = :idEnfant and " +
            "extrait.mere.id =  :idMere and extrait.pere.id = :idPere"),
    @NamedQuery(name = "Extrait.findByNomAndPrenomAndDateNaissance",
        query = "SELECT extrait FROM Extrait extrait where extrait.enfant.nom = :nom and " +
            "extrait.enfant.prenom =  :prenom and extrait.enfant.dateNaissance = :dateNaissance"),
    @NamedQuery(name = "Extrait.findByPrenomAndDateNaissance",
        query = "SELECT extrait FROM Extrait extrait where extrait.enfant.prenom =  :prenom and extrait.enfant.dateNaissance = :dateNaissance"),
    @NamedQuery(name = "Extrait.findByDateNaissance",
        query = "SELECT extrait FROM Extrait extrait where extrait.enfant.dateNaissance = :dateNaissance"),
    @NamedQuery(name = "Extrait.findByNomAndPrenom",
        query = "SELECT extrait FROM Extrait extrait where extrait.enfant.nom = :nom and extrait.enfant.prenom = :prenom"),
    @NamedQuery(name = "Extrait.findByNomAndDateNaissance",
        query = "SELECT extrait FROM Extrait extrait where extrait.enfant.nom = :nom and extrait.enfant.dateNaissance = :dateNaissance"),
    @NamedQuery(name = "Extrait.findByPrenom",
        query = "SELECT extrait FROM Extrait extrait where extrait.enfant.prenom = :prenom"),
    @NamedQuery(name = "Extrait.findByNom",
        query = "SELECT extrait FROM Extrait extrait where extrait.enfant.nom = :nom"),

})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Extrait implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "numero_registre", nullable = false)
    private String numeroRegistre;

    @NotNull
    @Size(max = 1000)
    @Lob
    @Column(name = "mention", nullable = false)
    private String mention;

    @NotNull
    @Column(name = "date_declaration", nullable = false)
    private LocalDate dateDeclaration;

    @Column(name = "validated")
    private Boolean validated;

    @ManyToOne(optional = false)
    @NotNull
    private Ville lieuDeclaration;

    @ManyToOne(optional = false)
    @NotNull
    private Personne enfant;

    @ManyToOne(optional = false)
    @NotNull
    private Personne mere;

    @ManyToOne(optional = false)
    @NotNull
    private User agent;

    @OneToMany(mappedBy = "declaration")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PieceJointe> piecesJointes = new HashSet<>();

    @ManyToOne
    private Personne pere;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroRegistre() {
        return numeroRegistre;
    }

    public Extrait numeroRegistre(String numeroRegistre) {
        this.numeroRegistre = numeroRegistre;
        return this;
    }

    public void setNumeroRegistre(String numeroRegistre) {
        this.numeroRegistre = numeroRegistre;
    }

    public String getMention() {
        return mention;
    }

    public Extrait mention(String mention) {
        this.mention = mention;
        return this;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    public LocalDate getDateDeclaration() {
        return dateDeclaration;
    }

    public Extrait dateDeclaration(LocalDate dateDeclaration) {
        this.dateDeclaration = dateDeclaration;
        return this;
    }

    public void setDateDeclaration(LocalDate dateDeclaration) {
        this.dateDeclaration = dateDeclaration;
    }

    public Boolean isValidated() {
        return validated;
    }

    public Extrait validated(Boolean validated) {
        this.validated = validated;
        return this;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    public Ville getLieuDeclaration() {
        return lieuDeclaration;
    }

    public Extrait lieuDeclaration(Ville ville) {
        this.lieuDeclaration = ville;
        return this;
    }

    public void setLieuDeclaration(Ville ville) {
        this.lieuDeclaration = ville;
    }

    public Personne getEnfant() {
        return enfant;
    }

    public Extrait enfant(Personne personne) {
        this.enfant = personne;
        return this;
    }

    public void setEnfant(Personne personne) {
        this.enfant = personne;
    }

    public Personne getMere() {
        return mere;
    }

    public Extrait mere(Personne personne) {
        this.mere = personne;
        return this;
    }

    public void setMere(Personne personne) {
        this.mere = personne;
    }

    public User getAgent() {
        return agent;
    }

    public Extrait agent(User user) {
        this.agent = user;
        return this;
    }

    public void setAgent(User user) {
        this.agent = user;
    }

    public Set<PieceJointe> getPiecesJointes() {
        return piecesJointes;
    }

    public Extrait piecesJointes(Set<PieceJointe> pieceJointes) {
        this.piecesJointes = pieceJointes;
        return this;
    }

    public Extrait addPiecesJointes(PieceJointe pieceJointe) {
        this.piecesJointes.add(pieceJointe);
        pieceJointe.setDeclaration(this);
        return this;
    }

    public Extrait removePiecesJointes(PieceJointe pieceJointe) {
        this.piecesJointes.remove(pieceJointe);
        pieceJointe.setDeclaration(null);
        return this;
    }

    public void setPiecesJointes(Set<PieceJointe> pieceJointes) {
        this.piecesJointes = pieceJointes;
    }

    public Personne getPere() {
        return pere;
    }

    public Extrait pere(Personne personne) {
        this.pere = personne;
        return this;
    }

    public void setPere(Personne personne) {
        this.pere = personne;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Extrait extrait = (Extrait) o;
        if (extrait.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), extrait.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Extrait{" +
            "id=" + getId() +
            ", numeroRegistre='" + getNumeroRegistre() + "'" +
            ", mention='" + getMention() + "'" +
            ", dateDeclaration='" + getDateDeclaration() + "'" +
            ", validated='" + isValidated() + "'" +
            "}";
    }
}
