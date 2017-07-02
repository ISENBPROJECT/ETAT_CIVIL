package com.consulat.sn.etatcivil.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.consulat.sn.etatcivil.domain.enumeration.Genre;

/**
 * A Personne.
 */
@Entity
@Table(name = "personne")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Personne implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Column(name = "fonction")
    private String fonction;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private Genre genre;

    @Column(name = "numero_carte_identite")
    private String numeroCarteIdentite;

    @Column(name = "numero_passport")
    private String numeroPassport;

    @ManyToOne(optional = false)
    @NotNull
    private Ville adresse;

    @ManyToOne
    private Personne pere;

    @ManyToOne
    private Personne mere;

    @ManyToOne(optional = false)
    @NotNull
    private Ville lieuNaissance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Personne nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Personne prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public Personne dateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getFonction() {
        return fonction;
    }

    public Personne fonction(String fonction) {
        this.fonction = fonction;
        return this;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public Genre getGenre() {
        return genre;
    }

    public Personne genre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getNumeroCarteIdentite() {
        return numeroCarteIdentite;
    }

    public Personne numeroCarteIdentite(String numeroCarteIdentite) {
        this.numeroCarteIdentite = numeroCarteIdentite;
        return this;
    }

    public void setNumeroCarteIdentite(String numeroCarteIdentite) {
        this.numeroCarteIdentite = numeroCarteIdentite;
    }

    public String getNumeroPassport() {
        return numeroPassport;
    }

    public Personne numeroPassport(String numeroPassport) {
        this.numeroPassport = numeroPassport;
        return this;
    }

    public void setNumeroPassport(String numeroPassport) {
        this.numeroPassport = numeroPassport;
    }

    public Ville getAdresse() {
        return adresse;
    }

    public Personne adresse(Ville ville) {
        this.adresse = ville;
        return this;
    }

    public void setAdresse(Ville ville) {
        this.adresse = ville;
    }

    public Personne getPere() {
        return pere;
    }

    public Personne pere(Personne personne) {
        this.pere = personne;
        return this;
    }

    public void setPere(Personne personne) {
        this.pere = personne;
    }

    public Personne getMere() {
        return mere;
    }

    public Personne mere(Personne personne) {
        this.mere = personne;
        return this;
    }

    public void setMere(Personne personne) {
        this.mere = personne;
    }

    public Ville getLieuNaissance() {
        return lieuNaissance;
    }

    public Personne lieuNaissance(Ville ville) {
        this.lieuNaissance = ville;
        return this;
    }

    public void setLieuNaissance(Ville ville) {
        this.lieuNaissance = ville;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Personne personne = (Personne) o;
        if (personne.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personne.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Personne{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", fonction='" + getFonction() + "'" +
            ", genre='" + getGenre() + "'" +
            ", numeroCarteIdentite='" + getNumeroCarteIdentite() + "'" +
            ", numeroPassport='" + getNumeroPassport() + "'" +
            "}";
    }
}
