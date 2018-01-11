package com.sagar.qbar;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class HistoryActivity extends AppCompatActivity {
    private AdView mAdView;

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


        mAdView = this.findViewById(R.id.adViewHistoryBottom);

        AdRequest adRequest = new AdRequest.Builder().
                addTestDevice("C06EC5B37D145628D1527D7ECFC97CFA")
                .build();
        mAdView.loadAd(adRequest);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}
