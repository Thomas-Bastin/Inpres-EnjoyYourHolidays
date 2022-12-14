/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReservationDataLayer.entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Arkios
 */
public class Voyageurs implements Serializable {

    private Integer numeroClient;
    private String nomVoyageur;
    private String prenomVoyageur;
    private String nomRue;
    private int numHabitation;
    private Integer numBoiteHabitation;
    private int codePostal;
    private String commune;
    private String nationalite;
    private Date dateNaissance;
    private String email;
    private int voyageurReferent;

    
    
    public Voyageurs() {
    }

    public Voyageurs(Integer numeroClient) {
        this.numeroClient = numeroClient;
    }

    public Voyageurs(Integer numeroClient, String nomVoyageur, String prenomVoyageur) {
        this.numeroClient = numeroClient;
        this.nomVoyageur = nomVoyageur;
        this.prenomVoyageur = prenomVoyageur;
    }
    
    public Voyageurs(Integer numeroClient, String nomVoyageur, String prenomVoyageur, String nomRue, Integer numHabitation, Integer numBoiteHabitation, Integer codePostal, String commune, String nationalite, Date dateNaissance, String email, Integer voyageurReferent) {
        this.numeroClient = numeroClient;
        this.nomVoyageur = nomVoyageur;
        this.prenomVoyageur = prenomVoyageur;
        this.nomRue = nomRue;
        this.numHabitation = numHabitation;
        this.numBoiteHabitation = numBoiteHabitation;
        this.codePostal = codePostal;
        this.commune = commune;
        this.nationalite = nationalite;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.voyageurReferent = voyageurReferent;
    }
    
    public int getVoyageurReferent() {
        return voyageurReferent;
    }

    public void setVoyageurReferent(int voyageurReferent) {
        this.voyageurReferent = voyageurReferent;
    }
    
    public Integer getNumeroClient() {
        return numeroClient;
    }

    public void setNumeroClient(Integer numeroClient) {
        this.numeroClient = numeroClient;
    }

    public String getNomVoyageur() {
        return nomVoyageur;
    }

    public void setNomVoyageur(String nomVoyageur) {
        this.nomVoyageur = nomVoyageur;
    }

    public String getPrenomVoyageur() {
        return prenomVoyageur;
    }

    public void setPrenomVoyageur(String prenomVoyageur) {
        this.prenomVoyageur = prenomVoyageur;
    }

    public String getNomRue() {
        return nomRue;
    }

    public void setNomRue(String nomRue) {
        this.nomRue = nomRue;
    }

    public int getNumHabitation() {
        return numHabitation;
    }

    public void setNumHabitation(int numHabitation) {
        this.numHabitation = numHabitation;
    }

    public Integer getNumBoiteHabitation() {
        return numBoiteHabitation;
    }

    public void setNumBoiteHabitation(Integer numBoiteHabitation) {
        this.numBoiteHabitation = numBoiteHabitation;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroClient != null ? numeroClient.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Voyageurs)) {
            return false;
        }
        Voyageurs other = (Voyageurs) object;
        if ((this.numeroClient == null && other.numeroClient != null) || (this.numeroClient != null && !this.numeroClient.equals(other.numeroClient))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nomVoyageur + " " + prenomVoyageur;
    }
    
}
