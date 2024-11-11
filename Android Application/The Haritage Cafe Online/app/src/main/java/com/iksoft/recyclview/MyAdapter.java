package com.iksoft.recyclview;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Storebitmap.Bitmapstore;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
 {
     private List<ListItem> listItems;
     private Context context;
     int count = 0;

     public MyAdapter(List<ListItem> listItems, Context context)
      {
         this.listItems = listItems;
         this.context = context;
      }

     @NonNull
     @Override
     public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
      {
         View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
         return new ViewHolder(v);
      }

     @Override
     public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)
     {

         final ListItem listItem = listItems.get(position);

         count = getItemCount();
        /*
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         byte[] imageBytes = baos.toByteArray();

         //convert string to bitmap
         String imageString = listItem.getImg();
         imageBytes = Base64.decode(imageString, Base64.DEFAULT);
         Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length); */

        //set item name
        holder.head.setText(listItem.getHead());
        //set item description
        holder.desc.setText(listItem.getDesc());
        //set item image
        holder.imageView.setImageBitmap(listItem.getImg());

        //check price
        double getprice = 0;
        if (listItem.getDiscount() == 0)
        {
            getprice = listItem.getPrice();
        }
        else
        {
            double discount = (listItem.getDiscount());
            getprice = listItem.getPrice() - listItem.getPrice() * (discount/100);
        }

        //set price
        holder.price.setText("Rs " +Double.toString(getprice));

        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(context.getApplicationContext(), Popupdetails.class);
                intent.putExtra("Topic",listItem.getHead());
                intent.putExtra("des",listItem.getDesc());

                context.startActivity(intent);
            }
        });

         final double finalGetprice = getprice;
         holder.addtocart.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view)
             {
                 Bitmapstore.getInstance().setBitmap(listItem.getImg());
                 Intent intentcart = new Intent(context.getApplicationContext(),Qtydetails.class);
                 intentcart.putExtra("name",listItem.getHead());
                 intentcart.putExtra("price", finalGetprice);

                 context.startActivity(intentcart);

             }
         });

     }



     @Override
     public int getItemCount()
      {
         return listItems.size();
      }

     public class ViewHolder extends RecyclerView.ViewHolder
     {
         public TextView head;
         public TextView desc;
         public ImageView imageView;
         public LinearLayout linearLayout;
         public ImageView info;
         public TextView price;
         public Button addtocart;
         //public ElegantNumberButton quentity;

         public ViewHolder(@NonNull View itemView)
          {
             super(itemView);

             head = (TextView) itemView.findViewById(R.id.textHead);
             desc = (TextView) itemView.findViewById(R.id.textDesc);
             imageView = (ImageView) itemView.findViewById(R.id.imgview);
             linearLayout = (LinearLayout) itemView.findViewById(R.id.mainpanel);
             info = (ImageView) itemView.findViewById(R.id.info);
             price = (TextView) itemView.findViewById(R.id.price);
             addtocart = (Button) itemView.findViewById(R.id.adcartbtn);
             //quentity = (ElegantNumberButton) itemView.findViewById(R.id.quentity);

          }
     }
 }
