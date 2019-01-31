package com.sagar.qbar.activities.host.scanner;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.util.AttributeSet;

import com.journeyapps.barcodescanner.DecoratedBarcodeView;

/**
 * Created by SAGAR MAHOBIA on 29-Jan-19. at 16:32
 */
public class MyBarcodeView extends DecoratedBarcodeView implements LifecycleObserver {
    public MyBarcodeView(Context context) {
        super(context);
    }

    public MyBarcodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBarcodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        super.resume();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        super.pause();
    }

}
