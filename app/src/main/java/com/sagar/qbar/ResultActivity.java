package com.sagar.qbar;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.zxing.BarcodeFormat;
import com.sagar.qbar.utils.ResultType;
import com.sagar.qbar.utils.ResultWrapper;
import com.sagar.qbar.utils.UrlUtil;

public class ResultActivity extends AppCompatActivity {
    private String result;
    private BarcodeFormat type;
    private AdView mAdView;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mAdView = this.findViewById(R.id.adViewResultScreen);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                mAdView.setVisibility(View.GONE);
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLoaded() {
                mAdView.setVisibility(View.VISIBLE);
                super.onAdLoaded();
            }
        });

        AdRequest adRequest = new AdRequest.Builder().
                addTestDevice("C06EC5B37D145628D1527D7ECFC97CFA")
                .build();
        mAdView.loadAd(adRequest);


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.logEvent("scannedImage", null);

        ResultWrapper resultWrapper = (ResultWrapper) this.getIntent().getSerializableExtra(ResultWrapper.RESULT_TAG);


        result = resultWrapper.getText();
        type = resultWrapper.getBarcodeFormat();


    }

    @Override
    protected void onResume() {
        super.onResume();
        ResultType resultType = ResultType.getResultType(type);

        if (resultType == ResultType.LINK_OR_TEXT) {
            if (UrlUtil.checkUrl(result)) {
                Log.d("myTag", "Url: - " + result);
            } else {
                Log.d("myTag", "Text: - " + result);
            }

        } else if (resultType == ResultType.PRODUCT) {
            Log.d("myTag", "product " + result);
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
