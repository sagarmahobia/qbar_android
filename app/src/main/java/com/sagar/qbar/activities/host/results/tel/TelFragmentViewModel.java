package com.sagar.qbar.activities.host.results.tel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.room.repository.StorableResultRepository;

/**
 * Created by SAGAR MAHOBIA on 03-Feb-19. at 15:32
 */
public class TelFragmentViewModel extends ViewModel {

    private final TelFragmentModel telFragmentModel;
    private LiveData<StorableResult> response;

    TelFragmentViewModel(StorableResultRepository repository, long id) {
        telFragmentModel = new TelFragmentModel();
        response = repository.getStorableResultLiveData(id);
    }

    public LiveData<StorableResult> getResponse() {
        return response;
    }

    TelFragmentModel getTelFragmentModel() {
        return telFragmentModel;
    }

}
