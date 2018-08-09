package com.sagar.qbar.activities.scanner;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 09-Aug-18. at 23:56
 */
@ScannerActivityScope
class Presenter implements ScannerActivityContract.Presenter {

    @Inject
    ScannerActivityContract.View view;

    @Inject
    Presenter(ScannerActivityComponent component) {
        component.inject(this);
    }
}


