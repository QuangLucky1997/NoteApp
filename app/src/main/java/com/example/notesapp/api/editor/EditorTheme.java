package com.example.notesapp.api.editor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notesapp.R;
import com.thebluealliance.spectrum.SpectrumPalette;


public class EditorTheme extends AppCompatActivity implements EditorView {
    EditText edtTodo, edtEnterNotes;
    ProgressDialog progressDialog;
    SpectrumPalette palette;
    EditorPresenter editorPresenter;
    int color;
    int id;
    String title, note;
    Menu actionMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_theme);
        edtEnterNotes = findViewById(R.id.edtnotes);
        edtTodo = findViewById(R.id.edtTodo);
        palette = findViewById(R.id.spectrum);
        palette.setOnColorSelectedListener(
                new SpectrumPalette.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int clr) {
                        color = clr;
                    }
                }
        );
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        editorPresenter = new EditorPresenter(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        title = intent.getStringExtra("title");
        note = intent.getStringExtra("note");
        color = intent.getIntExtra("color", 0);
        setDataFromEditor();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_editor, menu);
        actionMenu = menu;
        if (id != 0) {
            actionMenu.findItem(R.id.edit).setVisible(true);
            actionMenu.findItem(R.id.delete).setVisible(true);
            actionMenu.findItem(R.id.save).setVisible(false);
            actionMenu.findItem(R.id.update).setVisible(false);
        } else {
            actionMenu.findItem(R.id.edit).setVisible(false);
            actionMenu.findItem(R.id.delete).setVisible(false);
            actionMenu.findItem(R.id.save).setVisible(true);
            actionMenu.findItem(R.id.update).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String title = edtTodo.getText().toString();
        String note = edtEnterNotes.getText().toString();
        int color = this.color;
        switch (item.getItemId()) {
            case R.id.save:
                if (title.isEmpty()) {
                    edtTodo.setError("Please enter a title");
                } else if (note.isEmpty()) {
                    edtEnterNotes.setError("Please enter a note");
                } else {
                    editorPresenter.saveNotes(title, note, color);
                }
                return true;
            case R.id.edit:
                editMode();
                actionMenu.findItem(R.id.edit).setVisible(false);
                actionMenu.findItem(R.id.delete).setVisible(false);
                actionMenu.findItem(R.id.save).setVisible(false);
                actionMenu.findItem(R.id.update).setVisible(true);
                return true;
            case R.id.update:
                if (title.isEmpty()) {
                    edtTodo.setError("Please enter a title");
                } else if (note.isEmpty()) {
                    edtEnterNotes.setError("Please enter a note");
                } else {
                    editorPresenter.Update(id, title, note, color);
                }
                return true;
            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirm!");
                builder.setMessage("Are you sure ?");
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        editorPresenter.Delete(id);
                    }
                });
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }


    @Override
    public void onRequestSuccess(String message) {
        Toast.makeText(EditorTheme.this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onRequestError(String message) {
        Toast.makeText(EditorTheme.this, message, Toast.LENGTH_LONG).show();
        finish();
    }

    private void setDataFromEditor() {
        if (id != 0) {
            edtTodo.setText(title);
            edtEnterNotes.setText(note);
            palette.setSelectedColor(color);
            getSupportActionBar().setTitle("Update Note");
            readMode();
        } else {
            palette.setSelectedColor(getResources().getColor(R.color.white));
            color = getResources().getColor(R.color.white);
            editMode();
        }
    }

    private void editMode() {
        edtTodo.setFocusableInTouchMode(true);
        edtEnterNotes.setFocusableInTouchMode(true);
        palette.setEnabled(true);
    }

    private void readMode() {
        edtEnterNotes.setFocusableInTouchMode(false);
        edtTodo.setFocusableInTouchMode(false);
        edtTodo.setFocusable(false);
        edtEnterNotes.setFocusable(false);
        palette.setEnabled(false);
    }
}