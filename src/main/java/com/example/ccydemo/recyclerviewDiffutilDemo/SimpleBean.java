package com.example.ccydemo.recyclerviewDiffutilDemo;

import android.util.Log;
import android.view.animation.AnimationUtils;


/**
 * Created by ccy on 2017-11-13.
 */

public class SimpleBean {
    public int id;  //primary key
    public String text;
    public String subText;
    public int icon;



    public SimpleBean(){}
    public SimpleBean(int id, String text, String subText, int icon) {
        this.id = id;
        this.text = text;
        this.subText = subText;
        this.icon = icon;
    }

}
