package com.sagar.qbar.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by SAGAR MAHOBIA on 10-Jan-18.
 */

public class OpenUrlUtil {

    public static void openUrl(Context context, String url) {
        Intent baseIntent = new Intent(Intent.ACTION_VIEW);
        baseIntent.setData(Uri.parse(url));

        Intent chooserIntent = Intent.createChooser(baseIntent, "Select Application");
        context.startActivity(chooserIntent);
    }
}
