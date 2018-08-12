package com.sagar.qbar.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sagar.qbar.ApplicationScope;

import java.io.InputStream;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@ApplicationScope
public class ImageDecoderService {
 
    @Inject
    ImageDecoderService() {
    }

    public Single<Bitmap> getBitmapSingle(InputStream imageStream) {
        Single<Bitmap> bitmapSingle = Single.create(emitter -> {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                emitter.onSuccess(bitmap);
            } catch (Exception e) {
                emitter.onError(e);
            }
        });

        return bitmapSingle.
                subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread());
    }
}