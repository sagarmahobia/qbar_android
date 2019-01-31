package com.sagar.qbar.activities.host.results.barcode;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sagar.qbar.room.entities.StorableResult;
import com.sagar.qbar.room.repository.StorableResultRepository;

/**
 * Created by SAGAR MAHOBIA on 01-Feb-19. at 00:05
 */
class BarcodeFragmentViewModel extends ViewModel {
    private BarcodeFragmentModel barcodeFragmentModel;
    private LiveData<StorableResult> response;

    BarcodeFragmentViewModel(StorableResultRepository repository, long id) {
        barcodeFragmentModel = new BarcodeFragmentModel();
        response = repository.getStorableResultLiveData(id);
    }


    LiveData<StorableResult> getResponse() {
        return response;
    }

    BarcodeFragmentModel getBarcodeFragmentModel() {
        return barcodeFragmentModel;
    }
}
