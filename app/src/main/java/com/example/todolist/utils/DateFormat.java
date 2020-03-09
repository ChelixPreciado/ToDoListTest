package com.example.todolist.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormat {

    public static String getFormattedDate(long timeStamp) {
        String d = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        Date date = new Date(timeStamp);
        d = sdf.format(date);
        return d;
    }

}
