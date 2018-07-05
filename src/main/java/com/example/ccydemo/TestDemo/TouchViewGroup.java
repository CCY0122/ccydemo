package com.example.ccydemo.TestDemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by ccy(17022) on 2018/6/6 上午11:59
 */
public class TouchViewGroup extends FrameLayout {

    public TouchViewGroup(Context context) {
        this(context, null);
    }

    public TouchViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean r = super.dispatchTouchEvent(ev);
        Log.d("ccy","view group dispatchTouchEvent,return " + r);
        return r;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean r = false;
        Log.d("ccy","view group onInterceptTouchEvent,return " + r);
        return r;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean r = super.onTouchEvent(event);
        Log.d("ccy", "view group onTouchEvent,return " + r);
        return r;
    }
}
