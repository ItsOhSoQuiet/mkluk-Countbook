package com.example.matt.mkluk_countbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/*
 * An activity that allows users to edit
 * every part of a Counter object,
 * except for the date.
 */
public class ChangeValuesActivity extends AppCompatActivity {

    /* get the counters */
    private static final String FILENAME = "file.sav";
    private ArrayList<Counter> counters = new ArrayList<Counter>();
    private int counterPosition;

    /* initialize the buttons */
    private Button newName;
    private Button newInit;
    private Button newCurr;
    private Button newComm;
    private Button okEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_values);

        /* get the counter to edit */
        Intent intent = getIntent();
        counterPosition = intent.getIntExtra("positionToEdit", 0);

        /* get the buttons */
        newName = (Button) findViewById(R.id.newName);
        newInit = (Button) findViewById(R.id.newInit);
        newCurr = (Button) findViewById(R.id.newCurr);
        newComm = (Button) findViewById(R.id.newComm);
        okEdit = (Button) findViewById(R.id.okEdit);

        /* change the name */
        newName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editName = (EditText) findViewById(R.id.editName);
                String countName = editName.getText().toString();

                if (countName.isEmpty()) {
                    Toast.makeText(ChangeValuesActivity.this,
                            "No name entered", Toast.LENGTH_SHORT).show();
                } else {
                    counters.get(counterPosition).setName(countName);
                    saveInFile();
                    Toast.makeText(ChangeValuesActivity.this,
                            "Name changed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /* change the Initial Value */
        newInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editInit = (EditText) findViewById(R.id.editInit);
                String countInit = editInit.getText().toString();

                if (countInit.isEmpty()) {
                    Toast.makeText(ChangeValuesActivity.this,
                            "No value entered", Toast.LENGTH_SHORT).show();
                } else {
                    int countInitInt = Integer.parseInt(countInit);
                    counters.get(counterPosition).changeInitialValue(countInitInt);
                    saveInFile();
                    Toast.makeText(ChangeValuesActivity.this,
                            "Initial Value changed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        /* change the Current Value */
        newCurr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editCurr = (EditText) findViewById(R.id.editCurr);
                String countCurr = editCurr.getText().toString();

                if (countCurr.isEmpty()) {
                    Toast.makeText(ChangeValuesActivity.this,
                            "No value entered", Toast.LENGTH_SHORT).show();
                } else {
                    int countCurrInt = Integer.parseInt(countCurr);
                    counters.get(counterPosition).setCurrentValue(countCurrInt);
                    saveInFile();
                    Toast.makeText(ChangeValuesActivity.this,
                            "Current Value changed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /* change the name */
        newComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editComm = (EditText) findViewById(R.id.editComm);
                String countComm = editComm.getText().toString();

                counters.get(counterPosition).setComment(countComm);
                saveInFile();
                Toast.makeText(ChangeValuesActivity.this,
                        "Comments changed", Toast.LENGTH_SHORT).show();

            }
        });

        /* Go back to the main menu */
        okEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMain = new Intent(ChangeValuesActivity.this,
                        MainActivity.class);
                startActivity(goToMain);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
    }

    /* load from a JSON file */
    private void loadFromFile() {
        try {
            /* Load in the data from the file */
            FileInputStream fIn = openFileInput(FILENAME);
            BufferedReader inRead = new BufferedReader(new InputStreamReader(fIn));

            /*
             * access from the GSON file
             * Taken from lonelyTwitter lab code
             */
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Counter>>() {}.getType();
            counters = gson.fromJson(inRead, listType);

        } catch (FileNotFoundException e) {
            counters = new ArrayList<Counter>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
         * save the new JSON values
         * adapted from lonelyTwitter code
         */
    private void saveInFile() {
        try {
            FileOutputStream fOut = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);

            OutputStreamWriter writer = new OutputStreamWriter(fOut);
            Gson gson = new Gson();
            gson.toJson(counters, writer);
            writer.flush();

            fOut.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
