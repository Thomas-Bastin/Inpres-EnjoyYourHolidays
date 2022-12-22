/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
/**
 *
 * @author Arkios
 */

public class Reservationchambre implements Serializable {

    private int idComplexe;
    private int idChambre;
    private int idVoyageur;
    private Date dateArrive;
    private int nbJours;
    private String VoyageurName;
    private boolean isPayed;

    /**
     *
     * @param idComplexe
     * @param idChambre
     * @param idVoyageur
     * @param dateArrive
     * @param nbJours
     * @param VoyageurName
     * @param isPayed
     */
    public Reservationchambre(int idComplexe, int idChambre, int idVoyageur, Date dateArrive, int nbJours, String VoyageurName, boolean isPayed) {
        this.idComplexe = idComplexe;
        this.idChambre = idChambre;
        this.idVoyageur = idVoyageur;
        this.dateArrive = dateArrive;
        this.nbJours = nbJours;
        this.VoyageurName = VoyageurName;
        this.isPayed = isPayed;
    }
    
    public Reservationchambre(int idComplexe, int idChambre, int idVoyageur, Date dateArrive, int nbJours) {
        this.idComplexe = idComplexe;
        this.idChambre = idChambre;
        this.idVoyageur = idVoyageur;
        this.dateArrive = dateArrive;
        this.nbJours = nbJours;
        this.VoyageurName = null;
        this.isPayed = false;
    }
    
    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Reservationchambre{" + "idComplexe=" + idComplexe + ", idChambre=" + idChambre + ", idVoyageur=" + idVoyageur + ", dateArrive=" + dateArrive + ", nbJours=" + nbJours + ", VoyageurName=" + getVoyageurName() + '}';
    }
    
    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.idComplexe;
        hash = 79 * hash + this.idChambre;
        hash = 79 * hash + this.idVoyageur;
        hash = 79 * hash + Objects.hashCode(this.dateArrive);
        hash = 79 * hash + this.nbJours;
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
        final Reservationchambre other = (Reservationchambre) obj;
        if (this.idComplexe != other.idComplexe) {
            return false;
        }
        if (this.idChambre != other.idChambre) {
            return false;
        }
        if (this.idVoyageur != other.idVoyageur) {
            return false;
        }
        return true;
    }
    
    /**
     *
     * @return
     */
    public int getIdComplexe() {
        return idComplexe;
    }

    /**
     *
     * @param idComplexe
     */
    public void setIdComplexe(int idComplexe) {
        this.idComplexe = idComplexe;
    }

    /**
     *
     * @return
     */
    public int getIdChambre() {
        return idChambre;
    }

    /**
     *
     * @param idChambre
     */
    public void setIdChambre(int idChambre) {
        this.idChambre = idChambre;
    }

    /**
     *
     * @return
     */
    public int getIdVoyageur() {
        return idVoyageur;
    }

    /**
     *
     * @param idVoyageur
     */
    public void setIdVoyageur(int idVoyageur) {
        this.idVoyageur = idVoyageur;
    }

    /**
     *
     * @return
     */
    public Date getDateArrive() {
        return dateArrive;
    }

    /**
     *
     * @param dateArrive
     */
    public void setDateArrive(Date dateArrive) {
        this.dateArrive = dateArrive;
    }

    /**
     *
     * @return
     */
    public int getNbJours() {
        return nbJours;
    }

    /**
     *
     * @param nbJours
     */
    public void setNbJours(int nbJours) {
        this.nbJours = nbJours;
    }
    
    /**
     * @return the VoyageurName
     */
    public String getVoyageurName() {
        return VoyageurName;
    }

    /**
     * @param VoyageurName the VoyageurName to set
     */
    public void setVoyageurName(String VoyageurName) {
        this.VoyageurName = VoyageurName;
    }

    /**
     *
     * @return
     */
    public boolean isIsPayed() {
        return isPayed;
    }

    /**
     *
     * @param isPayed
     */
    public void setIsPayed(boolean isPayed) {
        this.isPayed = isPayed;
    }
    
    
}
