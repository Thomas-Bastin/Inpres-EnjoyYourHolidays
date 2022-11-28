/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActivitiesDataLayer.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Arkios
 */
@Entity
@Table(name = "voyageurs", catalog = "bd_holidays", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Voyageurs.findAll", query = "SELECT v FROM Voyageurs v")
    , @NamedQuery(name = "Voyageurs.findByNumeroClient", query = "SELECT v FROM Voyageurs v WHERE v.numeroClient = :numeroClient")
    , @NamedQuery(name = "Voyageurs.findByNomVoyageur", query = "SELECT v FROM Voyageurs v WHERE v.nomVoyageur = :nomVoyageur")
    , @NamedQuery(name = "Voyageurs.findByPrenomVoyageur", query = "SELECT v FROM Voyageurs v WHERE v.prenomVoyageur = :prenomVoyageur")
    , @NamedQuery(name = "Voyageurs.findByNomRue", query = "SELECT v FROM Voyageurs v WHERE v.nomRue = :nomRue")
    , @NamedQuery(name = "Voyageurs.findByNumHabitation", query = "SELECT v FROM Voyageurs v WHERE v.numHabitation = :numHabitation")
    , @NamedQuery(name = "Voyageurs.findByNumBoiteHabitation", query = "SELECT v FROM Voyageurs v WHERE v.numBoiteHabitation = :numBoiteHabitation")
    , @NamedQuery(name = "Voyageurs.findByCodePostal", query = "SELECT v FROM Voyageurs v WHERE v.codePostal = :codePostal")
    , @NamedQuery(name = "Voyageurs.findByCommune", query = "SELECT v FROM Voyageurs v WHERE v.commune = :commune")
    , @NamedQuery(name = "Voyageurs.findByNationalite", query = "SELECT v FROM Voyageurs v WHERE v.nationalite = :nationalite")
    , @NamedQuery(name = "Voyageurs.findByDateNaissance", query = "SELECT v FROM Voyageurs v WHERE v.dateNaissance = :dateNaissance")
    , @NamedQuery(name = "Voyageurs.findByEmail", query = "SELECT v FROM Voyageurs v WHERE v.email = :email")})
public class Voyageurs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numeroClient")
    private Integer numeroClient;
    @Basic(optional = false)
    @Column(name = "nomVoyageur")
    private String nomVoyageur;
    @Basic(optional = false)
    @Column(name = "prenomVoyageur")
    private String prenomVoyageur;
    @Column(name = "nomRue")
    private String nomRue;
    @Column(name = "numHabitation")
    private Integer numHabitation;
    @Column(name = "numBoiteHabitation")
    private Integer numBoiteHabitation;
    @Column(name = "codePostal")
    private Integer codePostal;
    @Column(name = "commune")
    private String commune;
    @Column(name = "nationalite")
    private String nationalite;
    @Column(name = "dateNaissance")
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "voyageurTitulaire")
    private List<Reservationactivite> reservationactiviteList;
    @OneToMany(mappedBy = "voyageurReferent")
    private List<Voyageurs> voyageursList;
    @JoinColumn(name = "voyageurReferent", referencedColumnName = "numeroClient")
    @ManyToOne
    private Voyageurs voyageurReferent;

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

    public Integer getNumHabitation() {
        return numHabitation;
    }

    public void setNumHabitation(Integer numHabitation) {
        this.numHabitation = numHabitation;
    }

    public Integer getNumBoiteHabitation() {
        return numBoiteHabitation;
    }

    public void setNumBoiteHabitation(Integer numBoiteHabitation) {
        this.numBoiteHabitation = numBoiteHabitation;
    }

    public Integer getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(Integer codePostal) {
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

    @XmlTransient
    public List<Reservationactivite> getReservationactiviteList() {
        return reservationactiviteList;
    }

    public void setReservationactiviteList(List<Reservationactivite> reservationactiviteList) {
        this.reservationactiviteList = reservationactiviteList;
    }

    @XmlTransient
    public List<Voyageurs> getVoyageursList() {
        return voyageursList;
    }

    public void setVoyageursList(List<Voyageurs> voyageursList) {
        this.voyageursList = voyageursList;
    }

    public Voyageurs getVoyageurReferent() {
        return voyageurReferent;
    }

    public void setVoyageurReferent(Voyageurs voyageurReferent) {
        this.voyageurReferent = voyageurReferent;
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
        return "ActivitiesDataLayer.entities.Voyageurs[ numeroClient=" + numeroClient + " ]";
    }
    
}
