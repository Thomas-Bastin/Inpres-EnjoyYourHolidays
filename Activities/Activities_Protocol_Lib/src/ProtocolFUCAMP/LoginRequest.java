/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolFUCAMP;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import networklib.Server.Requete;
import networklib.Server.ServerConsole;

/**
 *
 * @author Arkios
 */
public class LoginRequest implements Requete, Serializable{
    private String login;
    private String hash;
    
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
        
        //Try Select the hash password based on login.
        count = 1;
        

        if(count == 0){
            log.Trace(sock.getRemoteSocketAddress().toString() + "#Login "+ login +": Unkown Email#" + Thread.currentThread().getName());
            oos.writeObject((Object) new LoginResponse(401, "Unkown Email"));
            oos.flush();
            oos.close();
            return;
        }
        
        

        if(count == 1){
            //Si hash != db.getPassword(login);
            if(!hash.equalsIgnoreCase("900150983CD24FB0D6963F7D28E17F72")){
                log.Trace(sock.getRemoteSocketAddress().toString() + "#Login "+ login +": BadPassword#" + Thread.currentThread().getName());
                oos.writeObject((Object) new LoginResponse(402, "Bad Password"));
                oos.flush();
                oos.close();
                return;
            }
            else{
                log.Trace(sock.getRemoteSocketAddress().toString() + "#Login "+ login +": Success#" + Thread.currentThread().getName());
                oos.writeObject((Object) new LoginResponse(200, "Login Success"));
                oos.flush();
                oos.close();
                return;
            }
        }
        
        
        //To Remove
        if(true) return;
        
        //Si Exception DB:
        log.Trace(sock.getRemoteSocketAddress().toString() + "#Login "+ login +": DB Error#" + Thread.currentThread().getName());
        oos.writeObject((Object) new LoginResponse(403, "DB Error"));
        oos.flush();
        oos.close();
        
        //Si Autre Exception:
        log.Trace(sock.getRemoteSocketAddress().toString() + "#Login "+ login +": Unkown Error#" + Thread.currentThread().getName());
        oos.writeObject((Object) new LoginResponse(404, "Unkown Error"));
        oos.flush();
        oos.close();
        
        
    }
}
