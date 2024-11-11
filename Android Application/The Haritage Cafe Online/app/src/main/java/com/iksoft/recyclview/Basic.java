package com.iksoft.recyclview;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class Basic
{
    double deliver = 0;


    public String getimeinumber(Context context)
    {
        //*****
       // ***** Below code not working becourse this application run on emulator.bot physical device but this work on smart device*******
       // *****

       //TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
       // String imei = telephonyManager.getDeviceId();

        String imeino = "0000000";
        return imeino;
    }

    public double getdeliverprice()
    {

        return deliver;
    }


}
