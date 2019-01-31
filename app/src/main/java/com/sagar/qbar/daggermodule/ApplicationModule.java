package com.sagar.qbar.daggermodule;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sagar.qbar.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by SAGAR MAHOBIA on 09-Aug-18. at 23:34
 */

@Module(includes = {FirebaseModule.class,
        AdmobModule.class,
        RoomModule.class})
public class ApplicationModule {

    @Provides
    @ApplicationScope
    Context provideContext(Application application) {
        return application;
    }

    @ApplicationScope
    @Provides
    SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}


