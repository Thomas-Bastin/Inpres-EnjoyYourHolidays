/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReservationDataLayer.entities;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Vector;

/**
 *
 * @author Arkios
 */

public class Chambres implements Serializable {
    private int numChambre;
    private int idComplexe;


    private String type;
    private String equipements;
    private Integer nombreLits;
    private Float prixHTVA;

    public Chambres(int numChambre, int idComplexe, String type, String equipements, Integer nombreLits, Float prixHTVA) {
        this.numChambre = numChambre;
        this.idComplexe = idComplexe;
        this.type = type;
        this.equipements = equipements;
        this.nombreLits = nombreLits;
        this.prixHTVA = prixHTVA;
    }

    public Vector toVector(){
        Vector<String> vec = new Vector<String>();
        
        vec.add("" + numChambre);
        vec.add("" + idComplexe);
        vec.add("" + type);
        vec.add("" + equipements);
        vec.add("" + nombreLits);
        vec.add("" + prixHTVA);
        
        return vec;
    }
    
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.UP);
        return "Chambre n°" + numChambre + " " + type + " - " +  df.format(prixHTVA*1.21) + "€";
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    
    
}
