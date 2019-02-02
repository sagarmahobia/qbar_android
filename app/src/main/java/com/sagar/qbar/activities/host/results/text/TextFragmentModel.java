package com.sagar.qbar.activities.host.results.text;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.sagar.qbar.BR;
import com.sagar.qbar.utils.TimeAndDateUtil;

/**
 * Created by SAGAR MAHOBIA on 31-Jan-19. at 22:16
 */
public class TextFragmentModel extends BaseObservable {

    private String text;
    private String timestamp;

    void setTimestamp(long timestamp) {
        this.timestamp = TimeAndDateUtil.getTimeFromTimestamp(timestamp);
        notifyPropertyChanged(BR.timestamp);
    }

    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(BR.text);
    }

    @Bindable
    public String getTimestamp() {
        return timestamp;
    }

    @Bindable
    public String getText() {
        return text;
    }

}
