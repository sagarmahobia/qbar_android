package com.sagar.qbar.activities.host.history;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by SAGAR MAHOBIA on 30-Jan-19. at 20:23
 */
public class HistoryFragmentModel extends BaseObservable {

    private int size = 1;

    public int getSize() {
        return size;
    }

    void setSize(int size) {
        this.size = size;
        notifyChange();
    }
}
