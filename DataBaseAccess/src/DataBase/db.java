/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;


import Entities.Reservationchambre;
import Entities.CalendRow;
import Entities.Complexes;
import Entities.Voyageurs;
import Entities.Chambres;
import DataBase.Connexions.MySqlConnexion;
import Entities.BasketRow;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.TimeZone;
import java.util.Vector;

/**
 *
 * @author Thomas
 */
public class db {
    
    private static final Connection mysql = MySqlConnexion.getMySQLConnection2();
    
    /**
     *
     */
    public static void initdatabase(){}
    
    /**
     *
     * @param login
     * @param client
     * @return
     * @throws Exception
     * @throws SQLException
     */
    public synchronized static String getUserPassword(String login, Voyageurs client) throws Exception, SQLException{
        //Création d'un nouveau modèle
        String Passwd = null;    
        PreparedStatement pStmt = mysql.prepareStatement(
            "SELECT voyageurs.*, passwd " +
            "FROM voyageurs_passwd INNER JOIN voyageurs " +
            "ON(voyageurs_passwd.idClient = voyageurs.numeroClient) " +
            "WHERE email = ? " +
            "; "
        );
        pStmt.setString(1, login);
        ResultSet rs = pStmt.executeQuery();
        
        
        //Déplacement du curseur sur le résult set
        int i = 0;
        while (rs.next()) {
            if(i>0) throw new Exception("toomanylogin");
            
            Passwd = rs.getString("passwd");
            
            client.setNumeroClient(rs.getInt("numeroClient"));
            client.setNomVoyageur(rs.getString("nomVoyageur"));
            client.setPrenomVoyageur(rs.getString("prenomVoyageur"));
            client.setNomRue(rs.getString("nomRue"));
            client.setNumHabitation(rs.getInt("numBoiteHabitation"));
            client.setNumBoiteHabitation(rs.getInt("numBoiteHabitation"));
            client.setCodePostal(rs.getInt("codePostal"));
            client.setCommune(rs.getString("commune"));
            client.setNationalite(rs.getString("nationalite"));
            client.setDateNaissance(rs.getDate("dateNaissance"));
            client.setEmail(rs.getString("email"));
            client.setVoyageurReferent(rs.getInt("voyageurReferent"));
            
            i++;
        }
        
        if(i==0) throw new Exception("badlogin");
        
        return Passwd;
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
    
    /**
     *
     * @param login
     * @return
     * @throws SQLException
     */
    public synchronized static boolean isAcredited(String login) throws SQLException{
        LinkedList cur = select("acred","employes INNER JOIN Acreditation","email =  AND Acred = Activities" + login, false);
        return !cur.isEmpty();
    }
    
    /**
     *
     * @return
     * @throws SQLException
     */
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
    
    /**
     *
     * @return
     * @throws SQLException
     */
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
    
    public synchronized static Complexes getSpecificComplex(int IdComplexe) throws SQLException {
        PreparedStatement pStmt = mysql.prepareStatement(
            "SELECT * FROM complexes WHERE idComplexe = ? ;"
        );
        pStmt.setInt(1, IdComplexe);
        ResultSet rs = pStmt.executeQuery();
        
        //Création d'un nouveau modèle
        Complexes complex = null;
        int i = 0;
        while (rs.next()) {    
            complex = new Complexes(
                    rs.getInt("idComplexe"), rs.getString("nomComplexe"), rs.getString("typeComplexe")
            );
            i++;
        }
        if(i==1) return complex;
        else return null;
    }
    
    /**
     *
     * @param c
     * @return
     * @throws SQLException
     */
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
    
    
    public synchronized static Chambres getSpecificRoom(Complexes c, int idRoom) throws SQLException {
        Chambres room = null;
        PreparedStatement pStmt = mysql.prepareStatement(
            "SELECT * FROM chambres WHERE idComplexe = ? AND idChambre = ? ;"
        );
        pStmt.setInt(1, c.getIdComplexe());
        pStmt.setInt(2, idRoom);
        ResultSet rs = pStmt.executeQuery();
        
        //Création d'un nouveau modèle
        int i = 0;
        while (rs.next()){
            room = new Chambres( rs.getInt("idChambre"), rs.getInt("idComplexe"), rs.getString("Type"), 
                    rs.getString("equipements"), rs.getInt("nombreLits"), rs.getFloat("prixHTVA"));
            i++;
        }
        //return du modèle
        return room;
    }
    
    
    /**
     *
     * @param c
     * @param date
     * @return
     * @throws SQLException
     */
    public synchronized static LinkedList<CalendRow> getReservationRoom(Complexes c, LocalDate date) throws SQLException {
        LinkedList<CalendRow> listRow = new LinkedList<CalendRow>();
        LinkedList<Chambres> Chambres =  getSubRooms(c);
        LocalDate myDate = LocalDate.from(date);
        
        for(Chambres ch : Chambres){            
            CalendRow tmpRow = new CalendRow(ch);
            
            PreparedStatement pStmt = mysql.prepareStatement(
                "SELECT idComplexe, idChambre, idVoyageur, " +
                "dateArrive, (dateArrive + INTERVAL  nbJours DAY ) AS dateFin, nbJours, nomVoyageur " +
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
                        false
                );
                
                checkReservPaied(ch, tmp);
                
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
    
    /**
     *
     * @param ch
     * @param tmp
     * @throws SQLException
     */
    public static void checkReservPaied(Chambres ch, Reservationchambre tmp) throws SQLException{
        int nbJ = tmp.getNbJours();
        double toPayHTVA = nbJ * ch.getPrixHTVA();
        final double TVA = (float) 1.21;
        double toPay = toPayHTVA * TVA;
        
        double sumAlreadyPaied = 0;
        PreparedStatement pStmt = mysql.prepareStatement(
            "SELECT montant FROM payementchambre WHERE idComplexe = ? AND idChambre = ? AND dateArrive = ?;"
        );
        
        pStmt.setInt(1,  tmp.getIdComplexe());
        pStmt.setInt(2,  tmp.getIdChambre());
        pStmt.setDate(3, new java.sql.Date(tmp.getDateArrive().getTime()));
        ResultSet rs = pStmt.executeQuery();
        int i = 0;
        //Création d'un nouveau modèle
        while (rs.next()){
            //Somme des payements TVAC
            sumAlreadyPaied += rs.getFloat("montant");
            i++;
        }
        
        System.out.println("RowFound: " + i);
        System.out.println("AlreadyPaied: " + sumAlreadyPaied);
        System.out.println("Topay: " + toPay);
        //Check si sumAlreadyPaied == ...
        if(sumAlreadyPaied >= toPay){
            tmp.setIsPayed(true);
        }
    }
    
    /**
     *
     * @param ch
     * @param reserv
     * @return
     * @throws SQLException
     */
    public synchronized static double howMuchToPay(Chambres ch, Reservationchambre reserv) throws SQLException{
        int nbJ = reserv.getNbJours();
        double toPayHTVA = nbJ * ch.getPrixHTVA();
        final double TVA = (float) 1.21;
        double toPay = toPayHTVA * TVA;
        
        double sumAlreadyPaied = 0;
        PreparedStatement pStmt = mysql.prepareStatement(
            "SELECT montant FROM payementchambre WHERE idComplexe = ? AND idChambre = ? AND dateArrive = ?;"
        );
        
        pStmt.setInt(1,  reserv.getIdComplexe());
        pStmt.setInt(2,  reserv.getIdChambre());
        pStmt.setDate(3, new java.sql.Date(reserv.getDateArrive().getTime()));
        ResultSet rs = pStmt.executeQuery();
        
        //Création d'un nouveau modèle
        while (rs.next()){
            //Somme des payements TVAC
            sumAlreadyPaied += rs.getFloat("montant");
        }
        return toPay - sumAlreadyPaied;
    }
    
    public synchronized static double howMuchToPay(Chambres ch, int idComplexe, int idChambre, java.sql.Date date, int nbJours) throws SQLException{
        int nbJ = nbJours;
        double toPayHTVA = nbJ * ch.getPrixHTVA();
        final double TVA = (float) 1.21;
        double toPay = toPayHTVA * TVA;
        
        double sumAlreadyPaied = 0;
        PreparedStatement pStmt = mysql.prepareStatement(
            "SELECT montant FROM payementchambre WHERE idComplexe = ? AND idChambre = ? AND dateArrive = ?;"
        );
        
        pStmt.setInt(1,  idComplexe);
        pStmt.setInt(2,  idChambre);
        pStmt.setDate(3, date);
        ResultSet rs = pStmt.executeQuery();
        
        //Création d'un nouveau modèle
        while (rs.next()){
            //Somme des payements TVAC
            sumAlreadyPaied += rs.getFloat("montant");
        }
        return toPay - sumAlreadyPaied;
    }
    
    /**
     *
     * @param dateToConvert
     * @return
     */
    public LocalDate DatetoLocal(java.util.Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }
    
    /**
     *
     * @param c
     * @return
     * @throws SQLException
     */
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
    
    public static synchronized LinkedList<BasketRow> getCommands(Voyageurs user) throws SQLException{
        LinkedList<BasketRow> list = new LinkedList<>();
        
        PreparedStatement pStmt = mysql.prepareStatement(
            "SELECT reservationchambre.idChambre, reservationchambre.idComplexe, reservationchambre.dateArrive, reservationchambre.nbJours, chambres.`Type`, " +
            "chambres.equipements, chambres.nombreLits, chambres.prixHTVA, complexes.nomComplexe, complexes.typeComplexe " +
            "FROM  reservationchambre " +
            "INNER JOIN chambres ON(chambres.idChambre = reservationchambre.idChambre AND chambres.idComplexe = reservationchambre.idComplexe) " +
            "INNER JOIN complexes ON(complexes.idComplexe = reservationchambre.idComplexe) " +
            "WHERE reservationchambre.idVoyageur = ? " +
            "GROUP BY reservationchambre.idChambre, reservationchambre.idComplexe, reservationchambre.dateArrive " +
            "ORDER BY 3 DESC ;"
        );
        pStmt.setInt(1, user.getNumeroClient());
        ResultSet rs = pStmt.executeQuery();
        
        while (rs.next()){            
            Chambres room = new Chambres( rs.getInt("idChambre"), rs.getInt("idComplexe"), rs.getString("Type"), 
                    rs.getString("equipements"), rs.getInt("nombreLits"), rs.getFloat("prixHTVA")
            );
            
            Complexes complexe = new Complexes(
                 rs.getInt("idComplexe"), rs.getString("nomComplexe"), rs.getString("typeComplexe")
            );
            
            
            list.add(new BasketRow(complexe, room, rs.getDate("dateArrive"), rs.getInt("nbJours")));
        }
        
        return list;
    }
    
    
    /**
     *
     * @param ch
     * @param dateBeg
     * @param Nights
     * @param ClientRef
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public synchronized static int BookRoom(Chambres ch, java.sql.Date dateBeg, int Nights, Voyageurs ClientRef) throws SQLException, Exception {
        java.sql.Date now = new Date(new java.util.Date().getTime());
        
        if(! dateBeg.toLocalDate().isEqual(now.toLocalDate())){
            if(dateBeg.before(now)){
                System.out.println("DateBeg: " + dateBeg + ", now: " + now);
                throw new Exception("DateToEarly");
            }
        }
        
        
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
        pStmt.setObject(4, dateBeg);
        pStmt.setInt(5, Nights);
        
        int rs = pStmt.executeUpdate();
        mysql.commit();
        mysql.setAutoCommit(true);
        
        return rs;
    }
    
    /**
     *
     * @param ch
     * @param dateBeg
     * @param Nights
     * @return
     * @throws SQLException
     */
    public static boolean checkDate(Chambres ch, java.sql.Date dateBeg, int Nights) throws SQLException {
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

    /**
     *
     * @return
     * @throws SQLException
     */
    public synchronized static boolean PayRoom() throws SQLException {
        //Select and calcul price
        
        //
        
        return true;
    }
    
    /**
     *
     * @param room
     * @param dateBeg
     * @return
     * @throws SQLException
     */
    public synchronized static boolean CancelRoom(Chambres room, java.sql.Date dateBeg) throws SQLException {
        java.sql.Date nowDate = java.sql.Date.valueOf( LocalDate.now(TimeZone.getDefault().toZoneId()));
        
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
    
    /**
     *
     * @param select
     * @param from
     * @param where
     * @param isheader
     * @return
     * @throws SQLException
     */
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
