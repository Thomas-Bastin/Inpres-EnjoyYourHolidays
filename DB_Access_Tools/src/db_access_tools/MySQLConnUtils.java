/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db_access_tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnUtils {


   // Connect to MySQL
   public static Connection getMySQLConnection() throws SQLException, ClassNotFoundException 
   {
       //Default Constructor
       String hostName = "192.168.1.63:3560";
       String dbName = "bd_holidays";
       String userName = "db_access_tools";
       String password = "p11eYu";

       return getMySQLConnection(hostName, dbName, userName, password);
   }

   public static Connection getMySQLConnection(String hostName, String dbName, String userName, String password) 
        throws SQLException, ClassNotFoundException 
   {
       //Déclaration du Driver JDBC
       Class.forName("com.mysql.cj.jdbc.Driver");
       
       //Creation de la string
       String connectionURL = "jdbc:mysql://" + hostName + "/"+ dbName;
       
       //retourne Connexion
       Connection conn = DriverManager.getConnection(connectionURL, userName, password);
       return conn;
   }
}