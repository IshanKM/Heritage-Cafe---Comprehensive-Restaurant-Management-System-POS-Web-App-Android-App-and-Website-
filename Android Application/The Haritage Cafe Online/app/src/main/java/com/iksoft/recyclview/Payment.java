package com.iksoft.recyclview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.Serializable;

public class Payment extends AppCompatActivity
{
    EditText name,mobilenumber,address;
    ImageView cashondelivery,visa,master;
    String getids = "";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        name = findViewById(R.id.name);
        mobilenumber = findViewById(R.id.mobile);
        address = findViewById(R.id.address);
        cashondelivery = findViewById(R.id.cashondilivery);
        visa = findViewById(R.id.visa);
        master = findViewById(R.id.master);

        Store_data store_data = new Store_data(this);

        name.setText(store_data.getname());
        mobilenumber.setText(store_data.getMobilenumber());
        address.setText(store_data.getaddress());

        final Intent intent = getIntent();
        String[] itemides = intent.getStringArrayExtra("itemides");
        final String getqty = intent.getStringExtra("qty");
        final String total = intent.getStringExtra("total");

        int size = itemides.length;
        for (int i = 0 ; i< size ; i++)
         {
            getids = getids + "," + itemides[i];
         }

        //remover first ","
        StringBuffer s = new StringBuffer(getids);
        final String editids = s.deleteCharAt(0).toString();

        visa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Payment.this, "Card payment has not been completed yet ", Toast.LENGTH_SHORT).show();
            }
        });

        master.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Payment.this, "Card payment has not been completed yet ", Toast.LENGTH_SHORT).show();
            }
        });

        cashondelivery.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               Intent i = new Intent(getApplicationContext(), Confirm.class);
               i.putExtra("itemides",  editids);
               i.putExtra("qty",getqty);
               i.putExtra("total",total);
               startActivity(i);

            }
        });
    }
}
