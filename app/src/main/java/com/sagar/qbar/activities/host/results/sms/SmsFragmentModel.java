package com.sagar.qbar.activities.host.results.sms;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.zxing.client.result.SMSParsedResult;
import com.sagar.qbar.BR;

/**
 * Created by SAGAR MAHOBIA on 03-Feb-19. at 01:00
 */
public class SmsFragmentModel extends BaseObservable {

    private SMSParsedResult smsParsedResult;

    @Bindable
    public String getDisplayResult() {
        return smsParsedResult != null ? smsParsedResult.getDisplayResult() : "";
    }

    String getNumber() {
        String[] numbers = smsParsedResult.getNumbers();
        if (numbers.length > 0) {
            return smsParsedResult.getNumbers()[0];
        } else {
            return "";
        }
    }

    String getBody() {
        return smsParsedResult.getBody();
    }

    void setSmsParsedResult(SMSParsedResult smsParsedResult) {
        this.smsParsedResult = smsParsedResult;
        notifyPropertyChanged(BR.displayResult);
    }
}
