package com.example.matt.mkluk_countbook;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Matt on 2017-09-21.
 */

public class Counter {
    // Counter class holds all of the values
    private String name;
    private Date date;
    private int initialValue;
    private int currentValue;
    private String comment;

    // Constructor
    public Counter( String name, int initialValue, String comment ) {
        this.name = name;
        date = new Date();
        // ensure that the initial and current values are non-negative
        if (initialValue < 0) {
            this.initialValue = 0;
        } else {
            this.initialValue = initialValue;
        }
        currentValue = this.initialValue;
        this.comment = comment;
    }

    // getters and setters
    public String getName() { return name; }
    public Date getDate() { return date; }
    public int getInitialValue() { return initialValue; }
    public int getCurrentValue() { return  currentValue; }
    public String getComment() { return comment; }

    public void setName( String name ) { this.name = name; }
    public void setDate() { date = new Date(); }
    // ensure that initial and current values are non-negative
    public void setInitialValue(int initialValue) {
        if ( initialValue >= 0 ) {
            this.initialValue = initialValue;
        }
    }
    public void setCurrentValue(int currentValue) {
        if ( currentValue >= 0 ) {
            this.currentValue = currentValue;
        }
    }
    public void setComment(String comment) { this.comment = comment; }

    /* methods to add and subtract from the counters, along with the date */
    public void addToCurrentValue() {
        if ( currentValue < 2147483647 ) {
            currentValue++;
            this.setDate();
        }
    }
    public void subFromCurrentValue() {
        if ( currentValue > 0 ) {
            currentValue--;
            this.setDate();
        }
    }

    /* change the initial value with the date */
    public void changeInitialValue(int newInitialValue) {
        if ( newInitialValue >= 0 ) {
            this.setCurrentValue(newInitialValue);
            this.setDate();
        }
    }

    @Override
    public String toString() {
        /* convert date to yyyy-MM-dd format */
        SimpleDateFormat yearMonthDay = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        String dateFormatted = yearMonthDay.format(date);

        /* return the format */
        return name + " |\n" + date.toString() +" |\n"
                + "Current Value | " + Integer.toString(currentValue)
                + " |\n" + dateFormatted + " |\n" + comment;
    }
}
