package com.sagar.qbar.activities.host.results.email;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.sagar.qbar.BR;
import com.sagar.qbar.utils.TimeAndDateUtil;

/**
 * Created by SAGAR MAHOBIA on 05-Feb-19. at 13:37
 */
public class EmailFragmentModel extends BaseObservable {


    private String[] tos;
    private String[] ccs;
    private String[] bccs;
    private String subject;
    private String body;

    private String displayResult;
    private String timestamp;

    void setTimestamp(long timestamp) {
        this.timestamp = TimeAndDateUtil.getTimeFromTimestamp(timestamp);
        notifyPropertyChanged(BR.timestamp);
    }

    void setDisplayResult(String displayResult) {
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

    String[] getTos() {
        return tos;
    }

    void setTos(String[] tos) {
        this.tos = tos;
    }

    String[] getCcs() {
        return ccs;
    }

    void setCcs(String[] ccs) {
        this.ccs = ccs;
    }

    String[] getBccs() {
        return bccs;
    }

    void setBccs(String[] bccs) {
        this.bccs = bccs;
    }

    String getSubject() {
        return subject;
    }

    void setSubject(String subject) {
        this.subject = subject;
    }

    String getBody() {
        return body;
    }

    void setBody(String body) {
        this.body = body;
    }
}
