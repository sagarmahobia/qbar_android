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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sagar.qbar.utils.BannerAdManager;
import com.sagar.qbar.utils.IndexBean;
import com.sagar.qbar.utils.ResultBean;
import com.sagar.qbar.utils.UrlParser;


public class ResultActivity extends AppCompatActivity {
    String result;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        new BannerAdManager(this).createAd();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView textView = (TextView) this.findViewById(R.id.resultText);
        TextView typeTextView = (TextView) this.findViewById(R.id.typeTextView);
        result = this.getIntent().getStringExtra(ScannerActivity.CONTENT_TAG);
        type = this.getIntent().getStringExtra(ScannerActivity.TYPE_TAG);
        if (textView != null) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }

        SpannableString spannablResult = new SpannableString(result);

        UrlParser urlParser = new UrlParser(result);
        ResultBean resultBean = urlParser.getResultBean();
        for (final IndexBean indexBean : resultBean.getIndexBeans()) {
//            Log.d("My Tag", "" + indexBean.getIndexStart() + " , " + indexBean.getIndexEnd());
            spannablResult.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    String s = ((TextView) widget).getText().toString();
                    String url = s.substring(indexBean.getIndexStart(), indexBean.getIndexEnd());
                    Intent viewIntent =
                            new Intent("android.intent.action.VIEW",
                                    Uri.parse(url));
                    startActivity(viewIntent);
//                    Log.d("My Tag", "Clicked = " + url);
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
