package com.sagar.qbar.activities.scanner;

import com.crashlytics.android.Crashlytics;
import com.sagar.qbar.greendao.ResultService;
import com.sagar.qbar.greendao.entities.StorableResult;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by SAGAR MAHOBIA on 09-Aug-18. at 23:56
 */
@ScannerActivityScope
class Presenter implements ScannerActivityContract.Presenter {
    private CompositeDisposable disposable;

    @Inject
    ScannerActivityContract.View view;

    @Inject
    ResultService resultService;

    @Inject
    Presenter(ScannerActivityComponent component) {
        component.inject(this);
    }

    @Override
    public void onCreate() {
        disposable = new CompositeDisposable();
    }

    @Override
    public void onHandleResult(StorableResult result) {
        disposable.add(resultService.
                saveResultSingle(result).
                subscribe((id) -> view.startResultActivity(id),
                        error -> {
                            Crashlytics.logException(error);
                            view.onError();
                        }));
    }

    @Override
    public void onDestroy() {
        disposable.dispose();
    }
}


