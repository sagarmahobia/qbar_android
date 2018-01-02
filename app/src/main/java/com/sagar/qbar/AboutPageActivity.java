package com.sagar.qbar;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.sagar.qbar.utils.ImageDecodeTask;
import com.sagar.qbar.utils.MyHtml;

public class AboutPageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);
        ActionBar actionBar = getSupportActionBar();
        ImageView image = findViewById(R.id.about_logo_image);
        new ImageDecodeTask(this, image, R.raw.logo).execute();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.logEvent("aboutPageVisited", null);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView barcodeScannerGithub = findViewById(R.id.projectLinkDm77);
        if (barcodeScannerGithub != null) {
            barcodeScannerGithub.setText(MyHtml.fromHtml("<a href=\"https://github.com/dm77/barcodescanner\">https://github.com/dm77/barcodescanner</a>"));
            barcodeScannerGithub.setMovementMethod(LinkMovementMethod.getInstance());
        }


        TextView ZxingProjectLink = findViewById(R.id.projectLinkZxing);
        if (ZxingProjectLink != null) {
            ZxingProjectLink.setText(MyHtml.fromHtml("<a href=\"https://github.com/zxing/zxing\">https://github.com/zxing/zxing</a>"));
            ZxingProjectLink.setMovementMethod(LinkMovementMethod.getInstance());
        }


        TextView ZBarProjectLink = findViewById(R.id.projectLinkZbar);
        if (ZBarProjectLink != null) {
            ZBarProjectLink.setText(MyHtml.fromHtml("<a href=\"http://sourceforge.net/projects/zbar/files/AndroidSDK/\"> http://sourceforge.net/projects/zbar/files/AndroidSDK/</a>"));
            ZBarProjectLink.setMovementMethod(LinkMovementMethod.getInstance());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
