package com.sagar.qbar.activities.host;

import com.google.zxing.client.android.BeepManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by SAGAR MAHOBIA on 29-Jan-19. at 15:47
 */

@Module
public class HostActivityModule {


    @HostActivityScope
    @Provides
    BeepManager beepManager(HostActivity hostActivity) {
        return new BeepManager(hostActivity);
    }

}
