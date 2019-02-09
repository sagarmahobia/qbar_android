package com.sagar.qbar.activities.host.results.barcode;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.sagar.qbar.BR;

/**
 * Created by SAGAR MAHOBIA on 01-Feb-19. at 00:03
 */
public class BarcodeFragmentModel extends BaseObservable {

    private String barcode;

    public void setBarcode(String barcode) {
        this.barcode = barcode;
        notifyPropertyChanged(BR.barcode);
    }

    @Bindable
    public String getBarcode() {
        return barcode;
    }

}
