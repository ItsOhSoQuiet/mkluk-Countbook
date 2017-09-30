package com.example.matt.mkluk_countbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class AddCounterActivity extends Activity {

    /* get the filename and the counters */
    private static final String FILENAME = "file.sav";
    private ArrayList<Counter> counters = new ArrayList<Counter>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_counter);

        /* get the intent */
        Intent intent = getIntent();

        /* get the buttons and EditTexts */
        Button ok = (Button) findViewById(R.id.ok);
        Button cancel = (Button) findViewById(R.id.cancel);

        /* cancel without affecting the counter */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCounterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        /* add the counter */
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Get the text from the boxes */
                EditText nameMsg = (EditText) findViewById(R.id.nameMsg);
                EditText initMsg = (EditText) findViewById(R.id.initMsg);
                EditText commentMsg = (EditText) findViewById(R.id.commentMsg);
                String countName = nameMsg.getText().toString();
                String countInit = initMsg.getText().toString();
                String countComment = commentMsg.getText().toString();

                /*
                 * If name or initial value is empty, return an error.
                 * else, save the counter and go back to the main activity
                 */
                if (countName.isEmpty() || countInit.isEmpty()) {
                    Toast.makeText(AddCounterActivity.this,
                            "Need name and initial value",
                            Toast.LENGTH_LONG).show();
                } else {
                    int initValue = Integer.parseInt(countInit);
                    Counter counter = new Counter(countName, initValue, countComment);
                    counters.add(counter);
                    saveInFile();

                    /* go back to the main activity */
                    Intent intent1 = new Intent(AddCounterActivity.this,
                            MainActivity.class);
                    startActivity(intent1);
                }
            }
        });
    }

    /* get the list of Counters */
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
