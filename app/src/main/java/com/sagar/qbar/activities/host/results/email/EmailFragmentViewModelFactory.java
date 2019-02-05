package com.sagar.qbar.activities.host.results.email;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.sagar.qbar.room.repository.StorableResultRepository;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 05-Feb-19. at 13:34
 */

@EmailFragmentScope
public class EmailFragmentViewModelFactory implements ViewModelProvider.Factory {

    private StorableResultRepository repository;
    private long id;
    private boolean setId;

    @Inject
    EmailFragmentViewModelFactory(StorableResultRepository repository) {
        this.repository = repository;
    }

    public void setId(long id) {
        this.id = id;
        setId = true;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (!setId) {
            throw new IllegalStateException("setId(int) should be called");
        }
        if (modelClass.isAssignableFrom(EmailFragmentViewModel.class)) {
            return (T) new EmailFragmentViewModel(repository, id);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }

    }
}
