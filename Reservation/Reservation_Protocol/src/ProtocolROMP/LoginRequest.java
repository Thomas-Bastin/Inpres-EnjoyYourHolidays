/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolROMP;

import DataBase.db;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import networklib.Server.ServerConsole;

/**
 *
 * @author Arkios
 */
public class LoginRequest extends Request {
    private final String login;
    private final String hash;
    
    public LoginRequest(String l, String p){
        login = l;
        hash = p;
    }
    
    //Sera executé côté serveur.
    @Override
    public void Task(Socket sock, ServerConsole log) throws IOException{
        int count = 0;
        LoginResponse rep;
        
        ObjectOutputStream oos = new ObjectOutputStream( sock.getOutputStream());
        
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
        }else{
            log.Trace(sock.getRemoteSocketAddress().toString() + "#Login " + login + ": BadPassword#" + Thread.currentThread().getName());
            oos.writeObject((Object) new LoginResponse(LoginResponse.BADPSWD, "Bad Password"));
        }
    }
}
