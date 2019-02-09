package com.sagar.qbar.activities.host.results.sms;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sagar.qbar.activities.host.results.ResultCommonModel;
import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.room.repository.StorableResultRepository;

/**
 * Created by SAGAR MAHOBIA on 03-Feb-19. at 00:59
 */
public class SmsFragmentViewModel extends ViewModel {
    private final SmsFragmentModel smsFragmentModel;
    private ResultCommonModel commonModel;
    private LiveData<StorableResult> response;

    SmsFragmentViewModel(StorableResultRepository repository, long id) {
        smsFragmentModel = new SmsFragmentModel();
        commonModel = new ResultCommonModel();
        response = repository.getStorableResultLiveData(id);
    }

    public LiveData<StorableResult> getResponse() {
        return response;
    }

    SmsFragmentModel getSmsFragmentModel() {
        return smsFragmentModel;
    }

    public ResultCommonModel getCommonModel() {
        return commonModel;
    }
}
