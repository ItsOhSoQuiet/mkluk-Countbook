package com.example.matt.mkluk_countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
public class MainActivity extends AppCompatActivity {

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
        Button addButton = (Button) findViewById(R.id.plus);
        counterList = (ListView) findViewById(R.id.counterList);

        /* go to AddCounterActivity */
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCounterActivity.class);
                startActivity(intent);
            }
        });

        /* go to activity to edit the counter */
        counterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editIntent = new Intent(MainActivity.this, EditCounterActivity.class);
                int countPosition = position;
                editIntent.putExtra("counterPosition", position);
                startActivity(editIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        /* load in the counters */
        super.onStart();
        loadFromFile();

        /* show the number of counters */
        String numOfCounts = Integer.toString(counters.size());
        String numCountMsg;
        if (counters.size() == 1) {
            numCountMsg = numOfCounts + " Counter";
        } else {
            numCountMsg = numOfCounts + " Counters";
        }
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(numCountMsg);

        adapter = new ArrayAdapter<Counter>(this,
                android.R.layout.simple_list_item_1, counters);
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
