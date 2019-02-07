package com.sagar.qbar.activities.host.results.contact;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.zxing.client.result.AddressBookParsedResult;
import com.sagar.qbar.BR;
import com.sagar.qbar.utils.TimeAndDateUtil;

/**
 * Created by SAGAR MAHOBIA on 05-Feb-19. at 19:34
 */
public class ContactFragmentModel extends BaseObservable {

    private String timestamp;
    private AddressBookParsedResult parsedResult;


    void setParsedResult(AddressBookParsedResult parsedResult) {
        this.parsedResult = parsedResult;
        notifyPropertyChanged(BR.displayResult);
    }

    public AddressBookParsedResult getParsedResult() {
        return parsedResult;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = TimeAndDateUtil.getTimeFromTimestamp(timestamp);
        notifyPropertyChanged(BR.timestamp);
    }

    @Bindable
    public String getDisplayResult() {
        return parsedResult != null ? parsedResult.getDisplayResult() : "";
    }

    public String getTimestamp() {
        return timestamp;
    }

}
