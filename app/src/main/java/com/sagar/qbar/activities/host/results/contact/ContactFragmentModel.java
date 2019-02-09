package com.sagar.qbar.activities.host.results.contact;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.zxing.client.result.AddressBookParsedResult;
import com.sagar.qbar.BR;

/**
 * Created by SAGAR MAHOBIA on 05-Feb-19. at 19:34
 */
public class ContactFragmentModel extends BaseObservable {

    private AddressBookParsedResult parsedResult;

    @Bindable
    public String getDisplayResult() {
        return parsedResult != null ? parsedResult.getDisplayResult() : "";
    }

    void setParsedResult(AddressBookParsedResult parsedResult) {
        this.parsedResult = parsedResult;
        notifyPropertyChanged(BR.displayResult);
    }

    public AddressBookParsedResult getParsedResult() {
        return parsedResult;
    }

}
