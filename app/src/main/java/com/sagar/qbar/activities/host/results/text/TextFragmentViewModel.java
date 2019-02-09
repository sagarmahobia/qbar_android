package com.sagar.qbar.activities.host.results.text;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sagar.qbar.activities.host.results.ResultCommonModel;
import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.room.repository.StorableResultRepository;

/**
 * Created by SAGAR MAHOBIA on 31-Jan-19. at 22:15
 */
class TextFragmentViewModel extends ViewModel {

    private final TextFragmentModel textFragmentModel;
    private ResultCommonModel commonModel;
    private LiveData<StorableResult> response;

    TextFragmentViewModel(StorableResultRepository repository, long id) {
        textFragmentModel = new TextFragmentModel();
        commonModel = new ResultCommonModel();
        response = repository.getStorableResultLiveData(id);

    }

    LiveData<StorableResult> getResponse() {
        return response;
    }

    TextFragmentModel getTextFragmentModel() {
        return textFragmentModel;
    }

    public ResultCommonModel getCommonModel() {
        return commonModel;
    }
}
