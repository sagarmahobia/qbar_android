package com.sagar.qbar.activities.host;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.sagar.qbar.ApplicationComponent;
import com.sagar.qbar.R;
import com.sagar.qbar.utils.ShareTextUtil;

import javax.inject.Inject;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static androidx.navigation.Navigation.findNavController;
import static androidx.navigation.ui.NavigationUI.navigateUp;
import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;
import static androidx.navigation.ui.NavigationUI.setupWithNavController;

/**
 * Created by SAGAR MAHOBIA on 27-Jan-19. at 02:42
 */
public class HostActivity extends AppCompatActivity
        implements HasSupportFragmentInjector, NavigationView.OnNavigationItemSelectedListener {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    ApplicationComponent component;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.ad_view_scanner_screen)
    AdView adView;

    private static final int MY_CAMERA_REQUEST_CODE = 100;

    private ActionBar supportActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        supportActionBar = getSupportActionBar();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    MY_CAMERA_REQUEST_CODE);
        }
 
        NavHostFragment finalHost = NavHostFragment.create(R.navigation.mobile_navigation);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_view, finalHost)
                .setPrimaryNavigationFragment(finalHost) // this is the equivalent to app:defaultNavHost="true"
                .commitNow();


        setUp(finalHost);
        setUpAds();

    }

    private void setUp(NavHostFragment finalHost) {

        navigationView.setNavigationItemSelectedListener(this);

        NavController navController = finalHost.getNavController();
        setupActionBarWithNavController(this, navController, drawer);
        setupWithNavController(navigationView, navController);
    }

    private void setUpAds() {
        adView.loadAd(component.provideAdRequest());
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                adView.setVisibility(View.GONE);
            }
        });
    }

    public boolean onSupportNavigateUp() {
        return navigateUp(findNavController(this, R.id.fragment_view), drawer);
    }

    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openStore() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=com.sagar.qbar")
        );
        startActivity(browserIntent);
    }


    public void setTitle(String title) {
        if (supportActionBar != null) {
            supportActionBar.setTitle(title);
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
