
package pos.system;

import com.sbix.jnotify.NPosition;
import com.sbix.jnotify.NotifyType;
import com.sbix.jnotify.NotifyWindow;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import net.proteanit.sql.DbUtils;


public class billauto extends javax.swing.JFrame {

 
    public billauto() throws SQLException 
    {
        try 
         {
            initComponents();
            decorate();
            billdisplay();
            displaykitchen();
            displayreject();
            clock();
            detailsbox();
            seticon();
            
         } 
        
        catch (Exception e) 
         {
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT); 
         }
 
    }
    
    public void seticon()
     {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon/companylogo.png")));
     }
    
    public void decorate()
      {
        // all aorders table
          
        JTableHeader allordersheader = allorders.getTableHeader();
        allordersheader.setForeground(Color.red);
        allordersheader.setFont(new Font("Times New Roman", Font.BOLD,16));
        ((DefaultTableCellRenderer)allordersheader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);

        allorders.setRowHeight(70);
        allorders.setShowGrid(true);
        allorders.setSelectionForeground(Color.white);
        
        //item of bill
        
        JTableHeader itemsofbillheader = itemsofbill.getTableHeader();
        itemsofbillheader.setForeground(Color.red);
        itemsofbillheader.setFont(new Font("Times New Roman", Font.BOLD,20));
        ((DefaultTableCellRenderer)itemsofbillheader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);
        
        itemsofbill.setRowHeight(150);
        itemsofbill.setShowGrid(true);
        
        //reject table
        
        JTableHeader rejecttableheader = rejecttable.getTableHeader();
        rejecttableheader.setForeground(Color.red);
        rejecttableheader.setFont(new Font("Times New Roman", Font.BOLD,16));
        ((DefaultTableCellRenderer)rejecttableheader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);

        rejecttable.setRowHeight(50);
        rejecttable.setShowGrid(true);
        rejecttable.setSelectionForeground(Color.white);
      }
    
     public void billdisplay() throws SQLException
        { 
            
                      
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/DD HH:mm:ss");           
            Date date = new Date();
            String dateshow = sdf.format(date);
            
            loginpage lp = new loginpage();
            String details[] = lp.getdetails();
          
            String username = details[0];
            String password = details[1];
            
            MyQuery m = new MyQuery();
            String nameemployee = m.getnameemployee(username , password);
            
            
            String id = m.getfinalid();
            int reid = Integer.parseInt(id) + 1;

            
            billdisplay.setText
            (
                        "************ Haritage Bakery and Cafe **********" +"\n"
                        +"                  61 Pedlar St, Galle 80000" +"\n"
                        +"                            091- 4200 140"+ "\n"
                        +" ____________________________________"+"\n \n"
                        +" Date : " + dateshow+"\n"
                        +" Receipt : " + reid +"\n"
                        +" Checkout : "+nameemployee+"\n"
                        +" ____________________________________"+"\n\n"
                        +" Description    Qty    Price  Discount    Amount"+ "\n\n"           
                        
            );
                
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
                                            rs = st.executeQuery("SELECT * FROM complete_orders");
                                            allorders.setModel(DbUtils.resultSetToTableModel(rs));
                                            sleep(60000);
                                        }
                                }
                            catch(Exception e)
                                {
                                     new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);                                    
                                }
                       }
                                    

                };
            th.start();
        }
     
     public void displayreject()
        {
 
            Thread th = new Thread()
                {
                    @Override
                    public void run()
                        {
                            try
                                {
                                    MyQuery m = new MyQuery();
                                    Connection con = m.getConnection();
                                    Statement st; 
                                    ResultSet rs;
                                    int check = Integer.parseInt(m.getfinalidreject());
                                    for(;;)
                                        {
                           
                                            int finalid = Integer.parseInt(m.getfinalidreject());
                                            if (finalid != check) 
                                            {
                                                JOptionPane.showMessageDialog(null, "A New Rejected Order.Please Check Rejected Order Table");
                                            }
                                            
                                            check = finalid;
                                            
                                            
                                            st = con.createStatement();
                                            rs = st.executeQuery("SELECT Time,Item_Names,Table_No,Description FROM rejected_order");
                                            rejecttable.setModel(DbUtils.resultSetToTableModel(rs));
                                            sleep(10000);
                                        }
                                }
                            catch(Exception e)
                                {
                                    new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);  
                                }
                       }
                                    

                };
            th.start();
        }
     
     
     public void addbill(int a, int b,int selected)
      {
         
        int row = a;
        int j = b;
        String cashfield = cash.getText();
        double allqty = 0;
 
        if (!"".equals(cashfield)) 
        {    

                try 
                 {
                  billdisplay();
                  if (row != 0) 
                    {
                      for (int i = 0; i <= row ; i++) 
                          {             
                              for (int k = i; k == row; k++) 
                              {
                                  
                                  billdisplay.setText(billdisplay.getText() +" AMOUNT"+ "\t\t" +"RS "+ amount.getText() +"\n");
                                  billdisplay.setText(billdisplay.getText() +" CASH"+ "\t\t" + "RS "+ cash.getText() +".0"+"\n");
                                  billdisplay.setText(billdisplay.getText() +" CHANGE"+ "\t\t" + "RS "+ change.getText() +"\n");
                                  billdisplay.setText(billdisplay.getText() +" Payment Methord"+ "\t" + methord.getSelectedItem().toString() +"\n");
                                  billdisplay.setText(billdisplay.getText() + "-----------------------------------------------------------"+"\n");
                                  billdisplay.setText(billdisplay.getText() +" No Of Items = "+ row + "\t" + "Total Qty =  "+  allqty +"\n");
                                  billdisplay.setText(billdisplay.getText() + "-----------------------------------------------------------"+"\n\n");
                                  billdisplay.setText(billdisplay.getText() + "            Thank You.See You Again...!"+"\n");
                                  billdisplay.setText(billdisplay.getText() +" ____________________________________"+"\n\n");
                                  billdisplay.setText(billdisplay.getText() + "            SOFTWARE(C) I&K SOFT \n");
                                  billdisplay.setText(billdisplay.getText() + "      WWW.OFFICIALIKSOFT.LK / 0771213111"+"\n");
                                 
                              }
                              
                              double priceor = Double.parseDouble(itemsofbill.getModel().getValueAt(i, 1 ).toString());
                              String qtys = allorders.getModel().getValueAt(selected, 6 ).toString();
                              char qtyget = qtys.charAt(i);
                              String qtystring = Character.toString(qtyget);
                              double qty = Double.parseDouble(qtystring);
                              allqty = allqty + qty;
                              double Amount = 0.0;
                              
                              if(Double.parseDouble(itemsofbill.getModel().getValueAt(i, 4 ).toString()) == 0)
                               {
                                    Amount = priceor* qty;
                                    billdisplay.setText(billdisplay.getText()+" "+ j + ". " + itemsofbill.getModel().getValueAt(i, 0 ).toString()+"\n\t");
                                    billdisplay.setText(billdisplay.getText() + itemsofbill.getModel().getValueAt(i, 1 ).toString()+" * ");
                                    billdisplay.setText(billdisplay.getText() + qtyget);
                                    billdisplay.setText(billdisplay.getText() + "("+ itemsofbill.getModel().getValueAt(i, 4 ).toString()+")");
                                    billdisplay.setText(billdisplay.getText() + " =     Rs "+ Amount +"\n");
                                    billdisplay.setText(billdisplay.getText() + "-----------------------------------------------------------"+"\n");
                               }
                              else
                               {
                                    Amount = (priceor*qtyget)*(Double.parseDouble(itemsofbill.getModel().getValueAt(i, 4 ).toString()))/100;
                                    billdisplay.setText(billdisplay.getText()+" "+ j + ". " + itemsofbill.getModel().getValueAt(i, 0 ).toString()+"\n\t");
                                    billdisplay.setText(billdisplay.getText() + itemsofbill.getModel().getValueAt(i, 1 ).toString()+" * ");
                                    billdisplay.setText(billdisplay.getText() + qtyget);
                                    billdisplay.setText(billdisplay.getText() + "("+ itemsofbill.getModel().getValueAt(i, 4 ).toString()+")");
                                    billdisplay.setText(billdisplay.getText() + " =     Rs "+ Amount +"\n");
                                    billdisplay.setText(billdisplay.getText() + "-----------------------------------------------------------"+"\n");
                               }
                              
                              j++;

                          }
                     } 

                  else 
                      {
                          //JOptionPane.showMessageDialog(null, "ADD TO CART TABLE IS EMPTY");
                          new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"ADD TO CART TABLE IS EMPTY",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                      }
                 } 
                catch (Exception e) 
                    {

                    }            
        }

        else
        {
           JOptionPane.showMessageDialog(null, "Please Fill The Cash Field"); 
        }
     
    }
     
      public void clock()
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
                                           Calendar cl = Calendar.getInstance();
                                          
                                           int day = cl.get(Calendar.DAY_OF_MONTH);
                                           int mounth = cl.get(Calendar.MONTH);
                                           int year = cl.get(Calendar.YEAR);
                                           
                                           int second = cl.get(Calendar.SECOND);
                                           int min = cl.get(Calendar.MINUTE);
                                           int hour = cl.get(Calendar.HOUR);
                                           int am_pm = cl.get(Calendar.AM_PM);
                                           
                                           String d_n = "";
                                           
                                           if( am_pm == 1)
                                                {
                                                   d_n = "PM"; 
                                                }
                                           else
                                                {
                                                   d_n = "AM";
                                                }
                                           
                                           timeshow.setText("" +hour+":"+min+":"+second+" "+d_n+" "+day+"/"+mounth+"/"+year+"");
                                           sleep(1000);
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
      
      public void clearall()
       {
        try 
         {
            amount.setText("");
            cash.setText("");
            change.setText("");
            methord.setSelectedIndex(0);
            
            itemsofbill.setModel(new DefaultTableModel(null,new String[] {"Name","Price","Qty","Image","Discount"}));
            billdisplay();
            displaykitchen();
            
         } 
        catch (Exception e) 
         {
             new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Someting Wrong .Try Again",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
         }

       }
    
     public void detailsbox() throws SQLException
        {
            
            Connection con = null;
            Statement st;
            ResultSet rs;
            String category = null;
            byte[] img = null;
            
            loginpage lp = new loginpage();
            String[] details = lp.getdetails();           
            String uname = details[0];
            String pwd = details[1];
            
            MyQuery mq = new MyQuery();
            con = mq.getConnection();
            st = con.createStatement();
            
            
            rs = st.executeQuery( "select Image,Category from employee where User_Name='"+uname+"' and Password='"+pwd+"'");
            while(rs.next())
              {
                 category = rs.getString("Category");                
                 img = rs.getBytes("Image");                    
              }
          
            showemployeename.setText(category);
            ImageIcon image = new  ImageIcon(img);
            Image myimg = image.getImage().getScaledInstance(60,60, Image.SCALE_SMOOTH);
            ImageIcon newimg = new ImageIcon(myimg);
            imageshow.setIcon(newimg);
  
        }

    
  
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jp = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        refresh = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        allorders = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        tblshow = new javax.swing.JLabel();
        idshow = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        itemsofbill = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        cash = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        amount = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        change = new javax.swing.JTextField();
        methord = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        setbill = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        clear = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        seven = new javax.swing.JButton();
        four = new javax.swing.JButton();
        one = new javax.swing.JButton();
        dot = new javax.swing.JButton();
        zero = new javax.swing.JButton();
        two = new javax.swing.JButton();
        five = new javax.swing.JButton();
        eight = new javax.swing.JButton();
        nine = new javax.swing.JButton();
        six = new javax.swing.JButton();
        three = new javax.swing.JButton();
        doublezero = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        print = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        billdisplay = new javax.swing.JEditorPane();
        clearbill = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        timeshow = new javax.swing.JLabel();
        detailshowpanel = new javax.swing.JPanel();
        showemployeename = new javax.swing.JLabel();
        imageshow = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        rejecttable = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jp.setBackground(new java.awt.Color(0, 0, 0));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 0, 3, new java.awt.Color(0, 0, 0)));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/billauto.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tempus Sans ITC", 3, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setText("BILL FROM ORDERS PAGE");

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/close.png"))); // NOI18N
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/minimize.png"))); // NOI18N
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel12MouseClicked(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/home.png"))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("HOME");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        refresh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        refresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addGap(41, 41, 41))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(refresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jPanel10.setBackground(new java.awt.Color(0, 0, 153));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel10MouseClicked(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/billmanualimg.png"))); // NOI18N

        jLabel25.setFont(new java.awt.Font("Orator Std", 3, 16)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("MANUAL");

        jLabel26.setFont(new java.awt.Font("Orator Std", 3, 16)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("BILL");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(151, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(149, 149, 149))
        );

        jPanel11.setBackground(new java.awt.Color(204, 0, 51));
        jPanel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel11MouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Orator Std", 3, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("EMPLOYEE");

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/employee.png"))); // NOI18N

        jLabel18.setFont(new java.awt.Font("Orator Std", 3, 16)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("UNIT");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(138, 138, 138))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        allorders = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex,int colIndex)
            {
                return false;
            }
        };
        allorders.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        allorders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "TIME", "DETAILS", "PRICE", "TABLE_NO", "IDs_in_Order", "QTYs_in_Order"
            }
        ));
        allorders.setFocusable(false);
        allorders.setGridColor(new java.awt.Color(255, 0, 0));
        allorders.setSelectionBackground(new java.awt.Color(255, 0, 0));
        allorders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                allordersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(allorders);

        jLabel14.setFont(new java.awt.Font("Stencil Std", 3, 24)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("ALL ORDERs");

        tblshow.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tblshow.setForeground(new java.awt.Color(255, 0, 0));
        tblshow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        idshow.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        idshow.setForeground(new java.awt.Color(255, 0, 0));
        idshow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(266, 266, 266)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(idshow, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tblshow, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 773, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tblshow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(idshow, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Stencil Std", 3, 24)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("ItEmS OF BILL");

        itemsofbill = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex,int colIndex)
            {
                return false;
            }
        };
        itemsofbill.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        itemsofbill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Price", "Qty", "Image", "Discount"
            }
        ));
        itemsofbill.setSelectionBackground(new java.awt.Color(255, 0, 0));
        jScrollPane3.setViewportView(itemsofbill);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(261, 261, 261)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane3)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 204));
        jLabel11.setText("Cash");

        cash.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        cash.setForeground(new java.awt.Color(0, 0, 204));
        cash.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cash.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 51, 204)));
        cash.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cashMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cashMouseReleased(evt);
            }
        });
        cash.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cashKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Net Amount");

        amount.setEditable(false);
        amount.setBackground(new java.awt.Color(255, 255, 255));
        amount.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        amount.setForeground(new java.awt.Color(255, 0, 51));
        amount.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        amount.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 0, 0)));
        amount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                amountMouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 153, 102));
        jLabel7.setText("Change");

        change.setEditable(false);
        change.setBackground(new java.awt.Color(255, 255, 255));
        change.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        change.setForeground(new java.awt.Color(0, 153, 51));
        change.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        change.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 153, 51)));
        change.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changeMouseClicked(evt);
            }
        });

        methord.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        methord.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "Cash Payment", "Card Payment" }));
        methord.setBorder(null);
        methord.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        methord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                methordMouseClicked(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Stencil Std", 3, 24)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("PaymentS AREA");

        setbill.setBackground(new java.awt.Color(0, 0, 0));
        setbill.setFont(new java.awt.Font("Engravers MT", 3, 24)); // NOI18N
        setbill.setForeground(new java.awt.Color(255, 255, 255));
        setbill.setText("SET BILL");
        setbill.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setbill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setbillActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel20.setText("Payment Method");

        clear.setBackground(new java.awt.Color(0, 0, 0));
        clear.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        clear.setForeground(new java.awt.Color(255, 255, 255));
        clear.setText("Clear");
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(0, 0, 0));
        jButton8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("<");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        seven.setBackground(new java.awt.Color(0, 0, 0));
        seven.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        seven.setForeground(new java.awt.Color(255, 255, 255));
        seven.setText("7");
        seven.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sevenActionPerformed(evt);
            }
        });

        four.setBackground(new java.awt.Color(0, 0, 0));
        four.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        four.setForeground(new java.awt.Color(255, 255, 255));
        four.setText("4");
        four.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fourActionPerformed(evt);
            }
        });

        one.setBackground(new java.awt.Color(0, 0, 0));
        one.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        one.setForeground(new java.awt.Color(255, 255, 255));
        one.setText("1");
        one.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                oneMouseClicked(evt);
            }
        });
        one.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oneActionPerformed(evt);
            }
        });

        dot.setBackground(new java.awt.Color(0, 0, 0));
        dot.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        dot.setForeground(new java.awt.Color(255, 255, 255));
        dot.setText(".");
        dot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dotActionPerformed(evt);
            }
        });

        zero.setBackground(new java.awt.Color(0, 0, 0));
        zero.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        zero.setForeground(new java.awt.Color(255, 255, 255));
        zero.setText("0");
        zero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zeroActionPerformed(evt);
            }
        });

        two.setBackground(new java.awt.Color(0, 0, 0));
        two.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        two.setForeground(new java.awt.Color(255, 255, 255));
        two.setText("2");
        two.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                twoActionPerformed(evt);
            }
        });

        five.setBackground(new java.awt.Color(0, 0, 0));
        five.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        five.setForeground(new java.awt.Color(255, 255, 255));
        five.setText("5");
        five.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fiveActionPerformed(evt);
            }
        });

        eight.setBackground(new java.awt.Color(0, 0, 0));
        eight.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        eight.setForeground(new java.awt.Color(255, 255, 255));
        eight.setText("8");
        eight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eightActionPerformed(evt);
            }
        });

        nine.setBackground(new java.awt.Color(0, 0, 0));
        nine.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        nine.setForeground(new java.awt.Color(255, 255, 255));
        nine.setText("9");
        nine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nineActionPerformed(evt);
            }
        });

        six.setBackground(new java.awt.Color(0, 0, 0));
        six.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        six.setForeground(new java.awt.Color(255, 255, 255));
        six.setText("6");
        six.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sixActionPerformed(evt);
            }
        });

        three.setBackground(new java.awt.Color(0, 0, 0));
        three.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        three.setForeground(new java.awt.Color(255, 255, 255));
        three.setText("3");
        three.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                threeActionPerformed(evt);
            }
        });

        doublezero.setBackground(new java.awt.Color(0, 0, 0));
        doublezero.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        doublezero.setForeground(new java.awt.Color(255, 255, 255));
        doublezero.setText("00");
        doublezero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doublezeroActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Clear All");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel15.setBackground(new java.awt.Color(0, 0, 0));
        jLabel15.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 0, 0, 0, new java.awt.Color(0, 0, 0)));

        jLabel22.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 3, 0, 0, new java.awt.Color(0, 0, 0)));

        jLabel23.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 3, 0, 0, new java.awt.Color(0, 0, 0)));

        jLabel24.setBackground(new java.awt.Color(0, 0, 0));
        jLabel24.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 0, 0, 0, new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(setbill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jButton8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(seven, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(four, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(one, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dot, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(clear, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(two, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(zero, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(five, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(eight, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel22)
                                .addGap(17, 17, 17)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(three, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(six, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(nine, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(doublezero, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel20)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(methord, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(change, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cash, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(amount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(8, 8, 8))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(98, 98, 98))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(amount, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cash, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(change, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(methord, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(setbill, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addGap(21, 21, 21)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(seven, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(eight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nine, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(four, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(six, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(five, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(one, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(three, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(two, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(zero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(doublezero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        print.setBackground(new java.awt.Color(255, 255, 255));
        print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/print.png"))); // NOI18N
        print.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });

        billdisplay.setEditable(false);
        billdisplay.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        billdisplay.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jScrollPane5.setViewportView(billdisplay);

        clearbill.setBackground(new java.awt.Color(255, 255, 255));
        clearbill.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        clearbill.setText("CLEAR BILL");
        clearbill.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        clearbill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbillActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(print, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                        .addComponent(clearbill, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(clearbill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(print, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel19.setBackground(new java.awt.Color(0, 0, 0));
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logo2.png"))); // NOI18N

        timeshow.setFont(new java.awt.Font("Stencil Std", 1, 48)); // NOI18N
        timeshow.setForeground(new java.awt.Color(255, 255, 255));

        detailshowpanel.setBackground(new java.awt.Color(255, 51, 0));
        detailshowpanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 4, true));
        detailshowpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                detailshowpanelMouseClicked(evt);
            }
        });
        detailshowpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        showemployeename.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        showemployeename.setForeground(new java.awt.Color(255, 255, 255));
        showemployeename.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        showemployeename.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        detailshowpanel.add(showemployeename, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 120, 60));
        detailshowpanel.add(imageshow, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 62, 60));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        rejecttable.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        rejecttable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Time", "I_Names", "Table", "Description"
            }
        ));
        rejecttable.setSelectionBackground(new java.awt.Color(255, 0, 0));
        rejecttable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rejecttableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(rejecttable);

        jLabel21.setFont(new java.awt.Font("Stencil Std", 3, 24)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("RejectED ORDER");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jpLayout = new javax.swing.GroupLayout(jp);
        jp.setLayout(jpLayout);
        jpLayout.setHorizontalGroup(
            jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpLayout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(299, 299, 299)
                        .addComponent(timeshow, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpLayout.createSequentialGroup()
                        .addGroup(jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 794, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpLayout.createSequentialGroup()
                        .addGap(0, 164, Short.MAX_VALUE)
                        .addComponent(detailshowpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpLayout.setVerticalGroup(
            jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpLayout.createSequentialGroup()
                        .addGroup(jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addGroup(jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(detailshowpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(timeshow, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpLayout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jpLayout.createSequentialGroup()
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked

        int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
        if (confirm == 0)
        {
            System.exit(0);
        }
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked

        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_jLabel9MouseClicked

    private void allordersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_allordersMouseClicked

        int selected =  allorders.getSelectedRow();
        String ids = allorders.getModel().getValueAt(selected, 5 ).toString();
        
        String orderid = allorders.getModel().getValueAt(selected, 0 ).toString();
        String tblno = allorders.getModel().getValueAt(selected, 4 ).toString();
        idshow.setText("Id is :- " + orderid);
        tblshow.setText("Table No is is :- " +tblno);
        
        MyQuery mq = new MyQuery();
        ArrayList<getdetails> list = mq.getiddetails(ids);
        String[] columnName = {"Name","Price","Qty","Image","Discount"};
        Object[][] rows = new Object[list.size()][5];
        for(int i = 0; i < list.size(); i++){
            rows[i][0] = list.get(i).getName();
            rows[i][1] = list.get(i).getPrice();
            String qty= allorders.getModel().getValueAt(selected,6 ).toString();
            rows[i][2] = qty.charAt(i);
            if(list.get(i).getMyImage() != null)
            {

                ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getMyImage()).getImage().getScaledInstance(250, 150, Image.SCALE_SMOOTH) );

                rows[i][3] = image;
            }
            else
            {
                rows[i][3] = null;
            }
            
            rows[i][4] = list.get(i).getDiscount();
        }

        JTableHeader itemsofbillheader = itemsofbill.getTableHeader();
        itemsofbillheader.setForeground(Color.red);
        itemsofbillheader.setFont(new Font("Times New Roman", Font.BOLD,20));
        ((DefaultTableCellRenderer)itemsofbillheader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);
        
        TheModel model =  new TheModel(rows, columnName);
        itemsofbill.setModel(model);
        itemsofbill.setRowHeight(150);
        itemsofbill.setShowGrid(true);

        itemsofbill.setModel(model);
        itemsofbill.getColumnModel().getColumn(0).setPreferredWidth(270);
        itemsofbill.getColumnModel().getColumn(1).setPreferredWidth(80);
        itemsofbill.getColumnModel().getColumn(2).setPreferredWidth(80);
        itemsofbill.getColumnModel().getColumn(3).setPreferredWidth(210);
        itemsofbill.getColumnModel().getColumn(4).setPreferredWidth(150);

        amount.setText(allorders.getModel().getValueAt(selected, 3 ).toString());

        double cashall = Double.parseDouble(cash.getText());
        double changeall = cashall -  Double.parseDouble(amount.getText());

        change.setText(Double.toString(changeall));
       
        
    }//GEN-LAST:event_allordersMouseClicked

    private void cashMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cashMouseClicked

        // keyboardvalue.setText("2");
    }//GEN-LAST:event_cashMouseClicked

    private void cashMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cashMouseReleased

    }//GEN-LAST:event_cashMouseReleased

    private void cashKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cashKeyReleased

        String cashall = cash.getText();
        String amountvalue = amount.getText();

        double cashallconvert = Double.parseDouble(cashall);
        double amountconvert = Double.parseDouble(amountvalue);

        String changeall = Double.toString(cashallconvert - amountconvert);

        change.setText(changeall);
    }//GEN-LAST:event_cashKeyReleased

    private void amountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_amountMouseClicked

    }//GEN-LAST:event_amountMouseClicked

    private void changeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changeMouseClicked

    }//GEN-LAST:event_changeMouseClicked

    private void methordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_methordMouseClicked

    }//GEN-LAST:event_methordMouseClicked

    private void setbillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setbillActionPerformed
        if ( !"".equals(amount.getText().trim()) )
        {
            if ( !"".equals(cash.getText().trim()) )
            {
                if (Double.parseDouble(amount.getText()) <= Double.parseDouble(cash.getText()) ) 
                {
                    if ( methord.getSelectedItem().toString() == "Select")
                    {
                        //JOptionPane.showMessageDialog(null, "");
                        new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Select Payment Methord",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                    }
                    else
                    {
                        int addtobill = itemsofbill.getRowCount();
                        int selected = allorders.getSelectedRow();
                        int j = 1;
                        
                        // billdisplaybuttonclick();
                        addbill(addtobill,j,selected);
                    }
                    
                }
                else
                {
                    //JOptionPane.showMessageDialog(null, "Cash is Less Than Amount");
                    new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Cash is Less Than Amount",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                }
            }
            else
            {
                //JOptionPane.showMessageDialog(null, "Cash Field is Empty");
                new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Cash Field is Empty",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                
            }
        }
        else
        {
            //OptionPane.showMessageDialog(null, "Amount Field is Empty");
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Amount Field is Empty",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
        }
       
    }//GEN-LAST:event_setbillActionPerformed

    private void printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printActionPerformed
        
        int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
        if (confirm == 0)
        {
            try
            {

                //itemsofbill.setModel(new DefaultTableModel(null,new String[] {"Name","Price","Image","Discount"}));
                Connection con = null;
                PreparedStatement ps = null;
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/the_haritage_cafe", "root", "");

                int id = 0;
                
                MyQuery mq = new MyQuery();
                int orderid = Integer.parseInt(mq.getfinalid()) + 1;
                String orderidS = Integer.toString(orderid);

                LocalDate date = java.time.LocalDate.now();
                LocalTime time = java.time.LocalTime.now();

                loginpage lp = new loginpage();
                String details[] = lp.getdetails();
                String username = details[0];
                String password = details[1];
                MyQuery m = new MyQuery();
                int idemployee = m.getidemployee(username , password);
                
                int row = itemsofbill.getRowCount();
                for (int i = 0; i < row; i++) 
                    {
                         String name = itemsofbill.getModel().getValueAt(i, 0 ).toString();
                         double price = Double.parseDouble(itemsofbill.getModel().getValueAt(i, 1 ).toString());
                         double cashall = Double.parseDouble(cash.getText());
                         double changeall = Double.parseDouble(change.getText());
                         int qty = Integer.parseInt(itemsofbill.getModel().getValueAt(i, 2 ).toString());
                         double finalprice = price * qty;
                         String pmethord = methord.getSelectedItem().toString();
                         
                          ps = con.prepareStatement("INSERT INTO all_orders VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
                          ps.setInt(1, id);
                          ps.setString(2, orderidS);
                          ps.setString(3, date.toString());
                          ps.setString(4, time.toString());
                          ps.setInt(5, idemployee);
                          ps.setString(6, name);
                          ps.setInt(7, qty);
                          ps.setDouble(8, price);
                          ps.setDouble(9, finalprice);
                          ps.setDouble(10, cashall);
                          ps.setDouble(11, changeall);
                          ps.setString(12, pmethord);

                          ps.executeUpdate();
                          
                          orderidS = null;
                    }

                billdisplay.print();

            }
            catch (Exception e)
            {
                //JOptionPane.showMessageDialog(null, "Please Check Again..You Entered Data is Incorrect");
                new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Check Again..You Entered Data is Incorrect",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
            }
        } 
    }//GEN-LAST:event_printActionPerformed

    private void clearbillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbillActionPerformed

        billdisplay.setText("");
        try
        {
            billdisplay();
        } catch (SQLException ex)
        {

        }
    }//GEN-LAST:event_clearbillActionPerformed

    private void detailshowpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_detailshowpanelMouseClicked

        detailshow ds = new detailshow();
        ds.setVisible(true);

    }//GEN-LAST:event_detailshowpanelMouseClicked

    private void eightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eightActionPerformed

        cash.setText(cash.getText() + "8");
        double cal = Double.parseDouble(cash.getText()) - Double.parseDouble(amount.getText());
        change.setText(Double.toString(cal));
    }//GEN-LAST:event_eightActionPerformed

    private void sevenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sevenActionPerformed

        cash.setText(cash.getText() + "7");
        double cal = Double.parseDouble(cash.getText()) - Double.parseDouble(amount.getText());
        change.setText(Double.toString(cal));
    }//GEN-LAST:event_sevenActionPerformed

    private void fourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fourActionPerformed

        cash.setText(cash.getText() + "4");
        double cal = Double.parseDouble(cash.getText()) - Double.parseDouble(amount.getText());
        change.setText(Double.toString(cal));

    }//GEN-LAST:event_fourActionPerformed

    private void oneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_oneMouseClicked

    }//GEN-LAST:event_oneMouseClicked

    private void oneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oneActionPerformed

        cash.setText(cash.getText() + "1");
        double cal = Double.parseDouble(cash.getText()) - Double.parseDouble(amount.getText());
        change.setText(Double.toString(cal));

    }//GEN-LAST:event_oneActionPerformed

    private void dotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dotActionPerformed

        cash.setText(cash.getText() + ".");
        double cal = Double.parseDouble(cash.getText()) - Double.parseDouble(amount.getText());
        change.setText(Double.toString(cal));

    }//GEN-LAST:event_dotActionPerformed

    private void zeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zeroActionPerformed

        cash.setText(cash.getText() + "0");
        double cal = Double.parseDouble(cash.getText()) - Double.parseDouble(amount.getText());
        change.setText(Double.toString(cal));
    }//GEN-LAST:event_zeroActionPerformed

    private void doublezeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doublezeroActionPerformed

       cash.setText(cash.getText() + "00");
        double cal = Double.parseDouble(cash.getText()) - Double.parseDouble(amount.getText());
        change.setText(Double.toString(cal));

    }//GEN-LAST:event_doublezeroActionPerformed

    private void threeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_threeActionPerformed

        cash.setText(cash.getText() + "3");
        double cal = Double.parseDouble(cash.getText()) - Double.parseDouble(amount.getText());
        change.setText(Double.toString(cal));

    }//GEN-LAST:event_threeActionPerformed

    private void twoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_twoActionPerformed
        
        cash.setText(cash.getText() + "2");
        double cal = Double.parseDouble(cash.getText()) - Double.parseDouble(amount.getText());
        change.setText(Double.toString(cal));
    }//GEN-LAST:event_twoActionPerformed

    private void fiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fiveActionPerformed

        cash.setText(cash.getText() + "5");
        double cal = Double.parseDouble(cash.getText()) - Double.parseDouble(amount.getText());
        change.setText(Double.toString(cal));
    }//GEN-LAST:event_fiveActionPerformed

    private void sixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sixActionPerformed

        cash.setText(cash.getText() + "6");
        double cal = Double.parseDouble(cash.getText()) - Double.parseDouble(amount.getText());
        change.setText(Double.toString(cal));

    }//GEN-LAST:event_sixActionPerformed

    private void nineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nineActionPerformed

        cash.setText(cash.getText() + "");
        double cal = Double.parseDouble(cash.getText()) - Double.parseDouble(amount.getText());
        change.setText(Double.toString(cal));

    }//GEN-LAST:event_nineActionPerformed

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed

        cash.setText("");
    }//GEN-LAST:event_clearActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        clearall();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed

       
            int length = cash.getText().length();
            int number = cash.getText().length() - 1;
            String Store;

            if (length > 0 )
            {
                StringBuilder back = new StringBuilder(cash.getText());
                back.deleteCharAt(number);
                Store = back.toString();
                cash.setText(Store);
            }
        

      

    }//GEN-LAST:event_jButton8ActionPerformed

    private void rejecttableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rejecttableMouseClicked
       
        int row = rejecttable.getSelectedRow();
        String time = rejecttable.getModel().getValueAt(row, 0 ).toString();
        String names = rejecttable.getModel().getValueAt(row, 1 ).toString();
        String tblno = rejecttable.getModel().getValueAt(row, 2 ).toString();
        String des = rejecttable.getModel().getValueAt(row, 3 ).toString();
        
       billdisplay.setText
            (
                        " \n < Time :- "+time+"         " +"\n\n"
                        +" < Table No :- "+tblno+"\n\n"
                        +" < Item Details :- "+names+ "\n\n"
                        +" < Reason :- "+des
        
            );
        
    }//GEN-LAST:event_rejecttableMouseClicked

    private void jPanel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseClicked
        
        main m = new main();
        m.setVisible(true);
        
        this.setVisible(false);
               
    }//GEN-LAST:event_jPanel12MouseClicked

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
        
        try 
         {
            this.setVisible(false);
            billmanual b = new billmanual();
            b.setVisible(true);
         } 
        catch (SQLException ex)
         {
           
         }

    }//GEN-LAST:event_jPanel10MouseClicked

    private void jPanel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseClicked
        
        Preferences pf = Preferences.userNodeForPackage(billauto.class);
        pf.put("currentpage", "billauto");
        
        loginemployeeunit e = new loginemployeeunit();
        e.setVisible(true);
        
        this.setVisible(false);
    }//GEN-LAST:event_jPanel11MouseClicked

    private void refreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshMouseClicked
       
        try 
         {
            this.dispose();
            
            billauto b = new billauto();
            b.setVisible(true);
         }
        catch (Exception ex)
         {
             new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail..!",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
         }
    }//GEN-LAST:event_refreshMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(billauto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(billauto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(billauto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(billauto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try 
                {
                    new billauto().setVisible(true);
                } 
                catch (SQLException ex) 
                {
                    Logger.getLogger(billauto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTable allorders;
    private javax.swing.JTextField amount;
    private javax.swing.JEditorPane billdisplay;
    private javax.swing.JTextField cash;
    private javax.swing.JTextField change;
    private javax.swing.JButton clear;
    private javax.swing.JButton clearbill;
    private javax.swing.JPanel detailshowpanel;
    private javax.swing.JButton dot;
    private javax.swing.JButton doublezero;
    private javax.swing.JButton eight;
    private javax.swing.JButton five;
    private javax.swing.JButton four;
    private javax.swing.JLabel idshow;
    private javax.swing.JLabel imageshow;
    private javax.swing.JTable itemsofbill;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPanel jp;
    private javax.swing.JComboBox methord;
    private javax.swing.JButton nine;
    private javax.swing.JButton one;
    private javax.swing.JButton print;
    private javax.swing.JLabel refresh;
    private javax.swing.JTable rejecttable;
    private javax.swing.JButton setbill;
    private javax.swing.JButton seven;
    private javax.swing.JLabel showemployeename;
    private javax.swing.JButton six;
    private javax.swing.JLabel tblshow;
    private javax.swing.JButton three;
    private javax.swing.JLabel timeshow;
    private javax.swing.JButton two;
    private javax.swing.JButton zero;
    // End of variables declaration//GEN-END:variables
}
