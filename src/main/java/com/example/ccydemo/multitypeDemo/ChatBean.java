package com.example.ccydemo.multitypeDemo;

/**
 * Created by ccy on 2017-11-20.
 * 测试multitype一对多功能
 */

public class ChatBean {
    public static final int LEFT = 1;
    public static final int RIGHT = 2;

    int type;
    String content;

    public ChatBean(int type, String content) {
        this.type = type;
        this.content = content;
    }
}
