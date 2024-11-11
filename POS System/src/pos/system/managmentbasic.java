
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
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import net.proteanit.sql.DbUtils;


public class managmentbasic extends javax.swing.JFrame {

    public managmentbasic() 
    {
        try 
        {
            initComponents();
            this.setSize(1815,1000);
            setsalary();
            clock();
            detailsbox();
            showemployee();
            getallabsent();
            decorate();
            seticon();
            checkworkingday();
            
            
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
    
    public void checkworkingday()
      {
          try 
          {
             
            LocalDate month = java.time.LocalDate.now();
            DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM");

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd");
             
             Connection con = null;
             PreparedStatement pst = null;
             ResultSet rs = null;
             
             if("01".equals(dtf.format(month)))
              {
                  
                  MyQuery m = new MyQuery();
                  con = m.getConnection();

                  String query = "select * from workingdays_month WHERE Month LIKE '"+dt.format(month)+"'";
                  pst = con.prepareStatement(query);
                  rs = pst.executeQuery();

                  if(rs.next())
                    {

                    }
                  else
                    {
                       callworkingpange();
                    }    
  

               }
                  
          }
          catch (Exception e)
          {
             new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Someting Wrong .Try Again",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT); 
          }
      }
    
    public void callworkingpange()
        {
            try 
             {
              Thread th = new Thread()
                {
                    @Override
                    public void run()
                        {
                            try
                                {
                                            sleep(1000);
                                            JOptionPane.showMessageDialog(null,"Please Insert Working Days And Allowance Details for This Month");
                                            workingNallowance wa = new workingNallowance();
                                            wa.setVisible(true);
                                            
                                }

                            catch(Exception e)
                                {
                                     new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Someting Wrong .Try Again",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT); 
                                }
                        }
                };
              th.start();
             }
          catch (Exception e) 
           {
               new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Someting Wrong .Try Again",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT); 
           }

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
        absenttable.setShowGrid(true);
        showemployee.setSelectionForeground(Color.white);
        
        //oldersalaryshow decorate 
        
        JTableHeader showallabsentheader = showallabsent.getTableHeader();
        showallabsentheader.setForeground(Color.red);
        showallabsentheader.setFont(new Font("Times New Roman", Font.BOLD,16));
        ((DefaultTableCellRenderer)header.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);

        showallabsent.setRowHeight(50);
        showallabsent.setShowGrid(true);
        showallabsent.setSelectionForeground(Color.white);
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
                                    rs = st.executeQuery("SELECT ID,Name,Category,Nic_No FROM employee");
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
            String msg = "";
            
            
            MyQuery mq = new MyQuery();
            con = mq.getConnection();
            st = con.createStatement();
            
            rs = st.executeQuery( "select Image,Admin_MSG from employee where ID = "+id+"");
            while(rs.next())
              {               
                 img = rs.getBytes("Image");  
                 msg = rs.getString("Admin_MSG");  
              }
         
            ImageIcon image = new  ImageIcon(img);
            Image myimg = image.getImage().getScaledInstance(130,130, Image.SCALE_SMOOTH);
            ImageIcon newimg = new ImageIcon(myimg);
            mainimg.setIcon(newimg);
            
            message.setText(msg);
  
        }
      
       public void showallowance(int id) throws SQLException
        {
            
            buttonGroup.clearSelection();

            Connection con = null;
            Statement st;
            ResultSet rs;
            String  allowances_details = "";
            double allowances_pay = 0;
            double allowances_day = 0;
            
            
            MyQuery mq = new MyQuery();
            con = mq.getConnection();
            st = con.createStatement();
            
            rs = st.executeQuery( "select allowances_details from employee_salary where Employee_ID = "+id+"");
            while(rs.next())
              {               
                 allowances_details = rs.getString("allowances_details");  
              }
            
           
            
            if ("yes".equals(allowances_details)) 
              {
                 Appropriate.setSelected(true);

              }
            else if("no".equals(allowances_details))
              {
                 Inappropriate.setSelected(true);
              }
            
            LocalDate date = java.time.LocalDate.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
            
            rs = st.executeQuery( "select allowances_Pay,allowances_day from workingdays_month where Month = '"+dtf.format(date)+"'");
            while(rs.next())
              {               
                 allowances_pay  = rs.getDouble("allowances_Pay");  
                 allowances_day  = rs.getDouble("allowances_day");  
              }
     
            allowancelbl.setText("LIMIT ="+allowances_day+"    PAY = "+allowances_pay+"%");
            allowancepay.setValue((int) allowances_pay);
  
        }
      
      
      public void getallabsent()
        {
            try
             {
                Connection con = null;
                Statement st;
                ResultSet rs;
                int nod = 0;

                MyQuery mq = new MyQuery();
                con = mq.getConnection();
                st = con.createStatement();
                
                LocalDate date = java.time.LocalDate.now();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
                
                absentlabel.setText("EMPLOYEE'S ABSENT DATES OF "+dtf.format(date));
                rs = st.executeQuery( "select ID,Name from employee");
                
                 
                while(rs.next())
                  {               
                          int id = rs.getInt("ID");
                          String Name = rs.getString("Name");
                          nod =getabsentday(id);
                          
                          DefaultTableModel  allabsent =  (DefaultTableModel)showallabsent.getModel();
                          allabsent.addRow(new Object[] {id,Name,nod});
                  }
              } 
            catch (Exception e) 
             {
                new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
             }

        }
      
      public int getabsentday(int id)
        {
             Connection con = null;
             Statement st;
             ResultSet rs;
             int nod = 0;
            
            try
             {
                  MyQuery mq = new MyQuery();
                  con = mq.getConnection();
                  st = con.createStatement();

                  LocalDate date = java.time.LocalDate.now();
                  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");

                  rs = st.executeQuery( "select COUNT(Employee_ID) from employee_absent_day WHERE Employee_ID = "+id+" AND Date LIKE '"+dtf.format(date)+"%'");
                  while(rs.next())
                    {               
                            nod = rs.getInt("COUNT(Employee_ID)");
                    }
             }

            catch (Exception e) 
             {
                 new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
             }

              
          
         return nod;
        }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
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
        absentlabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
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
        jLabel7 = new javax.swing.JLabel();
        timeshow = new javax.swing.JLabel();
        detailshowpanel = new javax.swing.JPanel();
        showemployeename = new javax.swing.JLabel();
        imageshow = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        showemployee = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        submitbtn = new javax.swing.JButton();
        absentbtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        absenttable = new javax.swing.JTable();
        removebtn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        reason = new javax.swing.JEditorPane();
        mainimg = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        clearreasonbtn = new javax.swing.JButton();
        showid = new javax.swing.JLabel();
        search = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        showallabsent = new javax.swing.JTable();
        absentlabel = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        message = new javax.swing.JTextArea();
        absentlabel1 = new javax.swing.JLabel();
        sendmsgbtn = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        absentlabel2 = new javax.swing.JLabel();
        Appropriate = new javax.swing.JRadioButton();
        Inappropriate = new javax.swing.JRadioButton();
        allowance = new javax.swing.JButton();
        allowancepay = new javax.swing.JProgressBar();
        jButton7 = new javax.swing.JButton();
        allowancelbl = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        absentlabel3 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        ot = new javax.swing.JTextField();
        emid = new javax.swing.JTextField();
        totalallowance = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        absentall = new javax.swing.JTextField();
        salarybtn = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        totalsalary = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();

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
        jLabel1.setText("BASIC ACTIVITIES MANAGMENT UNIT");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 0, 0)));

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        jLabel36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel36MouseClicked(evt);
            }
        });

        absentlabel4.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        absentlabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        absentlabel4.setText("TABLES");
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
                .addGap(10, 10, 10)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 734, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(absentlabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
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
                    .addComponent(absentlabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel14.setBackground(new java.awt.Color(255, 0, 0));
        jPanel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel14MouseClicked(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/basicmanagment.png"))); // NOI18N
        jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

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
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel26)
                .addContainerGap())
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
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel29)
                .addContainerGap())
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
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel32)
                .addContainerGap())
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
                    .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel35)
                .addContainerGap())
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
                .addGap(21, 21, 21)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logo2.png"))); // NOI18N

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
        detailshowpanel.add(imageshow, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 62, 60));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("MARK TODAY EMPLOYEE'S ABSENT ");
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

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
                "ID", "Name", "Service", "Nic_No"
            }
        ));
        showemployee.setSelectionBackground(new java.awt.Color(255, 0, 0));
        showemployee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showemployeeMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(showemployee);

        jLabel3.setFont(new java.awt.Font("Calibri Light", 1, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("SEARCH HERE");

        submitbtn.setBackground(new java.awt.Color(0, 0, 0));
        submitbtn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        submitbtn.setForeground(new java.awt.Color(255, 255, 255));
        submitbtn.setText("SUBMIT");
        submitbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitbtnActionPerformed(evt);
            }
        });

        absentbtn.setBackground(new java.awt.Color(0, 0, 0));
        absentbtn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        absentbtn.setForeground(new java.awt.Color(255, 255, 255));
        absentbtn.setText("ABSENT");
        absentbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                absentbtnActionPerformed(evt);
            }
        });

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
                "ID", "Name", "Service", "Nic_No", "Reason"
            }
        ));
        absenttable.setGridColor(new java.awt.Color(255, 255, 255));
        absenttable.setSelectionBackground(new java.awt.Color(255, 0, 0));
        absenttable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                absenttableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(absenttable);

        removebtn.setBackground(new java.awt.Color(0, 0, 0));
        removebtn.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        removebtn.setForeground(new java.awt.Color(255, 255, 255));
        removebtn.setText("REMOVE");
        removebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removebtnActionPerformed(evt);
            }
        });

        reason.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        reason.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        reason.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reasonMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(reason);

        mainimg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainimg.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel11.setText("REASON :");

        clearreasonbtn.setBackground(new java.awt.Color(0, 0, 0));
        clearreasonbtn.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        clearreasonbtn.setForeground(new java.awt.Color(255, 255, 255));
        clearreasonbtn.setText("Clear");
        clearreasonbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearreasonbtnActionPerformed(evt);
            }
        });

        showid.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        showid.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        search.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        search.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        search.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 0, 0)));
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

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("ALL EMPLOYEES WORKING");
        jLabel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(removebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                            .addComponent(absentbtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(search)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(mainimg, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                    .addComponent(showid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(30, 30, 30))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(clearreasonbtn)
                                .addGap(10, 10, 10))))
                    .addComponent(submitbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(showid, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(absentbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(mainimg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(15, 15, 15)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clearreasonbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(removebtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(submitbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        showallabsent = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex,int colIndex)
            {
                return false;
            }
        };
        showallabsent.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        showallabsent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "No of Days"
            }
        ));
        showallabsent.setSelectionBackground(new java.awt.Color(255, 0, 0));
        showallabsent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showallabsentMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(showallabsent);

        absentlabel.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        absentlabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        absentlabel.setText("EMPLOYEE'S ABSENT DATES ");
        absentlabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                    .addComponent(absentlabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(absentlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        message.setColumns(20);
        message.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        message.setRows(5);
        message.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jScrollPane5.setViewportView(message);

        absentlabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        absentlabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        absentlabel1.setText("SEND MESSAGE TO EMPLOYEE");
        absentlabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        sendmsgbtn.setBackground(new java.awt.Color(0, 0, 0));
        sendmsgbtn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        sendmsgbtn.setForeground(new java.awt.Color(255, 255, 255));
        sendmsgbtn.setText("SEND");
        sendmsgbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendmsgbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(sendmsgbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(absentlabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(absentlabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(sendmsgbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jScrollPane5)
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        absentlabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        absentlabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        absentlabel2.setText("WORKING DAYS & ALLOWANCES ");
        absentlabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        buttonGroup.add(Appropriate);
        Appropriate.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Appropriate.setText("Appropriate");
        Appropriate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AppropriateActionPerformed(evt);
            }
        });

        buttonGroup.add(Inappropriate);
        Inappropriate.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Inappropriate.setText(" Inappropriate");

        allowance.setBackground(new java.awt.Color(0, 0, 0));
        allowance.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        allowance.setForeground(new java.awt.Color(255, 255, 255));
        allowance.setText("SUBMIT");
        allowance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allowanceActionPerformed(evt);
            }
        });

        allowancepay.setBackground(new java.awt.Color(255, 255, 255));
        allowancepay.setForeground(new java.awt.Color(255, 0, 0));
        allowancepay.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jButton7.setBackground(new java.awt.Color(0, 0, 0));
        jButton7.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("GO TO THE PAGE");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        allowancelbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        allowancelbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        allowancelbl.setText("LIMIT = 0                  PAY = 0% ");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(allowancepay, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(allowancelbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                .addComponent(Appropriate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Inappropriate))
                            .addComponent(absentlabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                            .addComponent(allowance, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(absentlabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Appropriate)
                    .addComponent(Inappropriate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(allowancelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(allowancepay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(allowance, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        absentlabel3.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        absentlabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        absentlabel3.setText("SALARY ISSUE");
        absentlabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Employee ID");

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("No of Ot Hours Worked");

        ot.setEditable(false);
        ot.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        ot.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        emid.setEditable(false);
        emid.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        emid.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        emid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        totalallowance.setEditable(false);
        totalallowance.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        totalallowance.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        totalallowance.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel22.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Total Allowance");

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Absent Dates ");

        absentall.setEditable(false);
        absentall.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        absentall.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        absentall.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        salarybtn.setBackground(new java.awt.Color(0, 0, 0));
        salarybtn.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        salarybtn.setForeground(new java.awt.Color(255, 255, 255));
        salarybtn.setText("PAY");
        salarybtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salarybtnActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Total Salary");

        totalsalary.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        totalsalary.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        totalsalary.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(absentlabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(salarybtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(emid, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(totalallowance, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(totalsalary)
                            .addComponent(absentall)
                            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ot))))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(absentlabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ot, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emid, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(absentall, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalallowance, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalsalary, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(salarybtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jPanel10.setBackground(new java.awt.Color(255, 0, 0));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel10MouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("OUTDOOR");
        jLabel14.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 0, 0, new java.awt.Color(255, 255, 255)));

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outdoorcam.png"))); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel11.setBackground(new java.awt.Color(0, 0, 255));
        jPanel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel11MouseClicked(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("SHOP");
        jLabel15.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 0, 0, new java.awt.Color(255, 255, 255)));

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/shop.png"))); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel13.setBackground(new java.awt.Color(0, 153, 102));
        jPanel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel13MouseClicked(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("KITCHEN");
        jLabel16.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 0, 0, new java.awt.Color(255, 255, 255)));

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/kitchen_1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(278, 278, 278)
                        .addComponent(timeshow, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(detailshowpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap())
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(timeshow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(detailshowpanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void showemployeeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showemployeeMouseClicked

        try
        {

            int row = showemployee.getSelectedRow();
            int id = Integer.parseInt(showemployee.getModel().getValueAt(row, 0 ).toString());
            showid.setText(Integer.toString(id));
            Double othours = null;
            int countabsentday = 0;
            double finalsalary = 0;
            
            showimg(id);
            showallowance(id);
            
            try 
              {
                    Connection con = null;
                    Statement st;
                    ResultSet rs;

                    MyQuery mq = new MyQuery();
                    con = mq.getConnection();
                    st = con.createStatement();

                    LocalDate date = java.time.LocalDate.now();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");

                    emid.setText(Integer.toString(id));

                    rs = st.executeQuery( "select SUM(OT_Hours) from ot_hours where Employee_ID = "+id+" AND Date LIKE '"+dtf.format(date)+"%' ");
                       while(rs.next())
                            {      
                                    othours = rs.getDouble("SUM(OT_Hours)");
                            }
                       
                    ot.setText(Double.toString(othours));

                    employeeUnit e = new employeeUnit();
                    double allowanceall = e.getsalary(dtf.format(date), id);

                    totalallowance.setText(Double.toString(allowanceall));

                    rs = st.executeQuery( "select COUNT(Employee_ID) from employee_absent_day where Employee_ID = '"+id+"' AND Date LIKE '"+dtf.format(date)+"%'");
                    while(rs.next())
                             {
                                countabsentday = rs.getInt("COUNT(Employee_ID)");
                             }
                    absentall.setText(Integer.toString(countabsentday));

                    rs = st.executeQuery( "select total_Salary from employee_salary where Employee_ID = '"+id+"'");
                    while(rs.next())
                             {
                                finalsalary = rs.getDouble("total_Salary");
                             }
                    totalsalary.setText(Double.toString(finalsalary));
              }
            
            catch (Exception e) 
              {
                   //JOptionPane.showMessageDialog(null, e );
                    new NotifyWindow(NotifyType.ERROR_NOTIFICATION, "Still not Process "+id+" 's Salary",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
              }

            
        }
        catch (Exception e)
        {
           new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
        }

    }//GEN-LAST:event_showemployeeMouseClicked

    private void absenttableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_absenttableMouseClicked
        try 
         {
            int row = absenttable.getSelectedRow();
            String id = absenttable.getModel().getValueAt(row, 0 ).toString();
            showid.setText((id));
            showimg(Integer.parseInt(id));
         } 
        catch (Exception e) 
         {
              new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Someting Wrong .Try Again",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT); 
         }
        
    }//GEN-LAST:event_absenttableMouseClicked

    private void absentbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_absentbtnActionPerformed
       
        try 
         {
           int row = showemployee.getSelectedRow();
           
          
           int j =1;
           
           if (row >= 0) 
              {
                  int rowcount = absenttable.getRowCount();
                  String name = showemployee.getModel().getValueAt(row, 1 ).toString();
                  String service = showemployee.getModel().getValueAt(row, 2 ).toString();
                  String nic = showemployee.getModel().getValueAt(row, 3 ).toString();
                  String getreason = reason.getText();
                  String id = showemployee.getModel().getValueAt(row, 0 ).toString();
                  LocalDate date = java.time.LocalDate.now();
                  
                  String query = "select * from employee_absent_day where Employee_ID=? and Date=? ";
                  Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/the_haritage_cafe", "root", "");
                  PreparedStatement pst = con.prepareStatement(query);
                  pst.setInt(1, Integer.parseInt(id));
                  pst.setString(2, date.toString());
                  ResultSet rs = pst.executeQuery();
                
                  if(rs.next())
                    {
                        //JOptionPane.showMessageDialog(this,"Already You Submit Absent of This Employee");
                        new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Already You Submit Absent of This Employee",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                        reason.setText("");
                        
                    }
                else
                    {
                       for (int i = 0; i < rowcount; i++) 
                        {

                          if (id.equals(absenttable.getModel().getValueAt(i, 0 ).toString())) 
                            {
                              j = 0;
                              addabsent(j,id,name,service,nic,getreason);
                              reason.setText("");
                            }   
                        } 
                  
                       addabsent(j,id,name,service,nic,getreason);
                       reason.setText("");
                    }                
              }
           else
              {
                //JOptionPane.showMessageDialog(null, "Please Select Employee");
                new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Select Employee",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
              } 
         } 
        catch (Exception e) 
         {
              new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
         }

    }//GEN-LAST:event_absentbtnActionPerformed

    private void removebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removebtnActionPerformed
        
        int row = absenttable.getSelectedRow();
        if (row >= 0) 
              {
                  DefaultTableModel  absent =  (DefaultTableModel)absenttable.getModel();
                  absent.removeRow(row);
                  mainimg.setIcon(null);
                  showid.setText("");
              }
        else
              {
                //JOptionPane.showMessageDialog(null, "Please Select Employee You Want to Delete");
                new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Select Employee You Want to Delete",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
              } 
              
        
    }//GEN-LAST:event_removebtnActionPerformed

    private void clearreasonbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearreasonbtnActionPerformed
        
        reason.setText("");
    }//GEN-LAST:event_clearreasonbtnActionPerformed

    private void reasonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reasonMouseClicked
       
        try
          {
            Runtime.getRuntime().exec("cmd /c osk");
            reason.grabFocus();
          }
        catch (IOException ex)
          {
             //JOptionPane.showMessageDialog(null, "Fail to get Touch Key Board ");
             new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Fail to get Touch Key Board ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
          }
    }//GEN-LAST:event_reasonMouseClicked

    private void submitbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitbtnActionPerformed
       
        int rowcount = absenttable.getRowCount();
        if (rowcount != 0) 
         {
            int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
            if (confirm == 0)
             {
                for (int i = 0; i < rowcount; i++) 
                       {  

                           try
                            {


                              int id = Integer.parseInt(absenttable.getModel().getValueAt(i, 0).toString());
                              String reason = absenttable.getModel().getValueAt(i, 4).toString();

                              LocalDate date = java.time.LocalDate.now();
                              DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                              Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/the_haritage_cafe", "root", "");

                               //insert to table

                              PreparedStatement  ps = con.prepareStatement("INSERT INTO employee_absent_day VALUES (?,?,?,?)");
                              ps.setInt(1, 0);
                              ps.setInt(2, id);
                              ps.setString(3, dtf.format(date));
                              ps.setString(4, reason);
                              ps.executeUpdate();



                             }

                          catch(Exception e)
                             {
                                   new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT); 
                             }   
                       } 
                 //JOptionPane.showMessageDialog(null, "Successfully submited");
                 new NotifyWindow(NotifyType.SUCCESS_NOTIFICATION,"Successfully submited",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                 
                 showallabsent.setModel(new DefaultTableModel(null,new String[] {"ID","Name","No of Days"}));
                 getallabsent();
             }
         }
        else
         {
            //JOptionPane.showMessageDialog(null, "The Table is Empty");
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"The Table is Empty",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
         }
        
        
    }//GEN-LAST:event_submitbtnActionPerformed

    private void searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyReleased
        
        try
         {

            MyQuery m = new MyQuery();
            Connection con = m.getConnection();
            Statement st; 
            ResultSet rs;
                                    
            String searchdetails = search.getText();
            st = con.createStatement();
            rs = st.executeQuery("SELECT ID,Name,Category,Nic_No FROM employee WHERE Name LIKE '%"+searchdetails+"%'");
            showemployee.setModel(DbUtils.resultSetToTableModel(rs));

                                        
         }
        catch(Exception e)
         {
           new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
         }
    }//GEN-LAST:event_searchKeyReleased

    private void sendmsgbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendmsgbtnActionPerformed
       
        if (!"".equals(message.getText())) 
          {
                try
                  {
                    int row = showemployee.getSelectedRow();

                    if (row >= 0) 
                       {
                             int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
                             if (confirm == 0)
                              { 
                                   int id = Integer.parseInt(showemployee.getModel().getValueAt(row, 0).toString());
                                  
                                   MyQuery m = new MyQuery();
                                   Connection con = m.getConnection();
                                   
                                   PreparedStatement ps = con.prepareStatement("UPDATE employee SET Admin_MSG =? WHERE ID = "+id+"");
                                   ps.setString(1, message.getText());
                                   ps.executeUpdate();
                                   //JOptionPane.showMessageDialog(null, "Send Succesfullly ");
                                   new NotifyWindow(NotifyType.SUCCESS_NOTIFICATION,"Send Succesfullly",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);

                              }
                       }

                    else 
                       {
                          //JOptionPane.showMessageDialog(null, "Please Select the Employee from employee table You Want to Send the Message");
                          new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Select the Employee from employee table You Want to Send the Message",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                       }
                   }
                 catch(Exception e)
                  {
                     new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
                  }
             } 
           
        else
           {
               //JOptionPane.showMessageDialog(null, "Your Message Is Empty");
               new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Your Message Is Empty",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
            }
    }//GEN-LAST:event_sendmsgbtnActionPerformed

    private void showallabsentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showallabsentMouseClicked
        
        int row = showallabsent.getSelectedRow();
        String id = showemployee.getModel().getValueAt(row, 0 ).toString();
        try 
        {
            showimg(Integer.parseInt(id));
        } 
        catch (SQLException ex) 
        {
             new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Someting Wrong .Try Again",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT); 
        }
    }//GEN-LAST:event_showallabsentMouseClicked

    private void allowanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allowanceActionPerformed
        
        // set value to radio
        
        Inappropriate.setActionCommand("no");
        Appropriate.setActionCommand("yes");
        
        // get value from radio
        
        String getradio = buttonGroup.getSelection().getActionCommand();
        
        try
            {
                int row = showemployee.getSelectedRow();

                if (row >= 0) 
                       {
                          int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
                          if (confirm == 0)
                           { 
                                int id = Integer.parseInt(showemployee.getModel().getValueAt(row, 0).toString());
                                  
                                MyQuery m = new MyQuery();
                                Connection con = m.getConnection();
                                PreparedStatement pst = null;
                                ResultSet rs = null;
                                
                                String query = "select * from employee_salary where Employee_ID = ? ";
                                pst = con.prepareStatement(query);
                                pst.setInt(1, id);
                                rs = pst.executeQuery();
                                
                                if(rs.next())
                                    {
                                        
                                         pst = con.prepareStatement("UPDATE employee_salary SET allowances_details =? WHERE Employee_ID = "+id+"");
                                         pst.setString(1, getradio);
                                         pst.executeUpdate();
                                         //JOptionPane.showMessageDialog(null, "Update Succesfullly ");
                                         new NotifyWindow(NotifyType.SUCCESS_NOTIFICATION,"Update Succesfullly ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                                    }
                                else
                                    {
                                        //JOptionPane.showMessageDialog(this,"You Still not Add ID = "+id+" Salary.Please Add Salary for this employee From Salary Management Page");
                                        new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"You Still not Add ID = "+id+" Salary",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                                    } 
                                     
                           }
                       }

                    else 
                       {
                          //JOptionPane.showMessageDialog(null, "Please Select the Employee You Want Change Allowance");
                          new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Select the Employee You Want Change Allowance",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                       }
                   }
                 catch(Exception e)
                  {
                     new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
                  }
        
    }//GEN-LAST:event_allowanceActionPerformed

    private void AppropriateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AppropriateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AppropriateActionPerformed

    private void salarybtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salarybtnActionPerformed
        
        int row = showemployee.getSelectedRow();
        if(!"".equals(emid.getText()) && !"".equals(ot.getText()) && !"".equals(totalallowance.getText()) && !"".equals(absentall .getText()) && !"".equals(totalsalary.getText()))
          {
            if (row >= 0) 
                {
                    int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
                    if (confirm == 0)
                      { 
                            int id = Integer.parseInt(showemployee.getModel().getValueAt(row, 0).toString());
                            paysheet p = new  paysheet();
                            p.createpaysheet(id);
                      }
                }
            else
                {
                     //JOptionPane.showMessageDialog(null, "Some Text Filed Is Empty");
                     new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Some Text Filed Is Empty",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                }
          }
        else
          {
             //JOptionPane.showMessageDialog(null, "Please Make Sure You Processed this Employee Salary ");
             new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Make Sure You Processed this Employee Salary ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
          }
        
    }//GEN-LAST:event_salarybtnActionPerformed

    private void jPanel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel15MouseClicked
        
        managmentemployee m = new managmentemployee();
        m.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel15MouseClicked

    private void jPanel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseClicked
        
        managmentbasic m = new managmentbasic();
        m.setVisible(true);
        this.dispose();
        
    }//GEN-LAST:event_jPanel14MouseClicked

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

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        
        workingNallowance w = new workingNallowance();
        w.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jPanel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseClicked
        
        dispose();
        
        main m = new main();
        m.setVisible(true);
    }//GEN-LAST:event_jPanel12MouseClicked

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
        
         //note : this get from gihub.com--------------
        
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
    }//GEN-LAST:event_jPanel10MouseClicked

    private void jPanel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseClicked
        
         //note : this get from gihub.com-------------------
        
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
    }//GEN-LAST:event_jPanel11MouseClicked

    private void jPanel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseClicked
       
         //note : this get from gihub.com------------------------
        
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
    }//GEN-LAST:event_jPanel13MouseClicked

    private void jLabel36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel36MouseClicked

        try
        {
            this.dispose();

            managmentbasic b = new managmentbasic();
            b.setVisible(true);
        }
        catch (Exception ex)
        {
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail..!",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
        }
    }//GEN-LAST:event_jLabel36MouseClicked

    private void absentlabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_absentlabel4MouseClicked
       
        tables t = new tables();
        t.setVisible(true);
    }//GEN-LAST:event_absentlabel4MouseClicked

    public void addabsent(int j,String id,String a,String b,String c,String d)
      {
        int check = j;
        String name = a;
        String service = b;
        String nic = c;
        String getreason = d;
        int row = absenttable.getRowCount();
        

        if (check != 0) 
                {
                        name = showemployee.getModel().getValueAt(row, 1 ).toString();
                        service = showemployee.getModel().getValueAt(row, 2 ).toString();
                        nic = showemployee.getModel().getValueAt(row, 3 ).toString();
                        getreason = reason.getText();

                        DefaultTableModel  absent =  (DefaultTableModel)absenttable.getModel();
                        absent.addRow(new Object[] {id,name,service,nic,getreason});
                        mainimg.setIcon(null);
                        showid.setText("");
                        showemployee();
                } 
            else 
                {
                       //JOptionPane.showMessageDialog(null, "Already Added to Absent Table"); 
                       new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Already Added to Absent Table",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                }

      }
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new managmentbasic().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Appropriate;
    private javax.swing.JRadioButton Inappropriate;
    private javax.swing.JTextField absentall;
    private javax.swing.JButton absentbtn;
    private javax.swing.JLabel absentlabel;
    private javax.swing.JLabel absentlabel1;
    private javax.swing.JLabel absentlabel2;
    private javax.swing.JLabel absentlabel3;
    private javax.swing.JLabel absentlabel4;
    private javax.swing.JTable absenttable;
    private javax.swing.JButton allowance;
    private javax.swing.JLabel allowancelbl;
    private javax.swing.JProgressBar allowancepay;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JButton clearreasonbtn;
    private javax.swing.JPanel detailshowpanel;
    private javax.swing.JTextField emid;
    private javax.swing.JLabel imageshow;
    private javax.swing.JButton jButton7;
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
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
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
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel mainimg;
    private javax.swing.JTextArea message;
    private javax.swing.JTextField ot;
    private javax.swing.JEditorPane reason;
    private javax.swing.JButton removebtn;
    private javax.swing.JButton salarybtn;
    private javax.swing.JTextField search;
    private javax.swing.JButton sendmsgbtn;
    private javax.swing.JTable showallabsent;
    private javax.swing.JTable showemployee;
    private javax.swing.JLabel showemployeename;
    private javax.swing.JLabel showid;
    private javax.swing.JButton submitbtn;
    private javax.swing.JLabel timeshow;
    private javax.swing.JTextField totalallowance;
    private javax.swing.JTextField totalsalary;
    // End of variables declaration//GEN-END:variables
}
