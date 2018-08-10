package com.sagar.qbar;

import android.content.Context;

import com.sagar.qbar.greendao.DbOpenHelper;
import com.sagar.qbar.greendao.entities.DaoMaster;
import com.sagar.qbar.greendao.entities.DaoSession;
import com.sagar.qbar.greendao.entities.StorableResultDao;

import dagger.Module;
import dagger.Provides;

/**
 * Created by SAGAR MAHOBIA on 03-Aug-18. at 23:54
 */
@Module(includes = ApplicationModule.class)
class GreenDaoModule {

    private static final String DB_NAME = "user_data.db";

    @ApplicationScope
    @Provides
    DaoSession getDaoSession(Context context) {
        return new DaoMaster(new DbOpenHelper(context, DB_NAME).getWritableDb()).newSession();
    }

    @ApplicationScope
    @Provides
    StorableResultDao getScanResultDao(DaoSession daoSession) {
        return daoSession.getStorableResultDao();
    }


}
