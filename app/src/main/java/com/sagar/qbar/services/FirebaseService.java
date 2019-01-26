package com.sagar.qbar.services;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.sagar.qbar.ApplicationScope;
import com.sagar.qbar.models.ResultType;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 12-Aug-18. at 03:28
 */

@ApplicationScope
public class FirebaseService {

    private FirebaseAnalytics firebaseAnalytics;

    @Inject
    FirebaseService(FirebaseAnalytics firebaseAnalytics) {
        this.firebaseAnalytics = firebaseAnalytics;
    }


    public void linkOpen() {
        firebaseAnalytics.logEvent("searchedFromResult", null);
    }

    public void linkOrTextSearched() {
        firebaseAnalytics.logEvent("openedLinkFromResult", null);
    }

    public void scannedImage(ResultType resultType) {

        Bundle bundle = new Bundle();
        bundle.putString("type", resultType.toString().toLowerCase());
        firebaseAnalytics.logEvent("scannedResultType", bundle);

        firebaseAnalytics.logEvent("scannedImage", null);

    }
}
