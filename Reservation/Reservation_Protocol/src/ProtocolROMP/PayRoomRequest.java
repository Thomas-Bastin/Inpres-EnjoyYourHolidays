/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolROMP;

import ReservationDataLayer.db;
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
public class PayRoomRequest extends Request {
    private final int numChambre;
    private final Voyageurs client;
    private final String creditCard;

    public PayRoomRequest(int numChambre, Voyageurs clientName, String creditCard) {
        this.numChambre = numChambre;
        this.client = clientName;
        this.creditCard = creditCard;
    }

    public int getNumChambre() {
        return numChambre;
    }

    public Voyageurs getClientName() {
        return client;
    }

    public String getCreditCard() {
        return creditCard;
    }
    
    
    @Override
    public void Task(Socket sock, ServerConsole log, ObjectOutputStream oos) throws IOException, ClassNotFoundException {
        LinkedList list = null;
        
        try {
            //Try Cancel the Room
            boolean ret = db.PayRoom();
            
            
            if(ret){
                oos.writeObject(new PayRoomResponse(PayRoomResponse.SUCCESS, "PayRoom Success"));
                log.Trace(sock.getRemoteSocketAddress().toString() + "# PayRoomResponse SUCCESS #" + Thread.currentThread().getName());
            }   
    
            throw new Exception("Erreur");
        } catch (SQLException ex) {
            log.Trace(sock.getRemoteSocketAddress().toString() + "# PayRoomResponse SQL Error: " + ex.getErrorCode() + " #" + Thread.currentThread().getName());
            oos.writeObject(new PayRoomResponse(PayRoomResponse.BADDB, ex.getMessage()));
        } catch(Exception ex){
            log.Trace(sock.getRemoteSocketAddress().toString() + "# PayRoomResponse Unkown Error: " + ex.getMessage() + " #" + Thread.currentThread().getName());
            oos.writeObject(new PayRoomResponse(PayRoomResponse.UNKOWN, ex.getMessage()));
        }
    }
}
