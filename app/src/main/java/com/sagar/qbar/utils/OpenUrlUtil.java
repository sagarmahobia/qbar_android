package com.sagar.qbar.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by SAGAR MAHOBIA on 10-Jan-18.
 */

public class OpenUrlUtil {

    public static void openUrl(String url, Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);

    }
}
