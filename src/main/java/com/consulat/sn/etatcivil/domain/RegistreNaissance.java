package com.consulat.sn.etatcivil.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A RegistreNaissance.
 */
@Entity
@Table(name = "registre_naissance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RegistreNaissance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 1)
    @Max(value = 9999)
    @Column(name = "numero", nullable = false)
    private Integer numero;

    @NotNull
    @Column(name = "annee_registre", nullable = false)
    private LocalDate anneeRegistre;

    @ManyToOne(optional = false)
    @NotNull
    private Extrait extrait;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public RegistreNaissance numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public LocalDate getAnneeRegistre() {
        return anneeRegistre;
    }

    public RegistreNaissance anneeRegistre(LocalDate anneeRegistre) {
        this.anneeRegistre = anneeRegistre;
        return this;
    }

    public void setAnneeRegistre(LocalDate anneeRegistre) {
        this.anneeRegistre = anneeRegistre;
    }

    public Extrait getExtrait() {
        return extrait;
    }

    public RegistreNaissance extrait(Extrait extrait) {
        this.extrait = extrait;
        return this;
    }

    public void setExtrait(Extrait extrait) {
        this.extrait = extrait;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RegistreNaissance registreNaissance = (RegistreNaissance) o;
        if (registreNaissance.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), registreNaissance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RegistreNaissance{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", anneeRegistre='" + getAnneeRegistre() + "'" +
            "}";
    }
}
