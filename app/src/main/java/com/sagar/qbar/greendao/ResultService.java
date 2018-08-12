package com.sagar.qbar.greendao;

import com.sagar.qbar.ApplicationScope;
import com.sagar.qbar.greendao.entities.StorableResult;
import com.sagar.qbar.greendao.entities.StorableResultDao;

import java.util.Collections;
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

    public Single<List<StorableResult>> loadAllResultsSingle() {
        Single<List<StorableResult>> single = Single.create(emitter -> {
            try {
                List<StorableResult> storableResults = storableResultDao.loadAll();
                Collections.sort(storableResults, (o1, o2) -> Long.compare(o2.getId(), o1.getId()));
                emitter.onSuccess(storableResults);
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
        return single
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<StorableResult> loadResultSingle(long id) {
        Single<StorableResult> resultSingle = Single.create(emitter -> {
            try {
                StorableResult load = storableResultDao.load(id);
                if (load != null) {
                    emitter.onSuccess(load);
                } else {
                    emitter.onError(new RuntimeException("Result not available"));
                }
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
        return resultSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Completable deleteResultCompletable(long id) {
        return Completable.create(emitter -> {
            try {
                storableResultDao.deleteByKey(id);
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


