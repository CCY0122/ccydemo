package com.example.ccydemo.collapsingtoolbar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by ccy(17022) on 2018/10/25 下午2:38
 */
public class TopInBehavior extends CoordinatorLayout.Behavior<View> {

    public TopInBehavior(){
        super();
    }
    public TopInBehavior(Context context, AttributeSet attrs){
        super(context,attrs);
    }



    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        Log.d("ccy","child measured height = " + child.getMeasuredHeight() + ";height = " + child.getHeight());
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }
}
