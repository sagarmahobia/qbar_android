package com.sagar.qbar.greendao;

import com.sagar.qbar.ApplicationScope;
import com.sagar.qbar.greendao.entities.StorableResult;
import com.sagar.qbar.greendao.entities.StorableResultDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by SAGAR MAHOBIA on 10-Aug-18. at 22:02
 */

@ApplicationScope
public class ResultService {

    private StorableResultDao storableResultDao;

    @Inject
    ResultService(StorableResultDao storableResultDao) {
        this.storableResultDao = storableResultDao;
    }

    public Single<Long> saveResultSingle(StorableResult result) {
        Single<Long> saveSingle = Single.create(emitter -> {
            long id = storableResultDao.insert(result);
            emitter.onSuccess(id);
        });
        return saveSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<StorableResult>> loadAllResultsSingle() {
        Single<List<StorableResult>> single = Single.create(emitter -> {
            emitter.onSuccess(storableResultDao.loadAll());
        });
        return single
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<StorableResult> loadResultSingle(long id) {
        Single<StorableResult> resultSingle = Single.create(emitter -> {
            emitter.onSuccess(storableResultDao.load(id));
        });
        return resultSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Completable deleteResultCompletable(long id) {
        return Completable.create(emitter -> {
            storableResultDao.deleteByKey(id);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());
    }

    public Completable deleteAllResultCompletable() {
        return Completable.create(emitter -> {
            storableResultDao.deleteAll();
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());
    }


}


