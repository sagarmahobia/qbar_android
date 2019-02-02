package com.sagar.qbar.activities.host.results.barcode;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.google.zxing.client.result.ParsedResultType;
import com.sagar.qbar.BR;
import com.sagar.qbar.R;
import com.sagar.qbar.utils.TimeAndDateUtil;

/**
 * Created by SAGAR MAHOBIA on 01-Feb-19. at 00:03
 */
public class BarcodeFragmentModel extends BaseObservable {


    private String barcode;

    private String timestamp;

    private ParsedResultType type;

    public void setBarcode(String barcode) {
        this.barcode = barcode;
        notifyPropertyChanged(BR.barcode);
    }

    void setTimestamp(long timestamp) {
        this.timestamp = TimeAndDateUtil.getTimeFromTimestamp(timestamp);
        notifyPropertyChanged(BR.timestamp);
    }

    public void setType(ParsedResultType type) {
        this.type = type;
        notifyPropertyChanged(BR.typeText);
    }

    @Bindable
    public String getBarcode() {
        return barcode;
    }

    @Bindable
    public int getCodeTypeIcon() {
        if (type == null) {
            return R.drawable.ic_book;
        }
        switch (type) {
            case ISBN:
                return R.drawable.ic_book;
            default:
                return R.drawable.ic_barcode;
        }
    }

    @Bindable
    public String getTypeText() {
        if (type == null) {
            return "";
        }
        switch (type) {
            case ISBN:
                return "Book";
            default:
                return "Product";
        }
    }

    @Bindable
    public String getTimestamp() {
        return timestamp;
    }

    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

}
