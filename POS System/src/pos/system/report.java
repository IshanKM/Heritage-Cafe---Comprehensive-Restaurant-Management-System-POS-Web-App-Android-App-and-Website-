
package pos.system;

import com.sbix.jnotify.NPosition;
import com.sbix.jnotify.NotifyType;
import com.sbix.jnotify.NotifyWindow;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.print.PrinterException;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public class report extends javax.swing.JFrame 
{

    public report() 
    {
        try
          {
              
            initComponents();
            clock();
            detailsbox();
            LocalDate date = java.time.LocalDate.now();
            sellingreport(date.toString(),date.toString());
            dailysummury(date.toString());
            currentstock();
            seticon();

          }
        catch(Exception e)
          {
              new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
          }
        
    }
    
    public void seticon()
     {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon/companylogo.png")));
     }
    //selling report 
    
    public void sellingreport(String date1 , String date2)
      {
          try 
           {

               sellingreport.setText
                   (
                              "\n                     Haritage Bakery and Cafe           " +"\n"
                              +"              61 Pedlar St, Galle 80000, Sri Lanka" +"\n"
                              +"                            091- 4200 140"+ "\n"
                              +"_________________________________________"+"\n\n"
                              +"   Selling Report"+"             "+date1+" To "+date2
                              +"\n_________________________________________"+"\n"
                              +"\n  Item Name    Total QTY   Unit Price   Total Price \n\n"

                   );

               MyQuery m = new MyQuery();
               Connection con = m.getConnection();
               Statement st;
               ResultSet rs;
               int i = 1;
               double total = 0;
               
               st = con.createStatement();
               rs = st.executeQuery( "SELECT Item_Name,SUM(Qty),Price,SUM(total) FROM all_orders WHERE Date BETWEEN '"+date1+"' AND '"+date2+"' GROUP BY Item_Name HAVING COUNT(Item_Name) > 1 ");
               while(rs.next())
                   {
                       
                       String names = rs.getString("Item_Name");
                       int qty = rs.getInt("SUM(Qty)");
                       double unitprice = rs.getDouble("Price");
                       double totalprice = rs.getDouble("SUM(total)");
                       sellingreport.setText(sellingreport.getText() +" "+i+". "+ names+"\n\t");
                       sellingreport.setText(sellingreport.getText() +"       "+ qty);
                       sellingreport.setText(sellingreport.getText() +"             "+ unitprice);
                       sellingreport.setText(sellingreport.getText() +"           "+ totalprice +"\n");
                       i++;
                       total = total + totalprice;
                   }
               
               rs = st.executeQuery( "SELECT Item_Name,SUM(Qty),Price,SUM(total) FROM all_orders WHERE Date BETWEEN '"+date1+"' AND '"+date2+"' GROUP BY Item_Name HAVING COUNT(Item_Name) = 1 ");
               while(rs.next())
                   {
                       
                       String names = rs.getString("Item_Name");
                       int qty = rs.getInt("SUM(Qty)");
                       double unitprice = rs.getDouble("Price");
                       double totalprice = rs.getDouble("SUM(total)");
                       sellingreport.setText(sellingreport.getText() +" "+i+". "+ names+"\n\t");
                       sellingreport.setText(sellingreport.getText() +"       "+ qty);
                       sellingreport.setText(sellingreport.getText() +"         "+ unitprice);
                       sellingreport.setText(sellingreport.getText() +"         "+ totalprice +"\n");
                       i++;
                       total = total + totalprice;
                   }
               
               sellingreport.setText(sellingreport.getText() +"\n_________________________________________"+"\n\n"
                                                             +"\t Total Income : Rs " +total
                                                             +"\n_________________________________________\n");

           }
          catch (Exception e)
           {
               new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
           }
         
         
      }
    
    //daily summury
    
     public void dailysummury(String dateget)
      {
          try 
            {
                
              MyQuery m = new MyQuery();
              Connection con = m.getConnection();
              Statement st;
              ResultSet rs;
              double getibalance = 0;
              
              st = con.createStatement();
              rs = st.executeQuery( "SELECT Balance from initial_balance Where Date = '"+dateget+"'");
              while(rs.next())
                   {
                       getibalance = rs.getDouble("Balance");
                   }

              double total = m.gettotalpriceinday(dateget);
              double totalcard = m.getcardpriceinday(dateget);
              double totalcash = m.getcashpriceinday(dateget);
              double totalonline = m.getonlineorderprice(dateget);
              int noofbill = m.getnoofbill(dateget);
              
              

              dailysummury.setText
                (
                           "\n                    Haritage Bakery and Cafe           " +"\n"
                           +"             61 Pedlar St, Galle 80000, Sri Lanka" +"\n"
                           +"                           091- 4200 140"+ "\n"
                           +"___________________________________________"+"\n\n"
                           +"  Daily Summury Report            " +dateget+ " Report"
                           +"\n___________________________________________"+"\n\n"
                           +"     Total Income Of Day            Number Of Bills \n"
                           +"           Rs. "+total+"  \t           "+noofbill+" \n\n"
                           +"     Initial Balance                     Online Orders \n"
                           +"           Rs. "+getibalance+"  \t       Rs. "+totalonline+"    \n\n"            
                           +"       Cash Payment                   Card Payment  \n"
                           +"           Rs. "+totalcard+"                            Rs. "+totalcash+"     \n\n"
                           +"\n___________________________________________"+"\n"
                           +"                     SOFTWARE(C) I&K SOFT             \n"
                           +"           WWW.OFFICIALI&KSOFT.LK / 078-7318908"

                );
            }
          catch (Exception e) 
            {
                new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
            }
         
      }
     
     public void currentstock()
      {
          try 
           {

               stock.setText
                   (
                              "\n                       Haritage Bakery and Cafe           " +"\n"
                              +"                61 Pedlar St, Galle 80000, Sri Lanka" +"\n"
                              +"                              091- 4200 140"+ "\n"
                              +"_______________________________________________"+"\n\n"
                              +"\t         CURRENT STOCK  "
                              +"\n_______________________________________________"+"\n"
                              +"\n  Item Name                  Quentity              Supplier_ID \n\n"

                   );

               MyQuery m = new MyQuery();
               Connection con = m.getConnection();
               Statement st;
               ResultSet rs;
               int i = 1;
               
               st = con.createStatement();
               rs = st.executeQuery( "SELECT Item_Name,QTY,Supplier_ID,Last_ChangerEmp FROM store ");
               while(rs.next())
                   {
                       String name = rs.getString("Item_Name");
                       int qty = rs.getInt("QTY");
                       int sid = rs.getInt("Supplier_ID");

                       stock.setText(stock.getText() +"  "+i+". "+ name+"\n\t");
                       stock.setText(stock.getText() +"                   "+ qty);
                       stock.setText(stock.getText() +"                        "+ sid+"\n");

                       i++;
                   }
               
           }
          catch (Exception e)
           {
              new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
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
        jPanel9 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        timeshow = new javax.swing.JLabel();
        detailshowpanel = new javax.swing.JPanel();
        showemployeename = new javax.swing.JLabel();
        imageshow = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        stock = new javax.swing.JEditorPane();
        jButton18 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        sellingreport = new javax.swing.JEditorPane();
        srgetdate1 = new com.toedter.calendar.JDateChooser();
        jButton19 = new javax.swing.JButton();
        srgetdate2 = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        sellingserchbtn = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        dailysummury = new javax.swing.JEditorPane();
        jButton21 = new javax.swing.JButton();
        dsgetdate = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        dailysummarybtn = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 4));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 0, 3, new java.awt.Color(0, 0, 0)));

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/billauto.png"))); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tempus Sans ITC", 3, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 0, 0));
        jLabel18.setText("REPORT DISPLAY PAGE");

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/close.png"))); // NOI18N
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/minimize.png"))); // NOI18N
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel12MouseClicked(evt);
            }
        });

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/home.png"))); // NOI18N

        jLabel22.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("HOME");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        jLabel38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel38MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addGap(18, 18, 18)
                .addComponent(jLabel19)
                .addGap(41, 41, 41))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        imageshow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageshow.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        detailshowpanel.add(imageshow, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 62, 60));

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logo2.png"))); // NOI18N

        jPanel5.setBackground(new java.awt.Color(0, 153, 102));

        stock.setEditable(false);
        stock.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 5, true));
        stock.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        stock.setInheritsPopupMenu(true);
        jScrollPane2.setViewportView(stock);

        jButton18.setBackground(new java.awt.Color(255, 255, 255));
        jButton18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/print.png"))); // NOI18N
        jButton18.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tekton Pro", 1, 48)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("CURRENT STOCKS");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton18)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(255, 0, 0));

        sellingreport.setEditable(false);
        sellingreport.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 5, true));
        sellingreport.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        sellingreport.setInheritsPopupMenu(true);
        jScrollPane3.setViewportView(sellingreport);

        srgetdate1.setDateFormatString("yyyy-MMM-d");
        srgetdate1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jButton19.setBackground(new java.awt.Color(255, 255, 255));
        jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/print.png"))); // NOI18N
        jButton19.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        srgetdate2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Tekton Pro", 1, 48)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("SELLING REPORT");

        sellingserchbtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sellingserchbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search_1.png"))); // NOI18N
        sellingserchbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sellingserchbtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(sellingserchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(srgetdate1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(srgetdate2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8))
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(srgetdate1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(srgetdate2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sellingserchbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton19)
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(0, 51, 204));

        dailysummury.setEditable(false);
        dailysummury.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 5, true));
        dailysummury.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        dailysummury.setInheritsPopupMenu(true);
        jScrollPane5.setViewportView(dailysummury);

        jButton21.setBackground(new java.awt.Color(255, 255, 255));
        jButton21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/print.png"))); // NOI18N
        jButton21.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        dsgetdate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Tekton Pro", 1, 48)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("DAILY SUMMURY");

        dailysummarybtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dailysummarybtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search_1.png"))); // NOI18N
        dailysummarybtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dailysummarybtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(dailysummarybtn, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(dsgetdate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)))))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dsgetdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dailysummarybtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton21)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(333, 333, 333)
                        .addComponent(timeshow, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 330, Short.MAX_VALUE)
                        .addComponent(detailshowpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(timeshow, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(detailshowpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        setSize(new java.awt.Dimension(1800, 1000));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked

        int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
        if (confirm == 0)
        {
            System.exit(0);
        }
    }//GEN-LAST:event_jLabel19MouseClicked

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked

        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_jLabel20MouseClicked

    private void detailshowpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_detailshowpanelMouseClicked

        detailshow ds = new detailshow();
        ds.setVisible(true);

    }//GEN-LAST:event_detailshowpanelMouseClicked

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        
        int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
         if (confirm == 0)
            {
              try 
                {
                   stock.print();
                }
              catch (PrinterException ex) 
                {  
                     new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Someting Wrong .Try Again",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT); 
                }
            }
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        
         int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
         if (confirm == 0)
            {
              try 
                {
                   sellingreport.print();
                }
              catch (PrinterException ex) 
                {  
                     new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Someting Wrong .Try Again",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT); 
                }
            }
    }//GEN-LAST:event_jButton19ActionPerformed
  
    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        
        int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
         if (confirm == 0)
            {
              try 
                { 
                   dailysummury.print();
                }
              catch (PrinterException ex) 
                {  
                     new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Someting Wrong .Try Again",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT); 
                }
            }
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jPanel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseClicked
       
        main m = new main();
        m.setVisible(true);
        this.dispose();
        
    }//GEN-LAST:event_jPanel12MouseClicked

    private void sellingserchbtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sellingserchbtnMouseClicked
        
        if (srgetdate1.getDate() != null && srgetdate2.getDate() != null) 
         {
            Date date1 = srgetdate1.getDate();
            Date date2 = srgetdate2.getDate();

            if (date1.compareTo(date2)<0) 
            {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                sellingreport(df.format(date1),df.format(date2));
            }
            else if(date1.compareTo(date2)>0)
            {
                //JOptionPane.showMessageDialog(null, "Second Date Chooser must Greater Than First Date Chooser ");
                new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Second Date less Than First Date",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
            }
         }
        else 
         {
             new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Choose Date",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
         }
  
        

    }//GEN-LAST:event_sellingserchbtnMouseClicked

    private void dailysummarybtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dailysummarybtnMouseClicked
       
        if (dsgetdate.getDate() != null)
         {
            Date date = dsgetdate.getDate();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            dailysummury(df.format(date));
         } 
        else
         {
             new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Choose Date",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
         }
        
    }//GEN-LAST:event_dailysummarybtnMouseClicked

    private void jLabel38MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel38MouseClicked

        this.dispose();

        report m = new report();
        m.setVisible(true);
    }//GEN-LAST:event_jLabel38MouseClicked

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
            java.util.logging.Logger.getLogger(report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new report().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel dailysummarybtn;
    private javax.swing.JEditorPane dailysummury;
    private javax.swing.JPanel detailshowpanel;
    private com.toedter.calendar.JDateChooser dsgetdate;
    private javax.swing.JLabel imageshow;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton21;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JEditorPane sellingreport;
    private javax.swing.JLabel sellingserchbtn;
    private javax.swing.JLabel showemployeename;
    private com.toedter.calendar.JDateChooser srgetdate1;
    private com.toedter.calendar.JDateChooser srgetdate2;
    private javax.swing.JEditorPane stock;
    private javax.swing.JLabel timeshow;
    // End of variables declaration//GEN-END:variables
}
