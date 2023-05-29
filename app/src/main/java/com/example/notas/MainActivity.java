package com.example.notas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<Note> noteList;
    NoteAdapter noteAdapter;
    NoteDatabase noteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.Listado);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        noteList = new ArrayList<>();
        noteAdapter = new NoteAdapter(noteList);
        recyclerView.setAdapter(noteAdapter);

        noteDatabase = new NoteDatabase(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add) {
            Intent intent = new Intent(MainActivity.this, AgregarNota.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    private void loadNotes() {
        noteList.clear();
        noteList.addAll(noteDatabase.getAllNotes());

        for (Note note : noteList) {
            Log.d("Note", "Title: " + note.getTitle() + ", Date: " + note.getDate() + "TIME: " + note.getTime());
        }

        noteAdapter.notifyDataSetChanged();

        if (noteList.size() > 1) {
            Log.d("Note", "La base de datos tiene más de una nota");
        } else if (noteList.size() == 1) {
            Log.d("Note", "La base de datos tiene una nota");
        } else {
            Log.d("Note", "La base de datos está vacía");
        }
    }
}
