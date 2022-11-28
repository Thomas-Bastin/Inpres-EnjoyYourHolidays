/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networklib.Server;

import java.io.*;

/**
 *
 * @author Thomas
 */
public class ThreadSocketService extends Thread {    
    private final TasksSource tasks;
    private final String name;
    
    private Runnable currentTask;
    
    
    public ThreadSocketService(TasksSource t, String n){
        tasks = t;
        name = n;
    }
    
    public void run(){
        while (!isInterrupted()){
            try{
                currentTask = tasks.getTask();
                //Pour éviter tout hack, nous vérifier que la tache enregistrée est bien une de nos Request.
                if(!(currentTask instanceof Request)){
                    continue;
                }
                
                if(currentTask instanceof Login){
                    currentTask.run();
                    continue;
                }
            }
            catch (InterruptedException e){
                System.out.println("Interruption: " + e.getMessage());
            }
            
            System.out.println("Start the Current Task: ");
            currentTask.run();
        } 
    }
    
    public void Shutdown() throws IOException{
        super.stop();
    }
}
