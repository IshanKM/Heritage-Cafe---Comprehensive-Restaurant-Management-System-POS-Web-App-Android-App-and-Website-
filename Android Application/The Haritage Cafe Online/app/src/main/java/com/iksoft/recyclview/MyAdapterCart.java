package com.iksoft.recyclview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyAdapterCart extends RecyclerView.Adapter<MyAdapterCart.ViewHolder>
{
    private List<ListItem> listItems;
    private Context context;
    int count = 0;

    public MyAdapterCart(List<ListItem> listItems, Context context)
    {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitem,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)
    {

        final ListItem listItem = listItems.get(position);

        count = getItemCount();


        //set item name
        holder.name.setText(listItem.getHead());
        //set item price
        holder.price.setText("Rs." +Double.toString(listItem.getPrice()));
        //set item description
        holder.qty.setText(Integer.toString(listItem.getQty()));
        //set item image
        holder.imageView.setImageBitmap(listItem.getImg());


    }

    @Override
    public int getItemCount()
    {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name;
        public TextView qty;
        public ImageView imageView;
        public TextView price;


        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.namecart);
            imageView = (ImageView) itemView.findViewById(R.id.imgcart);
            qty = (TextView) itemView.findViewById(R.id.quentitycart);
            price = (TextView) itemView.findViewById(R.id.pricecart);


        }
    }
}
