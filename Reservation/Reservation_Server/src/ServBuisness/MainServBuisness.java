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
import java.util.Set;
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
        
        
        while(!sock.isInputShutdown() && !sock.isOutputShutdown()) {
            oos.flush();
            
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
            
            listDebugThreads();
        }
    }

    
    
    public static void listDebugThreads(){
        Set<Thread> threads = Thread.getAllStackTraces().keySet();
 
        for (Thread t : threads) {
            String name = t.getName();
            Thread.State state = t.getState();
            int priority = t.getPriority();
            String type = t.isDaemon() ? "Daemon" : "Normal";
            
            if(name.contains("Serveur-TCP")){
                System.out.printf("%-20s \t %s \t %d \t %s\n", name, state, priority, type);
            }
        }
    }
    
    
    @Override
    public Runnable createRunnable(Socket s, ServerConsole cs) {
        return () -> {
            try {
                Task(s, cs);
            } catch (IOException | ClassNotFoundException ex) {
                if(ex instanceof java.io.EOFException) return;
                if(ex instanceof java.net.SocketException && ex.getCause().equals("Connection reset")) return;
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
    }
}
