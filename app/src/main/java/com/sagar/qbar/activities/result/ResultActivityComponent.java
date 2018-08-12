package com.sagar.qbar.activities.result;

import com.sagar.qbar.ApplicationComponent;

import dagger.Component;

/**
 * Created by SAGAR MAHOBIA on 10-Aug-18. at 00:18
 */

@ResultActivityScope
@Component(modules = ResultActivityModule.class, dependencies = ApplicationComponent.class)
public interface ResultActivityComponent {

    void inject(Presenter presenter);

    void inject(ResultActivity resultActivity);

}
