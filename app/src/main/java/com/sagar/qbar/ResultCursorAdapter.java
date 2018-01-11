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

import com.sagar.qbar.utils.ResultWrapper;

/**
 * Created by SAGAR MAHOBIA on 10-Jan-18. at 23:55
 */

public class ResultCursorAdapter extends CursorAdapter implements AdapterView.OnItemClickListener {
    private HistoryDbHelper dbHelper;
    private Context context;

    ResultCursorAdapter(Context context, Cursor c, HistoryDbHelper dbHelper) {
        super(context, c, 0);
        this.context = context;
        this.dbHelper = dbHelper;
    }

    @Override
    public View newView(Context context, final Cursor cursor, ViewGroup parent) {

        final int id = cursor.getInt(cursor.getColumnIndex(HistoryDbHelper.ID));

        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(context).
                inflate(R.layout.history_list_item_layout, parent, false);

        linearLayout.findViewById(R.id.list_item_delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteHistory(id);
                ResultCursorAdapter.this.changeCursor(dbHelper.getHistoriesCursor());
            }
        });

        return linearLayout;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        LinearLayout layout = (LinearLayout) view;
        TextView resultText = layout.findViewById(R.id.list_item_result_text);
        resultText.setText(cursor.getString(cursor.getColumnIndex(HistoryDbHelper.DATA)));

        int resultType = cursor.getInt(cursor.getColumnIndex(HistoryDbHelper.RESULT_TYPE));
        ImageView viewById = layout.findViewById(R.id.list_item_code_type_icon);

        switch (resultType) {
            case 1:
                viewById.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_barcode_black_24dp));
                break;
            case 2:
                viewById.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_link_black_24dp));
                break;
            case 3:
                viewById.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_text_black));
                break;
        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ResultWrapper resultWrapper = dbHelper.getResultWrapper(id);

        Intent intent
                = new Intent(context, ResultActivity.class);
        intent.putExtra(ResultWrapper.RESULT_TAG, resultWrapper);
        context.startActivity(intent);
    }
}
