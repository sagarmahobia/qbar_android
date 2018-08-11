package com.sagar.qbar.activities.scanner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.google.zxing.Result;
import com.sagar.qbar.ApplicationComponent;
import com.sagar.qbar.QbarApplication;
import com.sagar.qbar.R;
import com.sagar.qbar.activities.about.AboutPageActivity;
import com.sagar.qbar.activities.history.HistoryActivity;
import com.sagar.qbar.activities.result.ResultActivity;
import com.sagar.qbar.greendao.entities.StorableResult;
import com.sagar.qbar.models.ResultType;
import com.sagar.qbar.onclickutil.OpenUrlUtil;
import com.sagar.qbar.onclickutil.ShareTextUtil;
import com.sagar.qbar.utils.SoundGenerator;
import com.sagar.qbar.views.MyScannerView;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AppCompatActivity
        implements ScannerActivityContract.View, NavigationView.OnNavigationItemSelectedListener, ZXingScannerView.ResultHandler {

    @Inject
    ScannerActivityContract.Presenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.ad_view_scanner_screen)
    AdView adView;

    @BindView(R.id.camera_container)
    FrameLayout cameraContainer;

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    public static final String FROM_SCANNER = "FROM_SCANNER";
    public static final String ID = "id";

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ApplicationComponent component = QbarApplication.get(this).getComponent();
        DaggerScannerActivityComponent.builder()
                .applicationComponent(component)
                .scannerActivityModule(new ScannerActivityModule(this))
                .build()
                .inject(this);

        presenter.onCreate();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    MY_CAMERA_REQUEST_CODE);
        }

        adView.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                adView.setVisibility(View.VISIBLE);
                super.onAdLoaded();
            }
        });

        adView.loadAd(component.provideAdRequest());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView = new MyScannerView(this);

        cameraContainer.addView(mScannerView);
        if (mScannerView != null) {
            mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
            mScannerView.startCamera();
            mScannerView.stopCameraPreview();

        } else {
            this.finish();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.setFlash(false);
        mScannerView.stopCameraPreview();
        mScannerView.stopCamera();
        cameraContainer.removeAllViews();
        invalidateOptionsMenu();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.scanner, menu);
        return true;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_flash) {
            boolean flash = mScannerView.getFlash();
            ActionMenuItemView menuItem = findViewById(R.id.action_flash);

            if (flash) {
                mScannerView.setFlash(false);
                if (menuItem != null) {
                    menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_flash_on_black_24dp));
                }
            } else {
                mScannerView.setFlash(true);
                if (menuItem != null) {
                    menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_flash_off_black_24dp));
                }
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share) {

            String shareBody = "I'm using QBar - Qr and Barcode Scanner app, the fastest QR and Barcode reader. Try it NOW! " +
                    "https://play.google.com/store/apps/details?id=com.sagar.qbar";

            ShareTextUtil.share(this, shareBody);

        } else if (id == R.id.about) {

            Intent intent = new Intent(this, AboutPageActivity.class);
            this.startActivity(intent);

        } else if (id == R.id.ourApps) {

            OpenUrlUtil.openUrl("market://search?q=pub:Sagar+Mahobia", this);

        } else if (id == R.id.history) {
            Intent intent = new Intent(this, HistoryActivity.class);
            this.startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_CAMERA_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.

                if (!(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "Camera permission is required", Toast.LENGTH_LONG).show();
                    this.finish();
                }
        }
    }

    @Override
    public void handleResult(Result rawResult) {

        SoundGenerator.playBeep();

        StorableResult result = new StorableResult();

        result.setResultType(ResultType.getResultType(rawResult.getBarcodeFormat(), rawResult.getText()).getId());
        result.setText(rawResult.getText());
        result.setTimestamp(new Date().getTime());

        presenter.onHandleResult(result);

    }


    @Override
    public void startResultActivity(long id) {

        showToast("Scanned Successfully");

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(FROM_SCANNER, true);
        intent.putExtra(ID, id);
        this.startActivity(intent);
    }

    @Override
    public void onError() {
        showToast("Something isn't right. Try again.");
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
