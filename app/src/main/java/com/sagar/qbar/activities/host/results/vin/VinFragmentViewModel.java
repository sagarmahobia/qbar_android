package com.sagar.qbar.activities.host.results.vin;

import com.sagar.qbar.activities.host.results.ResultCommonViewModel;
import com.sagar.qbar.room.repository.StorableResultRepository;

/**
 * Created by SAGAR MAHOBIA on 05-Feb-19. at 15:21
 */
class VinFragmentViewModel extends ResultCommonViewModel {

    private final VinFragmentModel vinFragmentModel;

    VinFragmentViewModel(StorableResultRepository repository, long id) {
        super(repository, id);
        vinFragmentModel = new VinFragmentModel();
    }

    VinFragmentModel getVinFragmentModel() {
        return vinFragmentModel;
    }

}
