package com.sagar.qbar.activities.host.results.uri;

import com.sagar.qbar.activities.host.results.ResultCommonViewModel;
import com.sagar.qbar.room.repository.StorableResultRepository;

/**
 * Created by SAGAR MAHOBIA on 31-Jan-19. at 16:25
 */
class URIFragmentViewModel extends ResultCommonViewModel {
    private URIFragmentModel uriModel;

    URIFragmentViewModel(StorableResultRepository repository, long id) {
        super(repository, id);
        uriModel = new URIFragmentModel();
    }

    URIFragmentModel getUriModel() {
        return uriModel;
    }
}
