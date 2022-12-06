/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolFUCAMP;

import ActivitiesDataLayer.entities.Activities;
import java.io.Serializable;
import java.util.LinkedList;
import networklib.Server.Response;

/**
 *
 * @author Thomas
 */
public class UnlistResponse implements Response, Serializable{
    public static final int SUCCESS = 200;
    public static final int BADDB = 403;
    public static final int UNKOWN = 404;
    public static final int UNKNOWNCLIENT = 405;
    public static final int UNKOWNACTIVITIES = 406;
    public static final int WRONGDAY = 407;
    
    private final int code;
    private final String message;
    
    UnlistResponse(int c, String m){
        code = c;
        message = m;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
