/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActivitiesDataLayer.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Arkios
 */
@Embeddable
public class AccessemployesPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "numeroEmploye")
    private int numeroEmploye;
    @Basic(optional = false)
    @Column(name = "habilitation")
    private String habilitation;

    public AccessemployesPK() {
    }

    public AccessemployesPK(int numeroEmploye, String habilitation) {
        this.numeroEmploye = numeroEmploye;
        this.habilitation = habilitation;
    }

    public int getNumeroEmploye() {
        return numeroEmploye;
    }

    public void setNumeroEmploye(int numeroEmploye) {
        this.numeroEmploye = numeroEmploye;
    }

    public String getHabilitation() {
        return habilitation;
    }

    public void setHabilitation(String habilitation) {
        this.habilitation = habilitation;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) numeroEmploye;
        hash += (habilitation != null ? habilitation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccessemployesPK)) {
            return false;
        }
        AccessemployesPK other = (AccessemployesPK) object;
        if (this.numeroEmploye != other.numeroEmploye) {
            return false;
        }
        if ((this.habilitation == null && other.habilitation != null) || (this.habilitation != null && !this.habilitation.equals(other.habilitation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ActivitiesDataLayer.entities.AccessemployesPK[ numeroEmploye=" + numeroEmploye + ", habilitation=" + habilitation + " ]";
    }
    
}
