package com.sagar.qbar.activities.scanner;

import com.sagar.qbar.greendao.entities.StorableResult;

/**
 * Created by SAGAR MAHOBIA on 09-Aug-18. at 23:55
 */
public interface ScannerActivityContract {
    interface View {
        void startResultActivity(long id);
    }

    interface Presenter {
        void onCreate();

        void onDestroy();

        void onHandleResult(StorableResult result);

    }

}
