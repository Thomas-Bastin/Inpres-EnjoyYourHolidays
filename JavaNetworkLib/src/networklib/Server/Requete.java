/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networklib.Server;

import java.net.Socket;

/**
 *   Logique : une requête doit être capable de générer l'objet Runnable qui la traite au
 *             moyen d'une méthode createRunnable(). On remarquera les deux paramètres :
 *                ♦ une socket (pour que l'on puisse toujours savoir d'où la requête provient);
 *                ♦ un objet désignant le GUI qui tracera les actions effectuées (si, du moins on le souhaite).
 * @author Arkios
 */
public interface Requete {
    // Ce qui va être exécuté doit connaître la socket du client distant
    // ainsi que le GUI qui affiche les traces
    public Runnable createRunnable (Socket s, ServerConsole cs);
}