package com.example.ccydemo.EventBusDemo;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by ccy(17022) on 2018/11/22 下午7:02
 */
public class JustBean {

    private String str = "init";

    public JustBean() {
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageReceived(final CustomBean bean) {
        Log.d("eventBus", "JustBean event invoke");
        str = "after";
    }

    public void test(){
        Log.d("ccy","str = " + str);
    }
}
