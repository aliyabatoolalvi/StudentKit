package com.aliya.studentkit.retrofit;

import com.aliya.studentkit.data.Item;
import com.aliya.studentkit.data.User;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterface {
    @GET("loadItems.php")
    Call<List<Item>> loadItems();
    @FormUrlEncoded
    @POST("saveorupdate.php")
    Call<Item> saveOrUpdateProduct(@FieldMap Map<String, String> items);

    @FormUrlEncoded
    @POST("signin.php")
    Call<User> signUp(@FieldMap Map<String, String> items,@Header(value = "Password") String password);

    @FormUrlEncoded
    @POST("login.php")
    Call<User> login(@FieldMap Map<String, String> items,@Header(value = "Password") String password);
    @Multipart
    @POST("uploadfile.php")
    Call<ResponseBody> uploadPicture(@Part MultipartBody.Part part);
}