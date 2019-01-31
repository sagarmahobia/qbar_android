package com.sagar.qbar.utils;

import android.content.Context;
import android.net.Uri;

/**
 * Created by SAGAR MAHOBIA on 10-Jan-18.
 */

public class SearchUtil {

    public static void searchText(Context context, String textToSearch) {
        OpenUrlUtil.openUrl(context, "http://google.com/search?q=" + Uri.encode(textToSearch));

    }

}
