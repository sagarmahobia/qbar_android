package com.sagar.qbar.activities.history;

import com.sagar.qbar.ApplicationComponent;

import dagger.Component;

/**
 * Created by SAGAR MAHOBIA on 05-Aug-18. at 12:55
 */

@HistoryActivityScope
@Component(modules = HistoryActivityModule.class, dependencies = ApplicationComponent.class)
public interface HistoryActivityComponent {

    void inject(HistoryActivity historiesActivity);

    void inject(Presenter presenter);
}
