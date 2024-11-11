
package pos.system;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core;
import com.sbix.jnotify.NPosition;
import com.sbix.jnotify.NotifyType;
import com.sbix.jnotify.NotifyWindow;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.print.PrinterException;
import java.io.IOException;
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

public class Onlineorder extends javax.swing.JFrame {

    public Onlineorder()
    {
       

        try 
            {
                initComponents();
                detailsbox();
                displaykitchen();
                clock();
                decorate();
                setnotes();
                seticon();
                billdisplay();
            } 
        
        catch (Exception ex) 
            {
                new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
            }
    }
    
    public void seticon()
     {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon/companylogo.png")));
     }
    
    public void setnotes()
    {
        Preferences pf = Preferences.userNodeForPackage(Onlineorder.class);
        String note = pf.get("notesonline", "root");
        
        notes.setText(note);
    }

    public void decorate()
      {
        //displayorder table
          
        JTableHeader displayorderheader = displayorder.getTableHeader();
        displayorderheader.setForeground(Color.red);
        displayorderheader.setFont(new Font("Times New Roman", Font.BOLD,20));
        ((DefaultTableCellRenderer)displayorderheader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);
        displayorder.setRowHeight(30);
        displayorder.setSelectionForeground(Color.white);
 
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
                                             rs = st.executeQuery("SELECT * FROM online_order");
                                             displayorder.setModel(DbUtils.resultSetToTableModel(rs));
                                             
                                             JTableHeader header = showitems.getTableHeader();
                                             header.setForeground(Color.red);
                                             header.setFont(new Font("Times New Roman", Font.BOLD,20));
                                             ((DefaultTableCellRenderer)header.getDefaultRenderer())
                                                    .setHorizontalAlignment(JLabel.CENTER);
                                             showitems.setSelectionForeground(Color.white);
                                             sleep(30000);
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
                        +" Description  Qty   Price  Discount   Amount"+ "\n\n"           
                        
            );
            
                
        }
     
     public void setbilldetails(int rowcount,int selectedrow)
     {
         try 
          {
              billdisplay();

              double totalprice = 0;
              double totalpriceall = 0 ;
              int countqty = 0;
              int j = 1;
              
              for (int i = 0; i < rowcount; i++) 
              {
 
                   billdisplay.setText(billdisplay.getText()+ j + ". " + showitems.getModel().getValueAt(i, 0 ).toString()+"\n\t");
                   billdisplay.setText(billdisplay.getText()+showitems.getModel().getValueAt(i, 1 ).toString());
                   billdisplay.setText(billdisplay.getText()+ " * " +showitems.getModel().getValueAt(i, 3 ).toString());
                   billdisplay.setText(billdisplay.getText()+ " " + "("+showitems.getModel().getValueAt(i, 4 ).toString()+")");

                   double price = Double.parseDouble(showitems.getModel().getValueAt(i, 3 ).toString());
                   int qty = Integer.parseInt(showitems.getModel().getValueAt(i, 1 ).toString());
                   double discount = Double.parseDouble(showitems.getModel().getValueAt(i, 4 ).toString());
                   double delivery = Double.parseDouble(displayorder.getModel().getValueAt(selectedrow, 11 ).toString());
                   if (discount != 0 ) 
                    {
                      totalprice = (price - (price * discount/100)) *qty;
                      billdisplay.setText(billdisplay.getText()+ " = Rs. " +totalprice +"\n");
                    }
                   else 
                    {
                        totalprice = price * qty;
                        billdisplay.setText(billdisplay.getText()+ " = Rs. " +totalprice +"\n");
                    }
                   
                   countqty = countqty + qty;
                   totalpriceall = totalpriceall + totalprice;
                   
                   for (int k = i; k == rowcount-1 ; k++)
                   {
                      billdisplay.setText(billdisplay.getText() +" ____________________________________"+"\n\n");
                      billdisplay.setText(billdisplay.getText() +" AMOUNT"+ "\t\t" +"RS "+ totalpriceall +"\n");
                      billdisplay.setText(billdisplay.getText() +" Delivery Charge "+ "\t" +"RS "+ delivery +"\n");
                      billdisplay.setText(billdisplay.getText() +" Total Price "+ "\t\t" +"RS "+ (totalpriceall+delivery) +"\n");
                      billdisplay.setText(billdisplay.getText() +" No Of Items = "+ rowcount + "\t" + "Total Qty =  "+ countqty +"\n");
                      billdisplay.setText(billdisplay.getText() +" ____________________________________"+"\n\n");
                      billdisplay.setText(billdisplay.getText() +"\tCustomer Details  " +"\n");
                      billdisplay.setText(billdisplay.getText() +" 1.Customer Name : "+displayorder.getModel().getValueAt(selectedrow, 4 ).toString()+"\n");
                      billdisplay.setText(billdisplay.getText() +" 2.Customer Mobile No : "+displayorder.getModel().getValueAt(selectedrow, 6 ).toString() +"\n");
                      billdisplay.setText(billdisplay.getText() +" 3.Customer Address : "+displayorder.getModel().getValueAt(selectedrow, 5 ).toString()+"\n");
                      billdisplay.setText(billdisplay.getText() +" 4.Customer E-Mail : "+displayorder.getModel().getValueAt(selectedrow, 7 ).toString() +"\n");
                      billdisplay.setText(billdisplay.getText() +" ____________________________________"+"\n\n");
                      billdisplay.setText(billdisplay.getText() +"             Thank You,See You Again..!"+"\n\n");
                      
                   }
                   
                   j++;
                   
              }
          } 
         catch (Exception e)
          {
             JOptionPane.showMessageDialog(this, e);
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
                                           int mounth = cl.get(Calendar.MONTH) + 1;
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        displayorder = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        showitems = new javax.swing.JTable();
        time = new javax.swing.JLabel();
        dateshow = new javax.swing.JLabel();
        totalshow = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        orderid = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        notes = new javax.swing.JEditorPane();
        clearnote = new javax.swing.JButton();
        ordercomplete = new javax.swing.JButton();
        shopcamera = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        absentlabel4 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        timeshow = new javax.swing.JLabel();
        detailshowpanel = new javax.swing.JPanel();
        showemployeename = new javax.swing.JLabel();
        imageshow = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        email = new javax.swing.JEditorPane();
        mobile = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        address = new javax.swing.JEditorPane();
        jScrollPane8 = new javax.swing.JScrollPane();
        name = new javax.swing.JEditorPane();
        jLabel19 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        billdisplay = new javax.swing.JEditorPane();
        clearbill = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        displayorder = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex,int colIndex)
            {
                return false;
            }
        };
        displayorder.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        displayorder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Date", "Time", "CID", "CName", "CAddress", "CMobile", "CEmail", "Total", "IDs_in_Order", "QTYs_in_Order", "Delivery Charge"
            }
        ));
        displayorder.setGridColor(new java.awt.Color(255, 0, 0));
        displayorder.setSelectionBackground(new java.awt.Color(255, 0, 0));
        displayorder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                displayorderMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                displayorderMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(displayorder);

        jLabel18.setFont(new java.awt.Font("Stencil Std", 3, 24)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("ALL_ORDERS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(297, 297, 297)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(342, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        showitems = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex,int colIndex)
            {
                return false;
            }
        };
        showitems.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        showitems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Qty", "Image", "Price", "Discount"
            }
        ));
        showitems.setGridColor(new java.awt.Color(255, 0, 0));
        showitems.setSelectionBackground(new java.awt.Color(255, 0, 0));
        showitems.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        showitems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showitemsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(showitems);

        time.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        time.setForeground(new java.awt.Color(255, 0, 51));
        time.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        time.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        dateshow.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dateshow.setForeground(new java.awt.Color(255, 0, 51));
        dateshow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dateshow.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        totalshow.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        totalshow.setForeground(new java.awt.Color(255, 0, 51));
        totalshow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalshow.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel20.setFont(new java.awt.Font("Stencil Std", 3, 24)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("ITEMS DISPLAY");

        orderid.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        orderid.setForeground(new java.awt.Color(255, 0, 51));
        orderid.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        orderid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(dateshow, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(orderid, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
                    .addComponent(totalshow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dateshow, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(time, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(orderid, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(totalshow, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(0, 153, 51));
        jPanel7.setForeground(new java.awt.Color(255, 255, 255));

        jLabel14.setFont(new java.awt.Font("Stencil Std", 3, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("NOTES");

        notes.setFont(new java.awt.Font("Nirmala UI", 1, 18)); // NOI18N
        notes.setForeground(new java.awt.Color(0, 102, 102));
        notes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                noteMouseClicked(evt);
            }
        });
        notes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                notesKeyReleased(evt);
            }
        });
        jScrollPane6.setViewportView(notes);

        clearnote.setBackground(new java.awt.Color(0, 153, 51));
        clearnote.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        clearnote.setForeground(new java.awt.Color(255, 255, 255));
        clearnote.setText("Clear");
        clearnote.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        clearnote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearnoteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(clearnote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(clearnote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                .addContainerGap())
        );

        ordercomplete.setBackground(new java.awt.Color(0, 153, 51));
        ordercomplete.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        ordercomplete.setForeground(new java.awt.Color(255, 255, 255));
        ordercomplete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/readyorder.png"))); // NOI18N
        ordercomplete.setText("ORDER");
        ordercomplete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        ordercomplete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ordercompleteActionPerformed(evt);
            }
        });

        shopcamera.setBackground(new java.awt.Color(0, 0, 255));
        shopcamera.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        shopcamera.setForeground(new java.awt.Color(255, 255, 255));
        shopcamera.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/camera.png"))); // NOI18N
        shopcamera.setText("SHOP");
        shopcamera.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        shopcamera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shopcameraActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 0, 3, new java.awt.Color(0, 0, 0)));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/kitchen.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tempus Sans ITC", 3, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setText("ONLINE ORDERS PAGE");

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
        jPanel12.setToolTipText("Go to Home");
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

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });

        absentlabel4.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        absentlabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        absentlabel4.setText("KITCHEN");
        absentlabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        absentlabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                absentlabel4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(absentlabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addGap(41, 41, 41))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(absentlabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jPanel9.setBackground(new java.awt.Color(0, 0, 153));
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Orator Std", 3, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("INVENTRY");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/inventary.png"))); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(151, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(179, 179, 179))
        );

        jPanel10.setBackground(new java.awt.Color(255, 0, 0));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel10MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Orator Std", 3, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("EMPLOYEE");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/employee.png"))); // NOI18N

        jLabel11.setFont(new java.awt.Font("Orator Std", 3, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("UNIT");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel11.setBackground(new java.awt.Color(0, 0, 0));

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logo2.png"))); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
        );

        timeshow.setFont(new java.awt.Font("Stencil Std", 1, 48)); // NOI18N
        timeshow.setForeground(new java.awt.Color(255, 255, 255));
        timeshow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

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

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField1.setText("Customer Name");
        jTextField1.setBorder(null);

        jTextField2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField2.setText("Customer Address");
        jTextField2.setBorder(null);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jTextField3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField3.setText("Customer Mobile No");
        jTextField3.setBorder(null);

        jTextField4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextField4.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField4.setText("Customer E-Mail");
        jTextField4.setBorder(null);

        email.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        email.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jScrollPane4.setViewportView(email);

        mobile.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        mobile.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        mobile.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));

        address.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        address.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jScrollPane7.setViewportView(address);

        name.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        name.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jScrollPane8.setViewportView(name);

        jLabel19.setFont(new java.awt.Font("Stencil Std", 3, 24)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Customer Details");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(mobile, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap())
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addGap(35, 35, 35)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField2)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mobile, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(clearbill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane5))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(clearbill, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(timeshow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(detailshowpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(ordercomplete, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                                    .addComponent(shopcamera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 31, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(detailshowpanel, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                                    .addComponent(timeshow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(ordercomplete, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(shopcamera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 1, Short.MAX_VALUE))))))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ordercompleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ordercompleteActionPerformed

        int row = displayorder.getSelectedRow();
        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;

        if (row >= 0)
        {
            int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
            if (confirm == 0)
            {
                try
                {
                    billdisplay.print();
                    //delete from kithen table after complete
                    MyQuery m = new MyQuery();
                    con = m.getConnection();
                    String delete = displayorder.getModel().getValueAt(row,0).toString();
                    ps = con.prepareStatement("DELETE FROM online_order WHERE ID = '"+delete+"'");
                    ps.executeUpdate();
                    
                    //store all order table
                    
                    int getrow = showitems.getRowCount();
                    MyQuery mq = new MyQuery();
                    int orderid = Integer.parseInt(mq.getfinalorderid()) + 1;
                    String orderidS = Integer.toString(orderid);
                    
                    LocalDate getdate = java.time.LocalDate.now();
                    LocalTime gettime = java.time.LocalTime.now();                 
                    
                    loginpage lp = new loginpage();
                    String details[] = lp.getdetails();
                    String username = details[0];
                    String password = details[1];
                    int idemployee = m.getidemployee(username , password);
                    
                    for (int i = 0; i < getrow; i++)
                     {
                        String name = showitems.getModel().getValueAt(i, 0 ).toString();
                        double price = Double.parseDouble(showitems.getModel().getValueAt(i, 3 ).toString());
                        int qty = Integer.parseInt(showitems.getModel().getValueAt(i, 1 ).toString());
                        double finalprice = price * qty;
                        String pmethord = "Online Order";

                        ps = con.prepareStatement("INSERT INTO all_orders VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
                        ps.setInt(1, 0);
                        ps.setString(2, orderidS);
                        ps.setString(3, getdate.toString());
                        ps.setString(4, gettime.toString());
                        ps.setInt(5, idemployee);
                        ps.setString(6, name);
                        ps.setInt(7, qty);
                        ps.setDouble(8, price);
                        ps.setDouble(9, finalprice);
                        ps.setDouble(10, 0);
                        ps.setDouble(11, 0);
                        ps.setString(12, pmethord);

                        ps.executeUpdate();

                        orderidS = null;
                     }
                    
                    //delete row from displayorder table
                        
                    DefaultTableModel  removerow =  (DefaultTableModel)displayorder.getModel();
                    removerow.removeRow(row);

                    showitems.setModel(new DefaultTableModel(null,new String[] {"Name","Qty","Price","Image"}));
                    dateshow.setText("");
                    totalshow.setText("");
                    time.setText("");
                    billdisplay.setText("");
                    name.setText("");
                    address.setText("");
                    email.setText("");
                    
                    displaykitchen();
                    

                }
                catch (SQLException ex)
                {
                     new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Someting Wrong .Try Again",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);         
                } 
                catch (PrinterException ex) 
                {
                    JOptionPane.showMessageDialog(this, ex);
                }
                
                
            }
        }
        else
        {
            //JOptionPane.showMessageDialog(null, "Please Select the Row You Want to Deliver");
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Select The Order ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
        }

    }//GEN-LAST:event_ordercompleteActionPerformed

    private void noteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_noteMouseClicked

        try
        {
            Runtime.getRuntime().exec("cmd /c osk");
            notes.grabFocus();
        }
        catch (IOException ex)
        {
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Fail To Get KeyBoard",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
        }
    }//GEN-LAST:event_noteMouseClicked

    private void displayorderMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_displayorderMouseEntered

    }//GEN-LAST:event_displayorderMouseEntered

    private void displayorderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_displayorderMouseClicked

        int selected =  displayorder.getSelectedRow();
        
        String getorderid = displayorder.getModel().getValueAt(selected, 0 ).toString();
        String ids = displayorder.getModel().getValueAt(selected, 9 ).toString();
        String getname = displayorder.getModel().getValueAt(selected, 4 ).toString();
        String getaddress = displayorder.getModel().getValueAt(selected, 5 ).toString();
        String getemail = displayorder.getModel().getValueAt(selected, 7 ).toString();
        String getmobile = displayorder.getModel().getValueAt(selected, 6 ).toString();
        String gettotal = displayorder.getModel().getValueAt(selected, 8 ).toString();
        String date = displayorder.getModel().getValueAt(selected, 1 ).toString();
        String gettime = displayorder.getModel().getValueAt(selected, 2 ).toString();
        
        //add customer order area
        name.setText(getname);
        address.setText(getaddress);
        email.setText(getemail);
        mobile.setText(getmobile);
        totalshow.setText("Total Balance Is Rs. "+gettotal);
        dateshow.setText(date);
        time.setText(gettime);
        orderid.setText(getorderid);
        
        //add show item table
        MyQuery mq = new MyQuery();
        ArrayList<getdetails> list = mq.getiddetails(ids);
        String[] columnName = {"Name","QTY","Image","Price","Discount"};
        Object[][] rows = new Object[list.size()][5];
        for(int i = 0; i < list.size(); i++){
            rows[i][0] = list.get(i).getName();
            String qty= displayorder.getModel().getValueAt(selected,10 ).toString();
            rows[i][1] = qty.charAt(i);

            if(list.get(i).getMyImage() != null)
            {

                ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getMyImage()).getImage()
                    .getScaledInstance(250, 180, Image.SCALE_SMOOTH) );

                rows[i][2] = image;
            }
            else
            {
                rows[i][2] = null;
            }
            rows[i][3] = list.get(i).getPrice();
            rows[i][4] = list.get(i).getDiscount();

        }

        JTableHeader header = showitems.getTableHeader();
        header.setForeground(Color.red);
        header.setFont(new Font("Times New Roman", Font.BOLD,20));
        ((DefaultTableCellRenderer)header.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);
        
        TheModel model =  new TheModel(rows, columnName);
        showitems.setModel(model);
        showitems.setRowHeight(150);
        showitems.setShowGrid(true);
        
        showitems.getColumnModel().getColumn(0).setPreferredWidth(500);
        showitems.getColumnModel().getColumn(1).setPreferredWidth(50);
        showitems.getColumnModel().getColumn(2).setPreferredWidth(210);
        
        int rowcount = showitems.getRowCount();
        setbilldetails(rowcount,selected);
        
        
        
        

    }//GEN-LAST:event_displayorderMouseClicked

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

    private void detailshowpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_detailshowpanelMouseClicked
        
        detailshow ds = new detailshow();
        ds.setVisible(true);
        
    }//GEN-LAST:event_detailshowpanelMouseClicked

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
       
        inventry i = new inventry ();
        i.setVisible(true);
    }//GEN-LAST:event_jPanel9MouseClicked

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
       
        Preferences pf = Preferences.userNodeForPackage(billauto.class);
        pf.put("currentpage", "onliineorder");
        
        loginemployeeunit e = new loginemployeeunit();
        e.setVisible(true);
        
        this.setVisible(false);
    }//GEN-LAST:event_jPanel10MouseClicked

    private void jPanel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseClicked
       
        dispose();
        main m = new main();
        m.setVisible(true);
    }//GEN-LAST:event_jPanel12MouseClicked

    private void shopcameraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shopcameraActionPerformed
        
        
        //note : this get from gihub.com-------------------------------
        
        try 
         {
            OpenCVFrameGrabber frameGrabber = new OpenCVFrameGrabber("http://192.168.8.100:8554/video?dummy=param.mjpg"); 
            frameGrabber.setFormat("mjpeg");
            frameGrabber.start();
            opencv_core.IplImage iPimg = frameGrabber.grab();
            CanvasFrame canvasFrame = new CanvasFrame("Camera");
            canvasFrame.setCanvasSize(iPimg.width(), iPimg.height());

            while (canvasFrame.isVisible() && (iPimg = frameGrabber.grab()) != null) 
            {
                canvasFrame.showImage(iPimg);
            }
            frameGrabber.stop();
            canvasFrame.dispose();
            System.exit(0);
         } 
        catch (Exception e) 
         {
             new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Still Working on it",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT); 
         }
    }//GEN-LAST:event_shopcameraActionPerformed

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked

        try
        {
            this.dispose();

            Onlineorder b = new Onlineorder();
            b.setVisible(true);
        }
        catch (Exception ex)
        {
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail..!",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
        }
    }//GEN-LAST:event_jLabel16MouseClicked

    private void clearnoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearnoteActionPerformed
       
        notes.setText("");
        
        Preferences pf = Preferences.userNodeForPackage(Onlineorder.class);
        pf.put("notespnline", "");
    }//GEN-LAST:event_clearnoteActionPerformed

    private void notesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_notesKeyReleased
        
        Preferences pf = Preferences.userNodeForPackage(Onlineorder.class);
        pf.put("notesonline", notes.getText().trim());
    }//GEN-LAST:event_notesKeyReleased

    private void clearbillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbillActionPerformed

        try 
         {
            billdisplay();
         } 
        catch (SQLException ex)
         {
             new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Something Wromg,Please Try Again..!",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
         }
    }//GEN-LAST:event_clearbillActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void showitemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showitemsMouseClicked
       
        
    }//GEN-LAST:event_showitemsMouseClicked

    private void absentlabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_absentlabel4MouseClicked

        kitchen k = new kitchen();
        k.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_absentlabel4MouseClicked

    
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Onlineorder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel absentlabel4;
    private javax.swing.JEditorPane address;
    private javax.swing.JEditorPane billdisplay;
    private javax.swing.JButton clearbill;
    private javax.swing.JButton clearnote;
    private javax.swing.JLabel dateshow;
    private javax.swing.JPanel detailshowpanel;
    private javax.swing.JTable displayorder;
    private javax.swing.JEditorPane email;
    private javax.swing.JLabel imageshow;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField mobile;
    private javax.swing.JEditorPane name;
    private javax.swing.JEditorPane notes;
    private javax.swing.JButton ordercomplete;
    private javax.swing.JLabel orderid;
    private javax.swing.JButton shopcamera;
    private javax.swing.JLabel showemployeename;
    private javax.swing.JTable showitems;
    private javax.swing.JLabel time;
    private javax.swing.JLabel timeshow;
    private javax.swing.JLabel totalshow;
    // End of variables declaration//GEN-END:variables
}
