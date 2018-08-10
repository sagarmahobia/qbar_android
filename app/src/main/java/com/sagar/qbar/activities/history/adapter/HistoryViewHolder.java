package com.sagar.qbar.activities.history.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sagar.qbar.R;
import com.sagar.qbar.models.ResultType;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v4.content.ContextCompat.getDrawable;

/**
 * Created by SAGAR MAHOBIA on 11-Aug-18. at 01:11
 */
public class HistoryViewHolder extends RecyclerView.ViewHolder implements HistoryCard {

    @BindView(R.id.list_item_result_text)
    TextView resultTextView;

    @BindView(R.id.list_item_code_type_icon)
    ImageView codeTypeIcon;

    HistoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setText(String text) {
        resultTextView.setText(text);
    }

    @Override
    public void setIcon(ResultType resultType) {
        Context context = codeTypeIcon.getContext();
        Drawable drawable;

        switch (resultType) {
            case PRODUCT:
                drawable = getDrawable(context, R.drawable.ic_barcode_black_24dp);
                break;
            case LINK:
                drawable = getDrawable(context, R.drawable.ic_link_black_24dp);
                break;
            default:
                drawable = getDrawable(context, R.drawable.ic_text_black);
                break;
        }
        codeTypeIcon.setImageDrawable(drawable);
    }


}
