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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.zxing.Result;
import com.sagar.qbar.QbarApplication;
import com.sagar.qbar.R;
import com.sagar.qbar.activities.about.AboutPageActivity;
import com.sagar.qbar.activities.history.HistoryActivity;
import com.sagar.qbar.activities.result.ResultActivity;
import com.sagar.qbar.database.HistoryDbHelper;
import com.sagar.qbar.database.models.ResultType;
import com.sagar.qbar.database.models.ResultWrapper;
import com.sagar.qbar.onclickutil.OpenUrlUtil;
import com.sagar.qbar.onclickutil.ShareTextUtil;
import com.sagar.qbar.utils.SoundGenerator;
import com.sagar.qbar.views.MyScannerView;

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
    AdView mAdView;

    @BindView(R.id.camera_container)
    FrameLayout cameraContainer;

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    public static final String FROM_SCANNER = "FROM_SCANNER";

    private ZXingScannerView mScannerView;
    private FirebaseAnalytics mFirebaseAnalytics;

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

        DaggerScannerActivityComponent.builder()
                .applicationComponent(QbarApplication.get(this).getComponent())
                .scannerActivityModule(new ScannerActivityModule(this))
                .build().inject(this);


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);//todo use dagger

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    MY_CAMERA_REQUEST_CODE);
            mFirebaseAnalytics.logEvent("permissionRequested", null);//todo remove
        }

        mAdView.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                mAdView.setVisibility(View.VISIBLE);
                super.onAdLoaded();
            }
        });

        AdRequest adRequest = new AdRequest.Builder().
                addTestDevice("C06EC5B37D145628D1527D7ECFC97CFA")
                .build();//todo use dagger
        mAdView.loadAd(adRequest);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myTag", "OnResumeCalled");
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
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            mFirebaseAnalytics.logEvent("appClosedUsingBackButton", null);//todo remove
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_flash) {
            boolean flash = mScannerView.getFlash();
            ActionMenuItemView menuItem = findViewById(R.id.action_flash);

            if (flash) {
                mScannerView.setFlash(false);
                mFirebaseAnalytics.logEvent("flashSwitchedOff", null);

                if (menuItem != null) {
                    menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_flash_on_black_24dp));
                }


            } else {
                mScannerView.setFlash(true);
                mFirebaseAnalytics.logEvent("flashSwitchedOn", null);

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

            mFirebaseAnalytics.logEvent("appSharerOpened", null);

        } else if (id == R.id.about) {


            Intent intent = new Intent(this, AboutPageActivity.class);
            this.startActivity(intent);


        } else if (id == R.id.ourApps) {

            OpenUrlUtil.openUrl("market://search?q=pub:Sagar+Mahobia", this);

            mFirebaseAnalytics.logEvent("visitedOurApps", null);
        } else if (id == R.id.history) {
            Intent intent = new Intent(this, HistoryActivity.class);
            this.startActivity(intent);
            this.mFirebaseAnalytics.logEvent("openedHistoryActivityFromScanner", null);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void handleResult(Result rawResult) {

        SoundGenerator.playBeep();

        Toast.makeText(this, "Scanned Successfully", Toast.LENGTH_SHORT).show();

        ResultWrapper resultWrapper = new ResultWrapper(

                ResultType.getResultType(rawResult.getBarcodeFormat(), rawResult.getText()),
                rawResult.getText(),
                rawResult.getTimestamp());

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(ResultWrapper.RESULT_TAG, resultWrapper);
        intent.putExtra(FROM_SCANNER, true);
        HistoryDbHelper historyDbHelper = new HistoryDbHelper(this);
        historyDbHelper.storeResult(resultWrapper);
        this.startActivity(intent);

        Bundle bundle = new Bundle();
        bundle.putString("CodeFormate", rawResult.getBarcodeFormat().toString());
        this.mFirebaseAnalytics.logEvent("scannedResultType", bundle);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_CAMERA_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    mFirebaseAnalytics.logEvent("cameraPermissionGranted", null);

                } else {
                    Toast.makeText(this, "Camera permission is required", Toast.LENGTH_LONG).show();
                    this.finish();

                    mFirebaseAnalytics.logEvent("cameraPermissionDenied", null);

                }
        }
    }
}
