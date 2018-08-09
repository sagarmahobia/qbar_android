package com.sagar.qbar;

import android.app.Activity;
import android.app.Application;

import com.google.android.gms.ads.MobileAds;

/**
 * Created by SAGAR MAHOBIA on 12-Jan-18. at 01:28
 */

public class QbarApplication extends Application {

    private ApplicationComponent component;

    public ApplicationComponent getComponent() {
        return component;
    }

    public static QbarApplication get(Activity activity) {
        return (QbarApplication) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.
                builder().
                applicationModule(new ApplicationModule(this)).
                build();
        component.inject(this);
        MobileAds.initialize(this, this.getResources().getString(R.string.app_pub_id));

    }
}
