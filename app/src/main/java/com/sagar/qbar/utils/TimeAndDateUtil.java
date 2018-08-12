package com.sagar.qbar.utils;

import android.annotation.SuppressLint;

import com.crashlytics.android.Crashlytics;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SAGAR MAHOBIA on 10-Jan-18.
 */

public class TimeAndDateUtil {

    @SuppressWarnings("deprecation")
    public static String getTimeFromTimestamp(long timestamp) {

        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, YYYY hh:mm a");
            Date date = new Date(timestamp);
            return sdf.format(date);

        } catch (IllegalArgumentException e) {
            Crashlytics.logException(e);
            return "";
        }

    }
}

