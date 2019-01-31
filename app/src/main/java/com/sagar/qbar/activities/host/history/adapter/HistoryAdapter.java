package com.sagar.qbar.activities.host.history.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sagar.qbar.R;
import com.sagar.qbar.activities.host.history.HistoryFragmentScope;
import com.sagar.qbar.databinding.HistoryItemLayoutBinding;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 30-Jan-19. at 20:00
 */

@HistoryFragmentScope
public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {

    private LayoutInflater layoutInflater;
    private List<HistoryModel> histories;
    private AdapterListener adapterListener;
    private HistoryItemEventHandler historyItemEventHandler;

    @Inject
    HistoryAdapter() {
    }

    public void setHistories(List<HistoryModel> histories) {
        this.histories = histories;
        notifyDataSetChanged();
    }

    public void setAdapterListener(AdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    public void setHistoryItemEventHandler(HistoryItemEventHandler historyItemEventHandler) {
        this.historyItemEventHandler = historyItemEventHandler;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        HistoryItemLayoutBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.history_item_layout, viewGroup, false);
        return new HistoryViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.getBinding().setHistory(histories.get(position));
        holder.getBinding().setHandler(historyItemEventHandler);
        holder.itemView.setOnClickListener(v -> {
            if (adapterListener != null) {
                adapterListener.onHistoryClicked(histories.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return histories != null ? histories.size() : 0;
    }


    public interface AdapterListener {
        void onHistoryClicked(HistoryModel history);
    }
}
