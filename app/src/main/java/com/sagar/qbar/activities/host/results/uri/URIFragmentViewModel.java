package com.sagar.qbar.activities.host.results.uri;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.room.repository.StorableResultRepository;

/**
 * Created by SAGAR MAHOBIA on 31-Jan-19. at 16:25
 */
class URIFragmentViewModel extends ViewModel {
    private URIFragmentModel uriModel;
    private LiveData<StorableResult> response;

    URIFragmentViewModel(StorableResultRepository repository, long id) {
        uriModel = new URIFragmentModel();
        response = repository.getStorableResultLiveData(id);
    }

    LiveData<StorableResult> getResponse() {
        return response;
    }

    URIFragmentModel getUriModel() {
        return uriModel;
    }
}
