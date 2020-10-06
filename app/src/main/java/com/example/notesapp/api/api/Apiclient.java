package com.example.notesapp.api.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Apiclient {
    private static final String base_url = "http://192.168.143.2/NoteApp/";
    private static Retrofit retrofit;

    public static Retrofit getApiClient() {
       Gson gson=new GsonBuilder().setLenient().create();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(ScalarsConverterFactory.create()) //important
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }
}
