package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NotesListActivity extends AppCompatActivity {
    boolean isDeleting = false;
    NotesAdapter adapter;
    ArrayList<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        initListButton();
        initNewNoteButton();
        initSettingsButton();
        initItemClick();
        initDeleteButton();

    }

    @Override
    public void onResume() {
        super.onResume();
        String sortBy = getSharedPreferences("NotesListPreferences", Context.MODE_PRIVATE).
                getString("sortfield", "date");
        String sortOrder = getSharedPreferences("NotesListPreferences", Context.MODE_PRIVATE).
                getString("sortorder", "ASC");

        NotesDataSource ds = new NotesDataSource(NotesListActivity.this);

        try {
            ds.open();
            notes = ds.getNotes(sortBy, sortOrder);
            ds.close();
            if (notes.size() > 0) {
                ListView listView = (ListView) findViewById(R.id.lvNotes);
                adapter = new NotesAdapter(this, notes);
                listView.setAdapter(adapter);

            }
            else {
//                Intent intent = new Intent(NotesListActivity.this, NotesActivity.class);
//                startActivity(intent);

            }
        }
        catch (Exception e) {
            Toast.makeText(this, "Error retrieving notess", Toast.LENGTH_LONG).show();

        }
    }

    private void initListButton() {

        Button listButton = (Button) findViewById(R.id.buttonList);
        listButton.setEnabled(false);

    }

    private void initNewNoteButton() {

        Button newButton = (Button) findViewById(R.id.buttonNewNote);
        newButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NotesListActivity.this, NotesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
    }

    private void initSettingsButton() {

        Button settingsButton = (Button) findViewById(R.id.buttonSettings);
        settingsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NotesListActivity.this, NotesSettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

    }

    private void initItemClick() {
        ListView listView = (ListView) findViewById(R.id.lvNotes);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                Note selectedNote = notes.get(position);
                if (isDeleting) {
                    adapter.showDelete(position, itemClicked, NotesListActivity.this, selectedNote);
                }
                else {
                    Intent intent = new Intent(NotesListActivity.this, NotesActivity.class);
                    intent.putExtra("noteid", selectedNote.getNoteID());
                    startActivity(intent);

                }
            }

        });

    }

    private void initDeleteButton() {
        final Button db = (Button) findViewById(R.id.buttonDelete);
        db.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isDeleting) {
                    db.setText("Delete");
                    isDeleting = false;
                    adapter.notifyDataSetChanged();

                }
                else {
                    db.setText("Done Deleting");
                    isDeleting = true;

                }

            }

        });

    }

}
