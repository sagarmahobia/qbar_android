package com.sagar.qbar.activities.result;


import com.sagar.qbar.greendao.ResultService;
import com.sagar.qbar.models.DisplayableResult;
import com.sagar.qbar.models.ResultType;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by SAGAR MAHOBIA on 10-Aug-18. at 00:17
 */


@ResultActivityScope
public class Presenter implements ResultActivityContract.Presenter {

    @Inject
    ResultActivityContract.View view;

    @Inject
    ResultService resultService;

    private CompositeDisposable disposable;

    @Inject
    public Presenter(ResultActivityComponent component) {
        component.inject(this);
    }

    @Override
    public void onCreate() {
        disposable = new CompositeDisposable();
    }

    @Override
    public void onLoad(long id) {
        disposable.add(resultService.
                loadResultSingle(id).
                subscribe(storableResult -> {
                    String text = storableResult.getText();
                    ResultType resultType = ResultType.getResultTypeFromId(storableResult.getResultType());
                    long timestamp = storableResult.getTimestamp();
                    DisplayableResult displayableResult = new DisplayableResult(resultType, text, timestamp);

                    view.populateView(displayableResult);

                }));
    }

    @Override
    public void onDestroy() {
        disposable.dispose();
    }
}
