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

    @NotNull
    private String nomPere;

    @NotNull
    private String prenomPere;

    @NotNull
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeclarationExtraitDTO that = (DeclarationExtraitDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (!Arrays.equals(copieLiterale, that.copieLiterale)) return false;
        if (copieLiteraleContentType != null ? !copieLiteraleContentType.equals(that.copieLiteraleContentType) : that.copieLiteraleContentType != null)
            return false;
        if (!Arrays.equals(copieCarte, that.copieCarte)) return false;
        if (copieCarteContentType != null ? !copieCarteContentType.equals(that.copieCarteContentType) : that.copieCarteContentType != null)
            return false;
        if (mention != null ? !mention.equals(that.mention) : that.mention != null) return false;
        if (nomEnfant != null ? !nomEnfant.equals(that.nomEnfant) : that.nomEnfant != null) return false;
        if (prenomEnfant != null ? !prenomEnfant.equals(that.prenomEnfant) : that.prenomEnfant != null) return false;
        if (dateNaissanceEnfant != null ? !dateNaissanceEnfant.equals(that.dateNaissanceEnfant) : that.dateNaissanceEnfant != null)
            return false;
        if (genreEnfant != that.genreEnfant) return false;
        if (nomMere != null ? !nomMere.equals(that.nomMere) : that.nomMere != null) return false;
        if (prenomMere != null ? !prenomMere.equals(that.prenomMere) : that.prenomMere != null) return false;
        if (dateNaissanceMere != null ? !dateNaissanceMere.equals(that.dateNaissanceMere) : that.dateNaissanceMere != null)
            return false;
        if (fonctionMere != null ? !fonctionMere.equals(that.fonctionMere) : that.fonctionMere != null) return false;
        if (adresseComplMere != null ? !adresseComplMere.equals(that.adresseComplMere) : that.adresseComplMere != null)
            return false;
        if (numeroIdentiteMere != null ? !numeroIdentiteMere.equals(that.numeroIdentiteMere) : that.numeroIdentiteMere != null)
            return false;
        if (numeroPassportMere != null ? !numeroPassportMere.equals(that.numeroPassportMere) : that.numeroPassportMere != null)
            return false;
        if (nomPere != null ? !nomPere.equals(that.nomPere) : that.nomPere != null) return false;
        if (prenomPere != null ? !prenomPere.equals(that.prenomPere) : that.prenomPere != null) return false;
        if (dateNaissancePere != null ? !dateNaissancePere.equals(that.dateNaissancePere) : that.dateNaissancePere != null)
            return false;
        if (adresseComplPere != null ? !adresseComplPere.equals(that.adresseComplPere) : that.adresseComplPere != null)
            return false;
        if (fonctionPere != null ? !fonctionPere.equals(that.fonctionPere) : that.fonctionPere != null) return false;
        if (numeroIdentitePere != null ? !numeroIdentitePere.equals(that.numeroIdentitePere) : that.numeroIdentitePere != null)
            return false;
        if (numeroPassportPere != null ? !numeroPassportPere.equals(that.numeroPassportPere) : that.numeroPassportPere != null)
            return false;
        if (lieuNaissanceEnfantId != null ? !lieuNaissanceEnfantId.equals(that.lieuNaissanceEnfantId) : that.lieuNaissanceEnfantId != null)
            return false;
        if (adresseEnfantId != null ? !adresseEnfantId.equals(that.adresseEnfantId) : that.adresseEnfantId != null)
            return false;
        if (adressePereId != null ? !adressePereId.equals(that.adressePereId) : that.adressePereId != null)
            return false;
        if (adresseMereId != null ? !adresseMereId.equals(that.adresseMereId) : that.adresseMereId != null)
            return false;
        if (lieuNaissancePereId != null ? !lieuNaissancePereId.equals(that.lieuNaissancePereId) : that.lieuNaissancePereId != null)
            return false;
        if (lieuNaissanceMereId != null ? !lieuNaissanceMereId.equals(that.lieuNaissanceMereId) : that.lieuNaissanceMereId != null)
            return false;
        if (lieuDeclarationId != null ? !lieuDeclarationId.equals(that.lieuDeclarationId) : that.lieuDeclarationId != null)
            return false;
        if (paysNaissanceEnfantId != null ? !paysNaissanceEnfantId.equals(that.paysNaissanceEnfantId) : that.paysNaissanceEnfantId != null)
            return false;
        if (adrPaysEnfantId != null ? !adrPaysEnfantId.equals(that.adrPaysEnfantId) : that.adrPaysEnfantId != null)
            return false;
        if (paysNaissanceMereId != null ? !paysNaissanceMereId.equals(that.paysNaissanceMereId) : that.paysNaissanceMereId != null)
            return false;
        if (adrPaysMereId != null ? !adrPaysMereId.equals(that.adrPaysMereId) : that.adrPaysMereId != null)
            return false;
        if (paysNaissancePereId != null ? !paysNaissancePereId.equals(that.paysNaissancePereId) : that.paysNaissancePereId != null)
            return false;
        if (adrPaysPereId != null ? !adrPaysPereId.equals(that.adrPaysPereId) : that.adrPaysPereId != null)
            return false;
        return paysDeclarationId != null ? paysDeclarationId.equals(that.paysDeclarationId) : that.paysDeclarationId == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(copieLiterale);
        result = 31 * result + (copieLiteraleContentType != null ? copieLiteraleContentType.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(copieCarte);
        result = 31 * result + (copieCarteContentType != null ? copieCarteContentType.hashCode() : 0);
        result = 31 * result + (mention != null ? mention.hashCode() : 0);
        result = 31 * result + (nomEnfant != null ? nomEnfant.hashCode() : 0);
        result = 31 * result + (prenomEnfant != null ? prenomEnfant.hashCode() : 0);
        result = 31 * result + (dateNaissanceEnfant != null ? dateNaissanceEnfant.hashCode() : 0);
        result = 31 * result + (genreEnfant != null ? genreEnfant.hashCode() : 0);
        result = 31 * result + (nomMere != null ? nomMere.hashCode() : 0);
        result = 31 * result + (prenomMere != null ? prenomMere.hashCode() : 0);
        result = 31 * result + (dateNaissanceMere != null ? dateNaissanceMere.hashCode() : 0);
        result = 31 * result + (fonctionMere != null ? fonctionMere.hashCode() : 0);
        result = 31 * result + (adresseComplMere != null ? adresseComplMere.hashCode() : 0);
        result = 31 * result + (numeroIdentiteMere != null ? numeroIdentiteMere.hashCode() : 0);
        result = 31 * result + (numeroPassportMere != null ? numeroPassportMere.hashCode() : 0);
        result = 31 * result + (nomPere != null ? nomPere.hashCode() : 0);
        result = 31 * result + (prenomPere != null ? prenomPere.hashCode() : 0);
        result = 31 * result + (dateNaissancePere != null ? dateNaissancePere.hashCode() : 0);
        result = 31 * result + (adresseComplPere != null ? adresseComplPere.hashCode() : 0);
        result = 31 * result + (fonctionPere != null ? fonctionPere.hashCode() : 0);
        result = 31 * result + (numeroIdentitePere != null ? numeroIdentitePere.hashCode() : 0);
        result = 31 * result + (numeroPassportPere != null ? numeroPassportPere.hashCode() : 0);
        result = 31 * result + (lieuNaissanceEnfantId != null ? lieuNaissanceEnfantId.hashCode() : 0);
        result = 31 * result + (adresseEnfantId != null ? adresseEnfantId.hashCode() : 0);
        result = 31 * result + (adressePereId != null ? adressePereId.hashCode() : 0);
        result = 31 * result + (adresseMereId != null ? adresseMereId.hashCode() : 0);
        result = 31 * result + (lieuNaissancePereId != null ? lieuNaissancePereId.hashCode() : 0);
        result = 31 * result + (lieuNaissanceMereId != null ? lieuNaissanceMereId.hashCode() : 0);
        result = 31 * result + (lieuDeclarationId != null ? lieuDeclarationId.hashCode() : 0);
        result = 31 * result + (paysNaissanceEnfantId != null ? paysNaissanceEnfantId.hashCode() : 0);
        result = 31 * result + (adrPaysEnfantId != null ? adrPaysEnfantId.hashCode() : 0);
        result = 31 * result + (paysNaissanceMereId != null ? paysNaissanceMereId.hashCode() : 0);
        result = 31 * result + (adrPaysMereId != null ? adrPaysMereId.hashCode() : 0);
        result = 31 * result + (paysNaissancePereId != null ? paysNaissancePereId.hashCode() : 0);
        result = 31 * result + (adrPaysPereId != null ? adrPaysPereId.hashCode() : 0);
        result = 31 * result + (paysDeclarationId != null ? paysDeclarationId.hashCode() : 0);
        return result;
    }
}
