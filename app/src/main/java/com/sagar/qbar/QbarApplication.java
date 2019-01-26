package com.sagar.qbar;

import android.app.Activity;
import android.app.Application;

import com.google.android.gms.ads.MobileAds;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by SAGAR MAHOBIA on 12-Jan-18. at 01:28
 */

public class QbarApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    public static QbarApplication get(Activity activity) {
        return (QbarApplication) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        MobileAds.initialize(this, this.getResources().getString(R.string.app_pub_id));

        DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
                .inject(this);

    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

}
