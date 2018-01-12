package com.sagar.qbar.utils;

import java.io.Serializable;

/**
 * Created by SAGAR MAHOBIA on 10-Jan-18. at 21:33
 */

public class ResultWrapper implements Serializable {

    public static final String RESULT_TAG = "scanned_result";
    private ResultType resultType;
    private String text;
    private long timestamp;


    public ResultWrapper(ResultType resultType, String text, long timestamp) {
        this.resultType = resultType;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getText() {
        return this.text;

    }

    public long getTimestamp() {
        return timestamp;
    }

    public ResultType getResultType() {
        return this.resultType;
    }
}
