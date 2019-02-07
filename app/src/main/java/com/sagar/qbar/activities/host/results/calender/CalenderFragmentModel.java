package com.sagar.qbar.activities.host.results.calender;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.zxing.client.result.CalendarParsedResult;
import com.sagar.qbar.BR;
import com.sagar.qbar.utils.TimeAndDateUtil;

/**
 * Created by SAGAR MAHOBIA on 05-Feb-19. at 19:49
 */
public class CalenderFragmentModel extends BaseObservable {

    private String timestamp;
    private CalendarParsedResult parsedResult;

    public void setTimestamp(long timestamp) {
        this.timestamp = TimeAndDateUtil.getTimeFromTimestamp(timestamp);
        notifyPropertyChanged(BR.timestamp);
        notifyPropertyChanged(BR.displayResult);
    }

    @Bindable
    public String getDisplayResult() {
        return parsedResult != null ? parsedResult.getDisplayResult() : "";
    }

    @Bindable
    public String getTimestamp() {
        return timestamp;
    }


    public void setParsedResult(CalendarParsedResult parsedResult) {
        this.parsedResult = parsedResult;
    }

    public CalendarParsedResult getParsedResult() {
        return parsedResult;
    }
}
