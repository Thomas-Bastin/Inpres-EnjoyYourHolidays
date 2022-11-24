/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networklib.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Thomas
 */
public class ServerTCP extends Thread {
    private final ServerSocket ssock;
    private ServerConsole Log;
    private TasksSource tasks;
    
    private final int ThreadsCount;
    private LinkedList<ThreadSocketService> threads;
    
    
    public ServerTCP(int port, TasksSource s, ServerConsole l, int countthreads) throws IOException{
        ssock = new ServerSocket();
        ssock.bind(new InetSocketAddress(InetAddress.getByName("0.0.0.0"),port));
        
        tasks = s; 
        Log = l;
        ThreadsCount = countthreads;
        
        threads = new LinkedList<>();
    }
    
    
    public void Shutdown() throws IOException{
        //A l'avenir il faudra que le serveur envoi un message de fermeture au client
        for(ThreadSocketService th : threads){
            th.interrupt();
            th.Shutdown();
        }
        Log.Trace("serveur#fermeture des threads service#main");
        
        ssock.close();
        Log.Trace("serveur#fermeture listen socket#main");
        
        Log.Trace("serveur#fully shutdown#main");
        System.out.println("************ Fin exécution Serveur");
    }

    @Override
    public void run() {
        //Create Thread to Accept new Connexion
        for(int i = 0 ; i < ThreadsCount ; i++){
            ThreadSocketService th = new ThreadSocketService(tasks, "Thread n°" + String.valueOf(i));
            th.setName("Serveur-TCP Thread Service n°" + String.valueOf(i));
            th.start();
            
            threads.add(th);
        }
        Log.Trace("serveur#pool thread créer#main");
        ServerTCP.listDebugThreads();
        
        Socket serviceSock;
        //le Is Interrupted() permet de quitter la boucle du thread en cas d'interruption.
        while(!isInterrupted()){
            try {
                System.out.println("************ Serveur en attente");
                Log.Trace("serveur#attente accept#main");
                serviceSock = ssock.accept(); 
                Log.Trace(serviceSock.getRemoteSocketAddress().toString()+"#accept success#thread serveur");
                
            } catch (IOException ex) {
                Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            
            
            ObjectInputStream ois;
            Requete req;
            
            //Récupération de l'objet qui compose la requète de nouvelle connexion:
            try {
                ois = new ObjectInputStream(serviceSock.getInputStream());
                
                System.out.println("Serveur: AttenteObjet en entrée");
                
                req = (Requete) ois.readObject();
                System.out.println("Requete lue par le serveur, instance de " + req.getClass().getName());
                
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            
            //Création d'un Runnable sur base de la requete de nouvelle connexion récupéré a l'instant.
            Runnable todo = req.createRunnable(serviceSock, Log);
            if (todo != null)
            {
                if(!(todo instanceof Requete)){
                    Log.Trace(serviceSock.getRemoteSocketAddress().toString()+"#Tentative de Hack: Runnable Inconnus#thread serveur");
                }
                else{
                    //On ajoute la nouvelle Connexion dans la liste des requètes a traitée
                    tasks.recordTask(todo);
                    System.out.println("Nouvelle Connexion mise dans la File d'attente.");
                    Log.Trace(serviceSock.getRemoteSocketAddress().toString()+"#Tentative Nouvelle Connexion Réussie#thread serveur");
                }
            }
            else{
                //On ne fait rien
                System.out.println("Nouvelle Connexion mise dans la File d'attente.");
                Log.Trace(serviceSock.getRemoteSocketAddress().toString()+"#Tentative Nouvelle Connexion Annulée#thread serveur");
            } 
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

}