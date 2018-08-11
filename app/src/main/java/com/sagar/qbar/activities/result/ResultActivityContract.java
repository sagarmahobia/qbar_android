package com.sagar.qbar.activities.result;


import com.sagar.qbar.models.DisplayableResult;

/**
 * Created by SAGAR MAHOBIA on 10-Aug-18. at 00:16
 */


public interface ResultActivityContract {

    interface View {

        void showToast(String msg);

        void populateView(DisplayableResult displayableResult);

        void onError();

    }

    interface Presenter {
        void onCreate();

        void onDestroy();

        void onLoad(long id);
    }
}

