/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolROMP;

import ReservationDataLayer.db;
import ReservationDataLayer.entities.Voyageurs;
import java.io.IOException;
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
    private final int numChambre;
    private final Voyageurs client;

    public CancelRoomRequest(int numChambre, Voyageurs client) {
        this.numChambre = numChambre;
        this.client = client;
    }    
    
    
    @Override
    public void Task(Socket sock, ServerConsole log) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream( sock.getOutputStream());
        LinkedList list = null;
        Date datedep;
        
        try {
            //Try Cancel the Room
            boolean ret = db.CancelRoom();
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
