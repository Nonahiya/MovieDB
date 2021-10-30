package com.enricowakiman.moviedb.helper;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatting {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String dateFormat(String string) {
            LocalDate date = LocalDate.parse(string);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return date.format(formatter);
    }
}
