package com.sagar.qbar.activities.about;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.sagar.qbar.R;
import com.sagar.qbar.services.ImageDecoderService;
import com.sagar.qbar.utils.MyHtml;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.Disposable;


public class AboutPageActivity extends AppCompatActivity {

    @Inject
    ImageDecoderService imageDecoderService;

    @BindView(R.id.about_logo_image)
    ImageView logoImageView;

    @BindView(R.id.project_link_dm_77)
    TextView barcodeScannerGithub;

    @BindView(R.id.project_link_zxing)
    TextView zxingProjectLink;

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        disposable = imageDecoderService.
                getBitmapSingle(getResources().openRawResource(R.raw.logo)).
                subscribe(logoImageView::setImageBitmap);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (barcodeScannerGithub != null) {
            barcodeScannerGithub.setText(MyHtml.fromHtml("<a href=\"https://github.com/dm77/barcodescanner\">https://github.com/dm77/barcodescanner</a>"));
            barcodeScannerGithub.setMovementMethod(LinkMovementMethod.getInstance());
        }


        if (zxingProjectLink != null) {
            zxingProjectLink.setText(MyHtml.fromHtml("<a href=\"https://github.com/zxing/zxing\">https://github.com/zxing/zxing</a>"));
            zxingProjectLink.setMovementMethod(LinkMovementMethod.getInstance());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }
}
