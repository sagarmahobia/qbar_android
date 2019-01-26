package com.sagar.qbar.daggermodule;

import com.google.android.gms.ads.AdRequest;

import dagger.Module;
import dagger.Provides;

/**
 * Created by SAGAR MAHOBIA on 07-Aug-18. at 16:37
 */
@Module()
public class AdmobModule {

    @Provides
    public AdRequest request() {
        return new AdRequest.Builder().addTestDevice("72ADA023573A4638309C127E0B87CEF4").build();
    }
}
