/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolROMP;


import Entities.*;
import java.util.LinkedList;

/**
 *
 * @author Thomas
 */
public class ListReservationRoomResponse extends Response {
    
    private final LinkedList<CalendRow> list;
    
    public ListReservationRoomResponse(int c, String m, LinkedList<CalendRow> l) {
        super(c, m);
        list = l;
    }

    public LinkedList<CalendRow> getList() {
        return list;
    }
}
