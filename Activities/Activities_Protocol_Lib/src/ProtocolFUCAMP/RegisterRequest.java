/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolFUCAMP;

import ActivitiesDataLayer.db;
import ActivitiesDataLayer.entities.Activities;
import ActivitiesDataLayer.entities.Voyageurs;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import networklib.Server.Request;
import networklib.Server.ServerConsole;

/**
 *
 * @author Thomas
 */
public class RegisterRequest implements Request, Serializable{
    
    Activities activities;
    Voyageurs voyageurs;
    boolean isPayed;
    
    public RegisterRequest(Activities act, Voyageurs v, boolean isp){
        activities = act;
        voyageurs = v;
        isPayed = isp;
    }
    
    @Override
    public Runnable createRunnable(Socket s, ServerConsole cs) {
        return () -> {
            System.out.println( Thread.currentThread().getName() + ": Execution d'une Tache pour " +  s.getRemoteSocketAddress().toString());
            try {
                s.getOutputStream().flush();
                GetListActTASK(s,cs);
            } catch (IOException ex) {
                Logger.getLogger(RegisterRequest.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
    }
    
    private void GetListActTASK(Socket sock, ServerConsole log) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream( sock.getOutputStream());
        if(activities == null){
            log.Trace(sock.getRemoteSocketAddress().toString() + "# Register error: unkown activities#" + Thread.currentThread().getName());
            oos.writeObject(new RegisterResponse(RegisterResponse.UNKOWNACTIVITIES, "Activitiée inconnue"));
            return;
        }
        
        if(voyageurs == null){
            log.Trace(sock.getRemoteSocketAddress().toString() + "# Register error: unkown Client#" + Thread.currentThread().getName());
            oos.writeObject(new RegisterResponse(RegisterResponse.UNKNOWNCLIENT, "Voyageur inconnu"));
            return;
        }
        
        
        try {
            db.RegisterToActivities(activities, voyageurs, isPayed);
            oos.writeObject(new RegisterResponse(RegisterResponse.SUCCESS, "Client inscrit"));
            
        } catch (SQLException ex) {
            log.Trace(sock.getRemoteSocketAddress().toString() + "# Register SQL Error: " + ex.getErrorCode() + " #" + Thread.currentThread().getName());
            oos.writeObject(new RegisterResponse(RegisterResponse.BADDB, ex.getMessage()));
        } catch(Exception ex){
            log.Trace(sock.getRemoteSocketAddress().toString() + "# Register Unkown Error: " + ex.getMessage() + " #" + Thread.currentThread().getName());
            oos.writeObject(new RegisterResponse(RegisterResponse.UNKOWN, ex.getMessage()));
        }
    }
}
