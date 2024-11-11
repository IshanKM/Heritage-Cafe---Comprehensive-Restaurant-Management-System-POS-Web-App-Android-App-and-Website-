/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.system;

import com.sbix.jnotify.NPosition;
import com.sbix.jnotify.NotifyType;
import com.sbix.jnotify.NotifyWindow;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import net.proteanit.sql.DbUtils;


public class reject extends javax.swing.JFrame 
{

    public reject() 
     {
        initComponents();
        displaykitchen();
        decorate();
        seticon();

     }
    
    public void seticon()
     {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon/companylogo.png")));
     }
    
    public void decorate()
        {
 
 
            JTableHeader addtocartheader = displayorder.getTableHeader();
            addtocartheader.setFont(new Font("Times New Roman", Font.BOLD,18));
            ((DefaultTableCellRenderer)addtocartheader.getDefaultRenderer())
                    .setHorizontalAlignment(JLabel.CENTER);
            
            displayorder.setRowHeight(50);

        }
    
    public void displaykitchen()
        {
            
            Thread th = new Thread()
                {
                    @Override
                    public void run()
                        {
                            try
                                {
                    
                                    for(;;)
                                        {
                                             MyQuery m = new MyQuery();
                                             Connection con = m.getConnection();
                                             Statement st;
                                             ResultSet rs;
                                             
                                             st = con.createStatement();
                                             rs = st.executeQuery("SELECT * FROM kitchen");
                                             displayorder.setModel(DbUtils.resultSetToTableModel(rs));
                                             sleep(60000);
                                             
                                        }
           
                                }
                            catch(Exception e)
                                {
                                     new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Someting Wrong .Try Again",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT); 
                                }

                        }
        
                    };
            th.start();
        }

    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        reject = new javax.swing.JEditorPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        okbtn = new javax.swing.JButton();
        closebtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        displayorder = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 5, true));

        reject.setFont(new java.awt.Font("Sylfaen", 0, 24)); // NOI18N
        reject.setForeground(new java.awt.Color(204, 0, 0));
        reject.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rejectMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(reject);

        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("REJECT BOX");

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("-> Do You Want To Cancel This Order ?");

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("-> Please Choose order You Want to Reject");

        okbtn.setBackground(new java.awt.Color(0, 0, 0));
        okbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/yes.png"))); // NOI18N
        okbtn.setBorder(null);
        okbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okbtnActionPerformed(evt);
            }
        });

        closebtn.setBackground(new java.awt.Color(0, 0, 0));
        closebtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/no.png"))); // NOI18N
        closebtn.setBorder(null);
        closebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closebtnActionPerformed(evt);
            }
        });

        displayorder = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex,int colIndex)
            {
                return false;
            }
        };
        displayorder.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        displayorder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "TIME", "DETAILS", "PRICE", "TABLE_NO", "IDs_in_Order", "QTYs_in_Order"
            }
        ));
        displayorder.setGridColor(new java.awt.Color(255, 255, 255));
        displayorder.setSelectionBackground(new java.awt.Color(204, 0, 0));
        displayorder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                displayorderMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                displayorderMouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(displayorder);

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("-> Please Explain the Reason why you Reject This Order");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(okbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(closebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(okbtn)
                    .addComponent(closebtn))
                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rejectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rejectMouseClicked
        
        try
        {
            Runtime.getRuntime().exec("cmd /c osk");
            reject.grabFocus();
        }
        catch (IOException ex)
        {
           new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Fail To get Key Board",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
        }
    }//GEN-LAST:event_rejectMouseClicked

    private void closebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebtnActionPerformed
       
        this.dispose();
    }//GEN-LAST:event_closebtnActionPerformed

    private void okbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okbtnActionPerformed
         
        int row = displayorder.getSelectedRow();
        String text = reject.getText();

        if (row < 0) 
         {
            
            //JOptionPane.showMessageDialog(null, "Please Select Row You Want to Reject");
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Select Order",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
         }
        else if ("".equals(reject.getText().trim()))
         {
             new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Enter The Reason",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
         }
        else
         {
            int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure You Want To Reject This ?","Confirm",JOptionPane.YES_NO_OPTION);
            if (confirm == 0)
             { 

              try
                {
                    Connection con = null;
                    PreparedStatement ps = null;
                    Class.forName("com.mysql.jdbc.Driver");
                    MyQuery m = new MyQuery();
                    con = m.getConnection();

                    int id = 0;
                    
                    LocalDate date = java.time.LocalDate.now();
                    
                    LocalTime time = java.time.LocalTime.now();

                    loginpage lp = new loginpage();
                    String details[] = lp.getdetails();
                    String username = details[0];
                    String password = details[1];

                    String nameemployee = m.getnameemployee(username , password);
                    
                    String alldetails = displayorder.getModel().getValueAt(row, 2 ).toString();
                    
                    int tblno = Integer.parseInt(displayorder.getModel().getValueAt(row, 4 ).toString());
                    
                    String reason = reject.getText();
                    
                    ps = con.prepareStatement("INSERT INTO rejected_order VALUES(?,?,?,?,?,?,?)");
                    ps.setInt(1, id);
                    ps.setString(2, date.toString());
                    ps.setString(3, time.toString());
                    ps.setString(4, nameemployee);
                    ps.setString(5,alldetails);
                    ps.setInt(6, tblno);
                    ps.setString(7,reason);
                    
                    ps.executeUpdate();
                    
                    //JOptionPane.showMessageDialog(null, "Reject Order Successfully");
                    new NotifyWindow(NotifyType.SUCCESS_NOTIFICATION," Reject Order Successfully",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                    this.dispose();

                } 
              catch (Exception e) 
                {
                   new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
                }
             }
         }
    }//GEN-LAST:event_okbtnActionPerformed

    private void displayorderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_displayorderMouseClicked

     

    }//GEN-LAST:event_displayorderMouseClicked

    private void displayorderMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_displayorderMouseEntered

    }//GEN-LAST:event_displayorderMouseEntered

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new reject().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closebtn;
    private javax.swing.JTable displayorder;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton okbtn;
    private javax.swing.JEditorPane reject;
    // End of variables declaration//GEN-END:variables
}
