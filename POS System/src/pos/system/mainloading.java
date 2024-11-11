
package pos.system;

import java.awt.Color;
import java.awt.Toolkit;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;


public class mainloading extends javax.swing.JFrame {

    
    public mainloading() 
    {
        initComponents();
        setBackground(new Color(0,0,0,0));
        mainpanel.setBackground(new Color(0,0,0,0));
        loading.setBackground(new Color(0,0,0,0));
        loadingshow();
        seticon();
    }
    
    public void seticon()
     {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon/companylogo.png")));
     }

     public void loadingshow()
      {
        Thread th = new Thread()
                {
                    @Override
                    public void run()
                        {
                            try
                             {
                    
                                for (int i = 0; i <= 101; i++) 
                                  {
                                      if (i == 20 ) 
                                       {
                                          sleep(500);
                                          continue;
                                       }
                                      if (i == 50  ) 
                                       {
                                          sleep(700);
                                          continue;
                                       }
    
                                      loading.setValue(i);
                                      sleep(30);
                                      
                                  }
                                
                                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/the_haritage_cafe", "root", "");
                                dispose();
                                loginpage l = new loginpage();
                                l.setVisible(true);
                              }
                            catch(Exception e)
                              {
                                  //JOptionPane.showMessageDialog(null,e);
                                  end();
                              }
                        }
                };
        th.start();
    }
     
    public void end()
     {
         dispose();  
         nointernet n = new nointernet();
         n.setVisible(true);
        
     }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainpanel = new javax.swing.JPanel();
        loading = new javax.swing.JProgressBar();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        mainpanel.setLayout(null);

        loading.setBackground(new java.awt.Color(0, 0, 0));
        loading.setForeground(new java.awt.Color(255, 255, 255));
        loading.setBorderPainted(false);
        mainpanel.add(loading);
        loading.setBounds(0, 420, 680, 5);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/mainloadingimg.jpg"))); // NOI18N
        mainpanel.add(jLabel2);
        jLabel2.setBounds(0, 0, 680, 430);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainpanel, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainpanel, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) 
    {

        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run() {
                new mainloading().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JProgressBar loading;
    private javax.swing.JPanel mainpanel;
    // End of variables declaration//GEN-END:variables
}
