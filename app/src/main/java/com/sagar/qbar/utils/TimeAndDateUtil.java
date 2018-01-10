package com.sagar.qbar.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.text.Html;
import android.text.Spanned;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by SAGAR MAHOBIA on 10-Jan-18.
 */

public class TimeAndDateUtil {


    @SuppressWarnings("deprecation")
    public static String getTimeFromTimestamp(long timestamp, Configuration configuration) {
        Locale locale;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            locale = configuration.getLocales().get(0);
        } else {
            locale = configuration.locale;
        }

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("MMM DD, YYYY hh:mm aaa", locale);
        Date date = new Date(timestamp);

        return sdf.format(date);
    }
}

