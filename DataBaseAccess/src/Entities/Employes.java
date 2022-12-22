/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

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

    /**
     *
     */
    public Employes() {
    }

    /**
     *
     * @param numeroEmploye
     */
    public Employes(Integer numeroEmploye) {
        this.numeroEmploye = numeroEmploye;
    }

    /**
     *
     * @param numeroEmploye
     * @param nomEmploye
     * @param prenomEmploye
     * @param email
     * @param password
     */
    public Employes(Integer numeroEmploye, String nomEmploye, String prenomEmploye, String email, String password) {
        this.numeroEmploye = numeroEmploye;
        this.nomEmploye = nomEmploye;
        this.prenomEmploye = prenomEmploye;
        this.email = email;
        this.password = password;
    }

    /**
     *
     * @return
     */
    public Integer getNumeroEmploye() {
        return numeroEmploye;
    }

    /**
     *
     * @param numeroEmploye
     */
    public void setNumeroEmploye(Integer numeroEmploye) {
        this.numeroEmploye = numeroEmploye;
    }

    /**
     *
     * @return
     */
    public String getNomEmploye() {
        return nomEmploye;
    }

    /**
     *
     * @param nomEmploye
     */
    public void setNomEmploye(String nomEmploye) {
        this.nomEmploye = nomEmploye;
    }

    /**
     *
     * @return
     */
    public String getPrenomEmploye() {
        return prenomEmploye;
    }

    /**
     *
     * @param prenomEmploye
     */
    public void setPrenomEmploye(String prenomEmploye) {
        this.prenomEmploye = prenomEmploye;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroEmploye != null ? numeroEmploye.hashCode() : 0);
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
        if (!(object instanceof Employes)) {
            return false;
        }
        Employes other = (Employes) object;
        if ((this.numeroEmploye == null && other.numeroEmploye != null) || (this.numeroEmploye != null && !this.numeroEmploye.equals(other.numeroEmploye))) {
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
        return "ReservationDataLayer.entities.Employes[ numeroEmploye=" + numeroEmploye + " ]";
    }
    
}
