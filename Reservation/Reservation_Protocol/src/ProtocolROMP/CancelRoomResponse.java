/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolROMP;

import java.sql.Date;

/**
 *
 * @author Thomas
 */
public class CancelRoomResponse extends Response {
    public final static int KO = 201;
    
    private final Date dateDepasse;
    public CancelRoomResponse(int c, String m, Date dateDespasse) {
        super(c, m);
        dateDepasse = dateDespasse;
    }
    
    public Date getDateDepasse() {
        return dateDepasse;
    }    
}
