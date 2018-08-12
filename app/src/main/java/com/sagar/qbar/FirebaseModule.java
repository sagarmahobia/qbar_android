package com.sagar.qbar;

import android.content.Context;

import com.google.firebase.analytics.FirebaseAnalytics;

import dagger.Module;
import dagger.Provides;

/**
 * Created by SAGAR MAHOBIA on 07-Aug-18. at 20:09
 */

@Module(includes = ApplicationModule.class)
class FirebaseModule {

    @Provides
    @ApplicationScope
    FirebaseAnalytics getFirebaseAnalytics(Context context) {
        return FirebaseAnalytics.getInstance(context);
    }

}
