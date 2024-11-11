package com.iksoft.recyclview;

import android.graphics.Bitmap;

public class ListItem
 {
        private String head;
        private String desc;
        private Bitmap img;
        private double price;
        private double discount;
        private int qty;

     public ListItem(String head, String desc,Bitmap img,double price,double discount,int qty)
      {
         this.head = head;
         this.desc = desc;
         this.img = img;
         this.price = price;
         this.discount = discount;
         this.qty = qty;
      }

     public String getHead()
      {
         return head;
      }

     public String getDesc()
      {
         return desc;
      }

     public Bitmap getImg()
     {
         return img;
     }

     public int getQty()
     {
         return qty;
     }

     public void setPrice(double price) {
         this.price = price;
     }

     public double getPrice()
     {
         return price;
     }

     public double getDiscount()
     {
         return discount;
     }
 }
