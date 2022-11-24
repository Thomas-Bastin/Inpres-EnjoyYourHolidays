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
import networklib.Server.Requete;
import networklib.Server.ServerConsole;

/**
 *
 * @author Arkios
 */
public class LoginRequest implements Requete, Serializable, Login{
    private final String login;
    private final String hash;
    
    public LoginRequest(String l, String p){
        login = l;
        hash = p;
    }
    
    @Override
    public Runnable createRunnable(Socket s, ServerConsole cs){
        return () -> {
            System.out.println("Serveur: Execution d'une Tache");
            try {
                s.getOutputStream().flush();
                LoginTask(s, cs);
            } catch (IOException ex) {
                Logger.getLogger(LoginRequest.class.getName()).log(Level.SEVERE, null, ex);
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
        
        //Check Login & Password on SGBD
        try {
            if(!db.checkLogin(login)){
                log.Trace(sock.getRemoteSocketAddress().toString() + "#Login "+ login +": Unkown Email#" + Thread.currentThread().getName());
                oos.writeObject((Object) new LoginResponse(LoginResponse.BADMAIL, "Unkown Email"));
                oos.flush();
                oos.close();
                return;
            }
        } catch (SQLException ex) {
            log.Trace(sock.getRemoteSocketAddress().toString() + "#Login " + login + ": DB Error#" + Thread.currentThread().getName());
            oos.writeObject((Object) new LoginResponse(LoginResponse.BADDB, "DB Error"));
            oos.flush();
            oos.close();
            return;
        }
        
        try {
            //Si hash != db.checkPassword(hash);
            if (db.checkPassword(login, hash)) {
                log.Trace(sock.getRemoteSocketAddress().toString() + "#Login " + login + ": BadPassword#" + Thread.currentThread().getName());
                oos.writeObject((Object) new LoginResponse(LoginResponse.BADPSWD, "Bad Password"));
                oos.flush();
                oos.close();
            } else {
                log.Trace(sock.getRemoteSocketAddress().toString() + "#Login " + login + ": Success#" + Thread.currentThread().getName());
                oos.writeObject((Object) new LoginResponse(LoginResponse.SUCCESS, "Login Success"));
                oos.flush();
                oos.close();
            }
        } catch (SQLException ex) {
            //Si Exception DB:
            log.Trace(sock.getRemoteSocketAddress().toString() + "#Login " + login + ": DB Error#" + Thread.currentThread().getName());
            oos.writeObject((Object) new LoginResponse(LoginResponse.BADDB, "DB Error"));
            oos.flush();
            oos.close();
        }
    }
}
