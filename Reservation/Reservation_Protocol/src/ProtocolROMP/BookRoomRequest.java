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
public class BookRoomRequest extends Request {
    private final String category;
    private final String type;
    private final Date dateBeg;
    private final int nights;
    private final Voyageurs clientRef;
    
    
    public BookRoomRequest(String Category, String Type, Date dateBeg, int Nights, Voyageurs ClientRef){
        this.category = Category;
        this.type = Type;
        this.dateBeg = dateBeg;
        this.nights = Nights;
        this.clientRef = ClientRef;
    }
    
    @Override
    public void Task(Socket sock, ServerConsole log, ObjectOutputStream oos) throws IOException {
        LinkedList list = null;
        int numCh = -1;
        float prix = -1;
        
        try {
            //Try Cancel the Room
            boolean ret = db.BookRoom();
            
            if(ret){
                oos.writeObject(new BookRoomResponse(BookRoomResponse.SUCCESS, "BookRoom Success", numCh, prix));
                log.Trace(sock.getRemoteSocketAddress().toString() + "# BookRoomResponse SUCCESS #" + Thread.currentThread().getName());
            }
            else{
                oos.writeObject(new BookRoomResponse(BookRoomResponse.KO, "Can't Book this room", numCh, prix));
                log.Trace(sock.getRemoteSocketAddress().toString() + "# BookRoomResponse KO #" + Thread.currentThread().getName());
            }            
    
            throw new Exception("Erreur");
        } catch (SQLException ex) {
            log.Trace(sock.getRemoteSocketAddress().toString() + "# BookRoomResponse SQL Error: " + ex.getErrorCode() + " #" + Thread.currentThread().getName());
            oos.writeObject(new BookRoomResponse(BookRoomResponse.BADDB, ex.getMessage()));
        } catch(Exception ex){
            log.Trace(sock.getRemoteSocketAddress().toString() + "# BookRoomResponse Unkown Error: " + ex.getMessage() + " #" + Thread.currentThread().getName());
            oos.writeObject(new BookRoomResponse(BookRoomResponse.UNKOWN, ex.getMessage()));
        }
    }
}
