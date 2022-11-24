/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolFUCAMP;

import java.net.Socket;
import networklib.Server.ServerConsole;

/**
 *
 * @author Arkios
 */
public class RequeteFUCAMP implements Runnable{
    Object obj;
    private int type;
    private String chargeUtile;
    private Socket socketClient;
    
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
