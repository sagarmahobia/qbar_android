package com.sagar.qbar.activities.result;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.sagar.qbar.ApplicationComponent;
import com.sagar.qbar.QbarApplication;
import com.sagar.qbar.R;
import com.sagar.qbar.activities.history.HistoryActivity;
import com.sagar.qbar.activities.scanner.ScannerActivity;
import com.sagar.qbar.database.models.ResultType;
import com.sagar.qbar.database.models.ResultWrapper;
import com.sagar.qbar.onclickutil.OpenUrlUtil;
import com.sagar.qbar.onclickutil.SearchUtil;
import com.sagar.qbar.onclickutil.ShareTextUtil;
import com.sagar.qbar.utils.TimeAndDateUtil;
import com.sagar.qbar.utils.UrlUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultActivity extends AppCompatActivity implements ResultActivityContract.View {

    @Inject
    ResultActivityContract.Presenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.ad_view_result_screen)
    AdView adView;

    @BindView(R.id.result_container)
    FrameLayout resultContainerLayout;

    @BindView(R.id.code_type_icon)
    ImageView imageView;


    @BindView(R.id.code_type_text)
    TextView codeTypeTextView;

    @BindView(R.id.code_scan_timestamp)
    TextView timestampTextView;


    private String result;
    private ResultType type;
    long timestamp;
    private boolean fromScannerActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ApplicationComponent component = QbarApplication.get(this).getComponent();
        DaggerResultActivityComponent.builder()
                .applicationComponent(component)
                .resultActivityModule(new ResultActivityModule(this))
                .build().inject(this);

        fromScannerActivity = getIntent().getBooleanExtra(ScannerActivity.FROM_SCANNER, false);

        adView.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                adView.setVisibility(View.VISIBLE);
                super.onAdLoaded();
            }
        });

        adView.loadAd(component.provideAdRequest());


        ResultWrapper resultWrapper = (ResultWrapper) this.getIntent().getSerializableExtra(ResultWrapper.RESULT_TAG);

        result = resultWrapper.getText();
        type = resultWrapper.getResultType();
        timestamp = resultWrapper.getTimestamp();

    }

    @Override
    protected void onResume() {
        super.onResume();

        timestampTextView.setText(TimeAndDateUtil.getTimeFromTimestamp(timestamp, this));


        View.OnClickListener shareButtonListener = v -> ShareTextUtil.share(ResultActivity.this, result);

        View.OnClickListener searchButtonListener = v -> SearchUtil.searchText(ResultActivity.this, result);


        if (type == ResultType.LINK) {

            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_link_black_24dp));
            codeTypeTextView.setText("Weblink");

            LinearLayout linkResultLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.link_result_layout, resultContainerLayout, false);

            TextView linkResultText = linkResultLayout.findViewById(R.id.linkResultText);

            linkResultText.setText(result);

            View.OnClickListener openLinkListener = v -> {
                OpenUrlUtil.openUrl(UrlUtil.checkAndGetUrlWithProtocol(result), ResultActivity.this);
            };

            linkResultText.setOnClickListener(openLinkListener);

            linkResultLayout.findViewById(R.id.open_link_button).setOnClickListener(openLinkListener);

            linkResultLayout.findViewById(R.id.link_share_button).setOnClickListener(shareButtonListener);

            linkResultLayout.findViewById(R.id.link_search_button).setOnClickListener(searchButtonListener);

            resultContainerLayout.addView(linkResultLayout);


        } else if (type == ResultType.TEXT) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_text_black));
            codeTypeTextView.setText("Text");

            LinearLayout textResultLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.text_result_layout, resultContainerLayout, false);

            TextView textResultTextView = textResultLayout.findViewById(R.id.textResultText);
            textResultTextView.setText(result);

            textResultLayout.findViewById(R.id.text_share_button).setOnClickListener(shareButtonListener);
            textResultLayout.findViewById(R.id.text_search_button).setOnClickListener(searchButtonListener);

            resultContainerLayout.addView(textResultLayout);


        } else if (type == ResultType.PRODUCT) {


            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_barcode_black_24dp));

            codeTypeTextView.setText("Product");


            LinearLayout productResultLayout = (LinearLayout) LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.product_result_layout, resultContainerLayout, false);

            TextView resultText = productResultLayout.findViewById(R.id.productResultText);
            resultText.setText(result);


            productResultLayout.findViewById(R.id.product_search_button).setOnClickListener(searchButtonListener);

            productResultLayout.findViewById(R.id.product_share_button).setOnClickListener(shareButtonListener);

            resultContainerLayout.addView(productResultLayout);


        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        if (fromScannerActivity) {
            getMenuInflater().inflate(R.menu.result_activity_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.onBackPressed();
        } else if (id == R.id.action_open_history) {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }
}
