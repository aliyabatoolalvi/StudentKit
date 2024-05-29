package com.aliya.studentkit.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import com.aliya.studentkit.MainActivity;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class APIClient {
    public static Retrofit retrofit = null;
//    public static Retrofit getClient() {
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//         OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(interceptor)
//                .connectTimeout(15, TimeUnit.SECONDS)
//                .readTimeout(30,TimeUnit.SECONDS).build();
//         Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();
//        if (retrofit == null)
//            retrofit = new Retrofit.Builder()
//                    .baseUrl("http://192.168.137.1/studentkit/api/")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(client)
//                    .build();
//        return retrofit;
//    }
    public static Retrofit getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor authInterceptor= chain -> chain.proceed(chain.request().newBuilder().addHeader("Session", MainActivity.session).build());
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(interceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS).build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.137.1/studentkit/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();


        return retrofit;
    }
}