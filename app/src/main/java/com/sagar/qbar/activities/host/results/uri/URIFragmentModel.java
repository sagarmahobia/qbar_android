package com.sagar.qbar.activities.host.results.uri;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.sagar.qbar.R;
import com.sagar.qbar.utils.TimeAndDateUtil;

/**
 * Created by SAGAR MAHOBIA on 31-Jan-19. at 14:14
 */
public class URIFragmentModel extends BaseObservable {

    private String uri;

    private String timestamp;


    public void setUri(String uri) {
        this.uri = uri;
        notifyChange();
    }

    void setTimestamp(long timestamp) {
        this.timestamp = TimeAndDateUtil.getTimeFromTimestamp(timestamp);
        notifyChange();
    }

    public int getCodeTypeIcon() {
        return R.drawable.ic_link;
    }

    public String getUri() {
        return uri;
    }

    public String getTypeText() {
        return "Weblink";
    }

    public String getTimestamp() {
        return timestamp;
    }

    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }
}
