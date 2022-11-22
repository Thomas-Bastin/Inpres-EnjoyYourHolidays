/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networklib;

import java.net.Socket;
import java.util.Hashtable;
import networklib.Server.Requete;
import networklib.Server.ServerConsole;

/**
 *
 * @author Arkios
 */
class RequeteSUM implements Requete {
    public static int CONNECT = 1;
    
    
    private int type;
    private String message;
    private Socket socketClient;
    
    public RequeteSUM(int t, String chu, Socket s)
    {
        type = t; 
        message = chu;
        socketClient =s;
    } 
    
    @Override
    public Runnable createRunnable(Socket s, ServerConsole cs) {
        if (type==CONNECT) return new Runnable()
        { 
            @Override
            public void run()
            {
                //Ceci sera exécuté sur le serveur en temps voulu.
                
            }
        };
        else return null; 
    }
}
