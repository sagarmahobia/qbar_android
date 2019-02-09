package com.sagar.qbar.activities.host.results;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.room.repository.StorableResultRepository;

/**
 * Created by SAGAR MAHOBIA on 10-Feb-19. at 00:43
 */
public class ResultCommonViewModel extends ViewModel {

    private ResultCommonModel commonModel;
    private LiveData<StorableResult> response;

    public ResultCommonViewModel(StorableResultRepository repository, long id) {
        commonModel = new ResultCommonModel();
        response = repository.getStorableResultLiveData(id);
    }

    public LiveData<StorableResult> getResponse() {
        return response;
    }

    public ResultCommonModel getCommonModel() {
        return commonModel;
    }

}
