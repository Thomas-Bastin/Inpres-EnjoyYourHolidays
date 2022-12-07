/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReservationDataLayer;


import JDBC.MySqlConnexion;
import ReservationDataLayer.entities.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thomas
 */
public class db {
    
    private static Connection mysql;
    
    public static void initdatabase(){
        try {
            mysql = MySqlConnexion.getMySQLConnection();
        } catch (SQLException ex) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("DataBase Not Reachable...");
        }
    }
    
    
    public synchronized static String getPassword(String login) throws Exception, SQLException{
        //Création d'un nouveau modèle
        LinkedList list = new LinkedList();
        
        
        Statement statement = mysql.createStatement();
        String sql = "SELECT password" + " FROM employes" + " WHERE email = \""+ login +"\"" ;        
        ResultSet rs = statement.executeQuery(sql);

        //Récupération du result set
        ResultSetMetaData metaData = rs.getMetaData();
        //récupéaration nombre de colonne
        int columnCount = metaData.getColumnCount();

        
        //Création d'un vecteur d'objet de la taille du nombre de colonne
        Object[] row = new Object[columnCount];

        //Déplacement du curseur sur le résult set
        while (rs.next()) {
            String hash = null;
            //Récupération des objets dans une ligne.
            for (int i = 0; i < columnCount; i++) {
                hash = (String) rs.getObject(1);
            }
            //ajout de la ligne au modèle
            list.add(hash);
        }
        //return du modèle

        if(list.isEmpty()) throw new Exception("badlogin");
        if(list.size() > 1) throw new Exception("toomanylogin");
        
        return (String) list.get(0);
    }
    
    public synchronized static boolean isAcredited(String login) throws SQLException{
        LinkedList cur = select("acred","employes INNER JOIN Acreditation","email =  AND Acred = Activities" + login, false);
        return !cur.isEmpty();
    }
    
    public synchronized static LinkedList<Voyageurs> getClients() throws SQLException{
        String sql="SELECT * FROM voyageurs"; 
        
        //Création d'un nouveau modèle
        LinkedList<Voyageurs> list = new LinkedList<Voyageurs>();
        ResultSet rs = mysql.createStatement().executeQuery(sql);
        
        while (rs.next()) {    
            Voyageurs v = new Voyageurs(rs.getInt("numeroClient") , rs.getString("nomVoyageur"), rs.getString("prenomVoyageur"),
                                        rs.getString("nomRue"), rs.getInt("numHabitation"), rs.getInt("numBoiteHabitation"), 
                                        rs.getInt("codePostal"), rs.getString("commune"), rs.getString("nationalite"), rs.getDate("dateNaissance"), rs.getString("email"), rs.getInt("voyageurReferent"));
            list.add(v);
        }
        //return du modèle
        return list;
    }
    
    
    public synchronized static LinkedList getReservationRoom() throws SQLException {
        LinkedList list = new LinkedList<>();
        return list;
    }
    
    public synchronized static LinkedList<Chambres> getRooms() throws SQLException {
        LinkedList<Chambres> list = new LinkedList<Chambres>();
        return list;
    }
    
    public synchronized static boolean BookRoom() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized static boolean PayRoom() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public synchronized static boolean CancelRoom() {
        return true;
    }
    
    
    
    
    
    public static LinkedList select(String select, String from, String where, boolean isheader) throws SQLException {
        //Création d'un nouveau modèle
        LinkedList list = new LinkedList();

        Statement statement = mysql.createStatement();
        String sql;
        if(where.equals("") || where == null){
            sql = "SELECT " + select + " FROM " + from;
        }
        else{
            sql = "SELECT " + select + " FROM " + from + " WHERE " + where;
        }
        
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
}
