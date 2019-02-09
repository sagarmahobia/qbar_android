package com.sagar.qbar.activities.host.results.tel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.zxing.client.result.TelParsedResult;
import com.sagar.qbar.BR;

/**
 * Created by SAGAR MAHOBIA on 03-Feb-19. at 15:32
 */
public class TelFragmentModel extends BaseObservable {

    private TelParsedResult telParsedResult;

    @Bindable
    public String getDisplayResult() {
        return telParsedResult != null ? telParsedResult.getDisplayResult() : "";
    }

    TelParsedResult getTelParsedResult() {
        return telParsedResult;
    }

    void setTelParsedResult(TelParsedResult telParsedResult) {
        this.telParsedResult = telParsedResult;
        notifyPropertyChanged(BR.displayResult);
    }
}
