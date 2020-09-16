package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorTheme extends AppCompatActivity {
    EditText edtTodo, edtEnterNotes;
    ProgressDialog progressDialog;
    ApiInterface apiInterface;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_theme);
        edtEnterNotes = findViewById(R.id.edtnotes);
        edtTodo = findViewById(R.id.edtTodo);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                String title = edtTodo.getText().toString();
                String note = edtEnterNotes.getText().toString();
                int color = -2184710;
                if (title.isEmpty()) {
                    edtTodo.setError("Please enter a title");
                } else if (note.isEmpty()) {
                    edtTodo.setError("Please enter a note");
                } else {
                    saveNotes(title, note, color);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveNotes(String title, String note, int color) {
        progressDialog.show();
        apiInterface = Apiclient.getApiClient().create(ApiInterface.class);
        Call<Notes> notesCall = apiInterface.saveNotes(title, note, color);
        notesCall.enqueue(new Callback<Notes>() {
            @Override
            public void onResponse(@NonNull Call<Notes> call, @NonNull Response<Notes> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();
                    if (success) {
                        Toast.makeText(EditorTheme.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(EditorTheme.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull  Call<Notes> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditorTheme.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}