/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActivitiesDataLayer.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Arkios
 */

public class Inscriptionactivites implements Serializable {
    private int paye;
    private Integer activites;
    private Integer voyageurs;

    public Inscriptionactivites() {
    }
    
    public Inscriptionactivites(int voy, int act) {
        voyageurs = voy;
        activites = act;
        paye = 0;
    }

    public Inscriptionactivites(int voy, int act, int pay) {
        voyageurs = voy;
        activites = act;
        paye = pay;
    }

    public int getPaye() {
        return paye;
    }

    public void setPaye(int paye) {
        this.paye = paye;
    }

    public Integer getActivites() {
        return activites;
    }

    public void setActivites(Integer activites) {
        this.activites = activites;
    }

    public Integer getVoyageurs() {
        return voyageurs;
    }

    public void setVoyageurs(Integer voyageurs) {
        this.voyageurs = voyageurs;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inscriptionactivites)) {
            return false;
        }
        Inscriptionactivites other = (Inscriptionactivites) object;
        
        if((activites == null && other.activites == null) && (voyageurs == null && other.voyageurs == null)){
            return true;
        }
        
        if((activites == other.activites) && (voyageurs == other.voyageurs)){
            return true;
        }
        
        return false;
    }

    
    @Override
    public String toString() {
        return "Inscriptionactivites{" + "paye=" + paye + ", activites=" + activites + ", voyageurs=" + voyageurs + '}';
    }
}
