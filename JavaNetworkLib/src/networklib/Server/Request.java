/*
 * Request.java
*/
package networklib.Server;
import java.net.*;
/**
 * @author Vilvens
 */

public interface Request
{
 // Ce qui va être exécuté doit connaître la socket du client distant
 // ainsi que le GUI qui affiche les traces
 public Runnable createRunnable (Socket s, ServerConsole cs);
} 