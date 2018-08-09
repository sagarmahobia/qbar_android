package com.sagar.qbar.activities.result;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 10-Aug-18. at 00:17
 */


@ResultActivityScope
public class Presenter implements ResultActivityContract.Presenter {

    @Inject
    ResultActivityContract.View view;

    @Inject
    public Presenter(ResultActivityComponent component) {
        component.inject(this);
    }

}
