package com.consulat.sn.etatcivil.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PieceJointe.
 */
@Entity
@Table(name = "piece_jointe")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PieceJointe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 8192, max = 25165824)
    @Lob
    @Column(name = "copie_literale", nullable = false)
    private byte[] copieLiterale;

    @Column(name = "copie_literale_content_type", nullable = false)
    private String copieLiteraleContentType;

    @NotNull
    @Size(min = 8192, max = 25165824)
    @Lob
    @Column(name = "copie_carte", nullable = false)
    private byte[] copieCarte;

    @Column(name = "copie_carte_content_type", nullable = false)
    private String copieCarteContentType;

    @ManyToOne(optional = false)
    @NotNull
    private Extrait declaration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getCopieLiterale() {
        return copieLiterale;
    }

    public PieceJointe copieLiterale(byte[] copieLiterale) {
        this.copieLiterale = copieLiterale;
        return this;
    }

    public void setCopieLiterale(byte[] copieLiterale) {
        this.copieLiterale = copieLiterale;
    }

    public String getCopieLiteraleContentType() {
        return copieLiteraleContentType;
    }

    public PieceJointe copieLiteraleContentType(String copieLiteraleContentType) {
        this.copieLiteraleContentType = copieLiteraleContentType;
        return this;
    }

    public void setCopieLiteraleContentType(String copieLiteraleContentType) {
        this.copieLiteraleContentType = copieLiteraleContentType;
    }

    public byte[] getCopieCarte() {
        return copieCarte;
    }

    public PieceJointe copieCarte(byte[] copieCarte) {
        this.copieCarte = copieCarte;
        return this;
    }

    public void setCopieCarte(byte[] copieCarte) {
        this.copieCarte = copieCarte;
    }

    public String getCopieCarteContentType() {
        return copieCarteContentType;
    }

    public PieceJointe copieCarteContentType(String copieCarteContentType) {
        this.copieCarteContentType = copieCarteContentType;
        return this;
    }

    public void setCopieCarteContentType(String copieCarteContentType) {
        this.copieCarteContentType = copieCarteContentType;
    }

    public Extrait getDeclaration() {
        return declaration;
    }

    public PieceJointe declaration(Extrait extrait) {
        this.declaration = extrait;
        return this;
    }

    public void setDeclaration(Extrait extrait) {
        this.declaration = extrait;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PieceJointe pieceJointe = (PieceJointe) o;
        if (pieceJointe.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pieceJointe.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PieceJointe{" +
            "id=" + getId() +
            ", copieLiterale='" + getCopieLiterale() + "'" +
            ", copieLiteraleContentType='" + copieLiteraleContentType + "'" +
            ", copieCarte='" + getCopieCarte() + "'" +
            ", copieCarteContentType='" + copieCarteContentType + "'" +
            "}";
    }
}
