package com.sagar.qbar.activities.scanner;

import com.sagar.qbar.ApplicationComponent;

import dagger.Component;

/**
 * Created by SAGAR MAHOBIA on 09-Aug-18. at 23:42
 */

@ScannerActivityScope
@Component(modules = ScannerActivityModule.class, dependencies = ApplicationComponent.class)
public interface ScannerActivityComponent {

    void inject(ScannerActivity scannerActivity);

    void inject(Presenter presenter);
}
