package com.consulat.sn.etatcivil.service.dto;



import com.consulat.sn.etatcivil.domain.enumeration.Genre;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * A DTO for the Extrait entity.
 */
public class DeclarationExtraitRechercheDTO implements Serializable {

private String numeroRegistre;
    private String nomEnfant;
    private String prenomEnfant;
    private LocalDate dateNaissanceEnfant;

    public String getNumeroRegistre() {
        return numeroRegistre;
    }

    public void setNumeroRegistre(String numeroRegistre) {
        this.numeroRegistre = numeroRegistre;
    }

    public String getNomEnfant() {
        return nomEnfant;
    }

    public void setNomEnfant(String nomEnfant) {
        this.nomEnfant = nomEnfant;
    }

    public String getPrenomEnfant() {
        return prenomEnfant;
    }

    public void setPrenomEnfant(String prenomEnfant) {
        this.prenomEnfant = prenomEnfant;
    }

    public LocalDate getDateNaissanceEnfant() {
        return dateNaissanceEnfant;
    }

    public void setDateNaissanceEnfant(LocalDate dateNaissanceEnfant) {
        this.dateNaissanceEnfant = dateNaissanceEnfant;
    }
}
