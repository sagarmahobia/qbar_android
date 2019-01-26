package com.sagar.qbar;

import com.sagar.qbar.activities.about.AboutPageActivity;
import com.sagar.qbar.activities.about.AboutPageModule;
import com.sagar.qbar.activities.about.AboutPageScope;
import com.sagar.qbar.activities.history.HistoryActivity;
import com.sagar.qbar.activities.history.HistoryActivityModule;
import com.sagar.qbar.activities.history.HistoryActivityScope;
import com.sagar.qbar.activities.result.ResultActivity;
import com.sagar.qbar.activities.result.ResultActivityModule;
import com.sagar.qbar.activities.result.ResultActivityScope;
import com.sagar.qbar.activities.scanner.ScannerActivity;
import com.sagar.qbar.activities.scanner.ScannerActivityModule;
import com.sagar.qbar.activities.scanner.ScannerActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by SAGAR MAHOBIA on 27-Jan-19. at 01:52
 */

@Module
public abstract class ActivityProvider {

    @ContributesAndroidInjector(modules = ScannerActivityModule.class)
    @ScannerActivityScope
    abstract ScannerActivity scannerActivity();

    @ContributesAndroidInjector(modules = ResultActivityModule.class)
    @ResultActivityScope
    abstract ResultActivity resultActivity();

    @ContributesAndroidInjector(modules = HistoryActivityModule.class)
    @HistoryActivityScope
    abstract HistoryActivity historyActivity();


    @ContributesAndroidInjector(modules = AboutPageModule.class)
    @AboutPageScope
    abstract AboutPageActivity aboutPageActivity();


}
