package com.example.notesapp.api.main;

import com.example.notesapp.api.model.Notes;

import java.util.List;

public interface MainView {
    void showLoading();
    void hideLoading();
    void onGetResult(List<Notes>notes);
    void onErrorLoading(String message);
}
