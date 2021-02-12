package com.institutotransire.events.controller;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTime {

    public static String getDate(long timeInMillies) {
        String date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        date = formatter.format(timeInMillies);
        System.out.println("Today is " + date);
        return date;
    }

}
