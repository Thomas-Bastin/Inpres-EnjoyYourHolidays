/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolFUCAMP;

import ActivitiesDataLayer.db;
import ActivitiesDataLayer.entities.Activities;
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
public class GetListPartRequest implements Request, Serializable{
    private final Activities activities;
    
    public GetListPartRequest(Activities act){
        activities = act;
    }
    
    @Override
    public Runnable createRunnable(Socket s, ServerConsole cs) {
        return () -> {
            System.out.println( Thread.currentThread().getName() + ": Execution d'une Tache pour " +  s.getRemoteSocketAddress().toString());
            try {
                s.getOutputStream().flush();
                task(s,cs);
            } catch (IOException ex) {
                Logger.getLogger(GetListPartRequest.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
    }
    
    private void task(Socket sock, ServerConsole log) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream( sock.getOutputStream());
        LinkedList list = null;
        
        try {
            list = db.getRegisteredClients(activities);
            if(list != null){
                oos.writeObject(new GetListPartResponse(GetListPartResponse.SUCCESS, "row: " + list.size(), list));
                return;
            }
            throw new Exception("La liste récupéré est égal à NULL");
        } catch (SQLException ex) {
            log.Trace(sock.getRemoteSocketAddress().toString() + "# GetListPART SQL Error: " + ex.getErrorCode() + " #" + Thread.currentThread().getName());
            oos.writeObject(new GetListPartResponse(GetListPartResponse.BADDB, ex.getMessage()));
        } catch(Exception ex){
            log.Trace(sock.getRemoteSocketAddress().toString() + "# GetListPART Unkown Error: " + ex.getMessage() + " #" + Thread.currentThread().getName());
            oos.writeObject(new GetListPartResponse(GetListPartResponse.UNKOWN, ex.getMessage()));
        }
    }
}
