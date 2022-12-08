/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolROMP;

import ReservationDataLayer.db;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import networklib.Server.ServerConsole;

/**
 *
 * @author Thomas
 */
public class LoginRequest extends Request {
    
    public boolean Logged;
    
    private final String login;
    private final String hash;
    
    public LoginRequest(String login, String hash){
        this.login = login;
        this.hash = hash;
        Logged = false;
    }
    
    @Override
    public void Task(Socket sock, ServerConsole log, ObjectOutputStream oos) throws IOException {
        int count = 0;
        LoginResponse rep;
        oos.flush();
        
        log.Trace(sock.getRemoteSocketAddress().toString() + "#Login "+ login +"#" + Thread.currentThread().getName());
        
        //Recuperation du hash enregistrer en db
        String hashdb = null;
        try {
            hashdb = db.getPassword(login);
        } catch (Exception ex) {
            
            if(ex instanceof SQLException){
                log.Trace(sock.getRemoteSocketAddress().toString() + "#Login " + login + ": " + ex.getMessage() + "#" + Thread.currentThread().getName());
                oos.writeObject((Object) new LoginResponse(LoginResponse.BADDB, ex.getMessage()));
                return;
            }
            if(ex.getMessage().equals("badlogin")){ 
                log.Trace(sock.getRemoteSocketAddress().toString() + "#Login "+ login +": Unkown Email#" + Thread.currentThread().getName());
                oos.writeObject((Object) new LoginResponse(LoginResponse.BADMAIL, "Unkown Email"));
                return;
            }
            if(ex.getMessage().equals("toomanylogin")){
                log.Trace(sock.getRemoteSocketAddress().toString() + "#Login "+ login +": Too Many Email#" + Thread.currentThread().getName());
                oos.writeObject((Object) new LoginResponse(LoginResponse.BADMAIL, "Too Many Email"));
                return;
            } 
        }
        
        System.err.println("hash App: " + hash);
        System.err.println("hash DB: " + hashdb);
        
        //Check Login & Password on SGBD
        if(hash.equals(hashdb)){
            log.Trace(sock.getRemoteSocketAddress().toString() + "#Login " + login + ": Success#" + Thread.currentThread().getName());
            oos.writeObject((Object) new LoginResponse(LoginResponse.SUCCESS, "Login Success"));
            Logged = true;
        }else{
            log.Trace(sock.getRemoteSocketAddress().toString() + "#Login " + login + ": BadPassword#" + Thread.currentThread().getName());
            oos.writeObject((Object) new LoginResponse(LoginResponse.BADPSWD, "Bad Password"));
        }
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
