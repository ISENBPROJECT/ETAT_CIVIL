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

    private Long lieuNaissanceEnfantId;

    private Long adresseEnfantId;

    private Long adressePereId;

    private Long adresseMereId;

    private Long lieuNaissancePereId;

    private Long lieuNaissanceMereId;

    private Long lieuDeclarationId;

    private String paysNaissanceEnfantId;

    private String adrPaysEnfantId;

    private String paysNaissanceMereId;

    private String adrPaysMereId;

    private String paysNaissancePereId;

    private String adrPaysPereId;

    private String paysDeclarationId;

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

    public Long getLieuNaissanceEnfantId() {
        return lieuNaissanceEnfantId;
    }

    public void setLieuNaissanceEnfantId(Long villeId) {
        this.lieuNaissanceEnfantId = villeId;
    }

    public Long getAdresseEnfantId() {
        return adresseEnfantId;
    }

    public void setAdresseEnfantId(Long villeId) {
        this.adresseEnfantId = villeId;
    }

    public Long getAdressePereId() {
        return adressePereId;
    }

    public void setAdressePereId(Long villeId) {
        this.adressePereId = villeId;
    }

    public Long getAdresseMereId() {
        return adresseMereId;
    }

    public void setAdresseMereId(Long villeId) {
        this.adresseMereId = villeId;
    }

    public Long getLieuNaissancePereId() {
        return lieuNaissancePereId;
    }

    public void setLieuNaissancePereId(Long villeId) {
        this.lieuNaissancePereId = villeId;
    }

    public Long getLieuNaissanceMereId() {
        return lieuNaissanceMereId;
    }

    public void setLieuNaissanceMereId(Long villeId) {
        this.lieuNaissanceMereId = villeId;
    }

    public Long getLieuDeclarationId() {
        return lieuDeclarationId;
    }

    public void setLieuDeclarationId(Long villeId) {
        this.lieuDeclarationId = villeId;
    }

    public String getPaysNaissanceEnfantId() {
        return paysNaissanceEnfantId;
    }

    public void setPaysNaissanceEnfantId(String paysNaissanceEnfantId) {
        this.paysNaissanceEnfantId = paysNaissanceEnfantId;
    }

    public String getAdrPaysEnfantId() {
        return adrPaysEnfantId;
    }

    public void setAdrPaysEnfantId(String adrPaysEnfantId) {
        this.adrPaysEnfantId = adrPaysEnfantId;
    }

    public String getAdrPaysMereId() {
        return adrPaysMereId;
    }

    public void setAdrPaysMereId(String adrPaysMereId) {
        this.adrPaysMereId = adrPaysMereId;
    }

    public String getPaysNaissanceMereId() {
        return paysNaissanceMereId;
    }

    public void setPaysNaissanceMereId(String paysNaissanceMereId) {
        this.paysNaissanceMereId = paysNaissanceMereId;
    }

    public String getPaysNaissancePereId() {
        return paysNaissancePereId;
    }

    public void setPaysNaissancePereId(String paysNaissancePereId) {
        this.paysNaissancePereId = paysNaissancePereId;
    }

    public String getAdrPaysPereId() {
        return adrPaysPereId;
    }

    public void setAdrPaysPereId(String adrPaysPereId) {
        this.adrPaysPereId = adrPaysPereId;
    }

    public String getPaysDeclarationId() {
        return paysDeclarationId;
    }

    public void setPaysDeclarationId(String paysDeclarationId) {
        this.paysDeclarationId = paysDeclarationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeclarationExtraitDTO declarationExtraitDTO = (DeclarationExtraitDTO) o;
        if(declarationExtraitDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), declarationExtraitDTO.getId());
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
