package com.aliya.studentkit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
        binding.price.setText(item.getPrice()+" Rs/day");
        binding.details.setText(item.getDetails());
//        binding.price.setText(String.valueOf(item.getPrice()));
        binding.status.setText(item.getStatus());
        Picasso.get().load("http://192.168.137.1/studentkit/images/" + item.getImg()).into(binding.img);
        binding.lendme.setOnClickListener(v->{

            String productName = item.getTitle();
            String productDetails = item.getDetails();

            // Create the message
            String message1 = "Hello, I am interested in borrowing the following product:\n\n" +
                    "Product Name: " + productName + "\n" +
                    "Details: " + productDetails + "\n\n" +
                    "Please let me know the process.";

            // Create the WhatsApp intent
//            String whatsappUrl = "https://api.whatsapp.com/send?phone=+923169125129&text=" + Uri.encode(message);
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse(whatsappUrl));
//
//            // Check if WhatsApp is installed
//            if (intent.resolveActivity(getPackageManager()) != null) {
//                Log.d("WhatsAppInteraction", "WhatsApp intent resolved, starting activity...");
//                startActivity(intent);
//            } else {
//                Log.e("WhatsAppInteraction", "No activity found to handle WhatsApp intent");
//                Toast.makeText(this, "WhatsApp is not installed or no activity found to handle the intent", Toast.LENGTH_SHORT).show();
//            }
//            String message1 = "Hello, ";
//            String encodedMessage = Uri.encode(message1);
//            String url = "https://wa.me/" + "+923169125129" + "?text=" + encodedMessage;
//            Intent sendIntent = new Intent();
//            sendIntent.setAction(Intent.ACTION_SEND);
////            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
//            sendIntent.setType("text/plain"); startActivity(sendIntent);

//            String message1 = "Hello, I want to borrow this item: "+ item.getTitle() + " with " + item.getDetails() + " for days ___ ";
            String encodedMessage = Uri.encode(message1);
            String phone="+923169125129";
            String url = "https://wa.me/" + phone + "?text=" + encodedMessage;

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_VIEW);
            sendIntent.setData(Uri.parse(url));
            startActivity(sendIntent);

            Intent thankYouIntent = new Intent(DetailsActivity.this, ThankyouActivity.class);
            startActivity(thankYouIntent);
        });
    }
}