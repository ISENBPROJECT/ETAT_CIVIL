package com.consulat.sn.etatcivil.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the PieceJointe entity.
 */
public class PieceJointeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 8192, max = 25165824)
    @Lob
    private byte[] copieLiterale;
    private String copieLiteraleContentType;

    @NotNull
    @Size(min = 8192, max = 25165824)
    @Lob
    private byte[] copieCarte;
    private String copieCarteContentType;

    private Long declarationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getCopieLiterale() {
        return copieLiterale;
    }

    public void setCopieLiterale(byte[] copieLiterale) {
        this.copieLiterale = copieLiterale;
    }

    public String getCopieLiteraleContentType() {
        return copieLiteraleContentType;
    }

    public void setCopieLiteraleContentType(String copieLiteraleContentType) {
        this.copieLiteraleContentType = copieLiteraleContentType;
    }

    public byte[] getCopieCarte() {
        return copieCarte;
    }

    public void setCopieCarte(byte[] copieCarte) {
        this.copieCarte = copieCarte;
    }

    public String getCopieCarteContentType() {
        return copieCarteContentType;
    }

    public void setCopieCarteContentType(String copieCarteContentType) {
        this.copieCarteContentType = copieCarteContentType;
    }

    public Long getDeclarationId() {
        return declarationId;
    }

    public void setDeclarationId(Long extraitId) {
        this.declarationId = extraitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PieceJointeDTO pieceJointeDTO = (PieceJointeDTO) o;
        if(pieceJointeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pieceJointeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PieceJointeDTO{" +
            "id=" + getId() +
            ", copieLiterale='" + getCopieLiterale() + "'" +
            ", copieCarte='" + getCopieCarte() + "'" +
            "}";
    }
}
