package com.sagar.qbar.activities.host.results.geo;

import com.sagar.qbar.activities.host.results.ResultCommonViewModel;
import com.sagar.qbar.room.repository.StorableResultRepository;

/**
 * Created by SAGAR MAHOBIA on 02-Feb-19. at 21:57
 */

class GeoFragmentViewModel extends ResultCommonViewModel {

    private final GeoFragmentModel geoFragmentModel;

    GeoFragmentViewModel(StorableResultRepository repository, long id) {
        super(repository, id);
        geoFragmentModel = new GeoFragmentModel();
    }

    GeoFragmentModel getGeoFragmentModel() {
        return geoFragmentModel;
    }

}
