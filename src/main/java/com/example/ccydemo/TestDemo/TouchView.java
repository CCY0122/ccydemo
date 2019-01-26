package com.example.ccydemo.TestDemo;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ccy(17022) on 2018/6/6 下午12:01
 */
public class TouchView extends View {

    public TouchView(Context context) {
        this(context, null);
    }

    public TouchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean r = super.dispatchTouchEvent(event);
        Log.d("ccy", "view dispatchTouchEvent() called,return " + r);
        return r;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean r =  false;
        Log.d("ccy","view onTouchEvent,return " + r);
        return r;
    }
}
