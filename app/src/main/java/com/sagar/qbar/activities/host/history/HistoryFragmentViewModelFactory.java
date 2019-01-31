package com.sagar.qbar.activities.host.history;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.sagar.qbar.room.repository.StorableResultRepository;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 29-Jan-19. at 20:46
 */

@HistoryFragmentScope
public class HistoryFragmentViewModelFactory implements ViewModelProvider.Factory {
    private StorableResultRepository historyRepository;

    @Inject
    HistoryFragmentViewModelFactory(StorableResultRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HistoryFragmentViewModel.class)) {
            return (T) new HistoryFragmentViewModel(historyRepository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
