package com.sagar.qbar.activities.history;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.sagar.qbar.R;
import com.sagar.qbar.adapter.ResultCursorAdapter;
import com.sagar.qbar.database.HistoryDbHelper;

public class HistoryActivity extends AppCompatActivity {
    private AdView mAdView;
    FirebaseAnalytics mFirebaseAnalytics;

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

        Button button = findViewById(R.id.clear_all_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryDbHelper dbHelper = new HistoryDbHelper(HistoryActivity.this);

                dbHelper.deleteAll();
                Toast.makeText(HistoryActivity.this, "History Cleared", Toast.LENGTH_SHORT).show();
                FirebaseAnalytics.getInstance(HistoryActivity.this).logEvent("ClearedHistory", null);

                HistoryActivity.this.finish();

            }
        });
        if (historiesCursor.getCount() > 0) {
            button.setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.no_history_text_view).setVisibility(View.VISIBLE);
        }

        ResultCursorAdapter resultCursorAdapter = new ResultCursorAdapter(this, historiesCursor, historyDbHelper);
        historyListView.setAdapter(resultCursorAdapter);
        historyListView.setOnItemClickListener(resultCursorAdapter);


        mAdView = this.findViewById(R.id.adViewHistoryBottom);

        AdRequest adRequest = new AdRequest.Builder().
                addTestDevice("C06EC5B37D145628D1527D7ECFC97CFA")
                .build();
        mAdView.loadAd(adRequest);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.logEvent("openedHistoryActivity", null);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
