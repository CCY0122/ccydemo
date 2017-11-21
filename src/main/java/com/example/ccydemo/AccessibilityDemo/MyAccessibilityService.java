package com.example.ccydemo.AccessibilityDemo;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.List;

/**
 * Created by XMuser on 2017-02-07.
 */

public class MyAccessibilityService extends AccessibilityService {

    private String packagename = "com.xiaomi.shop";
    private String btnClassName = "android.widget.Button";
    private boolean firstIn = false;


    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
//        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
//        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED |
//                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
//        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
//        info.notificationTimeout=100;
//        this.setServiceInfo(info);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        switch (event.getEventType()){
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                if(event.getClassName().toString().equals(btnClassName)) {
                        AccessUtil.findTextAndClick(this, "加入购物车");
                    Log.d("access", "content changed;\t" + event.getClassName().toString() + "\t");
                }
                    break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                    Log.d("access","state changed----"+event.getClassName().toString());
                if(event.getPackageName().toString().equals(packagename) && firstIn){
                    firstIn = false;
                    Toast.makeText(getApplicationContext(),"已进入小米商城，监控开始，\t请进入mix购买界面",Toast.LENGTH_LONG).show();
                }
                if(!event.getPackageName().toString().equals(packagename)){
                    firstIn = true;
                }
                break;
        }
    }

    @Override
    public void onInterrupt() {
    }

}
