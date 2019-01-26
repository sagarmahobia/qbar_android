package com.sagar.qbar;

import android.app.Application;

import com.google.android.gms.ads.AdRequest;
import com.sagar.qbar.daggermodule.ApplicationModule;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by SAGAR MAHOBIA on 09-Aug-18. at 23:34
 */


@ApplicationScope
@Component(modules = {AndroidInjectionModule.class,
        ActivityProvider.class,
        ApplicationModule.class})
public interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }

    void inject(QbarApplication application);

    AdRequest provideAdRequest();

}
