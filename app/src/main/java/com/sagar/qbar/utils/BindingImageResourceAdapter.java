package com.sagar.qbar.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

/**
 * Created by SAGAR MAHOBIA on 09-Feb-19. at 19:27
 */
public class BindingImageResourceAdapter {

    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }
}
