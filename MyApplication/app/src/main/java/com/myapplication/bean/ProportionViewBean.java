package com.myapplication.bean;

/**
 * 作者: Nathans'Liu
 * 邮箱: a1053128464@qq.com
 * 时间: 2017/11/27 14:34
 * 描述:
 */

public class ProportionViewBean {
    private String title;
    private double d;

    public ProportionViewBean(String title, double d) {
        this.title = title;
        this.d = d;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }
}
