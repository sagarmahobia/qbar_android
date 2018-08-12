package com.sagar.qbar;

import com.google.android.gms.ads.AdRequest;
import com.sagar.qbar.greendao.ResultService;
import com.sagar.qbar.tasks.ImageDecoderService;

import dagger.Component;

/**
 * Created by SAGAR MAHOBIA on 09-Aug-18. at 23:34
 */


@ApplicationScope
@Component(modules = {FirebaseModule.class, AdmobModule.class, GreenDaoModule.class})
public interface ApplicationComponent {
    void inject(QbarApplication qbarApplication);

    ImageDecoderService provideImageDecoderService();

    AdRequest provideAdRequest();

    FirebaseService provideFirebaseService();

    ResultService provideResultService();
}
