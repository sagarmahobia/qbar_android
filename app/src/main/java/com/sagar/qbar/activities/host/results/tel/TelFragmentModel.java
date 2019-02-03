package com.sagar.qbar.activities.host.results.tel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.sagar.qbar.BR;
import com.sagar.qbar.utils.TimeAndDateUtil;

/**
 * Created by SAGAR MAHOBIA on 03-Feb-19. at 15:32
 */
public class TelFragmentModel extends BaseObservable {

    private String displayResult;
    private String timestamp;
    private String number;
    private String uri;

    void setTimestamp(long timestamp) {
        this.timestamp = TimeAndDateUtil.getTimeFromTimestamp(timestamp);
        notifyPropertyChanged(BR.timestamp);
    }

    void setDisplayResult(String displayResult) {
        this.displayResult = displayResult;
        notifyPropertyChanged(BR.displayResult);
    }

    void setNumber(String number) {
        this.number = number;
    }

    @Bindable
    public String getTimestamp() {
        return timestamp;
    }

    @Bindable
    public String getDisplayResult() {
        return displayResult;
    }

    String getNumber() {
        return number;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
