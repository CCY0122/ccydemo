package com.example.ccydemo.sliderDemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by ccy on 2017-12-27.
 */

public class Frame1 extends FrameLayout {
    public Frame1(@NonNull Context context) {
        super(context);
    }

    public Frame1(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Frame1(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("ccy","frame 1 : onInterceptTouchEvent : "+ev.getAction());
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("ccy","frame 1 : onTouchEvent : "+event.getAction());
        return true;
    }
}
