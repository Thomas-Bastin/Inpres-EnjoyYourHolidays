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

public class Inscriptionactivites implements Serializable {

    private static final long serialVersionUID = 1L;

    private int voyageurRef;
    private int activiteRef;
    private int paye;
    private Date dateInscription;

    /**
     *
     * @param voyageurRef
     * @param activiteRef
     * @param paye
     * @param dateInscription
     */
    public Inscriptionactivites(int voyageurRef, int activiteRef, int paye, Date dateInscription) {
        this.voyageurRef = voyageurRef;
        this.activiteRef = activiteRef;
        this.paye = paye;
        this.dateInscription = dateInscription;
    }

    /**
     *
     * @return
     */
    public int getVoyageurRef() {
        return voyageurRef;
    }

    /**
     *
     * @param voyageurRef
     */
    public void setVoyageurRef(int voyageurRef) {
        this.voyageurRef = voyageurRef;
    }

    /**
     *
     * @return
     */
    public int getActiviteRef() {
        return activiteRef;
    }

    /**
     *
     * @param activiteRef
     */
    public void setActiviteRef(int activiteRef) {
        this.activiteRef = activiteRef;
    }

    /**
     *
     * @return
     */
    public int getPaye() {
        return paye;
    }

    /**
     *
     * @param paye
     */
    public void setPaye(int paye) {
        this.paye = paye;
    }

    /**
     *
     * @return
     */
    public Date getDateInscription() {
        return dateInscription;
    }

    /**
     *
     * @param dateInscription
     */
    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Inscriptionactivites{" + "voyageurRef=" + voyageurRef + ", activiteRef=" + activiteRef + ", paye=" + paye + ", dateInscription=" + dateInscription + '}';
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.voyageurRef;
        hash = 71 * hash + this.activiteRef;
        return hash;
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
        final Inscriptionactivites other = (Inscriptionactivites) obj;
        if (this.voyageurRef != other.voyageurRef) {
            return false;
        }
        if (this.activiteRef != other.activiteRef) {
            return false;
        }
        return true;
    }

    
}
