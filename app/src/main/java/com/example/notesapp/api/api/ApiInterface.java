package com.example.notesapp.api.api;


import com.example.notesapp.api.model.Notes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("save.php")
    Call<String> saveNotes(@Field("title") String title,
                           @Field("note") String note, @Field("color") int color);
    @GET("DisplayData.php")
    Call<List<Notes>>getNotes();
    @FormUrlEncoded
    @POST("Update.php")
    Call<String> updateNotes(@Field("id") int id,@Field("title") String title,
                           @Field("note") String note, @Field("color") int color);
    @FormUrlEncoded
    @POST("Delete.php")
    Call<String> deleteNotes(@Field("id") int id);

}
