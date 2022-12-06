/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolFUCAMP;

import ActivitiesDataLayer.db;
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
public class GetListClientRequest implements Request, Serializable{
    
    public GetListClientRequest(){}
    
    @Override
    public Runnable createRunnable(Socket s, ServerConsole cs) {
        return () -> {
            System.out.println( Thread.currentThread().getName() + ": Execution d'une Tache pour " +  s.getRemoteSocketAddress().toString());
            try {
                s.getOutputStream().flush();
                task(s,cs);
            } catch (IOException ex) {
                Logger.getLogger(GetListClientRequest.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
    }
    
    private void task(Socket sock, ServerConsole log) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream( sock.getOutputStream());
        LinkedList list = null;
        
        try {
            list = db.getClients();
            if(list != null){
                oos.writeObject(new GetListClientResponse(GetListClientResponse.SUCCESS, "row: " + list.size(), list));
                log.Trace(sock.getRemoteSocketAddress().toString() + "# GetListClientRequest #" + Thread.currentThread().getName());
                return;
            }
            
            throw new Exception("La liste récupéré est égal à NULL");
        } catch (SQLException ex) {
            log.Trace(sock.getRemoteSocketAddress().toString() + "# GetListCLIENT SQL Error: " + ex.getErrorCode() + " #" + Thread.currentThread().getName());
            oos.writeObject(new GetListClientResponse(GetListClientResponse.BADDB, ex.getMessage()));
        } catch(Exception ex){
            log.Trace(sock.getRemoteSocketAddress().toString() + "# GetListCLIENT Unkown Error: " + ex.getMessage() + " #" + Thread.currentThread().getName());
            oos.writeObject(new GetListClientResponse(GetListClientResponse.UNKOWN, ex.getMessage()));
        }
    }
}
