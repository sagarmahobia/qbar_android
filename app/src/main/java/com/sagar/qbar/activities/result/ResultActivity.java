package com.sagar.qbar.activities.result;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.sagar.qbar.ApplicationComponent;
import com.sagar.qbar.FirebaseService;
import com.sagar.qbar.QbarApplication;
import com.sagar.qbar.R;
import com.sagar.qbar.activities.history.HistoryActivity;
import com.sagar.qbar.activities.scanner.ScannerActivity;
import com.sagar.qbar.models.DisplayableResult;
import com.sagar.qbar.models.ResultType;
import com.sagar.qbar.onclickutil.OpenUrlUtil;
import com.sagar.qbar.onclickutil.SearchUtil;
import com.sagar.qbar.onclickutil.ShareTextUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultActivity extends AppCompatActivity implements ResultActivityContract.View {

    @Inject
    ResultActivityContract.Presenter presenter;

    @Inject
    FirebaseService firebaseService;

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

    private InterstitialAd interstitialAd;

    private boolean fromScannerActivity;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ApplicationComponent component = QbarApplication.get(this).getComponent();
        DaggerResultActivityComponent.builder()
                .applicationComponent(component)
                .resultActivityModule(new ResultActivityModule(this))
                .build().inject(this);


        adView.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                adView.setVisibility(View.VISIBLE);
                super.onAdLoaded();
            }
        });

        adView.loadAd(component.provideAdRequest());

        Intent intent = getIntent();
        fromScannerActivity = intent.getBooleanExtra(ScannerActivity.FROM_SCANNER, false);
        id = intent.getLongExtra(ScannerActivity.ID, -1);
        if (id == -1) {
            showToast("Result isn't available");
            this.finish();
            return;
        }
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.ad_unit_id_search_or_open_link));
        interstitialAd.loadAd(component.provideAdRequest());

        presenter.onCreate();

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onLoad(id);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        if (fromScannerActivity) {
            getMenuInflater().inflate(R.menu.result_activity_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
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

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void populateView(DisplayableResult result) {
        resultContainerLayout.removeAllViews();
        timestampTextView.setText(result.getTime());

        View.OnClickListener shareButtonListener = v -> ShareTextUtil.share(ResultActivity.this, result.getText());

        View.OnClickListener searchButtonListener = v -> {

            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    SearchUtil.searchText(ResultActivity.this, result.getText());
                    firebaseService.linkOrTextSearched();
                }
            });
            if (interstitialAd.isLoaded()) {
                interstitialAd.show();
            } else {
                SearchUtil.searchText(ResultActivity.this, result.getText());
                firebaseService.linkOrTextSearched();
            }

        };

        if (result.getResultType() == ResultType.LINK) {

            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_link_black_24dp));
            codeTypeTextView.setText("Weblink");

            View linkResultLayout = LayoutInflater.from(this).inflate(R.layout.link_result_layout, resultContainerLayout, false);

            TextView linkResultText = linkResultLayout.findViewById(R.id.linkResultText);

            linkResultText.setText(result.getText());

            View.OnClickListener openLinkListener = v -> {

                interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        OpenUrlUtil.openUrl(result.getText(), ResultActivity.this);
                        firebaseService.linkOpen();
                    }
                });
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {
                    OpenUrlUtil.openUrl(result.getText(), ResultActivity.this);
                    firebaseService.linkOpen();
                }


            };
            linkResultText.setOnClickListener(openLinkListener);

            linkResultLayout.findViewById(R.id.open_link_button).setOnClickListener(openLinkListener);

            linkResultLayout.findViewById(R.id.link_share_button).setOnClickListener(shareButtonListener);

            linkResultLayout.findViewById(R.id.link_search_button).setOnClickListener(searchButtonListener);

            resultContainerLayout.addView(linkResultLayout);


        } else if (result.getResultType() == ResultType.TEXT) {

            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_text_black));
            codeTypeTextView.setText("Text");

            View textResultLayout = LayoutInflater.from(this).inflate(R.layout.text_result_layout, resultContainerLayout, false);

            TextView textResultTextView = textResultLayout.findViewById(R.id.textResultText);
            textResultTextView.setText(result.getText());

            textResultLayout.findViewById(R.id.text_share_button).setOnClickListener(shareButtonListener);
            textResultLayout.findViewById(R.id.text_search_button).setOnClickListener(searchButtonListener);

            resultContainerLayout.addView(textResultLayout);


        } else if (result.getResultType() == ResultType.PRODUCT) {

            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_barcode_black_24dp));

            codeTypeTextView.setText("Product");


            View productResultLayout = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.product_result_layout, resultContainerLayout, false);

            TextView resultText = productResultLayout.findViewById(R.id.productResultText);
            resultText.setText(result.getText());


            productResultLayout.findViewById(R.id.product_search_button).setOnClickListener(searchButtonListener);

            productResultLayout.findViewById(R.id.product_share_button).setOnClickListener(shareButtonListener);

            resultContainerLayout.addView(productResultLayout);
        }
    }

    @Override
    public void onError() {
        this.finish();
        showToast("Result doesn't exist");
    }
}
