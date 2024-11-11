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

public class Yourdetails extends AppCompatActivity
{

    private EditText name, mobilenumber,email,password,address;
    private Button submitbutton;
    private TextView delete;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yourdetails);

        name = (EditText) findViewById(R.id.name);
        mobilenumber = (EditText) findViewById(R.id.mobilenumber);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        address = (EditText) findViewById(R.id.address);
        submitbutton = (Button) findViewById(R.id.submitbutton);
        delete = (TextView) findViewById(R.id.delete);
        progressDialog  = new ProgressDialog(this);

        final Store_data store_data = new Store_data(this);
        name.setText(store_data.getname());
        mobilenumber.setText(store_data.getMobilenumber());
        email.setText(store_data.getemail());
        address.setText(store_data.getaddress());
        name.setText(store_data.getname());



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(),deleteconfirm.class));
            }
        });

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             
             boolean userlogin = Store_data.getInstance(getApplicationContext()).userlogin
                        (
                                store_data.getid(),
                                name.getText().toString().trim(),
                                email.getText().toString().trim(),
                                mobilenumber.getText().toString().trim(),
                                address.getText().toString().trim()

                        );

                if (userlogin)
                {
                    Toast.makeText(Yourdetails.this, "Successfully Submited", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


}