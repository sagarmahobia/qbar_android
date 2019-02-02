package com.sagar.qbar.activities.host.results.geo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.sagar.qbar.BR;
import com.sagar.qbar.utils.TimeAndDateUtil;

/**
 * Created by SAGAR MAHOBIA on 02-Feb-19. at 21:59
 */
public class GeoFragmentModel extends BaseObservable {

    private String displayResult;
    private String timestamp;
    private String uri;
    private double lat;
    private double lon;

    void setTimestamp(long timestamp) {
        this.timestamp = TimeAndDateUtil.getTimeFromTimestamp(timestamp);
        notifyPropertyChanged(BR.timestamp);
    }

    void setDisplayResult(String displayResult) {
        this.displayResult = displayResult;
        notifyPropertyChanged(BR.displayResult);
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Bindable
    public String getTimestamp() {
        return timestamp;
    }

    @Bindable
    public String getDisplayResult() {
        return displayResult;
    }

    public String getUri() {
        return uri;
    }

    void setLat(double lat) {
        this.lat = lat;
    }

    double getLat() {
        return lat;
    }

    void setLon(double lon) {
        this.lon = lon;
    }

    double getLon() {
        return lon;
    }
}
