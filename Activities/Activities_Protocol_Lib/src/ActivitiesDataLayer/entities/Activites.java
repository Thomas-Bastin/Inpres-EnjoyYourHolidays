/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActivitiesDataLayer.entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Arkios
 */
public class Activites implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idActivite;
    private String typeActivite;
    private int nombreMaxParticipants;
    private int nombreParticipantsInscrits;
    private int dureeActivite;
    private float prixHTVA;
    private Date dateDebut;

    public Activites() {
    }

    public Activites(Integer idActivite) {
        this.idActivite = idActivite;
    }

    public Activites(Integer idActivite, String typeActivite, Integer nombreMaxParticipants, Integer nombreParticipantsInscrits, Integer dureeActivite, Float prixHTVA, Date dateDebut) {
        this.idActivite = idActivite;
        this.typeActivite = typeActivite;
        this.nombreMaxParticipants = nombreMaxParticipants;
        this.nombreParticipantsInscrits = nombreParticipantsInscrits;
        this.dureeActivite = dureeActivite;
        this.prixHTVA = prixHTVA;
        this.dateDebut = dateDebut;
    }

    public Integer getIdActivite() {
        return idActivite;
    }

    public void setIdActivite(Integer idActivite) {
        this.idActivite = idActivite;
    }

    public String getTypeActivite() {
        return typeActivite;
    }

    public void setTypeActivite(String typeActivite) {
        this.typeActivite = typeActivite;
    }

    public int getNombreMaxParticipants() {
        return nombreMaxParticipants;
    }

    public void setNombreMaxParticipants(int nombreMaxParticipants) {
        this.nombreMaxParticipants = nombreMaxParticipants;
    }

    public int getNombreParticipantsInscrits() {
        return nombreParticipantsInscrits;
    }

    public void setNombreParticipantsInscrits(int nombreParticipantsInscrits) {
        this.nombreParticipantsInscrits = nombreParticipantsInscrits;
    }

    public int getDureeActivite() {
        return dureeActivite;
    }

    public void setDureeActivite(int dureeActivite) {
        this.dureeActivite = dureeActivite;
    }

    public float getPrixHTVA() {
        return prixHTVA;
    }

    public void setPrixHTVA(float prixHTVA) {
        this.prixHTVA = prixHTVA;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idActivite != null ? idActivite.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Activites)) {
            return false;
        }
        Activites other = (Activites) object;
        if ((this.idActivite == null && other.idActivite != null) || (this.idActivite != null && !this.idActivite.equals(other.idActivite))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ActivitiesDataLayer.entities.Activites[ idActivite=" + idActivite + " ]";
    }
    
}
