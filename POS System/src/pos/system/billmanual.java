
package pos.system;

import com.sbix.jnotify.NPosition;
import com.sbix.jnotify.NotifyType;
import com.sbix.jnotify.NotifyWindow;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.sql.SQLException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;



public class billmanual extends javax.swing.JFrame {

    
    public billmanual() throws SQLException 
    {
        initComponents();
        try 
         {
             loadingshowdatabase();
             billdisplay(); 
             clock();
             decorate();
             detailsbox();
             seticon();
             
         } 
        catch (Exception e) 
         {
              new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
         }

    }
    
    Double amountall = 0.0;
    
    public void seticon()
     {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon/companylogo.png")));
     }
    
    public void decorate()
        {
 
            JTableHeader addtocartheader = addtocart.getTableHeader();
            addtocartheader.setFont(new Font("Times New Roman", Font.BOLD,14));
            ((DefaultTableCellRenderer)addtocartheader.getDefaultRenderer())
                    .setHorizontalAlignment(JLabel.CENTER);
            
            addtocart.setRowHeight(50);

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
            

            displaybill.setText
             (
                        "********** Haritage Bakery and Cafe **********" +"\n"
                        +"               61 Pedlar St, Galle 80000" +"\n"
                        +"                         091- 4200 140"+ "\n"
                        +" ____________________________________"+"\n \n"
                        +" Date : " + dateshow+"\n"
                        +" Receipt : " + reid +"\n"
                        +" Checkout : "+nameemployee+"\n"
                        +" ____________________________________"+"\n\n"
                        +" Description   Qty   Price Discount   Amount"+ "\n\n"           
                        
             );
                
        }
    
    
    public void addbill(int a)
    {
        int row = a;
        int j = 1;
        int countqua = 0;
        String cashfield = cash.getText();
        double Amount = 0;
        
        
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
                                  displaybill.setText(displaybill.getText() +" AMOUNT"+ "\t\t" +"RS "+ amount.getText() +"\n");
                                  displaybill.setText(displaybill.getText() +" CASH"+ "\t\t" + "RS "+ cash.getText() +".0"+"\n");
                                  displaybill.setText(displaybill.getText() +" CHANGE"+ "\t\t" + "RS "+ change.getText() +"\n");
                                  displaybill.setText(displaybill.getText() +" Payment Methord"+ "\t" + methord.getSelectedItem().toString() +"\n");
                                  displaybill.setText(displaybill.getText() + "-----------------------------------------------------------"+"\n");
                                  displaybill.setText(displaybill.getText() +" No Of Items = "+ row + "\t" + "Total Qty =  "+ countqua +"\n");
                                  displaybill.setText(displaybill.getText() + "-----------------------------------------------------------"+"\n\n");
                                  displaybill.setText(displaybill.getText() + "            Thank You.See You Again...!"+"\n");
                                  displaybill.setText(displaybill.getText() +" ____________________________________"+"\n\n");
                                  displaybill.setText(displaybill.getText() + "            SOFTWARE(C) I&K SOFT \n");
                                  displaybill.setText(displaybill.getText() + "      WWW.OFFICIALIKSOFT.LK / 0771213111"+"\n");
  
                              }

                              double priceor = Double.parseDouble(addtocart.getModel().getValueAt(i, 2 ).toString());
                              int quaor = Integer.parseInt(addtocart.getModel().getValueAt(i, 1 ).toString());
                              countqua = countqua +Integer.parseInt(addtocart.getModel().getValueAt(i, 1 ).toString()); 
                              double getdiscount = Double.parseDouble(addtocart.getModel().getValueAt(i, 3 ).toString()); 
                              if (getdiscount != 0) 
                               {
                                  double finalprice = priceor - (priceor * getdiscount/100);
                                  Amount = finalprice * quaor;
                               } 
                              else 
                               {
                                   Amount = priceor * quaor;
                               }

                              displaybill.setText(displaybill.getText()+ j + ". " + addtocart.getModel().getValueAt(i, 0 ).toString()+"\n\t");
                              displaybill.setText(displaybill.getText() + addtocart.getModel().getValueAt(i, 1 ).toString()+" * ");
                              displaybill.setText(displaybill.getText() + addtocart.getModel().getValueAt(i, 2 ).toString());
                              displaybill.setText(displaybill.getText() + "("+ addtocart.getModel().getValueAt(i, 3 ).toString()+")");
                              displaybill.setText(displaybill.getText() + " =     Rs "+ Amount +"\n");
                              displaybill.setText(displaybill.getText() + "-----------------------------------------------------------"+"\n"); 
                             
                              j++;

                          }


                     } 

                  else 
                      {
                          //JOptionPane.showMessageDialog(null, "ADD TO CART TABLE IS EMPTY");
                          new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"ITEM OF BILL TABLE IS EMPTY",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                      }
                } 
                catch (Exception e) 
                    {
                        
                    }            
        }

        else
        {
           //JOptionPane.showMessageDialog(null, "Please Fill The Cash Field"); 
           new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Fill The Cash Field",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
        }
     
    }
    

    public void loadingshowdatabase()
    {
        MyQuery mq = new MyQuery();
        ArrayList<getdetails> list = mq.breakfast();
        String[] columnName = {"Name","Price","Image","Discount"};
        Object[][] rows = new Object[list.size()][4];
        for(int i = 0; i < list.size(); i++)
        {
            rows[i][0] = list.get(i).getName();
            rows[i][1] = list.get(i).getPrice();
            
            if(list.get(i).getMyImage() != null)
            {
                
                ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getMyImage()).getImage()
                .getScaledInstance(250, 150, Image.SCALE_SMOOTH) );   

               rows[i][2] = image;
            }
            else
            {
                rows[i][2] = null;
            }
            rows[i][3] = list.get(i).getDiscount();
        }
        
        JTableHeader header = databaseshow.getTableHeader();
        header.setForeground(Color.red);
        header.setFont(new Font("Times New Roman", Font.BOLD,20));
        ((DefaultTableCellRenderer)header.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);
        
        TheModel model =  new TheModel(rows, columnName);
        databaseshow.setModel(model);
        databaseshow.setRowHeight(150);
        databaseshow.setShowGrid(true);

        databaseshow.setModel(model);
        databaseshow.getColumnModel().getColumn(0).setPreferredWidth(270);
        databaseshow.getColumnModel().getColumn(1).setPreferredWidth(80);
        databaseshow.getColumnModel().getColumn(2).setPreferredWidth(210);
        databaseshow.getColumnModel().getColumn(3).setPreferredWidth(90);

    }
    
    public void addtocart(int j,String a,String b,String c,String d)
        {
            
        int check = j;
        String itemname = a;
        String itemmqua = b;
        String itemprice = c;
        String itemdiscount = d;
        int row = addtocart.getRowCount();
        
        if ( !"".equals(itemname)) 
          {
                
            
            if (check != 0) 
                {
                        DefaultTableModel  addtocarttable =  (DefaultTableModel)addtocart.getModel();
                        addtocarttable.addRow(new Object[] {itemname,itemmqua,itemprice,itemdiscount});

                        //show it sum of net amount 

                        double itempriceor = Double.parseDouble(itemprice);
                        double itemdiscountor = Double.parseDouble(itemdiscount);


                        if (itemdiscountor != 0 ) 
                          {
                             amountall =  amountall +(itempriceor - (itempriceor  * (itemdiscountor/100))) * Integer.parseInt(itemmqua);
                          }
                        else
                          {
                             amountall =  amountall + itempriceor * Integer.parseInt(itemmqua);
                          }

                        amount.setText(Double.toString(amountall));

                        String cashall = cash.getText();
                        String amountvalue = amount.getText();

                        double cashallconvert = Double.parseDouble(cashall);
                        double amountconvert = Double.parseDouble(amountvalue);

                        String changeall = Double.toString(cashallconvert - amountconvert);


                        change.setText(changeall);
                } 
            else 
                {
                       //JOptionPane.showMessageDialog(null, "Already Added"); 
                       new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Already Added",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                }
        } 
        else 
           {
               //JOptionPane.showMessageDialog(null, "Please check the Item Details"); 
               new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please check the Item Details",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
           }       
            
        }
    
    public void searchdatabase()
    {
            MyQuery mq = new MyQuery();
            ArrayList<getdetails> list = mq.search(search.getText());
            String[] columnName = {"Name","Price","Image","Discount"};
            Object[][] rows = new Object[list.size()][4];
            for(int i = 0; i < list.size(); i++)
                {
                    rows[i][0] = list.get(i).getName();
                    rows[i][1] = list.get(i).getPrice();

                    if(list.get(i).getMyImage() != null){

                        ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getMyImage()).getImage()
                            .getScaledInstance(250, 150, Image.SCALE_SMOOTH) );

                        rows[i][2] = image;
                    }
                    else{
                        rows[i][2] = null;
                    }
                    rows[i][3] = list.get(i).getDiscount();
                 }

              JTableHeader header = databaseshow.getTableHeader();
            header.setForeground(Color.red);
            header.setFont(new Font("Times New Roman", Font.BOLD,20));
            ((DefaultTableCellRenderer)header.getDefaultRenderer())
                    .setHorizontalAlignment(JLabel.CENTER);

            TheModel model =  new TheModel(rows, columnName);
            databaseshow.setModel(model);
            databaseshow.setRowHeight(150);
            databaseshow.setShowGrid(true);

            databaseshow.setModel(model);
            databaseshow.getColumnModel().getColumn(0).setPreferredWidth(270);
            databaseshow.getColumnModel().getColumn(1).setPreferredWidth(80);
            databaseshow.getColumnModel().getColumn(2).setPreferredWidth(210);
            databaseshow.getColumnModel().getColumn(3).setPreferredWidth(90);
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainpanel = new javax.swing.JPanel();
        category = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        databaseshow = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        discount = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        methord = new javax.swing.JComboBox();
        name = new javax.swing.JLabel();
        price = new javax.swing.JLabel();
        addbtn = new javax.swing.JButton();
        qua = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        price1 = new javax.swing.JLabel();
        deletebtn = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        addtocart = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        amount = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        change = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cash = new javax.swing.JTextField();
        setbillbtn = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        search = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        timeshow = new javax.swing.JLabel();
        detailshowpanel = new javax.swing.JPanel();
        showemployeename = new javax.swing.JLabel();
        imageshow = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        displaybill = new javax.swing.JEditorPane();
        two1 = new javax.swing.JButton();
        one1 = new javax.swing.JButton();
        dot1 = new javax.swing.JButton();
        zero1 = new javax.swing.JButton();
        doublezero1 = new javax.swing.JButton();
        three1 = new javax.swing.JButton();
        six1 = new javax.swing.JButton();
        nine1 = new javax.swing.JButton();
        eight1 = new javax.swing.JButton();
        five1 = new javax.swing.JButton();
        four1 = new javax.swing.JButton();
        seven1 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        clear1 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        clearbill = new javax.swing.JButton();
        print = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        mainpanel.setBackground(new java.awt.Color(0, 0, 0));

        category.setBackground(new java.awt.Color(255, 255, 255));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/breakfast.jpg"))); // NOI18N
        jButton4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/pizza.jpg"))); // NOI18N
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/pasta.jpg"))); // NOI18N
        jButton6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/soup.jpg"))); // NOI18N
        jButton5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/sandwich.jpg"))); // NOI18N
        jButton7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/desert.jpg"))); // NOI18N
        jButton9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/drink.jpg"))); // NOI18N
        jButton11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jPanel10.setBackground(new java.awt.Color(0, 0, 153));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel10MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Orator Std", 3, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("BILL FROM");

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/billauto.png"))); // NOI18N
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Orator Std", 3, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("ORDERS");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20)
                .addGap(182, 182, 182))
        );

        javax.swing.GroupLayout categoryLayout = new javax.swing.GroupLayout(category);
        category.setLayout(categoryLayout);
        categoryLayout.setHorizontalGroup(
            categoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, categoryLayout.createSequentialGroup()
                .addGap(0, 26, Short.MAX_VALUE)
                .addGroup(categoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23))
            .addGroup(categoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        categoryLayout.setVerticalGroup(
            categoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(categoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        databaseshow = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex,int colIndex)
            {
                return false;
            }
        };
        databaseshow.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        databaseshow.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Price", "Image", "Discount"
            }
        ));
        databaseshow.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        databaseshow.setFocusable(false);
        databaseshow.setGridColor(new java.awt.Color(255, 0, 51));
        databaseshow.setIntercellSpacing(new java.awt.Dimension(10, 10));
        databaseshow.setName(""); // NOI18N
        databaseshow.setRowHeight(25);
        databaseshow.setSelectionBackground(new java.awt.Color(255, 0, 0));
        databaseshow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                databaseshowMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                databaseshowMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(databaseshow);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Item Name");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Quantity");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Price");

        discount.setEditable(false);
        discount.setBackground(new java.awt.Color(255, 255, 255));
        discount.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        discount.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        discount.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 0, 0)));
        discount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                discountMouseClicked(evt);
            }
        });
        discount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discountActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel4.setText("Discount");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel8.setText("Payment Method");

        methord.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        methord.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "Cash Payment", "Card Payment" }));
        methord.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        methord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                methordMouseClicked(evt);
            }
        });

        name.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        name.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 0, 0)));

        price.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        price.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        price.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 0, 0)));

        addbtn.setBackground(new java.awt.Color(0, 153, 102));
        addbtn.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        addbtn.setForeground(new java.awt.Color(255, 255, 255));
        addbtn.setText("ADD");
        addbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addbtnActionPerformed(evt);
            }
        });

        qua.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        qua.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
        qua.setToolTipText("");

        jLabel11.setFont(new java.awt.Font("Stencil Std", 3, 24)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Process OF BILL");

        price1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        price1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        price1.setText("Rs");
        price1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 0, 0)));

        deletebtn.setBackground(new java.awt.Color(255, 0, 51));
        deletebtn.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        deletebtn.setForeground(new java.awt.Color(255, 255, 255));
        deletebtn.setText("DELETE");
        deletebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(addbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(deletebtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(52, 52, 52))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(qua, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(78, 78, 78)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(methord, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(price1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(discount, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(price1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(discount, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(qua, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(methord, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deletebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        addtocart = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex,int colIndex)
            {
                return false;
            }
        };
        addtocart.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        addtocart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Name", "Quantity", "Price", "Discount"
            }
        ));
        addtocart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addtocartMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(addtocart);
        if (addtocart.getColumnModel().getColumnCount() > 0) {
            addtocart.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel12.setFont(new java.awt.Font("Stencil Std", 3, 24)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("ItEmS OF BILL");

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Net Amount");

        amount.setEditable(false);
        amount.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        amount.setForeground(new java.awt.Color(255, 0, 51));
        amount.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        amount.setToolTipText("Total ");
        amount.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 0, 0)));
        amount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                amountMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 102));
        jLabel5.setText("Change");

        change.setEditable(false);
        change.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        change.setForeground(new java.awt.Color(0, 153, 51));
        change.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        change.setToolTipText("Change ");
        change.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 153, 51)));
        change.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changeMouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 204));
        jLabel10.setText("Cash");

        cash.setEditable(false);
        cash.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        cash.setForeground(new java.awt.Color(0, 0, 204));
        cash.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cash.setToolTipText("Enter Cash Here");
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

        setbillbtn.setBackground(new java.awt.Color(0, 0, 0));
        setbillbtn.setFont(new java.awt.Font("Engravers MT", 3, 24)); // NOI18N
        setbillbtn.setForeground(new java.awt.Color(255, 255, 255));
        setbillbtn.setText("SET BILL");
        setbillbtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setbillbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setbillbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(162, 162, 162))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(cash, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(amount, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(change, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel5)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(setbillbtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(change, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(amount, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cash, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(setbillbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 0, 3, new java.awt.Color(0, 0, 0)));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/billmanual.png"))); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tempus Sans ITC", 3, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 0, 0));
        jLabel14.setText("BILL MANUAL PAGE");

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/close.png"))); // NOI18N
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
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

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/home.png"))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("HOME");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        search.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        search.setForeground(new java.awt.Color(255, 0, 0));
        search.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        search.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 4, 0, new java.awt.Color(0, 0, 0)));
        search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchMouseClicked(evt);
            }
        });
        search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchKeyReleased(evt);
            }
        });

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search_1.png"))); // NOI18N
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        jLabel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel25MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addGap(41, 41, 41))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(search, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel19.setBackground(new java.awt.Color(0, 0, 0));
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logo2.png"))); // NOI18N

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

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        displaybill.setEditable(false);
        displaybill.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        displaybill.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jScrollPane4.setViewportView(displaybill);

        two1.setBackground(new java.awt.Color(0, 0, 0));
        two1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        two1.setForeground(new java.awt.Color(255, 255, 255));
        two1.setText("2");
        two1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                two1ActionPerformed(evt);
            }
        });

        one1.setBackground(new java.awt.Color(0, 0, 0));
        one1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        one1.setForeground(new java.awt.Color(255, 255, 255));
        one1.setText("1");
        one1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                one1MouseClicked(evt);
            }
        });
        one1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                one1ActionPerformed(evt);
            }
        });

        dot1.setBackground(new java.awt.Color(0, 0, 0));
        dot1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dot1.setForeground(new java.awt.Color(255, 255, 255));
        dot1.setText(".");
        dot1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dot1ActionPerformed(evt);
            }
        });

        zero1.setBackground(new java.awt.Color(0, 0, 0));
        zero1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        zero1.setForeground(new java.awt.Color(255, 255, 255));
        zero1.setText("0");
        zero1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zero1ActionPerformed(evt);
            }
        });

        doublezero1.setBackground(new java.awt.Color(0, 0, 0));
        doublezero1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        doublezero1.setForeground(new java.awt.Color(255, 255, 255));
        doublezero1.setText("00");
        doublezero1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doublezero1ActionPerformed(evt);
            }
        });

        three1.setBackground(new java.awt.Color(0, 0, 0));
        three1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        three1.setForeground(new java.awt.Color(255, 255, 255));
        three1.setText("3");
        three1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                three1ActionPerformed(evt);
            }
        });

        six1.setBackground(new java.awt.Color(0, 0, 0));
        six1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        six1.setForeground(new java.awt.Color(255, 255, 255));
        six1.setText("6");
        six1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                six1ActionPerformed(evt);
            }
        });

        nine1.setBackground(new java.awt.Color(0, 0, 0));
        nine1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        nine1.setForeground(new java.awt.Color(255, 255, 255));
        nine1.setText("9");
        nine1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nine1ActionPerformed(evt);
            }
        });

        eight1.setBackground(new java.awt.Color(0, 0, 0));
        eight1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        eight1.setForeground(new java.awt.Color(255, 255, 255));
        eight1.setText("8");
        eight1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eight1ActionPerformed(evt);
            }
        });

        five1.setBackground(new java.awt.Color(0, 0, 0));
        five1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        five1.setForeground(new java.awt.Color(255, 255, 255));
        five1.setText("5");
        five1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                five1ActionPerformed(evt);
            }
        });

        four1.setBackground(new java.awt.Color(0, 0, 0));
        four1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        four1.setForeground(new java.awt.Color(255, 255, 255));
        four1.setText("4");
        four1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                four1ActionPerformed(evt);
            }
        });

        seven1.setBackground(new java.awt.Color(0, 0, 0));
        seven1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        seven1.setForeground(new java.awt.Color(255, 255, 255));
        seven1.setText("7");
        seven1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seven1ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(0, 0, 0));
        jButton10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("<-");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        clear1.setBackground(new java.awt.Color(0, 0, 0));
        clear1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        clear1.setForeground(new java.awt.Color(255, 255, 255));
        clear1.setText("Clear");
        clear1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clear1ActionPerformed(evt);
            }
        });

        jButton19.setBackground(new java.awt.Color(0, 0, 0));
        jButton19.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton19.setForeground(new java.awt.Color(255, 255, 255));
        jButton19.setText("Clear All");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jLabel22.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 0, 0)));

        clearbill.setBackground(new java.awt.Color(255, 255, 255));
        clearbill.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        clearbill.setText("CLEAR BILL");
        clearbill.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        clearbill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbillActionPerformed(evt);
            }
        });

        print.setBackground(new java.awt.Color(255, 255, 255));
        print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/print.png"))); // NOI18N
        print.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(dot1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(four1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(one1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(seven1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(five1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(two1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(eight1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(zero1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clear1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nine1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(three1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(six1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(doublezero1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(print, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clearbill, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(clearbill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(print, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addGap(11, 11, 11)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(clear1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nine1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(eight1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(seven1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(six1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(five1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(four1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(two1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(three1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(doublezero1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(one1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(zero1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dot1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout mainpanelLayout = new javax.swing.GroupLayout(mainpanel);
        mainpanel.setLayout(mainpanelLayout);
        mainpanelLayout.setHorizontalGroup(
            mainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainpanelLayout.createSequentialGroup()
                .addGroup(mainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainpanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(mainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(mainpanelLayout.createSequentialGroup()
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(timeshow, javax.swing.GroupLayout.PREFERRED_SIZE, 1114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(detailshowpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mainpanelLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jScrollPane1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        mainpanelLayout.setVerticalGroup(
            mainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainpanelLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(mainpanelLayout.createSequentialGroup()
                        .addGroup(mainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainpanelLayout.createSequentialGroup()
                                .addComponent(timeshow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(13, 13, 13))
                            .addGroup(mainpanelLayout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainpanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(detailshowpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addGroup(mainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(mainpanelLayout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(category, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1799, 990));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        loadingshowdatabase();

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        MyQuery mq = new MyQuery();
        ArrayList<getdetails> list = mq.pizza();
        String[] columnName = {"Name","Price","Image","Discount"};
        Object[][] rows = new Object[list.size()][4];
        for(int i = 0; i < list.size(); i++){
            rows[i][0] = list.get(i).getName();
            rows[i][1] = list.get(i).getPrice();
            
            if(list.get(i).getMyImage() != null){
                
             ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getMyImage()).getImage()
             .getScaledInstance(250, 150, Image.SCALE_SMOOTH) );   
                
            rows[i][2] = image;
            }
            else{
                rows[i][2] = null;
            }
            rows[i][3] = list.get(i).getDiscount();
        }
        
        TheModel model =  new TheModel(rows, columnName);
        databaseshow.setModel(model);
        databaseshow.setRowHeight(150);
        databaseshow.setShowGrid(true);

        databaseshow.setModel(model);
        databaseshow.getColumnModel().getColumn(0).setPreferredWidth(270);
        databaseshow.getColumnModel().getColumn(1).setPreferredWidth(80);
        databaseshow.getColumnModel().getColumn(2).setPreferredWidth(210);
        databaseshow.getColumnModel().getColumn(3).setPreferredWidth(90);
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        MyQuery mq = new MyQuery();
        ArrayList<getdetails> list = mq.pasta();
        String[] columnName = {"Name","Price","Image","Discount"};
        Object[][] rows = new Object[list.size()][4];
        for(int i = 0; i < list.size(); i++){
            rows[i][0] = list.get(i).getName();
            rows[i][1] = list.get(i).getPrice();
            
            if(list.get(i).getMyImage() != null){
                
             ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getMyImage()).getImage()
             .getScaledInstance(250, 150, Image.SCALE_SMOOTH) );   
                
            rows[i][2] = image;
            }
            else{
                rows[i][2] = null;
            }
            rows[i][3] = list.get(i).getDiscount();
        }
        
        TheModel model =  new TheModel(rows, columnName);
        databaseshow.setModel(model);
        databaseshow.setRowHeight(150);
        databaseshow.setShowGrid(true);

        databaseshow.setModel(model);
        databaseshow.getColumnModel().getColumn(0).setPreferredWidth(270);
        databaseshow.getColumnModel().getColumn(1).setPreferredWidth(80);
        databaseshow.getColumnModel().getColumn(2).setPreferredWidth(210);
        databaseshow.getColumnModel().getColumn(3).setPreferredWidth(90);
        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        MyQuery mq = new MyQuery();
        ArrayList<getdetails> list = mq.soup();
        String[] columnName = {"Name","Price","Image","Discount"};
        Object[][] rows = new Object[list.size()][4];
        for(int i = 0; i < list.size(); i++){
            rows[i][0] = list.get(i).getName();
            rows[i][1] = list.get(i).getPrice();
            
            if(list.get(i).getMyImage() != null){
                
             ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getMyImage()).getImage()
             .getScaledInstance(250, 150, Image.SCALE_SMOOTH) );   
                
            rows[i][2] = image;
            }
            else{
                rows[i][2] = null;
            }
            rows[i][3] = list.get(i).getDiscount();
        }
        
        TheModel model =  new TheModel(rows, columnName);
        databaseshow.setModel(model);
        databaseshow.setRowHeight(150);
        databaseshow.setShowGrid(true);

        databaseshow.setModel(model);
        databaseshow.getColumnModel().getColumn(0).setPreferredWidth(270);
        databaseshow.getColumnModel().getColumn(1).setPreferredWidth(80);
        databaseshow.getColumnModel().getColumn(2).setPreferredWidth(210);
        databaseshow.getColumnModel().getColumn(3).setPreferredWidth(90);
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        
        MyQuery mq = new MyQuery();
        ArrayList<getdetails> list = mq.sandwich();
        String[] columnName = {"Name","Price","Image","Discount"};
        Object[][] rows = new Object[list.size()][4];
        for(int i = 0; i < list.size(); i++){
            rows[i][0] = list.get(i).getName();
            rows[i][1] = list.get(i).getPrice();
            
            if(list.get(i).getMyImage() != null){
                
             ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getMyImage()).getImage()
             .getScaledInstance(250, 150, Image.SCALE_SMOOTH) );   
                
            rows[i][2] = image;
            }
            else{
                rows[i][2] = null;
            }
            rows[i][3] = list.get(i).getDiscount();
        }
        
        TheModel model =  new TheModel(rows, columnName);
        databaseshow.setModel(model);
        databaseshow.setRowHeight(150);
        databaseshow.setShowGrid(true);

        databaseshow.setModel(model);
        databaseshow.getColumnModel().getColumn(0).setPreferredWidth(270);
        databaseshow.getColumnModel().getColumn(1).setPreferredWidth(80);
        databaseshow.getColumnModel().getColumn(2).setPreferredWidth(210);
        databaseshow.getColumnModel().getColumn(3).setPreferredWidth(90);
        
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        
        MyQuery mq = new MyQuery();
        ArrayList<getdetails> list = mq.desert();
        String[] columnName = {"Name","Price","Image","Discount"};
        Object[][] rows = new Object[list.size()][4];
        for(int i = 0; i < list.size(); i++){
            rows[i][0] = list.get(i).getName();
            rows[i][1] = list.get(i).getPrice();
            
            if(list.get(i).getMyImage() != null){
                
             ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getMyImage()).getImage()
             .getScaledInstance(250, 150, Image.SCALE_SMOOTH) );   
                
            rows[i][2] = image;
            }
            else{
                rows[i][2] = null;
            }
            rows[i][3] = list.get(i).getDiscount();
        }
        
        TheModel model =  new TheModel(rows, columnName);
        databaseshow.setModel(model);
        databaseshow.setRowHeight(150);
        databaseshow.setShowGrid(true);

        databaseshow.setModel(model);
        databaseshow.getColumnModel().getColumn(0).setPreferredWidth(270);
        databaseshow.getColumnModel().getColumn(1).setPreferredWidth(80);
        databaseshow.getColumnModel().getColumn(2).setPreferredWidth(210);
        databaseshow.getColumnModel().getColumn(3).setPreferredWidth(90);
        
    }//GEN-LAST:event_jButton9ActionPerformed

    private void databaseshowMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_databaseshowMouseReleased

    }//GEN-LAST:event_databaseshowMouseReleased

    private void databaseshowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_databaseshowMouseClicked

        try
        {

            int row = databaseshow.getSelectedRow();
            String click1 = (databaseshow.getModel().getValueAt(row, 0 ).toString());
            String click2 = (databaseshow.getModel().getValueAt(row, 1 ).toString());
            String click3 = (databaseshow.getModel().getValueAt(row, 3 ).toString());

            name.setText(click1);
            price.setText(click2);
            
            discount.setText(click3);

            qua.setValue(1);

        }

        catch(Exception e)
        {
             new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Someting Wrong .Try Again",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT); 
        }

    }//GEN-LAST:event_databaseshowMouseClicked

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        
        MyQuery mq = new MyQuery();
        ArrayList<getdetails> list = mq.drink();
        String[] columnName = {"Name","Price","Image","Discount"};
        Object[][] rows = new Object[list.size()][4];
        for(int i = 0; i < list.size(); i++){
            rows[i][0] = list.get(i).getName();
            rows[i][1] = list.get(i).getPrice();
            
            if(list.get(i).getMyImage() != null){
                
             ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getMyImage()).getImage()
             .getScaledInstance(250, 150, Image.SCALE_SMOOTH) );   
                
            rows[i][2] = image;
            }
            else{
                rows[i][2] = null;
            }
            rows[i][3] = list.get(i).getDiscount();
        }
        
        TheModel model =  new TheModel(rows, columnName);
        databaseshow.setModel(model);
        databaseshow.setRowHeight(150);
        databaseshow.setShowGrid(true);

        databaseshow.setModel(model);
        databaseshow.getColumnModel().getColumn(0).setPreferredWidth(270);
        databaseshow.getColumnModel().getColumn(1).setPreferredWidth(80);
        databaseshow.getColumnModel().getColumn(2).setPreferredWidth(210);
        databaseshow.getColumnModel().getColumn(3).setPreferredWidth(90);
        
    }//GEN-LAST:event_jButton11ActionPerformed

    private void changeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changeMouseClicked

        
    }//GEN-LAST:event_changeMouseClicked

    private void methordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_methordMouseClicked

    }//GEN-LAST:event_methordMouseClicked

    private void amountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_amountMouseClicked

 
    }//GEN-LAST:event_amountMouseClicked

    private void discountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_discountMouseClicked

       
        
    }//GEN-LAST:event_discountMouseClicked

    private void addbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addbtnActionPerformed
        
        String itemname = name.getText();
        String itemmqua = qua.getValue().toString();
        String itemprice = price.getText();
        String itemdiscount = discount.getText();
        int row = addtocart.getRowCount();
        int j = 1;
        
        name.setText("");
        price.setText("");
        discount.setText("");
        qua.setValue(1);
        cash.setText("");
        amount.setText("");
        change.setText("");
        methord.setSelectedIndex(0);
        
        for (int i = 0; i < row; i++) 
        {
            if ( itemname ==  addtocart.getModel().getValueAt(i, 0 ).toString()) 
            {
                j = 0;
               
                addtocart(j,itemname,itemmqua,itemprice,itemdiscount);
            }
                         
        }      
       addtocart(j,itemname,itemmqua,itemprice,itemdiscount);
      
    }//GEN-LAST:event_addbtnActionPerformed

    private void addtocartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addtocartMouseClicked
        
        try
        {

            int row = addtocart.getSelectedRow();
            String click1 = (addtocart.getModel().getValueAt(row, 0 ).toString());
            String click2 = (addtocart.getModel().getValueAt(row, 1 ).toString());
            String click3 = (addtocart.getModel().getValueAt(row, 2 ).toString());
            String click4 = (addtocart.getModel().getValueAt(row, 3 ).toString());
            
            int quaa = Integer.parseInt(click2);
            
            name.setText(click1);
            qua.setValue(quaa);
            price.setText(click3);           
            discount.setText(click4);
            
            

        }

        catch(Exception e)
        {
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Someting Wrong .Try Again",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT); 
        }
        
    }//GEN-LAST:event_addtocartMouseClicked

    private void deletebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebtnActionPerformed
                
        int delete = addtocart.getSelectedRow();
        if (delete >= 0 ) 
        {
            DefaultTableModel  addtocarttable =  (DefaultTableModel)addtocart.getModel();
            int all = addtocart.getRowCount();
                
                if (all != 0) 
                    {
                        String price =  addtocart.getModel().getValueAt(delete, 2 ).toString();
                        double discountget =  Double.parseDouble(addtocart.getModel().getValueAt(delete, 3 ).toString());
                        int qty = Integer.parseInt(addtocart.getModel().getValueAt(delete, 1 ).toString());
                        String itempricealready = amount.getText();
                        
                        if (discountget != 0) 
                          {
                                double convertitemprice = Double.parseDouble(itempricealready);
                                double convertprice = Double.parseDouble(price);
                                
                                double caldisprice = convertprice - (convertprice * discountget /100) * qty;
                                amountall = convertitemprice - caldisprice;
                                amount.setText(amountall.toString());
                                addtocarttable.removeRow(delete);


                                String cashall = cash.getText();
                                String amountvalue = amount.getText();

                                double cashallconvert = Double.parseDouble(cashall);
                                double amountconvert = Double.parseDouble(amountvalue);

                                String changeall = Double.toString(cashallconvert - amountconvert);


                                change.setText(changeall);      
                          }
                        else
                          {
                                double convertitemprice = Double.parseDouble(itempricealready);
                                double convertprice = Double.parseDouble(price);

                                amountall = convertitemprice - convertprice * qty;
                                amount.setText(amountall.toString());
                                addtocarttable.removeRow(delete);


                                String cashall = cash.getText();
                                String amountvalue = amount.getText();

                                double cashallconvert = Double.parseDouble(cashall);
                                double amountconvert = Double.parseDouble(amountvalue);

                                String changeall = Double.toString(cashallconvert - amountconvert);


                                change.setText(changeall);                         
                          }
                        
                    }
                else
                    {
                         amount.setText("");
                    }
  
        }
        else
        {
             //JOptionPane.showMessageDialog(null, "Please Select Item You want to delete");
             new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Select Item You want to delete",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
        }
        
    }//GEN-LAST:event_deletebtnActionPerformed

    private void setbillbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setbillbtnActionPerformed
        
        if(!"".equals(cash.getText().trim()))
        {
            
            double cashget = Double.parseDouble(cash.getText());
            double amountget = Double.parseDouble(amount.getText());
            if (cashget >= amountget)
            {
                if ( methord.getSelectedItem().toString() == "Select")
                {
                    //JOptionPane.showMessageDialog(null, "Please Select Payment Methord");
                    new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Select Payment Methord",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                }
                else
                {
                    int addtobill = addtocart.getRowCount();
                    
                    //billdisplayprint(addtobill);
                    addbill(addtobill);
                }
            }
            else
            {
                //JOptionPane.showMessageDialog(null, "Cash is Less Than Amount ");
                new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Cash is Less Than Amount ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
            }
        }
        else
        {
            //JOptionPane.showMessageDialog(null, "Cash Is Empty ");
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Cash Is Empty ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
        }

        
    }//GEN-LAST:event_setbillbtnActionPerformed


    
  
    
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

    private void discountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_discountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_discountActionPerformed

    private void cashMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cashMouseClicked
       
    }//GEN-LAST:event_cashMouseClicked

    public void clearall()
        {
            
            name.setText("");
            price.setText("");
            discount.setText("");
            qua.setValue(1);
            displaybill.setText("");
            cash.setText("");
            amount.setText("");
            change.setText("");
            amountall = 0.0;
            
            try 
            {
                billdisplay();
            }
            catch (SQLException ex) 
            {
                new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
            }
            
            methord.setSelectedIndex(0);
            addtocart.setModel(new DefaultTableModel(null,new String[] {"Item Name","Quantity","Price","Discount"}));
            loadingshowdatabase();
            
        
        }
    
    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked

        int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
        if (confirm == 0)
        {
            System.exit(0);
        }
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked

        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_jLabel9MouseClicked

    private void detailshowpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_detailshowpanelMouseClicked

        detailshow ds = new detailshow();
        ds.setVisible(true);

    }//GEN-LAST:event_detailshowpanelMouseClicked

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked

        if (!"".equals(search.getText()))
         {
             searchdatabase();
         } 
        else 
         {
             new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Search Text Field is Empty",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
         }

    }//GEN-LAST:event_jLabel21MouseClicked

    private void two1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_two1ActionPerformed
        
        cash.setText(cash.getText() + "2");

        String c = cash.getText();
        String a = amount.getText();
        double cdouble = Double.parseDouble(c);
        double adouble = Double.parseDouble(a);
        double chnageall = cdouble - adouble;
        String changeconvert = Double.toString(chnageall);

        change.setText(changeconvert);
    }//GEN-LAST:event_two1ActionPerformed

    private void one1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_one1MouseClicked

    }//GEN-LAST:event_one1MouseClicked

    private void one1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_one1ActionPerformed

        cash.setText(cash.getText() + "1");

        String c = cash.getText();
        String a = amount.getText();
        double cdouble = Double.parseDouble(c);
        double adouble = Double.parseDouble(a);
        double chnageall = cdouble - adouble;
        String changeconvert = Double.toString(chnageall);

        change.setText(changeconvert);
    }//GEN-LAST:event_one1ActionPerformed

    private void dot1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dot1ActionPerformed

        cash.setText(cash.getText() + ".");

        String c = cash.getText();
        String a = amount.getText();
        double cdouble = Double.parseDouble(c);
        double adouble = Double.parseDouble(a);
        double chnageall = cdouble - adouble;
        String changeconvert = Double.toString(chnageall);

        change.setText(changeconvert);
        
    }//GEN-LAST:event_dot1ActionPerformed

    private void zero1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zero1ActionPerformed

       cash.setText(cash.getText() + "0");

        String c = cash.getText();
        String a = amount.getText();
        double cdouble = Double.parseDouble(c);
        double adouble = Double.parseDouble(a);
        double chnageall = cdouble - adouble;
        String changeconvert = Double.toString(chnageall);

        change.setText(changeconvert);
    }//GEN-LAST:event_zero1ActionPerformed

    private void doublezero1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doublezero1ActionPerformed

        cash.setText(cash.getText() + "00");

        String c = cash.getText();
        String a = amount.getText();
        double cdouble = Double.parseDouble(c);
        double adouble = Double.parseDouble(a);
        double chnageall = cdouble - adouble;
        String changeconvert = Double.toString(chnageall);

        change.setText(changeconvert);
    }//GEN-LAST:event_doublezero1ActionPerformed

    private void three1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_three1ActionPerformed

        cash.setText(cash.getText() + "3");

        String c = cash.getText();
        String a = amount.getText();
        double cdouble = Double.parseDouble(c);
        double adouble = Double.parseDouble(a);
        double chnageall = cdouble - adouble;
        String changeconvert = Double.toString(chnageall);

        change.setText(changeconvert);
    }//GEN-LAST:event_three1ActionPerformed

    private void six1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_six1ActionPerformed

        cash.setText(cash.getText() + "6");

        String c = cash.getText();
        String a = amount.getText();
        double cdouble = Double.parseDouble(c);
        double adouble = Double.parseDouble(a);
        double chnageall = cdouble - adouble;
        String changeconvert = Double.toString(chnageall);

        change.setText(changeconvert);
    }//GEN-LAST:event_six1ActionPerformed

    private void nine1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nine1ActionPerformed

        cash.setText(cash.getText() + "9");

        String c = cash.getText();
        String a = amount.getText();
        double cdouble = Double.parseDouble(c);
        double adouble = Double.parseDouble(a);
        double chnageall = cdouble - adouble;
        String changeconvert = Double.toString(chnageall);

        change.setText(changeconvert);
    }//GEN-LAST:event_nine1ActionPerformed

    private void eight1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eight1ActionPerformed

        cash.setText(cash.getText() + "8");

        String c = cash.getText();
        String a = amount.getText();
        double cdouble = Double.parseDouble(c);
        double adouble = Double.parseDouble(a);
        double chnageall = cdouble - adouble;
        String changeconvert = Double.toString(chnageall);

        change.setText(changeconvert);
    }//GEN-LAST:event_eight1ActionPerformed

    private void five1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_five1ActionPerformed

        cash.setText(cash.getText() + "5");

        String c = cash.getText();
        String a = amount.getText();
        double cdouble = Double.parseDouble(c);
        double adouble = Double.parseDouble(a);
        double chnageall = cdouble - adouble;
        String changeconvert = Double.toString(chnageall);

        change.setText(changeconvert);
    }//GEN-LAST:event_five1ActionPerformed

    private void four1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_four1ActionPerformed

        cash.setText(cash.getText() + "4");

        String c = cash.getText();
        String a = amount.getText();
        double cdouble = Double.parseDouble(c);
        double adouble = Double.parseDouble(a);
        double chnageall = cdouble - adouble;
        String changeconvert = Double.toString(chnageall);

        change.setText(changeconvert);
    }//GEN-LAST:event_four1ActionPerformed

    private void seven1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seven1ActionPerformed

        cash.setText(cash.getText() + "7");

        String c = cash.getText();
        String a = amount.getText();
        double cdouble = Double.parseDouble(c);
        double adouble = Double.parseDouble(a);
        double chnageall = cdouble - adouble;
        String changeconvert = Double.toString(chnageall);

        change.setText(changeconvert);
    }//GEN-LAST:event_seven1ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed

 
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
            
            String c = cash.getText();
            String a = amount.getText();
            double cdouble = Double.parseDouble(c);
            double adouble = Double.parseDouble(a);
            double chnageall = cdouble - adouble;
            String changeconvert = Double.toString(chnageall);

            change.setText(changeconvert);
        
    }//GEN-LAST:event_jButton10ActionPerformed

    private void clear1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear1ActionPerformed

        cash.setText("");

        change.setText("");
        
    }//GEN-LAST:event_clear1ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed

        clearall();
    }//GEN-LAST:event_jButton19ActionPerformed

    private void searchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchMouseClicked
       
        try
        {
            Runtime.getRuntime().exec("cmd /c osk");
            search.grabFocus();
        }
        catch (IOException ex)
        {
           //JOptionPane.showMessageDialog(null, "Fail to get Touch Key Board ");
           new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Fail to get Touch Key Board ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
        }
    }//GEN-LAST:event_searchMouseClicked

    private void clearbillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbillActionPerformed

        displaybill.setText("");
        try
        {
            billdisplay();
        }
        catch (SQLException ex)
        {
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
        }
    }//GEN-LAST:event_clearbillActionPerformed

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
                int orderid = Integer.parseInt(mq.getfinalorderid()) + 1;
                String orderidS = Integer.toString(orderid);

                LocalDate date = java.time.LocalDate.now();
                LocalTime time = java.time.LocalTime.now();

                loginpage lp = new loginpage();
                String details[] = lp.getdetails();
                String username = details[0];
                String password = details[1];
                MyQuery m = new MyQuery();
                int idemployee = m.getidemployee(username , password);

                int row = addtocart.getRowCount();
                
                for (int i = 0; i < row; i++)
                {
                    String name = addtocart.getModel().getValueAt(i, 0 ).toString();
                    double price = Double.parseDouble(addtocart.getModel().getValueAt(i, 2 ).toString());
                    double cashall = Double.parseDouble(cash.getText());
                    double changeall = Double.parseDouble(change.getText());
                    int qty = Integer.parseInt(addtocart.getModel().getValueAt(i, 1 ).toString());
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

                displaybill.print();
                clearall();

            }
            catch (Exception e)
            {
               new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
            }
        }
    }//GEN-LAST:event_printActionPerformed

    private void jPanel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseClicked
       
         main m = new main();
         m.setVisible(true);
        
         this.setVisible(false);
    }//GEN-LAST:event_jPanel12MouseClicked

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
        
        employeeUnit e = new employeeUnit();
        e.setVisible(true);
    }//GEN-LAST:event_jPanel10MouseClicked

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
       
        try 
        {
            this.setVisible(false);
            billauto b = new billauto();
            b.setVisible(true);
        } 
        catch (SQLException ex) 
        {
            
        }
    }//GEN-LAST:event_jLabel18MouseClicked

    private void jLabel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseClicked

        try
        {
            this.dispose();

            billmanual b = new billmanual();
            b.setVisible(true);
        }
        catch (Exception ex)
        {
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail..!",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
        }
    }//GEN-LAST:event_jLabel25MouseClicked

    private void searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyReleased
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if (!"".equals(search.getText()))
               {
                  searchdatabase();
               } 
            else 
               {
                 new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Search Text Field is Empty",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
                 search.grabFocus();
               }
             
        }
    }//GEN-LAST:event_searchKeyReleased

 
    public static void main(String args[]) {
       try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(billmanual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(billmanual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(billmanual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(billmanual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new billmanual().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(billmanual.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addbtn;
    private javax.swing.JTable addtocart;
    private javax.swing.JTextField amount;
    private javax.swing.JTextField cash;
    private javax.swing.JPanel category;
    private javax.swing.JTextField change;
    private javax.swing.JButton clear1;
    private javax.swing.JButton clearbill;
    private javax.swing.JTable databaseshow;
    private javax.swing.JButton deletebtn;
    private javax.swing.JPanel detailshowpanel;
    private javax.swing.JTextField discount;
    private javax.swing.JEditorPane displaybill;
    private javax.swing.JButton dot1;
    private javax.swing.JButton doublezero1;
    private javax.swing.JButton eight1;
    private javax.swing.JButton five1;
    private javax.swing.JButton four1;
    private javax.swing.JLabel imageshow;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JComboBox methord;
    private javax.swing.JLabel name;
    private javax.swing.JButton nine1;
    private javax.swing.JButton one1;
    private javax.swing.JLabel price;
    private javax.swing.JLabel price1;
    private javax.swing.JButton print;
    private javax.swing.JSpinner qua;
    private javax.swing.JTextField search;
    private javax.swing.JButton setbillbtn;
    private javax.swing.JButton seven1;
    private javax.swing.JLabel showemployeename;
    private javax.swing.JButton six1;
    private javax.swing.JButton three1;
    private javax.swing.JLabel timeshow;
    private javax.swing.JButton two1;
    private javax.swing.JButton zero1;
    // End of variables declaration//GEN-END:variables
}
