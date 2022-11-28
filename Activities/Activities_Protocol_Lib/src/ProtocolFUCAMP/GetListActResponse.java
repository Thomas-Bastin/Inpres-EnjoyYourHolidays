/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolFUCAMP;

import java.io.Serializable;
import java.util.LinkedList;
import networklib.Server.Response;

/**
 *
 * @author Thomas
 */
public class GetListActResponse implements Response, Serializable{
    public static final int SUCCESS = 200;
    public static final int BADDB = 403;
    public static final int UNKOWN = 404;
    
    public final int code;
    public final String message;
    public final LinkedList list;
    
    GetListActResponse(int c, String m){
        code = c;
        message = m;    
        list = new LinkedList();
    }
    
    GetListActResponse(int c, String m, LinkedList l){
        code = c;
        message = m;
        
        if(c == SUCCESS){
            list = l;
        }
        else{
            list = new LinkedList();
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
    
    public LinkedList getList(){
        return list;
    }
}
