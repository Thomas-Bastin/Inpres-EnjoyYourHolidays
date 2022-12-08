package ServBuisness;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ProtocolROMP.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import networklib.Server.Requestv2;
import networklib.Server.ServerBuisness;
import networklib.Server.ServerConsole;

/**
 *
 * @author Thomas
 */
public class MainServBuisness implements ServerBuisness {

    private boolean isLogged;

    private ObjectInputStream ios;
    private ObjectOutputStream oos;

    public MainServBuisness() {
        this.isLogged = false;
    }

    public void Task(Socket sock, ServerConsole log) throws IOException, ClassNotFoundException, java.net.SocketException {
        ios = new ObjectInputStream(sock.getInputStream());
        oos = new ObjectOutputStream(sock.getOutputStream());
        
        
        while (!sock.isInputShutdown() && !sock.isOutputShutdown()) {
            Object r = ios.readObject();
            if (!(r instanceof ProtocolROMP.Request)) {
                isLogged = false;
                return;
            }
            Requestv2 req = (Requestv2) r;

            if (req instanceof ProtocolROMP.TimeOut) {
                isLogged = false;
                return;
            }
            
            if (!isLogged) {
                if (req instanceof LoginRequest) {
                    LoginRequest lr = (LoginRequest) req;
                    //Execute Login Task
                    lr.Task(sock, log, oos);
                    isLogged = lr.Logged;
                }
            } else {
                req.Task(sock, log, oos);
            }
        }
    }

    
    
    @Override
    public Runnable createRunnable(Socket s, ServerConsole cs) {
        return () -> {
            System.out.println(Thread.currentThread().getName() + ": Execution d'une Tache pour " + s.getRemoteSocketAddress().toString());
            try {
                s.getOutputStream().flush();
                Task(s, cs);
            } catch (IOException | ClassNotFoundException ex) {
                if(ex instanceof java.io.EOFException) return;
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
    }
}
