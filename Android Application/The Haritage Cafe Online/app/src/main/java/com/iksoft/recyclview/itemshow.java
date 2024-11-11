package com.iksoft.recyclview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class itemshow extends AppCompatActivity
{
    Context context;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listitem;
    TextView heading;
    ImageView breakfastbtn,pizzabtn,pastabtn,soupsbtn,sandwichbtn,dessertbtn,cart;
    Button menu;
    Bitmap setsizebitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemshow);

        heading = findViewById(R.id.heading);
        breakfastbtn = findViewById(R.id.breakfastbtn);
        pizzabtn = findViewById(R.id.pizzabtn);
        pastabtn = findViewById(R.id.pastabtn);
        soupsbtn = findViewById(R.id.soupsbtn);
        sandwichbtn = findViewById(R.id.sandwichbtn);
        dessertbtn = findViewById(R.id.dessertbtn);
        cart = findViewById(R.id.cart);
        menu = findViewById(R.id.menu);

        recyclerView = findViewById(R.id.recyclerViewitem);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listitem = new ArrayList<>();

        Intent intent = getIntent();
        String geturl = intent.getStringExtra("url");
        String getheading = intent.getStringExtra("heading");

        heading.setText(getheading);
        databaseshow(geturl);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),Menu.class);
                startActivity(intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getApplicationContext(),Addtocart.class);
                startActivity(intent);
            }
        });

        breakfastbtn.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                clearrecycleview();
                heading.setText("Breakfast");
                String url = Important.getMain_Url() +"breakfast.php";
                databaseshow(url);
            }
        });
        
        pizzabtn.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                clearrecycleview();
                heading.setText("Pizza");
                String url = Important.getMain_Url() +"pizza.php";
                databaseshow(url);
            }
        });

        pastabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                clearrecycleview();
                heading.setText("Pasta");
                String url = Important.getMain_Url() +"pasta.php";
                databaseshow(url);

            }
        });

        soupsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                clearrecycleview();
                heading.setText("Soups");
                String url = Important.getMain_Url() +"soups.php";
                databaseshow(url);
            }
        });

        sandwichbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                clearrecycleview();
                heading.setText("Sandwich");
                String url = Important.getMain_Url() +"sandwich.php";
                databaseshow(url);
            }
        });

        dessertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                clearrecycleview();
                heading.setText("Dessert");
                String url = Important.getMain_Url() +"deserts.php";
                databaseshow(url);
            }
        });

    }

    private void databaseshow(String url)
    {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...!");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

                progressDialog.dismiss();
                try
                {
                    JSONObject jsonObject = new JSONObject(response);

                    int  size = jsonObject.getInt("size" );
                    for (int i = 0; i<size ; i++)
                    {
                        String  getbase64 = jsonObject.getString("img" + i);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] imageBytes = baos.toByteArray();

                        //convert string to bitmap
                        imageBytes = Base64.decode(getbase64, Base64.DEFAULT);
                        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                        if (decodedImage != null)
                        {
                            setsizebitmap = Bitmap.createScaledBitmap(decodedImage, 400, 250, true);
                        }
                        else
                        {
                            setsizebitmap = decodedImage;
                        }


                        ListItem Item = new ListItem(
                                jsonObject.getString("name" + i),
                                jsonObject.getString("desc" + i),
                                setsizebitmap,
                                jsonObject.getDouble("price" + i),
                                jsonObject.getDouble("discount" + i),
                                0
                               );

                        listitem.add(Item);
                        adapter = new MyAdapter(listitem, getApplicationContext());
                        recyclerView.setAdapter(adapter);

                    }

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
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Database Connection Fail..", Toast.LENGTH_LONG).show();
                    }
                });

        //RequestQueue requestQueue = Volley.newRequestQueue(this);
        //requestQueue.add(stringRequest);
        RequestHandler .getInstance(this).addToRequestQueue(stringRequest);
    }

    public void clearrecycleview()
     {
        int size = listitem.size();
        if (size > 0)
         {
            for (int i = 0; i < size; i++)
             {
                listitem.remove(0);
             }

            adapter.notifyItemRangeRemoved(0,size);
         }
     }


    /*
    public void getcartcount(String url)
    {
        final int count[] = {0};
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

                try
                {
                    JSONObject jsonObject = new JSONObject(response);

                    count[0] = jsonObject.getInt("size" );

                    showcount.setText(Integer.toString(count[0]));

                }
                catch(JSONException e)
                {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){

            protected Map<String, String> getParams() throws AuthFailureError
            {
                Basic b = new Basic();
                Map<String, String> adddata = new HashMap<>();
                adddata.put("IMEI_Number", b.getimeinumber(getApplicationContext()));
                return adddata;
            }
        };

        //RequestQueue requestQueue = Volley.newRequestQueue(this);
        //requestQueue.add(stringRequest);
        RequestHandler .getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    } */
}