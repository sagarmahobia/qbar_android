package com.sagar.qbar.activities.host.results;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.room.repository.StorableResultRepository;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by SAGAR MAHOBIA on 10-Feb-19. at 00:43
 */
public class ResultCommonViewModel extends ViewModel {

    private ResultCommonModel commonModel;
    private LiveData<StorableResult> response;
    private MutableLiveData<Boolean> timerLiveData;
    private CompositeDisposable disposable;

    public ResultCommonViewModel(StorableResultRepository repository, long id) {
        commonModel = new ResultCommonModel();
        response = repository.getStorableResultLiveData(id);
        timerLiveData = new MutableLiveData<>();
        disposable = new CompositeDisposable();

        prepareRateTimer();

    }

    private void prepareRateTimer() {
        disposable.add(
                Single.
                        just(true).
                        delay(5, TimeUnit.SECONDS).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe((x) -> timerLiveData.setValue(x))
        );
    }

    public LiveData<StorableResult> getResponse() {
        return response;
    }

    MutableLiveData<Boolean> getTimerLiveData() {
        return timerLiveData;
    }

    public ResultCommonModel getCommonModel() {
        return commonModel;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }

}
