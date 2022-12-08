/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import ReservationDataLayer.entities.CalendRow;
import ProtocolROMP.*;
import ProtocolROMP.TimeOut;
import ReservationDataLayer.entities.Chambres;
import ReservationDataLayer.entities.Complexes;
import ReservationDataLayer.entities.Reservationchambre;
import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
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
    private ObjectOutputStream oos;
    private ObjectInputStream ios;
    private Socket sock;
    private LocalDate firstWeekDay;
    
    /**
     * Creates new form ComplexeView
     */
    public ComplexeView(java.awt.Frame parent, boolean modal, Complexes c) throws IOException, ClassNotFoundException {
        super(parent, modal);
        initComponents();
        
        Calendar.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row == 0 ? Color.orange : row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
                c.setForeground(Color.black);
                return c;
            }
        });
        
        
        oos = null;
        ios = null;
        sock = null;
        
        if(parent instanceof MainApp){
            MainApp p = (MainApp) parent;
            oos = p.getOos();
            ios = p.getIos();
            sock = p.getSock();
        }
        
        this.setTitle(c.getTypeComplexe() + ": " +c.getNomComplexe());
        selectedComp = c;
        
        firstWeekDay = LocalDate.now( ZoneId.systemDefault()).with( TemporalAdjusters.previous( DayOfWeek.MONDAY ) );
        refreshCalendar();
    }
    
    private void refreshCalendar() throws IOException, ClassNotFoundException{
        LinkedList<CalendRow> listRow = new LinkedList<CalendRow>();
        listRow = getReservations();
        
        if(listRow == null) return;
        
        DefaultTableModel model = (DefaultTableModel) Calendar.getModel();
        model.setRowCount(0);
        model.addRow(getRowDate());
        
        for( CalendRow row : listRow){
            model.addRow(row.toVector());
        }
        
        Calendar.setModel(model);
    }
    
    private Vector getRowDate(){
        Vector vec = new Vector();
        
        vec.add("Chambres:");
        LocalDate date = firstWeekDay;
        for(int i = 0; i<7 ; i++){
            vec.add(date.toString());
            date = date.plusDays(1);
        }
        return vec;
    }
    
    private LinkedList<Chambres> getChambresList() throws IOException, ClassNotFoundException{
        LinkedList<Chambres> list = new LinkedList<Chambres>();
        
        oos.writeObject(new ListRoomRequest(selectedComp));
        
        Object r = ios.readObject();
        
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
    
    private LinkedList<CalendRow> getReservations() throws IOException, ClassNotFoundException{
        LinkedList<CalendRow> list = new LinkedList<CalendRow>();
        
        oos.writeObject(new ListReservationRoomRequest(selectedComp, firstWeekDay));
        Object r = ios.readObject();
        
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
                "", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"
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

        UnList.setText("Libérer");

        Payed.setText("Marquer comme Payé");

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(UnList, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(Payed)))
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
}
