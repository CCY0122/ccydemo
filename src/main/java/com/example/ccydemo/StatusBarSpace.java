package com.example.ccydemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by ccy(17022) on 2018/9/21 上午11:00
 */
public class StatusBarSpace extends View {
    public StatusBarSpace(Context context) {
        this(context, null);
    }

    public StatusBarSpace(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusBarSpace(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
//        Log.d("ccy","hSize = " + hSize + ";wSize = " + wSize);
//        Log.d("ccy", "hSize in dp = " + px2dp(hSize) + ";wsize in dp = " + px2dp(wSize));
        if(hMode != MeasureSpec.EXACTLY){
            hSize = getStatusBarHeight();
        }
        setMeasuredDimension(wSize,hSize);
    }

    public int getStatusBarHeight(){
        int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = getResources().getDimensionPixelSize(identifier);
        float statusBarHeight2 = getResources().getDimension(identifier);
//        Log.d("ccy", "status h1 = " + statusBarHeight + ";h2 = " + statusBarHeight2);
        return statusBarHeight;
    }

    public int px2dp(int px){
        return (int) (px / getResources().getDisplayMetrics().density);
    }
}
