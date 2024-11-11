
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import net.proteanit.sql.DbUtils;

public class employeeUnit extends javax.swing.JFrame {


    public employeeUnit()
    {
        try 
          {
            initComponents();
            
            Connection con = null;
            Statement st = null;
            ResultSet rs = null;
            int id = 0;
            
            MyQuery mq = new MyQuery();
            con = mq.getConnection();
            st = con.createStatement();
            
            clock();
            detailsbox();
            details();
            
            loginpage lp = new loginpage();
            String[] details = lp.getdetails();           
            String uname = details[0];
            String pwd = details[1];
            
            rs = st.executeQuery( "select ID from employee where User_Name='"+uname+"' and Password='"+pwd+"'");
                 while(rs.next())
                  {
                      id = rs.getInt("ID");
                  }
            
            LocalDate date = java.time.LocalDate.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
            
            getsalary(dtf.format(date),id);
            getabsent(dtf.format(date));
            getoldersalary(id);
            decorate();
            check();
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
    
    public void check()
      {
          try 
          {
                Connection con = null;
                PreparedStatement pst = null;
                ResultSet rs = null; 
                Statement st = null;
                int id = 0;
                MyQuery mq = new MyQuery();
                con = mq.getConnection();
                
                loginpage lp = new loginpage();
                String[] details = lp.getdetails();           
                String uname = details[0];
                String pwd = details[1];
                
                st = con.createStatement();
                rs = st.executeQuery( "select ID from employee where User_Name='"+uname+"' and Password='"+pwd+"'");
                 while(rs.next())
                  {
                      id = rs.getInt("ID");
                  }
                
                String query = "select * from employee where id = ? AND Category = ? ";
                pst = con.prepareStatement(query);
                pst.setInt(1, id);
                pst.setString(2, "Admin");
                rs = pst.executeQuery();
                
                if(rs.next())
                    {
                       toplabel.setText("");
                    }
                
          } 
          catch (Exception e) 
          {
              new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",
                      NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
          }
         
      }
    
    public void decorate()
      {
        //absenttable decorate 
        
        JTableHeader header = absenttable.getTableHeader();
        header.setForeground(Color.red);
        header.setFont(new Font("Times New Roman", Font.BOLD,20));
        ((DefaultTableCellRenderer)header.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);

        absenttable.setRowHeight(50);
        absenttable.setShowGrid(false);
        
        
        //oldersalaryshow decorate 
        
        JTableHeader salaryheader = oldersalaryshow.getTableHeader();
        salaryheader.setForeground(Color.red);
        salaryheader.setFont(new Font("Times New Roman", Font.BOLD,20));
        ((DefaultTableCellRenderer)header.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);

        oldersalaryshow.setRowHeight(50);
        oldersalaryshow.setShowGrid(false);

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

    
    public void details()
     {
        try 
             {
                Connection con = null;
                Statement st;
                ResultSet rs;

                loginpage lp = new loginpage();
                String[] details = lp.getdetails();           
                String uname = details[0];
                String pwd = details[1];

                String namev = null;
                int idv = 0;
                String shortnamev = null;
                int agev = 0;
                String nicv = null;
                String addressv = null;
                byte[] img = null;
                String category = "";
                Date date = null;

                MyQuery mq = new MyQuery();
                con = mq.getConnection();
                st = con.createStatement();

                rs = st.executeQuery( "select * from employee where User_Name='"+uname+"' and Password='"+pwd+"'");
                while(rs.next())
                  {
                     namev = rs.getString("Name"); 
                     idv = rs.getInt("ID"); 
                     shortnamev = rs.getString("Short_Name"); 
                     agev = rs.getInt("Age");
                     nicv = rs.getString("Nic_No");
                     addressv = rs.getString("Address");
                     category = rs.getString("Category");
                     date = rs.getDate("Join_Day");
                     img = rs.getBytes("Image");
                  }

                name.setText(namev);
                regno.setText(Integer.toString(idv));
                identy.setText(nicv);
                address.setText(addressv);
                age.setText(Integer.toString(agev));
                shortname.setText(shortnamev);
                categoryshow.setText(category);
                join.setText(date.toString());

                ImageIcon image = new  ImageIcon(img);
                Image myimg = image.getImage().getScaledInstance(150,150, Image.SCALE_SMOOTH);
                ImageIcon newimg = new ImageIcon(myimg);
                imgshow.setIcon(newimg);

             }
            catch(Exception e)
             {
                 new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
             }
    
     }
    
    public void getoldersalary(int id)
       {
           try 
            {
                Connection con = null;
                Statement st;
                ResultSet rs;
                double basic = 0;

                MyQuery mq = new MyQuery();
                con = mq.getConnection();
                st = con.createStatement();
                
                rs = st.executeQuery( "select Basic_Salary,allowances_details from employee_salary where Employee_ID = "+id+"");
                while(rs.next())
                  {
                      basic = rs.getDouble("Basic_Salary");
                      basicsalaryshow.setText(Double.toString(basic));
                      if ("yes".equals(rs.getString("allowances_details"))) 
                       {
                          allowancedetails.setText("Appropriate");
                       }
                      else if ("no".equals(rs.getString("allowances_details"))) 
                       {
                          allowancedetails.setText("Inappropriate");
                       } 
                  }
                 
                rs = st.executeQuery( "select Date,Total_Salary,By_whom from salary_issue where Employee_ID = "+id+"");
                oldersalaryshow.setModel(DbUtils.resultSetToTableModel(rs));
            }
           
           catch (Exception e) 
            {
              new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
            }
           
       
       }
    
     public double getsalary(String month,int id)
       {
           double allallownce = 0;
           
           monthshow.setText(month);
           
           try 
             {
                LocalDate date = java.time.LocalDate.now();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                Date getdate = new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(date));
                datechooserattendance.setDate(getdate);
                
                Connection con = null;
                Statement st;
                ResultSet rs;
                double basicsalary = 0;
                double othours = 0;
                double payot = 0;
                int absentday = 0;
                int workingdays = 0;
                double payofday  = 0;
                double calsalary = 0;
                int getallowanceday = 0;
                double allowancepay = 0;
                int countabsentday = 0;
                String allowancedetail = null;

                MyQuery mq = new MyQuery();
                con = mq.getConnection();
                st = con.createStatement();
                 
                 rs = st.executeQuery( "select pay_for_OT from employee_salary where Employee_ID = "+id+"");
                 while(rs.next())
                  {
                      payot = rs.getDouble("pay_for_OT");
                  }
                 
                 rs = st.executeQuery( "select COUNT(Date) from employee_absent_day where Employee_ID = "+id+" AND Date LIKE '"+month+"%' ");
                 while(rs.next())
                  {
                      absentday = rs.getInt("COUNT(Date)");
                  }

                rs = st.executeQuery( "select Basic_Salary from employee_salary where Employee_ID = "+id+"");
                 while(rs.next())
                  {
                      basicsalary = rs.getDouble("Basic_Salary");
                  }
                 rs = st.executeQuery( "select SUM(OT_Hours) from ot_hours where Employee_ID = "+id+" AND Date LIKE '"+month+"%' ");
                 while(rs.next())
                  {
                      othours = rs.getDouble("SUM(OT_Hours)");
                  }
                 
                 double calotsalary = (othours*payot);
                 
                 rs = st.executeQuery( "select noOf_Days from workingdays_month where Month = '"+month+"'");
                 while(rs.next())
                  {
                      workingdays = rs.getInt("noOf_Days");
                      
                      if (rs.getInt("noOf_Days") == 0) 
                       { 
                            calsalary = 0;
                            salary.setText("0");
                       }
                      else
                       {
                           rs = st.executeQuery( "select allowances_details from employee_salary where Employee_ID = '"+id+"'");
                           while(rs.next())
                            { 
                                allowancedetail = rs.getString("allowances_details");
                                
                                if ("yes".equals(allowancedetail))
                                  {

                                        rs = st.executeQuery( "select allowances_day,allowances_Pay from workingdays_month WHERE Month = '"+month+"'");
                                        while(rs.next())
                                           {
                                               getallowanceday = rs.getInt("allowances_day");
                                               allowancepay = rs.getDouble("allowances_Pay");
                                           }

                                        rs = st.executeQuery( "select COUNT(Employee_ID) from employee_absent_day where Employee_ID = '"+id+"' AND Date LIKE '"+month+"%'");
                                        while(rs.next())
                                           {
                                               countabsentday = rs.getInt("COUNT(Employee_ID)");
                                           }

                                        if (workingdays - getallowanceday >= countabsentday) 
                                         {
                                               payofday = (basicsalary/workingdays);
                                               calsalary = basicsalary - (payofday * absentday) + calotsalary + (basicsalary * allowancepay/100);
                                               allallownce = basicsalary * allowancepay/100;
                                         }
                                        else
                                         {
                                               payofday = (basicsalary/workingdays);
                                               calsalary = basicsalary - (payofday * absentday) + calotsalary ;
                                               allallownce = 0;
                                         }


                                       salary.setText(new DecimalFormat("0.00").format(calsalary));   
                                  }                                
                                else if("no".equals(allowancedetail))
                                  {

                                        payofday = (basicsalary/workingdays);
                                        calsalary = basicsalary - (payofday * absentday) + calotsalary ;
                                        
                                        salary.setText(new DecimalFormat("0.00").format(calsalary));                                     
                                  }
                            }      
                       }                   
                  }

                 absentdays.setText(Integer.toString(absentday));
                 ot.setText(Double.toString(othours));
                    
                 PreparedStatement ps = con.prepareStatement("UPDATE employee_salary SET total_Salary = "+new DecimalFormat("0.00").format(calsalary)+" WHERE Employee_ID = "+id+"");
                 ps.executeUpdate();
 

             } 
           
           catch (Exception e) 
             {
                 new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
             }
           
        allowanceshow.setText(Double.toString(allallownce));
         
        return  allallownce;   
       }
     
     public void getabsent(String getdate)
       {
           try 
            {
               absenttable.setModel(new DefaultTableModel(null,new String[] {"Date","Reason"}));
               
               topicattendance.setText("Absent Details  of "+getdate+" Month");
               LocalDate day = java.time.LocalDate.now();
               DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
               Date getday = new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(day));
               
               MyQuery m = new MyQuery();
               Connection con = m.getConnection();
               Statement st;
               ResultSet rs;
               int id = 0;
               Date date = null;
               String reason = "";
               
               loginpage lp = new loginpage();
               String[] details = lp.getdetails();           
               String uname = details[0];
               String pwd = details[1];
                
               MyQuery mq = new MyQuery();
               con = mq.getConnection();
               st = con.createStatement();
                
                
               rs = st.executeQuery( "select ID from employee where User_Name='"+uname+"' and Password='"+pwd+"'");
               while(rs.next())
                  {
                      id = rs.getInt("ID");
                  }

               rs = st.executeQuery("SELECT Date,Reason FROM employee_absent_day WHERE Employee_ID = "+id+" AND Date LIKE '"+getdate+"%' ORDER BY Date ASC");
               while(rs.next())
                  {
                      date = rs.getDate("Date");
                      reason = rs.getString("Reason");
         
                      DefaultTableModel  absent =  (DefaultTableModel)absenttable.getModel();
                      absent.addRow(new Object[] {date,reason});
                  }
            } 
           catch (Exception e)
            {
                new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
            }
           
       }
     
       
    @SuppressWarnings("unchecked")
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
        toplabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        imgshow = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        regno = new javax.swing.JTextField();
        identy = new javax.swing.JTextField();
        address = new javax.swing.JTextField();
        age = new javax.swing.JTextField();
        shortname = new javax.swing.JTextField();
        join = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        categoryshow = new javax.swing.JTextField();
        detailshowpanel = new javax.swing.JPanel();
        showemployeename = new javax.swing.JLabel();
        imageshow = new javax.swing.JLabel();
        timeshow = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        ot = new javax.swing.JLabel();
        salary = new javax.swing.JLabel();
        absentdays = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        allowanceshow = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        topicattendance = new javax.swing.JLabel();
        datechooserattendance = new com.toedter.calendar.JDateChooser();
        search = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        absenttable = new javax.swing.JTable();
        monthshow = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        basicsalaryshow = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        allowancedetails = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        oldersalaryshow = new javax.swing.JTable();
        topicselling1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 0, 3, new java.awt.Color(0, 0, 0)));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/employee.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tempus Sans ITC", 3, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setText("EMPLOYEE  UNIT");

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

        toplabel.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        toplabel.setForeground(new java.awt.Color(255, 0, 0));
        toplabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        toplabel.setText("If You Have Any Question of this Details Please Contact a Admin");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(toplabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addGap(41, 41, 41))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(toplabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Stencil", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("YOUR DETAILS");

        jPanel3.setBackground(new java.awt.Color(255, 0, 0));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));

        imgshow.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("NIC NUMBER");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("NAME");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("REGISTER NUMBER");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("ADDRESS");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("AGE");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("NICK NAME");

        name.setEditable(false);
        name.setBackground(new java.awt.Color(255, 0, 0));
        name.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        name.setForeground(new java.awt.Color(255, 255, 255));
        name.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        name.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameActionPerformed(evt);
            }
        });

        regno.setEditable(false);
        regno.setBackground(new java.awt.Color(255, 0, 0));
        regno.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        regno.setForeground(new java.awt.Color(255, 255, 255));
        regno.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        regno.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));

        identy.setEditable(false);
        identy.setBackground(new java.awt.Color(255, 0, 0));
        identy.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        identy.setForeground(new java.awt.Color(255, 255, 255));
        identy.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        identy.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));

        address.setEditable(false);
        address.setBackground(new java.awt.Color(255, 0, 0));
        address.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        address.setForeground(new java.awt.Color(255, 255, 255));
        address.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        address.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));

        age.setEditable(false);
        age.setBackground(new java.awt.Color(255, 0, 0));
        age.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        age.setForeground(new java.awt.Color(255, 255, 255));
        age.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        age.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        age.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ageActionPerformed(evt);
            }
        });

        shortname.setEditable(false);
        shortname.setBackground(new java.awt.Color(255, 0, 0));
        shortname.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        shortname.setForeground(new java.awt.Color(255, 255, 255));
        shortname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        shortname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));

        join.setEditable(false);
        join.setBackground(new java.awt.Color(255, 0, 0));
        join.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        join.setForeground(new java.awt.Color(255, 255, 255));
        join.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        join.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        join.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joinActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("JOIN DAY");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("CATEGORY");

        categoryshow.setEditable(false);
        categoryshow.setBackground(new java.awt.Color(255, 0, 0));
        categoryshow.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        categoryshow.setForeground(new java.awt.Color(255, 255, 255));
        categoryshow.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        categoryshow.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(imgshow, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(address, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                            .addComponent(identy, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(regno, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(name, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(age, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(join, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                            .addGap(55, 55, 55)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(shortname, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(categoryshow, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(42, 42, 42))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(imgshow, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(regno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(identy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(shortname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(age, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(categoryshow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(join, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(76, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

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

        jLabel19.setBackground(new java.awt.Color(0, 0, 0));
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logo2.png"))); // NOI18N

        jPanel4.setBackground(new java.awt.Color(0, 153, 51));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));

        ot.setFont(new java.awt.Font("Cooper Std Black", 0, 36)); // NOI18N
        ot.setForeground(new java.awt.Color(255, 255, 255));
        ot.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        salary.setFont(new java.awt.Font("Cooper Std Black", 0, 36)); // NOI18N
        salary.setForeground(new java.awt.Color(255, 255, 255));
        salary.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        absentdays.setFont(new java.awt.Font("Cooper Std Black", 0, 36)); // NOI18N
        absentdays.setForeground(new java.awt.Color(255, 255, 255));
        absentdays.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel21.setFont(new java.awt.Font("Cooper Std Black", 0, 36)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("YOUR  SALARY");

        jLabel22.setFont(new java.awt.Font("Cooper Std Black", 0, 36)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("ABSENT DAYS ");

        jLabel18.setFont(new java.awt.Font("Cooper Std Black", 0, 36)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("OT HOURS");

        jLabel23.setFont(new java.awt.Font("Cooper Std Black", 0, 36)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("ALLOWANCE");

        allowanceshow.setFont(new java.awt.Font("Cooper Std Black", 0, 36)); // NOI18N
        allowanceshow.setForeground(new java.awt.Color(255, 255, 255));
        allowanceshow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(salary, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(absentdays, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(39, 39, 39)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(allowanceshow, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(ot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ot, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(31, 31, 31)
                            .addComponent(allowanceshow, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(31, 31, 31)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(salary, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(absentdays, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(0, 0, 204));

        topicattendance.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        topicattendance.setForeground(new java.awt.Color(255, 255, 255));
        topicattendance.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        topicattendance.setText("Absent Details of this Month");
        topicattendance.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        datechooserattendance.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        search.setFont(new java.awt.Font("Tekton Pro", 0, 18)); // NOI18N
        search.setForeground(new java.awt.Color(255, 255, 255));
        search.setText("Search Month");
        search.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        search.setContentAreaFilled(false);
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        absenttable = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex,int colIndex)
            {
                return false;
            }
        };
        absenttable.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        absenttable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Reason"
            }
        ));
        absenttable.setGridColor(new java.awt.Color(255, 255, 255));
        absenttable.setSelectionBackground(new java.awt.Color(255, 0, 0));
        jScrollPane1.setViewportView(absenttable);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
                    .addComponent(topicattendance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(datechooserattendance, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(topicattendance, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(datechooserattendance, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        monthshow.setFont(new java.awt.Font("Times New Roman", 1, 40)); // NOI18N
        monthshow.setForeground(new java.awt.Color(255, 255, 255));
        monthshow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jPanel8.setBackground(new java.awt.Color(255, 0, 0));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("BASIC SALARY");

        basicsalaryshow.setBackground(new java.awt.Color(255, 0, 0));
        basicsalaryshow.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        basicsalaryshow.setForeground(new java.awt.Color(255, 255, 255));
        basicsalaryshow.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        basicsalaryshow.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("ALLOWANCE DETAILS");

        allowancedetails.setBackground(new java.awt.Color(255, 0, 0));
        allowancedetails.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        allowancedetails.setForeground(new java.awt.Color(255, 255, 255));
        allowancedetails.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        allowancedetails.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(basicsalaryshow))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(18, 18, 18)
                        .addComponent(allowancedetails)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(basicsalaryshow, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(allowancedetails, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(jLabel20))
                .addContainerGap())
        );

        jPanel9.setBackground(new java.awt.Color(0, 153, 51));

        oldersalaryshow = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex,int colIndex)
            {
                return false;
            }
        };
        oldersalaryshow.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        oldersalaryshow.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Total Salary", "By Whom"
            }
        ));
        oldersalaryshow.setGridColor(new java.awt.Color(255, 255, 255));
        oldersalaryshow.setSelectionBackground(new java.awt.Color(255, 0, 0));
        jScrollPane3.setViewportView(oldersalaryshow);

        topicselling1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        topicselling1.setForeground(new java.awt.Color(255, 255, 255));
        topicselling1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        topicselling1.setText("Your Older Salarys");
        topicselling1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addContainerGap())
                    .addComponent(topicselling1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(topicselling1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(timeshow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(monthshow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(detailshowpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(timeshow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(detailshowpanel, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(monthshow, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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

        setSize(new java.awt.Dimension(1800, 1009));
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
        // TODO add your handling code here:
    }//GEN-LAST:event_nameActionPerformed

    private void ageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ageActionPerformed

    private void joinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_joinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_joinActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
       
        Date getdate = datechooserattendance.getDate();
        DateFormat df = new SimpleDateFormat("yyyy-MM");
        getabsent(df.format(getdate));
        
    }//GEN-LAST:event_searchActionPerformed

    private void jPanel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseClicked
        
        dispose();
        main m  = new main();
        m.setVisible(true);
    }//GEN-LAST:event_jPanel12MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked

        try
        {
            this.dispose();

            employeeUnit b = new employeeUnit();
            b.setVisible(true);
        }
        catch (Exception ex)
        {
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail..!",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
        }
    }//GEN-LAST:event_jLabel2MouseClicked

    public static void main(String args[]) {

 
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(employeeUnit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(employeeUnit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(employeeUnit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(employeeUnit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new employeeUnit().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel absentdays;
    private javax.swing.JTable absenttable;
    private javax.swing.JTextField address;
    private javax.swing.JTextField age;
    private javax.swing.JTextField allowancedetails;
    private javax.swing.JLabel allowanceshow;
    private javax.swing.JTextField basicsalaryshow;
    private javax.swing.JTextField categoryshow;
    private com.toedter.calendar.JDateChooser datechooserattendance;
    private javax.swing.JPanel detailshowpanel;
    private javax.swing.JTextField identy;
    private javax.swing.JLabel imageshow;
    private javax.swing.JLabel imgshow;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField join;
    private javax.swing.JLabel monthshow;
    private javax.swing.JTextField name;
    private javax.swing.JTable oldersalaryshow;
    private javax.swing.JLabel ot;
    private javax.swing.JTextField regno;
    private javax.swing.JLabel salary;
    private javax.swing.JButton search;
    private javax.swing.JTextField shortname;
    private javax.swing.JLabel showemployeename;
    private javax.swing.JLabel timeshow;
    private javax.swing.JLabel topicattendance;
    private javax.swing.JLabel topicselling1;
    private javax.swing.JLabel toplabel;
    // End of variables declaration//GEN-END:variables
}
