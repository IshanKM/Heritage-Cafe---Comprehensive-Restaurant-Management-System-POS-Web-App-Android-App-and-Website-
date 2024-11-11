
package pos.system;

import com.sbix.jnotify.NPosition;
import com.sbix.jnotify.NotifyType;
import com.sbix.jnotify.NotifyWindow;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class paysheet extends javax.swing.JFrame {


    public paysheet() 
      {
        initComponents();
        seticon();
        
      }
    
    public void seticon()
     {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon/companylogo.png")));
     }
    
    public void createpaysheet(int id)
      {
          try 
            {
               LocalDate date = java.time.LocalDate.now();
               DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
               String name = null;
               String nic = null;
               double basic = 0;
               double othours = 0;
               double payforot = 0;
               String allowance_detail = "";
               int countabsentday = 0;
               double totalsalary = 0;
               String adminname = "";
               
               Connection con = null;
               Statement st;
               ResultSet rs;
               
               MyQuery mq = new MyQuery();
               con = mq.getConnection();
               st = con.createStatement();
               
               rs = st.executeQuery( "select Name,Nic_No from employee WHERE ID = "+id+"");
               while(rs.next())
                  {               
                            name = rs.getString("Name");
                            nic = rs.getString("Nic_No");
                  }
               
               rs = st.executeQuery( "select Basic_Salary,pay_for_OT from employee_salary WHERE Employee_ID = "+id+"");
               while(rs.next())
                    {      
                            basic = rs.getDouble("Basic_Salary");
                            payforot = rs.getDouble("pay_for_OT");
                    }
               
               rs = st.executeQuery( "select SUM(OT_Hours) from ot_hours where Employee_ID = "+id+" AND Date LIKE '"+dtf.format(date)+"%' ");
               while(rs.next())
                    {      
                            othours = rs.getDouble("SUM(OT_Hours)");
                    }
               
               rs = st.executeQuery( "select allowances_details from employee_salary WHERE Employee_ID = "+id+"");
               while(rs.next())
                    {  
                        if ("yes".equals(rs.getString("allowances_details"))) 
                         {
                              allowance_detail = "Appropriate";
                         } 
                        else if("no".equals(rs.getString("allowance_details")))
                         {
                              allowance_detail = "Inappropriate";
                         }
                           
                    }
               
               rs = st.executeQuery( "select COUNT(Employee_ID) from employee_absent_day where Employee_ID = '"+id+"' AND Date LIKE '"+dtf.format(date)+"%'");
               while(rs.next())
                     {
                        countabsentday = rs.getInt("COUNT(Employee_ID)");
                     }
               rs = st.executeQuery( "select total_Salary from employee_salary where Employee_ID = '"+id+"'");
               while(rs.next())
                     {
                        totalsalary = rs.getDouble("total_Salary");
                     }
               
               employeeUnit e = new employeeUnit();
               double allowanceall = e.getsalary(dtf.format(date), id);
                
               
               paysheet.setText
                   (
                              "                   Haritage Bakery and Cafe           " +"\n"
                              +"          61 Pedlar St, Galle 80000, Sri Lanka" +"\n"
                              +"                          091- 4200 140"+ "\n"
                              +"_____________________________________"+"\n\n"
                              +"      \t    PAY SHEET\n"
                              +"_____________________________________"+"\n"
                              +" > Date : " +date+"\n"
                              +" > Register Number : "+id+"\n"        
                              +" > Name : " +name+"\n"
                              +" > Identy Number : " +nic+"\n" 
                              +"_____________________________________"+"\n"
                              +"\t     Basic Salary \n"
                              +"\t       "+basic+"\n\n" 
                              +" Pay for a OT Hours    OT Hours       Total OT \n"
                              +"           "+payforot+" \t                 "+othours+"                " +othours *payforot
                              +"\n\n Allowance       Total Alowance    Absent Days "
                              + allowance_detail+"            "+allowanceall+" \t                "+countabsentday
                              +"\n_____________________________________"+"\n"
                              +"                      Total Salary : "+totalsalary
                              +"\n_____________________________________"+""
                   );
             
               
               loginpage lp = new loginpage();
               String[] details = lp.getdetails();           
               String uname = details[0];
               String pwd = details[1];
               
               rs = st.executeQuery( "select Name from employee where User_Name='"+uname+"' and Password='"+pwd+"'");
               while(rs.next())
                  {
                     adminname = rs.getString("Name"); 

                  }
               
               PreparedStatement  ps = con.prepareStatement("INSERT INTO salary_issue VALUES (?,?,?,?,?)");
               ps.setInt(1, 0);
               ps.setInt(2, id);
               ps.setString(3, date.toString());
               ps.setDouble(4, totalsalary);
               ps.setString(5, adminname);
               ps.executeUpdate();
               
               paysheet.print();
               
            } 
          
          catch (Exception e)
            {
                
                new NotifyWindow(NotifyType.ERROR_NOTIFICATION,"Database Connection Fail...!",NotifyWindow.LONG_DELAY,NPosition.BOTTOM_RIGHT);
            }
          
               
      }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        paysheet = new javax.swing.JEditorPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        paysheet.setEditable(false);
        paysheet.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jScrollPane1.setViewportView(paysheet);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(518, 747));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


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
            java.util.logging.Logger.getLogger(paysheet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(paysheet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(paysheet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(paysheet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new paysheet().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JEditorPane paysheet;
    // End of variables declaration//GEN-END:variables
}
