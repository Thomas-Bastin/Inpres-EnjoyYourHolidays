/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networklib.Server;

import java.util.LinkedList;

/**
 *
 * @author Arkios
 */
public class ListTask implements TasksSource {
    private final LinkedList taskList;
    
    public ListTask(){
        taskList = new LinkedList();
    }
    
    @Override
    public synchronized Runnable getTask() throws InterruptedException
    {
        //Récupération d'une nouvelle tache pouvant être exécutée si il n'y a plus de tache, on wait
        while(!existTasks()){
            wait();
        }
        //Si il reste des tache on en retourn une après l'avoir enlever.
        return (Runnable)taskList.remove();
    }
    
    @Override
    public synchronized boolean existTasks()
    {
        //Check si il reste des taches
        return !taskList.isEmpty();
    }
    
    @Override
    public synchronized void recordTask (Runnable r)
    {
        //Ajoute une nouvelle taches
        //On envoie un notify afin de quitté le Wait
        taskList.addLast(r);
        notify();
    } 
}
