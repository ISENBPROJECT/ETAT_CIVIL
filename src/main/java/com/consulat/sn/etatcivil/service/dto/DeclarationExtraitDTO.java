package com.consulat.sn.etatcivil.service.dto;


import com.consulat.sn.etatcivil.domain.enumeration.Genre;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

/**
 * A DTO for the Extrait entity.
 */
public class DeclarationExtraitDTO implements Serializable {

    private Long id;

    private String numeroRegistre;

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

    @NotNull
    @Size(max = 1000)
    @Lob
    private String mention;

    @NotNull
    private String nomEnfant;

    @NotNull
    private String prenomEnfant;

    @NotNull
    private LocalDate dateNaissanceEnfant;

    private Genre genreEnfant;

    @NotNull
    private String nomMere;

    @NotNull
    private String prenomMere;

    @NotNull
    private LocalDate dateNaissanceMere;

    private String fonctionMere;

    private String adresseComplMere;

    private String numeroIdentiteMere;

    private String numeroPassportMere;


    private String nomPere;


    private String prenomPere;

    @NotNull
    private String lieuNaissanceEnfant;


    private String lieuResidencePere;

    @NotNull
    private String lieuResidenceMere;


    private String paysResidencePere;

    @NotNull
    private String paysResidenceMere;

    @NotNull
    private String lieuNaissanceMere;


    private String lieuNaissancePere;

    @NotNull
    private String lieuDeclaration;


    @NotNull
    private String paysNaissanceEnfant;

    @NotNull
    private String paysNaissanceMere;


    private String paysNaissancePere;

    @NotNull
    private String paysDeclaration;


    private LocalDate dateNaissancePere;

    private String adresseComplPere;

    private String fonctionPere;

    private String numeroIdentitePere;

    private String numeroPassportPere;

    private VilleDTO lieuNaissanceEnfantId;

    private VilleDTO adresseEnfantId;

    private VilleDTO adressePereId;

    private VilleDTO adresseMereId;

    private VilleDTO lieuNaissancePereId;

    private VilleDTO lieuNaissanceMereId;

    private VilleDTO lieuDeclarationId;

    private PaysDTO paysNaissanceEnfantId;

    private PaysDTO adrPaysEnfantId;

    private PaysDTO paysNaissanceMereId;

    private PaysDTO adrPaysMereId;

    private PaysDTO paysNaissancePereId;

    private PaysDTO adrPaysPereId;

    private PaysDTO paysDeclarationId;

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

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
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

    public Genre getGenreEnfant() {
        return genreEnfant;
    }

    public void setGenreEnfant(Genre genreEnfant) {
        this.genreEnfant = genreEnfant;
    }

    public String getNomMere() {
        return nomMere;
    }

    public void setNomMere(String nomMere) {
        this.nomMere = nomMere;
    }

    public String getPrenomMere() {
        return prenomMere;
    }

    public void setPrenomMere(String prenomMere) {
        this.prenomMere = prenomMere;
    }

    public LocalDate getDateNaissanceMere() {
        return dateNaissanceMere;
    }

    public void setDateNaissanceMere(LocalDate dateNaissanceMere) {
        this.dateNaissanceMere = dateNaissanceMere;
    }

    public String getFonctionMere() {
        return fonctionMere;
    }

    public void setFonctionMere(String fonctionMere) {
        this.fonctionMere = fonctionMere;
    }

    public String getAdresseComplMere() {
        return adresseComplMere;
    }

    public void setAdresseComplMere(String adresseComplMere) {
        this.adresseComplMere = adresseComplMere;
    }

    public String getNumeroIdentiteMere() {
        return numeroIdentiteMere;
    }

    public void setNumeroIdentiteMere(String numeroIdentiteMere) {
        this.numeroIdentiteMere = numeroIdentiteMere;
    }

    public String getNumeroPassportMere() {
        return numeroPassportMere;
    }

    public void setNumeroPassportMere(String numeroPassportMere) {
        this.numeroPassportMere = numeroPassportMere;
    }

    public String getNomPere() {
        return nomPere;
    }

    public void setNomPere(String nomPere) {
        this.nomPere = nomPere;
    }

    public String getPrenomPere() {
        return prenomPere;
    }

    public void setPrenomPere(String prenomPere) {
        this.prenomPere = prenomPere;
    }

    public LocalDate getDateNaissancePere() {
        return dateNaissancePere;
    }

    public void setDateNaissancePere(LocalDate dateNaissancePere) {
        this.dateNaissancePere = dateNaissancePere;
    }

    public String getAdresseComplPere() {
        return adresseComplPere;
    }

    public void setAdresseComplPere(String adresseComplPere) {
        this.adresseComplPere = adresseComplPere;
    }

    public String getFonctionPere() {
        return fonctionPere;
    }

    public void setFonctionPere(String fonctionPere) {
        this.fonctionPere = fonctionPere;
    }

    public String getNumeroIdentitePere() {
        return numeroIdentitePere;
    }

    public void setNumeroIdentitePere(String numeroIdentitePere) {
        this.numeroIdentitePere = numeroIdentitePere;
    }

    public String getNumeroPassportPere() {
        return numeroPassportPere;
    }

    public void setNumeroPassportPere(String numeroPassportPere) {
        this.numeroPassportPere = numeroPassportPere;
    }

    public VilleDTO getLieuNaissanceEnfantId() {
        return lieuNaissanceEnfantId;
    }

    public void setLieuNaissanceEnfantId(VilleDTO lieuNaissanceEnfantId) {
        this.lieuNaissanceEnfantId = lieuNaissanceEnfantId;
    }

    public VilleDTO getAdresseEnfantId() {
        return adresseEnfantId;
    }

    public void setAdresseEnfantId(VilleDTO adresseEnfantId) {
        this.adresseEnfantId = adresseEnfantId;
    }

    public VilleDTO getAdressePereId() {
        return adressePereId;
    }

    public void setAdressePereId(VilleDTO adressePereId) {
        this.adressePereId = adressePereId;
    }

    public VilleDTO getAdresseMereId() {
        return adresseMereId;
    }

    public void setAdresseMereId(VilleDTO adresseMereId) {
        this.adresseMereId = adresseMereId;
    }

    public VilleDTO getLieuNaissancePereId() {
        return lieuNaissancePereId;
    }

    public void setLieuNaissancePereId(VilleDTO lieuNaissancePereId) {
        this.lieuNaissancePereId = lieuNaissancePereId;
    }

    public VilleDTO getLieuNaissanceMereId() {
        return lieuNaissanceMereId;
    }

    public void setLieuNaissanceMereId(VilleDTO lieuNaissanceMereId) {
        this.lieuNaissanceMereId = lieuNaissanceMereId;
    }

    public VilleDTO getLieuDeclarationId() {
        return lieuDeclarationId;
    }

    public void setLieuDeclarationId(VilleDTO lieuDeclarationId) {
        this.lieuDeclarationId = lieuDeclarationId;
    }

    public PaysDTO getPaysNaissanceEnfantId() {
        return paysNaissanceEnfantId;
    }

    public void setPaysNaissanceEnfantId(PaysDTO paysNaissanceEnfantId) {
        this.paysNaissanceEnfantId = paysNaissanceEnfantId;
    }

    public PaysDTO getAdrPaysEnfantId() {
        return adrPaysEnfantId;
    }

    public void setAdrPaysEnfantId(PaysDTO adrPaysEnfantId) {
        this.adrPaysEnfantId = adrPaysEnfantId;
    }

    public PaysDTO getPaysNaissanceMereId() {
        return paysNaissanceMereId;
    }

    public void setPaysNaissanceMereId(PaysDTO paysNaissanceMereId) {
        this.paysNaissanceMereId = paysNaissanceMereId;
    }

    public PaysDTO getAdrPaysMereId() {
        return adrPaysMereId;
    }

    public void setAdrPaysMereId(PaysDTO adrPaysMereId) {
        this.adrPaysMereId = adrPaysMereId;
    }

    public PaysDTO getPaysNaissancePereId() {
        return paysNaissancePereId;
    }

    public void setPaysNaissancePereId(PaysDTO paysNaissancePereId) {
        this.paysNaissancePereId = paysNaissancePereId;
    }

    public PaysDTO getAdrPaysPereId() {
        return adrPaysPereId;
    }

    public void setAdrPaysPereId(PaysDTO adrPaysPereId) {
        this.adrPaysPereId = adrPaysPereId;
    }

    public PaysDTO getPaysDeclarationId() {
        return paysDeclarationId;
    }

    public void setPaysDeclarationId(PaysDTO paysDeclarationId) {
        this.paysDeclarationId = paysDeclarationId;
    }

    public String getNumeroRegistre() {
        return numeroRegistre;
    }

    public void setNumeroRegistre(String numeroRegistre) {
        this.numeroRegistre = numeroRegistre;
    }

    public String getLieuNaissanceEnfant() {
        return lieuNaissanceEnfant;
    }

    public void setLieuNaissanceEnfant(String lieuNaissanceEnfant) {
        this.lieuNaissanceEnfant = lieuNaissanceEnfant;
    }

    public String getLieuNaissanceMere() {
        return lieuNaissanceMere;
    }

    public void setLieuNaissanceMere(String lieuNaissanceMere) {
        this.lieuNaissanceMere = lieuNaissanceMere;
    }

    public String getLieuNaissancePere() {
        return lieuNaissancePere;
    }

    public void setLieuNaissancePere(String lieuNaissancePere) {
        this.lieuNaissancePere = lieuNaissancePere;
    }

    public String getLieuDeclaration() {
        return lieuDeclaration;
    }

    public void setLieuDeclaration(String lieuDeclaration) {
        this.lieuDeclaration = lieuDeclaration;
    }

    public String getPaysNaissanceEnfant() {
        return paysNaissanceEnfant;
    }

    public void setPaysNaissanceEnfant(String paysNaissanceEnfant) {
        this.paysNaissanceEnfant = paysNaissanceEnfant;
    }

    public String getPaysNaissanceMere() {
        return paysNaissanceMere;
    }

    public void setPaysNaissanceMere(String paysNaissanceMere) {
        this.paysNaissanceMere = paysNaissanceMere;
    }

    public String getPaysNaissancePere() {
        return paysNaissancePere;
    }

    public void setPaysNaissancePere(String paysNaissancePere) {
        this.paysNaissancePere = paysNaissancePere;
    }

    public String getLieuResidencePere() {
        return lieuResidencePere;
    }

    public void setLieuResidencePere(String lieuResidencePere) {
        this.lieuResidencePere = lieuResidencePere;
    }

    public String getLieuResidenceMere() {
        return lieuResidenceMere;
    }

    public void setLieuResidenceMere(String lieuResidenceMere) {
        this.lieuResidenceMere = lieuResidenceMere;
    }

    public String getPaysResidencePere() {
        return paysResidencePere;
    }

    public void setPaysResidencePere(String paysResidencePere) {
        this.paysResidencePere = paysResidencePere;
    }

    public String getPaysResidenceMere() {
        return paysResidenceMere;
    }

    public void setPaysResidenceMere(String paysResidenceMere) {
        this.paysResidenceMere = paysResidenceMere;
    }

    public String getPaysDeclaration() {
        return paysDeclaration;
    }

    public void setPaysDeclaration(String paysDeclaration) {
        this.paysDeclaration = paysDeclaration;
    }
}
