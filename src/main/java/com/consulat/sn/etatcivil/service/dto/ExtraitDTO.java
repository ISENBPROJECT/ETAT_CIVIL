package com.consulat.sn.etatcivil.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Extrait entity.
 */
public class ExtraitDTO implements Serializable {

    private Long id;

    @NotNull
    private String numeroRegistre;

    @NotNull
    @Size(max = 1000)
    @Lob
    private String mention;

    @NotNull
    private LocalDate dateDeclaration;

    private Boolean validated;

    private Long lieuDeclarationId;

    private Long enfantId;

    private Long mereId;

    private Long pereId;

    private Long agentId;

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

    public Boolean isValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    public Long getLieuDeclarationId() {
        return lieuDeclarationId;
    }

    public void setLieuDeclarationId(Long villeId) {
        this.lieuDeclarationId = villeId;
    }

    public Long getEnfantId() {
        return enfantId;
    }

    public void setEnfantId(Long personneId) {
        this.enfantId = personneId;
    }

    public Long getMereId() {
        return mereId;
    }

    public void setMereId(Long personneId) {
        this.mereId = personneId;
    }

    public Long getPereId() {
        return pereId;
    }

    public void setPereId(Long personneId) {
        this.pereId = personneId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long userId) {
        this.agentId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExtraitDTO extraitDTO = (ExtraitDTO) o;
        if(extraitDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), extraitDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExtraitDTO{" +
            "id=" + getId() +
            ", numeroRegistre='" + getNumeroRegistre() + "'" +
            ", mention='" + getMention() + "'" +
            ", dateDeclaration='" + getDateDeclaration() + "'" +
            ", validated='" + isValidated() + "'" +
            "}";
    }
}
