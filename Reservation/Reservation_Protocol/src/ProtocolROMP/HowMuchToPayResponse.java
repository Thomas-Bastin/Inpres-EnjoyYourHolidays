/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolROMP;

import java.sql.Date;

/**
 *
 * @author Arkios
 */
public class HowMuchToPayResponse extends Response {    
    private final double howMuchToPay;
    
    public HowMuchToPayResponse(int c, String m) {
        super(c, m);
        howMuchToPay = -1;
    }
    
    public HowMuchToPayResponse(int c, String m, double howmuchtopay) {
        super(c, m);
        howMuchToPay = howmuchtopay;
    }

    public double getHowMuchToPay() {
        return howMuchToPay;
    }
}
