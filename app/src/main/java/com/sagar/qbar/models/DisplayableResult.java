package com.sagar.qbar.models;


/**
 * Created by SAGAR MAHOBIA on 10-Jan-18. at 21:33
 */

public class DisplayableResult {

    private ResultType resultType;
    private String text;
    private String time;


    public DisplayableResult(ResultType resultType, String text, String time) {
        this.resultType = resultType;
        this.text = text;
        this.time = time;
    }

    public String getText() {
        return this.text;

    }

    public String getTime() {
        return time;
    }

    public ResultType getResultType() {
        return this.resultType;
    }
}
