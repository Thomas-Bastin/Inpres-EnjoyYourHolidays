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

public class OracleConnUtils {

    // Connect to Oracle.
    public static Connection getOracleConnection() throws SQLException, ClassNotFoundException 
    {
        String hostName = "localhost";
        String sid = "db11g";
        String userName = "simplehr";
        String password = "1234";

        return getOracleConnection(hostName, sid, userName, password);
    }

    public static Connection getOracleConnection(String hostName, String sid, String userName, String password) 
        throws ClassNotFoundException, SQLException 
    {
       
        //Déclaration du Driver JDBC
        Class.forName("oracle.jdbc.driver.OracleDriver");
        
        //Creation de la string
        String connectionURL = "jdbc:oracle:thin:@" + hostName + ":1521:" + sid;
        
        //retourne Connexion
        Connection conn = DriverManager.getConnection(connectionURL, userName, password);
        return conn;
    }
}
