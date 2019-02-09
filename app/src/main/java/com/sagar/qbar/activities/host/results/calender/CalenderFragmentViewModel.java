package com.sagar.qbar.activities.host.results.calender;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sagar.qbar.activities.host.results.ResultCommonModel;
import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.room.repository.StorableResultRepository;

/**
 * Created by SAGAR MAHOBIA on 05-Feb-19. at 19:46
 */
class CalenderFragmentViewModel extends ViewModel {
    private CalenderFragmentModel calenderFragmentModel;
    private ResultCommonModel commonModel;
    private LiveData<StorableResult> response;

    CalenderFragmentViewModel(StorableResultRepository repository, long id) {
        calenderFragmentModel = new CalenderFragmentModel();
        commonModel = new ResultCommonModel();
        response = repository.getStorableResultLiveData(id);
    }

    LiveData<StorableResult> getResponse() {
        return response;
    }

    CalenderFragmentModel getCalenderFragmentModel() {
        return calenderFragmentModel;
    }

    public ResultCommonModel getCommonModel() {
        return commonModel;
    }
}
