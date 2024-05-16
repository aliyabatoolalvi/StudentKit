package com.aliya.studentkit;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.aliya.studentkit.databinding.ActivityDetailsBinding;
import com.aliya.studentkit.databinding.ActivityMainBinding;

public class DetailsActivity extends AppCompatActivity {
ActivityDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        binding.back.setOnClickListener(v->{
            super.onBackPressed();
        });

    }
}