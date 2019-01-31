package com.sagar.qbar.activities.host.results.uri;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.sagar.qbar.room.repository.StorableResultRepository;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 31-Jan-19. at 16:25
 */


@URIFragmentScope
public class URIFragmentViewModelFactory implements ViewModelProvider.Factory {

    private StorableResultRepository repository;
    private long id;
    private boolean setId;

    @Inject
    URIFragmentViewModelFactory(StorableResultRepository repository) {
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
            throw new IllegalStateException("setChannelIds(String) should be called");
        }

        if (modelClass.isAssignableFrom(URIFragmentViewModel.class)) {
            return (T) new URIFragmentViewModel(repository, id);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }


    }
}
