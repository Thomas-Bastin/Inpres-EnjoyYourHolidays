/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase.Connexions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arkios
 */
public class MySqlConnexion {


   // Connect to MySQL

    /**
     *
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
   public static Connection getMySQLConnection() throws SQLException, ClassNotFoundException 
   {
       Properties config = getProperties();

       //Default Constructor
       String hostName = config.getProperty("dbSocket");
       String dbName   = config.getProperty("dbName");
       String userName = config.getProperty("dbUserName");
       String password = config.getProperty("dbPassword");
       
       return getMySQLConnection(hostName, dbName, userName, password);
   }
   
   public static Connection getMySQLConnection2()
   {
       Properties config = getProperties();

       //Default Constructor
       String hostName = config.getProperty("dbSocket");
       String dbName   = config.getProperty("dbName");
       String userName = config.getProperty("dbUserName");
       String password = config.getProperty("dbPassword");
       
       return getMySQLConnection(hostName, dbName, userName, password);
   }

    /**
     *
     * @param hostName
     * @param dbName
     * @param userName
     * @param password
     * @return
     */
    public static Connection getMySQLConnection(String hostName, String dbName, String userName, String password)
   {
       try {
           //Déclaration du Driver JDBC
           Class.forName("com.mysql.cj.jdbc.Driver");
           
           //Creation de la string
           String connectionURL = "jdbc:mysql://" + hostName + "/"+ dbName;
           
           //retourne Connexion
           Connection conn = DriverManager.getConnection(connectionURL, userName, password);
           return conn;
       } catch (ClassNotFoundException | SQLException ex) {
           Logger.getLogger(MySqlConnexion.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }
   }
   
   private static synchronized Properties getProperties(){
        Properties config;
        
        File f = new File("server.cfg");
        config = new Properties();
        
        try {
            if(f.createNewFile()){
                OutputStream os = new FileOutputStream(f.getPath());
                config.setProperty("server_port", "50005");
                config.setProperty("threadNumber", "5");
                config.setProperty("dbSocket", "192.168.1.63:3560");
                config.setProperty("dbName", "bd_holidays");
                config.setProperty("dbUserName", "db_access_tools");
                config.setProperty("dbPassword", "p11eYu");
                
                config.store(os, "Configuration du Serveur:");
            }
            else{
                FileInputStream fis = new FileInputStream(f.getPath());
                config.load(fis);
            }
        } catch (IOException ex) {
            Logger.getLogger(MySqlConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return config;
    }
}