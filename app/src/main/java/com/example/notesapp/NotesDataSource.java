package com.example.notesapp;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.SQLException;
import android.database.Cursor;
import android.content.ContentValues;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.io.ByteArrayOutputStream;

public class NotesDataSource {
    private SQLiteDatabase database;
    private  NotesDBHelper dbHelper;

    public NotesDataSource(Context context) {
        dbHelper = new NotesDBHelper(context);

    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();

    }

    public void close() {
        dbHelper.close();

    }

    public boolean insertNote(Note n) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put("title", n.getNoteTitle());
            initialValues.put("body", n.getNoteBody());
            initialValues.put("priority", n.getPriority());
            initialValues.put("date", n.getNoteDate());

            didSucceed = database.insert("notes", null, initialValues) > 0;

        }
        catch (Exception e) {
            // do nothing will return false if there is an exception
        }
        return didSucceed;

    }

    public boolean updateNote(Note n) {
        boolean didSucceed = false;
        try {
            Long rowId = (long) n.getNoteID();
            ContentValues updateValues = new ContentValues();

            updateValues.put("title", n.getNoteTitle());
            updateValues.put("body", n.getNoteBody());
            updateValues.put("priority", n.getPriority());
            updateValues.put("date", n.getNoteDate());

            didSucceed = database.update("notes", updateValues, "_id = " + rowId,
                    null) > 0;

        }
        catch (Exception e) {
            // do nothing will return false if there is an exception
        }
        return didSucceed;
    }

    public ArrayList<Note> getNotes(String sortField, String sortOrder) {
        ArrayList<Note> notes = new ArrayList<Note>();
        try {
            String query = "select * from notes order by " + sortField + " " + sortOrder;
            Cursor cursor = database.rawQuery(query, null);

            Note newNote;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newNote = new Note();
                newNote.setNoteID(cursor.getInt(0));
                newNote.setNoteTitle(cursor.getString(1));
                newNote.setNoteBody(cursor.getString(2));
                newNote.setPriority(cursor.getInt(3));
                newNote.setNoteDate(cursor.getString(4));
                notes.add(newNote);
                cursor.moveToNext();

            }
            cursor.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return notes;

    }

    public int getLastNoteID() {
        int lastID = -1;
        try {
            String query = "select max(_id) from notes";
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            lastID = cursor.getInt(0);
            cursor.close();

        }
        catch (Exception e) {
            lastID = -1;

        }
        return lastID;
    }

    public Note getSpecificNote(int noteID) {
        Note note = new Note();
        String query = "select * from notes where _id = " + noteID;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            note.setNoteID(cursor.getInt(0));
            note.setNoteTitle(cursor.getString(1));
            note.setNoteBody(cursor.getString(2));
            note.setPriority(cursor.getInt(3));;
            note.setNoteDate(cursor.getString(4));

            cursor.close();
        }
        return note;

    }

    public boolean deleteNote(int noteID) {
        boolean didDelete = false;
        try {
            didDelete = database.delete("notes", "_id = " + noteID, null) > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
            // do nothing : return value already set to false
        }
        return didDelete;

    }

}
