/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networklib.Server;

/**
 * A priori, la méthode de traçage recevra une chaîne de caractères, que le serveur découpera à
 * sa guise (par exemple, le symbole "#" séparera les différentes composantes à placer, par
 * exemple, dans un tableau).
 * 
 * @author Arkios
 */
public interface ServerConsole {
    public void Trace(String comment);
}
