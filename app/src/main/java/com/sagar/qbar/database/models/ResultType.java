package com.sagar.qbar.database.models;

import com.google.zxing.BarcodeFormat;
import com.sagar.qbar.utils.UrlUtil;

public enum ResultType {

    PRODUCT(1),
    LINK(2),
    TEXT(3);

    private int id;

    ResultType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    /**
     * Created by SAGAR MAHOBIA on 10-Jan-18. at 21:33
     */


    public static ResultType getResultType(BarcodeFormat barcodeFormat, String resultText) {

        switch (barcodeFormat) {
            case DATA_MATRIX:
            case QR_CODE:
            case PDF_417:
            case MAXICODE:
            case CODABAR:
            case CODE_39:
            case CODE_93:
            case CODE_128:
            case AZTEC:
                if (UrlUtil.checkUrl(resultText)) {
                    return LINK;
                }
                return TEXT;


            case EAN_8:
            case EAN_13:
            case ITF:
            case UPC_A:
            case UPC_E:
            case UPC_EAN_EXTENSION:
            case RSS_14:
            case RSS_EXPANDED:
                return PRODUCT;

        }
        return TEXT;
    }

    public static ResultType getResultTypeFromId(int id) {
        switch (id) {
            case 1:
                return PRODUCT;
            case 2:
                return LINK;
            case 3:
                return TEXT;
        }
        return TEXT;
    }
}

