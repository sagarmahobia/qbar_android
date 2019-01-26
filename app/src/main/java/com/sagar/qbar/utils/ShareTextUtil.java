package com.sagar.qbar.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by SAGAR MAHOBIA on 10-Jan-18.
 */

public class ShareTextUtil {

    public static void share(Context context, String textToShare) {


        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, textToShare);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }
}
