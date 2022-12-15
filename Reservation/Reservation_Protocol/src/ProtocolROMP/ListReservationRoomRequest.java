/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolROMP;

import ReservationDataLayer.db;
import ReservationDataLayer.entities.CalendRow;
import ReservationDataLayer.entities.Chambres;
import ReservationDataLayer.entities.Complexes;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import networklib.Server.ServerConsole;

/**
 *
 * @author Thomas
 */
public class ListReservationRoomRequest extends Request {
    private final Complexes complex;
    private final LocalDate date;
    
    public ListReservationRoomRequest(Complexes cp, LocalDate d) {
        complex = cp;
        date = d;
    }
    
    @Override
    public void Task(Socket sock, ServerConsole log) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream( sock.getOutputStream());
        LinkedList<CalendRow> l = new LinkedList<CalendRow>();
        
        try{
            l = db.getReservationRoom(complex, date);
            
            if(l != null){
                log.Trace(sock.getRemoteSocketAddress().toString() + "# ListReservationRoom Success #" + Thread.currentThread().getName());
                oos.writeObject(new ListReservationRoomResponse(
                    ListReservationRoomResponse.SUCCESS, "Reservation Reussie", l
                ));
                System.err.println("Send Reservation");
            }
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
