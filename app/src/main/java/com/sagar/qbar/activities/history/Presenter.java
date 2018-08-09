package com.sagar.qbar.activities.history;



import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 05-Aug-18. at 12:48
 */

@HistoryActivityScope
public class Presenter implements HistoryActivityContract.Presenter {

    @Inject
    HistoryActivityContract.View view;

    @Inject
    Presenter(HistoryActivityComponent component) {
        component.inject(this);
    }

}
