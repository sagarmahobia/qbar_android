/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sagar.qbar.utils;

//import android.util.Log;

import android.util.Patterns;

import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * @author SAGAR
 */
public class UrlParser {

    private String text;
    private ResultBean resultBean = new ResultBean();

    public UrlParser(String text) {
        this.text = text.toLowerCase();
        resultBean.setText(text);
        buildIndexBeans();
    }
    private void buildIndexBeans() {
        text = text.toLowerCase();
        ArrayList<String> linkList = extractLinks();
        for (String link : linkList) {
            int indexStart = text.indexOf(link);
            while (indexStart >= 0) {
                IndexBean result = new IndexBean();
                text = new StringBuilder(text).replace(indexStart, indexStart + link.length(), getReplaceString(link.length())).toString();
                result.setIndexStart(indexStart);
                result.setIndexEnd(indexStart + link.length());
                resultBean.addIndexBean(result);
                indexStart = text.indexOf(link, indexStart + link.length());
            }
        }

    }

    private ArrayList<String> extractLinks() {
        ArrayList<String> links = new ArrayList<>();
        Matcher m = Patterns.WEB_URL.matcher(text);
        while (m.find()) {
            String url = m.group();
//            Log.d(ScannerActivity.TAG, "URL extracted: " + url);
            links.add(url);
        }

        return links;
    }

    private static String getReplaceString(int length) {
        StringBuilder sb = new StringBuilder("");
        while ((length > sb.length())) {
            sb.append("@");
        }
        return sb.toString();
    }

    public ResultBean getResultBean() {
        return resultBean;
    }
}
