/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

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

    /**
     *
     * @param idActivite
     * @param typeActivite
     * @param nombreMaxParticipants
     * @param nombreParticipantsInscrits
     * @param dateDebut
     * @param dureeActivite
     * @param prixHTVA
     * @param voyageurReferent
     */
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

    /**
     *
     * @return
     */
    public int getVoyageurReferent() {
        return voyageurReferent;
    }

    /**
     *
     * @param voyageurReferent
     */
    public void setVoyageurReferent(int voyageurReferent) {
        this.voyageurReferent = voyageurReferent;
    }

    /**
     *
     */
    public Activites() {
    }

    /**
     *
     * @param idActivite
     */
    public Activites(Integer idActivite) {
        this.idActivite = idActivite;
    }

    /**
     *
     * @param idActivite
     * @param typeActivite
     * @param nombreMaxParticipants
     * @param dateDebut
     * @param dureeActivite
     * @param prixHTVA
     */
    public Activites(Integer idActivite, String typeActivite, int nombreMaxParticipants, Date dateDebut, int dureeActivite, float prixHTVA) {
        this.idActivite = idActivite;
        this.typeActivite = typeActivite;
        this.nombreMaxParticipants = nombreMaxParticipants;
        this.dateDebut = dateDebut;
        this.dureeActivite = dureeActivite;
        this.prixHTVA = prixHTVA;
    }

    /**
     *
     * @return
     */
    public Integer getIdActivite() {
        return idActivite;
    }

    /**
     *
     * @param idActivite
     */
    public void setIdActivite(Integer idActivite) {
        this.idActivite = idActivite;
    }

    /**
     *
     * @return
     */
    public String getTypeActivite() {
        return typeActivite;
    }

    /**
     *
     * @param typeActivite
     */
    public void setTypeActivite(String typeActivite) {
        this.typeActivite = typeActivite;
    }

    /**
     *
     * @return
     */
    public int getNombreMaxParticipants() {
        return nombreMaxParticipants;
    }

    /**
     *
     * @param nombreMaxParticipants
     */
    public void setNombreMaxParticipants(int nombreMaxParticipants) {
        this.nombreMaxParticipants = nombreMaxParticipants;
    }

    /**
     *
     * @return
     */
    public Integer getNombreParticipantsInscrits() {
        return nombreParticipantsInscrits;
    }

    /**
     *
     * @param nombreParticipantsInscrits
     */
    public void setNombreParticipantsInscrits(Integer nombreParticipantsInscrits) {
        this.nombreParticipantsInscrits = nombreParticipantsInscrits;
    }

    /**
     *
     * @return
     */
    public Date getDateDebut() {
        return dateDebut;
    }

    /**
     *
     * @param dateDebut
     */
    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    /**
     *
     * @return
     */
    public int getDureeActivite() {
        return dureeActivite;
    }

    /**
     *
     * @param dureeActivite
     */
    public void setDureeActivite(int dureeActivite) {
        this.dureeActivite = dureeActivite;
    }

    /**
     *
     * @return
     */
    public float getPrixHTVA() {
        return prixHTVA;
    }

    /**
     *
     * @param prixHTVA
     */
    public void setPrixHTVA(float prixHTVA) {
        this.prixHTVA = prixHTVA;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idActivite != null ? idActivite.hashCode() : 0);
        return hash;
    }

    /**
     *
     * @param object
     * @return
     */
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

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "ReservationDataLayer.entities.Activites[ idActivite=" + idActivite + " ]";
    }
    
}
