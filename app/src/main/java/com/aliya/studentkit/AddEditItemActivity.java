package com.aliya.studentkit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aliya.studentkit.data.Item;
import com.aliya.studentkit.databinding.ActivityAddItemBinding;
import com.aliya.studentkit.retrofit.APIClient;
import com.aliya.studentkit.retrofit.APIInterface;
import com.aliya.studentkit.room.AppDatabase;
import com.aliya.studentkit.utils.MediaUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEditItemActivity extends AppCompatActivity {
    ActivityAddItemBinding binding;
    Item item=new Item();

    MediaUtils mediaUtils;
    boolean isForEdit=false;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        mediaUtils=new MediaUtils(this,uri -> {
            Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
            binding.image.setImageURI(uri);
        });
        mediaUtils.setAllowFromGallery(true);

        if (getIntent().hasExtra("data")) {
            item = new Gson().fromJson(getIntent().getStringExtra("data"), Item.class);
            isForEdit=true;
            Picasso.get().load("http://192.168.137.1/studentkit/images/" + item.getImg()).placeholder(R.drawable.logo).into(binding.image);

//            Picasso.get().load(APIClient.BASE_URL_IMAGES + item.getImg()).placeholder(R.drawable.logo).into(binding.image);
            binding.productName.getEditText().setText(item.getTitle());
            binding.description.getEditText().setText(item.getDetails());
            binding.price.getEditText().setText(item.getPrice());

            String[] cats = getResources().getStringArray(R.array.categories);
            for (int i = 0; i < cats.length; i++) {
                if (cats[i].equals(item.getCategory())) {
                    binding.categorySpinner.setSelection(i);
                    break;
                }
            }

        }

        binding.chooseImage.setOnClickListener(v -> {
            mediaUtils.startImageSelectionActivity();
        });
//
        List<String> subCats= AppDatabase.getDatabase(this).productDao().getAllCats();
        ArrayAdapter<String> suggestionsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, subCats);

        binding.saveBtn.setOnClickListener(v -> {
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Please wait while we save Item...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            if (mediaUtils.choosedImageuri==null){
                uploadItemData();
                return;
            }
            uploadImage(mediaUtils.getSelectedImagePath());


        });


    }

    public void uploadImage(String path){
        File file = new File(path);
        RequestBody fileReqBody = RequestBody.create(file, MediaType.parse("image/*"));
        MultipartBody.Part part = MultipartBody.Part.createFormData("filename", System.currentTimeMillis()+".jpg", fileReqBody);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ResponseBody> call1 = apiInterface.uploadPicture(part);
        call1.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String url = response.body().string();
                        item.setImg(url);
                        uploadItemData();

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(AddEditItemActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    try {
                        File fdelete = file;
                        if (fdelete.exists()) {
                            if (fdelete.delete()) {
                            }
                        }
                    } catch (Exception e) {

                    }
                } else Toast.makeText(AddEditItemActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();;
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void uploadItemData(){
        if (binding.categorySpinner.getSelectedItemPosition()==0){
            Toast.makeText(this, "Select category", Toast.LENGTH_SHORT).show();
            return;
        }

        item.setTitle(binding.productName.getEditText().getText().toString());
        item.setDetails(binding.description.getEditText().getText().toString());
        item.setPrice(Integer.parseInt(String.valueOf(binding.price.getEditText().getText())));
        item.setStatus("Available");
        // Assign the selected category to the item object
        String selectedCategory = binding.categorySpinner.getSelectedItem().toString();
        item.setCategory(selectedCategory);
        if (!isForEdit) item.setId(-1);

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Map<String, String> map = objectMapper.convertValue(item, new TypeReference<Map<String, String>>() {});
        Call<Item> call1 = apiInterface.saveOrUpdateProduct(map);
        call1.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    Item Item = response.body();

                    AppDatabase.getDatabase(AddEditItemActivity.this).productDao().insertOrReplace(Item);
                    Toast.makeText(AddEditItemActivity.this, "Data saved", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddEditItemActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AddEditItemActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mediaUtils.checkActivityResult(requestCode, resultCode, data);
    }
}