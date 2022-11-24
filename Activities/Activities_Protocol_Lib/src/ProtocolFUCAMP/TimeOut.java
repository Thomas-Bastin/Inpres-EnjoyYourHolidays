/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolFUCAMP;

import java.io.Serializable;
import networklib.Server.Response;

/**
 *
 * @author Arkios
 */
public class TimeOut implements Response, Serializable{
    private int Code;
    private String Msg;
    
    TimeOut(int c, String m){
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
