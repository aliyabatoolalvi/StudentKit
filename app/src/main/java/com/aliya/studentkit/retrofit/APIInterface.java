package com.aliya.studentkit.retrofit;

import com.aliya.studentkit.data.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("loadItems.php")
    Call<List<Item>> loadItems();

}