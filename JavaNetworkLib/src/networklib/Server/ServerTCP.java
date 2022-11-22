/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networklib.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Thomas
 */
public class ServerTCP implements Runnable {
    private ServerSocket ssock;
    
    private Vector<ThreadSocketService> threads;
    
    //Manage Thread or Socket ?
    private Vector<Socket> services;
    private Iterator beg;
    private Iterator end;
    
    public ServerTCP(int port, int NbreThreads) throws IOException{
        ssock = new ServerSocket(port);
        
        //Init des itérateurs
        beg = services.iterator();
        end = services.iterator();
        
        //Create Thread to Accept new Connexion
        for(int i = 0 ; i < NbreThreads ; i++){
            threads.add( new ThreadSocketService(services));
        }        
    }

    @Override
    public void run() {
        //Lancement des Threads
        for(ThreadSocketService th : threads){
            th.start();
        }
        
        
        //Boucle sur Accept qui ajoute des éléments au service
        while(true){
            try {
                services.add( ssock.accept());
            } catch (IOException ex) {
                Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
                
            }
        }
    }
}
