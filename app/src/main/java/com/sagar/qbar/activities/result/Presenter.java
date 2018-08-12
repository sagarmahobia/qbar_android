package com.sagar.qbar.activities.result;


import com.sagar.qbar.greendao.ResultService;
import com.sagar.qbar.models.DisplayableResult;
import com.sagar.qbar.models.ResultType;
import com.sagar.qbar.utils.TimeAndDateUtil;
import com.sagar.qbar.utils.UrlUtil;

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

                    ResultType resultType = ResultType.getResultTypeFromId(storableResult.getResultType());
                    String text;
                    if (resultType == ResultType.LINK) {
                        text = UrlUtil.checkAndGetUrlWithProtocol(storableResult.getText());
                    } else {
                        text = storableResult.getText();
                    }
                    String time = TimeAndDateUtil.getTimeFromTimestamp(storableResult.getTimestamp());

                    DisplayableResult displayableResult = new DisplayableResult(resultType, text, time);
                    view.populateView(displayableResult);

                }, error -> view.onError()));
    }

    @Override
    public void onDestroy() {
        disposable.dispose();
    }
}
