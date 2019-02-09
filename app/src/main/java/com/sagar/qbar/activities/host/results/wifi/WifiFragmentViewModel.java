package com.sagar.qbar.activities.host.results.wifi;

import com.sagar.qbar.activities.host.results.ResultCommonViewModel;
import com.sagar.qbar.room.repository.StorableResultRepository;

/**
 * Created by SAGAR MAHOBIA on 04-Feb-19. at 20:24
 */
class WifiFragmentViewModel extends ResultCommonViewModel {
    private WifiFragmentModel wifiModel;

    WifiFragmentViewModel(StorableResultRepository repository, long id) {
        super(repository, id);
        wifiModel = new WifiFragmentModel();
    }

    WifiFragmentModel getWifiModel() {
        return wifiModel;
    }

}
