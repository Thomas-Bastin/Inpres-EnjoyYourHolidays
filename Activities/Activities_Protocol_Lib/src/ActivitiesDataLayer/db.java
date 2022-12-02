/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActivitiesDataLayer;

import ActivitiesDataLayer.entities.*;
import JDBC.MySqlConnexion;
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
        }
    }
    
    
    public synchronized static String getPassword(String login) throws Exception, SQLException{
        LinkedList list = new LinkedList();
        ResultSet rs = mysql.createStatement().executeQuery("SELECT password FROM employes WHERE email = \""+ login +"\"");

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
    
    public synchronized static void RegisterToActivities(Activities act,  Voyageurs cl, boolean payed) throws SQLException{
        Statement statement = mysql.createStatement();
        int bool;
        if(payed){ bool = 1; } else{ bool = 0; }
        
        String sql = "INSERT INTO inscriptionactivites ( voyageurRef, activiteRef, paye) VALUES ("+ cl.getNumeroClient() 
                   + ", "+ act.getIdActivite() + ", " + bool + ")";
        statement.execute(sql);
        return;
    }
    
    public synchronized static void UnlistToActivities(Activities act, Voyageurs cl, Date dateDebut) throws SQLException{
        return;
    }
    
    
    
    public synchronized static LinkedList<Voyageurs> getClients() throws SQLException{
        String sql="SELECT * FROM voyageurs";
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
    
    
    
    public synchronized static LinkedList<Activities> getActivities() throws SQLException{
        String sql="SELECT * FROM activites";
        LinkedList<Activities> list = new LinkedList<Activities>();
        ResultSet rs = mysql.createStatement().executeQuery(sql);
        
        while (rs.next()) {
            Activities a = new Activities(rs.getInt("idActivite"), rs.getString("typeActivite"), rs.getInt("nombreMaxParticipants"), 
                                        rs.getInt("nombreParticipantsInscrits"),rs.getDate("dateDebut"), rs.getInt("dureeActivite"), 
                                        rs.getFloat("prixHTVA"), rs.getInt("voyageurReferent"));
            list.add(a);
        }
        //return du modèle
        return list;
    }
    
    
    public synchronized static LinkedList<Voyageurs> getRegisteredClients(Activities act) throws SQLException{
        int id = act.getIdActivite();
        LinkedList<Voyageurs> list = new LinkedList<Voyageurs>();
        String sqlRequest = "SELECT voyageurs.* "
                          + "FROM voyageurs INNER JOIN inscriptionactivites "
                          + "ON(voyageurs.numeroClient = inscriptionactivites.voyageurRef) "
                          + "WHERE inscriptionactivites.activiteRef = " + act.getIdActivite();
        
        ResultSet rs = mysql.createStatement().executeQuery(sqlRequest);

        
        while (rs.next()) {    
            Voyageurs v = new Voyageurs(rs.getInt("numeroClient") , rs.getString("nomVoyageur"), rs.getString("prenomVoyageur"),
                                        rs.getString("nomRue"), rs.getInt("numHabitation"), rs.getInt("numBoiteHabitation"), 
                                        rs.getInt("codePostal"), rs.getString("commune"), rs.getString("nationalite"), rs.getDate("dateNaissance"), rs.getString("email"), rs.getInt("voyageurReferent"));
            list.add(v);
        }
        //return du modèle
        return list;
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
