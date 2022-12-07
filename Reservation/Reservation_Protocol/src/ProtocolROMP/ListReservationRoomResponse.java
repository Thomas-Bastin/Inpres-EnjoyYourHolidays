/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolROMP;

import java.util.LinkedList;

/**
 *
 * @author Thomas
 */
public class ListReservationRoomResponse extends Response {
    
    private final LinkedList list;
    
    public ListReservationRoomResponse(int c, String m, LinkedList l) {
        super(c, m);
        list = l;
    }

    public LinkedList getList() {
        return list;
    }
}
