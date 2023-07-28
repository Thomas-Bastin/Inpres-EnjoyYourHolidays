package CCAPP;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import networklib.Server.ServerConsole;

/**
 *
 * @author Thomas
 */
public class TimeOut extends Request {

    /**
     *
     * @param sock
     * @param log
     * @throws IOException
     * @throws ClassNotFoundException
     */

    @Override
    public void Task(Socket sock, ServerConsole log) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
