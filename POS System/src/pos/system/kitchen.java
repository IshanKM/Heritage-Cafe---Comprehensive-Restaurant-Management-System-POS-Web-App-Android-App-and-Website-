
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import net.proteanit.sql.DbUtils;

public class kitchen extends javax.swing.JFrame {

    public kitchen()
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
        Preferences pf = Preferences.userNodeForPackage(kitchen.class);
        String note = pf.get("notes", "root");
        
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
        
        //displayorder table
          
        JTableHeader completerheader = complete.getTableHeader();
        completerheader.setForeground(Color.red);
        completerheader.setFont(new Font("Times New Roman", Font.BOLD,20));
        ((DefaultTableCellRenderer)completerheader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);
        complete.setRowHeight(40);
        complete.setSelectionForeground(Color.white);
        
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
        jLabel19 = new javax.swing.JLabel();
        tableshow = new javax.swing.JLabel();
        idshow = new javax.swing.JLabel();
        timeshowarea = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        complete = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        notes = new javax.swing.JEditorPane();
        clearnote = new javax.swing.JButton();
        rejectorder = new javax.swing.JButton();
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
                "ID", "TIME", "DETAILS", "PRICE", "TABLE_NO", "IDs_in_Order", "QTYs_in_Order"
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                "Name", "Qty", "Image"
            }
        ));
        showitems.setGridColor(new java.awt.Color(255, 0, 0));
        showitems.setSelectionBackground(new java.awt.Color(255, 0, 0));
        showitems.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(showitems);

        jLabel19.setFont(new java.awt.Font("Stencil Std", 3, 24)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("ITEMS DISPLAY");

        tableshow.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tableshow.setForeground(new java.awt.Color(255, 0, 51));
        tableshow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tableshow.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        idshow.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        idshow.setForeground(new java.awt.Color(255, 0, 51));
        idshow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        idshow.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        timeshowarea.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        timeshowarea.setForeground(new java.awt.Color(255, 0, 51));
        timeshowarea.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeshowarea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(idshow, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(tableshow, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(timeshowarea, javax.swing.GroupLayout.PREFERRED_SIZE, 782, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tableshow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(idshow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(timeshowarea, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        complete = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex,int colIndex)
            {
                return false;
            }
        };
        complete.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "ORDER_TIME", "DELIVERY_TIME", "DETAILS", "PRICE", "TABLE_NO", "IDs_in_Order", "Qtys_in_Order"
            }
        ));
        jScrollPane3.setViewportView(complete);

        jLabel15.setFont(new java.awt.Font("Stencil Std", 3, 24)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("DAILY SUMMARY");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(292, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(316, 316, 316))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(122, 122, 122))
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

        rejectorder.setBackground(new java.awt.Color(255, 0, 0));
        rejectorder.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        rejectorder.setForeground(new java.awt.Color(255, 255, 255));
        rejectorder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/cancel.png"))); // NOI18N
        rejectorder.setText("ORDER");
        rejectorder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        rejectorder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rejectorderActionPerformed(evt);
            }
        });

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
        jLabel6.setText("KITCHEN PAGE");

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

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });

        absentlabel4.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        absentlabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        absentlabel4.setText("ONLINE ORDERING");
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
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(absentlabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
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
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(ordercomplete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(shopcamera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rejectorder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
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
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(detailshowpanel, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                                    .addComponent(timeshow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(ordercomplete, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(rejectorder, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(shopcamera, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                    //delete from kithen table after complete

                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/the_haritage_cafe", "root", "");
                    String delete = displayorder.getModel().getValueAt(row,0).toString();
                    ps = con.prepareStatement("DELETE FROM kitchen WHERE ID = '"+delete+"'");
                    ps.executeUpdate();

                    String id = displayorder.getModel().getValueAt(row,0).toString();
                    String time = displayorder.getModel().getValueAt(row,1).toString();
                    String details = displayorder.getModel().getValueAt(row,2).toString();
                    String price = displayorder.getModel().getValueAt(row,3).toString();
                    String tbleno = displayorder.getModel().getValueAt(row,4).toString();
                    String idorder = displayorder.getModel().getValueAt(row,5).toString();
                    String qtyorder = displayorder.getModel().getValueAt(row,6).toString();
                    LocalTime delivertime = java.time.LocalTime.now();

                    //add to data database

                    ps1 = con.prepareStatement("INSERT INTO complete_orders VALUES(?,?,?,?,?,?,?)");
                    ps1.setString(1, id);
                    ps1.setString(2, time);
                    ps1.setString(3, details);
                    ps1.setString(4, price);
                    ps1.setString(5, tbleno);
                    ps1.setString(6, idorder);
                    ps1.setString(7, qtyorder);

                    ps1.executeUpdate();

                    //add to data complete table

                    Object[] colomus = new Object[8];
                    DefaultTableModel  addtotable =  (DefaultTableModel)complete.getModel();

                    colomus[0] = id;
                    colomus[1] = time;
                    colomus[2] = delivertime;
                    colomus[3] = details;
                    colomus[4] = price;
                    colomus[5] = tbleno;
                    colomus[6] = idorder;
                    colomus[7] = qtyorder;
                    addtotable.addRow(colomus);

                    //delete row from displayorder table

                    DefaultTableModel  removerow =  (DefaultTableModel)displayorder.getModel();
                    removerow.removeRow(row);

                    showitems.setModel(new DefaultTableModel(null,new String[] {"Name","Qty","Price","Image"}));
                    idshow.setText("");
                    timeshowarea.setText("");
                    tableshow.setText("");

                }
                catch (SQLException ex)
                {
                     new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Someting Wrong .Try Again",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT); 
                }
            }
        }
        else
        {
            //JOptionPane.showMessageDialog(null, "Please Select the Row You Want to Deliver");
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Select the Row ",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
        }

    }//GEN-LAST:event_ordercompleteActionPerformed

    private void rejectorderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rejectorderActionPerformed
        

            reject r = new reject();
            r.setVisible(true);

    }//GEN-LAST:event_rejectorderActionPerformed

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
        String ids = displayorder.getModel().getValueAt(selected, 5 ).toString();
        String id = displayorder.getModel().getValueAt(selected, 0 ).toString();
        String time = displayorder.getModel().getValueAt(selected, 1 ).toString();
        String tblno = displayorder.getModel().getValueAt(selected, 4 ).toString();

        MyQuery mq = new MyQuery();
        ArrayList<getdetails> list = mq.getiddetails(ids);
        String[] columnName = {"Name","QTY","Image"};
        Object[][] rows = new Object[list.size()][4];
        for(int i = 0; i < list.size(); i++){
            rows[i][0] = list.get(i).getName();
            String qty= displayorder.getModel().getValueAt(selected,6 ).toString();
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
        
        idshow.setText("ID :-" +id);
        tableshow.setText("Table No :- " +tblno);
        timeshowarea.setText(time);

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
        pf.put("currentpage", "kitchen");
        
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

            kitchen b = new kitchen();
            b.setVisible(true);
        }
        catch (Exception ex)
        {
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail..!",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
        }
    }//GEN-LAST:event_jLabel16MouseClicked

    private void clearnoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearnoteActionPerformed
       
        notes.setText("");
        
        Preferences pf = Preferences.userNodeForPackage(kitchen.class);
        pf.put("notes", "");
    }//GEN-LAST:event_clearnoteActionPerformed

    private void notesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_notesKeyReleased
        
        Preferences pf = Preferences.userNodeForPackage(kitchen.class);
        pf.put("notes", notes.getText().trim());
    }//GEN-LAST:event_notesKeyReleased

    private void absentlabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_absentlabel4MouseClicked

        Onlineorder o = new Onlineorder();
        o.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_absentlabel4MouseClicked

    
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new kitchen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel absentlabel4;
    private javax.swing.JButton clearnote;
    private javax.swing.JTable complete;
    private javax.swing.JPanel detailshowpanel;
    private javax.swing.JTable displayorder;
    private javax.swing.JLabel idshow;
    private javax.swing.JLabel imageshow;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JEditorPane notes;
    private javax.swing.JButton ordercomplete;
    private javax.swing.JButton rejectorder;
    private javax.swing.JButton shopcamera;
    private javax.swing.JLabel showemployeename;
    private javax.swing.JTable showitems;
    private javax.swing.JLabel tableshow;
    private javax.swing.JLabel timeshow;
    private javax.swing.JLabel timeshowarea;
    // End of variables declaration//GEN-END:variables
}
