package com.sagar.qbar.utils;

import com.google.zxing.BarcodeFormat;

public enum ResultType {

    PRODUCT(),
    LINK_OR_TEXT();


    /**
     * Created by SAGAR MAHOBIA on 10-Jan-18. at 21:33
     */


    public static ResultType getResultType(BarcodeFormat barcodeFormat) {

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
                return LINK_OR_TEXT;

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
        return LINK_OR_TEXT;
    }


}
