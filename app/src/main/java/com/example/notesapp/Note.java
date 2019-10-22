package com.example.notesapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Note {

    private int noteID;
    private String noteTitle;
    private String noteBody;
    private int priority;
    private String noteDate;

    public Note() {
        noteID = -1;
        noteTitle = "Untitled";
        priority = 3;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String date = formatter.format(calendar.getTime());
        noteDate = date;

    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
