package com.sagar.qbar.activities.result;


import com.sagar.qbar.models.DisplayableResult;

/**
 * Created by SAGAR MAHOBIA on 10-Aug-18. at 00:16
 */


public interface ResultActivityContract {

    interface View {

        void populateView(DisplayableResult displayableResult);
    }

    interface Presenter {
        void onCreate();

        void onDestroy();

        void onLoad(long id);
    }
}

