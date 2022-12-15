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
public class BookRoomRequest extends Request {
    private final Chambres chambre;
    private final Date dateBeg;
    private final int nights;
    private final Voyageurs clientRef;
    
    
    public BookRoomRequest(Chambres ch, Date dateBeg, int Nights, Voyageurs ClientRef){
        this.chambre = ch;
        this.dateBeg = dateBeg;
        this.nights = Nights;
        this.clientRef = ClientRef;
    }
    
    @Override
    public void Task(Socket sock, ServerConsole log) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream( sock.getOutputStream());
        
        LinkedList list = null;
        int numCh = -1;
        float prix = -1;
        
        try {
            //Try Cancel the Room
            int ret = db.BookRoom(chambre, dateBeg, nights, clientRef);
            System.out.println("SQL Insert: "+ret);
            oos.writeObject(new BookRoomResponse(BookRoomResponse.SUCCESS, "BookRoom Success", numCh, prix));
            log.Trace(sock.getRemoteSocketAddress().toString() + "# BookRoomResponse SUCCESS #" + Thread.currentThread().getName());
            
        } catch (SQLException ex) {
            log.Trace(sock.getRemoteSocketAddress().toString() + "# BookRoomResponse SQL Error: " + ex.getErrorCode() + " #" + Thread.currentThread().getName());
            oos.writeObject(new BookRoomResponse(BookRoomResponse.BADDB, ex.getMessage()));
        } catch(Exception ex){
            if(ex.getMessage().equalsIgnoreCase("DateInvalid")){
                log.Trace(sock.getRemoteSocketAddress().toString() + "# BookRoomResponse DateInvalid Error: " + ex.getMessage() + " #" + Thread.currentThread().getName());
                oos.writeObject(new BookRoomResponse(BookRoomResponse.INVALIDDATE, "Une autre réservation est déjà en cours aux dates choisies"));
                return;
            }
            
            log.Trace(sock.getRemoteSocketAddress().toString() + "# BookRoomResponse Unkown Error: " + ex.getMessage() + " #" + Thread.currentThread().getName());
            oos.writeObject(new BookRoomResponse(BookRoomResponse.UNKOWN, ex.getMessage()));
        }
    }
}
