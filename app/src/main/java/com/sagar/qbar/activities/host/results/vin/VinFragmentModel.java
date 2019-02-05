package com.sagar.qbar.activities.host.results.vin;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.sagar.qbar.BR;
import com.sagar.qbar.utils.TimeAndDateUtil;

/**
 * Created by SAGAR MAHOBIA on 05-Feb-19. at 15:22
 */
public class VinFragmentModel extends BaseObservable {

    private String displayResult;
    private String timestamp;

    void setTimestamp(long timestamp) {
        this.timestamp = TimeAndDateUtil.getTimeFromTimestamp(timestamp);
        notifyPropertyChanged(BR.timestamp);
    }

    public void setDisplayResult(String displayResult) {
        this.displayResult = displayResult;
        notifyPropertyChanged(BR.displayResult);
    }

    @Bindable
    public String getTimestamp() {
        return timestamp;
    }

    @Bindable
    public String getDisplayResult() {
        return displayResult;
    }
}
