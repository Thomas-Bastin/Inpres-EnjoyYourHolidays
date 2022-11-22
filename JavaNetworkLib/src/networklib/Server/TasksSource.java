/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networklib.Server;

/**
 *  Un tel objet contiendra les tâches à exécuter sous forme d'objets Runnable. 
 *  Il les mémorisera au moyen d'une méthode recordTask() et en fournira un (si il en existe une – sinon, 
  on attendra : wait() ?) au moyen de sa méthode getTask(). 
 * @author Arkios
 */
public interface TasksSource {
        //Devra être Synchronized
        public Runnable getTask() throws InterruptedException;
        public boolean existTasks();
        public void recordTask (Runnable r); 
}