package com.sagar.qbar.activities.host.results.email;

import com.sagar.qbar.activities.host.results.ResultCommonViewModel;
import com.sagar.qbar.room.repository.StorableResultRepository;

/**
 * Created by SAGAR MAHOBIA on 05-Feb-19. at 13:35
 */
class EmailFragmentViewModel extends ResultCommonViewModel {

    private final EmailFragmentModel emailFragmentModel;

    EmailFragmentViewModel(StorableResultRepository repository, long id) {
        super(repository, id);
        emailFragmentModel = new EmailFragmentModel();
    }

    EmailFragmentModel getEmailFragmentModel() {
        return emailFragmentModel;
    }

}
