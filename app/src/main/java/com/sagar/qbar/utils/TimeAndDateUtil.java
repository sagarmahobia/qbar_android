package com.sagar.qbar.utils;

import android.content.Context;
import android.content.res.Configuration;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by SAGAR MAHOBIA on 10-Jan-18.
 */

public class TimeAndDateUtil {


    @SuppressWarnings("deprecation")
    public static String getTimeFromTimestamp(long timestamp, Context context) {
        Locale locale;
        Configuration configuration = context.getResources().getConfiguration();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            locale = configuration.getLocales().get(0);
        } else {
            locale = configuration.locale;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM DD, YYYY hh:mm a", locale);
            Date date = new Date(timestamp);

            return sdf.format(date);

        } catch (IllegalArgumentException e) {
            FirebaseAnalytics.getInstance(context).logEvent("ErrorInDateTimeUtil", null);
            return "";
        }

    }
}

