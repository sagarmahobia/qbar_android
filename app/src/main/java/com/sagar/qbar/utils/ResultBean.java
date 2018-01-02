
package com.sagar.qbar.utils;

import java.util.ArrayList;


public class ResultBean {

    private String text;
    private ArrayList<IndexBean> indexBeans = new ArrayList<>();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<IndexBean> getIndexBeans() {
        return indexBeans;
    }

    public void addIndexBean(IndexBean indexBean) {
        this.indexBeans.add(indexBean);
    }

}
