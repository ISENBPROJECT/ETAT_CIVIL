package com.consulat.sn.etatcivil.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.consulat.sn.etatcivil.domain.enumeration.Genre;

/**
 * A DTO for the Personne entity.
 */
public class PersonneDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    @NotNull
    private LocalDate dateNaissance;

    private String fonction;

    private Genre genre;

    private String numeroCarteIdentite;

    private String numeroPassport;

    @NotNull
    private String paysNaissance;

    @NotNull
    private String villeNaissance;

    @NotNull
    private String paysResidence;

    @NotNull
    private String villeResidence;

    private String adresseCompl;

    private Long adresseId;

    private Long pereId;

    private Long mereId;

    private Long lieuNaissanceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getNumeroCarteIdentite() {
        return numeroCarteIdentite;
    }

    public void setNumeroCarteIdentite(String numeroCarteIdentite) {
        this.numeroCarteIdentite = numeroCarteIdentite;
    }

    public String getNumeroPassport() {
        return numeroPassport;
    }

    public void setNumeroPassport(String numeroPassport) {
        this.numeroPassport = numeroPassport;
    }

    public String getPaysNaissance() {
        return paysNaissance;
    }

    public void setPaysNaissance(String paysNaissance) {
        this.paysNaissance = paysNaissance;
    }

    public String getVilleNaissance() {
        return villeNaissance;
    }

    public void setVilleNaissance(String villeNaissance) {
        this.villeNaissance = villeNaissance;
    }

    public String getPaysResidence() {
        return paysResidence;
    }

    public void setPaysResidence(String paysResidence) {
        this.paysResidence = paysResidence;
    }

    public String getVilleResidence() {
        return villeResidence;
    }

    public void setVilleResidence(String villeResidence) {
        this.villeResidence = villeResidence;
    }

    public Long getAdresseId() {
        return adresseId;
    }

    public void setAdresseId(Long villeId) {
        this.adresseId = villeId;
    }

    public Long getPereId() {
        return pereId;
    }

    public void setPereId(Long personneId) {
        this.pereId = personneId;
    }

    public Long getMereId() {
        return mereId;
    }

    public void setMereId(Long personneId) {
        this.mereId = personneId;
    }

    public Long getLieuNaissanceId() {
        return lieuNaissanceId;
    }

    public void setLieuNaissanceId(Long villeId) {
        this.lieuNaissanceId = villeId;
    }

    public String getAdresseCompl() {
        return adresseCompl;
    }

    public void setAdresseCompl(String adresseCompl) {
        this.adresseCompl = adresseCompl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonneDTO personneDTO = (PersonneDTO) o;
        if(personneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonneDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", fonction='" + getFonction() + "'" +
            ", genre='" + getGenre() + "'" +
            ", numeroCarteIdentite='" + getNumeroCarteIdentite() + "'" +
            ", numeroPassport='" + getNumeroPassport() + "'" +
            ", paysNaissance='" + getPaysNaissance() + "'" +
            ", villeNaissance='" + getVilleNaissance() + "'" +
            ", paysResidence='" + getPaysResidence() + "'" +
            ", villeResidence='" + getVilleResidence() + "'" +
            "}";
    }
}
