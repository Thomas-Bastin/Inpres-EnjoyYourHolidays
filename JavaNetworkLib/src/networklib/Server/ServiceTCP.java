/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networklib.Server;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Thomas
 */
public interface ServiceTCP {
    public Socket GetSocket();
    public void SetSocket(Socket s);
    
    public int GetPort();
    
    public String receiveMessage();
    public void sendMessage(String msg);
    
    public Object receiveObject();
    public void sendObject();
}
