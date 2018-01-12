package com.sagar.qbar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.sagar.qbar.utils.ResultType;
import com.sagar.qbar.utils.ResultWrapper;

/**
 * Created by SAGAR MAHOBIA on 10-Jan-18. at 23:55
 */

public class ResultCursorAdapter extends CursorAdapter implements AdapterView.OnItemClickListener {
    private HistoryDbHelper dbHelper;
    private HistoryActivity activity;

    ResultCursorAdapter(HistoryActivity activity, Cursor c, HistoryDbHelper dbHelper) {
        super(activity, c, 0);
        this.activity = activity;
        this.dbHelper = dbHelper;
    }

    @Override
    public View newView(Context context, final Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).
                inflate(R.layout.history_list_item_layout, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        LinearLayout layout = (LinearLayout) view;
        TextView resultText = layout.findViewById(R.id.list_item_result_text);
        resultText.setText(cursor.getString(cursor.getColumnIndex(HistoryDbHelper.DATA)));

        ResultType resultType = ResultType.getResultTypeFromId(cursor.getInt(cursor.getColumnIndex(HistoryDbHelper.RESULT_TYPE)));
        ImageView viewById = layout.findViewById(R.id.list_item_code_type_icon);


        switch (resultType) {
            case PRODUCT:
                viewById.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_barcode_black_24dp));
                break;
            case LINK:
                viewById.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_link_black_24dp));
                break;
            case TEXT:
                viewById.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_text_black));
                break;
        }


        final int id = cursor.getInt(cursor.getColumnIndex(HistoryDbHelper.ID));


        layout.findViewById(R.id.list_item_delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteHistory(id);
                Cursor historiesCursor = dbHelper.getHistoriesCursor();
                if (historiesCursor != null && historiesCursor.getCount() > 0) {
                    ResultCursorAdapter.this.changeCursor(historiesCursor);
                } else {
                    cursor.close();
                    dbHelper.close();
                    activity.finish();
                    Toast.makeText(activity, "All history cleared", Toast.LENGTH_SHORT).show();
                }
                FirebaseAnalytics.getInstance(context).logEvent("deletedItemFromHistory", null);

            }
        });


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ResultWrapper resultWrapper = dbHelper.getResultWrapper(id);

        Intent intent = new Intent(activity, ResultActivity.class);
        intent.putExtra(ResultWrapper.RESULT_TAG, resultWrapper);
        activity.startActivity(intent);
    }
}
