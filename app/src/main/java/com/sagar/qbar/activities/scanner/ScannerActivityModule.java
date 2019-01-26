package com.sagar.qbar.activities.scanner;

import dagger.Module;
import dagger.Provides;

/**
 * Created by SAGAR MAHOBIA on 09-Aug-18. at 23:44
 */

@Module
public class ScannerActivityModule {

    @ScannerActivityScope
    @Provides
    ScannerActivityContract.View view(ScannerActivity scannerActivity) {
        return scannerActivity;
    }

    @ScannerActivityScope
    @Provides
    ScannerActivityContract.Presenter presenter(Presenter presenter) {
        return presenter;
    }

}


