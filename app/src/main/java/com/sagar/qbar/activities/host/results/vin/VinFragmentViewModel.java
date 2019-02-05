package com.sagar.qbar.activities.host.results.vin;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.room.repository.StorableResultRepository;

/**
 * Created by SAGAR MAHOBIA on 05-Feb-19. at 15:21
 */
class VinFragmentViewModel extends ViewModel {

    private final VinFragmentModel vinFragmentModel;
    private LiveData<StorableResult> response;

    VinFragmentViewModel(StorableResultRepository repository, long id) {
        vinFragmentModel = new VinFragmentModel();
        response = repository.getStorableResultLiveData(id);
    }

    public LiveData<StorableResult> getResponse() {
        return response;
    }

    VinFragmentModel getVinFragmentModel() {
        return vinFragmentModel;
    }
}
