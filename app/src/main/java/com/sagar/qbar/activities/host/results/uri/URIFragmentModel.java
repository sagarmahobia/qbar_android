package com.sagar.qbar.activities.host.results.uri;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.sagar.qbar.BR;

/**
 * Created by SAGAR MAHOBIA on 31-Jan-19. at 14:14
 */
public class URIFragmentModel extends BaseObservable {

    private String uri;

    public void setUri(String uri) {
        this.uri = uri;
        notifyPropertyChanged(BR.uri);
    }

    @Bindable
    public String getUri() {
        return uri;
    }

}
