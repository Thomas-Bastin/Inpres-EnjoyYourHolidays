/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolROMP;

import ReservationDataLayer.db;
import ReservationDataLayer.entities.Chambres;
import java.io.IOException;
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

    public ListRoomRequest() {}
    
    
    @Override
    public void Task(Socket sock, ServerConsole log) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
        LinkedList<Chambres> l = new LinkedList<Chambres>();
        try{
            l = db.getRooms();
            
            if(l != null){
                oos.writeObject(new ListReservationRoomResponse(
                    ListReservationRoomResponse.SUCCESS, "ListRoom: " + l.size(), l
                ));
            }
            
            throw new Exception("La liste récupéré est égal à NULL");
        }
        catch (SQLException ex) {
            log.Trace(sock.getRemoteSocketAddress().toString() + "# ListRoom SQL Error: " + ex.getErrorCode() + " #" + Thread.currentThread().getName());
            oos.writeObject(new ListReservationRoomResponse(Response.BADDB, ex.getMessage(), l));
        } catch(Exception ex){
            log.Trace(sock.getRemoteSocketAddress().toString() + "# ListRoom Unkown Error: " + ex.getMessage() + " #" + Thread.currentThread().getName());
            oos.writeObject(new ListReservationRoomResponse(Response.UNKOWN, ex.getMessage(),l));
        }
    }
}
