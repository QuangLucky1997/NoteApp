package com.example.notesapp.api.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.notesapp.api.editor.EditorTheme;
import com.example.notesapp.R;
import com.example.notesapp.api.model.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {
    private static final int INTENT_ADD = 100;
    private static final int INTENT_EDIT = 200;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    MainPresenter mainPresenter;
    AdapterRV adapterRV;
    AdapterRV.ItemClickListener itemClickListener;

    List<Notes> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        fab = findViewById(R.id.add);
        fab.setOnClickListener(view -> startActivityForResult(new Intent(MainActivity.this, EditorTheme.class), INTENT_ADD));
        mainPresenter = new MainPresenter(this);
        mainPresenter.getData();
        swipeRefreshLayout.setOnRefreshListener(
                () -> mainPresenter.getData()
        );
        itemClickListener = ((view, position) -> {
            int id = notesList.get(position).getId();
            String title = notesList.get(position).getTitle();
            String note = notesList.get(position).getNote();
            int color = notesList.get(position).getColor();
            Intent intent = new Intent(this, EditorTheme.class);
            intent.putExtra("id", id);
            intent.putExtra("title", title);
            intent.putExtra("note", note);
            intent.putExtra("color", color);
            startActivityForResult(intent, INTENT_EDIT);
            Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (INTENT_ADD == requestCode && resultCode == RESULT_OK) {
            mainPresenter.getData();//reloadData
        } else if (requestCode == INTENT_EDIT && resultCode == RESULT_OK) {
            mainPresenter.getData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onGetResult(List<Notes> notes) {
        adapterRV = new AdapterRV(this, notes, itemClickListener);
        adapterRV.notifyDataSetChanged();
        recyclerView.setAdapter(adapterRV);
        notesList = notes;
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}