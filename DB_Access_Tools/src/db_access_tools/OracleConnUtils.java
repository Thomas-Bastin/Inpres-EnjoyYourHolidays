/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db_access_tools;

/**
 *
 * @author student
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OracleConnUtils {

    // Connect to Oracle.
    public static Connection getOracleConnection() throws ClassNotFoundException 
    {
        String hostName = "192.168.1.63";
        String sid = "xe";
        String userName = "C##DB_ACCESS_TOOLS";
        String password = "7mqA";

        return getOracleConnection(hostName, sid, userName, password);
    }

    public static Connection getOracleConnection(String hostName, String sid, String userName, String password) 
        throws ClassNotFoundException 
    {
       
        //Déclaration du Driver JDBC
        Class.forName("oracle.jdbc.driver.OracleDriver");
        
        //Creation de la string
        String connectionURL = "jdbc:oracle:thin:@" + hostName + ":1521:" + sid;
        
        //retourne Connexion
        Connection conn;
        try {
            conn = DriverManager.getConnection(connectionURL, userName, password);
        } catch (SQLException ex) {
            conn = null;
            Logger.getLogger(OracleConnUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
}
