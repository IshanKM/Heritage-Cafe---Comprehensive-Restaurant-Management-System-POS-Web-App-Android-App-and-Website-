package com.iksoft.recyclview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class loginactivity extends AppCompatActivity {

    private EditText email,password;
    private Button btnlogin;
    private TextView register;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
     {
        if (Store_data.getInstance(this).islogin())
         {
             finish();
             startActivity(new Intent(getApplicationContext(),Mainscreen.class));
         }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnlogin =  findViewById(R.id.signinbutton);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");

        btnlogin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 userlogin();
             }
         });

     }

    private void userlogin()
    {
        final String getemail = email.getText().toString().trim();
        final String getpassword = password.getText().toString().trim();
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Important.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progressDialog.dismiss();
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("error"))
                      {
                        progressDialog.dismiss();
                        Store_data.getInstance(getApplicationContext()).userlogin
                                (
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("name"),
                                        jsonObject.getString("email"),
                                        jsonObject.getString("Mobile_Number"),
                                        jsonObject.getString("address")

                                );

                          startActivity(new Intent(getApplicationContext(),Mainscreen.class));
                      }
                    else
                      {
                          Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                      }
                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> adddata = new HashMap<>();
                adddata.put("email", getemail);
                adddata.put("password", getpassword);
                return adddata;
            }
        };

        RequestHandler .getInstance(this).addToRequestQueue(stringRequest);
    }

}