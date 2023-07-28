package CCAPP;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import networklib.Server.*;

/**
 *
 * @author Thomas
 */
public abstract class Request implements networklib.Server.Request, Serializable {
    @Override
    public Runnable createRunnable(Socket s, ServerConsole cs) {
        return () -> {
            System.out.println( Thread.currentThread().getName() + ": Execution d'une Tache pour " +  s.getRemoteSocketAddress().toString());
            try {
                Task(s,cs);
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
    }
    
    public abstract void Task(Socket sock, ServerConsole log) throws IOException, ClassNotFoundException ;
}