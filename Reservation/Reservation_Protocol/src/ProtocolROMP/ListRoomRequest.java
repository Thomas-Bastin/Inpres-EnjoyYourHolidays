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
public class ListRoomRequest extends Request {
    private final Complexes selectComp;
    
    public ListRoomRequest(Complexes c) {
        selectComp = c;
    }
    
    
    @Override
    public void Task(Socket sock, ServerConsole log) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream( sock.getOutputStream());
        
        LinkedList<Chambres> l = new LinkedList<Chambres>();
        try{
            l = db.getRooms(selectComp);
            
            if(l != null){
                oos.writeObject(new ListRoomResponse(
                    ListRoomResponse.SUCCESS, "ListRoom: " + l.size(), l
                ));
            }
            
            l = new LinkedList<Chambres>();
        }
        catch (SQLException ex) {
            log.Trace(sock.getRemoteSocketAddress().toString() + "# ListRoom SQL Error: " + ex.getErrorCode() + " #" + Thread.currentThread().getName());
            oos.writeObject(new ListRoomResponse(Response.BADDB, ex.getMessage(), l));
        } catch(Exception ex){
            log.Trace(sock.getRemoteSocketAddress().toString() + "# ListRoom Unkown Error: " + ex.getMessage() + " #" + Thread.currentThread().getName());
            oos.writeObject(new ListRoomResponse(Response.UNKOWN, ex.getMessage(),l));
        }
    }
}
