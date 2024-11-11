package com.iksoft.recyclview;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Confirm extends AppCompatActivity {

    Button confirm,cancel;
    ProgressDialog progressDialog;
    TextView showmsg,price;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);

        int width = display.widthPixels;
        int height = display.heightPixels;

        getWindow().setLayout((int)(width),(int)(height*.4));

        confirm = findViewById(R.id.confirm);
        cancel = findViewById(R.id.cancel);
        showmsg = findViewById(R.id.showmsg);
        price = findViewById(R.id.price);;
        progressDialog = new ProgressDialog(this);

        Intent intent = getIntent();
        final String getids = intent.getStringExtra("itemides");
        final String getqtys= intent.getStringExtra("qty");
        final String gettotal= intent.getStringExtra("total");

        getdeliveryprice();
        
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendonlineorder(getids,getqtys,gettotal);
                deleteallcartitem();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void sendonlineorder(final String getids , final String getqtys , final String gettotal)
    {

        progressDialog.setMessage("Sending....!");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Important.getMain_Url() + "storeonlineorder.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(Confirm.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), Mainscreen.class));
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(Confirm.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> adddata = new HashMap<>();

                Store_data s = new Store_data(getApplicationContext());
                Basic b = new Basic();
                adddata.put("cid", Integer.toString(s.getid()));
                adddata.put("cname", s.getname());
                adddata.put("caddress", s.getaddress());
                adddata.put("cmobilenumber", s.getMobilenumber());
                adddata.put("cemail", s.getemail());
                adddata.put("orderids", getids);
                adddata.put("orderqtys", getqtys);
                adddata.put("total", gettotal);
                adddata.put("Delivery_Charge", price.getText().toString());
                return adddata;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }


    public void getdeliveryprice()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,Important.getMain_Url() +"getdeliveryprice.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    double deliver = jsonObject.getDouble("deliveryprice");
                    price.setText(Double.toString(deliver));
                }
                catch (JSONException e)
                {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    System.out.println(e.getMessage());
                }

            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(), "Database Connection Fail..", Toast.LENGTH_LONG).show();
                    }
                });

        //RequestQueue requestQueue = Volley.newRequestQueue(this);
        //requestQueue.add(stringRequest);
        RequestHandler .getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    public void deleteallcartitem()
    {
        StringRequest  stringRequest = new StringRequest(Request.Method.POST, Important.getMain_Url()+"deleteallitemcart.php", new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    //Toast.makeText(Confirm.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();


                }
                catch (Exception e)
                {
                    //Toast.makeText(Confirm.this, e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(Confirm.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Basic b = new Basic();
                Map<String, String> adddata = new HashMap<>();
                adddata.put("IMEI_Number", b.getimeinumber(getApplicationContext()));
                return adddata;
            }
        };

        RequestHandler .getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }




}