
package pos.system;

import com.sbix.jnotify.NPosition;
import com.sbix.jnotify.NotifyType;
import com.sbix.jnotify.NotifyWindow;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import net.proteanit.sql.DbUtils;


public class tables extends javax.swing.JFrame {

 
    public tables()
    {
        initComponents();
        showtable();
        decorate();
        seticon();
        getdelivery();
    }
    
    public void seticon()
     {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon/companylogo.png")));
     }

    public void showtable()
        {

                            try
                                {
                                    MyQuery m = new MyQuery();
                                    Connection con = m.getConnection();
                                    Statement st; 
                                    ResultSet rs;
                                    
                                    st = con.createStatement();
                                    rs = st.executeQuery("SELECT ID,Table_No,Status FROM tables");
                                    tableshow.setModel(DbUtils.resultSetToTableModel(rs));

                                        
                                }
                            catch(Exception e)
                                {
                                    new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
                                }

        }
    
    public void getdelivery()
    {
        try
        {
        
            MyQuery mq = new MyQuery();
            Connection con = mq.getConnection();
            Statement st = con.createStatement();
            double price = 0;

            ResultSet rs = st.executeQuery( "select * from delivert_price where ID = "+1+"");
            while(rs.next())
                {
                    price = rs.getDouble("Delivery_Charge");
                }
            
            delivery.setText(Double.toString(price));
        }
        catch(Exception e)
        {
            
        }
    }
    
    public void decorate()
      {
        //showemployee decorate 
        
        JTableHeader header = tableshow.getTableHeader();
        header.setForeground(Color.red);
        header.setFont(new Font("Times New Roman", Font.BOLD,16));
        ((DefaultTableCellRenderer)header.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);

        tableshow.setRowHeight(50);
        tableshow.setShowGrid(false);
        tableshow.setSelectionForeground(Color.white);
        
      }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttongroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        absentlabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableshow = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        tableno = new javax.swing.JLabel();
        available = new javax.swing.JRadioButton();
        unavailable = new javax.swing.JRadioButton();
        submit = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        delete = new javax.swing.JButton();
        tablecount = new javax.swing.JSpinner();
        addbtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        delivery = new javax.swing.JTextField();
        submit1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0), 5));

        absentlabel4.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        absentlabel4.setForeground(new java.awt.Color(255, 255, 255));
        absentlabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        absentlabel4.setText("TABLES MANAGE");
        absentlabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        tableshow = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex,int colIndex)
            {
                return false;
            }
        };
        tableshow.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        tableshow.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Table No", "Status"
            }
        ));
        tableshow.setSelectionBackground(new java.awt.Color(255, 0, 0));
        tableshow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableshowMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableshow);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/close.png"))); // NOI18N
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });

        tableno.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        tableno.setForeground(new java.awt.Color(255, 255, 255));
        tableno.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tableno.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        available.setBackground(new java.awt.Color(0, 0, 0));
        buttongroup.add(available);
        available.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        available.setForeground(new java.awt.Color(255, 255, 255));
        available.setText("available");
        available.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                availableActionPerformed(evt);
            }
        });

        unavailable.setBackground(new java.awt.Color(0, 0, 0));
        buttongroup.add(unavailable);
        unavailable.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        unavailable.setForeground(new java.awt.Color(255, 255, 255));
        unavailable.setText("unavailable");

        submit.setBackground(new java.awt.Color(0, 0, 255));
        submit.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        submit.setForeground(new java.awt.Color(255, 255, 255));
        submit.setText("SUBMIT");
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("DELIVERY CHARGE");

        delete.setBackground(new java.awt.Color(255, 0, 0));
        delete.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        delete.setForeground(new java.awt.Color(255, 255, 255));
        delete.setText("DELETE");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        tablecount.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        tablecount.setModel(new javax.swing.SpinnerNumberModel(0, 0, 10, 1));

        addbtn.setBackground(new java.awt.Color(0, 153, 51));
        addbtn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        addbtn.setForeground(new java.awt.Color(255, 255, 255));
        addbtn.setText("ADD");
        addbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addbtnActionPerformed(evt);
            }
        });

        jLabel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));

        jLabel3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("ADD TABLE");

        delivery.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        delivery.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        delivery.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deliveryMouseClicked(evt);
            }
        });

        submit1.setBackground(new java.awt.Color(0, 0, 255));
        submit1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        submit1.setForeground(new java.awt.Color(255, 255, 255));
        submit1.setText("UPDATE");
        submit1.setFocusPainted(false);
        submit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submit1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(absentlabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(tableno, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(available)
                                    .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(unavailable)
                                        .addGap(18, 18, 18))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(5, 5, 5))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(180, 180, 180)
                                .addComponent(tablecount, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(addbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(delivery)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(162, 162, 162)
                .addComponent(submit1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(jLabel4)
                    .addContainerGap(336, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(absentlabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(available, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(unavailable, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(tableno, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tablecount, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(delivery, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(submit1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(535, 535, 535)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(140, 140, 140)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(505, 713));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked

            dispose();
    }//GEN-LAST:event_jLabel8MouseClicked

    private void availableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_availableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_availableActionPerformed

    private void tableshowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableshowMouseClicked
        
        int row = tableshow.getSelectedRow();
        int tblno = Integer.parseInt(tableshow.getModel().getValueAt(row, 1 ).toString());
        tableno.setText(Integer.toString(tblno));
        
        String status = tableshow.getModel().getValueAt(row, 2 ).toString();
        buttongroup.clearSelection();
        
        if ("available".equals(status)) 
        {
            available.setSelected(true);
        } 
        else if("unavailable".equals(status))
        {
            unavailable.setSelected(true);
        }
        
        
        
    }//GEN-LAST:event_tableshowMouseClicked

    private void submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitActionPerformed
       
        try 
         {
            int row = tableshow.getSelectedRow();
            
            available.setActionCommand("available");
            unavailable.setActionCommand("unavailable");

             if (row >= 0) 
                {
                    int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
                    if (confirm == 0)
                        { 
                                int id = Integer.parseInt(tableshow.getModel().getValueAt(row, 0).toString());
                                String getradio = buttongroup.getSelection().getActionCommand();
                                
                                MyQuery m = new MyQuery();
                                Connection con = m.getConnection();
                                PreparedStatement pst = null;
                                ResultSet rs = null;
      
                                pst = con.prepareStatement("UPDATE tables SET Status =? WHERE ID = "+id+"");
                                pst.setString(1, getradio);
                                pst.executeUpdate();
                                
                                //JOptionPane.showMessageDialog(null, "Update Succesfullly ");
                                new NotifyWindow(NotifyType.SUCCESS_NOTIFICATION,"Update Succesfullly ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                                
                                showtable();
                        }
                          
                }
             else
               {
                    new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Select Table",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
               }
            
            
         }
        catch (Exception e)
         {
              new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
         }

    }//GEN-LAST:event_submitActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        
        try 
         {
            int row = tableshow.getSelectedRow();
            
             if (row >= 0) 
                {
                    int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
                    if (confirm == 0)
                        { 
                                int id = Integer.parseInt(tableshow.getModel().getValueAt(row, 0).toString());
                                  
                                MyQuery m = new MyQuery();
                                Connection con = m.getConnection();
                                PreparedStatement pst = null;
                                ResultSet rs = null;
      
                                pst = con.prepareStatement("DELETE FROM tables WHERE ID = "+id+"");
                                pst.executeUpdate();
                                
                                //JOptionPane.showMessageDialog(null, "Update Succesfullly ");
                                new NotifyWindow(NotifyType.SUCCESS_NOTIFICATION,"Delete Succesfullly ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                                
                                showtable();
                        }
                          
                }
             else
               {
                    new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Select Table",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
               }
            
            
         }
        catch (Exception e)
         {
              new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
         }
        
        
    }//GEN-LAST:event_deleteActionPerformed

    private void addbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addbtnActionPerformed
        
        try 
         {
           int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
           if (confirm == 0)
            {
             int counttable = Integer.parseInt(tablecount.getValue().toString());
            
             if (counttable != 0) 
              {
                 MyQuery m = new MyQuery();
                 Connection con = m.getConnection();
                 Statement st;
                 PreparedStatement ps = null;
                 ResultSet rs;
                 int tableno = 0;

                 st = con.createStatement();
                 for (int i = 0; i < counttable; i++)
                  {
                    rs = st.executeQuery( "SELECT Table_No FROM tables order by Table_No desc limit 1");
                    while(rs.next())
                        {
                          tableno = rs.getInt("Table_No");
                        }
                    ps = con.prepareStatement("INSERT INTO tables VALUES(?,?,?)");
                    ps.setInt(1, tableno+1);
                    ps.setInt(2, tableno+1);
                    ps.setString(3, "available");
                    ps.executeUpdate();
                  }

                 new NotifyWindow(NotifyType.SUCCESS_NOTIFICATION,"Successfully Added",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                 tablecount.setValue(0);
                 showtable();
                 
              } 
             else 
              {
                   new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Add Table Box is Empty",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
              }
            }
         }
        catch (Exception e)
         {
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
            JOptionPane.showMessageDialog(this, e);
         }
    }//GEN-LAST:event_addbtnActionPerformed

    private void submit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submit1ActionPerformed
        
        try 
        {
            int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
            if (confirm == 0)
            { 
                  
                MyQuery m = new MyQuery();
                Connection con = m.getConnection();
                PreparedStatement pst = null;
                ResultSet rs = null;
      
                pst = con.prepareStatement("UPDATE delivert_price SET Delivery_Charge =? WHERE ID = "+1+"");
                pst.setString(1, delivery.getText().trim());
                pst.executeUpdate();
                getdelivery();            
                //JOptionPane.showMessageDialog(null, "Update Succesfullly ");
                new NotifyWindow(NotifyType.SUCCESS_NOTIFICATION,"Update Succesfullly ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                                
            }
        } 
        catch (Exception e) 
        {
            MyQuery m = new MyQuery();
            Connection con = m.getConnection();
            PreparedStatement pst = null;
            ResultSet rs = null;
      
            try 
            {
                pst = con.prepareStatement("INSERT INTO `delivert_price`(ID,Delivery_Charge) VALUES (?,?)");
                pst.setInt(1, 1);
                pst.setString(2, delivery.getText().trim());
                pst.executeUpdate(); 
                new NotifyWindow(NotifyType.SUCCESS_NOTIFICATION,"Update Succesfullly ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                getdelivery();
            } 
            catch (SQLException ex)
            {
                Logger.getLogger(tables.class.getName()).log(Level.SEVERE, null, ex);
            }
   
        }
        
    }//GEN-LAST:event_submit1ActionPerformed

    private void deliveryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deliveryMouseClicked
       
        try
        {
            Runtime.getRuntime().exec("cmd /c osk");
            delivery.grabFocus();
        }
        catch (IOException ex)
        {
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Fail To Get KeyBoard",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
        }
    }//GEN-LAST:event_deliveryMouseClicked

    
    public static void main(String args[])
    {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new tables().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel absentlabel4;
    private javax.swing.JButton addbtn;
    private javax.swing.JRadioButton available;
    private javax.swing.ButtonGroup buttongroup;
    private javax.swing.JButton delete;
    private javax.swing.JTextField delivery;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton submit;
    private javax.swing.JButton submit1;
    private javax.swing.JSpinner tablecount;
    private javax.swing.JLabel tableno;
    private javax.swing.JTable tableshow;
    private javax.swing.JRadioButton unavailable;
    // End of variables declaration//GEN-END:variables
}
