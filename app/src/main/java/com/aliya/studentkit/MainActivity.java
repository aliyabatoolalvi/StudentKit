package com.aliya.studentkit;

import android.content.Intent;
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
import com.aliya.studentkit.data.User;
import com.aliya.studentkit.databinding.ActivityMainBinding;
import com.aliya.studentkit.retrofit.APIClient;
import com.aliya.studentkit.retrofit.APIInterface;
import com.aliya.studentkit.room.AppDatabase;
import com.onesignal.Continue;
import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    List<Item> data = new ArrayList<>();
    ProductAdapter adapter = new ProductAdapter(this,data);

    private static final String ONESIGNAL_APP_ID = "5821b1b4-1dd5-47ca-9aac-f42ea4ffbf5b";
    public static String session = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recycler.setLayoutManager(manager);
        binding.recycler.setAdapter(adapter);
        OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);

        // OneSignal Initialization
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID);

        // requestPermission will show the native Android notification permission prompt.
        // NOTE: It's recommended to use a OneSignal In-App Message to prompt instead.
        OneSignal.getNotifications().requestPermission(false, Continue.none());


        binding.floatingActionButton.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, AddEditItemActivity.class);
            startActivity(intent);
        });

        User user = AppDatabase.getDatabase(this).userDao().getUser();
        session = user.getSession();
        APIClient.retrofit = null;
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
// question was asked that difference between implicit and explicit
// and why retrofit is used?
// explicit
// open app with package name of whatsapp or your app
// implicit
// when user want to see privacy policy where it goes to url, we can use it by intent action
// Intent intent=new Intent(Intent.ACTION_VIEW);
//intent.setData(URI.parse("https.example.com"));
//show all the activities where its allowable
// used for send message , share text or image , to call dialer, send email with data to where recipient and subject and text are pre-written

//need conversion
//for call with no
// Intent intent=new Intent(Intent.ACTION_CALL);
//intent.setData(URI.parse("tel:+922458732"));
//
//send specific message on whatsapp
//String message = "Hello, this is a test message";
//String encodedMessage = Uri.encode(message);
//String url = "https://wa.me/" + phoneNumber + "?text=" + encodedMessage;

// custom dialog box , design xml file
// when click on button xml is called with dialog

// public static void showDifference(Context context, String oldOne, String newOne) {
//
//        LayoutChangesBinding binding = LayoutChangesBinding
//                .inflate(LayoutInflater.from(context));
//
//        binding.newV.setText(newOne);
//        binding.oldV.setText(oldOne);
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setView(binding.getRoot());
//        AlertDialog dialog = builder.show();
//        binding.cancel.setOnClickListener(view -> dialog.dismiss());
//
//    }

//we have to implement firebase
