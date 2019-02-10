package com.sagar.qbar.services;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.zxing.client.result.ParsedResultType;
import com.sagar.qbar.ApplicationScope;

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

    public void scannedImage(ParsedResultType type) {
        logType(type, "scanned_image");
    }

    public void accessedHistory(ParsedResultType type) {
        logType(type, "accessed_history");
    }

    public void onRateRequestAccepted() {
        firebaseAnalytics.logEvent("RATE_REQUEST_ACCEPTED".toLowerCase(), null);
    }

    public void onRateRequestRejected() {
        firebaseAnalytics.logEvent("RATE_REQUEST_REJECTED".toLowerCase(), null);
    }


    private void logType(ParsedResultType type, String tag) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type.toString().toLowerCase());
        firebaseAnalytics.logEvent(tag, bundle);

    }
}
