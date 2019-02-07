package com.sagar.qbar.activities.host.results.contact;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.room.repository.StorableResultRepository;

/**
 * Created by SAGAR MAHOBIA on 05-Feb-19. at 19:33
 */
class ContactFragmentViewModel extends ViewModel {

    private ContactFragmentModel contactFragmentModel;
    private LiveData<StorableResult> response;

    ContactFragmentViewModel(@NonNull StorableResultRepository repository, long id) {
        contactFragmentModel = new ContactFragmentModel();
        response = repository.getStorableResultLiveData(id);
    }

    LiveData<StorableResult> getResponse() {
        return response;
    }

    ContactFragmentModel getContactFragmentModel() {
        return contactFragmentModel;
    }
}
