package com.aliya.studentkit.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.aliya.studentkit.R;


public class ProductViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public ImageView itemimg;
    public TextView avalibility;
    public TextView details;
    public TextView price;

    public CardView post;
    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        post=itemView.findViewById(R.id.mycard);
        title=itemView.findViewById(R.id.title);
        itemimg= itemView.findViewById(R.id.img);
        avalibility=itemView.findViewById(R.id.status) ;


    }
}
