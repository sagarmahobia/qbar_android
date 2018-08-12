package com.sagar.qbar.activities.history.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sagar.qbar.R;
import com.sagar.qbar.activities.history.HistoryActivityContract;
import com.sagar.qbar.activities.history.HistoryActivityScope;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 11-Aug-18. at 01:12
 */

@HistoryActivityScope
public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {


    private HistoryActivityContract.Presenter presenter;

    @Inject
    HistoryAdapter(HistoryActivityContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_item_layout, parent, false);
        return new HistoryViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {

        presenter.onBindHistory(holder, position);

        View itemView = holder.itemView;
        itemView.findViewById(R.id.list_item_result_text).
                setOnClickListener(v -> presenter.onClickItem(position));

        itemView.findViewById(R.id.list_item_delete_button).
                setOnClickListener(v -> presenter.onClickDeleteItem(position));
    }

    @Override
    public int getItemCount() {
        return presenter.getHistoryCount();
    }
}
