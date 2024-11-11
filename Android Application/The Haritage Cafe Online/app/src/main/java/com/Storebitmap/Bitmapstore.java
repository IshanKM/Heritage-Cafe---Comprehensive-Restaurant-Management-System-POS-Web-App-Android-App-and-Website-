package com.Storebitmap;

import android.graphics.Bitmap;

public class Bitmapstore
 {
     private Bitmap bitmap = null;
     private static final Bitmapstore instance = new Bitmapstore();

     public Bitmapstore()
     {

     }

     public static Bitmapstore getInstance()
      {
         return instance;
      }

     public Bitmap getBitmap()
      {
         return bitmap;
      }

     public void setBitmap(Bitmap bitmap)
      {
         this.bitmap = bitmap;
      }
 }
