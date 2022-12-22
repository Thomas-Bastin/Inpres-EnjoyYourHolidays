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
public class ListRoomResponse extends Response {
    private final LinkedList<Chambres> list;
    
    public ListRoomResponse(int c, String m, LinkedList<Chambres> l) {
        super(c, m);
        list = l;
    }
    
    public LinkedList<Chambres> getList() {
        return list;
    }
    
}
