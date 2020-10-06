package com.example.notesapp.api.main;

import com.example.notesapp.api.api.ApiInterface;
import com.example.notesapp.api.api.Apiclient;
import com.example.notesapp.api.model.Notes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {
    private MainView mainView;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }

    void getData() {
        mainView.showLoading();
        ApiInterface apiInterface = Apiclient.getApiClient().create(ApiInterface.class);
        Call<List<Notes>> call = apiInterface.getNotes();
        call.enqueue(new Callback<List<Notes>>() {
            @Override
            public void onResponse(Call<List<Notes>> call, Response<List<Notes>> response) {
                mainView.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    mainView.onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Notes>> call, Throwable t) {
                mainView.hideLoading();
                mainView.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }
}
