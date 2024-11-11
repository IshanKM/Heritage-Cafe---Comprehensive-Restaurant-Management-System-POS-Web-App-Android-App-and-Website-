
package pos.system;

public class getdetails
{
    
    private int id;
    private String name;
    private Double price;
    private byte[] Image;
    private Double Discount;
   
    
    public getdetails()
    {
        
    }
    
    public getdetails(int id,String Name,Double Price,byte[] image, Double Discount)
    {
        
        this.id = id;
        this.name = Name;
        this.price = Price;
        this.Image = image;
        this.Discount = Discount;
       
    }
    
    

    public void setId(Double Discount)
     {
        this.id = id;
     }
    
     public int getID()
     {
        return id;
     }
    
    public String getName()
     {
        return name;
     }
    
    public void setName(String Name)
     {
        this.name = Name;
     }
    

    public Double getPrice()
     {
        return price;
     }
     
    public void setPrice(Double Price)
     {
        this.price = Price;
     }
    
    public byte[] getMyImage()
     {
        return Image;
     }
    
    public Double getDiscount()
     {
        return Discount;
     }
    
    public void setDiscount(Double Discount)
     {
        this.Discount = Discount;
     }
    
   
} 
