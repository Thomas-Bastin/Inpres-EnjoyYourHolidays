/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReservationDataLayer.entities;

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
    private Integer nombreParticipantsInscrits;
    private Date dateDebut;
    private int dureeActivite;
    private float prixHTVA;
    private int voyageurReferent;

    public Activites(Integer idActivite, String typeActivite, int nombreMaxParticipants, Integer nombreParticipantsInscrits, Date dateDebut, int dureeActivite, float prixHTVA, int voyageurReferent) {
        this.idActivite = idActivite;
        this.typeActivite = typeActivite;
        this.nombreMaxParticipants = nombreMaxParticipants;
        this.nombreParticipantsInscrits = nombreParticipantsInscrits;
        this.dateDebut = dateDebut;
        this.dureeActivite = dureeActivite;
        this.prixHTVA = prixHTVA;
        this.voyageurReferent = voyageurReferent;
    }

    public int getVoyageurReferent() {
        return voyageurReferent;
    }

    public void setVoyageurReferent(int voyageurReferent) {
        this.voyageurReferent = voyageurReferent;
    }

    public Activites() {
    }

    public Activites(Integer idActivite) {
        this.idActivite = idActivite;
    }

    public Activites(Integer idActivite, String typeActivite, int nombreMaxParticipants, Date dateDebut, int dureeActivite, float prixHTVA) {
        this.idActivite = idActivite;
        this.typeActivite = typeActivite;
        this.nombreMaxParticipants = nombreMaxParticipants;
        this.dateDebut = dateDebut;
        this.dureeActivite = dureeActivite;
        this.prixHTVA = prixHTVA;
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

    public Integer getNombreParticipantsInscrits() {
        return nombreParticipantsInscrits;
    }

    public void setNombreParticipantsInscrits(Integer nombreParticipantsInscrits) {
        this.nombreParticipantsInscrits = nombreParticipantsInscrits;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
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
        return "ReservationDataLayer.entities.Activites[ idActivite=" + idActivite + " ]";
    }
    
}
