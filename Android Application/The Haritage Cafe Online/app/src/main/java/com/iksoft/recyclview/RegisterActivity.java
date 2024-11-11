package com.iksoft.recyclview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText name, mobilenumber,email,password,address;
    private Button loginbtn;
    private TextView login;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeractivity);

        name = (EditText) findViewById(R.id.name);
        mobilenumber = (EditText) findViewById(R.id.mobilenumber);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        address = (EditText) findViewById(R.id.address);
        loginbtn = (Button) findViewById(R.id.signinbutton);
        login = (TextView) findViewById(R.id.register);
        progressDialog  = new ProgressDialog(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(),loginactivity.class));
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registeruser();
            }
        });

    }

    private void registeruser()
     {
         final String getname = name.getText().toString().trim();
         final String getmobile = mobilenumber.getText().toString().trim();
         final String getemail = email.getText().toString().trim();
         final String getpassword = password.getText().toString().trim();
         final String getaddress = address.getText().toString().trim();

         if (getname.equals("") || getmobile.equals("") || getemail.equals("") || getpassword.equals("") || getaddress.equals(""))
          {
             Toast.makeText(RegisterActivity.this, "Please Fill All Fields", Toast.LENGTH_LONG).show();
          }

         else
         {
             progressDialog.setMessage("Registering " +getname+" ....!");
             progressDialog.show();

             StringRequest  stringRequest = new StringRequest(Request.Method.POST, Important.URL_REGISTER, new Response.Listener<String>() {
                 @Override
                 public void onResponse(String response)
                 {
                     progressDialog.dismiss();
                     try
                     {
                         JSONObject jsonObject = new JSONObject(response);
                         if (!jsonObject.getBoolean("error"))
                         {
                             Toast.makeText(RegisterActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                             startActivity(new Intent(getApplicationContext(),loginactivity.class));
                             finish();
                         }
                         else
                         {
                             Toast.makeText(RegisterActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                         }

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
                             Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                         }
                     }){
                 @Override
                 protected Map<String, String> getParams() throws AuthFailureError {
                     Map<String, String> adddata = new HashMap<>();
                     adddata.put("email", getemail);
                     adddata.put("password", getpassword);
                     adddata.put("name", getname);
                     adddata.put("Mobile_Number", getmobile);
                     adddata.put("address", getaddress);
                     return adddata;
                 }
             };

             RequestHandler .getInstance(this).addToRequestQueue(stringRequest);
         }

     }
}