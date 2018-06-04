package com.udacity.ouz.bakingapp.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.udacity.ouz.bakingapp.util.NetworkUtil;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeClient {

    private static Retrofit retrofit = null;
    private static OkHttpClient.Builder client = null;

    public static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);

        if (retrofit != null) {
            retrofit = null;
        }

        if(client!=null){

            // Create a first instance a Gson
            Gson gson = new GsonBuilder().create();

            GsonConverterFactory factory = GsonConverterFactory.create(gson);

            retrofit = new Retrofit.Builder()
                    .baseUrl(NetworkUtil.BASE_URL)
                    .addConverterFactory(factory)
                    .client(client.build())
                    .build();
        }

        return retrofit;
    }

}
