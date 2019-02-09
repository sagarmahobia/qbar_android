package com.sagar.qbar.activities.host.results.tel;

import com.sagar.qbar.activities.host.results.ResultCommonViewModel;
import com.sagar.qbar.room.repository.StorableResultRepository;

/**
 * Created by SAGAR MAHOBIA on 03-Feb-19. at 15:32
 */
class TelFragmentViewModel extends ResultCommonViewModel {

    private final TelFragmentModel telFragmentModel;

    TelFragmentViewModel(StorableResultRepository repository, long id) {
        super(repository, id);
        telFragmentModel = new TelFragmentModel();
    }

    TelFragmentModel getTelFragmentModel() {
        return telFragmentModel;
    }

}
