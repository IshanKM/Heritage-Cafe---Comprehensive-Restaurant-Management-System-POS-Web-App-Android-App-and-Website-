
package pos.system;

import com.sbix.jnotify.NPosition;
import com.sbix.jnotify.NotifyType;
import com.sbix.jnotify.NotifyWindow;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
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
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class managmentitem extends javax.swing.JFrame {

    public managmentitem() 
    {
        try 
         {
           initComponents();
           clock();
           detailsbox();
           loadingshowdatabase();
           decorate();
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
         
        JTableHeader header = databaseshow.getTableHeader();
        header.setForeground(Color.red);
        header.setFont(new Font("Times New Roman", Font.BOLD,20));
        ((DefaultTableCellRenderer)header.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);
        databaseshow.setForeground(Color.black);
        databaseshow.setSelectionForeground(Color.white);
        databaseshow.setRowHeight(150);
        databaseshow.setShowGrid(true);

     }
    
    public void loadingshowdatabase()
    {
        
        try 
        {
            MyQuery mq = new MyQuery();
            ArrayList<getdetails> list = mq.breakfast();
            String[] columnName = {"id","Name","Price","Image","Discount"};
            Object[][] rows = new Object[list.size()][5];
            for(int i = 0; i < list.size(); i++)
            {
                rows[i][0] = list.get(i).getID();
                rows[i][1] = list.get(i).getName();
                rows[i][2] = list.get(i).getPrice();

                if(list.get(i).getMyImage() != null)
                {

                 ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getMyImage()).getImage()
                 .getScaledInstance(250, 150, Image.SCALE_SMOOTH) );   

                rows[i][3] = image;
                }
                else
                {
                    rows[i][3] = null;
                }
                rows[i][4] = list.get(i).getDiscount();
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
            databaseshow.getColumnModel().getColumn(0).setPreferredWidth(100);
            databaseshow.getColumnModel().getColumn(1).setPreferredWidth(200);
            databaseshow.getColumnModel().getColumn(2).setPreferredWidth(100);
            databaseshow.getColumnModel().getColumn(3).setPreferredWidth(281);
            databaseshow.getColumnModel().getColumn(4).setPreferredWidth(50);
            
            
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, e);
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
            try 
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
            catch (Exception e) 
             {
                JOptionPane.showMessageDialog(this, e);
             }
            
  
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
               Image newImage = img.getScaledInstance(362,250,Image.SCALE_SMOOTH);
               ImageIcon image = new ImageIcon(newImage);
               mainimg.setIcon(image);
               getpath = path;
             }
            else if(result == JFileChooser.CANCEL_OPTION)
             {
                JOptionPane.showMessageDialog(null, "No Data");
             }
         return getpath;
        }

      public void getdetails(int id) throws SQLException
        {
            try 
             {
                Connection con = null;
                Statement st;
                ResultSet rs;

                byte[] img = null;
                String getcategory = null;
                String getdes = null;
                String imgpath = null;
                String getavailability = "";

                MyQuery mq = new MyQuery();
                con = mq.getConnection();
                st = con.createStatement();

                rs = st.executeQuery( "select Category,Image,Description,imageoriginal,Availability from items where ID = "+id+"");
                while(rs.next())
                  {               
                     img = rs.getBytes("imageoriginal");  
                     getcategory = rs.getString("Category");
                     getdes = rs.getString("Description");
                     imgpath =rs.getString("Image");
                     getavailability =rs.getString("Availability");

                  }

                ImageIcon image = new  ImageIcon(img);
                Image myimg = image.getImage().getScaledInstance(362,250, Image.SCALE_SMOOTH);
                ImageIcon newimg = new ImageIcon(myimg);
                mainimg.setIcon(newimg);

                description.setText(getdes);
                categoryshow.setSelectedItem(getcategory);
                availability.setSelectedItem(getavailability);
                 
             }
            catch (Exception e) 
             {
                 new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
             }
  
        }
      
      
      public void clearall()
        {
            name.setText("");
            idno.setText("");
            categoryshow.setSelectedIndex(0);
            availability.setSelectedIndex(0);
            discount.setText("");
            price.setText("");
            showpath.setText("");
            description.setText("");
            mainimg.setIcon(null);
            search.setText("");
            loadingshowdatabase();

        }
      
      public void searchdatabase()
      {
           try 
         {
            MyQuery mq = new MyQuery();
             ArrayList<getdetails> list = mq.search(search.getText());
            String[] columnName = {"id","Name","Price","Image","Discount"};
            Object[][] rows = new Object[list.size()][5];
            for(int i = 0; i < list.size(); i++)
            {
                rows[i][0] = list.get(i).getID();
                rows[i][1] = list.get(i).getName();
                rows[i][2] = list.get(i).getPrice();

                if(list.get(i).getMyImage() != null)
                {

                 ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getMyImage()).getImage()
                 .getScaledInstance(250, 150, Image.SCALE_SMOOTH) );   

                rows[i][3] = image;
                }
                else
                {
                    rows[i][3] = null;
                }
                rows[i][4] = list.get(i).getDiscount();
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
            databaseshow.getColumnModel().getColumn(0).setPreferredWidth(100);
            databaseshow.getColumnModel().getColumn(1).setPreferredWidth(200);
            databaseshow.getColumnModel().getColumn(2).setPreferredWidth(100);
            databaseshow.getColumnModel().getColumn(3).setPreferredWidth(281);
            databaseshow.getColumnModel().getColumn(4).setPreferredWidth(50);
            
         } 
        catch (Exception e) 
         {
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
         }
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
        jLabel38 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        timeshow = new javax.swing.JLabel();
        detailshowpanel = new javax.swing.JPanel();
        showemployeename = new javax.swing.JLabel();
        imageshow = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        databaseshow = new javax.swing.JTable();
        category = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        searchbtn = new javax.swing.JLabel();
        search = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        mainimg = new javax.swing.JLabel();
        totallabel = new javax.swing.JLabel();
        idno = new javax.swing.JTextField();
        name = new javax.swing.JTextField();
        totallabel1 = new javax.swing.JLabel();
        totallabel2 = new javax.swing.JLabel();
        categoryshow = new javax.swing.JComboBox();
        totallabel3 = new javax.swing.JLabel();
        discount = new javax.swing.JTextField();
        totallabel4 = new javax.swing.JLabel();
        price = new javax.swing.JTextField();
        totallabel6 = new javax.swing.JLabel();
        additembtn = new javax.swing.JButton();
        updateitembtn = new javax.swing.JButton();
        deleteitembtn = new javax.swing.JButton();
        showpath = new javax.swing.JTextField();
        description = new javax.swing.JTextField();
        jButton12 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        availability = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();

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
        jLabel1.setText("ITEMS MANAGMENT UNIT");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 0, 0)));

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        jLabel38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel38MouseClicked(evt);
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
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
        detailshowpanel.add(imageshow, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 62, 60));

        databaseshow = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex,int colIndex)
            {
                return false;
            }
        };
        databaseshow.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        databaseshow.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Price", "Image", "Discount"
            }
        ));
        databaseshow.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        databaseshow.setFocusable(false);
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

        javax.swing.GroupLayout categoryLayout = new javax.swing.GroupLayout(category);
        category.setLayout(categoryLayout);
        categoryLayout.setHorizontalGroup(
            categoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, categoryLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(categoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton11))
                .addGap(22, 22, 22))
        );
        categoryLayout.setVerticalGroup(
            categoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, categoryLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addGap(43, 43, 43))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        searchbtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        searchbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search_1.png"))); // NOI18N
        searchbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchbtnMouseClicked(evt);
            }
        });

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

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("SEARCH HERE");
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(search, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(searchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(searchbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(8, 8, 8))
        );

        jPanel4.setBackground(new java.awt.Color(255, 0, 0));

        jPanel6.setBackground(new java.awt.Color(255, 0, 0));

        jButton1.setBackground(new java.awt.Color(255, 0, 0));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("BROWSE");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        mainimg.setForeground(new java.awt.Color(255, 255, 255));
        mainimg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainimg.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));

        totallabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        totallabel.setForeground(new java.awt.Color(255, 255, 255));
        totallabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        totallabel.setText("ID OF THE ITEM");

        idno.setEditable(false);
        idno.setBackground(new java.awt.Color(255, 0, 0));
        idno.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        idno.setForeground(new java.awt.Color(255, 255, 255));
        idno.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        idno.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        idno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idnoActionPerformed(evt);
            }
        });

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

        totallabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        totallabel1.setForeground(new java.awt.Color(255, 255, 255));
        totallabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        totallabel1.setText("CATEGORY");

        totallabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        totallabel2.setForeground(new java.awt.Color(255, 255, 255));
        totallabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        totallabel2.setText("NAME OF THE ITEM");

        categoryshow.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        categoryshow.setForeground(new java.awt.Color(255, 255, 255));
        categoryshow.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "breakfast", "pizza", "pasta", "soups", "sandwich", "deserts", "drinks", " " }));

        totallabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        totallabel3.setForeground(new java.awt.Color(255, 255, 255));
        totallabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        totallabel3.setText("DISCOUNT");

        discount.setBackground(new java.awt.Color(255, 0, 0));
        discount.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        discount.setForeground(new java.awt.Color(255, 255, 255));
        discount.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        discount.setAutoscrolls(false);
        discount.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        discount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                discountMouseClicked(evt);
            }
        });
        discount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                discountKeyReleased(evt);
            }
        });

        totallabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        totallabel4.setForeground(new java.awt.Color(255, 255, 255));
        totallabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        totallabel4.setText("PRICE");

        price.setBackground(new java.awt.Color(255, 0, 0));
        price.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        price.setForeground(new java.awt.Color(255, 255, 255));
        price.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        price.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        price.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                priceMouseClicked(evt);
            }
        });
        price.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                priceKeyReleased(evt);
            }
        });

        totallabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        totallabel6.setForeground(new java.awt.Color(255, 255, 255));
        totallabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        totallabel6.setText("DESCRIPTION");

        additembtn.setBackground(new java.awt.Color(0, 153, 102));
        additembtn.setFont(new java.awt.Font("Stencil Std", 1, 18)); // NOI18N
        additembtn.setForeground(new java.awt.Color(255, 255, 255));
        additembtn.setText("ADD");
        additembtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                additembtnActionPerformed(evt);
            }
        });

        updateitembtn.setBackground(new java.awt.Color(0, 51, 204));
        updateitembtn.setFont(new java.awt.Font("Stencil Std", 1, 18)); // NOI18N
        updateitembtn.setForeground(new java.awt.Color(255, 255, 255));
        updateitembtn.setText("UPDATE");
        updateitembtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateitembtnActionPerformed(evt);
            }
        });

        deleteitembtn.setBackground(new java.awt.Color(0, 0, 0));
        deleteitembtn.setFont(new java.awt.Font("Stencil Std", 1, 18)); // NOI18N
        deleteitembtn.setForeground(new java.awt.Color(255, 255, 255));
        deleteitembtn.setText("DELETE");
        deleteitembtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteitembtnActionPerformed(evt);
            }
        });

        showpath.setEditable(false);
        showpath.setBackground(new java.awt.Color(255, 0, 0));
        showpath.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        showpath.setForeground(new java.awt.Color(255, 255, 255));
        showpath.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        showpath.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        description.setBackground(new java.awt.Color(255, 0, 0));
        description.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        description.setForeground(new java.awt.Color(255, 255, 255));
        description.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        description.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        description.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                descriptionMouseClicked(evt);
            }
        });

        jButton12.setBackground(new java.awt.Color(255, 0, 0));
        jButton12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setText("CLEAR ALL");
        jButton12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("ITEM DETAILS");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));

        availability.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        availability.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Available", "Unavailable" }));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(additembtn, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(updateitembtn, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(deleteitembtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(totallabel, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(idno))
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(mainimg, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(showpath)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(totallabel2)
                        .addGap(27, 27, 27)
                        .addComponent(name))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(totallabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(price))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(totallabel6)
                        .addGap(23, 23, 23)
                        .addComponent(description))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(totallabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(categoryshow, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(availability, 0, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(totallabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(discount, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(showpath, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(mainimg, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(totallabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(idno, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(totallabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(name, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(totallabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(categoryshow, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(totallabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(discount, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(availability))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(totallabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(price, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(totallabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(description, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deleteitembtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(additembtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(updateitembtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jPanel15.setBackground(new java.awt.Color(255, 0, 0));
        jPanel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel15MouseClicked(evt);
            }
        });

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/basicmanagment.png"))); // NOI18N
        jLabel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        jLabel27.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("BASIC ACTIVITIES");

        jLabel28.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("MANAGMENT");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel28)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel16.setBackground(new java.awt.Color(0, 0, 255));
        jPanel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel16MouseClicked(evt);
            }
        });

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/managemployee.png"))); // NOI18N
        jLabel29.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        jLabel30.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("EMPLOYEES");

        jLabel31.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("MANAGMENT");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel31)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel17.setBackground(new java.awt.Color(0, 153, 102));
        jPanel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel17MouseClicked(evt);
            }
        });

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/itemmanage.png"))); // NOI18N
        jLabel32.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        jLabel33.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("ITEMS");

        jLabel34.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("MANAGMENT");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel34)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel18.setBackground(new java.awt.Color(0, 0, 0));
        jPanel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel18MouseClicked(evt);
            }
        });

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/suppliermanage.png"))); // NOI18N
        jLabel35.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        jLabel36.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("SUPPLIER");

        jLabel37.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("MANAGMENT");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel37)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
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
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(236, 236, 236)
                        .addComponent(timeshow, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(detailshowpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(detailshowpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(timeshow, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1))
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(category, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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

        setSize(new java.awt.Dimension(1800, 1001));
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

    private void databaseshowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_databaseshowMouseClicked

        try
        {

            int row = databaseshow.getSelectedRow();
            String id = (databaseshow.getModel().getValueAt(row, 0 ).toString());
            String getname = (databaseshow.getModel().getValueAt(row, 1 ).toString());
            String getprice = (databaseshow.getModel().getValueAt(row, 2 ).toString());
            String getdiscount = (databaseshow.getModel().getValueAt(row, 4 ).toString());

            idno.setText(id);
            name.setText(getname);
            price.setText(getprice);
            discount.setText(getdiscount);
            

            getdetails(Integer.parseInt(id));
 
        }

        catch(Exception e)
        {
             new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Someting Wrong .Try Again",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT); 
        }
    }//GEN-LAST:event_databaseshowMouseClicked

    private void databaseshowMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_databaseshowMouseReleased

    }//GEN-LAST:event_databaseshowMouseReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        try 
        {
            MyQuery mq = new MyQuery();
            ArrayList<getdetails> list = mq.breakfast();
            String[] columnName = {"id","Name","Price","Image","Discount"};
            Object[][] rows = new Object[list.size()][5];
            for(int i = 0; i < list.size(); i++)
            {
                rows[i][0] = list.get(i).getID();
                rows[i][1] = list.get(i).getName();
                rows[i][2] = list.get(i).getPrice();

                if(list.get(i).getMyImage() != null)
                {

                 ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getMyImage()).getImage()
                 .getScaledInstance(250, 150, Image.SCALE_SMOOTH) );   

                rows[i][3] = image;
                }
                else
                {
                    rows[i][3] = null;
                }
                rows[i][4] = list.get(i).getDiscount();
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
            databaseshow.getColumnModel().getColumn(0).setPreferredWidth(100);
            databaseshow.getColumnModel().getColumn(1).setPreferredWidth(200);
            databaseshow.getColumnModel().getColumn(2).setPreferredWidth(100);
            databaseshow.getColumnModel().getColumn(3).setPreferredWidth(281);
            databaseshow.getColumnModel().getColumn(4).setPreferredWidth(50);
            
        } 
        catch (Exception e) 
        {
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
        }


    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        try 
        {
            MyQuery mq = new MyQuery();
            ArrayList<getdetails> list = mq.pizza();
            String[] columnName = {"id","Name","Price","Image","Discount"};
            Object[][] rows = new Object[list.size()][5];
            for(int i = 0; i < list.size(); i++)
            {
                rows[i][0] = list.get(i).getID();
                rows[i][1] = list.get(i).getName();
                rows[i][2] = list.get(i).getPrice();

                if(list.get(i).getMyImage() != null)
                {

                 ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getMyImage()).getImage()
                 .getScaledInstance(250, 150, Image.SCALE_SMOOTH) );   

                rows[i][3] = image;
                }
                else
                {
                    rows[i][3] = null;
                }
                rows[i][4] = list.get(i).getDiscount();
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
            databaseshow.getColumnModel().getColumn(0).setPreferredWidth(100);
            databaseshow.getColumnModel().getColumn(1).setPreferredWidth(200);
            databaseshow.getColumnModel().getColumn(2).setPreferredWidth(100);
            databaseshow.getColumnModel().getColumn(3).setPreferredWidth(281);
            databaseshow.getColumnModel().getColumn(4).setPreferredWidth(50);
            
        } 
        catch (Exception e) 
        {
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
        }


    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        
        try 
        {
            MyQuery mq = new MyQuery();
            ArrayList<getdetails> list = mq.pasta();
            String[] columnName = {"id","Name","Price","Image","Discount"};
            Object[][] rows = new Object[list.size()][5];
            for(int i = 0; i < list.size(); i++)
            {
                rows[i][0] = list.get(i).getID();
                rows[i][1] = list.get(i).getName();
                rows[i][2] = list.get(i).getPrice();

                if(list.get(i).getMyImage() != null)
                {

                 ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getMyImage()).getImage()
                 .getScaledInstance(250, 150, Image.SCALE_SMOOTH) );   

                rows[i][3] = image;
                }
                else
                {
                    rows[i][3] = null;
                }
                rows[i][4] = list.get(i).getDiscount();
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
            databaseshow.getColumnModel().getColumn(0).setPreferredWidth(100);
            databaseshow.getColumnModel().getColumn(1).setPreferredWidth(200);
            databaseshow.getColumnModel().getColumn(2).setPreferredWidth(100);
            databaseshow.getColumnModel().getColumn(3).setPreferredWidth(281);
            databaseshow.getColumnModel().getColumn(4).setPreferredWidth(50);
            
        } 
        catch (Exception e) 
        {
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
        }

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        
        try 
        {
            MyQuery mq = new MyQuery();
            ArrayList<getdetails> list = mq.soup();
            String[] columnName = {"id","Name","Price","Image","Discount"};
            Object[][] rows = new Object[list.size()][5];
            for(int i = 0; i < list.size(); i++)
            {
                rows[i][0] = list.get(i).getID();
                rows[i][1] = list.get(i).getName();
                rows[i][2] = list.get(i).getPrice();

                if(list.get(i).getMyImage() != null)
                {

                 ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getMyImage()).getImage()
                 .getScaledInstance(250, 150, Image.SCALE_SMOOTH) );   

                rows[i][3] = image;
                }
                else
                {
                    rows[i][3] = null;
                }
                rows[i][4] = list.get(i).getDiscount();
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
            databaseshow.getColumnModel().getColumn(0).setPreferredWidth(100);
            databaseshow.getColumnModel().getColumn(1).setPreferredWidth(200);
            databaseshow.getColumnModel().getColumn(2).setPreferredWidth(100);
            databaseshow.getColumnModel().getColumn(3).setPreferredWidth(281);
            databaseshow.getColumnModel().getColumn(4).setPreferredWidth(50);
            
        } 
        catch (Exception e) 
        {
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
        }


    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

        try 
        {
            MyQuery mq = new MyQuery();
            ArrayList<getdetails> list = mq.sandwich();
            String[] columnName = {"id","Name","Price","Image","Discount"};
            Object[][] rows = new Object[list.size()][5];
            for(int i = 0; i < list.size(); i++)
            {
                rows[i][0] = list.get(i).getID();
                rows[i][1] = list.get(i).getName();
                rows[i][2] = list.get(i).getPrice();

                if(list.get(i).getMyImage() != null)
                {

                 ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getMyImage()).getImage()
                 .getScaledInstance(250, 150, Image.SCALE_SMOOTH) );   

                rows[i][3] = image;
                }
                else
                {
                    rows[i][3] = null;
                }
                rows[i][4] = list.get(i).getDiscount();
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
            databaseshow.getColumnModel().getColumn(0).setPreferredWidth(100);
            databaseshow.getColumnModel().getColumn(1).setPreferredWidth(200);
            databaseshow.getColumnModel().getColumn(2).setPreferredWidth(100);
            databaseshow.getColumnModel().getColumn(3).setPreferredWidth(281);
            databaseshow.getColumnModel().getColumn(4).setPreferredWidth(50);
            
        } 
        catch (Exception e) 
        {
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
        }


    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed

       try 
        {
            MyQuery mq = new MyQuery();
            ArrayList<getdetails> list = mq.desert();
            String[] columnName = {"id","Name","Price","Image","Discount"};
            Object[][] rows = new Object[list.size()][5];
            for(int i = 0; i < list.size(); i++)
            {
                rows[i][0] = list.get(i).getID();
                rows[i][1] = list.get(i).getName();
                rows[i][2] = list.get(i).getPrice();

                if(list.get(i).getMyImage() != null)
                {

                 ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getMyImage()).getImage()
                 .getScaledInstance(250, 150, Image.SCALE_SMOOTH) );   

                rows[i][3] = image;
                }
                else
                {
                    rows[i][3] = null;
                }
                rows[i][4] = list.get(i).getDiscount();
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
            databaseshow.getColumnModel().getColumn(0).setPreferredWidth(100);
            databaseshow.getColumnModel().getColumn(1).setPreferredWidth(200);
            databaseshow.getColumnModel().getColumn(2).setPreferredWidth(100);
            databaseshow.getColumnModel().getColumn(3).setPreferredWidth(281);
            databaseshow.getColumnModel().getColumn(4).setPreferredWidth(50);
            
        } 
        catch (Exception e) 
        {
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
        }


    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed

        try 
        {
            MyQuery mq = new MyQuery();
            ArrayList<getdetails> list = mq.drink();
            String[] columnName = {"id","Name","Price","Image","Discount"};
            Object[][] rows = new Object[list.size()][5];
            for(int i = 0; i < list.size(); i++)
            {
                rows[i][0] = list.get(i).getID();
                rows[i][1] = list.get(i).getName();
                rows[i][2] = list.get(i).getPrice();

                if(list.get(i).getMyImage() != null)
                {

                 ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getMyImage()).getImage()
                 .getScaledInstance(250, 150, Image.SCALE_SMOOTH) );   

                rows[i][3] = image;
                }
                else
                {
                    rows[i][3] = null;
                }
                rows[i][4] = list.get(i).getDiscount();
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
            databaseshow.getColumnModel().getColumn(0).setPreferredWidth(100);
            databaseshow.getColumnModel().getColumn(1).setPreferredWidth(200);
            databaseshow.getColumnModel().getColumn(2).setPreferredWidth(100);
            databaseshow.getColumnModel().getColumn(3).setPreferredWidth(281);
            databaseshow.getColumnModel().getColumn(4).setPreferredWidth(50);
            
        } 
        catch (Exception e) 
        {
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
        }


    }//GEN-LAST:event_jButton11ActionPerformed

    private void searchbtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchbtnMouseClicked

        if (!"".equals(search.getText()))
        {
             searchdatabase();
        } 
        else 
        {
             new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Search Text Field is Empty",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
        }
       
    }//GEN-LAST:event_searchbtnMouseClicked

    private void searchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchMouseClicked

        try
        {
            Runtime.getRuntime().exec("cmd /c osk");
            search.grabFocus();
        }
        catch (IOException ex)
        {
            //JOptionPane.showMessageDialog(null, "Fail to get Touch Key Board ");
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Fail to get Touch Key Board",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
        }
    }//GEN-LAST:event_searchMouseClicked

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed

        clearall();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void descriptionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_descriptionMouseClicked

        try
        {
            Runtime.getRuntime().exec("cmd /c osk");
            description.grabFocus();
        }
        catch (Exception ex)
        {
            //JOptionPane.showMessageDialog(null, "Fail to get Touch Key Board ");
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Fail to get Touch Key Board",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
        }
    }//GEN-LAST:event_descriptionMouseClicked

    private void deleteitembtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteitembtnActionPerformed

        int row = databaseshow.getSelectedRow();
        int id = Integer.parseInt(idno.getText());

        if (row >= 0)
        {
            int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
            if (confirm == 0)
            {
                try
                {
                    //delete from item table

                    MyQuery m = new MyQuery();
                    Connection con = m.getConnection();
                    PreparedStatement ps = con.prepareStatement("DELETE FROM items WHERE id = "+id+"");
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this,"Successfully Deleted");
                    clearall();

                }
                catch(Exception e)
                {
                    new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Please Select the Row You Want To Delete");
        }
    }//GEN-LAST:event_deleteitembtnActionPerformed

    private void updateitembtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateitembtnActionPerformed

        if ( !"".equals(name.getText()) && null != mainimg.getIcon() && !"".equals(discount.getText()) &&!"".equals(price.getText()) &&!"".equals(description.getText()) && "Select" != categoryshow.getSelectedItem())
        {

            int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
            if (confirm == 0)
            {
                try
                {

                    MyQuery m = new MyQuery();
                    Connection con = m.getConnection();

                    int id = Integer.parseInt(idno.getText());

                    if (!"".equals(showpath.getText()))
                    {
                        //update from iemployee table
                        InputStream img = new FileInputStream(new File(showpath.getText()));

                        PreparedStatement ps = con.prepareStatement("UPDATE items SET Name = ?,Category = ?, Price =?, Discount = ?,Image = ?, imageoriginal =?, Description = ?, Availability=? WHERE ID ="+id+"");
                        ps.setString(1, name.getText());
                        ps.setString(2, (String) categoryshow.getSelectedItem());
                        ps.setDouble(3,Double.parseDouble(price.getText()));
                        ps.setDouble(4, Double.parseDouble(discount.getText()));
                        ps.setString(5, showpath.getText());
                        ps.setBlob(6, img);
                        ps.setString(7, description.getText());
                        ps.setString(8, (String) availability.getSelectedItem());

                        ps.executeUpdate();
                        new NotifyWindow(NotifyType.SUCCESS_NOTIFICATION,"Update Succesfully...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);

                        clearall();
                    }
                    
                    else
                        
                    {
                        PreparedStatement ps = con.prepareStatement("UPDATE items SET Name = ?,Category = ?, Price =?, Discount = ?,Image = ?, Description = ?, Availability=?  WHERE ID ="+id+"");
                        ps.setString(1, name.getText());
                        ps.setString(2, (String) categoryshow.getSelectedItem());
                        ps.setDouble(3,Double.parseDouble(price.getText()));
                        ps.setDouble(4, Double.parseDouble(discount.getText()));
                        ps.setString(5, showpath.getText());
                        ps.setString(6, description.getText());
                        ps.setString(7, (String) availability.getSelectedItem());

                        ps.executeUpdate();
                        new NotifyWindow(NotifyType.SUCCESS_NOTIFICATION,"Update Succesfully...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);

                        clearall();

                    }

                }
                catch(Exception e)
                {
                    new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
                }
            }
        }
        else
        {
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Fill All Fields...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
        }
    }//GEN-LAST:event_updateitembtnActionPerformed

    private void additembtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_additembtnActionPerformed

        if ( !"".equals(name.getText()) && null != mainimg.getIcon() && !"".equals(discount.getText()) &&!"".equals(price.getText()) &&!"".equals(description.getText()) && "Select" != categoryshow.getSelectedItem())
        {

            try
            {

                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;

                InputStream img = new FileInputStream(new File(showpath.getText()));

                int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
                if (confirm == 0)
                {
                    MyQuery m = new MyQuery();
                    con = m.getConnection();
                    String query = "select * from items WHERE Name =?";
                    ps = con.prepareStatement(query);
                    ps.setString(1, name.getText());
                    rs = ps.executeQuery();

                    if(rs.next())
                    {
                        JOptionPane.showMessageDialog(this,"Alreay Have this Name in The Item Table ");

                    }

                    else
                    {

                        ps = con.prepareStatement("INSERT INTO items VALUES (?,?,?,?,?,?,?,?,?)");
                        ps.setInt(1, 0);
                        ps.setString(2, name.getText());
                        ps.setString(3, (String) categoryshow.getSelectedItem());
                        ps.setDouble(4,Double.parseDouble(price.getText()));
                        ps.setDouble(5, Double.parseDouble(discount.getText()));
                        ps.setString(6, showpath.getText());
                        ps.setBlob(7, img);
                        ps.setString(8, description.getText());
                        ps.setString(9, (String) availability.getSelectedItem());

                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null,"Item Added Successfully...!");
                        clearall();

                    }

                }

            }

            catch(Exception e)
            {
                new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
            }

        }
        else
        {
            JOptionPane.showMessageDialog(null,"Please Fill All Details");
        }
    }//GEN-LAST:event_additembtnActionPerformed

    private void priceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_priceKeyReleased

        if ("" != price.getText())
        {
            try
            {
                Double i = Double.parseDouble(price.getText());
            }
            catch (Exception e)
            {
                //JOptionPane.showMessageDialog(null, "Please Input Numbers");
                new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Input Numbers",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
                price.setText("");
            }
        }
    }//GEN-LAST:event_priceKeyReleased

    private void priceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_priceMouseClicked

        try
        {
            Runtime.getRuntime().exec("cmd /c osk");
            price.grabFocus();
        }
        catch (IOException ex)
        {
            //JOptionPane.showMessageDialog(null, "Fail to get Touch Key Board ");
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Fail to get Touch Key Board",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
        }
    }//GEN-LAST:event_priceMouseClicked

    private void discountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_discountKeyReleased

        if ("" != discount.getText())
        {
            try
            {
                Double i = Double.parseDouble(discount.getText());
                if (Double.parseDouble(discount.getText()) >= 0 && Double.parseDouble(discount.getText()) <= 100 )
                 {
                    
                 }
                else
                 {
                     JOptionPane.showMessageDialog(null, "Discount must between 0 and 100");
                     discount.setText("");
                 }
            }
            catch (Exception e)
            {
                //JOptionPane.showMessageDialog(null, "Please Input Numbers");
                new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Input Numbers",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
                discount.setText("");
            }
        }
    }//GEN-LAST:event_discountKeyReleased

    private void discountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_discountMouseClicked

        try
        {
            Runtime.getRuntime().exec("cmd /c osk");
            discount.grabFocus();
        }
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(null, "Fail to get Touch Key Board ");
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Fail to get Touch Key Board",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
        }
    }//GEN-LAST:event_discountMouseClicked

    private void nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameActionPerformed

    }//GEN-LAST:event_nameActionPerformed

    private void nameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nameMouseClicked

        try
        {
            Runtime.getRuntime().exec("cmd /c osk");
            name.grabFocus();
        }
        catch (Exception ex)
        {
            //JOptionPane.showMessageDialog(null, "Fail to get Touch Key Board ");
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Fail to get Touch Key Board",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
        }
    }//GEN-LAST:event_nameMouseClicked

    private void idnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idnoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String path = browseimg();
        showpath.setText(path);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jPanel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel15MouseClicked

        managmentbasic m = new managmentbasic();
        m.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_jPanel15MouseClicked

    private void jPanel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel16MouseClicked

        managmentemployee m = new managmentemployee();
        m.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel16MouseClicked

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

    private void jPanel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseClicked
        
        this.dispose();
        
        main m = new main();
        m.setVisible(true);
    }//GEN-LAST:event_jPanel12MouseClicked

    private void jLabel38MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel38MouseClicked
        
        this.dispose();
        
        managmentitem m = new managmentitem();
        m.setVisible(true);
        
    }//GEN-LAST:event_jLabel38MouseClicked

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
            java.util.logging.Logger.getLogger(managmentitem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(managmentitem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(managmentitem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(managmentitem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new managmentitem().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton additembtn;
    private javax.swing.JComboBox availability;
    private javax.swing.JPanel category;
    private javax.swing.JComboBox categoryshow;
    private javax.swing.JTable databaseshow;
    private javax.swing.JButton deleteitembtn;
    private javax.swing.JTextField description;
    private javax.swing.JPanel detailshowpanel;
    private javax.swing.JTextField discount;
    private javax.swing.JTextField idno;
    private javax.swing.JLabel imageshow;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel mainimg;
    private javax.swing.JTextField name;
    private javax.swing.JTextField price;
    private javax.swing.JTextField search;
    private javax.swing.JLabel searchbtn;
    private javax.swing.JLabel showemployeename;
    private javax.swing.JTextField showpath;
    private javax.swing.JLabel timeshow;
    private javax.swing.JLabel totallabel;
    private javax.swing.JLabel totallabel1;
    private javax.swing.JLabel totallabel2;
    private javax.swing.JLabel totallabel3;
    private javax.swing.JLabel totallabel4;
    private javax.swing.JLabel totallabel6;
    private javax.swing.JButton updateitembtn;
    // End of variables declaration//GEN-END:variables
}
