package com.sagar.qbar.activities.history;


import com.sagar.qbar.activities.history.adapter.HistoryCard;
import com.sagar.qbar.greendao.ResultService;
import com.sagar.qbar.greendao.entities.StorableResult;
import com.sagar.qbar.models.ResultType;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by SAGAR MAHOBIA on 05-Aug-18. at 12:48
 */

@HistoryActivityScope
public class Presenter implements HistoryActivityContract.Presenter {

    private CompositeDisposable disposable;

    private List<StorableResult> storableResults;

    @Inject
    HistoryActivityContract.View view;

    @Inject
    ResultService resultService;

    @Inject
    Presenter(HistoryActivityComponent component) {
        component.inject(this);
    }

    @Override
    public void onCreate() {
        disposable = new CompositeDisposable();
    }

    @Override
    public void onLoad() {
        disposable.add(resultService.
                loadAllResultsSingle().
                subscribe(storableResults -> {
                    this.storableResults = storableResults;
                    view.notifyAdapter();
                }));
    }

    @Override
    public void onDestroy() {
        disposable.dispose();
    }

    @Override
    public void onBindHistory(HistoryCard card, int position) {
        StorableResult storableResult = storableResults.get(position);
        card.setText(storableResult.getText());
        card.setIcon(ResultType.getResultTypeFromId(storableResult.getResultType()));
    }

    @Override
    public int getHistoryCount() {
        return (storableResults != null) ? storableResults.size() : 0;
    }

    @Override
    public void onClickItem(int position) {
        view.startResultActivity(storableResults.get(position).getId());
    }

    @Override
    public void onClickDeleteItem(int position) {
        disposable.add(resultService.
                        deleteResultCompletable(storableResults.
                                get(position).
                                getId()).subscribe(() -> {
                    storableResults.remove(position);
                    //todo show msg
                    view.notifyAdapter();
                })
        );
    }

    @Override
    public void onClearAll() {
        disposable.add(resultService
                .deleteAllResultCompletable()
                .subscribe(() -> {
                    //todo show msg
                }));
    }
}
