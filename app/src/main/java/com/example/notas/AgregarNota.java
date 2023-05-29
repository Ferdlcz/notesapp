package com.example.notas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AgregarNota extends AppCompatActivity {
    Toolbar toolbar;
    EditText noteTitle, noteDetails;
    NoteDatabase noteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nota);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        noteTitle = findViewById(R.id.noteTitle);
        noteDetails = findViewById(R.id.noteDetails);

        noteDatabase = new NoteDatabase(getApplicationContext());

        Button btnSaveNote = findViewById(R.id.btnSaveNote);

        btnSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = noteTitle.getText().toString();
                String content = noteDetails.getText().toString();

                String currentDate = getCurrentDate();
                String currentTime = getCurrentTime();


                Note newNote = new Note(title, content, currentDate, currentTime);

                long insertedId = noteDatabase.insertNote(newNote);

                if (insertedId != -1) {
                    newNote.setID(insertedId);
                    Toast.makeText(AgregarNota.this, "Nota guardada correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AgregarNota.this, "Error al guardar la nota", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }

    private String getCurrentTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date currentTime = new Date();
        return timeFormat.format(currentTime);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (noteDatabase != null) {
            noteDatabase.close();
        }
    }
}
