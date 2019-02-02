package com.sagar.qbar.activities.host.results.uri;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.sagar.qbar.BR;
import com.sagar.qbar.utils.TimeAndDateUtil;

/**
 * Created by SAGAR MAHOBIA on 31-Jan-19. at 14:14
 */
public class URIFragmentModel extends BaseObservable {

    private String uri;

    private String timestamp;


    public void setUri(String uri) {
        this.uri = uri;
        notifyPropertyChanged(BR.uri);
    }

    void setTimestamp(long timestamp) {
        this.timestamp = TimeAndDateUtil.getTimeFromTimestamp(timestamp);
        notifyPropertyChanged(BR.timestamp);
    }

    @Bindable
    public String getUri() {
        return uri;
    }

    @Bindable
    public String getTimestamp() {
        return timestamp;
    }

}
