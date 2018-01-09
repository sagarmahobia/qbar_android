package com.sagar.qbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
//import android.util.Log;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.sagar.qbar.utils.IndexBean;
import com.sagar.qbar.utils.ResultBean;
import com.sagar.qbar.utils.UrlParser;


public class ResultActivity extends AppCompatActivity {
    private String result;
    private String type;
    ResultBean resultBean;
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

        result = this.getIntent().getStringExtra(ScannerActivity.CONTENT_TAG);
        type = this.getIntent().getStringExtra(ScannerActivity.TYPE_TAG);

        UrlParser urlParser = new UrlParser(result);
        resultBean = urlParser.getResultBean();

        if (type.toLowerCase().contains("qr")) {
            mFirebaseAnalytics.logEvent("scannedQrCode", null);

            if (resultBean.getIndexBeans().size() > 0) {

                mFirebaseAnalytics.logEvent("scannedQrCodeWithUrl", null);

            } else {

                mFirebaseAnalytics.logEvent("scannedQrCodeWithTextOnly", null);

            }

        } else if (type.toLowerCase().contains("data")) {
            mFirebaseAnalytics.logEvent("scannedDataMatrixCode", null);
        } else {
            mFirebaseAnalytics.logEvent("scannedOtherCode", null);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView textView = this.findViewById(R.id.resultText);
        TextView typeTextView = this.findViewById(R.id.typeTextView);

        if (textView != null) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }

        SpannableString spannablResult = new SpannableString(result);

        for (final IndexBean indexBean : resultBean.getIndexBeans()) {

            spannablResult.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    String s = ((TextView) widget).getText().toString();
                    String url = s.substring(indexBean.getIndexStart(), indexBean.getIndexEnd());

                    if (!(url.toLowerCase().startsWith("https://") || url.toLowerCase().startsWith("http://"))) {
                        url = "http://" + url;
                    }

                    Intent viewIntent =
                            new Intent("android.intent.action.VIEW",
                                    Uri.parse(url));

                    mFirebaseAnalytics.logEvent("openedScannedUrl", null);
                    startActivity(viewIntent);
                }
            }, indexBean.getIndexStart(), indexBean.getIndexEnd(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        if (textView != null) {

            textView.setText(spannablResult);

        }
        if (typeTextView != null) {
            if (type.toLowerCase().contains("qr")) {
                typeTextView.setText(R.string.QrCodeText);


            } else if (type.toLowerCase().contains("data")) {
                typeTextView.setText(R.string.DataMatrixText);


            } else {
                typeTextView.setText(R.string.BarcodeText);


                FrameLayout frameLayout = this.findViewById(R.id.resultContainer);

                LinearLayout productResultLayout = (LinearLayout) LayoutInflater.from(this).
                        inflate(R.layout.product_result_layout, frameLayout, false);

                productResultLayout.findViewById(R.id.product_share_button).
                        setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");
                                String shareBody = result;
                                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                                startActivity(Intent.createChooser(sharingIntent, "Share via"));

                            }
                        });

                productResultLayout.findViewById(R.id.product_search_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse("http://google.com/search?q=" + result));
                        startActivity(viewIntent);
                    }
                });

                frameLayout.addView(productResultLayout);
            }
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
