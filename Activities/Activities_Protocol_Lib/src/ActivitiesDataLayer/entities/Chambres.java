/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActivitiesDataLayer.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Arkios
 */
@Entity
@Table(name = "chambres", catalog = "bd_holidays", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Chambres.findAll", query = "SELECT c FROM Chambres c")
    , @NamedQuery(name = "Chambres.findByNumChambre", query = "SELECT c FROM Chambres c WHERE c.chambresPK.numChambre = :numChambre")
    , @NamedQuery(name = "Chambres.findByEquipements", query = "SELECT c FROM Chambres c WHERE c.equipements = :equipements")
    , @NamedQuery(name = "Chambres.findByNombreLits", query = "SELECT c FROM Chambres c WHERE c.nombreLits = :nombreLits")
    , @NamedQuery(name = "Chambres.findByPrixHTVA", query = "SELECT c FROM Chambres c WHERE c.prixHTVA = :prixHTVA")
    , @NamedQuery(name = "Chambres.findByIdComplexe", query = "SELECT c FROM Chambres c WHERE c.chambresPK.idComplexe = :idComplexe")})
public class Chambres implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ChambresPK chambresPK;
    @Column(name = "equipements")
    private String equipements;
    @Column(name = "nombreLits")
    private Integer nombreLits;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "prixHTVA")
    private Float prixHTVA;
    @JoinColumn(name = "idComplexe", referencedColumnName = "idComplexe", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Complexes complexes;

    public Chambres() {
    }

    public Chambres(ChambresPK chambresPK) {
        this.chambresPK = chambresPK;
    }

    public Chambres(int numChambre, int idComplexe) {
        this.chambresPK = new ChambresPK(numChambre, idComplexe);
    }

    public ChambresPK getChambresPK() {
        return chambresPK;
    }

    public void setChambresPK(ChambresPK chambresPK) {
        this.chambresPK = chambresPK;
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

    public Complexes getComplexes() {
        return complexes;
    }

    public void setComplexes(Complexes complexes) {
        this.complexes = complexes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (chambresPK != null ? chambresPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Chambres)) {
            return false;
        }
        Chambres other = (Chambres) object;
        if ((this.chambresPK == null && other.chambresPK != null) || (this.chambresPK != null && !this.chambresPK.equals(other.chambresPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ActivitiesDataLayer.entities.Chambres[ chambresPK=" + chambresPK + " ]";
    }
    
}
