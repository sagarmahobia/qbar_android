package com.sagar.qbar.activities.host.results.calender;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.zxing.client.result.CalendarParsedResult;
import com.sagar.qbar.BR;

/**
 * Created by SAGAR MAHOBIA on 05-Feb-19. at 19:49
 */
public class CalenderFragmentModel extends BaseObservable {

    private CalendarParsedResult parsedResult;

    public void setParsedResult(CalendarParsedResult parsedResult) {
        this.parsedResult = parsedResult;
        notifyPropertyChanged(BR.displayResult);
    }

    @Bindable
    public String getDisplayResult() {
        return parsedResult != null ? parsedResult.getDisplayResult() : "";
    }

    public CalendarParsedResult getParsedResult() {
        return parsedResult;
    }
}
