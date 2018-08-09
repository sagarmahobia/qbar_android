package com.sagar.qbar.activities.scanner;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by SAGAR MAHOBIA on 08-Jan-18.
 */

public class MyScannerView extends ZXingScannerView {
    public MyScannerView(Context context) {
        super(context);
        super.setLayoutParams(
                new FrameLayout.LayoutParams(

                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT)
        );

    }
}
