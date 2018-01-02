package com.sagar.qbar.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;


public class ImageDecodeTask extends AsyncTask<Void, Void, Bitmap> {
    private Context context;
    private ImageView imageView;
    private int resId;

    public ImageDecodeTask(Context context, ImageView imageView, int resId) {
        this.context = context;
        this.imageView = imageView;
        this.resId = resId;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        return BitmapFactory.decodeStream(context.getResources().openRawResource(resId));
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (imageView != null) {
            try {
                imageView.setImageBitmap(bitmap);
            } catch (Exception ignored) {
            }

        }
        super.onPostExecute(bitmap);
    }
}
