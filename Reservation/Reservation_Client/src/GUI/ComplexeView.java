/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;


import ProtocolROMP.*;
import Entities.*;

import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.sql.Date;
import java.time.Instant;
import java.util.LinkedList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Arkios
 */
public class ComplexeView extends javax.swing.JDialog {
    private final Complexes selectedComp;
    private final LocalDate minDay;
    private LocalDate firstWeekDay;
    private LinkedList<CalendRow> listRow;
    private final InetSocketAddress Addr;

    /**
     * Creates new form ComplexeView
     */
    public ComplexeView(java.awt.Frame parent, boolean modal, Complexes c, InetSocketAddress ad) throws IOException, ClassNotFoundException {
        super(parent, modal);
        initComponents();
        Addr = ad;
        Calendar.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row == 0 ? Color.orange : row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
                c.setForeground(Color.black);
                return c;
            }
        });

        this.setTitle(c.getTypeComplexe() + ": " + c.getNomComplexe());
        selectedComp = c;

        firstWeekDay = LocalDate.now(ZoneId.systemDefault()).with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        minDay = LocalDate.now(ZoneId.systemDefault()).with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        refreshCalendar();
    }

    private void refreshCalendar() throws IOException, ClassNotFoundException {
        listRow = new LinkedList<>();
        listRow = getReservations();

        if (listRow == null) {
            return;
        }

        DefaultTableModel model = (DefaultTableModel) Calendar.getModel();
        model.setRowCount(0);
        model.addRow(getRowDate());

        for (CalendRow row : listRow) {
            model.addRow(row.toVector());
        }
        Calendar.setModel(model);
    }

    private Vector<LocalDate> getRowDate() {
        Vector<LocalDate> vec = new Vector<>();

        vec.add(null);
        LocalDate date = firstWeekDay;
        for (int i = 0; i < 7; i++) {
            vec.add(date);
            date = date.plusDays(1);
        }
        return vec;
    }

    private LinkedList<Chambres> getChambresList() throws IOException, ClassNotFoundException {
        LinkedList<Chambres> list = new LinkedList<Chambres>();
        Socket sock = new Socket();
        sock.connect(Addr);
        ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
            
        oos.writeObject(new ListRoomRequest(selectedComp));

        ObjectInputStream ios = new ObjectInputStream(sock.getInputStream());    
        Object r = ios.readObject();
        
        sock.close();
        
        if (r instanceof TimeOut) {
            JOptionPane.showMessageDialog(this, "Le serveur est éteint pour maintenance.", "Disconnect", JOptionPane.INFORMATION_MESSAGE);
            return list;
        }

        if (r instanceof ListRoomResponse) {
            ListRoomResponse req = (ListRoomResponse) r;

            switch (req.getCode()) {
                case ListRoomResponse.SUCCESS:
                    return req.getList();

                case ListRoomResponse.BADDB:
                    JOptionPane.showMessageDialog(this, "Erreur BDD: " + req.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    break;

                case ListRoomResponse.UNKOWN:
                default:
                    JOptionPane.showMessageDialog(this, "Erreur: " + req.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
        return list;
    }

    private LinkedList<CalendRow> getReservations() throws IOException, ClassNotFoundException {
        LinkedList<CalendRow> list = new LinkedList<CalendRow>();
        System.err.println("GetReservations: ");
        Socket sock = new Socket();
        sock.connect(Addr);
        ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
            
        
        oos.writeObject(new ListReservationRoomRequest(selectedComp, firstWeekDay));
        
        ObjectInputStream ios = new ObjectInputStream(sock.getInputStream());    
        Object r = ios.readObject();
        sock.close();
        
        System.err.println("ReadReservation Response");
        
        if (r instanceof TimeOut) {
            JOptionPane.showMessageDialog(this, "Le serveur est éteint pour maintenance.", "Disconnect", JOptionPane.INFORMATION_MESSAGE);
            return list;
        }

        if (r instanceof ListReservationRoomResponse) {
            ListReservationRoomResponse req = (ListReservationRoomResponse) r;

            switch (req.getCode()) {
                case ListReservationRoomResponse.SUCCESS:
                    return req.getList();

                case ListReservationRoomResponse.BADDB:
                    JOptionPane.showMessageDialog(this, "Erreur BDD: " + req.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    break;

                case ListReservationRoomResponse.UNKOWN:
                default:
                    JOptionPane.showMessageDialog(this, "Erreur: " + req.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
        return list;
    }

    private LinkedList<Voyageurs> getUsers() throws IOException, ClassNotFoundException {
        Socket sock = new Socket();
        sock.connect(Addr);
        
        ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());    
        oos.writeObject(new ListClientRequest());
        
        ObjectInputStream ios = new ObjectInputStream(sock.getInputStream());
        Object r = ios.readObject();

        
        sock.close();
        if (r instanceof TimeOut) {
            JOptionPane.showMessageDialog(this, "Le serveur est éteint pour maintenance.", "Disconnect", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        if (r instanceof ListClientResponse) {
            ListClientResponse req = (ListClientResponse) r;

            switch (req.getCode()) {
                case ListClientResponse.SUCCESS:
                    return req.getList();

                case ListClientResponse.BADDB:
                    JOptionPane.showMessageDialog(this, "Erreur BDD: " + req.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    break;

                case ListClientResponse.UNKOWN:
                default:
                    JOptionPane.showMessageDialog(this, "Erreur: " + req.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        Calendar = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        Booked = new javax.swing.JButton();
        UnList = new javax.swing.JButton();
        Payed = new javax.swing.JButton();
        weekLater = new javax.swing.JButton();
        weekBefore = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Calendar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Calendar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Chambres", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Calendar.setColumnSelectionAllowed(true);
        Calendar.setRowHeight(25);
        Calendar.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(Calendar);
        Calendar.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (Calendar.getColumnModel().getColumnCount() > 0) {
            Calendar.getColumnModel().getColumn(0).setMinWidth(200);
            Calendar.getColumnModel().getColumn(5).setResizable(false);
            Calendar.getColumnModel().getColumn(7).setResizable(false);
        }

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Chambres Reservée :");

        Booked.setText("Reserver");
        Booked.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BookedActionPerformed(evt);
            }
        });

        UnList.setText("Retirer Reservation");
        UnList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UnListActionPerformed(evt);
            }
        });

        Payed.setText("Payement");
        Payed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PayedActionPerformed(evt);
            }
        });

        weekLater.setText("--->");
        weekLater.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weekLaterActionPerformed(evt);
            }
        });

        weekBefore.setText("<---");
        weekBefore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weekBeforeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1078, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(weekBefore)
                                .addGap(26, 26, 26)))
                        .addComponent(weekLater))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Booked, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(UnList, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(150, 150, 150)
                        .addComponent(Payed, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(weekLater)
                            .addComponent(weekBefore))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Booked, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UnList, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Payed, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void weekBeforeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weekBeforeActionPerformed
        
        if (minDay.isAfter(firstWeekDay.minusWeeks(1))) {
            return;
        }

        firstWeekDay = firstWeekDay.minusWeeks(1);
        try {
            refreshCalendar();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ComplexeView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_weekBeforeActionPerformed

    private void weekLaterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weekLaterActionPerformed
        
        firstWeekDay = firstWeekDay.plusWeeks(1);
        try {
            refreshCalendar();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ComplexeView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_weekLaterActionPerformed

    private void BookedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BookedActionPerformed
        
        int col = Calendar.getSelectedColumn();
        int row = Calendar.getSelectedRow();

        Chambres selectedRoom;
        LocalDate selectedDate;
        LinkedList<Voyageurs> userList;
        LinkedList<Chambres> roomList;

        if (col < 1 || row < 1) {
            JOptionPane.showMessageDialog(this, "Vous devez sélectionner un crénaux horaires", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        row--;

        if (!isValidSelection(col, row, listRow)) {
            JOptionPane.showMessageDialog(this, "Le crénaux n'est pas libre.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //Recuperation chambre selectionner
        selectedRoom = listRow.get(row).getChambre();
        selectedDate = (LocalDate) getRowDate().get(col);

        try {
            userList = getUsers();
            if (userList == null) {
                JOptionPane.showMessageDialog(this, "Connexion au serveur interrompue, veuillez réessayer plus tard.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ComplexeView.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        try {
            roomList = getChambresList();
            if (roomList == null) {
                JOptionPane.showMessageDialog(this, "Connexion au serveur interrompue, veuillez réessayer plus tard.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ComplexeView.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        //Creation d'un dialog
        BookRoom window = new BookRoom(null, true, Addr, selectedRoom, selectedDate, userList, roomList);
        window.setVisible(true);

        try {
            this.refreshCalendar();
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Connexion au serveur interrompue, veuillez réessayer plus tard.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_BookedActionPerformed

    private void UnListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UnListActionPerformed

        int col = Calendar.getSelectedColumn();
        int row = Calendar.getSelectedRow();

        Chambres selectedRoom;
        LocalDate selectedDate;
        LinkedList<Voyageurs> userList;
        LinkedList<Chambres> roomList;

        if (col < 1 || row < 1) {
            JOptionPane.showMessageDialog(this, "Vous devez sélectionner un crénaux horaires", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        row--;

        if (isValidSelection(col, row, listRow)) {
            JOptionPane.showMessageDialog(this, "Aucune réservation sélectionnée", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        final int ret = JOptionPane.showConfirmDialog(this, "Êtes vous sur de vouloir supprimer cette réservation ?", "Confirmation Supression", JOptionPane.OK_CANCEL_OPTION);
        if (ret != 0) {
            return;
        }

        selectedRoom = listRow.get(row).getChambre();
        selectedDate = getRowDate(col, row);

        try {
            CancelReservation(selectedRoom, java.sql.Date.valueOf(selectedDate));

            this.refreshCalendar();

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ComplexeView.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Erreur: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_UnListActionPerformed

    private boolean CancelReservation(Chambres room, java.sql.Date begdate) throws IOException, ClassNotFoundException {
        Socket sock = new Socket();
        sock.connect(Addr);
        
        ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
        oos.writeObject(new CancelRoomRequest(room, begdate));
        
        ObjectInputStream ios = new ObjectInputStream(sock.getInputStream());        
        Object r = ios.readObject();
        
        sock.close();
        if (r instanceof TimeOut) {
            JOptionPane.showMessageDialog(this, "Le serveur est éteint pour maintenance.", "Disconnect", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        if (r instanceof CancelRoomResponse) {
            CancelRoomResponse req = (CancelRoomResponse) r;

            switch (req.getCode()) {
                case CancelRoomResponse.SUCCESS:
                    JOptionPane.showMessageDialog(this, "Suppression Réussie", "Success", JOptionPane.INFORMATION_MESSAGE);
                    return true;

                case CancelRoomResponse.KO:
                    JOptionPane.showMessageDialog(this, "Suppression Impossible:\nDate sélectionnée déjà passée", "Warning", JOptionPane.WARNING_MESSAGE);
                    return false;

                case CancelRoomResponse.BADDB:
                    JOptionPane.showMessageDialog(this, "Erreur BDD: " + req.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    break;

                case CancelRoomResponse.UNKOWN:
                default:
                    JOptionPane.showMessageDialog(this, "Erreur: " + req.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
        return false;
    }

    private boolean isValidSelection(int col, int row, LinkedList<CalendRow> tmpRows) {
        if (tmpRows.get(row).getD1() == null && col == 1) {
            return true;
        }
        if (tmpRows.get(row).getD2() == null && col == 2) {
            return true;
        }
        if (tmpRows.get(row).getD3() == null && col == 3) {
            return true;
        }
        if (tmpRows.get(row).getD4() == null && col == 4) {
            return true;
        }
        if (tmpRows.get(row).getD5() == null && col == 5) {
            return true;
        }
        if (tmpRows.get(row).getD6() == null && col == 6) {
            return true;
        }
        if (tmpRows.get(row).getD7() == null && col == 7) {
            return true;
        }
        return false;
    }

    private void PayedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PayedActionPerformed
        int col = Calendar.getSelectedColumn();
        int row = Calendar.getSelectedRow();
        CreditCard selectedCredit = null;
        Chambres selectedRoom = null;
        LocalDate selectedDate = null;
        Voyageurs selectedUser = null;

        LinkedList<Voyageurs> userList;
        LinkedList<Chambres> roomList;

        if (col < 1 || row < 1) {
            JOptionPane.showMessageDialog(this, "Vous devez sélectionner un crénaux horaires", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        row--;

        if (isValidSelection(col, row, listRow)) {
            JOptionPane.showMessageDialog(this, "Aucune réservation sélectionnée", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //Recuperation Date + Chambre
        selectedRoom = listRow.get(row).getChambre();
        selectedDate = getRowDate(col, row);

        try {

            selectedUser = getRowUser(col, row);

            //Creation d'une fenètre d'input
            selectedCredit = getCard(selectedUser, getHowMuchToPay(selectedRoom, getReservation(col, row)));

            //Send Payement Request
            sendPayementRequest(selectedRoom, LocaltoDate(selectedDate), selectedUser, selectedCredit);
            System.err.println(selectedCredit);

            this.refreshCalendar();

        } catch (IOException | ClassNotFoundException ex) {
            return;
        } catch (Exception ex) {
            Logger.getLogger(ComplexeView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_PayedActionPerformed

    private void sendPayementRequest(Chambres room, java.sql.Date begd, Voyageurs cl, CreditCard cC) throws IOException, ClassNotFoundException {
       Socket sock = new Socket();
        sock.connect(Addr);
        ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
        oos.writeObject(new PayRoomRequest(room, begd, cl, cC));
        
        ObjectInputStream ios = new ObjectInputStream(sock.getInputStream());
        Object r = ios.readObject();

        if (r instanceof TimeOut) {
            JOptionPane.showMessageDialog(this, "Le serveur est éteint pour maintenance.", "Disconnect", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (r instanceof PayRoomResponse) {
            PayRoomResponse req = (PayRoomResponse) r;

            switch (req.getCode()) {
                case PayRoomResponse.SUCCESS:
                    return;

                case PayRoomResponse.BADDB:
                    JOptionPane.showMessageDialog(this, "Erreur BDD: " + req.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    break;

                case PayRoomResponse.UNKOWN:
                default:
                    JOptionPane.showMessageDialog(this, "Erreur: " + req.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
        return;
    }

    private double getHowMuchToPay(Chambres selectedRoom, Reservationchambre reserv) throws IOException, ClassNotFoundException {
        Socket sock = new Socket();
        sock.connect(Addr);
        ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
        oos.writeObject(new HowMuchToPayRequest(selectedRoom, reserv));
        
        ObjectInputStream ios = new ObjectInputStream(sock.getInputStream());
        Object r = ios.readObject();

        
        sock.close();
        if (r instanceof TimeOut) {
            JOptionPane.showMessageDialog(this, "Le serveur est éteint pour maintenance.", "Disconnect", JOptionPane.INFORMATION_MESSAGE);
            return -1;
        }

        if (r instanceof HowMuchToPayResponse) {
            HowMuchToPayResponse req = (HowMuchToPayResponse) r;

            switch (req.getCode()) {
                case HowMuchToPayResponse.SUCCESS:
                    return req.getHowMuchToPay();

                case HowMuchToPayResponse.BADDB:
                    JOptionPane.showMessageDialog(this, "Erreur BDD: " + req.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    break;

                case HowMuchToPayResponse.UNKOWN:
                default:
                    JOptionPane.showMessageDialog(this, "Erreur: " + req.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
        return -1;
    }

    private CreditCard getCard(Voyageurs selectedUser, double howmuchtopay) throws Exception {
        CreditCard creditcard = new CreditCard();

        CreditCardGUI window = new CreditCardGUI(null, true, creditcard, selectedUser, howmuchtopay);
        window.setVisible(true);

        return creditcard;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Booked;
    private javax.swing.JTable Calendar;
    private javax.swing.JButton Payed;
    private javax.swing.JButton UnList;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton weekBefore;
    private javax.swing.JButton weekLater;
    // End of variables declaration//GEN-END:variables
    public LocalDate DatetoLocal(java.util.Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public java.sql.Date LocaltoDate(LocalDate dateToConvert) {
        return Date.valueOf(dateToConvert);
    }

    private Reservationchambre getReservation(int col, int row) {
        Reservationchambre reserv = null;
        switch (col) {
            case 1:
                reserv = listRow.get(row).getD1();
                break;
            case 2:
                reserv = listRow.get(row).getD2();
                break;
            case 3:
                reserv = listRow.get(row).getD3();
                break;
            case 4:
                reserv = listRow.get(row).getD4();
                break;
            case 5:
                reserv = listRow.get(row).getD5();
                break;
            case 6:
                reserv = listRow.get(row).getD6();
                break;
            case 7:
                reserv = listRow.get(row).getD7();
                break;
        }

        return reserv;
    }

    private Voyageurs getRowUser(int col, int row) throws IOException, ClassNotFoundException {
        int id = -1;
        switch (col) {
            case 1:
                id = listRow.get(row).getD1().getIdVoyageur();
                break;
            case 2:
                id = listRow.get(row).getD2().getIdVoyageur();
                break;
            case 3:
                id = listRow.get(row).getD3().getIdVoyageur();
                break;
            case 4:
                id = listRow.get(row).getD4().getIdVoyageur();
                break;
            case 5:
                id = listRow.get(row).getD5().getIdVoyageur();
                break;
            case 6:
                id = listRow.get(row).getD6().getIdVoyageur();
                break;
            case 7:
                id = listRow.get(row).getD7().getIdVoyageur();
                break;
        }

        LinkedList<Voyageurs> vlist = getUsers();

        for (Voyageurs v : vlist) {
            if (v.getNumeroClient() == id) {
                return v;
            }
        }

        throw new ClassNotFoundException();
    }

    private LocalDate getRowDate(int col, int row) {
        java.util.Date d = null;
        switch (col) {
            case 1:
                d = listRow.get(row).getD1().getDateArrive();
                break;
            case 2:
                d = listRow.get(row).getD2().getDateArrive();
                break;
            case 3:
                d = listRow.get(row).getD3().getDateArrive();
                break;
            case 4:
                d = listRow.get(row).getD4().getDateArrive();
                break;
            case 5:
                d = listRow.get(row).getD5().getDateArrive();
                break;
            case 6:
                d = listRow.get(row).getD6().getDateArrive();
                break;
            case 7:
                d = listRow.get(row).getD7().getDateArrive();
                break;
        }
        return DatetoLocal(d);
    }
}
