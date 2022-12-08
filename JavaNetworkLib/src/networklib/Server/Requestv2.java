/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networklib.Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Arkios
 */
public interface Requestv2 {
    public abstract void Task(Socket sock, ServerConsole log, ObjectOutputStream oos) throws IOException, ClassNotFoundException;
}
