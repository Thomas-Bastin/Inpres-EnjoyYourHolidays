/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db_access_tools;

import static com.sun.org.apache.xerces.internal.util.FeatureState.is;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author student
 */
public class ConnectionUtils {

    public static Connection getMyConnection(String s) {
        if (s.equals("Oracle")) {
            try {
                return OracleConnUtils.getOracleConnection();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConnectionUtils.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        if (s.equals("MySQL")) {
            try {
                return MySQLConnUtils.getMySQLConnection();
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionUtils.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConnectionUtils.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        return null;
    }

    
    
    
    public static TableModel select(Connection c, String select, String from, String where) {
        try {
            //Création d'un nouveau modèle
            DefaultTableModel tableModel = new DefaultTableModel();

            Statement statement = c.createStatement();
            String sql = "SELECT " + select + " FROM " + from + " WHERE " + where;
            
            
            ResultSet rs = statement.executeQuery(sql);

            //Récupération du result set
            ResultSetMetaData metaData = rs.getMetaData();

            //récupéaration nombre de colonne
            int columnCount = metaData.getColumnCount();

            //Récupération des noms de colonnes et ajout au modèle
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                tableModel.addColumn(metaData.getColumnLabel(columnIndex));
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
                tableModel.addRow(row);
            }

            //return du modèle
            return tableModel;

        } catch (SQLException ex) {
            String tmp = "Code: " + ex.getErrorCode() + "\n" + ex.getMessage();
            JOptionPane.showMessageDialog(null, tmp, "SQL ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public static TableModel update(Connection c, String Update, String set, String where) {

        //Création d'un nouveau modèle
        DefaultTableModel tableModel = new DefaultTableModel();

        Statement statement;
        try {
            statement = c.createStatement();
        } catch (SQLException ex) {
            String tmp = "Code: " + ex.getErrorCode() + "\n" + ex.getMessage();
            JOptionPane.showMessageDialog(null, tmp, "SQL ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        String sql = "UPDATE " + Update + " SET " + set + " WHERE " + where;

        try {
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            String tmp = "Code: " + ex.getErrorCode() + "\n" + ex.getMessage();
            JOptionPane.showMessageDialog(null, tmp, "SQL ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return select(c, "*", Update, where);
    }

    public static TableModel delete(Connection c, String DeleteFrom, String where) {
        Statement statement;
        try {
            statement = c.createStatement();
        } catch (SQLException ex) {
            String tmp = ex.getMessage();
            JOptionPane.showMessageDialog(null, tmp, "SQL ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        String sql = "DELETE FROM " + DeleteFrom + " WHERE " + where;

        try {
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            String tmp = "Code: " + ex.getErrorCode() + "\n" + ex.getMessage();
            JOptionPane.showMessageDialog(null, tmp, "SQL ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return select(c, "*", DeleteFrom, where);
    }
    
    public static int NumRow(Connection c, String from, String where) 
    {
        int NumRow = 0;
        
        try {
            //Création d'un nouveau modèle
            DefaultTableModel tableModel = new DefaultTableModel();

            Statement statement = c.createStatement();
            String sql = "SELECT COUNT(*) FROM " + from + " WHERE " + where + ";";
            ResultSet rs = statement.executeQuery(sql);

            rs.next();
            
            NumRow = rs.getInt(1);
            
        } catch (SQLException ex) {
            String tmp = "Code: " + ex.getErrorCode() + "\n" + ex.getMessage();
            JOptionPane.showMessageDialog(null, tmp, "SQL ERROR", JOptionPane.ERROR_MESSAGE);
            NumRow = 0;
        }
        
        return NumRow;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    public static Vector getTypeActivitée(Connection c) {
        try {
            //Création d'un nouveau modèle
            Vector v = new Vector();

            Statement statement = c.createStatement();

            String sql = "SELECT " + "typeActivite" + " FROM " + "activites";
            ResultSet rs = statement.executeQuery(sql);

            //Récupération du result set
            ResultSetMetaData metaData = rs.getMetaData();

            //récupéaration nombre de colonne
            int columnCount = metaData.getColumnCount();

            //Création d'un vecteur d'objet de la taille du nombre de colonne
            Object[] row = new Object[columnCount];

            //Déplacement du curseur sur le résult set
            while (rs.next()) {
                v.add(rs.getObject(1));
            }

            //return du modèle
            return v;

        } catch (SQLException ex) {
            String tmp = "Code: " + ex.getErrorCode() + "\n" + ex.getMessage();
            JOptionPane.showMessageDialog(null, tmp, "SQL ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
