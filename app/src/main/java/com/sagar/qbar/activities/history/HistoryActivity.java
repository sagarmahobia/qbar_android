package com.sagar.qbar.activities.history;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.sagar.qbar.ApplicationComponent;
import com.sagar.qbar.QbarApplication;
import com.sagar.qbar.R;
import com.sagar.qbar.adapter.ResultCursorAdapter;
import com.sagar.qbar.database.HistoryDbHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryActivity extends AppCompatActivity implements HistoryActivityContract.View {

    @Inject
    HistoryActivityContract.Presenter presenter;

    @BindView(R.id.history_list_view)
    ListView historyListView;

    @BindView(R.id.clear_all_button)
    Button button;

    @BindView(R.id.ad_view_history_bottom)
    AdView adView;

    @BindView(R.id.no_history_text_view)
    TextView noHistoryTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ApplicationComponent component = QbarApplication.get(this).getComponent();
        DaggerHistoryActivityComponent.builder()
                .applicationComponent(component)
                .historyActivityModule(new HistoryActivityModule(this))
                .build().
                inject(this);

        HistoryDbHelper historyDbHelper = new HistoryDbHelper(this);//todo use dagger
        Cursor historiesCursor = historyDbHelper.getHistoriesCursor();

        button.setOnClickListener(v -> {
            HistoryDbHelper dbHelper = new HistoryDbHelper(HistoryActivity.this);

            dbHelper.deleteAll();
            Toast.makeText(HistoryActivity.this, "History Cleared", Toast.LENGTH_SHORT).show();
            FirebaseAnalytics.getInstance(HistoryActivity.this).logEvent("ClearedHistory", null);

            HistoryActivity.this.finish();

        });
        if (historiesCursor.getCount() > 0) {
            button.setVisibility(View.VISIBLE);
        } else {
            noHistoryTextView.setVisibility(View.VISIBLE);
        }

        ResultCursorAdapter resultCursorAdapter = new ResultCursorAdapter(this, historiesCursor, historyDbHelper);
        historyListView.setAdapter(resultCursorAdapter);
        historyListView.setOnItemClickListener(resultCursorAdapter);

        adView.loadAd(component.provideAdRequest());
        adView.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adView.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
