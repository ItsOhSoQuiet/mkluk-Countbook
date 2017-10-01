package com.example.matt.mkluk_countbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

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
 * A collection of widgets and listeners
 * that edit every part of the counter
 * up to and including deleting the counter
 */
public class EditCounterActivity extends AppCompatActivity {

    /* Load in the counters */
    private static final String FILENAME = "file.sav";
    private ArrayList<Counter> counters = new ArrayList<Counter>();

    private TextView currentView; //shows the current value
    private int counterPosition; // for accessing the counters list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_counter);

        /* get the counter position */
        Intent intent = getIntent();
        counterPosition = intent.getIntExtra("counterPosition", 0);

        /* add the buttons */
        Button minus = (Button) findViewById(R.id.minus);
        Button plus = (Button) findViewById(R.id.plus);
        Button editValues = (Button) findViewById(R.id.editValues);
        Button delete = (Button) findViewById(R.id.delete);
        Button reset = (Button) findViewById(R.id.reset);
        Button done = (Button) findViewById(R.id.done);

        /* decrement from current value */
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counters.get(counterPosition).getCurrentValue() != 0) {
                    counters.get(counterPosition).subFromCurrentValue();
                    saveInFile();
                    viewChange();
                }

            }
        });

        /* add to the current value */
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counters.get(counterPosition).addToCurrentValue();
                saveInFile();
                viewChange();
            }
        });

        /* delete the counter */
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counters.remove(counterPosition);
                saveInFile();
                Intent goBack = new Intent(EditCounterActivity.this,
                        MainActivity.class);
                startActivity(goBack);
            }
        });

        /* reset Current Value to Initial View */
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int initial = counters.get(counterPosition).getInitialValue();
                counters.get(counterPosition).setCurrentValue(initial);
                counters.get(counterPosition).setDate();
                saveInFile();
                viewChange();
            }
        });

        /* exit without changing anything */
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(EditCounterActivity.this,
                        MainActivity.class);
                startActivity(goBack);
            }
        });

        /* Go to the Edit Values Activity */
        editValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goEdit = new Intent(EditCounterActivity.this,
                        ChangeValuesActivity.class);
                goEdit.putExtra("positionToEdit", counterPosition);
                startActivity(goEdit);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        viewChange();
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

    /* change the TextView of the counter if changed */
    private void viewChange() {
        currentView = (TextView) findViewById(R.id.currView);
        currentView.setText(counters.get(counterPosition).toString());
    }
}
