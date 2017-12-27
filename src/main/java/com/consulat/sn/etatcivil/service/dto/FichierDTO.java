package com.consulat.sn.etatcivil.service.dto;

import java.io.Serializable;

public class FichierDTO implements Serializable {
    private String extrait;
    private String transcription;

    public String getExtrait() {
        return extrait;
    }

    public void setExtrait(String extrait) {
        this.extrait = extrait;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }
}
