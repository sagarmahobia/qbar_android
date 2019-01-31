package com.sagar.qbar.room.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.sagar.qbar.room.entities.StorableResult;

import java.util.List;

/**
 * Created by SAGAR MAHOBIA on 29-Jan-19. at 19:58
 */
@Dao
public interface StorableResultDao {

    @Insert
    Long insert(StorableResult storableResult);

    @Query("DELETE FROM results")
    void deleteAll();

    @Query("SELECT * from results ORDER BY timestamp DESC")
    LiveData<List<StorableResult>> getAllStorableResultLiveData();

    @Query("SELECT * from results WHERE id = :id")
    LiveData<StorableResult> load(long id);

    @Query("DELETE from results WHERE id = :id")
    void delete(long id);
}
