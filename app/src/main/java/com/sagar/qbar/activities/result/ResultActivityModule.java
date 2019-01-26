package com.sagar.qbar.activities.result;

import dagger.Module;
import dagger.Provides;

/**
 * Created by SAGAR MAHOBIA on 10-Aug-18. at 00:14
 */
@Module
public class ResultActivityModule {

    @ResultActivityScope
    @Provides
    public ResultActivityContract.View view(ResultActivity resultActivity) {
        return resultActivity;
    }

    @ResultActivityScope
    @Provides
    ResultActivityContract.Presenter presenter(Presenter presenter) {
        return presenter;
    }


}
