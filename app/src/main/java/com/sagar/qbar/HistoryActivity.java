package com.sagar.qbar;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class HistoryActivity extends AppCompatActivity   {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ListView historyListView = findViewById(R.id.history_list_view);

        HistoryDbHelper historyDbHelper = new HistoryDbHelper(this);
        Cursor historiesCursor = historyDbHelper.getHistoriesCursor();

        ResultCursorAdapter resultCursorAdapter = new ResultCursorAdapter(this, historiesCursor, historyDbHelper);
        historyListView.setAdapter(resultCursorAdapter);
        historyListView.setOnItemClickListener(resultCursorAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }



}
