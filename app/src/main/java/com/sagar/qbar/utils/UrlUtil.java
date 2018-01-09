package com.sagar.qbar.utils;

import android.util.Patterns;

/**
 * Created by SAGAR MAHOBIA on 10-Jan-18.
 */

public class UrlUtil {

    public static boolean checkUrl(String text) {
        return Patterns.WEB_URL.matcher(text.toLowerCase()).matches();
    }
}
