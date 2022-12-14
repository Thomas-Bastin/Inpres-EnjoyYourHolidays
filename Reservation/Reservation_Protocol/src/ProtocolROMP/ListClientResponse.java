/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolROMP;

import ReservationDataLayer.entities.Voyageurs;
import java.util.LinkedList;

/**
 *
 * @author Thomas
 */
public class ListClientResponse extends Response{

    private final LinkedList<Voyageurs> list;
    
    public ListClientResponse(int c, String m){
        super(c,m);
        list = null;
    }
    
    public ListClientResponse(int c, String m, LinkedList<Voyageurs> l){
        super(c,m);
        
        if(c == SUCCESS){
            list = l;
        }
        else{
            list = new LinkedList<Voyageurs>();
        }
    }
    
    public LinkedList<Voyageurs> getList(){
        return list;
    }
}
