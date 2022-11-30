/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActivitiesDataLayer.entities;

import java.io.Serializable;

/**
 *
 * @author Arkios
 */

public class Reservationactivite implements Serializable {

    private static final long serialVersionUID = 1L;
    private String numReservation;
    private int nombreParticipant;
    private Character paye;
    private Activites refActivite;
    private Voyageurs voyageurTitulaire;

    public Reservationactivite() {
    }

    public Reservationactivite(String numReservation) {
        this.numReservation = numReservation;
    }

    public Reservationactivite(String numReservation, int nombreParticipant, Character paye) {
        this.numReservation = numReservation;
        this.nombreParticipant = nombreParticipant;
        this.paye = paye;
    }

    public Reservationactivite(String numReservation, int nombreParticipant, Character paye, Activites refActivite, Voyageurs voyageurTitulaire) {
        this.numReservation = numReservation;
        this.nombreParticipant = nombreParticipant;
        this.paye = paye;
        this.refActivite = refActivite;
        this.voyageurTitulaire = voyageurTitulaire;
    }
    
    

    public String getNumReservation() {
        return numReservation;
    }

    public void setNumReservation(String numReservation) {
        this.numReservation = numReservation;
    }

    public int getNombreParticipant() {
        return nombreParticipant;
    }

    public void setNombreParticipant(int nombreParticipant) {
        this.nombreParticipant = nombreParticipant;
    }

    public Character getPaye() {
        return paye;
    }

    public void setPaye(Character paye) {
        this.paye = paye;
    }

    public Activites getRefActivite() {
        return refActivite;
    }

    public void setRefActivite(Activites refActivite) {
        this.refActivite = refActivite;
    }

    public Voyageurs getVoyageurTitulaire() {
        return voyageurTitulaire;
    }

    public void setVoyageurTitulaire(Voyageurs voyageurTitulaire) {
        this.voyageurTitulaire = voyageurTitulaire;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numReservation != null ? numReservation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservationactivite)) {
            return false;
        }
        Reservationactivite other = (Reservationactivite) object;
        if ((this.numReservation == null && other.numReservation != null) || (this.numReservation != null && !this.numReservation.equals(other.numReservation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ActivitiesDataLayer.entities.Reservationactivite[ numReservation=" + numReservation + " ]";
    }
    
}
