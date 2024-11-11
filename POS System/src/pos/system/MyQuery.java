package pos.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.Query;
import javax.swing.JOptionPane;


public class MyQuery
{
    
   public Connection getConnection(){
        Connection con = null;
        try 
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/the_haritage_cafe", "root", "");
           
        } 
        catch (Exception ex) 
        {
             System.out.println(ex);
        }
        return con;
    }
   
   //billmanuall codes
    
   public ArrayList<getdetails> breakfast()
   {
        
   ArrayList<getdetails> list = new ArrayList<getdetails>();
   Connection con = getConnection();
   Statement st;
   ResultSet rs;
   
   try {
   st = con.createStatement();
   rs = st.executeQuery("SELECT id,Name, Price, imageoriginal, Discount  FROM items WHERE Category = 'breakfast' ORDER BY Price ASC");
   
   getdetails p;
   while(rs.next()){
   p = new getdetails(
   rs.getInt("id"),
   rs.getString("Name"),
   rs.getDouble("Price"),
   rs.getBytes("imageoriginal"),
   rs.getDouble("Discount")
   );
   list.add(p);
   }
   
   } 
   catch (Exception ex) 
   {
       
   }
   return list;
   }
   
    public ArrayList<getdetails> pizza()
    {
        
   ArrayList<getdetails> list = new ArrayList<getdetails>();
   Connection con = getConnection();
   Statement st;
   ResultSet rs;
   
   try {
   st = con.createStatement();
   rs = st.executeQuery("SELECT id,Name, Price, imageoriginal, Discount  FROM items WHERE Category = 'pizza' ORDER BY Price ASC");
   
   getdetails p;
   while(rs.next()){
   p = new getdetails(
   rs.getInt("id"),
   rs.getString("Name"),
   rs.getDouble("Price"),
   rs.getBytes("imageoriginal"),
   rs.getDouble("Discount")
   );
   list.add(p);
   }
   
   }
   catch (Exception ex)
   {
  
   }
   return list;
   }
    
   public ArrayList<getdetails> pasta()
   {
        
   ArrayList<getdetails> list = new ArrayList<getdetails>();
   Connection con = getConnection();
   Statement st;
   ResultSet rs;
   
   try {
   st = con.createStatement();
   rs = st.executeQuery("SELECT id,Name, Price, imageoriginal, Discount  FROM items WHERE Category = 'pasta' ORDER BY Price ASC");
   
   getdetails p;
   while(rs.next()){
   p = new getdetails(
   rs.getInt("id"),
   rs.getString("Name"),
   rs.getDouble("Price"),
   rs.getBytes("imageoriginal"),
   rs.getDouble("Discount")
   );
   list.add(p);
   }
   
   } 
   catch (Exception ex) 
   {

   }
   return list;
   }
   
   public ArrayList<getdetails> soup()
   {
        
   ArrayList<getdetails> list = new ArrayList<getdetails>();
   Connection con = getConnection();
   Statement st;
   ResultSet rs;
   
   try {
   st = con.createStatement();
   rs = st.executeQuery("SELECT id,Name, Price, imageoriginal, Discount  FROM items WHERE Category = 'soups' ORDER BY Price ASC");
   
   getdetails p;
   while(rs.next()){
   p = new getdetails(
   rs.getInt("id"),
   rs.getString("Name"),
   rs.getDouble("Price"),
   rs.getBytes("imageoriginal"),
   rs.getDouble("Discount")
   );
   list.add(p);
   }
   
   } 
   catch (Exception ex)
   {

   }
   return list;
   }
    
   public ArrayList<getdetails> sandwich(){
        
   ArrayList<getdetails> list = new ArrayList<getdetails>();
   Connection con = getConnection();
   Statement st;
   ResultSet rs;
   
   try {
   st = con.createStatement();
   rs = st.executeQuery("SELECT id,Name, Price, imageoriginal, Discount  FROM items WHERE Category = 'sandwich' ORDER BY Price ASC");;
   
   getdetails p;
   while(rs.next()){
   p = new getdetails(
   rs.getInt("id"),
   rs.getString("Name"),
   rs.getDouble("Price"),
   rs.getBytes("imageoriginal"),
   rs.getDouble("Discount")
   );
   list.add(p);
   }
   
   } 
   catch (Exception ex) 
   {

   }
   return list;
   }
   
    public ArrayList<getdetails> desert(){
        
   ArrayList<getdetails> list = new ArrayList<getdetails>();
   Connection con = getConnection();
   Statement st;
   ResultSet rs;
   
   try {
   st = con.createStatement();
   rs = st.executeQuery("SELECT id,Name, Price, imageoriginal, Discount  FROM items WHERE Category = 'deserts' ORDER BY Price ASC");
   
   getdetails p;
   while(rs.next()){
   p = new getdetails(
    rs.getInt("id"),
   rs.getString("Name"),
   rs.getDouble("Price"),
   rs.getBytes("imageoriginal"),
   rs.getDouble("Discount")
   );
   list.add(p);
   }
   
   } 
   catch (Exception ex) 
   {
  
   }
   return list;
   }
   
    public ArrayList<getdetails> drink()
    {
        
   ArrayList<getdetails> list = new ArrayList<getdetails>();
   Connection con = getConnection();
   Statement st;
   ResultSet rs;
   
   try {
   st = con.createStatement();
   rs = st.executeQuery("SELECT id,Name, Price, imageoriginal, Discount  FROM items WHERE Category = 'drink' ORDER BY Price ASC");
   
   getdetails p;
   while(rs.next()){
   p = new getdetails(
   rs.getInt("id"),
   rs.getString("Name"),
   rs.getDouble("Price"),
   rs.getBytes("imageoriginal"),
   rs.getDouble("Discount")
   );
   list.add(p);
   }
   
   }
   catch (SQLException ex)
   {
  
   }
   return list;
   }
    
   public ArrayList<getdetails> search(String search){
        
   ArrayList<getdetails> list = new ArrayList<getdetails>();
   Connection con = getConnection();
   Statement st;
   ResultSet rs;
   
   String searchfood = search;
   
   try {
   st = con.createStatement();
   rs = st.executeQuery("SELECT id,`Name`, `Price`, `imageoriginal`, `Discount`  FROM `items` WHERE Name LIKE '%"+searchfood+"%'");

   getdetails p;
   while(rs.next()){
   p = new getdetails(
   rs.getInt("id"),
   rs.getString("Name"),
   rs.getDouble("Price"),
   rs.getBytes("imageoriginal"),
   rs.getDouble("Discount")
   );
   list.add(p);
   }
   
   } 
   catch (SQLException ex)
   {
  
   }
   return list;
   }
   
   public String getfinalid() throws SQLException
      {
        
        Connection con = getConnection();
        Statement st;
        ResultSet rs;
        String id = null;
   
        st = con.createStatement();
        rs = st.executeQuery( "SELECT ID FROM all_orders order by ID desc limit 1");
        while(rs.next())
            {
              id = rs.getString("ID");
            }
         
          
         return id;
         
      }
   
   public String getfinalorderid() throws SQLException
      {
        
        Connection con = getConnection();
        Statement st;
        ResultSet rs;
        String id = null;
   
        st = con.createStatement();
        rs = st.executeQuery( "SELECT order_ID FROM all_orders WHERE order_ID IS NOT null order by order_ID desc limit 1");
        while(rs.next())
            {
              id = rs.getString("order_ID");
            }
         
          
         return id;
         
      }
   
   public int getidemployee(String username,String password) throws SQLException
        {
            
          Connection con = getConnection();
          Statement st;
          ResultSet rs;
          int id = 0;
          
          st = con.createStatement();
          rs = st.executeQuery( "select ID from employee where User_Name='"+username+"' and Password='"+password+"'");
          while(rs.next())
            {
              id = rs.getInt("ID"); 
            }
          
          
          return id;

        }
   
   public String getnameemployee(String username,String password) throws SQLException
        {
            
          Connection con = getConnection();
          Statement st;
          ResultSet rs;
          String name = "";
          
          st = con.createStatement();
          rs = st.executeQuery( "select Name from employee where User_Name='"+username+"' and Password='"+password+"'");
          while(rs.next())
            {
              name = rs.getString("Name"); 
            }
          
          
          return name;

        }
   
   //billauto codes
   
   public ArrayList<getdetails> getiddetails(String ids)
    {
        
        ArrayList<getdetails> list = new ArrayList<getdetails>();
        Connection con = getConnection();
        Statement st;
        ResultSet rs;

        try {
        st = con.createStatement();
        rs = st.executeQuery("SELECT id,Name,Price,imageoriginal,Discount FROM items WHERE id IN ("+ids+")");

        getdetails p;
        while(rs.next()){
        p = new getdetails(
        rs.getInt("id"),
        rs.getString("Name"),
        rs.getDouble("Price"),
        rs.getBytes("imageoriginal"),
        rs.getDouble("Discount")
        );
        list.add(p);
        }

        } catch (SQLException ex) {
        Logger.getLogger(MyQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
   
  
   
    public String getfinalidreject() throws SQLException
      {
        
        Connection con = getConnection();
        Statement st;
        ResultSet rs;
        String id = null;
   
        st = con.createStatement();
        rs = st.executeQuery( "SELECT ID FROM rejected_order order by ID desc limit 1");
        while(rs.next())
            {
              id = rs.getString("ID");
            }
         
          
         return id;
         
      }
   
    //reoport codes
    
    //daily Summury
    
     public double gettotalpriceinday(String date) throws SQLException
      {
        
        Connection con = getConnection();
        Statement st;
        ResultSet rs;
        double total = 0 ;
   
        st = con.createStatement();
        rs = st.executeQuery( "SELECT SUM(total) FROM all_orders WHERE date = '"+date+"'");
        while(rs.next())
            {
              total = rs.getDouble("SUM(total)");
            }
         
          
         return total;
         
      }
   
    public double getcardpriceinday(String date) throws SQLException
      {
        
        Connection con = getConnection();
        Statement st;
        ResultSet rs;
        double total = 0 ;
   
        st = con.createStatement();
        rs = st.executeQuery( "SELECT SUM(total) FROM all_orders WHERE payment_methord = 'Cash Payment' AND date = '"+date+"'");
        while(rs.next())
            {
              total = rs.getDouble("SUM(total)");
            }
         
          
         return total;
         
      }
    
    public double getcashpriceinday(String date) throws SQLException
      {
        
        Connection con = getConnection();
        Statement st;
        ResultSet rs;
        double total = 0 ;
   
        st = con.createStatement();
        rs = st.executeQuery( "SELECT SUM(total) FROM all_orders WHERE payment_methord = 'Card Payment' AND date = '"+date+"'");
        while(rs.next())
            {
              total = rs.getDouble("SUM(total)");
            }
         
          
         return total;
         
      }
    
    public double getonlineorderprice(String date) throws SQLException
      {
        
        Connection con = getConnection();
        Statement st;
        ResultSet rs;
        double total = 0 ;
   
        st = con.createStatement();
        rs = st.executeQuery( "SELECT SUM(total) FROM all_orders WHERE payment_methord = 'Online Order' AND date = '"+date+"'");
        while(rs.next())
            {
              total = rs.getDouble("SUM(total)");
            }
         
          
         return total;
         
      }
    
    public int getnoofbill(String date) throws SQLException
      {
        
        Connection con = getConnection();
        Statement st;
        ResultSet rs;
        int nobill = 0 ;
   
        st = con.createStatement();
        rs = st.executeQuery( "SELECT COUNT(order_ID) FROM all_orders WHERE order_ID IS NOT null AND date = '"+date+"'");
        while(rs.next())
            {
              nobill = rs.getInt("COUNT(order_ID)");
            }
         
          
         return nobill;
         
      }
 
    
}