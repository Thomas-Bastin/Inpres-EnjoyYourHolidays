/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReservationDataLayer.entities;

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

    public Reservationchambre(int idComplexe, int idChambre, int idVoyageur, Date dateArrive, int nbJours, String VoyageurName) {
        this.idComplexe = idComplexe;
        this.idChambre = idChambre;
        this.idVoyageur = idVoyageur;
        this.dateArrive = dateArrive;
        this.nbJours = nbJours;
        this.VoyageurName = VoyageurName;
    }
    
    @Override
    public String toString() {
        return "Reservationchambre{" + "idComplexe=" + idComplexe + ", idChambre=" + idChambre + ", idVoyageur=" + idVoyageur + ", dateArrive=" + dateArrive + ", nbJours=" + nbJours + ", VoyageurName=" + getVoyageurName() + '}';
    }
    
    
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
    
    
    
    

    public int getIdComplexe() {
        return idComplexe;
    }

    public void setIdComplexe(int idComplexe) {
        this.idComplexe = idComplexe;
    }

    public int getIdChambre() {
        return idChambre;
    }

    public void setIdChambre(int idChambre) {
        this.idChambre = idChambre;
    }

    public int getIdVoyageur() {
        return idVoyageur;
    }

    public void setIdVoyageur(int idVoyageur) {
        this.idVoyageur = idVoyageur;
    }

    public Date getDateArrive() {
        return dateArrive;
    }

    public void setDateArrive(Date dateArrive) {
        this.dateArrive = dateArrive;
    }

    public int getNbJours() {
        return nbJours;
    }

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
    
}
