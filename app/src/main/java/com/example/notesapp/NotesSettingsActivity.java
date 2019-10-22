package com.example.notesapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class NotesSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_settings);

        initListButton();
        initNewNoteButton();
        initSettingsButton();
        initSortByClick();
        initSortOrderClick();
        initSettings();

    }

    public void initListButton() {

        Button bListButton = (Button) findViewById(R.id.buttonList);
        bListButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NotesSettingsActivity.this, NotesListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

    }

    public void initNewNoteButton() {

        Button bNewButton = (Button) findViewById(R.id.buttonNewNote);
        bNewButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NotesSettingsActivity.this, NotesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
    }

    public void initSettingsButton() {

        Button bSettings = (Button) findViewById(R.id.buttonSettings);
        bSettings.setEnabled(false);

    }

    public void initSortByClick() {

        RadioGroup rgSortyBy = (RadioGroup) findViewById(R.id.rgSortBy);
        rgSortyBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rbP = (RadioButton) findViewById(R.id.rbPriority);
                if (rbP.isChecked()) {
                    getSharedPreferences("NotesListPreferences", Context.MODE_PRIVATE).edit().
                            putString("sortfield", "priority").commit();

                }
                else {
                    getSharedPreferences("NotesListPreferences", Context.MODE_PRIVATE).edit().
                            putString("sortfield", "date").commit();

                }

            }

        });

    }

    public void initSortOrderClick() {
        RadioGroup rgSortOrder = (RadioGroup) findViewById(R.id.rgSortOrder);
        rgSortOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rbASC = (RadioButton) findViewById(R.id.rbASC);
                if (rbASC.isChecked()) {
                    getSharedPreferences("NotesListPreferences", Context.MODE_PRIVATE).edit().
                            putString("sortorder", "ASC").commit();

                }
                else {
                    getSharedPreferences("NotesListPreferences", Context.MODE_PRIVATE).edit().
                            putString("sortorder", "DESC").commit();

                }

            }
        });
    }


    public void initSettings() {

        String sortBy = getSharedPreferences("NotesListPreferences", Context.MODE_PRIVATE).
                getString("sortfield", "date");
        String sortOrder = getSharedPreferences("NotesListPreferences", Context.MODE_PRIVATE).
                getString("sortorder", "ASC");

        RadioButton rbP = (RadioButton) findViewById(R.id.rbPriority);
        RadioButton rbD = (RadioButton) findViewById(R.id.rbDate);

        if (sortBy.equalsIgnoreCase("priority")) {
            rbP.setChecked(true);

        }
        else {
            rbD.setChecked(true);

        }

        RadioButton rbASC = (RadioButton) findViewById(R.id.rbASC);
        RadioButton rbDESC = (RadioButton) findViewById(R.id.rbDESC);

        if (sortOrder.equalsIgnoreCase("ASC")) {
            rbASC.setChecked(true);

        }
        else {
            rbDESC.setChecked(true);

        }
    }
}
