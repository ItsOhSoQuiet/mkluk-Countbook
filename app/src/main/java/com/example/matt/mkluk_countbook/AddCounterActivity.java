package com.example.matt.mkluk_countbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

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
