package com.example.ccydemo.nestedscroll;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.ccydemo.R;

/**
 * Created by ccy(17022) on 2018/6/23 下午2:57
 */
public class Behavior1 extends CoordinatorLayout.Behavior {

    public Behavior1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == R.id.behavior_tv;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) dependency.getLayoutParams();
        ViewGroup.MarginLayoutParams childLp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
        childLp.leftMargin = lp.leftMargin / 2;
        childLp.topMargin = lp.topMargin / 2;
        child.setLayoutParams(childLp);
        return true;
    }

}
