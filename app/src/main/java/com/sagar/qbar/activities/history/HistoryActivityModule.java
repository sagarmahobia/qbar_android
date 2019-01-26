package com.sagar.qbar.activities.history;

import dagger.Module;
import dagger.Provides;

/**
 * Created by SAGAR MAHOBIA on 05-Aug-18. at 12:44
 */

@Module
public class HistoryActivityModule {

    @Provides
    @HistoryActivityScope
    HistoryActivityContract.View view(HistoryActivity activity) {
        return activity;
    }

    @Provides
    @HistoryActivityScope
    HistoryActivityContract.Presenter presenter(Presenter presenter) {
        return presenter;
    }

}
