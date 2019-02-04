package com.sagar.qbar.activities.host.results.wifi;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.room.repository.StorableResultRepository;

/**
 * Created by SAGAR MAHOBIA on 04-Feb-19. at 20:24
 */
class WifiFragmentViewModel extends ViewModel {
    private WifiFragmentModel wifiModel;
    private LiveData<StorableResult> response;

    WifiFragmentViewModel(StorableResultRepository repository, long id) {
        wifiModel = new WifiFragmentModel();
        response = repository.getStorableResultLiveData(id);
    }

    LiveData<StorableResult> getResponse() {
        return response;
    }

    WifiFragmentModel getWifiModel() {
        return wifiModel;
    }
}
