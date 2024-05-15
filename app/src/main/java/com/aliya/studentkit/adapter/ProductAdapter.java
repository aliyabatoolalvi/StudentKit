package com.aliya.studentkit.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
    Item item;
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
        item = data.get(position);
        if (holder.title != null && item.getTitle() != null) {
            holder.title.setText(item.getTitle());
        }
//      holder.title.setText(item.getTitle());
        holder.availability.setText(item.getStatus());
        if ("borrowed".equalsIgnoreCase(item.getStatus())) {
            holder.availability.setTextColor(ContextCompat.getColor(context, R.color.no));
        } else if ("available".equalsIgnoreCase(item.getStatus())) {
            holder.availability.setTextColor(ContextCompat.getColor(context, R.color.yes));
        }
//        holder.price.setText(item.getPrice().toString()+ " Rs/Day");
        Picasso.get().load("http://192.168.137.1/studentkit/images/" + item.getImg()).into(holder.img);
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
