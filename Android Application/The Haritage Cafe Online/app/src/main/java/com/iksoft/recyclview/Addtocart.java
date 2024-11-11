package com.iksoft.recyclview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class Addtocart extends AppCompatActivity
{
    TextView priceshow;
    Button ordersend;
    private RecyclerView recyclerViewcart;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listitem;
    String[] getidarray;
    String getqty;
    @Override
    protected void onCreate(Bundle savedInstanceState)
     {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtocart);

        Intent intent = getIntent();
        String[] getname = {intent.getStringExtra("name")};
        double[] getprice= {intent.getDoubleExtra("price",0)};
        int[] getqty= {intent.getIntExtra("qty",0)};

        priceshow = findViewById(R.id.priceshow);
        ordersend = findViewById(R.id.ordersend);

        recyclerViewcart = findViewById(R.id.recyclerViewcart);
        recyclerViewcart.setHasFixedSize(true);
        recyclerViewcart.setLayoutManager(new LinearLayoutManager(this));
        listitem = new ArrayList<>();

        new ItemTouchHelper(itemtouchhelper).attachToRecyclerView(recyclerViewcart);

        addtocartshow(Important.getMain_Url()+ "getaddtocart.php");

        ordersend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                setorder();
            }
        });

     }

    public void setprice(int check,double deleteprice)
    {
        if (check == 0)
        {
            double total = 0;
            for (int i = 0; i < listitem.size(); i++)
            {
                total = total + listitem.get(i).getPrice();
                priceshow.setText(Double.toString(total));
            }
        }
        else if (check == 1)
        {
            priceshow.setText(Double.toString(Double.parseDouble((String) priceshow.getText()) - deleteprice));
        }

    }

    public void getnamearray(String[] getname,String qty,int getsize,int i)
    {
        this.getidarray = getname;
        this.getqty = qty;
        //Toast.makeText(this, getnamearray[0], Toast.LENGTH_SHORT).show();

        if (i == getsize -1)
        {
            Intent intent = new Intent(this, Payment.class);
            intent.putExtra("itemides",getname);
            intent.putExtra("qty",qty);
            intent.putExtra("total",priceshow.getText());
            startActivity(intent);
        }

    }
    public void setorder()
     {
         String getname = "";
         String qty = "";
         final String[] getids = new String[listitem.size()];

         for (int i = 0; i < listitem.size(); i++)
         {
             qty = qty + Integer.toString(listitem.get(i).getQty());
             getname = listitem.get(i).getHead();

             final String finalGetname = getname;
             final int finalI = i;
             final String finalQty = qty;
             StringRequest stringRequest = new StringRequest(Request.Method.POST, Important.getMain_Url() + "getorderid.php", new Response.Listener<String>()
             {
                 @Override
                 public void onResponse(String response) {

                     try
                     {
                         JSONObject jsonObject = new JSONObject(response);
                         getids[finalI] = Integer.toString(jsonObject.getInt("id"));
                         //Toast.makeText(Addtocart.this, Integer.toString(finalI), Toast.LENGTH_LONG).show();

                         getnamearray(getids, finalQty,listitem.size(),finalI);

                     }
                     catch (JSONException e)
                     {
                         e.printStackTrace();
                     }

                 }
             },
                     new Response.ErrorListener() {
                         @Override
                         public void onErrorResponse(VolleyError error)
                         {
                             Toast.makeText(Addtocart.this, error.getMessage(), Toast.LENGTH_LONG).show();
                         }
                     }) {
                 @Override
                 protected Map<String, String> getParams() throws AuthFailureError
                  {
                     Basic b = new Basic();
                     Map<String, String> adddata = new HashMap<>();
                     adddata.put("Name", finalGetname);
                     return adddata;
                  }
             };

             RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

         }

     }

    ItemTouchHelper.SimpleCallback itemtouchhelper = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT)
    {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target)
        {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
        {

            int i = viewHolder.getAdapterPosition();
            ListItem listItem = listitem.get(i);
            final String getname = listItem.getHead();
            //Toast.makeText(Addtocart.this, getname, Toast.LENGTH_SHORT).show();

            StringRequest  stringRequest = new StringRequest(Request.Method.POST, Important.getMain_Url()+"deleteaddtocart.php", new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response)
                {

                    try
                    {
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(Addtocart.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

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
                            Toast.makeText(Addtocart.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError
                {
                    Basic b = new Basic();
                    Map<String, String> adddata = new HashMap<>();
                    adddata.put("deletename", getname);
                    adddata.put("IMEI_Number", b.getimeinumber(getApplicationContext()));
                    return adddata;
                }
            };

            RequestHandler .getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

            listitem.remove(viewHolder.getAdapterPosition());
            adapter.notifyDataSetChanged();
            setprice(1,listItem.getPrice());
    }



        //this libarary get from github miss
        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(Addtocart.this, R.color.myRed))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


    private void addtocartshow(String url)
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
                        Bitmap.createScaledBitmap(decodedImage, 134, 100, false);

                        ListItem Item = new ListItem(
                                jsonObject.getString("name" + i),
                                "",
                                decodedImage,
                                jsonObject.getDouble("price" + i),
                                0,
                                jsonObject.getInt("qty" + i)
                        );

                        listitem.add(Item);
                        adapter = new MyAdapterCart(listitem, getApplicationContext());
                        recyclerViewcart.setAdapter(adapter);
                        setprice(0,0);

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
        RequestHandler .getInstance(this).addToRequestQueue(stringRequest);
    }


}



