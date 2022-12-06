/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReservationDataLayer.entities;

import java.io.Serializable;
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

/**
 *
 * @author Arkios
 */

public class Complexes implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idComplexe;
    private String nomComplexe;
    private String typeComplexe;

    public Complexes() {
    }

    public Complexes(Integer idComplexe) {
        this.idComplexe = idComplexe;
    }

    public Complexes(Integer idComplexe, String nomComplexe, String typeComplexe) {
        this.idComplexe = idComplexe;
        this.nomComplexe = nomComplexe;
        this.typeComplexe = typeComplexe;
    }

    public Integer getIdComplexe() {
        return idComplexe;
    }

    public void setIdComplexe(Integer idComplexe) {
        this.idComplexe = idComplexe;
    }

    public String getNomComplexe() {
        return nomComplexe;
    }

    public void setNomComplexe(String nomComplexe) {
        this.nomComplexe = nomComplexe;
    }

    public String getTypeComplexe() {
        return typeComplexe;
    }

    public void setTypeComplexe(String typeComplexe) {
        this.typeComplexe = typeComplexe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComplexe != null ? idComplexe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Complexes)) {
            return false;
        }
        Complexes other = (Complexes) object;
        if ((this.idComplexe == null && other.idComplexe != null) || (this.idComplexe != null && !this.idComplexe.equals(other.idComplexe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ReservationDataLayer.entities.Complexes[ idComplexe=" + idComplexe + " ]";
    }
    
}
