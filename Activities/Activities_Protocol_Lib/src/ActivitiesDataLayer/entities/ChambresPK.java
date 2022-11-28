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
public class ChambresPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "numChambre")
    private int numChambre;
    @Basic(optional = false)
    @Column(name = "idComplexe")
    private int idComplexe;

    public ChambresPK() {
    }

    public ChambresPK(int numChambre, int idComplexe) {
        this.numChambre = numChambre;
        this.idComplexe = idComplexe;
    }

    public int getNumChambre() {
        return numChambre;
    }

    public void setNumChambre(int numChambre) {
        this.numChambre = numChambre;
    }

    public int getIdComplexe() {
        return idComplexe;
    }

    public void setIdComplexe(int idComplexe) {
        this.idComplexe = idComplexe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) numChambre;
        hash += (int) idComplexe;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChambresPK)) {
            return false;
        }
        ChambresPK other = (ChambresPK) object;
        if (this.numChambre != other.numChambre) {
            return false;
        }
        if (this.idComplexe != other.idComplexe) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ActivitiesDataLayer.entities.ChambresPK[ numChambre=" + numChambre + ", idComplexe=" + idComplexe + " ]";
    }
    
}
