package com.example.notesapp.api.editor;


import com.example.notesapp.api.api.ApiInterface;
import com.example.notesapp.api.api.Apiclient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorPresenter {
    private EditorView editorView;

    public EditorPresenter(EditorView editorView) {
        this.editorView = editorView;
    }

    void saveNotes(String title, String note, int color) {
        editorView.showProgress();
        ApiInterface apiInterface = Apiclient.getApiClient().create(ApiInterface.class);
        Call<String> notessCall = apiInterface.saveNotes(title, note, color);
        notessCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                editorView.hideProgress();
                if (response.isSuccessful()) {
                    editorView.onRequestSuccess(response.body());
                } else {
                    editorView.onRequestError(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                editorView.hideProgress();
                editorView.onRequestError(t.getLocalizedMessage());

            }
        });
    }

    void Update(int id, String title, String note, int color) {
        editorView.showProgress();
        ApiInterface apiInterface = Apiclient.getApiClient().create(ApiInterface.class);
        Call<String> notessCall = apiInterface.updateNotes(id, title, note, color);
        notessCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                editorView.hideProgress();
                if (response.isSuccessful()) {
                    editorView.onRequestSuccess(response.body());
                } else {
                    editorView.onRequestError(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                editorView.hideProgress();
                editorView.onRequestError(t.getLocalizedMessage());

            }
        });
    }

    void Delete(int id) {
        editorView.showProgress();
        ApiInterface apiInterface = Apiclient.getApiClient().create(ApiInterface.class);
        Call<String> notesCall = apiInterface.deleteNotes(id);
        notesCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    editorView.onRequestSuccess(response.body());
                } else {
                    editorView.onRequestError(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                  editorView.onRequestSuccess(t.getLocalizedMessage());
            }
        });
    }
}
