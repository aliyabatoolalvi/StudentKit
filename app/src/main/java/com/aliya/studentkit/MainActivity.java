package com.aliya.studentkit;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.aliya.studentkit.adapter.ProductAdapter;
import com.aliya.studentkit.data.Item;
import com.aliya.studentkit.databinding.ActivityMainBinding;
import com.aliya.studentkit.retrofit.APIClient;
import com.aliya.studentkit.retrofit.APIInterface;
import com.aliya.studentkit.room.AppDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    List<Item> data = new ArrayList<>();
    ProductAdapter adapter = new ProductAdapter(this,data);

    private static final String ONESIGNAL_APP_ID = "########-####-####-####-############";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recycler.setLayoutManager(manager);
        binding.recycler.setAdapter(adapter);
        try {
            //  to fetch data from local database
            List<Item> Items = AppDatabase.getDatabase(this).productDao().getAll();
            // If local database data is available, display it
            if (Items != null && !Items.isEmpty()) {
                data.addAll(Items);
                adapter.notifyDataSetChanged();
            }
            // Make network call to fetch data from server
            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<List<Item>> call1 = apiInterface.loadItems();
            call1.enqueue(new Callback<List<Item>>() {
                @Override
                public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                    if (response.isSuccessful()) {
                        List<Item> Items = response.body();
                        Log.d("API Response", "Success: " + Items.size());

                        Toast.makeText(MainActivity.this, "Connection successful", Toast.LENGTH_SHORT).show();

                        AppDatabase.getDatabase(MainActivity.this).productDao().deleteAll();
                        AppDatabase.getDatabase(MainActivity.this).productDao().insertOrReplaceAll(Items);

                        refresh();
                    } else {
                        Log.e("API Response", "Error: " + response.code() + " " + response.message());
                        Toast.makeText(MainActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<Item>> call, Throwable t) {
                    Log.e("API Failure", "Error: " + t.getMessage(), t);
                    Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e("MainActivity", "Error: " + e.getMessage(), e);
            Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    void refresh() {
        data.clear();
        data.addAll(AppDatabase.getDatabase(this).productDao().getAll());
        adapter.notifyDataSetChanged();
    }
}