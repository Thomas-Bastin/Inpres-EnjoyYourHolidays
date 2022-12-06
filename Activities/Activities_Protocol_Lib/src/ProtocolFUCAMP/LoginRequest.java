/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolFUCAMP;

import ActivitiesDataLayer.db;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import networklib.Server.Login;
import networklib.Server.ServerConsole;
import networklib.Server.Request;

/**
 *
 * @author Arkios
 */
public class LoginRequest implements Request, Serializable{
    private final String login;
    private final String hash;
    
    public LoginRequest(String l, String p){
        login = l;
        hash = p;
    }
    
    @Override
    public Runnable createRunnable(Socket s, ServerConsole cs){
        return new Runnable() {
            @Override
            public void run() {
                System.out.println("Serveur: Execution d'une Tache");
                try {
                    s.getOutputStream().flush();
                    LoginTask(s, cs);
                } catch (IOException ex) {
                    Logger.getLogger(LoginRequest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    }
    
    //Sera executé côté serveur.
    private void LoginTask(Socket sock, ServerConsole log) throws IOException{
        int count = 0;
        LoginResponse rep;
        
        ObjectOutputStream oos = new ObjectOutputStream( sock.getOutputStream());
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
                oos.flush();
                oos.close();
                return;
            }
            if(ex.getMessage().equals("badlogin")){ 
                log.Trace(sock.getRemoteSocketAddress().toString() + "#Login "+ login +": Unkown Email#" + Thread.currentThread().getName());
                oos.writeObject((Object) new LoginResponse(LoginResponse.BADMAIL, "Unkown Email"));
                oos.flush();
                oos.close();
                return;
            }
            if(ex.getMessage().equals("toomanylogin")){
                log.Trace(sock.getRemoteSocketAddress().toString() + "#Login "+ login +": Too Many Email#" + Thread.currentThread().getName());
                oos.writeObject((Object) new LoginResponse(LoginResponse.BADMAIL, "Too Many Email"));
                oos.flush();
                oos.close();
                return;
            } 
        }
        
        System.err.println("hash App: " + hash);
        System.err.println("hash DB: " + hashdb);
        
        //Check Login & Password on SGBD
        if(hash.equals(hashdb)){
            log.Trace(sock.getRemoteSocketAddress().toString() + "#Login " + login + ": Success#" + Thread.currentThread().getName());
            oos.writeObject((Object) new LoginResponse(LoginResponse.SUCCESS, "Login Success"));
            oos.flush();
            oos.close();
        }else{
            log.Trace(sock.getRemoteSocketAddress().toString() + "#Login " + login + ": BadPassword#" + Thread.currentThread().getName());
            oos.writeObject((Object) new LoginResponse(LoginResponse.BADPSWD, "Bad Password"));
            oos.flush();
            oos.close();
        }
    }
}
