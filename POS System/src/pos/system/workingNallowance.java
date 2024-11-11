
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
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import net.proteanit.sql.DbUtils;


public class workingNallowance extends javax.swing.JFrame {

  
    public workingNallowance() 
      {
        initComponents();
        decorate();
        showallowance();
        seticon();
      }
    
    public void seticon()
     {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon/companylogo.png")));
     }
    public void getworkingdays()
      {
           Connection con = null;
           Statement st;
           ResultSet rs;
           
           try 
           {
              MyQuery mq = new MyQuery();
              con = mq.getConnection();
              st = con.createStatement();
            
              rs = st.executeQuery( " ");
              while(rs.next())
              {
                  
              }
           }
           
           catch (Exception e) 
           {
               
           }  new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
      
      }
    
    public void decorate()
      {
        //maintbl
          
        JTableHeader maintblheader = maintbl.getTableHeader();
        maintblheader.setForeground(Color.red);
        maintblheader.setFont(new Font("Times New Roman", Font.BOLD,16));
        ((DefaultTableCellRenderer)maintblheader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);

        maintbl.setRowHeight(50);
        maintbl.setShowGrid(true);
        maintbl.setSelectionForeground(Color.white);
        
        
      }
    

    public void showallowance()
        {

                            try
                                {
                                    LocalDate date = java.time.LocalDate.now();
                                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
                                    showdate.setText(dtf.format(date));
                                    
                                    MyQuery m = new MyQuery();
                                    Connection con = m.getConnection();
                                    Statement st; 
                                    ResultSet rs;
                                    
                                    st = con.createStatement();
                                    rs = st.executeQuery("SELECT * FROM workingdays_month ORDER BY Month DESC");
                                    maintbl.setModel(DbUtils.resultSetToTableModel(rs));

                                        
                                }
                            catch(Exception e)
                                {
                                    new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
                                }

        }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        workingdays = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        allowance = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        pay = new javax.swing.JTextField();
        addbtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        maintbl = new javax.swing.JTable();
        showdate = new javax.swing.JLabel();
        updatebtn = new javax.swing.JButton();
        clearbtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 4));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("WORKING DAYS & ALLOWANCE");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 4, 0, new java.awt.Color(255, 255, 255)));

        workingdays.setBackground(new java.awt.Color(0, 0, 0));
        workingdays.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        workingdays.setForeground(new java.awt.Color(255, 255, 255));
        workingdays.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        workingdays.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        workingdays.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                workingdaysMouseClicked(evt);
            }
        });
        workingdays.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                workingdaysKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Enter How many Working days are This Month?");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Enter Allowance Day Limit This Month");

        allowance.setBackground(new java.awt.Color(0, 0, 0));
        allowance.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        allowance.setForeground(new java.awt.Color(255, 255, 255));
        allowance.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        allowance.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        allowance.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                allowanceMouseClicked(evt);
            }
        });
        allowance.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                allowanceKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                allowanceKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Enter How Many Pay You For Allowance(%)");

        pay.setBackground(new java.awt.Color(0, 0, 0));
        pay.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        pay.setForeground(new java.awt.Color(255, 255, 255));
        pay.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pay.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        pay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                payMouseClicked(evt);
            }
        });
        pay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                payKeyReleased(evt);
            }
        });

        addbtn.setBackground(new java.awt.Color(255, 255, 255));
        addbtn.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        addbtn.setText("ADD");
        addbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addbtnActionPerformed(evt);
            }
        });

        maintbl = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex,int colIndex)
            {
                return false;
            }
        };
        maintbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "No Of Days", "Allowance Days", "Allowance Pay"
            }
        ));
        maintbl.setSelectionBackground(new java.awt.Color(255, 0, 0));
        maintbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                maintblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(maintbl);

        showdate.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        showdate.setForeground(new java.awt.Color(255, 255, 255));
        showdate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        updatebtn.setBackground(new java.awt.Color(255, 255, 255));
        updatebtn.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        updatebtn.setText("UPDATE");
        updatebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatebtnActionPerformed(evt);
            }
        });

        clearbtn.setBackground(new java.awt.Color(255, 255, 255));
        clearbtn.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        clearbtn.setText("CLEAR");
        clearbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbtnActionPerformed(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/close_1.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(allowance, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(workingdays, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pay)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(addbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updatebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(showdate, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)))
                .addGap(29, 29, 29))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(showdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(workingdays, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(allowance, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pay, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updatebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addbtnActionPerformed
        try 
        {
            
             if (!"".equals(workingdays.getText()) && !"".equals(allowance.getText()) && !"".equals(pay.getText())) 
             {
                 int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
                 if (confirm == 0)
                 {
                    LocalDate date = java.time.LocalDate.now();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
                    showdate.setText(dtf.format(date));

                    MyQuery m = new MyQuery();
                    Connection con = m.getConnection();
                    ResultSet rs;
                    PreparedStatement pst = null;
                    

                    String query = "select * from workingdays_month where Month = '"+dtf.format(date)+"'";
                    pst = con.prepareStatement(query);
                    rs = pst.executeQuery();

                    if(rs.next())
                        {
                            //JOptionPane.showMessageDialog(this,"You Already Added this Information For this month");
                            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"You Already Added For this month",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);

                        }
                    else
                        {
                            query = "INSERT INTO workingdays_month VALUES(?,?,?,?) ";
                            pst = con.prepareStatement(query);
                            pst.setString(1, dtf.format(date) );
                            pst.setInt(2, Integer.parseInt(workingdays.getText()));
                            pst.setInt(3, Integer.parseInt(allowance.getText()));
                            pst.setDouble(4, Double.parseDouble(pay.getText()));
                            pst.executeUpdate();
                            
                            //JOptionPane.showMessageDialog(null, "Successfull Added");
                            new NotifyWindow(NotifyType.SUCCESS_NOTIFICATION,"Successfull Added",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                            
                            workingdays.setText("");
                            allowance.setText("");
                            pay.setText("");
                            
                            showallowance();
                        } 

                 }
             }
            else 
             {
                   //JOptionPane.showMessageDialog(null, "Please Fill All of This Details");
                   new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Fill All of This Details",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
             }
            
        } 
        catch (Exception e) 
        {
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
        }
       
    }//GEN-LAST:event_addbtnActionPerformed

    private void maintblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_maintblMouseClicked
        
        
    }//GEN-LAST:event_maintblMouseClicked

    private void updatebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatebtnActionPerformed
        try 
        {
            
             if (!"".equals(workingdays.getText()) && !"".equals(allowance.getText()) && !"".equals(pay.getText())) 
             {
                int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
                if (confirm == 0)
                 {
                    LocalDate date = java.time.LocalDate.now();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
                    showdate.setText(dtf.format(date));

                    MyQuery m = new MyQuery();
                    Connection con = m.getConnection();
                    ResultSet rs;
                    PreparedStatement pst = null;
                    

                    String query = "Update workingdays_month SET noOf_Days = ? ,allowances_day = ? ,allowances_Pay = ? Where Month = '"+dtf.format(date)+"' ";
                    pst = con.prepareStatement(query);
                    pst.setInt(1, Integer.parseInt(workingdays.getText()));
                    pst.setInt(2, Integer.parseInt(allowance.getText()));
                    pst.setDouble(3, Double.parseDouble(pay.getText()));
                    pst.executeUpdate();
                            
                    //JOptionPane.showMessageDialog(null, "Successfull Update");
                    new NotifyWindow(NotifyType.SUCCESS_NOTIFICATION,"Successfull Update",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                    
                    workingdays.setText("");
                    allowance.setText("");
                    pay.setText("");
                    
                    showallowance();
                 }
              }
            else 
             {
                   //JOptionPane.showMessageDialog(null, "Please Fill All of This Details");
                   new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Fill All of This Details",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
             }
            
        } 
        catch (Exception e) 
        {
           new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
        }
       
    }//GEN-LAST:event_updatebtnActionPerformed

    private void clearbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbtnActionPerformed
        
        workingdays.setText("");
        allowance.setText("");
        pay.setText("");
    }//GEN-LAST:event_clearbtnActionPerformed

    private void workingdaysMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_workingdaysMouseClicked
        
        try
          {
            Runtime.getRuntime().exec("cmd /c osk");
            workingdays.grabFocus();
          }
        catch (IOException ex)
          {
             //JOptionPane.showMessageDialog(null, "Fail to get Touch Key Board ");
             new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Fail to get Touch Key Board ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
          }
    }//GEN-LAST:event_workingdaysMouseClicked

    private void allowanceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_allowanceMouseClicked
       
         try
          {
            Runtime.getRuntime().exec("cmd /c osk");
            allowance.grabFocus();
          }
        catch (IOException ex)
          {
             //JOptionPane.showMessageDialog(null, "Fail to get Touch Key Board ");
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Fail to get Touch Key Board ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
          }
    }//GEN-LAST:event_allowanceMouseClicked

    private void payMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_payMouseClicked
       
        try
          {
            Runtime.getRuntime().exec("cmd /c osk");
            pay.grabFocus();
          }
        catch (IOException ex)
          {
             //JOptionPane.showMessageDialog(null, "Fail to get Touch Key Board ");
             new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Fail to get Touch Key Board ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
          }
    }//GEN-LAST:event_payMouseClicked

    private void workingdaysKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_workingdaysKeyReleased

            try 
             {
                int i = Integer.parseInt(workingdays.getText());
                if (Integer.parseInt(workingdays.getText()) > 31 || Integer.parseInt(workingdays.getText()) < 1) 
                 {
                     //JOptionPane.showMessageDialog(null, "The date should be between 1 and 31 ");
                     new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"The date should be between 1 and 31 ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                     workingdays.setText("");
                 }
                        
             } 
            catch (Exception e) 
             {
                 //JOptionPane.showMessageDialog(null, "Please Input Numbers ");
                 new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Input Numbers ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                 workingdays.grabFocus();
                 workingdays.setText("");
             }
        
    }//GEN-LAST:event_workingdaysKeyReleased

    private void allowanceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_allowanceKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_allowanceKeyTyped

    private void allowanceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_allowanceKeyReleased
       
            try 
             {
                int i = Integer.parseInt(allowance.getText());
                if ( !"".equals(workingdays.getText()) ) 
                 {
                    if (Integer.parseInt(allowance.getText()) > Integer.parseInt(workingdays.getText()) ) 
                      {
                        //JOptionPane.showMessageDialog(null, "Allowance days Must be Less than working day field ");
                        new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"\"Allowance days Must be Less than working day field ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                        allowance.setText("");
                      }
                 }
                else
                 {
                     //JOptionPane.showMessageDialog(null, "Please Fill Working Day Field before Fill This field ");
                     new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Fill Working Day Field before Fill This field ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                     allowance.setText("");
                 }
                        
             } 
            catch (Exception e) 
             {
                 //JOptionPane.showMessageDialog(null, "Please Input Numbers ");
                 new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Input Numbers ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                 allowance.grabFocus();
                 allowance.setText("");
             }
    }//GEN-LAST:event_allowanceKeyReleased

    private void payKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_payKeyReleased
        
        try 
             {
                double i = Double.parseDouble(pay.getText());
                if(Integer.parseInt(pay.getText()) > 100)
                    
                  {
                      //JOptionPane.showMessageDialog(null, "Pay Prasentage should be less than 100 ");
                      new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Pay Prasentage should be less than 100 ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                      pay.setText("");
                  }
             } 
            catch (Exception e) 
             {
                 //JOptionPane.showMessageDialog(null, "Please Input Numbers ");
                 new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Input Numbers ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                 pay.grabFocus();
                 pay.setText("");
             }
    }//GEN-LAST:event_payKeyReleased

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        
         this.dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new workingNallowance().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addbtn;
    private javax.swing.JTextField allowance;
    private javax.swing.JButton clearbtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable maintbl;
    private javax.swing.JTextField pay;
    private javax.swing.JLabel showdate;
    private javax.swing.JButton updatebtn;
    private javax.swing.JTextField workingdays;
    // End of variables declaration//GEN-END:variables
}
