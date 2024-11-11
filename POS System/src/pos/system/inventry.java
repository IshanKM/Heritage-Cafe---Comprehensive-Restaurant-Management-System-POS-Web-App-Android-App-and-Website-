
package pos.system;

import com.sbix.jnotify.NPosition;
import com.sbix.jnotify.NotifyType;
import com.sbix.jnotify.NotifyWindow;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import net.proteanit.sql.DbUtils;

public class inventry extends javax.swing.JFrame {

    public inventry()
    {
        initComponents();
        showinventry();
        check();
        decorate();
        seticon();
    }
    
    public void seticon()
     {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon/companylogo.png")));
     }
    
    public void showinventry()
        {

                            try
                                {
                                    MyQuery m = new MyQuery();
                                    Connection con = m.getConnection();
                                    Statement st; 
                                    ResultSet rs;
                                    
                                    st = con.createStatement();
                                    rs = st.executeQuery("SELECT * FROM store");
                                    showinventry.setModel(DbUtils.resultSetToTableModel(rs));

                                        
                                }
                            catch(Exception e)
                                {
                                    new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
                                }

        }
    
    public void decorate()
        {
 
            JTableHeader addtocartheader = showinventry.getTableHeader();
            addtocartheader.setFont(new Font("Times New Roman", Font.BOLD,14));
            ((DefaultTableCellRenderer)addtocartheader.getDefaultRenderer())
                    .setHorizontalAlignment(JLabel.CENTER);
            
            showinventry.setRowHeight(50);

        }
    
    public void check()
        {
                try
                   {

                     Connection con = null;
                     PreparedStatement pst = null;
                     ResultSet rs = null;

                     loginpage lp = new loginpage();
                     String details[] = lp.getdetails();
                     String username = details[0];
                     String password = details[1];
                     
                     MyQuery m = new MyQuery();
                     int idemployee = m.getidemployee(username , password);
                     String Cat1 = "Admin";
                     String Cat2 = "Chef";


                     con = m.getConnection();
                     String query = "select * from employee WHERE ID =? AND Category =? OR Category =?";
                     pst = con.prepareStatement(query);
                     pst.setInt(1, idemployee);
                     pst.setString(2, Cat1);
                     pst.setString(3, Cat2);
      
                     rs = pst.executeQuery();

                     if(rs.next())
                        {

                           
                        }
                     else
                        {
                           name.hide();
                           id.hide();
                           sid.hide();
                           eid.hide();
                           qtyall.hide();
                        } 

                        

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
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        note = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        detailshow = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        sid = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        id = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        qtyall = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        eid = new javax.swing.JTextField();
        add = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        update = new javax.swing.JButton();
        clearall = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        showinventry = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        search = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/inventary.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tempus Sans ITC", 3, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setText("INVENTORY PAGE");

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

        note.setFont(new java.awt.Font("Tekton Pro", 1, 18)); // NOI18N
        note.setForeground(new java.awt.Color(255, 0, 0));
        note.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        note.setText("Note : Only Admins And Chefs Can Add,Update And Delete Items From Inventery");

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(note, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(note, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        detailshow.setBackground(new java.awt.Color(255, 0, 0));

        jLabel2.setFont(new java.awt.Font("Stencil Std", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("ADD,EDIT & DELETE");
        jLabel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("LCE ID");

        sid.setBackground(new java.awt.Color(255, 0, 0));
        sid.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        sid.setForeground(new java.awt.Color(255, 255, 255));
        sid.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Item_Name");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Supplier ID");

        name.setBackground(new java.awt.Color(255, 0, 0));
        name.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        name.setForeground(new java.awt.Color(255, 255, 255));
        name.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        name.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));

        id.setEditable(false);
        id.setBackground(new java.awt.Color(255, 0, 0));
        id.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        id.setForeground(new java.awt.Color(255, 255, 255));
        id.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        id.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Quantity");

        qtyall.setBackground(new java.awt.Color(255, 0, 0));
        qtyall.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        qtyall.setForeground(new java.awt.Color(255, 255, 255));
        qtyall.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        qtyall.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("ID Of Item");

        eid.setEditable(false);
        eid.setBackground(new java.awt.Color(255, 0, 0));
        eid.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        eid.setForeground(new java.awt.Color(255, 255, 255));
        eid.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        eid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));

        add.setBackground(new java.awt.Color(0, 153, 102));
        add.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        add.setForeground(new java.awt.Color(51, 204, 0));
        add.setText("ADD");
        add.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 204, 0), 3, true));
        add.setContentAreaFilled(false);
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        delete.setBackground(new java.awt.Color(255, 0, 0));
        delete.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        delete.setForeground(new java.awt.Color(255, 255, 255));
        delete.setText("DELETE");
        delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        delete.setContentAreaFilled(false);
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        update.setBackground(new java.awt.Color(0, 51, 204));
        update.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        update.setForeground(new java.awt.Color(0, 51, 204));
        update.setText("UPDATE");
        update.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 204), 3, true));
        update.setContentAreaFilled(false);
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        clearall.setBackground(new java.awt.Color(255, 255, 255));
        clearall.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        clearall.setText("Clear All");
        clearall.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));
        clearall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearallActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout detailshowLayout = new javax.swing.GroupLayout(detailshow);
        detailshow.setLayout(detailshowLayout);
        detailshowLayout.setHorizontalGroup(
            detailshowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detailshowLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detailshowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(clearall, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(detailshowLayout.createSequentialGroup()
                        .addGroup(detailshowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(detailshowLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(add, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(update, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(name, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(detailshowLayout.createSequentialGroup()
                                .addGroup(detailshowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(detailshowLayout.createSequentialGroup()
                                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(14, 14, 14)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(detailshowLayout.createSequentialGroup()
                                        .addComponent(id)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(sid)))
                                .addGroup(detailshowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(detailshowLayout.createSequentialGroup()
                                        .addGap(5, 5, 5)
                                        .addComponent(eid))
                                    .addGroup(detailshowLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addComponent(qtyall, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, 0)
                        .addComponent(jLabel7)))
                .addContainerGap())
        );
        detailshowLayout.setVerticalGroup(
            detailshowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailshowLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addGroup(detailshowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(detailshowLayout.createSequentialGroup()
                        .addGap(243, 243, 243)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(detailshowLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                        .addGap(2, 2, 2)
                        .addComponent(name, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                        .addGap(14, 14, 14)
                        .addGroup(detailshowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(detailshowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(detailshowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(sid, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                                .addComponent(eid))
                            .addComponent(id))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(qtyall, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)))
                .addGap(29, 29, 29)
                .addGroup(detailshowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(add, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(update, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(clearall, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(0, 153, 102));

        showinventry = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex,int colIndex)
            {
                return false;
            }
        };
        showinventry.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        showinventry.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Date", "Item_Name", "QTy", "Supplier_ID", "Last_Change_Emp"
            }
        ));
        showinventry.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showinventryMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(showinventry);

        jLabel1.setFont(new java.awt.Font("Stencil Std", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("INVENTARY");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));

        search.setBackground(new java.awt.Color(0, 153, 102));
        search.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        search.setForeground(new java.awt.Color(255, 255, 255));
        search.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        search.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(detailshow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(detailshow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        setSize(new java.awt.Dimension(1234, 621));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked

        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked

        int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
        if (confirm == 0)
        {
            dispose();
        }
    }//GEN-LAST:event_jLabel8MouseClicked

    private void clearallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearallActionPerformed
       
        name.setText("");
        id.setText("");
        sid.setText("");
        eid.setText("");
        qtyall.setText("");
    }//GEN-LAST:event_clearallActionPerformed

    private void showinventryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showinventryMouseClicked
       
        int row = showinventry.getSelectedRow();
        

        
        name.setText(showinventry.getModel().getValueAt(row, 2 ).toString());
        id.setText(showinventry.getModel().getValueAt(row, 0 ).toString());
        sid.setText(showinventry.getModel().getValueAt(row, 4 ).toString());
        eid.setText(showinventry.getModel().getValueAt(row, 5 ).toString());
        qtyall.setText(showinventry.getModel().getValueAt(row, 3 ).toString());
        
        
        
    }//GEN-LAST:event_showinventryMouseClicked

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        
        try
         {
            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            loginpage lp = new loginpage();
            String details[] = lp.getdetails();
            String username = details[0];
            String password = details[1];
            MyQuery m = new MyQuery();
            int idemployee = m.getidemployee(username , password);

            if (!"".equals(name.getText()) & !"".equals(sid.getText()) & !"".equals(qtyall.getText())) 
               {
                  int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
                    if (confirm == 0)
                       {
                            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/the_haritage_cafe", "root", "");
                            String query = "select * from store WHERE Item_Name =? ";
                            ps = con.prepareStatement(query);
                            ps.setString(1, name.getText());
                            rs = ps.executeQuery();

                               if(rs.next())
                                   {
                                      new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Already Have This Item t",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                                   }
                                else
                                   {
                                       ps = con.prepareStatement("INSERT INTO store VALUES (?,?,?,?,?,?)");
                                       ps.setInt(1, 0);
                                       ps.setString(2,java.time.LocalDate.now().toString());
                                       ps.setString(3, name.getText());
                                       ps.setDouble(4, Double.parseDouble(qtyall.getText()));
                                       ps.setInt(5, Integer.parseInt(sid.getText()));
                                       ps.setInt(6, idemployee);

                                       ps.executeUpdate();
                                       //JOptionPane.showMessageDialog(null,"Insert Successfully...!");
                                       new NotifyWindow(NotifyType.SUCCESS_NOTIFICATION,"Insert Successfully...!",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);

                                       showinventry();
                                   } 
                       }
                    else
                       {
                             new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Select the Row You Want To Delete",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                       }
                  }
              else
                {
                    new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Fill Item Name,Qty,Supplier Id Fileds",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                }
            }
           
        catch(Exception e)
            {
               new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
            }   
    }//GEN-LAST:event_addActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
      
        int row = showinventry.getSelectedRow();
        
        if (row >= 0) 
        {
            int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
            if (confirm == 0)
            {
                try
                 {
                    //delete from invrentry table
                    MyQuery m = new MyQuery();
                    Connection con = m.getConnection();
                    
                    String delete = showinventry.getModel().getValueAt(row,0).toString();
                    PreparedStatement ps = con.prepareStatement("DELETE FROM store WHERE ID = '"+delete+"'");
                    ps.executeUpdate();
                    
                    //JOptionPane.showMessageDialog(this,"Successfully Deleted");
                    new NotifyWindow(NotifyType.SUCCESS_NOTIFICATION,"Successfully Deleted",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
                    showinventry();
                    
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
            
    }//GEN-LAST:event_deleteActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
       
        int row = showinventry.getSelectedRow();
        if (row >= 0) 
        {
            int confirm = JOptionPane.showConfirmDialog(null,"Are You Sure ?","Confirm",JOptionPane.YES_NO_OPTION);
            if (confirm == 0)
            {
                try
                  {   
                    Statement st = null; 
                    ResultSet rs;

                    loginpage lp = new loginpage();
                    String details[] = lp.getdetails();
                    String username = details[0];
                    String password = details[1];
                    MyQuery m = new MyQuery();
                    int idemployee = m.getidemployee(username , password);

                    String getname = name.getText();
                    int getid = Integer.parseInt(id.getText());
                    int getsid = Integer.parseInt(sid.getText());
                    double getqty = Double.parseDouble(qtyall.getText());
                    LocalDate date = java.time.LocalDate.now();

                    //update from invrentry table

                    Connection con = m.getConnection();
                    PreparedStatement ps = con.prepareStatement("UPDATE store SET Date = '"+date.toString()+"',Item_Name = '"+getname+"',QTY = '"+getqty+"', supplier_ID = "+getsid+", Last_ChangerEmp = "+idemployee+" WHERE ID ="+getid+"");

                    ps.executeUpdate();
                    //JOptionPane.showMessageDialog(null,"Update Successfully...!"); 
                    new NotifyWindow(NotifyType.SUCCESS_NOTIFICATION,"Update Successfully...!",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);

                    showinventry();

                   }
                catch(Exception e)
                   {
                         new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT); 
                   }   
            }
        }
        else
        {
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Please Select the Row You Want To Delete",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
        }
        
        
    }//GEN-LAST:event_updateActionPerformed

    private void searchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchMouseClicked

        try
        {
            search.setText("");
            Runtime.getRuntime().exec("cmd /c osk");
            search.grabFocus();
        }
        catch (IOException ex)
        {
           new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Fail to get Touch Key Board ",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
        }
    }//GEN-LAST:event_searchMouseClicked

    private void searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyReleased
       
        
                            try
                                {

                                    MyQuery m = new MyQuery();
                                    Connection con = m.getConnection();
                                    Statement st; 
                                    ResultSet rs;
                                    
                                    String searchdetails = search.getText();
                                    st = con.createStatement();
                                    rs = st.executeQuery("SELECT * FROM store WHERE Item_Name LIKE '%"+searchdetails+"%'");
                                    showinventry.setModel(DbUtils.resultSetToTableModel(rs));

                                        
                                }
                            catch(Exception e)
                                {
                                   new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
                                }
    }//GEN-LAST:event_searchKeyReleased

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked

        try
        {
            this.dispose();

            inventry b = new inventry();
            b.setVisible(true);
        }
        catch (Exception ex)
        {
            new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail..!",NotifyWindow.SHORT_DELAY,NPosition.BOTTOM_RIGHT);
        }
    }//GEN-LAST:event_jLabel12MouseClicked

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
            java.util.logging.Logger.getLogger(inventry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(inventry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(inventry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(inventry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new inventry().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JButton clearall;
    private javax.swing.JButton delete;
    private javax.swing.JPanel detailshow;
    private javax.swing.JTextField eid;
    private javax.swing.JTextField id;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField name;
    private javax.swing.JLabel note;
    private javax.swing.JTextField qtyall;
    private javax.swing.JTextField search;
    private javax.swing.JTable showinventry;
    private javax.swing.JTextField sid;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables
}
