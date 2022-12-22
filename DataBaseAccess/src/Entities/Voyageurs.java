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

    /**
     *
     */
    public Voyageurs() {
        this.numeroClient = -1;
    }

    /**
     *
     * @param numeroClient
     */
    public Voyageurs(Integer numeroClient) {
        this.numeroClient = numeroClient;
    }

    /**
     *
     * @param numeroClient
     * @param nomVoyageur
     * @param prenomVoyageur
     */
    public Voyageurs(Integer numeroClient, String nomVoyageur, String prenomVoyageur) {
        this.numeroClient = numeroClient;
        this.nomVoyageur = nomVoyageur;
        this.prenomVoyageur = prenomVoyageur;
    }
    
    /**
     *
     * @param numeroClient
     * @param nomVoyageur
     * @param prenomVoyageur
     * @param nomRue
     * @param numHabitation
     * @param numBoiteHabitation
     * @param codePostal
     * @param commune
     * @param nationalite
     * @param dateNaissance
     * @param email
     * @param voyageurReferent
     */
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
    
    /**
     *
     * @return
     */
    public int getVoyageurReferent() {
        return voyageurReferent;
    }

    /**
     *
     * @param voyageurReferent
     */
    public void setVoyageurReferent(int voyageurReferent) {
        this.voyageurReferent = voyageurReferent;
    }
    
    /**
     *
     * @return
     */
    public Integer getNumeroClient() {
        return numeroClient;
    }

    /**
     *
     * @param numeroClient
     */
    public void setNumeroClient(Integer numeroClient) {
        this.numeroClient = numeroClient;
    }

    /**
     *
     * @return
     */
    public String getNomVoyageur() {
        return nomVoyageur;
    }

    /**
     *
     * @param nomVoyageur
     */
    public void setNomVoyageur(String nomVoyageur) {
        this.nomVoyageur = nomVoyageur;
    }

    /**
     *
     * @return
     */
    public String getPrenomVoyageur() {
        return prenomVoyageur;
    }

    /**
     *
     * @param prenomVoyageur
     */
    public void setPrenomVoyageur(String prenomVoyageur) {
        this.prenomVoyageur = prenomVoyageur;
    }

    /**
     *
     * @return
     */
    public String getNomRue() {
        return nomRue;
    }

    /**
     *
     * @param nomRue
     */
    public void setNomRue(String nomRue) {
        this.nomRue = nomRue;
    }

    /**
     *
     * @return
     */
    public int getNumHabitation() {
        return numHabitation;
    }

    /**
     *
     * @param numHabitation
     */
    public void setNumHabitation(int numHabitation) {
        this.numHabitation = numHabitation;
    }

    /**
     *
     * @return
     */
    public Integer getNumBoiteHabitation() {
        return numBoiteHabitation;
    }

    /**
     *
     * @param numBoiteHabitation
     */
    public void setNumBoiteHabitation(Integer numBoiteHabitation) {
        this.numBoiteHabitation = numBoiteHabitation;
    }

    /**
     *
     * @return
     */
    public int getCodePostal() {
        return codePostal;
    }

    /**
     *
     * @param codePostal
     */
    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    /**
     *
     * @return
     */
    public String getCommune() {
        return commune;
    }

    /**
     *
     * @param commune
     */
    public void setCommune(String commune) {
        this.commune = commune;
    }

    /**
     *
     * @return
     */
    public String getNationalite() {
        return nationalite;
    }

    /**
     *
     * @param nationalite
     */
    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    /**
     *
     * @return
     */
    public Date getDateNaissance() {
        return dateNaissance;
    }

    /**
     *
     * @param dateNaissance
     */
    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
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
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroClient != null ? numeroClient.hashCode() : 0);
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
        if (!(object instanceof Voyageurs)) {
            return false;
        }
        Voyageurs other = (Voyageurs) object;
        if ((this.numeroClient == null && other.numeroClient != null) || (this.numeroClient != null && !this.numeroClient.equals(other.numeroClient))) {
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
        return nomVoyageur + " " + prenomVoyageur;
    }
    
}
