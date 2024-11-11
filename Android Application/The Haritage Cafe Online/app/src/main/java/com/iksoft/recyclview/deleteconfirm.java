package com.iksoft.recyclview;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
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


public class deleteconfirm extends AppCompatActivity {

    Button confirm,cancel;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logoutconfirm);

        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);

        int width = display.widthPixels;
        int height = display.heightPixels;

        getWindow().setLayout((int)(width),(int)(height*.3));

        confirm = findViewById(R.id.confirm);
        cancel = findViewById(R.id.cancel);
        progressDialog  = new ProgressDialog(this);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Store_data s = new Store_data(getApplicationContext());
                deleteuser(Integer.toString(s.getid()));
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void deleteuser(final String id)
    {

        progressDialog.setMessage("Deleting ....!");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Important.getMain_Url() + "deleteaccount.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progressDialog.dismiss();
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(deleteconfirm.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    Store_data store_data = new Store_data(getApplicationContext());
                    store_data.logout();
                    finish();
                    startActivity(new Intent(getApplicationContext(),loginactivity.class));
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
                        progressDialog.hide();
                        Toast.makeText(deleteconfirm.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> adddata = new HashMap<>();

                adddata.put("id", id);

                return adddata;
            }
        };

        RequestHandler .getInstance(this).addToRequestQueue(stringRequest);
    }



}