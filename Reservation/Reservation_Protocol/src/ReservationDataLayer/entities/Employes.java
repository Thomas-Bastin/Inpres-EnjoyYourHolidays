/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReservationDataLayer.entities;

import java.io.Serializable;

/**
 *
 * @author Arkios
 */
public class Employes implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer numeroEmploye;
    private String nomEmploye;
    private String prenomEmploye;
    private String email;
    private String password;

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
        return "ReservationDataLayer.entities.Employes[ numeroEmploye=" + numeroEmploye + " ]";
    }
    
}
