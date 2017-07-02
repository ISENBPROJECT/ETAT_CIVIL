package com.consulat.sn.etatcivil.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the RegistreNaissance entity.
 */
public class RegistreNaissanceDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 1)
    @Max(value = 9999)
    private Integer numero;

    @NotNull
    private LocalDate anneeRegistre;

    private Long extraitId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public LocalDate getAnneeRegistre() {
        return anneeRegistre;
    }

    public void setAnneeRegistre(LocalDate anneeRegistre) {
        this.anneeRegistre = anneeRegistre;
    }

    public Long getExtraitId() {
        return extraitId;
    }

    public void setExtraitId(Long extraitId) {
        this.extraitId = extraitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RegistreNaissanceDTO registreNaissanceDTO = (RegistreNaissanceDTO) o;
        if(registreNaissanceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), registreNaissanceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RegistreNaissanceDTO{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", anneeRegistre='" + getAnneeRegistre() + "'" +
            "}";
    }
}
