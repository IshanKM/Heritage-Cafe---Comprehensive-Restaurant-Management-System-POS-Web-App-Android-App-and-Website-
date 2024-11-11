package com.iksoft.recyclview;


import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class logoutconfirm extends AppCompatActivity {

    Button confirm,cancel;

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

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Store_data s = new Store_data(getApplicationContext());
                s.logout();
                Intent intent = new Intent(getApplicationContext(),loginactivity.class);
                startActivity(intent);
                Toast.makeText(logoutconfirm.this, "Successfully Logout", Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


}