package com.sagar.qbar.daggermodule;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.sagar.qbar.ApplicationScope;
import com.sagar.qbar.room.ResultDatabase;
import com.sagar.qbar.room.daos.StorableResultDao;

import dagger.Module;
import dagger.Provides;

/**
 * Created by SAGAR MAHOBIA on 29-Jan-19. at 20:07
 */

@Module(includes = ApplicationModule.class)
public class RoomModule {

    @ApplicationScope
    @Provides
    ResultDatabase providesRoomDatabase(Context context) {
        return Room.databaseBuilder(context, ResultDatabase.class, "result_database").build();

    }

    @ApplicationScope
    @Provides
    StorableResultDao providesProductDao(ResultDatabase resultDatabase) {
        return resultDatabase.getStorableResultDao();
    }


}