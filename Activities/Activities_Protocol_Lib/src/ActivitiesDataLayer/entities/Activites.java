/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActivitiesDataLayer.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Arkios
 */
@Entity
@Table(name = "activites", catalog = "bd_holidays", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Activites.findAll", query = "SELECT a FROM Activites a")
    , @NamedQuery(name = "Activites.findByIdActivite", query = "SELECT a FROM Activites a WHERE a.idActivite = :idActivite")
    , @NamedQuery(name = "Activites.findByTypeActivite", query = "SELECT a FROM Activites a WHERE a.typeActivite = :typeActivite")
    , @NamedQuery(name = "Activites.findByNombreMaxParticipants", query = "SELECT a FROM Activites a WHERE a.nombreMaxParticipants = :nombreMaxParticipants")
    , @NamedQuery(name = "Activites.findByNombreParticipantsInscrits", query = "SELECT a FROM Activites a WHERE a.nombreParticipantsInscrits = :nombreParticipantsInscrits")
    , @NamedQuery(name = "Activites.findByDureeActivite", query = "SELECT a FROM Activites a WHERE a.dureeActivite = :dureeActivite")
    , @NamedQuery(name = "Activites.findByPrixHTVA", query = "SELECT a FROM Activites a WHERE a.prixHTVA = :prixHTVA")
    , @NamedQuery(name = "Activites.findByDateDebut", query = "SELECT a FROM Activites a WHERE a.dateDebut = :dateDebut")})
public class Activites implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idActivite")
    private Integer idActivite;
    @Basic(optional = false)
    @Column(name = "typeActivite")
    private String typeActivite;
    @Basic(optional = false)
    @Column(name = "nombreMaxParticipants")
    private int nombreMaxParticipants;
    @Basic(optional = false)
    @Column(name = "nombreParticipantsInscrits")
    private int nombreParticipantsInscrits;
    @Basic(optional = false)
    @Column(name = "dureeActivite")
    private int dureeActivite;
    @Basic(optional = false)
    @Column(name = "prixHTVA")
    private float prixHTVA;
    @Basic(optional = false)
    @Column(name = "DateDebut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "refActivite")
    private List<Reservationactivite> reservationactiviteList;

    public Activites() {
    }

    public Activites(Integer idActivite) {
        this.idActivite = idActivite;
    }

    public Activites(Integer idActivite, String typeActivite, int nombreMaxParticipants, int nombreParticipantsInscrits, int dureeActivite, float prixHTVA, Date dateDebut) {
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

    @XmlTransient
    public List<Reservationactivite> getReservationactiviteList() {
        return reservationactiviteList;
    }

    public void setReservationactiviteList(List<Reservationactivite> reservationactiviteList) {
        this.reservationactiviteList = reservationactiviteList;
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
