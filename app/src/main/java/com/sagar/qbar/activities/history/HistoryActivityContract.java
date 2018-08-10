package com.sagar.qbar.activities.history;


import com.sagar.qbar.activities.history.adapter.HistoryCard;

/**
 * Created by SAGAR MAHOBIA on 05-Aug-18. at 12:45
 */
public interface HistoryActivityContract {

    interface View {

        void startResultActivity(long id);

        void notifyAdapter();
    }

    interface Presenter {
        void onCreate();

        void onLoad();

        void onDestroy();

        void onBindHistory(HistoryCard card, int position);

        int getHistoryCount();

        void onClickItem(int position);

        void onClickDeleteItem(int position);

        void onClearAll();
    }

}
