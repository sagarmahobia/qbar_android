package com.sagar.qbar.activities.host.results.vin;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.sagar.qbar.BR;

/**
 * Created by SAGAR MAHOBIA on 05-Feb-19. at 15:22
 */
public class VinFragmentModel extends BaseObservable {

    private String displayResult;

    public void setDisplayResult(String displayResult) {
        this.displayResult = displayResult;
        notifyPropertyChanged(BR.displayResult);
    }

    @Bindable
    public String getDisplayResult() {
        return displayResult;
    }
}
