package com.example.notesapp;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
//import android.view.View.OnClickListener;


public class NotesAdapter extends ArrayAdapter<Note> {

    private ArrayList<Note> items;
    private Context adapterContext;
    private int currentNoteID;

    public NotesAdapter(Context context,  ArrayList<Note> items) {
        super(context, R.layout.note_item, items);
        adapterContext = context;
        this.items = items;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        currentNoteID = position;
        View v = convertView;
        try {
            Note note = items.get(position);

            if (v == null) {
                LayoutInflater vi = (LayoutInflater)
                        adapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.note_item, null);

            }

            TextView noteTitle = (TextView) v.findViewById(R.id.textNoteTitle);
            TextView noteBody = (TextView) v.findViewById(R.id.textNoteBody);
            TextView noteDate = (TextView) v.findViewById(R.id.textNoteDateTime);
            TextView notePriority = (TextView) v.findViewById(R.id.textNotePriority);
            Button deleteButton = (Button) v.findViewById(R.id.buttonDeleteNote);

            noteTitle.setText(note.getNoteTitle());
            if (note.getNoteBody().length() > 15) {
                noteBody.setText(note.getNoteBody().substring(0, 14) + "...");

            }
            else {
                noteBody.setText(note.getNoteBody());

            }
            if (note.getPriority() == 3) {
                notePriority.setText("HIGH");

            }
            else if (note.getPriority() == 2) {
                notePriority.setText("MED");

            }
            else {
                notePriority.setText("LOW");

            }
            noteDate.setText(note.getNoteDate());
            deleteButton.setVisibility(View.INVISIBLE);

        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return v;

    }

    public void showDelete(final int position, final View convertView, final Context context,
                           final Note note) {
        View v = convertView;
        final Button b = (Button) v.findViewById(R.id.buttonDeleteNote);
        if (b.getVisibility() == View.INVISIBLE) {
            b.setVisibility(View.VISIBLE);
            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    hideDelete(position, convertView, context);
                    items.remove(note);
                    deleteOption(note.getNoteID(), context);

                }

            });

        }
        else {
            hideDelete(position, convertView, context);

        }

    }

    private void deleteOption(int noteToDelete, Context context) {
        NotesDataSource db = new NotesDataSource(context);
        try {
            db.open();
            db.deleteNote(noteToDelete);
            db.close();

        }
        catch (Exception e) {
            Toast.makeText(adapterContext, "Delete Note Failed", Toast.LENGTH_LONG).show();

        }
        this.notifyDataSetChanged();

    }

    public void hideDelete(int position, View convertView, Context context) {
        View v = convertView;
        final Button b = v.findViewById(R.id.buttonDeleteNote);
        b.setVisibility(View.INVISIBLE);
        b.setOnClickListener(null);

    }


}
