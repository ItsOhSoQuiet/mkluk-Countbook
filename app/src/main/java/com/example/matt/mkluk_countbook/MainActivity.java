package com.example.matt.mkluk_countbook;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/*
 * The main activity of the class
 * It shows the counters, allows the user to create a counter,
 * and allows the user to access the counters to change them.
 */
public class MainActivity extends Activity {

    /*
     * Initialize the file to load and save from
     * and the list of counters
     */
    private static final String FILENAME = "file.sav";
    private ListView counterList;

    /* Define the list of counters and the adapter */
    private ArrayList<Counter> counters = new ArrayList<Counter>();
    private ArrayAdapter<Counter> adapter;

    /* called when the app is first created */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* code the Button and ListView */
        Button addButton = (Button) findViewById(R.id.add);
        counterList = (ListView) findViewById(R.id.counterList);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        // TODO find out how to show objects
        adapter = new ArrayAdapter<Counter>(this,
                R.layout.list_main, counters);
        counterList.setAdapter(adapter);
    }

    /* load from a GSON file */
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
}
