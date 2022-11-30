/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActivitiesDataLayer.entities;

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
@Entity
@Table(name = "employes")
@NamedQueries({
    @NamedQuery(name = "Employes.findAll", query = "SELECT e FROM Employes e")})
public class Employes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numeroEmploye")
    private Integer numeroEmploye;
    @Basic(optional = false)
    @Column(name = "nomEmploye")
    private String nomEmploye;
    @Basic(optional = false)
    @Column(name = "prenomEmploye")
    private String prenomEmploye;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employes")
    private List<Accessemployes> accessemployesList;

    public Employes() {
    }

    public Employes(Integer numeroEmploye) {
        this.numeroEmploye = numeroEmploye;
    }

    public Employes(Integer numeroEmploye, String nomEmploye, String prenomEmploye, String email, String password) {
        this.numeroEmploye = numeroEmploye;
        this.nomEmploye = nomEmploye;
        this.prenomEmploye = prenomEmploye;
        this.email = email;
        this.password = password;
    }

    public Integer getNumeroEmploye() {
        return numeroEmploye;
    }

    public void setNumeroEmploye(Integer numeroEmploye) {
        this.numeroEmploye = numeroEmploye;
    }

    public String getNomEmploye() {
        return nomEmploye;
    }

    public void setNomEmploye(String nomEmploye) {
        this.nomEmploye = nomEmploye;
    }

    public String getPrenomEmploye() {
        return prenomEmploye;
    }

    public void setPrenomEmploye(String prenomEmploye) {
        this.prenomEmploye = prenomEmploye;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Accessemployes> getAccessemployesList() {
        return accessemployesList;
    }

    public void setAccessemployesList(List<Accessemployes> accessemployesList) {
        this.accessemployesList = accessemployesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroEmploye != null ? numeroEmploye.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employes)) {
            return false;
        }
        Employes other = (Employes) object;
        if ((this.numeroEmploye == null && other.numeroEmploye != null) || (this.numeroEmploye != null && !this.numeroEmploye.equals(other.numeroEmploye))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ActivitiesDataLayer.entities.Employes[ numeroEmploye=" + numeroEmploye + " ]";
    }
    
}
