package com.sagar.qbar.activities.scanner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
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
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.zxing.Result;
import com.sagar.qbar.ApplicationComponent;
import com.sagar.qbar.FirebaseLogTags;
import com.sagar.qbar.FirebaseService;
import com.sagar.qbar.GlobalConstants;
import com.sagar.qbar.QbarApplication;
import com.sagar.qbar.R;
import com.sagar.qbar.activities.about.AboutPageActivity;
import com.sagar.qbar.activities.history.HistoryActivity;
import com.sagar.qbar.activities.result.ResultActivity;
import com.sagar.qbar.greendao.entities.StorableResult;
import com.sagar.qbar.models.ResultType;
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

    @Inject
    FirebaseService firebaseService;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    FirebaseAnalytics firebaseAnalytics;


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.ad_view_scanner_screen)
    AdView adView;

    @BindView(R.id.camera_container)
    FrameLayout cameraContainer;

    private InterstitialAd interstitialAd;
    private ApplicationComponent component;

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    public static final String FROM_SCANNER = "FROM_SCANNER";
    public static final String ID = "id";


    private ZXingScannerView mScannerView;
    private boolean showAd;

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

        component = QbarApplication.get(this).getComponent();
        DaggerScannerActivityComponent.builder()
                .applicationComponent(component)
                .scannerActivityModule(new ScannerActivityModule(this))
                .build()
                .inject(this);

        showAd = false;

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
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.ad_unit_id_scanner_full_screen));
        interstitialAd.loadAd(component.provideAdRequest());
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
            long exitCount = sharedPreferences.getLong(GlobalConstants.EXIT_APP_COUNT, 1);
            boolean openedEarlier = sharedPreferences.getBoolean(GlobalConstants.OPENED_RATE_APP, false);
            if (exitCount % 3 == 0 && !openedEarlier) {
                showRateUsDialog();
            } else {
                super.onBackPressed();
                incrementExitCount();
            }
        }
    }

    private void openStore() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=com.sagar.qbar")
        );
        startActivity(browserIntent);
    }


    private void incrementExitCount() {
        long exitCount = sharedPreferences.getLong(GlobalConstants.EXIT_APP_COUNT, 1);
        sharedPreferences.edit().putLong(GlobalConstants.EXIT_APP_COUNT, exitCount + 1).apply();
    }

    private void showRateUsDialog() {

        new AlertDialog.Builder(this)
                .setTitle("Enjoying this code scanner?")
                .setMessage("Would you like to rate us?")
                .setPositiveButton("Ok, Sure", (dialog, which) -> {
                    dialog.dismiss();
                    openStore();
                    sharedPreferences.edit().putBoolean(GlobalConstants.OPENED_RATE_APP, true).apply();
                    firebaseAnalytics.logEvent(FirebaseLogTags.RATE_REQUEST_ACCEPTED, null);

                })
                .setNegativeButton("No, Thanks", (dialog, which) -> {
                    dialog.dismiss();
                    super.onBackPressed();
                    incrementExitCount();
                    firebaseAnalytics.logEvent(FirebaseLogTags.RATE_REQUEST_REJECTED, null);

                })
                .setCancelable(false)
                .create()
                .show();
        firebaseAnalytics.logEvent(FirebaseLogTags.ASKED_FOR_RATE, null);

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
        if (id == R.id.history) {

            Intent intent = new Intent(this, HistoryActivity.class);
            this.startActivity(intent);

        } else if (id == R.id.nav_share) {

            String shareBody = "I'm using QBar - Qr and Barcode Scanner app, the fastest QR and Barcode reader. Try it NOW! " +
                    "https://play.google.com/store/apps/details?id=com.sagar.qbar";

            ShareTextUtil.share(this, shareBody);

        } else if (id == R.id.about) {

            Intent intent = new Intent(this, AboutPageActivity.class);
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

        StorableResult result = new StorableResult();

        ResultType resultType = ResultType.getResultType(rawResult.getBarcodeFormat(), rawResult.getText());
        result.setResultType(resultType.getId());
        result.setText(rawResult.getText());
        result.setTimestamp(new Date().getTime());

        presenter.onHandleResult(result);
        firebaseService.scannedImage(resultType);
    }


    @Override
    public void startResultActivity(long id) {

        showToast("Scanned Successfully");
        SoundGenerator.playBeep();

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(FROM_SCANNER, true);
        intent.putExtra(ID, id);


        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                interstitialAd.loadAd(component.provideAdRequest());
                ScannerActivity.this.startActivity(intent);
            }
        });
        if (interstitialAd.isLoaded() && showAd) {
            interstitialAd.show();
            showAd = false;
        } else {
            this.startActivity(intent);
            showAd = true;
        }
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
