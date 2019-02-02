package com.sagar.qbar.activities.host.results.geo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.room.repository.StorableResultRepository;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 02-Feb-19. at 21:57
 */

@GeoFragmentScope
class GeoFragmentViewModel extends ViewModel {

    private final GeoFragmentModel geoFragmentModel;
    private LiveData<StorableResult> response;

    @Inject
    GeoFragmentViewModel(StorableResultRepository repository, long id) {
        geoFragmentModel = new GeoFragmentModel();
        response = repository.getStorableResultLiveData(id);
    }

    LiveData<StorableResult> getResponse() {
        return response;
    }

    GeoFragmentModel getGeoFragmentModel() {
        return geoFragmentModel;
    }
}
