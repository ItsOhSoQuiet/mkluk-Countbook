package com.example.matt.mkluk_countbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
