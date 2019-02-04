package com.sagar.qbar.activities.host.results.wifi;

import android.content.Context;
import android.net.wifi.WifiManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by SAGAR MAHOBIA on 04-Feb-19. at 20:23
 */

@Module
public class WifiFragmentModule {

    @Provides
    @WifiFragmentScope
    WifiManager wifiManager(Context context) {
        return (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

    }
}
