package com.sagar.qbar.activities.host.results.vin;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.sagar.qbar.room.repository.StorableResultRepository;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 05-Feb-19. at 15:19
 */

@VinFragmentScope
class VinFragmentViewModelFactory implements ViewModelProvider.Factory {

    private StorableResultRepository repository;
    private long id;
    private boolean setId;

    @Inject
    VinFragmentViewModelFactory(StorableResultRepository repository) {
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
        if (modelClass.isAssignableFrom(VinFragmentViewModel.class)) {
            return (T) new VinFragmentViewModel(repository, id);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
