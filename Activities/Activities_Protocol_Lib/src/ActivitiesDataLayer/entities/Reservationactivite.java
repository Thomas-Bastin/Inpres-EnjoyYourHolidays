/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActivitiesDataLayer.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Arkios
 */
@Entity
@Table(name = "reservationactivite", catalog = "bd_holidays", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservationactivite.findAll", query = "SELECT r FROM Reservationactivite r")
    , @NamedQuery(name = "Reservationactivite.findByNumReservation", query = "SELECT r FROM Reservationactivite r WHERE r.numReservation = :numReservation")
    , @NamedQuery(name = "Reservationactivite.findByNombreParticipant", query = "SELECT r FROM Reservationactivite r WHERE r.nombreParticipant = :nombreParticipant")
    , @NamedQuery(name = "Reservationactivite.findByPaye", query = "SELECT r FROM Reservationactivite r WHERE r.paye = :paye")})
public class Reservationactivite implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "numReservation")
    private String numReservation;
    @Basic(optional = false)
    @Column(name = "nombreParticipant")
    private int nombreParticipant;
    @Basic(optional = false)
    @Column(name = "paye")
    private Character paye;
    @JoinColumn(name = "refActivite", referencedColumnName = "idActivite")
    @ManyToOne(optional = false)
    private Activites refActivite;
    @JoinColumn(name = "voyageurTitulaire", referencedColumnName = "numeroClient")
    @ManyToOne
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
