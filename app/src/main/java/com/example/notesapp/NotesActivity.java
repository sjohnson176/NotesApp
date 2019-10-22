package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class NotesActivity extends AppCompatActivity {
    private Note currentNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        initNotesButton();
        setForEditing(true);
        initTextChangedEvents();
        initSaveButton();
        initPriorityClick();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            initNote(extras.getInt("noteid"));
        }
        else {
            currentNote = new Note();
        }

    }


    public void initNotesButton() {
        Button notesButton = (Button) findViewById(R.id.buttonNotes);
        notesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotesActivity.this, NotesListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }

        });

    }

    private void setForEditing(boolean enabled) {
        EditText title = (EditText) findViewById(R.id.editTitle);
        EditText notes = (EditText) findViewById(R.id.editBody);

        title.setEnabled(enabled);
        notes.setEnabled(enabled);

        if (enabled) {
            notes.requestFocus();

        }

    }

    public void initTextChangedEvents() {
        final EditText etNoteTitle = (EditText) findViewById(R.id.editTitle);
        etNoteTitle.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable editable) {
                currentNote.setNoteTitle(etNoteTitle.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        final EditText etNoteBody = (EditText) findViewById(R.id.editBody);
        etNoteBody.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable editable) {
                currentNote.setNoteBody(etNoteBody.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

        });

    }

    public void initPriorityClick() {
        RadioGroup rgPriority = (RadioGroup) findViewById(R.id.rgPriority);
        rgPriority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                RadioButton rbHP = (RadioButton) findViewById(R.id.rbHP);
                RadioButton rbMP = (RadioButton) findViewById(R.id.rbMP);
                RadioButton rbLP = (RadioButton) findViewById(R.id.rbLP);

                if (rbHP.isChecked()) {
                    currentNote.setPriority(3);

                }
                else if (rbMP.isChecked()) {
                    currentNote.setPriority(2);

                }
                else if (rbLP.isChecked()) {
                    currentNote.setPriority(1);

                }
            }
        });
    }

    private void initSaveButton() {
        Button saveButton = (Button) findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean wasSuccessful = false;
                NotesDataSource ds = new NotesDataSource(NotesActivity.this);
                try {
                    ds.open();

                    if (currentNote.getNoteID() == -1) {
                        wasSuccessful = ds.insertNote(currentNote);
                        if (wasSuccessful) {
                            int newID = ds.getLastNoteID();
                            currentNote.setNoteID(newID);

                        }

                    }
                    else {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss");
                        Calendar newDate = Calendar.getInstance();
                        String date = formatter.format(newDate.getTime());
                        currentNote.setNoteDate(date);

                        wasSuccessful = ds.updateNote(currentNote);

                    }
                    ds.close();

                }
                catch (Exception e) {
                    wasSuccessful = false;

                }

                if (wasSuccessful) {
                    Intent intent = new Intent(NotesActivity.this, NotesListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }

            }

        });

    }

    public void initNote(int id) {
        NotesDataSource ds = new NotesDataSource(NotesActivity.this);
        try {
            ds.open();
            currentNote = ds.getSpecificNote(id);
            ds.close();

        }
        catch (Exception e) {
            Toast.makeText(this, "Load Note Failed", Toast.LENGTH_LONG).show();

        }

        EditText editTitle = (EditText) findViewById(R.id.editTitle);
        EditText editBody = (EditText) findViewById(R.id.editBody);
        RadioButton nbHP = (RadioButton) findViewById(R.id.rbHP);
        RadioButton nbMP = (RadioButton) findViewById(R.id.rbMP);
        RadioButton nbLP = (RadioButton) findViewById(R.id.rbLP);

        editTitle.setText(currentNote.getNoteTitle());
        editBody.setText(currentNote.getNoteBody());
        if (currentNote.getPriority() == 3) {
            nbHP.setChecked(true);

        }
        else if (currentNote.getPriority() == 2) {
            nbMP.setChecked(true);

        }
        else {
            nbLP.setChecked(true);

        }

    }

}
