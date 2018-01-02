/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sagar.qbar.utils;

import java.util.ArrayList;

/**
 *
 * @author SAGAR
 */
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
