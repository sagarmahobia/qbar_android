package com.sagar.qbar.utils;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sagar.qbar.R;

/**
 * Created by SAGAR.
 */
public class BannerAdManager {
    private AdView mAdView;

    public BannerAdManager(AppCompatActivity activity) {
        mAdView = (AdView) activity.findViewById(R.id.adView);
    }

    public void createAd() {

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                mAdView.setVisibility(View.GONE);
                super.onAdFailedToLoad(i);
                Thread thread = new Thread() {
                   final Handler h = new Handler();

                    @Override
                    public void run() {
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                createAd();
                            }
                        }, 15000);
                    }
                };
                thread.start();
            }

            @Override
            public void onAdLoaded() {
                mAdView.setVisibility(View.VISIBLE);
                super.onAdLoaded();
            }
        });
        loadAd();
    }

    private void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("0DC6479FEB571FB9E5870A216898F6FB")
                .build();
        mAdView.loadAd(adRequest);
    }
}
