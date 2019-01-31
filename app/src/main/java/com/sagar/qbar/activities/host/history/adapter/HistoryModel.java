package com.sagar.qbar.activities.host.history.adapter;


import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.google.zxing.client.result.ParsedResultType;
import com.sagar.qbar.R;

/**
 * Created by SAGAR MAHOBIA on 30-Jan-19. at 19:47
 */
public class HistoryModel extends BaseObservable {

    private Long id;
    private ParsedResultType parsedResultType;

    private String text;
    private int imageResource;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(ParsedResultType type) {
        switch (type) {
            case GEO:
                imageResource = R.drawable.ic_location;
                break;
            case SMS:
                imageResource = R.drawable.ic_sms;
                break;
            case TEL:
                imageResource = R.drawable.ic_phone;
                break;
            case URI:
                imageResource = R.drawable.ic_link;
                break;
            case VIN:
                imageResource = R.drawable.ic_vehicle;
                break;
            case ISBN:
                imageResource = R.drawable.ic_book;
                break;
            case PRODUCT:
                imageResource = R.drawable.ic_barcode;
                break;
            case TEXT:
                imageResource = R.drawable.ic_text;
                break;
            case WIFI:
                imageResource = R.drawable.ic_wifi;
                break;
            case CALENDAR:
                imageResource = R.drawable.ic_calender;
                break;
            case ADDRESSBOOK:
                imageResource = R.drawable.ic_address;
                break;
            case EMAIL_ADDRESS:
                imageResource = R.drawable.ic_email;
                break;
        }
    }

    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    public void setParsedResultType(ParsedResultType parsedResultType) {
        this.parsedResultType = parsedResultType;
    }

    public ParsedResultType getParsedResultType() {
        return parsedResultType;
    }
}
