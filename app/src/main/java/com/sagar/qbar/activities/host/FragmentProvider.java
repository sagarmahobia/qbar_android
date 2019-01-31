package com.sagar.qbar.activities.host;

import com.sagar.qbar.activities.host.about.AboutFragment;
import com.sagar.qbar.activities.host.about.AboutFragmentModule;
import com.sagar.qbar.activities.host.about.AboutFragmentScope;
import com.sagar.qbar.activities.host.history.HistoryFragment;
import com.sagar.qbar.activities.host.history.HistoryFragmentModule;
import com.sagar.qbar.activities.host.history.HistoryFragmentScope;
import com.sagar.qbar.activities.host.results.uri.URIFragment;
import com.sagar.qbar.activities.host.results.uri.URIFragmentModule;
import com.sagar.qbar.activities.host.results.uri.URIFragmentScope;
import com.sagar.qbar.activities.host.scanner.ScannerFragment;
import com.sagar.qbar.activities.host.scanner.ScannerFragmentModule;
import com.sagar.qbar.activities.host.scanner.ScannerFragmentScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by SAGAR MAHOBIA on 29-Jan-19. at 15:48
 */

@Module
public abstract class FragmentProvider {

    @ContributesAndroidInjector(modules = ScannerFragmentModule.class)
    @ScannerFragmentScope
    abstract ScannerFragment scannerFragment();

    @ContributesAndroidInjector(modules = HistoryFragmentModule.class)
    @HistoryFragmentScope
    abstract HistoryFragment historyFragment();

    @ContributesAndroidInjector(modules = AboutFragmentModule.class)
    @AboutFragmentScope
    abstract AboutFragment aboutFragment();

    @ContributesAndroidInjector(modules = URIFragmentModule.class)
    @URIFragmentScope
    abstract URIFragment uriFragment();


}
