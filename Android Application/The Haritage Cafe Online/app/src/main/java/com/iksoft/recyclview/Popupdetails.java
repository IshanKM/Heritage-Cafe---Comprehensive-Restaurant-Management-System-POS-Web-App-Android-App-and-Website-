package com.iksoft.recyclview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class Popupdetails extends AppCompatActivity {

    TextView topicshow;
    TextView descriptionshow;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popupdetails);

        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);

        int width = display.widthPixels;
        int height = display.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        topicshow = findViewById(R.id.heading);
        descriptionshow = findViewById(R.id.details);

        Intent intent = getIntent();
        String gettopic = intent.getStringExtra("Topic");
        String getdes = intent.getStringExtra("des");

        topicshow.setText(gettopic);
        descriptionshow.setText(getdes);

    }
}