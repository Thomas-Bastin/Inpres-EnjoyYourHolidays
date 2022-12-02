/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolROMP;

import java.io.IOException;
import java.net.Socket;
import networklib.Server.ServerConsole;

/**
 *
 * @author Thomas
 */
public class LoginRequest extends Request {
    
    private final String login;
    private final String hash;
    
    LoginRequest(String login, String hash){
        this.login = login;
        this.hash = hash;
    }
    
    @Override
    protected void Task(Socket sock, ServerConsole log) throws IOException {
        //Todo
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @return the hash
     */
    public String getHash() {
        return hash;
    }
}
