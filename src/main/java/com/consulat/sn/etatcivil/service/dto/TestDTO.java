package com.consulat.sn.etatcivil.service.dto;


import com.consulat.sn.etatcivil.domain.enumeration.Genre;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the Extrait entity.
 */
public class TestDTO implements Serializable {

    private Long id;

    private String numeroRegistre;

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
}
