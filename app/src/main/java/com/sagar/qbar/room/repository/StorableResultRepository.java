package com.sagar.qbar.room.repository;

import android.arch.lifecycle.LiveData;

import com.sagar.qbar.ApplicationScope;
import com.sagar.qbar.room.daos.StorableResultDao;
import com.sagar.qbar.room.entities.StorableResult;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by SAGAR MAHOBIA on 29-Jan-19. at 20:50
 */


@ApplicationScope
public class StorableResultRepository {

    private StorableResultDao storableResultDao;
    private LiveData<List<StorableResult>> allStorableResultLiveData;

    @Inject
    StorableResultRepository(StorableResultDao storableResultDao) {
        this.storableResultDao = storableResultDao;
        allStorableResultLiveData = storableResultDao.getAllStorableResultLiveData();
    }

    public LiveData<List<StorableResult>> getAllStorableResultLiveData() {
        return allStorableResultLiveData;
    }

    public LiveData<StorableResult> getStorableResultLiveData(long id) {
        return storableResultDao.load(id);
    }

    public Single<Long> insert(StorableResult result) {
        Single<Long> saveSingle = Single.create(emitter -> {
            try {
                long id = storableResultDao.insert(result);
                emitter.onSuccess(id);
            } catch (Exception error) {
                emitter.onError(error);
            }
        });
        return saveSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Completable deleteResultCompletable(long id) {
        return Completable.create(emitter -> {
            try {
                storableResultDao.delete(id);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        }).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());
    }

    public Completable deleteAllResultCompletable() {
        return Completable.create(emitter -> {
            try {
                storableResultDao.deleteAll();
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        }).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());
    }

}
