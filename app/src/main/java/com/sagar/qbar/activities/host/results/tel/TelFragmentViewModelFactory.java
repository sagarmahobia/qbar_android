package com.sagar.qbar.activities.host.results.tel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.sagar.qbar.room.repository.StorableResultRepository;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 03-Feb-19. at 15:32
 */

@TelFragmentScope
public class TelFragmentViewModelFactory implements ViewModelProvider.Factory {

    private StorableResultRepository repository;
    private long id;
    private boolean setId;

    @Inject
    TelFragmentViewModelFactory(StorableResultRepository repository) {
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
        if (modelClass.isAssignableFrom(TelFragmentViewModel.class)) {
            return (T) new TelFragmentViewModel(repository, id);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
