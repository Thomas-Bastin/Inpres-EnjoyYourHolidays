/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActivitiesDataLayer.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Arkios
 */

public class Activities implements Serializable {

    private Integer idActivite;
    private String typeActivite;
    private int nombreMaxParticipants;
    private int nombreParticipantsInscrits;
    private Date dateDebut;
    private int dureeActivite;
    private float prixHTVA;
    private Integer voyageurReferent;

    public Activities() {
    }

    public Activities(Integer idActivite) {
        this.idActivite = idActivite;
    }

    public Activities(Integer idActivite, String typeActivite, int nombreMaxParticipants, int nombreParticipantsInscrits, Date dateDebut, int dureeActivite, float prixHTVA) {
        this.idActivite = idActivite;
        this.typeActivite = typeActivite;
        this.nombreMaxParticipants = nombreMaxParticipants;
        this.nombreParticipantsInscrits = nombreParticipantsInscrits;
        this.dateDebut = dateDebut;
        this.dureeActivite = dureeActivite;
        this.prixHTVA = prixHTVA;
    }

    public Activities(Integer idActivite, String typeActivite, int nombreMaxParticipants, int nombreParticipantsInscrits, Date dateDebut, int dureeActivite, float prixHTVA, Integer voyageurReferent) {
        this.idActivite = idActivite;
        this.typeActivite = typeActivite;
        this.nombreMaxParticipants = nombreMaxParticipants;
        this.nombreParticipantsInscrits = nombreParticipantsInscrits;
        this.dateDebut = dateDebut;
        this.dureeActivite = dureeActivite;
        this.prixHTVA = prixHTVA;
        this.voyageurReferent = voyageurReferent;
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

    public Integer getVoyageurReferent() {
        return voyageurReferent;
    }

    public void setVoyageurReferent(Integer voyageurReferent) {
        this.voyageurReferent = voyageurReferent;
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
        if (!(object instanceof Activities)) {
            return false;
        }
        Activities other = (Activities) object;
        if ((this.idActivite == null && other.idActivite != null) || (this.idActivite != null && !this.idActivite.equals(other.idActivite))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Activites{" + "idActivite=" + idActivite + ", typeActivite=" + typeActivite + ", nombreMaxParticipants=" + nombreMaxParticipants + ", nombreParticipantsInscrits=" + nombreParticipantsInscrits + ", dateDebut=" + dateDebut + ", dureeActivite=" + dureeActivite + ", prixHTVA=" + prixHTVA + ", voyageurReferent=" + voyageurReferent + '}';
    }
    
    
    public Vector<String> toVector(){
        Vector<String> vec = new Vector<String>();
        
        vec.add(typeActivite.toString());
        vec.add("" + nombreMaxParticipants);
        vec.add("" + nombreParticipantsInscrits);
        vec.add(dateDebut.toString());
        vec.add("" + dureeActivite);
        vec.add("" + prixHTVA);
        
        return vec;
    }
}
