package com.sagar.qbar.onclickutil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by SAGAR MAHOBIA on 10-Jan-18.
 */

public class SearchUtil {

    public static void searchText(Context context, String textToSearch) {
       OpenUrlUtil.openUrl("http://google.com/search?q=" + Uri.encode(textToSearch),context);

    }

}
