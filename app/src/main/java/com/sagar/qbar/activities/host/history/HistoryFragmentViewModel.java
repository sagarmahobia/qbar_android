package com.sagar.qbar.activities.host.history;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.crashlytics.android.Crashlytics;
import com.sagar.qbar.activities.host.history.adapter.HistoryItemEventHandler;
import com.sagar.qbar.activities.host.history.adapter.HistoryModel;
import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.room.repository.StorableResultRepository;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by SAGAR MAHOBIA on 29-Jan-19. at 20:45
 */

public class HistoryFragmentViewModel extends ViewModel implements HistoryItemEventHandler {

    private StorableResultRepository historyRepository;
    private LiveData<List<StorableResult>> storableResultsLiveData;
    private CompositeDisposable disposable;
    private HistoryFragmentModel historyFragmentModel;


    HistoryFragmentViewModel(StorableResultRepository historyRepository) {
        this.historyRepository = historyRepository;

        historyFragmentModel = new HistoryFragmentModel();
        storableResultsLiveData = historyRepository.getAllStorableResultLiveData();
        disposable = new CompositeDisposable();
    }

    LiveData<List<StorableResult>> getStorableResultsLiveData() {
        return storableResultsLiveData;
    }

    HistoryFragmentModel getHistoryFragmentModel() {
        return this.historyFragmentModel;
    }

    void clearAll() {
        disposable.add(historyRepository
                .deleteAllResultCompletable()
                .subscribe(() -> {
                }, Crashlytics::logException));
    }

    @Override
    public void onClickDeleteItem(HistoryModel historyModel) {
        disposable.add(historyRepository.
                        deleteResultCompletable(historyModel.
                                getId()).subscribe(() -> {
                }, Crashlytics::logException)
        );
    }

    @Override
    public void onCleared() {
        disposable.dispose();
    }


}
