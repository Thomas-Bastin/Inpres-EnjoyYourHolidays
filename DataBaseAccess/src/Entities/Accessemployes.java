/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Arkios
 */
public class Accessemployes implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int numeroEmploye;
    private String habilitation;

    /**
     *
     * @param numeroEmploye
     * @param habilitation
     */
    public Accessemployes(int numeroEmploye, String habilitation) {
        this.numeroEmploye = numeroEmploye;
        this.habilitation = habilitation;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.numeroEmploye;
        hash = 67 * hash + Objects.hashCode(this.habilitation);
        return hash;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Accessemployes{" + "numeroEmploye=" + numeroEmploye + ", habilitation=" + habilitation + '}';
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Accessemployes other = (Accessemployes) obj;
        if (this.numeroEmploye != other.numeroEmploye) {
            return false;
        }
        if (!Objects.equals(this.habilitation, other.habilitation)) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    public int getNumeroEmploye() {
        return numeroEmploye;
    }

    /**
     *
     * @param numeroEmploye
     */
    public void setNumeroEmploye(int numeroEmploye) {
        this.numeroEmploye = numeroEmploye;
    }

    /**
     *
     * @return
     */
    public String getHabilitation() {
        return habilitation;
    }

    /**
     *
     * @param habilitation
     */
    public void setHabilitation(String habilitation) {
        this.habilitation = habilitation;
    }

    /**
     *
     */
    public Accessemployes() {
    } 
}
