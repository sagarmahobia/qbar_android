package com.sagar.qbar.activities.host.scanner;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.sagar.qbar.room.repository.StorableResultRepository;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 29-Jan-19. at 15:40
 */

@ScannerFragmentScope
public class ScannerFragmentViewModelFactory implements ViewModelProvider.Factory {

    private StorableResultRepository repository;

    @Inject
    ScannerFragmentViewModelFactory(StorableResultRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ScannerFragmentViewModel.class)) {
            return (T) new ScannerFragmentViewModel(repository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
