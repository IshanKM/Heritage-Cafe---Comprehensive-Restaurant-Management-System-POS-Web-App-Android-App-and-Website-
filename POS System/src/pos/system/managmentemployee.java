
package pos.system;

import com.sbix.jnotify.NPosition;
import com.sbix.jnotify.NotifyType;
import com.sbix.jnotify.NotifyWindow;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import net.proteanit.sql.DbUtils;

public class managmentemployee extends javax.swing.JFrame {

    public managmentemployee() 
    {   
        try 
          {
              
            initComponents();
            clock();
            detailsbox();
            showemployee();
            setsalary();
            decorate();
            seticon();
            
            
            LocalDate day = java.time.LocalDate.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            Date getday = new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(day));
            absentdate.setDate(getday);
            sellingdate.setDate(getday);

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
        //showemployee decorate 
        
        JTableHeader header = showemployee.getTableHeader();
        header.setForeground(Color.red);
        header.setFont(new Font("Times New Roman", Font.BOLD,16));
        ((DefaultTableCellRenderer)header.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);

        showemployee.setRowHeight(50);
        showemployee.setShowGrid(false);
        showemployee.setSelectionForeground(Color.white);
        
        //absenttable decorate 
        
        JTableHeader absenheader = absenttable.getTableHeader();
        absenheader.setForeground(Color.red);
        absenheader.setFont(new Font("Times New Roman", Font.BOLD,16));
        ((DefaultTableCellRenderer)header.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);

        absenttable.setRowHeight(50);
        absenttable.setShowGrid(false);
        showemployee.setSelectionForeground(Color.white);
        
        //oldersalaryshow decorate 
        
        JTableHeader sellingordersheader = sellingorders.getTableHeader();
        sellingordersheader.setForeground(Color.red);
        sellingordersheader.setFont(new Font("Times New Roman", Font.BOLD,16));
        ((DefaultTableCellRenderer)header.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);

        sellingorders.setRowHeight(50);
        sellingorders.setShowGrid(false);
        sellingorders.setSelectionForeground(Color.white);
      }
    
    public void setsalary()
      {
          try 
          {
             Connection con = null;
             Statement st;
             ResultSet rs;
             MyQuery mq = new MyQuery();
             
             LocalDate date = java.time.LocalDate.now();
             DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
             con = mq.getConnection();
             st = con.createStatement();

             rs = st.executeQuery( "select ID from employee ");
             while(rs.next())
               {
                   employeeUnit e = new employeeUnit();
                   e.getsalary(dtf.format(date), rs.getInt("ID"));
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

    
      public void showemployee()
        {

                            try
                                {
                                    MyQuery m = new MyQuery();
                                    Connection con = m.getConnection();
                                    Statement st; 
                                    ResultSet rs;
                                    
                                    st = con.createStatement();
                                    rs = st.executeQuery("SELECT ID,Name,Category,Short_Name,User_Name,Password,Nic_No,Age,Address,Join_Day,Contact_No FROM employee");
                                    showemployee.setModel(DbUtils.resultSetToTableModel(rs));

                                        
                                }
                            catch(Exception e)
                                {
                                    new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
                                }

        }
      
      
       public void showimg(int id) throws SQLException
        {
            Connection con = null;
            Statement st;
            ResultSet rs;
            String name = null;
            byte[] img = null;
            
            
            MyQuery mq = new MyQuery();
            con = mq.getConnection();
            st = con.createStatement();
            
            rs = st.executeQuery( "select Image from employee where ID = "+id+"");
            while(rs.next())
              {               
                 img = rs.getBytes("Image");                    
              }
         
            ImageIcon image = new  ImageIcon(img);
            Image myimg = image.getImage().getScaledInstance(165,165, Image.SCALE_SMOOTH);
            ImageIcon newimg = new ImageIcon(myimg);
            mainimg.setIcon(newimg);
  
        }
       
       public String browseimg()
        {
            String getpath = null;
         
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg","gif","png");
            fileChooser.addChoosableFileFilter(filter);
            int result = fileChooser.showSaveDialog(null);
            if(result == JFileChooser.APPROVE_OPTION)
             {
                File selectedFile = fileChooser.getSelectedFile();
                String path = selectedFile.getAbsolutePath();

                ImageIcon MyImage = new ImageIcon(path);
                Image img = MyImage.getImage();
                Image newImage = img.getScaledInstance(165,165,Image.SCALE_SMOOTH);
                ImageIcon image = new ImageIcon(newImage);
                mainimg.setIcon(image);
                getpath = path;
             }
            else if(result == JFileChooser.CANCEL_OPTION)
             {
                //JOptionPane.showMessageDialog(null, "No Image ");
                new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"No Image",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
             }
         return getpath;
        }
       
       public void getabsent(String getdate,int id)
       {
           try 
            {

               absenttable.setModel(new DefaultTableModel(null,new String[] {"Count","Date","Reason"}));
                
               MyQuery mq = new MyQuery();
               Connection con = mq.getConnection();
               Statement st;
               ResultSet rs;
               Date date = null;
               String reason = "";
               int  count = 0;
                               
               con = mq.getConnection();
               st = con.createStatement();
               
               absentlabel.setText("Absent Dates of "+getdate+ " Month");
               
               int i = 1;
               rs = st.executeQuery("SELECT Date,Reason FROM employee_absent_day WHERE Employee_ID = "+id+" AND Date LIKE '"+getdate+"%' ORDER BY Date ASC");
               while(rs.next())
                  {
                      date = rs.getDate("Date");
                      reason = rs.getString("Reason");
                      
         
                      DefaultTableModel  absent =  (DefaultTableModel)absenttable.getModel();
                      absent.addRow(new Object[] {i,date,reason});

                      i++;
                  }
            } 
           catch (Exception e)
            {
                new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
            }
           
       }
       
       public void getorderdetails(String getdate,int id)
        {
           try 
            {
               sellingorders.setModel(new DefaultTableModel(null,new String[] {"Count","Time","Date","Item","Total"}));
               topicselling.setText("Selling Orders of "+getdate+" Month");

               MyQuery m = new MyQuery();
               Connection con = m.getConnection();
               Statement st;
               ResultSet rs;
               Date date = null;
               Time time = null;
               String item = "";
               double total = 0;
               int count = 0;
               
                
               MyQuery mq = new MyQuery();
               con = mq.getConnection();
               st = con.createStatement();
               

               int a = 1;
               rs = st.executeQuery("SELECT Date,Time,Item_Name,total FROM all_orders WHERE Cashior_ID = "+id+" AND Date LIKE '"+getdate+"%' ORDER BY Date ASC");
               while(rs.next())
                  {
                      date = rs.getDate("Date");
                      time = rs.getTime("Time");
                      item = rs.getString("Item_Name");
                      total = rs.getDouble("total");
                      
                      DefaultTableModel  absent =  (DefaultTableModel)sellingorders.getModel();
                      absent.addRow(new Object[] {a,date,time,item,total});
                      
                      a++;
                  }
            } 
           catch (Exception e)
            {
                new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
            }
       }
       
       public void clearall()
         {
              
              idno.setText("");
              category.setSelectedIndex(0);
              name.setText("");
              shortname.setText("");
              uname.setText("");
              password.setText("");
              identy.setText("");
              age.setText("");   
              getjoindate.setDate(null);
              address.setText("");
              contact.setText("");
              showpath.setText("");
              mainimg.setIcon(null);
              
              basicsalary.setText("");
              payforot.setText("");
              totalsalary.setText("");
              ot.setText("");
              todayot.setValue(0);
              
              absenttable.setModel(new DefaultTableModel(null,new String[] {"Count","Date","Reason"}));
              sellingorders.setModel(new DefaultTableModel(null,new String[] {"Count","Time","Date","Item","Total"}));
              showemployee();
              
         
         }
       
       


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        detailshowpanel = new javax.swing.JPanel();
        showemployeename = new javax.swing.JLabel();
        imageshow = new javax.swing.JLabel();
        timeshow = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        mainimg = new javax.swing.JLabel();
        identylabel = new javax.swing.JLabel();
        sidlabel = new javax.swing.JLabel();
        totallabel = new javax.swing.JLabel();
        emaillabel = new javax.swing.JLabel();
        receivelabel = new javax.swing.JLabel();
        cnamelabel = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        idno = new javax.swing.JTextField();
        address = new javax.swing.JTextField();
        uname = new javax.swing.JTextField();
        password = new javax.swing.JTextField();
        categorylabel = new javax.swing.JLabel();
        identy = new javax.swing.JTextField();
        contactlabel = new javax.swing.JLabel();
        age = new javax.swing.JTextField();
        paybellabel = new javax.swing.JLabel();
        shortname = new javax.swing.JTextField();
        category = new javax.swing.JComboBox();
        updatebtn = new javax.swing.JButton();
        addbtn = new javax.swing.JButton();
        deleteregister = new javax.swing.JButton();
        browsimg = new javax.swing.JButton();
        showpath = new javax.swing.JLabel();
        joinlabel = new javax.swing.JLabel();
        getjoindate = new com.toedter.calendar.JDateChooser();
        contact = new javax.swing.JTextField();
        econtactlabel = new javax.swing.JLabel();
        clearall = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        showemployee = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        bsalarylabel = new javax.swing.JLabel();
        basicsalary = new javax.swing.JTextField();
        bsalarylabel1 = new javax.swing.JLabel();
        ot = new javax.swing.JTextField();
        bsalarylabel2 = new javax.swing.JLabel();
        payforot = new javax.swing.JTextField();
        bsalarylabel3 = new javax.swing.JLabel();
        totalsalary = new javax.swing.JTextField();
        todayot = new javax.swing.JSpinner();
        bsalarylabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        absenttable = new javax.swing.JTable();
        absentdate = new com.toedter.calendar.JDateChooser();
        absentlabel = new javax.swing.JLabel();
        searchabsent = new javax.swing.JButton();
        topicselling = new javax.swing.JLabel();
        sellingdate = new com.toedter.calendar.JDateChooser();
        searchselling = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        sellingorders = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 0, 3, new java.awt.Color(0, 0, 0)));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/managmentpage.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tempus Sans ITC", 3, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setText("MANAGMENT PAGE");

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

        jLabel1.setFont(new java.awt.Font("Prestige Elite Std", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("EMPLOYEE MANAGMENT UNIT");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 0, 0)));

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        jLabel36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel36MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 732, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(241, 241, 241)
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
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logo2.png"))); // NOI18N

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

        timeshow.setFont(new java.awt.Font("Stencil Std", 1, 48)); // NOI18N
        timeshow.setForeground(new java.awt.Color(255, 255, 255));
        timeshow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jPanel4.setBackground(new java.awt.Color(255, 0, 0));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));

        mainimg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainimg.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));

        identylabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        identylabel.setForeground(new java.awt.Color(255, 255, 255));
        identylabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        identylabel.setText("IDENTY NO");

        sidlabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        sidlabel.setForeground(new java.awt.Color(255, 255, 255));
        sidlabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sidlabel.setText("NAME");

        totallabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        totallabel.setForeground(new java.awt.Color(255, 255, 255));
        totallabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        totallabel.setText("REGISTER NUMBER");

        emaillabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        emaillabel.setForeground(new java.awt.Color(255, 255, 255));
        emaillabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        emaillabel.setText("ADDRESS");

        receivelabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        receivelabel.setForeground(new java.awt.Color(255, 255, 255));
        receivelabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        receivelabel.setText("AGE OF EMPLOYEE");

        cnamelabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        cnamelabel.setForeground(new java.awt.Color(255, 255, 255));
        cnamelabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cnamelabel.setText("SHORT NAME");

        name.setBackground(new java.awt.Color(255, 0, 0));
        name.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        name.setForeground(new java.awt.Color(255, 255, 255));
        name.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        name.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        name.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nameMouseClicked(evt);
            }
        });
        name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameActionPerformed(evt);
            }
        });

        idno.setEditable(false);
        idno.setBackground(new java.awt.Color(255, 0, 0));
        idno.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        idno.setForeground(new java.awt.Color(255, 255, 255));
        idno.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        idno.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));

        address.setBackground(new java.awt.Color(255, 0, 0));
        address.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        address.setForeground(new java.awt.Color(255, 255, 255));
        address.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        address.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        address.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addressMouseClicked(evt);
            }
        });

        uname.setBackground(new java.awt.Color(255, 0, 0));
        uname.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        uname.setForeground(new java.awt.Color(255, 255, 255));
        uname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        uname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        uname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                unameMouseClicked(evt);
            }
        });
        uname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unameActionPerformed(evt);
            }
        });

        password.setBackground(new java.awt.Color(255, 0, 0));
        password.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        password.setForeground(new java.awt.Color(255, 255, 255));
        password.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        password.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        password.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                passwordMouseClicked(evt);
            }
        });

        categorylabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        categorylabel.setForeground(new java.awt.Color(255, 255, 255));
        categorylabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        categorylabel.setText("CATEGORY");

        identy.setBackground(new java.awt.Color(255, 0, 0));
        identy.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        identy.setForeground(new java.awt.Color(255, 255, 255));
        identy.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        identy.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        identy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                identyMouseClicked(evt);
            }
        });

        contactlabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        contactlabel.setForeground(new java.awt.Color(255, 255, 255));
        contactlabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        contactlabel.setText("USER NAME");

        age.setBackground(new java.awt.Color(255, 0, 0));
        age.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        age.setForeground(new java.awt.Color(255, 255, 255));
        age.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        age.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        age.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ageMouseClicked(evt);
            }
        });
        age.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ageActionPerformed(evt);
            }
        });
        age.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ageKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ageKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ageKeyTyped(evt);
            }
        });

        paybellabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        paybellabel.setForeground(new java.awt.Color(255, 255, 255));
        paybellabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        paybellabel.setText("PASSWORD");

        shortname.setBackground(new java.awt.Color(255, 0, 0));
        shortname.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        shortname.setForeground(new java.awt.Color(255, 255, 255));
        shortname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        shortname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        shortname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                shortnameMouseClicked(evt);
            }
        });

        category.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "Admin", "Chef", "Cashier\t", "Waiter", " " }));

        updatebtn.setBackground(new java.awt.Color(0, 51, 204));
        updatebtn.setFont(new java.awt.Font("Stencil Std", 1, 18)); // NOI18N
        updatebtn.setForeground(new java.awt.Color(255, 255, 255));
        updatebtn.setText("UPDATE");
        updatebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatebtnActionPerformed(evt);
            }
        });

        addbtn.setBackground(new java.awt.Color(0, 153, 102));
        addbtn.setFont(new java.awt.Font("Stencil Std", 1, 18)); // NOI18N
        addbtn.setForeground(new java.awt.Color(255, 255, 255));
        addbtn.setText("Register");
        addbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addbtnActionPerformed(evt);
            }
        });

        deleteregister.setBackground(new java.awt.Color(0, 0, 0));
        deleteregister.setFont(new java.awt.Font("Stencil Std", 1, 18)); // NOI18N
        deleteregister.setForeground(new java.awt.Color(255, 255, 255));
        deleteregister.setText("DELETE");
        deleteregister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteregisterActionPerformed(evt);
            }
        });

        browsimg.setBackground(new java.awt.Color(255, 255, 255));
        browsimg.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        browsimg.setText("Browse");
        browsimg.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        browsimg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browsimgActionPerformed(evt);
            }
        });

        showpath.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        showpath.setForeground(new java.awt.Color(255, 255, 255));
        showpath.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        joinlabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        joinlabel.setForeground(new java.awt.Color(255, 255, 255));
        joinlabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        joinlabel.setText("JOIN DAY");

        getjoindate.setDateFormatString("yyyy-MM-dd");
        getjoindate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                getjoindateMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                getjoindateMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                getjoindateMouseReleased(evt);
            }
        });

        contact.setBackground(new java.awt.Color(255, 0, 0));
        contact.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        contact.setForeground(new java.awt.Color(255, 255, 255));
        contact.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        contact.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        contact.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                contactMouseClicked(evt);
            }
        });

        econtactlabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        econtactlabel.setForeground(new java.awt.Color(255, 255, 255));
        econtactlabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        econtactlabel.setText("CONTACT NO");

        clearall.setBackground(new java.awt.Color(255, 255, 255));
        clearall.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        clearall.setText("Clear");
        clearall.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        clearall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearallActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(browsimg, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clearall, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(mainimg, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(contactlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(joinlabel))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(uname, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(getjoindate, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(emaillabel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(paybellabel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(password)
                                        .addGap(179, 179, 179)
                                        .addComponent(contact, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(address)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(cnamelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(shortname, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(receivelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(sidlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(totallabel)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(idno, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                                    .addComponent(age))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(econtactlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addComponent(identylabel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(21, 21, 21)
                                            .addComponent(identy, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addComponent(categorylabel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(deleteregister, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(updatebtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(showpath, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(showpath, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(mainimg, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(browsimg, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clearall, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sidlabel)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(totallabel)
                                        .addComponent(idno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(categorylabel)
                                        .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cnamelabel)
                                    .addComponent(shortname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(receivelabel)
                                    .addComponent(age, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(identylabel)
                                    .addComponent(identy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(contactlabel)
                                    .addComponent(uname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(paybellabel)
                                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(econtactlabel)
                                    .addComponent(contact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(addbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(updatebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(deleteregister, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(getjoindate, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(joinlabel)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(emaillabel)))
                                .addGap(10, 10, 10)))
                        .addGap(6, 6, 6)))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(0, 153, 51));

        showemployee = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex,int colIndex)
            {
                return false;
            }
        };
        showemployee.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        showemployee.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Category", "Short Name", "User_Name", "Password", "Nic_No", "Age", "Address", "Join Day"
            }
        ));
        showemployee.setSelectionBackground(new java.awt.Color(255, 0, 0));
        showemployee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showemployeeMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(showemployee);
        if (showemployee.getColumnModel().getColumnCount() > 0) {
            showemployee.getColumnModel().getColumn(3).setHeaderValue("Short Name");
            showemployee.getColumnModel().getColumn(4).setHeaderValue("User_Name");
            showemployee.getColumnModel().getColumn(5).setHeaderValue("Password");
            showemployee.getColumnModel().getColumn(6).setHeaderValue("Nic_No");
            showemployee.getColumnModel().getColumn(7).setHeaderValue("Age");
            showemployee.getColumnModel().getColumn(8).setHeaderValue("Address");
            showemployee.getColumnModel().getColumn(9).setHeaderValue("Join Day");
        }

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Sitka Heading", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("ALL EMPLOYEES");
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.setBackground(new java.awt.Color(0, 0, 204));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Salary Manage");
        jLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        bsalarylabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        bsalarylabel.setForeground(new java.awt.Color(255, 255, 255));
        bsalarylabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bsalarylabel.setText("Basic Salary");

        basicsalary.setBackground(new java.awt.Color(0, 0, 204));
        basicsalary.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        basicsalary.setForeground(new java.awt.Color(255, 255, 255));
        basicsalary.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        basicsalary.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        basicsalary.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                basicsalaryMouseClicked(evt);
            }
        });
        basicsalary.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                basicsalaryKeyReleased(evt);
            }
        });

        bsalarylabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        bsalarylabel1.setForeground(new java.awt.Color(255, 255, 255));
        bsalarylabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bsalarylabel1.setText("OT HOURS");

        ot.setBackground(new java.awt.Color(0, 0, 204));
        ot.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        ot.setForeground(new java.awt.Color(255, 255, 255));
        ot.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ot.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        ot.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                otMouseClicked(evt);
            }
        });
        ot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otActionPerformed(evt);
            }
        });
        ot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                otKeyReleased(evt);
            }
        });

        bsalarylabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        bsalarylabel2.setForeground(new java.awt.Color(255, 255, 255));
        bsalarylabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bsalarylabel2.setText("PAY FOR A OT HOUR");

        payforot.setBackground(new java.awt.Color(0, 0, 204));
        payforot.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        payforot.setForeground(new java.awt.Color(255, 255, 255));
        payforot.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        payforot.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        payforot.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                payforotMouseClicked(evt);
            }
        });
        payforot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                payforotKeyReleased(evt);
            }
        });

        bsalarylabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        bsalarylabel3.setForeground(new java.awt.Color(255, 255, 255));
        bsalarylabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bsalarylabel3.setText("TOTAL SALARY");

        totalsalary.setEditable(false);
        totalsalary.setBackground(new java.awt.Color(0, 0, 204));
        totalsalary.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        totalsalary.setForeground(new java.awt.Color(255, 255, 255));
        totalsalary.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        totalsalary.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        totalsalary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalsalaryActionPerformed(evt);
            }
        });
        totalsalary.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                totalsalaryKeyReleased(evt);
            }
        });

        todayot.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        todayot.setToolTipText("");

        bsalarylabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        bsalarylabel4.setForeground(new java.awt.Color(255, 255, 255));
        bsalarylabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bsalarylabel4.setText("Today OT");

        absenttable = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex,int colIndex)
            {
                return false;
            }
        };
        absenttable.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        absenttable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Count", "Date", "reason"
            }
        ));
        absenttable.setSelectionBackground(new java.awt.Color(255, 0, 0));
        absenttable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                absenttableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(absenttable);

        absentdate.setDateFormatString("yyyy-MMM-d");
        absentdate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        absentlabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        absentlabel.setForeground(new java.awt.Color(255, 255, 255));
        absentlabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        absentlabel.setText("Absent Dates of this Month");
        absentlabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        searchabsent.setBackground(new java.awt.Color(0, 0, 0));
        searchabsent.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        searchabsent.setForeground(new java.awt.Color(255, 255, 255));
        searchabsent.setText("Search");
        searchabsent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchabsentActionPerformed(evt);
            }
        });

        topicselling.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        topicselling.setForeground(new java.awt.Color(255, 255, 255));
        topicselling.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        topicselling.setText("Selling Orders Of This Month");
        topicselling.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        sellingdate.setDateFormatString("yyyy-MMM-d");
        sellingdate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        searchselling.setBackground(new java.awt.Color(0, 0, 0));
        searchselling.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        searchselling.setForeground(new java.awt.Color(255, 255, 255));
        searchselling.setText("Search");
        searchselling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchsellingActionPerformed(evt);
            }
        });

        sellingorders = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex,int colIndex)
            {
                return false;
            }
        };
        sellingorders.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        sellingorders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Count", "Date", "Time", "Item", "Total"
            }
        ));
        sellingorders.setSelectionBackground(new java.awt.Color(255, 0, 0));
        sellingorders.setSelectionForeground(new java.awt.Color(255, 0, 0));
        sellingorders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sellingordersMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(sellingorders);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(payforot)
                                    .addComponent(bsalarylabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(basicsalary, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(bsalarylabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(ot)
                                            .addComponent(bsalarylabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(bsalarylabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(todayot, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(2, 2, 2))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(bsalarylabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(totalsalary, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(absentlabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(absentdate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(searchabsent, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                        .addComponent(sellingdate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(searchselling, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(topicselling, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bsalarylabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(basicsalary, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bsalarylabel4)
                            .addComponent(bsalarylabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(todayot, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addComponent(bsalarylabel2)
                        .addGap(18, 18, 18)
                        .addComponent(payforot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bsalarylabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(totalsalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(absentlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(topicselling, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(absentdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(searchabsent, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(sellingdate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchselling, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap())
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel14.setBackground(new java.awt.Color(255, 0, 0));
        jPanel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel14MouseClicked(evt);
            }
        });

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/basicmanagment.png"))); // NOI18N
        jLabel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        jLabel25.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("BASIC ACTIVITIES");

        jLabel26.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("MANAGMENT");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel15.setBackground(new java.awt.Color(0, 0, 255));
        jPanel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel15MouseClicked(evt);
            }
        });

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/managemployee.png"))); // NOI18N
        jLabel27.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        jLabel28.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("EMPLOYEES");

        jLabel29.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("MANAGMENT");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel29)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel17.setBackground(new java.awt.Color(0, 153, 102));
        jPanel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel17MouseClicked(evt);
            }
        });

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/itemmanage.png"))); // NOI18N
        jLabel30.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        jLabel31.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("ITEMS");

        jLabel32.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("MANAGMENT");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel32)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel18.setBackground(new java.awt.Color(0, 0, 0));
        jPanel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel18MouseClicked(evt);
            }
        });

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/suppliermanage.png"))); // NOI18N
        jLabel33.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        jLabel34.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("SUPPLIER");

        jLabel35.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("MANAGMENT");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel35)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(21, 21, 21)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23)
                .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(271, 271, 271)
                        .addComponent(timeshow, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(detailshowpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(timeshow, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(detailshowpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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

    private void nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameActionPerformed

    }//GEN-LAST:event_nameActionPerformed

    private void unameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_unameActionPerformed

    private void ageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ageActionPerformed

    private void showemployeeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showemployeeMouseClicked
        
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        
        double othours = 0;
        try
          {
              absentdate.setDate(null);
              sellingdate.setDate(null);
              
              int row = showemployee.getSelectedRow();
              int id = Integer.parseInt(showemployee.getModel().getValueAt(row, 0 ).toString());
              idno.setText(showemployee.getModel().getValueAt(row, 0 ).toString());
              category.setSelectedItem(showemployee.getModel().getValueAt(row, 2 ).toString());
              name.setText(showemployee.getModel().getValueAt(row, 1 ).toString());
              shortname.setText(showemployee.getModel().getValueAt(row, 3 ).toString());
              uname.setText(showemployee.getModel().getValueAt(row, 4 ).toString());
              password.setText(showemployee.getModel().getValueAt(row, 5 ).toString());
              identy.setText(showemployee.getModel().getValueAt(row, 6 ).toString());
              age.setText(showemployee.getModel().getValueAt(row, 7 ).toString());
              Date getdate = new SimpleDateFormat("yyyy-MM-dd").parse(showemployee.getModel().getValueAt(row, 9 ).toString());
              getjoindate.setDate(getdate);
              address.setText(showemployee.getModel().getValueAt(row, 8 ).toString());
              contact.setText(showemployee.getModel().getValueAt(row, 10 ).toString());
              showimg(id);
              
              basicsalary.setText("");
              ot.setText("");
              payforot.setText("");
              totalsalary.setText("");
              
              MyQuery mq = new MyQuery();
              con = mq.getConnection();
              st = con.createStatement();
              
              rs = st.executeQuery( "select * from employee_salary where Employee_ID = "+id+"");
              while(rs.next())
                {
                    basicsalary.setText(rs.getString("Basic_Salary"));
                    payforot.setText(rs.getString("Pay_for_OT"));
                    totalsalary.setText(rs.getString("total_Salary"));
               }
              
              LocalDate date = java.time.LocalDate.now();
              DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
              
              rs = st.executeQuery( "select SUM(OT_Hours) from ot_hours where Employee_ID = "+id+" AND Date LIKE '"+dtf.format(date)+"%' ");
                 while(rs.next())
                  {
                      othours = rs.getDouble("SUM(OT_Hours)");
                      ot.setText(Double.toString(othours));
                  }
               
              
             
              getabsent(dtf.format(date),id);
              
              getorderdetails(dtf.format(date),id);
          }
        catch (Exception e) 
          {
               new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
          }
        
        
        
    }//GEN-LAST:event_showemployeeMouseClicked

    private void browsimgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browsimgActionPerformed
            
            String path =browseimg();
            showpath.setText(path);
         
    }//GEN-LAST:event_browsimgActionPerformed

    private void addbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addbtnActionPerformed
     
            if ( !"".equals(name.getText()) &&!"".equals(shortname.getText()) &&!"".equals(uname.getText()) &&!"".equals(password.getText()) &&!"".equals(idno.getText()) &&!"".equals(address.getText()) &&!"".equals(identy.getText()) && !"".equals(contact.getText()) && category.getSelectedItem() != "Select" && null != getjoindate.getDate() && !"".equals(basicsalary.getText()) && !"".equals(payforot.getText())) 
              {
                if (password.getText().trim().length() > 7) 
                 {
                   if (!"".equals(showpath.getText()) && mainimg.getIcon() != null) 
                   {
                       try
                        {

                           Connection con = null;
                           PreparedStatement ps = null;
                           ResultSet rs = null;
                           Statement st = null;
                           
                           int id = 0;
                           Date date = getjoindate.getDate();
                           DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                           InputStream img = new FileInputStream(new File(showpath.getText()));


                           int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
                               if (confirm == 0)
                                   {

                                       MyQuery m = new MyQuery();
                                       con = m.getConnection();
                                       String query = "select * from employee WHERE User_Name =? OR Password = ?";
                                       ps = con.prepareStatement(query);
                                       ps.setString(1, uname.getText());
                                       ps.setString(2, password.getText());
                                       rs = ps.executeQuery();

                                       if(rs.next())
                                           {
                                               //JOptionPane.showMessageDialog(this,"Please Change User Name or Password");
                                               new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Change User Name or Password",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);

                                           }

                                       else
                                           {

                                                ps = con.prepareStatement("INSERT INTO employee VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                                                ps.setInt(1, 0);
                                                ps.setString(2, name.getText());
                                                ps.setString(3, (String) category.getSelectedItem());
                                                ps.setString(4, shortname.getText());
                                                ps.setString(5, uname.getText());
                                                ps.setString(6, password.getText());
                                                ps.setString(7, identy.getText());
                                                ps.setInt(8, Integer.parseInt(age.getText()));
                                                ps.setString(9, address.getText());
                                                ps.setString(10, contact.getText());
                                                ps.setBlob(11, img);
                                                ps.setString(12, df.format(date));
                                                ps.setString(13,"");
                                                
                                                ps.executeUpdate();
                                                MyQuery mq = new MyQuery();
                                                con = mq.getConnection();
                                                st = con.createStatement();

                                                rs = st.executeQuery( "select id from employee where User_Name = '"+uname.getText()+"' AND Password = '"+password.getText()+"'");
                                                while(rs.next())
                                                  {               
                                                      id = rs.getInt("id");
                                                  }
                                                
                                                
                                                ps = con.prepareStatement("INSERT INTO employee_salary VALUES(?,?,?,?,?,?)");
                                                ps.setInt(1,0);
                                                ps.setInt(2, id);
                                                ps.setDouble(3, Double.parseDouble(basicsalary.getText()));
                                                ps.setDouble(4, Double.parseDouble(payforot.getText()));
                                                ps.setString(5, "yes");
                                                ps.setDouble(6,0);
                                                ps.executeUpdate();
                                                
                                                LocalDate dateforcall = java.time.LocalDate.now();
                                                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
                                                
                                                ps = con.prepareStatement("INSERT INTO ot_hours VALUES (?,?,?,?)");
                                                ps.setInt(1, 0);
                                                ps.setString(2, df.format(date));
                                                ps.setInt(3, id);
                                                ps.setDouble(4, Double.parseDouble(todayot.getValue().toString()));
                                                ps.executeUpdate();
                                                
                                                employeeUnit e = new employeeUnit();
                                                e.getsalary(dtf.format(dateforcall),id);
                                                
                                                //JOptionPane.showMessageDialog(null,"Employee Added Successfully...!");
                                                new NotifyWindow(NotifyType.SUCCESS_NOTIFICATION,"Employee Added Successfully...!",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                                                clearall();
                                                showemployee();
                                           } 

                                   }

                        }

                       catch(Exception e)
                        {
                              new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Error.Please Try Again",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
                        }   
                     
                      } 
                    else
                       {
                           //JOptionPane.showMessageDialog(null,"Please Fill All Details");
                           new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Change the Image",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                       }
                  } 
                  else 
                  {
                       new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Password Too Short",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                  }
                     
              }          
            else 
              {
                //JOptionPane.showMessageDialog(this,"This Image Is Already use for Employee.Please Change the Image From Browse Button");
                  new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Fill All Details",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                
              }
 
        
    }//GEN-LAST:event_addbtnActionPerformed

    private void updatebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatebtnActionPerformed
        
           
        if (mainimg.getIcon() != null && !"".equals(name.getText()) &&!"".equals(shortname.getText()) &&!"".equals(uname.getText()) &&!"".equals(password.getText()) &&!"".equals(idno.getText()) &&!"".equals(address.getText()) &&!"".equals(identy.getText()) && !"".equals(contact.getText()) && category.getSelectedItem() != "Select" && null != getjoindate.getDate() && !"".equals(basicsalary.getText()) && !"".equals(payforot.getText())) 
         {    
          if (password.getText().trim().length() > 7) 
           {
            int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
            if (confirm == 0)
            {
                try
                  {
                      
                    int id = Integer.parseInt(idno.getText());
                    Statement st = null; 
                    ResultSet rs;

                    
                    Date date = getjoindate.getDate();
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    MyQuery m = new MyQuery();
                    Connection con = m.getConnection();
                    
                    if (!"".equals(showpath.getText())) 
                      {
                            //update from iemployee table
                            InputStream img = new FileInputStream(new File(showpath.getText()));
                            
                            
                            PreparedStatement ps = con.prepareStatement("UPDATE employee SET Name =?,Category = ?,Short_Name = ?, User_Name =?, Password = ?,Nic_No = ?, Age =?, Address = ?, Contact_No = ?, Image = ?,Join_Day = ? WHERE ID ="+id+"");
                            ps.setString(1, name.getText());
                            ps.setString(2, (String) category.getSelectedItem());
                            ps.setString(3, shortname.getText());
                            ps.setString(4, uname.getText());
                            ps.setString(5, password.getText());
                            ps.setString(6, identy.getText());
                            ps.setInt(7, Integer.parseInt(age.getText()));
                            ps.setString(8, address.getText());
                            ps.setString(9, contact.getText());
                            ps.setBlob(10, img);
                            ps.setString(11, df.format(date));

                            ps.executeUpdate();
                            
                            String query = "select * from employee_salary WHERE Employee_ID = ?";
                            ps = con.prepareStatement(query);
                            ps.setInt(1, id);
                            rs = ps.executeQuery();

                                       if(rs.next())
                                           {
                                               ps = con.prepareStatement("UPDATE employee_salary SET Basic_Salary =?,pay_for_OT = ? WHERE Employee_ID ="+id+"");
                                               ps.setString(1, basicsalary.getText());
                                               ps.setDouble(2, Double.parseDouble(payforot.getText()));
                                               ps.executeUpdate();
                                           }

                                       else
                                           {
                                               ps = con.prepareStatement("INSERT INTO employee_salary VALUES (?,?,?,?,?,?)");
                                               ps.setInt(1, 0);
                                               ps.setInt(2, id);
                                               ps.setDouble(3, Double.parseDouble(basicsalary.getText()));
                                               ps.setDouble(4, Double.parseDouble(payforot.getText()));
                                               ps.setString(5, "yes");
                                               ps.setDouble(6, 0);
                                               
                                               ps.executeUpdate();
                                           
                                           }      
                            LocalDate day = java.time.LocalDate.now();
                            DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            ps = con.prepareStatement("INSERT INTO ot_hours VALUES (?,?,?,?)");
                            ps.setInt(1, 0);
                            ps.setString(2, dt.format(day));
                            ps.setInt(3, id);
                            ps.setDouble(4, Double.parseDouble(todayot.getValue().toString()));
                            ps.executeUpdate();
                            
                            //JOptionPane.showMessageDialog(null," Update Successfully...!");
                            new NotifyWindow(NotifyType.SUCCESS_NOTIFICATION," Update Successfully...!",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                            
                            LocalDate dateforcall = java.time.LocalDate.now();
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
                            
                            employeeUnit e = new employeeUnit();
                            e.getsalary(dtf.format(dateforcall),id);
                            clearall();
                            showemployee();
                            detailsbox();
                      }
                    else                         
                      {
                            PreparedStatement ps = con.prepareStatement("UPDATE employee SET Name =?,Category = ?,Short_Name = ?, User_Name =?, Password = ?,Nic_No = ?, Age =?, Address = ?, Contact_No = ?,Join_Day = ? WHERE ID ="+id+"");
                            ps.setString(1, name.getText());
                            ps.setString(2, (String) category.getSelectedItem());
                            ps.setString(3, shortname.getText());
                            ps.setString(4, uname.getText());
                            ps.setString(5, password.getText());
                            ps.setString(6, identy.getText());
                            ps.setInt(7, Integer.parseInt(age.getText()));
                            ps.setString(8, address.getText());
                            ps.setString(9, contact.getText());
                            ps.setString(10, df.format(date));

                            ps.executeUpdate();
                            
                             String query = "select * from employee_salary WHERE Employee_ID = ?";
                            ps = con.prepareStatement(query);
                            ps.setInt(1, id);
                            rs = ps.executeQuery();

                                       if(rs.next())
                                           {
                                               ps = con.prepareStatement("UPDATE employee_salary SET Basic_Salary =?,pay_for_OT = ? WHERE Employee_ID ="+id+"");
                                               ps.setString(1, basicsalary.getText());
                                               ps.setDouble(2, Double.parseDouble(payforot.getText()));
                                               ps.executeUpdate();
                                           }

                                       else
                                           {
                                               ps = con.prepareStatement("INSERT INTO employee_salary VALUES (?,?,?,?,?,?)");
                                               ps.setInt(1, 0);
                                               ps.setInt(2, id);
                                               ps.setDouble(3, Double.parseDouble(basicsalary.getText()));
                                               ps.setDouble(4, Double.parseDouble(payforot.getText()));
                                               ps.setString(5, "yes");
                                               ps.setDouble(6, 0);
                                               
                                               ps.executeUpdate();
                                           
                                           }
                                       
                            LocalDate day = java.time.LocalDate.now();
                            DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            ps = con.prepareStatement("INSERT INTO ot_hours VALUES (?,?,?,?)");
                            ps.setInt(1, 0);
                            ps.setString(2, dt.format(day));
                            ps.setInt(3, id);
                            ps.setDouble(4, Double.parseDouble(todayot.getValue().toString()));
                            ps.executeUpdate();
                            
                            //JOptionPane.showMessageDialog(null," Update Successfully...!"); 
                            new NotifyWindow(NotifyType.SUCCESS_NOTIFICATION," Update Successfully...!",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                            
                            LocalDate dateforcall = java.time.LocalDate.now();
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
                            
                            employeeUnit e = new employeeUnit();
                            e.getsalary(dtf.format(dateforcall),id);
                            clearall();
                            showemployee();
                      }
                   }
                catch(Exception e)
                   {
                         new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Somethig Wrong.Try Again",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
                         JOptionPane.showMessageDialog(this, e);
                   }   
            }
          }
          else
          {
               new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Password Too Short",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
          }
         }
        else
         {
            //JOptionPane.showMessageDialog(null,"Please Fill All Details");
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Fill All Details",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
         }
 
    }//GEN-LAST:event_updatebtnActionPerformed

    private void getjoindateMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_getjoindateMouseReleased
       
        
    }//GEN-LAST:event_getjoindateMouseReleased

    private void getjoindateMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_getjoindateMouseExited
      
        
    }//GEN-LAST:event_getjoindateMouseExited

    private void getjoindateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_getjoindateMouseClicked
        
        
 
    }//GEN-LAST:event_getjoindateMouseClicked

    private void deleteregisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteregisterActionPerformed
        
        int row = showemployee.getSelectedRow();
        int id = Integer.parseInt(idno.getText());
        
        if (row >= 0) 
        {
            int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
            if (confirm == 0)
            {
                try
                 {
                    //delete from Employee table

                    MyQuery m = new MyQuery();
                    Connection con = m.getConnection();
                    PreparedStatement ps = con.prepareStatement("DELETE FROM employee WHERE ID = "+id+"");
                    ps.executeUpdate();
                    //JOptionPane.showMessageDialog(this,"Successfully Deleted");
                    new NotifyWindow(NotifyType.SUCCESS_NOTIFICATION," Successfully Deleted",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                    showemployee();
                    
                 }
                catch(Exception e)
                 {
                      new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
                 }          
            }
        }
        else
        {
            //JOptionPane.showMessageDialog(null, "Please Select the Row You Want To Delete");
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Select the Row You Want To Delete",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
        }
            
    }//GEN-LAST:event_deleteregisterActionPerformed

    private void ageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ageKeyPressed
       
        
            
    }//GEN-LAST:event_ageKeyPressed

    private void ageKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ageKeyTyped
        
        
    }//GEN-LAST:event_ageKeyTyped

    private void ageKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ageKeyReleased
       
        if ("" != age.getText()) 
        {
             try 
             {
                int i = Integer.parseInt(age.getText());
             } 
            catch (Exception e) 
             {
                 //JOptionPane.showMessageDialog(null, "Please Input Numbers");
                 new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Input Numbers",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                 age.setText("");
             }
        }
           
    }//GEN-LAST:event_ageKeyReleased

    private void nameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nameMouseClicked
        
         try
          {
            Runtime.getRuntime().exec("cmd /c osk");
            name.grabFocus();
          }
        catch (IOException ex)
          {
             //JOptionPane.showMessageDialog(null, "Fail to get Touch Key Board ");
             new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Fail to get Touch Key Board ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
          }
        
    }//GEN-LAST:event_nameMouseClicked

    private void shortnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_shortnameMouseClicked
       
         try
          {
            Runtime.getRuntime().exec("cmd /c osk");
            shortname.grabFocus();
          }
        catch (IOException ex)
          {
             //JOptionPane.showMessageDialog(null, "Fail to get Touch Key Board ");
             new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Fail to get Touch Key Board ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT); 
          }
        
    }//GEN-LAST:event_shortnameMouseClicked

    private void unameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_unameMouseClicked
      
         try
          {
            Runtime.getRuntime().exec("cmd /c osk");
            uname.grabFocus();
          }
        catch (IOException ex)
          {
             //JOptionPane.showMessageDialog(null, "Fail to get Touch Key Board ");
             new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Fail to get Touch Key Board ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
          }
        
    }//GEN-LAST:event_unameMouseClicked

    private void passwordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_passwordMouseClicked
       
         try
          {
            Runtime.getRuntime().exec("cmd /c osk");
            password.grabFocus();
          }
        catch (IOException ex)
          {
             //JOptionPane.showMessageDialog(null, "Fail to get Touch Key Board ");
              new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Fail to get Touch Key Board ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
          }
        
    }//GEN-LAST:event_passwordMouseClicked

    private void contactMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contactMouseClicked
       
         try
          {
            Runtime.getRuntime().exec("cmd /c osk");
            contact.grabFocus();
          }
        catch (IOException ex)
          {
             //JOptionPane.showMessageDialog(null, "Fail to get Touch Key Board ");
             new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Fail to get Touch Key Board ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
          }
        
    }//GEN-LAST:event_contactMouseClicked

    private void identyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_identyMouseClicked
       
         try
          {
            Runtime.getRuntime().exec("cmd /c osk");
            identy.grabFocus();
          }
        catch (IOException ex)
          {
             //JOptionPane.showMessageDialog(null, "Fail to get Touch Key Board ");
              new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Fail to get Touch Key Board ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
          }
        
    }//GEN-LAST:event_identyMouseClicked

    private void ageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ageMouseClicked
        
         try
          {
            Runtime.getRuntime().exec("cmd /c osk");
            age.grabFocus();
          }
        catch (IOException ex)
          {
             //JOptionPane.showMessageDialog(null, "Fail to get Touch Key Board ");
              new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Fail to get Touch Key Board ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
          }
        
    }//GEN-LAST:event_ageMouseClicked

    private void addressMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addressMouseClicked
      
         try
          {
            Runtime.getRuntime().exec("cmd /c osk");
            address.grabFocus();
          }
        catch (IOException ex)
          {
             //JOptionPane.showMessageDialog(null, "Fail to get Touch Key Board ");
              new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Fail to get Touch Key Board ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
          }
        
    }//GEN-LAST:event_addressMouseClicked

    private void basicsalaryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_basicsalaryMouseClicked

        try
        {
            Runtime.getRuntime().exec("cmd /c osk");
            basicsalary.grabFocus();
        }
        catch (IOException ex)
        {
            //JOptionPane.showMessageDialog(null, "Fail to get Touch Key Board ");
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Fail to get Touch Key Board ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
        }
    }//GEN-LAST:event_basicsalaryMouseClicked

    private void basicsalaryKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_basicsalaryKeyReleased

        if ("" != basicsalary.getText())
        {
            try
            {
                Double i = Double.parseDouble(basicsalary.getText());
            }
            catch (Exception e)
            {
                //JOptionPane.showMessageDialog(null, "Please Input Numbers");
                new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Input Numbers ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                basicsalary.setText("");
            }
        }
    }//GEN-LAST:event_basicsalaryKeyReleased

    private void otMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_otMouseClicked

        try
        {
            Runtime.getRuntime().exec("cmd /c osk");
            ot.grabFocus();
        }
        catch (IOException ex)
        {
            //JOptionPane.showMessageDialog(null, "Fail to get Touch Key Board ");
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Fail to get Touch Key Board ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
        }
    }//GEN-LAST:event_otMouseClicked

    private void otActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_otActionPerformed

    private void otKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_otKeyReleased

        if ("" != ot.getText())
        {
            try
            {
                Double i = Double.parseDouble(ot.getText());
            }
            catch (Exception e)
            {
                //JOptionPane.showMessageDialog(null, "Please Input Numbers");
                new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Input Numbers ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                ot.setText("");
            }
        }
    }//GEN-LAST:event_otKeyReleased

    private void payforotMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_payforotMouseClicked

        try
        {
            Runtime.getRuntime().exec("cmd /c osk");
            payforot.grabFocus();
        }
        catch (IOException ex)
        {
            //JOptionPane.showMessageDialog(null, "Fail to get Touch Key Board ");
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Fail to get Touch Key Board ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
        }
    }//GEN-LAST:event_payforotMouseClicked

    private void payforotKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_payforotKeyReleased

        if ("" != payforot.getText())
        {
            try
            {
                Double i = Double.parseDouble(payforot.getText());
            }
            catch (Exception e)
            {
                //JOptionPane.showMessageDialog(null, "Please Input Numbers");
                new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Input Numbers ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                payforot.setText("");
            }
        }
    }//GEN-LAST:event_payforotKeyReleased

    private void totalsalaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalsalaryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalsalaryActionPerformed

    private void totalsalaryKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_totalsalaryKeyReleased

    }//GEN-LAST:event_totalsalaryKeyReleased

    private void searchabsentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchabsentActionPerformed

        int row = showemployee.getSelectedRow();
        int id = Integer.parseInt(showemployee.getModel().getValueAt(row, 0 ).toString());
        if ( 0 <= row)
        {
            Date month = absentdate.getDate();
            DateFormat df = new SimpleDateFormat("yyyy-MM");
            getabsent(df.format(month).toString(),id);
        }
        else
        {
            //JOptionPane.showMessageDialog(null, "Please Select Employee in the Table");
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Select Employee in the Table",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
        }
    }//GEN-LAST:event_searchabsentActionPerformed

    private void absenttableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_absenttableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_absenttableMouseClicked

    private void searchsellingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchsellingActionPerformed

        int row = showemployee.getSelectedRow();
        int id = Integer.parseInt(showemployee.getModel().getValueAt(row, 0 ).toString());
        if ( 0 <= row)
        {
            Date month = sellingdate.getDate();
            DateFormat df = new SimpleDateFormat("yyyy-MM");
            getorderdetails(df.format(month).toString(),id);
        }
        else
        {
           // JOptionPane.showMessageDialog(null, "Please Select Employee in the Table");
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Select Employee in the Table",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
        }
    }//GEN-LAST:event_searchsellingActionPerformed

    private void sellingordersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sellingordersMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_sellingordersMouseClicked

    private void jPanel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseClicked

        managmentbasic m = new managmentbasic();
        m.setVisible(true);
       this.dispose();

    }//GEN-LAST:event_jPanel14MouseClicked

    private void jPanel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel15MouseClicked

        managmentemployee m = new managmentemployee();
        m.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel15MouseClicked

    private void jPanel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel17MouseClicked

        managmentitem m = new managmentitem();
        m.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_jPanel17MouseClicked

    private void jPanel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel18MouseClicked

        managmentsupplier m = new managmentsupplier();
        m.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel18MouseClicked

    private void clearallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearallActionPerformed

            clearall();

    }//GEN-LAST:event_clearallActionPerformed

    private void jPanel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseClicked
       
        this.dispose();
        
        main m  = new main();
        m.setVisible(true);
    }//GEN-LAST:event_jPanel12MouseClicked

    private void jLabel36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel36MouseClicked
       
        this.dispose();
        
        managmentemployee m = new managmentemployee();
        m.setVisible(true);
    }//GEN-LAST:event_jLabel36MouseClicked


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
            java.util.logging.Logger.getLogger(managmentemployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(managmentemployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(managmentemployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(managmentemployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new managmentemployee().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser absentdate;
    private javax.swing.JLabel absentlabel;
    private javax.swing.JTable absenttable;
    private javax.swing.JButton addbtn;
    private javax.swing.JTextField address;
    private javax.swing.JTextField age;
    private javax.swing.JTextField basicsalary;
    private javax.swing.JButton browsimg;
    private javax.swing.JLabel bsalarylabel;
    private javax.swing.JLabel bsalarylabel1;
    private javax.swing.JLabel bsalarylabel2;
    private javax.swing.JLabel bsalarylabel3;
    private javax.swing.JLabel bsalarylabel4;
    private javax.swing.JComboBox category;
    private javax.swing.JLabel categorylabel;
    private javax.swing.JButton clearall;
    private javax.swing.JLabel cnamelabel;
    private javax.swing.JTextField contact;
    private javax.swing.JLabel contactlabel;
    private javax.swing.JButton deleteregister;
    private javax.swing.JPanel detailshowpanel;
    private javax.swing.JLabel econtactlabel;
    private javax.swing.JLabel emaillabel;
    private com.toedter.calendar.JDateChooser getjoindate;
    private javax.swing.JTextField identy;
    private javax.swing.JLabel identylabel;
    private javax.swing.JTextField idno;
    private javax.swing.JLabel imageshow;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel joinlabel;
    private javax.swing.JLabel mainimg;
    private javax.swing.JTextField name;
    private javax.swing.JTextField ot;
    private javax.swing.JTextField password;
    private javax.swing.JLabel paybellabel;
    private javax.swing.JTextField payforot;
    private javax.swing.JLabel receivelabel;
    private javax.swing.JButton searchabsent;
    private javax.swing.JButton searchselling;
    private com.toedter.calendar.JDateChooser sellingdate;
    private javax.swing.JTable sellingorders;
    private javax.swing.JTextField shortname;
    private javax.swing.JTable showemployee;
    private javax.swing.JLabel showemployeename;
    private javax.swing.JLabel showpath;
    private javax.swing.JLabel sidlabel;
    private javax.swing.JLabel timeshow;
    private javax.swing.JSpinner todayot;
    private javax.swing.JLabel topicselling;
    private javax.swing.JLabel totallabel;
    private javax.swing.JTextField totalsalary;
    private javax.swing.JTextField uname;
    private javax.swing.JButton updatebtn;
    // End of variables declaration//GEN-END:variables
}
