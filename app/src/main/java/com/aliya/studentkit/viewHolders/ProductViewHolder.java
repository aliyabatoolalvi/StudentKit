package com.aliya.studentkit.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aliya.studentkit.R;


public class ProductViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public ImageView img;
    public TextView availability;
    public TextView price;

    public CardView post;
    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        price=itemView.findViewById(R.id.price);
        post=itemView.findViewById(R.id.mycard);
        title=itemView.findViewById(R.id.title);
        img= itemView.findViewById(R.id.img);
        availability =itemView.findViewById(R.id.status) ;
    }
}
