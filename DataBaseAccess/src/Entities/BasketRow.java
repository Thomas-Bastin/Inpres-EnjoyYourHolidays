/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import Entities.*;
import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Arkios
 */
public class BasketRow implements Serializable{
    Complexes complexe;
    Chambres room;
    Date date;
    int nbNuit;

    /**
     *
     * @param complexe
     * @param room
     * @param date
     * @param nbNuit
     */
    public BasketRow(Complexes complexe, Chambres room, Date date, int nbNuit) {
        this.complexe = complexe;
        this.room = room;
        this.date = date;
        this.nbNuit = nbNuit;
    }

    /**
     *
     * @return
     */
    public Complexes getComplexe() {
        return complexe;
    }

    /**
     *
     * @param complexe
     */
    public void setComplexe(Complexes complexe) {
        this.complexe = complexe;
    }

    /**
     *
     * @return
     */
    public Chambres getRoom() {
        return room;
    }

    /**
     *
     * @param room
     */
    public void setRoom(Chambres room) {
        this.room = room;
    }

    /**
     *
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     *
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     *
     * @return
     */
    public int getNbNuit() {
        return nbNuit;
    }

    /**
     *
     * @param nbNuit
     */
    public void setNbNuit(int nbNuit) {
        this.nbNuit = nbNuit;
    }
}
