package com.example.ccydemo.brvah;

/**
 * Created by ccy(17022) on 2018/7/18 下午2:24
 */
public class Bean {
    public Bean(String name, int res) {
        this.name = name;
        this.res = res;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String name;
    public int res;
}
