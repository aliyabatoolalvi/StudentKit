package com.aliya.studentkit.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aliya.studentkit.DetailsActivity;
import com.aliya.studentkit.R;
import com.aliya.studentkit.data.Item;
import com.aliya.studentkit.viewHolders.ProductViewHolder;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    Context context;
    List<Item> data;
    public ProductAdapter(Context context, List<Item> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.itemdesign,parent,false);
        return new ProductViewHolder(view);
    }

    
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Item item = data.get(position);

//        holder.itemimg.setImageResource(Item.getImg());
        holder.title.setText(item.getTitle());

        holder.details.setText(item.getDetails());
        holder.price.setText(item.getPrice() + " Rs/Day");
        Picasso.get().load("http://192.168.137.1/studentkit/images/" + item.getImg()).into(holder.itemimg);
        holder.post.setOnClickListener(v -> {
            Intent intent=new Intent(context, DetailsActivity.class);
            intent.putExtra("data",new Gson().toJson(item));
            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
}
