package com.sagar.qbar;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

/**
 * Created by SAGAR MAHOBIA on 12-Jan-18. at 01:28
 */

public class QbarApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this, this.getResources().getString(R.string.app_pub_id));

    }
}
