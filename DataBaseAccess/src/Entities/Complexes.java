/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author Arkios
 */

public class Complexes implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idComplexe;
    private String nomComplexe;
    private String typeComplexe;
    
    /**
     *
     * @return
     */
    public Vector toVector(){
        Vector<String> s = new Vector<String>();
        
        s.add(nomComplexe);
        s.add(typeComplexe);
        
        return s;
    }
    
    /**
     *
     */
    public Complexes() {
    }

    /**
     *
     * @param idComplexe
     */
    public Complexes(Integer idComplexe) {
        this.idComplexe = idComplexe;
    }

    /**
     *
     * @param idComplexe
     * @param nomComplexe
     * @param typeComplexe
     */
    public Complexes(Integer idComplexe, String nomComplexe, String typeComplexe) {
        this.idComplexe = idComplexe;
        this.nomComplexe = nomComplexe;
        this.typeComplexe = typeComplexe;
    }

    /**
     *
     * @return
     */
    public Integer getIdComplexe() {
        return idComplexe;
    }

    /**
     *
     * @param idComplexe
     */
    public void setIdComplexe(Integer idComplexe) {
        this.idComplexe = idComplexe;
    }

    /**
     *
     * @return
     */
    public String getNomComplexe() {
        return nomComplexe;
    }

    /**
     *
     * @param nomComplexe
     */
    public void setNomComplexe(String nomComplexe) {
        this.nomComplexe = nomComplexe;
    }

    /**
     *
     * @return
     */
    public String getTypeComplexe() {
        return typeComplexe;
    }

    /**
     *
     * @param typeComplexe
     */
    public void setTypeComplexe(String typeComplexe) {
        this.typeComplexe = typeComplexe;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComplexe != null ? idComplexe.hashCode() : 0);
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
        if (!(object instanceof Complexes)) {
            return false;
        }
        Complexes other = (Complexes) object;
        if ((this.idComplexe == null && other.idComplexe != null) || (this.idComplexe != null && !this.idComplexe.equals(other.idComplexe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Complexes{" + "idComplexe=" + idComplexe + ", nomComplexe=" + nomComplexe + ", typeComplexe=" + typeComplexe + '}';
    }
}
