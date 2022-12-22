/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolROMP;

import DataBase.db;
import Entities.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.LinkedList;
import networklib.Server.ServerConsole;

/**
 *
 * @author Thomas
 */
public class ListComplexRequest extends Request{
    
    public ListComplexRequest(){}
    
    @Override
    public void Task(Socket sock, ServerConsole log) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream( sock.getOutputStream());
        LinkedList<Complexes> list = null;
        
        try {
            list = db.getComplex();
            
            if(list != null){
                oos.writeObject(new ListComplexResponse(ListComplexResponse.SUCCESS, "row: " + list.size(), list));
                log.Trace(sock.getRemoteSocketAddress().toString() + "# ListComplexRequest Sended #" + Thread.currentThread().getName());
                return;
            }
            
            throw new Exception("La liste récupéré est égal à NULL");
        } catch (SQLException ex) {
            log.Trace(sock.getRemoteSocketAddress().toString() + "# ListComplexRequest SQL Error: " + ex.getErrorCode() + " #" + Thread.currentThread().getName());
            oos.writeObject(new ListComplexResponse(ListComplexResponse.BADDB, ex.getMessage()));
        } catch(Exception ex){
            log.Trace(sock.getRemoteSocketAddress().toString() + "# ListComplexRequest Unkown Error: " + ex.getMessage() + " #" + Thread.currentThread().getName());
            oos.writeObject(new ListComplexResponse(ListComplexResponse.UNKOWN, ex.getMessage()));
        }
    }
}

