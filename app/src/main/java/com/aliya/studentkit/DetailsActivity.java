package com.aliya.studentkit;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.aliya.studentkit.adapter.ProductAdapter;
import com.aliya.studentkit.data.Item;
import com.aliya.studentkit.databinding.ActivityDetailsBinding;
import com.aliya.studentkit.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    ActivityDetailsBinding binding;
    Item item;
//    Context context = this;
//    ProductAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        binding.back.setOnClickListener(v->{
            super.onBackPressed();
        });
        item = new Gson().fromJson(getIntent().getStringExtra("data"), Item.class);

        binding.title.setText(item.getTitle());
        binding.price.setText(item.getPrice()+"Rs/day");
        binding.details.setText(item.getDetails());
        binding.price.setText(String.valueOf(item.getPrice()));
        binding.status.setText(item.getStatus());
        Picasso.get().load("http://192.168.137.1/studentkit/images/" + item.getImg()).into(binding.img);


    }
}