package com.sagar.qbar.activities.host.results.email;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.zxing.client.result.EmailAddressParsedResult;
import com.sagar.qbar.BR;

/**
 * Created by SAGAR MAHOBIA on 05-Feb-19. at 13:37
 */
public class EmailFragmentModel extends BaseObservable {

    private EmailAddressParsedResult emailAddressParsedResult;

    @Bindable
    public String getDisplayResult() {
        return emailAddressParsedResult != null ? emailAddressParsedResult.getDisplayResult() : "";
    }

    EmailAddressParsedResult getEmailAddressParsedResult() {
        return emailAddressParsedResult;
    }

    void setEmailAddressParsedResult(EmailAddressParsedResult emailAddressParsedResult) {
        this.emailAddressParsedResult = emailAddressParsedResult;
        notifyPropertyChanged(BR.displayResult);
    }
}
