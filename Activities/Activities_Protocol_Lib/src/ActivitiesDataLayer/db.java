/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActivitiesDataLayer;

import JDBC.MySqlConnexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Thomas
 */
public class db {
    private static Connection mysql;
    
    static{
        try {
            mysql = MySqlConnexion.getMySQLConnection();
        } catch (SQLException ex) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static boolean checkLogin(String login) throws SQLException{
        // LinkedList cur = select("","","", false);
        
        return true;
    }
    
    public static boolean checkPassword(String login, String hash) throws SQLException{
        // LinkedList cur = select("","","", false);
        
        return true;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    public static LinkedList select(String select, String from, String where, boolean isheader) throws SQLException {
        //Création d'un nouveau modèle
        LinkedList list = new LinkedList();

        Statement statement = mysql.createStatement();
        String sql = "SELECT " + select + " FROM " + from + " WHERE " + where;

        ResultSet rs = statement.executeQuery(sql);

        //Récupération du result set
        ResultSetMetaData metaData = rs.getMetaData();

        //récupéaration nombre de colonne
        int columnCount = metaData.getColumnCount();

        //Récupération des noms de colonnes et ajout au modèle
        Vector header = new Vector();
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            header.add(metaData.getColumnLabel(columnIndex));
        }
        if (isheader) {
            list.add(header);
        }

        //Création d'un vecteur d'objet de la taille du nombre de colonne
        Object[] row = new Object[columnCount];

        //Déplacement du curseur sur le résult set
        while (rs.next()) {
            //Récupération des objets dans une ligne.
            for (int i = 0; i < columnCount; i++) {
                row[i] = rs.getObject(i + 1);
            }
            //ajout de la ligne au modèle
            list.add(row);
        }
        //return du modèle
        return list;
    }

    public static LinkedList update(String Update, String set, String where, boolean isHeader) throws SQLException {
        Statement statement = mysql.createStatement();
        String sql = "UPDATE " + Update + " SET " + set + " WHERE " + where;
        statement.executeUpdate(sql);
        return select("*", Update, where, isHeader);
    }

    public static LinkedList delete(String DeleteFrom, String where, boolean isHeader) throws SQLException {
        Statement statement = mysql.createStatement();
        String sql = "DELETE FROM " + DeleteFrom + " WHERE " + where;
        statement.executeUpdate(sql);
        return select("*", DeleteFrom, where, isHeader);
    }

}
