package com.sagar.qbar.activities.host.results.geo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.zxing.client.result.GeoParsedResult;
import com.sagar.qbar.BR;

/**
 * Created by SAGAR MAHOBIA on 02-Feb-19. at 21:59
 */
public class GeoFragmentModel extends BaseObservable {

    private GeoParsedResult geoParsedResult;

    @Bindable
    public String getDisplayResult() {
        return geoParsedResult != null ? geoParsedResult.getDisplayResult() : "";
    }

    GeoParsedResult getGeoParsedResult() {
        return geoParsedResult;
    }

    void setGeoParsedResult(GeoParsedResult geoParsedResult) {
        this.geoParsedResult = geoParsedResult;
        notifyPropertyChanged(BR.displayResult);

    }
}
