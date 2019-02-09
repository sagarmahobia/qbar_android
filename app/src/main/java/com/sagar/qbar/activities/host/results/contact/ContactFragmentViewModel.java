package com.sagar.qbar.activities.host.results.contact;

import android.support.annotation.NonNull;

import com.sagar.qbar.activities.host.results.ResultCommonViewModel;
import com.sagar.qbar.room.repository.StorableResultRepository;

/**
 * Created by SAGAR MAHOBIA on 05-Feb-19. at 19:33
 */
class ContactFragmentViewModel extends ResultCommonViewModel {

    private ContactFragmentModel contactFragmentModel;

    ContactFragmentViewModel(@NonNull StorableResultRepository repository, long id) {
        super(repository, id);
        contactFragmentModel = new ContactFragmentModel();
    }

    ContactFragmentModel getContactFragmentModel() {
        return contactFragmentModel;
    }
}
