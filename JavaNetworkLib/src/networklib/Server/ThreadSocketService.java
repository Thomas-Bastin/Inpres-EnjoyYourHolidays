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
                currentTask.run();
            }
            catch (Exception ex){
                if(ex instanceof InterruptedException){
                    System.out.println("Interruption: " + ex.getMessage());
                }
                else continue;
            }
        } 
    }
    
    public void Shutdown() throws IOException{
        super.stop();
    }
}
