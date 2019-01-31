package com.sagar.qbar;

import com.sagar.qbar.activities.host.FragmentProvider;
import com.sagar.qbar.activities.host.HostActivity;
import com.sagar.qbar.activities.host.HostActivityModule;
import com.sagar.qbar.activities.host.HostActivityScope;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by SAGAR MAHOBIA on 27-Jan-19. at 01:52
 */

@Module
abstract class ActivityProvider {


    @ContributesAndroidInjector(modules = {HostActivityModule.class, FragmentProvider.class})
    @HostActivityScope
    abstract HostActivity hostActivity();

}
