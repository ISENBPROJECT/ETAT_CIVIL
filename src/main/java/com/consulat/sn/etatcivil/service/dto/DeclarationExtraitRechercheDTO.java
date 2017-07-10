package com.consulat.sn.etatcivil.service.dto;

import com.consulat.sn.etatcivil.domain.Personne;
import com.consulat.sn.etatcivil.domain.PieceJointe;
import com.consulat.sn.etatcivil.domain.User;
import com.consulat.sn.etatcivil.domain.Ville;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Extrait.
 */
public class DeclarationExtraitRechercheDTO implements Serializable {


    private Long id;

    private String numeroRegistre;

    private String mention;

    private LocalDate dateDeclaration;

    private Boolean validated;

    private VilleDTO lieuDeclaration;

    private PersonneDTO enfant;

    private PersonneDTO mere;

    private PersonneDTO pere;

    private UserDTO agent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroRegistre() {
        return numeroRegistre;
    }

    public void setNumeroRegistre(String numeroRegistre) {
        this.numeroRegistre = numeroRegistre;
    }

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    public LocalDate getDateDeclaration() {
        return dateDeclaration;
    }

    public void setDateDeclaration(LocalDate dateDeclaration) {
        this.dateDeclaration = dateDeclaration;
    }

    public Boolean getValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    public VilleDTO getLieuDeclaration() {
        return lieuDeclaration;
    }

    public void setLieuDeclaration(VilleDTO lieuDeclaration) {
        this.lieuDeclaration = lieuDeclaration;
    }

    public PersonneDTO getEnfant() {
        return enfant;
    }

    public void setEnfant(PersonneDTO enfant) {
        this.enfant = enfant;
    }

    public PersonneDTO getMere() {
        return mere;
    }

    public void setMere(PersonneDTO mere) {
        this.mere = mere;
    }

    public PersonneDTO getPere() {
        return pere;
    }

    public void setPere(PersonneDTO pere) {
        this.pere = pere;
    }

    public UserDTO getAgent() {
        return agent;
    }

    public void setAgent(UserDTO agent) {
        this.agent = agent;
    }
}
