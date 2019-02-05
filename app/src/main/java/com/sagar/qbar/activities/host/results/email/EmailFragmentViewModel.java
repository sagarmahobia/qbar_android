package com.sagar.qbar.activities.host.results.email;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.room.repository.StorableResultRepository;

/**
 * Created by SAGAR MAHOBIA on 05-Feb-19. at 13:35
 */
class EmailFragmentViewModel extends ViewModel {
    private final EmailFragmentModel emailFragmentModel;
    private LiveData<StorableResult> response;


    EmailFragmentViewModel(StorableResultRepository repository, long id) {
        emailFragmentModel = new EmailFragmentModel();
        response = repository.getStorableResultLiveData(id);
    }

    LiveData<StorableResult> getResponse() {
        return response;
    }

    EmailFragmentModel getEmailFragmentModel() {
        return emailFragmentModel;
    }

}
