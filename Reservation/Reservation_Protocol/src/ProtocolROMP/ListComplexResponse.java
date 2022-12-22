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
public class ListComplexResponse extends Response{

    private final LinkedList<Complexes> list;
    
    public ListComplexResponse(int c, String m){
        super(c,m);
        list = null;
    }
    
    public ListComplexResponse(int c, String m, LinkedList<Complexes> l){
        super(c,m);
        
        if(c == SUCCESS){
            list = l;
        }
        else{
            list = new LinkedList<Complexes>();
        }
    }
    
    public LinkedList<Complexes> getList(){
        return list;
    }
}
