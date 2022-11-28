/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActivitiesDataLayer.entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "accessemployes", catalog = "bd_holidays", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accessemployes.findAll", query = "SELECT a FROM Accessemployes a")
    , @NamedQuery(name = "Accessemployes.findByNumeroEmploye", query = "SELECT a FROM Accessemployes a WHERE a.accessemployesPK.numeroEmploye = :numeroEmploye")
    , @NamedQuery(name = "Accessemployes.findByHabilitation", query = "SELECT a FROM Accessemployes a WHERE a.accessemployesPK.habilitation = :habilitation")})
public class Accessemployes implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AccessemployesPK accessemployesPK;
    @JoinColumn(name = "numeroEmploye", referencedColumnName = "numeroEmploye", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Employes employes;

    public Accessemployes() {
    }

    public Accessemployes(AccessemployesPK accessemployesPK) {
        this.accessemployesPK = accessemployesPK;
    }

    public Accessemployes(int numeroEmploye, String habilitation) {
        this.accessemployesPK = new AccessemployesPK(numeroEmploye, habilitation);
    }

    public AccessemployesPK getAccessemployesPK() {
        return accessemployesPK;
    }

    public void setAccessemployesPK(AccessemployesPK accessemployesPK) {
        this.accessemployesPK = accessemployesPK;
    }

    public Employes getEmployes() {
        return employes;
    }

    public void setEmployes(Employes employes) {
        this.employes = employes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accessemployesPK != null ? accessemployesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accessemployes)) {
            return false;
        }
        Accessemployes other = (Accessemployes) object;
        if ((this.accessemployesPK == null && other.accessemployesPK != null) || (this.accessemployesPK != null && !this.accessemployesPK.equals(other.accessemployesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ActivitiesDataLayer.entities.Accessemployes[ accessemployesPK=" + accessemployesPK + " ]";
    }
    
}
