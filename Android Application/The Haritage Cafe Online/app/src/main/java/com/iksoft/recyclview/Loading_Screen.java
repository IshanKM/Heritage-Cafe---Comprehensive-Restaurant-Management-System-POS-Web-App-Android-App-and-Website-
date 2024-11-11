package com.iksoft.recyclview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class Loading_Screen extends AppCompatActivity
{

    private static int SPLASH_TIME_OUT = 4000 ;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading__screen);

        pb = findViewById(R.id.pb);

        loadingshow();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),loginactivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);


    }

    public void loadingshow()
    {
        Thread th = new Thread()
        {
            @Override
            public void run()
            {
                try
                {

                    for (int i = 0; i <= 101; i++)
                    {
                        if (i == 20 )
                        {
                            sleep(500);
                            continue;
                        }
                        if (i == 50  )
                        {
                            sleep(700);
                            continue;
                        }

                        pb.setProgress(i);
                        sleep(30);

                    }

                }
                catch(Exception e)
                {

                }
            }
        };
        th.start();
    }
}