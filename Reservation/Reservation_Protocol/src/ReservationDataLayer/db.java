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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    
    public synchronized static LinkedList<Complexes> getComplex() throws SQLException {
        PreparedStatement pStmt = mysql.prepareStatement(
            "SELECT * FROM complexes;"
        );
        ResultSet rs = pStmt.executeQuery();
        
        //Création d'un nouveau modèle
        LinkedList<Complexes> list = new LinkedList<Complexes>();
        while (rs.next()) {    
            Complexes v = new Complexes(
                    rs.getInt("idComplexe"), rs.getString("nomComplexe"), rs.getString("typeComplexe")
            );
            list.add(v);
        }
        //return du modèle
        return list;
    }
    
    public synchronized static LinkedList<Chambres> getRooms(Complexes c) throws SQLException {
        LinkedList<Chambres> list = new LinkedList<Chambres>();
        
        PreparedStatement pStmt = mysql.prepareStatement(
            "SELECT * FROM chambres WHERE idComplexe = ? ;"
        );
        pStmt.setInt(1, c.getIdComplexe());
        ResultSet rs = pStmt.executeQuery();
        
        //Création d'un nouveau modèle
        
        while (rs.next()){
            Chambres ch = new Chambres( rs.getInt("idChambre"), rs.getInt("idComplexe"), rs.getString("Type"), 
                    rs.getString("equipements"), rs.getInt("nombreLits"), rs.getFloat("prixHTVA"));
            
            list.add(ch);
        }
        //return du modèle
        return list;
    }
    
    public synchronized static LinkedList<CalendRow> getReservationRoom(Complexes c, LocalDate date) throws SQLException {
        LinkedList<CalendRow> listRow = new LinkedList<CalendRow>();
        LinkedList<Chambres> Chambres =  getSubRooms(c);
        LocalDate myDate = LocalDate.from(date);
        
        for(Chambres ch : Chambres){            
            CalendRow tmpRow = new CalendRow(ch);
            
            PreparedStatement pStmt = mysql.prepareStatement(
                "SELECT idComplexe, idChambre, idVoyageur, " +
                "dateArrive, (dateArrive + INTERVAL  nbJours DAY ) AS dateFin, nbJours, nomVoyageur, paye " +
                "FROM reservationchambre " +
                "INNER JOIN voyageurs ON (voyageurs.numeroClient = reservationchambre.idVoyageur) " +
                "WHERE idComplexe = ? AND idChambre = ?"
            );
            pStmt.setInt(1, c.getIdComplexe());
            pStmt.setInt(2, ch.getNumChambre());
            
            ResultSet rs = pStmt.executeQuery();
            
            while(rs.next()){
                LocalDate dBeg = LocalDate.from(myDate);
                LocalDate dEnd = LocalDate.from(myDate.plusDays(6));
                LocalDate tmpArr = LocalDate.from(rs.getDate("dateArrive").toLocalDate());
                LocalDate tmpFin = LocalDate.from(rs.getDate("dateFin").toLocalDate());
                
                Reservationchambre tmp = new Reservationchambre(
                        rs.getInt("idComplexe"),
                        rs.getInt("idChambre"),
                        rs.getInt("idVoyageur"),
                        rs.getDate("dateArrive"),
                        rs.getInt("nbJours"),
                        rs.getString("nomVoyageur"),
                        rs.getInt("paye")
                );
                
                
                LocalDate tmpDate = LocalDate.from(tmpArr);
                while(tmpDate.isBefore(tmpFin)){
                    if((tmpDate.isEqual(dBeg)||tmpDate.isAfter(dBeg)) && (tmpDate.isBefore(dEnd)||tmpDate.isEqual(dEnd))) {
                        tmpRow.setReserv( tmpDate.getDayOfWeek() , tmp);
                    }
                    
                    tmpDate = tmpDate.plusDays(1);
                }
            }
            
            listRow.add(tmpRow);
        }
        
        for(CalendRow ctest : listRow){
            System.out.println(ctest.toVector());
        }
        
        return listRow;
    }
    
    public static LinkedList<Chambres> getSubRooms(Complexes c) throws SQLException {
        LinkedList<Chambres> list = new LinkedList<Chambres>();
        
        PreparedStatement pStmt = mysql.prepareStatement(
            "SELECT * FROM chambres WHERE idComplexe = ? ;"
        );
        pStmt.setInt(1, c.getIdComplexe());
        ResultSet rs = pStmt.executeQuery();
        
        //Création d'un nouveau modèle
        
        while (rs.next()){
            Chambres ch = new Chambres( rs.getInt("idChambre"), rs.getInt("idComplexe"), rs.getString("Type"), 
                    rs.getString("equipements"), rs.getInt("nombreLits"), rs.getFloat("prixHTVA"));
            
            list.add(ch);
        }
        //return du modèle
        return list;
    }
    
    
    
    public synchronized static int BookRoom(Chambres ch, Date dateBeg, int Nights, Voyageurs ClientRef) throws SQLException, Exception {
        mysql.setAutoCommit(false);
        if(!checkDate(ch, dateBeg, Nights)){
            mysql.rollback();
            mysql.setAutoCommit(true);
            throw new Exception("DateInvalid");
        }
        
        PreparedStatement pStmt = mysql.prepareStatement(
            "INSERT INTO reservationchambre (idChambre, idComplexe, idVoyageur, dateArrive, nbJours)\n" +
                            "VALUES ( ? , ? , ? , ? , ?);"
        );
        
        pStmt.setInt(1, ch.getNumChambre());
        pStmt.setInt(2, ch.getIdComplexe());
        pStmt.setInt(3, ClientRef.getNumeroClient());
        pStmt.setDate(4, dateBeg);
        pStmt.setInt(5, Nights);
        
        int rs = pStmt.executeUpdate();
        mysql.commit();
        mysql.setAutoCommit(true);
        
        return rs;
    }
    
    public static boolean checkDate(Chambres ch, Date dateBeg, int Nights) throws SQLException {
        boolean errored = false;
        
        LocalDate dbeg = dateBeg.toLocalDate();
        LocalDate dend = dbeg.plusDays(Nights);
        
        PreparedStatement pStmt = mysql.prepareStatement(
            " SELECT dateArrive, nbJours, idVoyageur "
          + " FROM reservationchambre"
          + " WHERE idChambre = ? AND idComplexe = ?; "
        );
        pStmt.setInt(1, ch.getNumChambre());
        pStmt.setInt(2, ch.getIdComplexe());    
        ResultSet rs = pStmt.executeQuery();
            
        while(rs.next()){
            LocalDate tmpbeg = rs.getDate("dateArrive").toLocalDate();
            LocalDate tmpend = tmpbeg.plusDays(rs.getInt("nbJours"));
            
            LocalDate tmpDate = LocalDate.from(tmpbeg);
            while (tmpDate.isBefore(tmpend)) {
                if ((tmpDate.isEqual(dbeg) || tmpDate.isAfter(dbeg)) && (tmpDate.isBefore(dend))) {
                    errored = true;
                    break;
                }
                tmpDate = tmpDate.plusDays(1);
            }
        }
        
        return !errored;
    }

    
    
    
    public synchronized static boolean PayRoom() throws SQLException {
        
        return true;
    }
    
    
    
    public synchronized static boolean CancelRoom(Chambres room, Date dateBeg) throws SQLException {
        Date nowDate = (Date) Date.from(Instant.now());
        if(nowDate.compareTo(dateBeg) >= 0){ return false;}
        
        PreparedStatement pStmt = mysql.prepareStatement(
            "DELETE FROM reservationchambre WHERE idChambre = ? AND idComplexe = ? AND dateArrive = ? ;"
        );
        
        pStmt.setInt(1, room.getNumChambre());
        pStmt.setInt(2, room.getIdComplexe());
        pStmt.setDate(3, dateBeg);
        
        int rs = pStmt.executeUpdate();
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
