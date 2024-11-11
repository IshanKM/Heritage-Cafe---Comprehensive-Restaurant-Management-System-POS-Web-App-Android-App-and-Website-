package com.iksoft.recyclview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Storebitmap.Bitmapstore;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Qtydetails extends AppCompatActivity
{

    ElegantNumberButton quentity;
    Button submit;
    TextView priceshow;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qtydetails);

        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);

        int width = display.widthPixels;
        int height = display.heightPixels;

        getWindow().setLayout((int)(width*.7),(int)(height*.5));

        quentity = (ElegantNumberButton) findViewById(R.id.quentity);
        submit = findViewById(R.id.submit);
        priceshow = findViewById(R.id.priceshow);

        Intent intent = getIntent();
        final String getname = intent.getStringExtra("name");
        final double getprice= intent.getDoubleExtra("price",0);

        priceshow.setText("Rs. " +Double.toString(getprice));

        quentity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener()
        {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue)
            {
                priceshow.setText("Rs. " +Double.toString(Integer.parseInt(quentity.getNumber()) * getprice));
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                int qty = Integer.parseInt(quentity.getNumber());
                addtocarttable( getname, getprice*qty, qty, Bitmapstore.getInstance().getBitmap());


            }
        });

    }

    private void addtocarttable(final String name, final double price, final int qty, Bitmap img)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        final String encodeimg = Base64.encodeToString(b,Base64.DEFAULT);

        //Basic imei_number = new Basic();
        //final String imei = imei_number.getDeviceIMEI(getApplicationContext());

        String url = Important.getMain_Url() + "storeaddtocart.php";
        StringRequest  stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
        public void onResponse(String response)
            {
               try
                  {
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        finish();
                  }
                catch (JSONException e)
                  {
                        e.printStackTrace();
                  }

            }
        },
        new Response.ErrorListener()
             {
                        @Override
                  public void onErrorResponse(VolleyError error)
                     {
                         Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                     }
             }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError
                {
                    Basic b = new Basic();

                    Map<String, String> adddata = new HashMap<>();
                    adddata.put("IMEI_Number", b.getimeinumber(getApplicationContext()));
                    adddata.put("Name", name);
                    adddata.put("Price", Double.toString(price));
                    adddata.put("Qty", Integer.toString(qty));
                    adddata.put("image", encodeimg);

                    return adddata;
                }
            };

            RequestHandler .getInstance(this).addToRequestQueue(stringRequest);
    }

}
