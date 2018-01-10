package com.sagar.qbar.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.io.Serializable;

/**
 * Created by SAGAR MAHOBIA on 10-Jan-18.
 */

public class ResultWrapper implements Serializable {

    public static final String RESULT_TAG = "scanned_result";
    private BarcodeFormat barcodeFormat;
    private String text;
    long timestamp;

    public ResultWrapper(BarcodeFormat barcodeFormat, String text, long timestamp) {
        this.barcodeFormat = barcodeFormat;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getText() {
        return this.text;

    }

    public long getTimestamp() {
        return timestamp;
    }

    public BarcodeFormat getBarcodeFormat() {
        return this.barcodeFormat;
    }
}
