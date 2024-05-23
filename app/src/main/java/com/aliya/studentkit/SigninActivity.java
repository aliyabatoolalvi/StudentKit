package com.aliya.studentkit;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aliya.studentkit.data.User;
import com.aliya.studentkit.databinding.ActivitySigninBinding;
import com.aliya.studentkit.retrofit.APIClient;
import com.aliya.studentkit.retrofit.APIInterface;
import com.aliya.studentkit.room.AppDatabase;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity {
    ActivitySigninBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);
        binding.back.setOnClickListener(v->{
            super.onBackPressed();
        });
        binding.signbtn.setOnClickListener(v -> {

            //validate
            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
            Map<String, String> map = new HashMap<>();

            map.put("email", "");
            map.put("phone", binding.phone.getText().toString());
            map.put("reg", binding.reg.getText().toString());
            map.put("name", binding.name.getText().toString());
            Call<User> call1 = apiInterface.signUp(map, binding.password.getText().toString());
            call1.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.code() == 200) {
                        User user = response.body();
                        String session = response.headers().get("session");
                        user.setSession(session);

                        AppDatabase.getDatabase(SigninActivity.this).userDao().insertOrReplace(user);
                        Toast.makeText(SigninActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SigninActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(SigninActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(SigninActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}