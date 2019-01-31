package com.sagar.qbar.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.sagar.qbar.room.converters.Converter;
import com.sagar.qbar.room.daos.StorableResultDao;
import com.sagar.qbar.room.entities.StorableResult;

/**
 * Created by SAGAR MAHOBIA on 29-Jan-19. at 20:04
 */

@Database(entities = {StorableResult.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class ResultDatabase extends RoomDatabase {

    public abstract StorableResultDao getStorableResultDao();


}
