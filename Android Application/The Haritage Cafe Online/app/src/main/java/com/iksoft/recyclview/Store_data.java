package com.iksoft.recyclview;

import android.content.Context;
import android.content.SharedPreferences;

//This get from https://developer.android.com/training/volley/requestqueue#java web site
//This use for Use a singleton pattern

public class Store_data
{
    private static Store_data instance;
    private static Context ctx;

    private static final String SHERED_PREF_NAME = "myloginsherepf";
    private static final String KEY_EMAIL = "useremail";
    private static final String KEY_ID = "userid";
    private static final String KEY_Mobile_Number = "Mobile_Number";
    private static final String KEY_NAME = "username";
    private static final String KEY_ADDRESS = "useraddress";

    Store_data(Context context)
     {
        ctx = context;
     }

    public static synchronized Store_data getInstance(Context context)
     {
        if (instance == null)
         {
            instance = new Store_data(context);
         }
        return instance;
     }

    public boolean userlogin(int id,String name,String email,String Mobile_Number,String address)
     {
         SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHERED_PREF_NAME,Context.MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedPreferences.edit();

         editor.putInt(KEY_ID,id);
         editor.putString(KEY_EMAIL,email);
         editor.putString(KEY_Mobile_Number,Mobile_Number);
         editor.putString(KEY_NAME,name);
         editor.putString(KEY_ADDRESS,address);

         editor.apply();

         return true;
     }

    public boolean islogin()
     {
         SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHERED_PREF_NAME,Context.MODE_PRIVATE);
         if (sharedPreferences.getString(KEY_EMAIL,null ) != null)
          {
              return true;
          }

         return false;
     }

    public boolean logout()
     {
         SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHERED_PREF_NAME,Context.MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedPreferences.edit();
         editor.clear();
         editor.apply();

         return true;
     }

     public int getid()
      {
          SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHERED_PREF_NAME,Context.MODE_PRIVATE);
          return sharedPreferences.getInt(KEY_ID,0);
      }

    public String getemail()
     {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHERED_PREF_NAME,Context.MODE_PRIVATE);
         return sharedPreferences.getString(KEY_EMAIL,null);
     }

    public String getMobilenumber()
     {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHERED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Mobile_Number,null);
     }

    public String getname()
     {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHERED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME,null);
     }

    public String getaddress()
     {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHERED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ADDRESS,null);
     }

}
