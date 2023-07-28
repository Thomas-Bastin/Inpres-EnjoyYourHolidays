package SPAYMAP;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;

/**
 *
 * @author Thomas
 */
public abstract class Response implements networklib.Server.Response, Serializable {
    public static final int SUCCESS = 200;
    public static final int BADDB = 403;
    public static final int UNKOWN = 404;
    
    private final int Code;
    private final String Msg;
    
    Response(int c, String m){
        Code = c;
        Msg = m;
    }
    
    @Override
    public int getCode() {
        return Code;
    }

    @Override
    public String getMessage() {
        return Msg;
    }
}
