package com.iksoft.recyclview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mainscreen extends AppCompatActivity {

    ViewFlipper imgslider;
    Button breakfast,pizza,pasta,soups,sandwich,dessert,menu;
    TextView toptext,showcount;
    ImageView cart;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);

        imgslider = findViewById(R.id.imageslider);
        cart = findViewById(R.id.cart);;

        breakfast = findViewById(R.id.breakfast);
        pizza = findViewById(R.id.pizza);
        pasta = findViewById(R.id.pasta);
        soups = findViewById(R.id.soups);
        sandwich = findViewById(R.id.sandwich);
        dessert = findViewById(R.id.dessert);
        toptext = findViewById(R.id.toptext);
        menu = findViewById(R.id.menu);

        int img[] = {R.drawable.bg,R.drawable.bg1,R.drawable.bg2};
        for (int i = 0 ; i<img.length ; i++)
        {
            slider(img[i]);
        }

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Menu.class);
                startActivity(intent);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(),Addtocart.class);
                startActivity(intent);

            }
        });

        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String url = "breakfast.php";

                Intent intent = new Intent(getApplicationContext(),itemshow.class);
                intent.putExtra("url",Important.getMain_Url()+url);
                intent.putExtra("heading","Breakfast");
                startActivity(intent);
            }
        });

        pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String url = "pizza.php";

                Intent intent = new Intent(getApplicationContext(),itemshow.class);
                intent.putExtra("url",Important.getMain_Url()+url);
                intent.putExtra("heading","Pizza");
                startActivity(intent);
            }
        });

        pasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String url = "pasta.php";

                Intent intent = new Intent(getApplicationContext(),itemshow.class);
                intent.putExtra("url",Important.getMain_Url()+url);
                intent.putExtra("heading","Pasta");
                startActivity(intent);
            }
        });

        soups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String url = "soups.php";

                Intent intent = new Intent(getApplicationContext(),itemshow.class);
                intent.putExtra("url",Important.getMain_Url()+url);
                intent.putExtra("heading","Soups");
                startActivity(intent);
            }
        });

        sandwich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String url = "sandwich.php";

                Intent intent = new Intent(getApplicationContext(),itemshow.class);
                intent.putExtra("url",Important.getMain_Url()+url);
                intent.putExtra("heading","Sandwich");
                startActivity(intent);
            }
        });

        dessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String url = "deserts.php";

                Intent intent = new Intent(getApplicationContext(),itemshow.class);
                intent.putExtra("url",Important.getMain_Url()+url);
                intent.putExtra("heading","Desserts");
                startActivity(intent);
            }
        });
    }

    public void slider(int img)
     {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(img);

        imgslider.addView(imageView);
        imgslider.setFlipInterval(2000);
        imgslider.setAutoStart(true);

        imgslider.setInAnimation(this,android.R.anim.slide_in_left);
        imgslider.setOutAnimation(this,android.R.anim.slide_out_right);
     }



}

