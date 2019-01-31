package com.sagar.qbar.activities.host.results.text;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.sagar.qbar.R;
import com.sagar.qbar.utils.TimeAndDateUtil;

/**
 * Created by SAGAR MAHOBIA on 31-Jan-19. at 22:16
 */
public class TextFragmentModel extends BaseObservable {

    private String text;
    private String timestamp;

    void setTimestamp(long timestamp) {
        this.timestamp = TimeAndDateUtil.getTimeFromTimestamp(timestamp);
        notifyChange();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        notifyChange();
    }

    public int getCodeTypeIcon() {
        return R.drawable.ic_text;
    }

    public String getTypeText() {
        return "Text";
    }


    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

}
