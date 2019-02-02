package com.sagar.qbar.activities.host.results.sms;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.sagar.qbar.BR;
import com.sagar.qbar.utils.TimeAndDateUtil;

/**
 * Created by SAGAR MAHOBIA on 03-Feb-19. at 01:00
 */
public class SmsFragmentModel extends BaseObservable {

    private String displayResult;
    private String timestamp;
    private String number;
    private String body;

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

    void setBody(String body) {
        this.body = body;
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

    String getBody() {
        return body;
    }
}
