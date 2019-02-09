package com.sagar.qbar.activities.host.results;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.zxing.client.result.ParsedResultType;
import com.sagar.qbar.BR;
import com.sagar.qbar.R;
import com.sagar.qbar.utils.TimeAndDateUtil;

/**
 * Created by SAGAR MAHOBIA on 09-Feb-19. at 19:30
 */
public class ResultCommonModel extends BaseObservable {

    private String timestamp;
    private ParsedResultType type;


    @Bindable
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = TimeAndDateUtil.getTimeFromTimestamp(timestamp);
        notifyPropertyChanged(BR.timestamp);
    }


    @Bindable
    public int getCodeTypeIcon() {
        if (type == null) {
            return R.drawable.ic_empty;
        }
        switch (type) {
            case GEO:
                return R.drawable.ic_location;
            case SMS:
                return R.drawable.ic_sms;
            case TEL:
                return R.drawable.ic_phone;
            case URI:
                return R.drawable.ic_link;
            case ISBN:
                return R.drawable.ic_book;
            case PRODUCT:
                return R.drawable.ic_barcode;
            case TEXT:
                return R.drawable.ic_text;
            case VIN:
                return R.drawable.ic_vehicle;
            case WIFI:
                return R.drawable.ic_wifi;
            case CALENDAR:
                return R.drawable.ic_calender;
            case ADDRESSBOOK:
                return R.drawable.ic_address;
            case EMAIL_ADDRESS:
                return R.drawable.ic_email;
        }
        return R.drawable.ic_error;
    }

    @Bindable
    public String getTypeText() {
        if (type == null) {
            return "Loading..";
        }
        switch (type) {
            case GEO:
                return "Location";
            case SMS:
                return "SMS";
            case TEL:
                return "Phone";
            case URI:
                return "Web link";
            case ISBN:
                return "Book";
            case PRODUCT:
                return "Product";
            case TEXT:
                return "Text";
            case VIN:
                return "Vehicle Identification Number";
            case WIFI:
                return "Wi-fi";
            case CALENDAR:
                return "Calender";
            case ADDRESSBOOK:
                return "Address";
            case EMAIL_ADDRESS:
                return "Email";
        }
        return "Error!";
    }

    public void setType(ParsedResultType type) {
        this.type = type;
        notifyPropertyChanged(BR.codeTypeIcon);
        notifyPropertyChanged(BR.typeText);
    }
}
