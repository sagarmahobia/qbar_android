package com.sagar.qbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by SAGAR MAHOBIA on 09-Aug-18. at 23:34
 */

@Module
public class ApplicationModule {

    private Context context;


    ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    @ApplicationScope
    public Context getContext() {
        return context;
    }


    @ApplicationScope
    @Provides
    SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}


