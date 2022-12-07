/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolROMP;

import ReservationDataLayer.db;
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
public class ListReservationRoomRequest extends Request {
    
    ListReservationRoomRequest(){}
    
    @Override
    public void Task(Socket sock, ServerConsole log) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
        LinkedList l = new LinkedList<>();
        try{
            l = db.getReservationRoom();
            
            if(l != null){
                oos.writeObject(new ListReservationRoomResponse(
                    ListReservationRoomResponse.SUCCESS, "Reservation Reussie", l
                ));
            }
            
            throw new Exception("La liste récupéré est égal à NULL");
        }
        catch (SQLException ex) {
            log.Trace(sock.getRemoteSocketAddress().toString() + "# ListReservationRoom SQL Error: " + ex.getErrorCode() + " #" + Thread.currentThread().getName());
            oos.writeObject(new ListReservationRoomResponse(Response.BADDB, ex.getMessage(), l));
        } catch(Exception ex){
            log.Trace(sock.getRemoteSocketAddress().toString() + "# ListReservationRoom Unkown Error: " + ex.getMessage() + " #" + Thread.currentThread().getName());
            oos.writeObject(new ListReservationRoomResponse(Response.UNKOWN, ex.getMessage(),l));
        }
    }
}
