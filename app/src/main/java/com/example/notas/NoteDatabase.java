package com.example.notas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notes_db";

    private static final String TABLE_NAME = "notes";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";

    public NoteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_CONTENT + " TEXT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_TIME + " TEXT"
                + ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    public long insertNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, note.getTitle());
        values.put(COLUMN_CONTENT, note.getContent());
        values.put(COLUMN_DATE, note.getDate());
        values.put(COLUMN_TIME, note.getTime());
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public List<Note> getAllNotes() {
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                COLUMN_ID,
                COLUMN_TITLE,
                COLUMN_CONTENT,
                COLUMN_DATE,
                COLUMN_TIME
        };
        Cursor cursor = db.query(TABLE_NAME, projection, null, null, null, null, null);
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
            String content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME));
            Note note = new Note(id, title, content, date, time);
            noteList.add(note);
        }
        cursor.close();
        db.close();
        return noteList;
    }



    public void deleteNoteById(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}