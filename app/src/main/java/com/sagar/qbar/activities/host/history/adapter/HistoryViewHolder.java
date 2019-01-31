package com.sagar.qbar.activities.host.history.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sagar.qbar.databinding.HistoryItemLayoutBinding;

/**
 * Created by SAGAR MAHOBIA on 30-Jan-19. at 19:57
 */
class HistoryViewHolder extends RecyclerView.ViewHolder {

    private final HistoryItemLayoutBinding binding;

    HistoryViewHolder(HistoryItemLayoutBinding historyItemLayoutBinding) {
        super(historyItemLayoutBinding.getRoot());
        this.binding = historyItemLayoutBinding;
    }

    HistoryItemLayoutBinding getBinding() {
        return binding;
    }
}
