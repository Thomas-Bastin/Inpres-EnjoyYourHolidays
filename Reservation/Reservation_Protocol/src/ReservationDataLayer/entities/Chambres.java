/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReservationDataLayer.entities;

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
public class Chambres implements Serializable {

    private static final long serialVersionUID = 1L;
    private int numChambre;
    private int idComplexe;
    private String equipements;
    private Integer nombreLits;
    private Float prixHTVA;
    private int complexes;

    public Chambres() {
    }

    public int getNumChambre() {
        return numChambre;
    }

    public void setNumChambre(int numChambre) {
        this.numChambre = numChambre;
    }

    public int getIdComplexe() {
        return idComplexe;
    }

    public void setIdComplexe(int idComplexe) {
        this.idComplexe = idComplexe;
    }

    public String getEquipements() {
        return equipements;
    }

    public void setEquipements(String equipements) {
        this.equipements = equipements;
    }

    public Integer getNombreLits() {
        return nombreLits;
    }

    public void setNombreLits(Integer nombreLits) {
        this.nombreLits = nombreLits;
    }

    public Float getPrixHTVA() {
        return prixHTVA;
    }

    public void setPrixHTVA(Float prixHTVA) {
        this.prixHTVA = prixHTVA;
    }

    public int getComplexes() {
        return complexes;
    }

    public void setComplexes(int complexes) {
        this.complexes = complexes;
    }

    public Chambres(int numChambre, int idComplexe, String equipements, Integer nombreLits, Float prixHTVA, int complexes) {
        this.numChambre = numChambre;
        this.idComplexe = idComplexe;
        this.equipements = equipements;
        this.nombreLits = nombreLits;
        this.prixHTVA = prixHTVA;
        this.complexes = complexes;
    }
    
    
}
