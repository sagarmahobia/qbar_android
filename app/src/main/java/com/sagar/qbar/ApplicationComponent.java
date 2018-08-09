package com.sagar.qbar;

import dagger.Component;

/**
 * Created by SAGAR MAHOBIA on 09-Aug-18. at 23:34
 */


@ApplicationScope
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(QbarApplication qbarApplication);

}
