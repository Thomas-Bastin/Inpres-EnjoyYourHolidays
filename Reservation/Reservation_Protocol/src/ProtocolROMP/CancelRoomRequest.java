/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolROMP;

import ReservationDataLayer.db;
import ReservationDataLayer.entities.Chambres;
import ReservationDataLayer.entities.Voyageurs;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.util.LinkedList;
import networklib.Server.ServerConsole;

/**
 *
 * @author Thomas
 */
public class CancelRoomRequest extends Request{
    private final Chambres room;
    private final Date dateBeg;

    public CancelRoomRequest(Chambres roo, Date dBeg) {
        dateBeg = dBeg;
        room = roo;
    }    
    
    
    public void Task(Socket sock, ServerConsole log, ObjectOutputStream oos) throws IOException, ClassNotFoundException {
        LinkedList list = null;
        Date datedep;
        
        try {
            //Try Cancel the Room
            boolean ret = db.CancelRoom(room, dateBeg);
            datedep = (Date) Date.from(Instant.now());
            
            
            if(ret){
                oos.writeObject(new CancelRoomResponse(CancelRoomResponse.SUCCESS, "CancelRoom Success", datedep));
                log.Trace(sock.getRemoteSocketAddress().toString() + "# CancelRoomResponse SUCCESS #" + Thread.currentThread().getName());
            }
            else{
                oos.writeObject(new CancelRoomResponse(CancelRoomResponse.KO, "Can't Cancel this room", datedep));
                log.Trace(sock.getRemoteSocketAddress().toString() + "# CancelRoomResponse KO #" + Thread.currentThread().getName());
            }            
    
            throw new Exception("Erreur");
        } catch (SQLException ex) {
            log.Trace(sock.getRemoteSocketAddress().toString() + "# CancelRoomResponse SQL Error: " + ex.getErrorCode() + " #" + Thread.currentThread().getName());
            oos.writeObject(new CancelRoomResponse(CancelRoomResponse.BADDB, ex.getMessage(), null));
        } catch(Exception ex){
            log.Trace(sock.getRemoteSocketAddress().toString() + "# CancelRoomResponse Unkown Error: " + ex.getMessage() + " #" + Thread.currentThread().getName());
            oos.writeObject(new CancelRoomResponse(CancelRoomResponse.UNKOWN, ex.getMessage(), null));
        }
    }
}
