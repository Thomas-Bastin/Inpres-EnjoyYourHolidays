/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolROMP;

import ReservationDataLayer.db;
import ReservationDataLayer.entities.Chambres;
import ReservationDataLayer.entities.Reservationchambre;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
import java.sql.SQLException;
import networklib.Server.ServerConsole;

/**
 *
 * @author Arkios
 */
public class HowMuchToPayRequest extends Request{
    private final Chambres room;
    private final Reservationchambre reserv;

    public HowMuchToPayRequest(Chambres roo, Reservationchambre r) {
        room = roo;
        reserv = r;
    }

    @Override
    public void Task(Socket sock, ServerConsole log, ObjectOutputStream oos) throws IOException, ClassNotFoundException {
        try {
            double ret = db.howMuchToPay(room, reserv);
            
            oos.writeObject(new HowMuchToPayResponse(HowMuchToPayResponse.SUCCESS, "HowMuchToPayRequest Success", ret));
            log.Trace(sock.getRemoteSocketAddress().toString() + "# HowMuchToPayRequest SUCCESS #" + Thread.currentThread().getName());
            
        } catch (SQLException ex) {
            log.Trace(sock.getRemoteSocketAddress().toString() + "# HowMuchToPayRequest SQL Error: " + ex.getErrorCode() + " #" + Thread.currentThread().getName());
            oos.writeObject(new HowMuchToPayResponse(HowMuchToPayResponse.BADDB, ex.getMessage()));
        } catch(Exception ex){
            log.Trace(sock.getRemoteSocketAddress().toString() + "# HowMuchToPayRequest Unkown Error: " + ex.getMessage() + " #" + Thread.currentThread().getName());
            oos.writeObject(new HowMuchToPayResponse(HowMuchToPayResponse.UNKOWN, ex.getMessage()));
        }
    }
}
