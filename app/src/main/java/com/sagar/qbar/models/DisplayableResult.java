package com.sagar.qbar.models;


/**
 * Created by SAGAR MAHOBIA on 10-Jan-18. at 21:33
 */

public class DisplayableResult {

    private ResultType resultType;
    private String text;
    private long timestamp;


    public DisplayableResult(ResultType resultType, String text, long timestamp) {
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
