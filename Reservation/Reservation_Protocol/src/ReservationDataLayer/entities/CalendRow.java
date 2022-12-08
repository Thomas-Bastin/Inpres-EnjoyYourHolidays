/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReservationDataLayer.entities;

import ReservationDataLayer.entities.Chambres;
import ReservationDataLayer.entities.Reservationchambre;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.LinkedList;
import java.util.Vector;

/**
 *
 * @author Arkios
 */
public class CalendRow implements Serializable {
    private final Chambres chambre;
    
    private Reservationchambre d1;
    private Reservationchambre d2;
    private Reservationchambre d3;
    private Reservationchambre d4;
    private Reservationchambre d5;
    private Reservationchambre d6;
    private Reservationchambre d7;

    public CalendRow(Chambres chambre) {
        this.chambre = chambre;
        this.d1 = null;
        this.d2 = null;
        this.d3 = null;
        this.d4 = null;
        this.d5 = null;
        this.d6 = null;
        this.d7 = null;
    }
    
    public CalendRow(Chambres chambre, Reservationchambre d1, Reservationchambre d2, Reservationchambre d3, Reservationchambre d4, Reservationchambre d5, Reservationchambre d6, Reservationchambre d7) {
        this.chambre = chambre;
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
        this.d4 = d4;
        this.d5 = d5;
        this.d6 = d6;
        this.d7 = d7;
    }
    
    public Vector toVector(){
        Vector vec = new Vector();
        
        vec.add(chambre.getType() + " n°" + chambre.getNumChambre());
        vec.add(d1 == null ? " ": d1.getVoyageurName());
        vec.add(d2 == null ? " ": d2.getVoyageurName());
        vec.add(d3 == null ? " ": d3.getVoyageurName());
        vec.add(d4 == null ? " ": d4.getVoyageurName());
        vec.add(d5 == null ? " ": d5.getVoyageurName());
        vec.add(d6 == null ? " ": d6.getVoyageurName());
        vec.add(d7 == null ? " ": d7.getVoyageurName());
        
        return vec;
    }
    
    public void setReserv(DayOfWeek id, Reservationchambre ch){
        if(DayOfWeek.MONDAY== id) d1 = ch;
        if(DayOfWeek.TUESDAY== id) d2 = ch;
        if(DayOfWeek.WEDNESDAY== id) d3 = ch;
        if(DayOfWeek.THURSDAY== id) d4 = ch;
        if(DayOfWeek.FRIDAY == id) d5 = ch;
        if(DayOfWeek.SATURDAY == id) d6 = ch;
        if(DayOfWeek.SUNDAY == id) d7 = ch;
    }

    public Reservationchambre getD1() {
        return d1;
    }

    public void setD1(Reservationchambre d1) {
        this.d1 = d1;
    }

    public Reservationchambre getD2() {
        return d2;
    }

    public void setD2(Reservationchambre d2) {
        this.d2 = d2;
    }

    public Reservationchambre getD3() {
        return d3;
    }

    public void setD3(Reservationchambre d3) {
        this.d3 = d3;
    }

    public Reservationchambre getD4() {
        return d4;
    }

    public void setD4(Reservationchambre d4) {
        this.d4 = d4;
    }

    public Reservationchambre getD5() {
        return d5;
    }

    public void setD5(Reservationchambre d5) {
        this.d5 = d5;
    }

    public Reservationchambre getD6() {
        return d6;
    }

    public void setD6(Reservationchambre d6) {
        this.d6 = d6;
    }

    public Reservationchambre getD7() {
        return d7;
    }

    public void setD7(Reservationchambre d7) {
        this.d7 = d7;
    }
}
