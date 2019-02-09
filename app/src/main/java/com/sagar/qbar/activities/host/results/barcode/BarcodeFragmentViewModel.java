package com.sagar.qbar.activities.host.results.barcode;

import com.sagar.qbar.activities.host.results.ResultCommonViewModel;
import com.sagar.qbar.room.repository.StorableResultRepository;

/**
 * Created by SAGAR MAHOBIA on 01-Feb-19. at 00:05
 */
class BarcodeFragmentViewModel extends ResultCommonViewModel {
    private BarcodeFragmentModel barcodeFragmentModel;

    BarcodeFragmentViewModel(StorableResultRepository repository, long id) {
        super(repository, id);
        barcodeFragmentModel = new BarcodeFragmentModel();
    }

    BarcodeFragmentModel getBarcodeFragmentModel() {
        return barcodeFragmentModel;
    }

}
