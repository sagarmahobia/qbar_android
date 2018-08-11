package com.sagar.qbar.activities.history;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.Group;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.sagar.qbar.ApplicationComponent;
import com.sagar.qbar.QbarApplication;
import com.sagar.qbar.R;
import com.sagar.qbar.activities.history.adapter.HistoryAdapter;
import com.sagar.qbar.activities.result.ResultActivity;
import com.sagar.qbar.activities.scanner.ScannerActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryActivity extends AppCompatActivity implements HistoryActivityContract.View {

    @Inject
    HistoryActivityContract.Presenter presenter;

    @Inject
    HistoryAdapter adapter;

    @BindView(R.id.history_recycler_view)
    RecyclerView historyRecyclerView;

    @BindView(R.id.clear_all_button)
    Button clearAllButton;

    @BindView(R.id.ad_view_history_bottom)
    AdView adView;

    @BindView(R.id.no_history_text_view)
    TextView noHistoryTextView;

    @BindView(R.id.recycler_group)
    Group recyclerGroup;


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

        historyRecyclerView.setAdapter(adapter);
         clearAllButton.setOnClickListener(v -> presenter.onClearAll());
        adView.loadAd(component.provideAdRequest());
        adView.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adView.setVisibility(View.VISIBLE);
            }
        });
        presenter.onCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onLoad();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void notifyAdapter() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showList() {
        recyclerGroup.setVisibility(View.VISIBLE);
        noHistoryTextView.setVisibility(View.GONE);

    }

    @Override
    public void showNoHistory() {
        noHistoryTextView.setVisibility(View.VISIBLE);
        recyclerGroup.setVisibility(View.GONE);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startResultActivity(long id) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(ScannerActivity.ID, id);
        this.startActivity(intent);

    }
}
