package com.sagar.qbar.activities.host.history.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sagar.qbar.R;
import com.sagar.qbar.activities.host.history.HistoryFragmentScope;
import com.sagar.qbar.databinding.HistoryItemLayoutBinding;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 30-Jan-19. at 20:00
 */

@HistoryFragmentScope
public class HistoryAdapter extends ListAdapter<HistoryModel, HistoryViewHolder> {

    private LayoutInflater layoutInflater;
    private AdapterListener adapterListener;
    private HistoryItemEventHandler historyItemEventHandler;

    @Inject
    HistoryAdapter() {
        super(DIFF_CALLBACK);
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
        HistoryModel item = getItem(position);
        holder.getBinding().setHistory(item);
        holder.getBinding().setHandler(historyItemEventHandler);
        holder.itemView.setOnClickListener(v -> {
            if (adapterListener != null) {
                adapterListener.onHistoryClicked(item);
            }
        });
    }

    private static final DiffUtil.ItemCallback<HistoryModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<HistoryModel>() {
                @Override
                public boolean areItemsTheSame(@NonNull HistoryModel old, @NonNull HistoryModel new_) {
                    return old.getId().equals(new_.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull HistoryModel old, @NonNull HistoryModel new_) {
                    return true;
                }
            };

    public interface AdapterListener {
        void onHistoryClicked(HistoryModel history);
    }
}
