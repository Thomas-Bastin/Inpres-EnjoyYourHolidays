/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolROMP;

import DataBase.db;
import Entities.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
import networklib.Server.ServerConsole;

/**
 *
 * @author Thomas
 */
public class PayRoomRequest extends Request {
    private final Chambres room;
    private final Date begDate;
    private final Voyageurs client;
    private final CreditCard creditCard;

    public PayRoomRequest(Chambres ch, Date begd, Voyageurs cl, CreditCard cC) {
        this.room = ch;
        this.begDate = begd;
        this.client = cl;
        this.creditCard = cC;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public Chambres getRoom() {
        return room;
    }

    public Date getBegDate() {
        return begDate;
    }

    public Voyageurs getClient() {
        return client;
    }
    
    
    @Override
    public void Task(Socket sock, ServerConsole log) throws IOException, ClassNotFoundException {
        //TODO
        ObjectOutputStream oos = new ObjectOutputStream( sock.getOutputStream());
        oos.writeObject(new PayRoomResponse(PayRoomResponse.SUCCESS, "Success"));
        log.Trace(sock.getRemoteSocketAddress().toString() + "# PayRoomResponse SUCCESS #" + Thread.currentThread().getName());
    }
}
