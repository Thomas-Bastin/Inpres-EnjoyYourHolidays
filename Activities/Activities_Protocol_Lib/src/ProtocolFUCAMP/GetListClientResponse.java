/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolFUCAMP;

import ActivitiesDataLayer.entities.Activities;
import ActivitiesDataLayer.entities.Voyageurs;
import java.io.Serializable;
import java.util.LinkedList;
import networklib.Server.Response;

/**
 *
 * @author Thomas
 */
public class GetListClientResponse implements Response, Serializable{
    public static final int SUCCESS = 200;
    public static final int BADDB = 403;
    public static final int UNKOWN = 404;
    
    private final int code;
    private final String message;
    private final LinkedList<Voyageurs> list;
    
    GetListClientResponse(int c, String m){
        code = c;
        message = m;    
        list = new LinkedList<Voyageurs>();
    }
    
    GetListClientResponse(int c, String m, LinkedList<Voyageurs> l){
        code = c;
        message = m;
        
        if(c == SUCCESS){
            list = l;
        }
        else{
            list = new LinkedList<Voyageurs>();
        }
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
    
    public LinkedList<Voyageurs> getList(){
        return list;
    }
}
