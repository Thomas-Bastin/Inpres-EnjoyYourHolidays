/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networklib.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
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
        ssock = new ServerSocket(port);
        tasks = s; 
        Log = l;
        ThreadsCount = countthreads;
        
        threads = new LinkedList<>();
    }
    
    
    public void Shutdown() throws IOException{
        
        for(ThreadSocketService th : threads){
            th.Shutdown();
        }
        ssock.close();
        
        Log.Trace("Serveur#Shutdown#thread serveur");
        super.stop();
    }

    @Override
    public void run() {
        //Create Thread to Accept new Connexion
        for(int i = 0 ; i < ThreadsCount ; i++){
            ThreadSocketService th = new ThreadSocketService(tasks, "Thread n°" + String.valueOf(i));
            th.start();
            
            threads.add(th);
        }
        
        
        Socket serviceSock;
        //le Is Interrupted() permet de quitter la boucle du thread en cas d'interruption.
        while(!isInterrupted()){
            try {    
                System.out.println("************ Serveur en attente");
                serviceSock = ssock.accept(); 
                Log.Trace(serviceSock.getRemoteSocketAddress().toString()+"#accept#thread serveur");
                
            } catch (IOException ex) {
                Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            
            
            ObjectInputStream ois;
            Requete req;
            
            //Récupération de l'objet qui compose la requète de nouvelle connexion:
            try {
                ois = new ObjectInputStream(serviceSock.getInputStream());
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
                //On ajoute la nouvelle Connexion dans la liste des requètes a traitée
                tasks.recordTask(todo);
                System.out.println("Nouvelle Connexion mise dans la File d'attente.");
                Log.Trace(serviceSock.getRemoteSocketAddress().toString()+"#Tentative Nouvelle Connexion Réussie#thread serveur");
            }
            else{
                //On ne fait rien
                System.out.println("Nouvelle Connexion mise dans la File d'attente.");
                Log.Trace(serviceSock.getRemoteSocketAddress().toString()+"#Tentative Nouvelle Connexion Annulée#thread serveur");
            } 
        }
    }
}