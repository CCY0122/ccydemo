package com.example.ccydemo.nestedscroll;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ccydemo.BaseActivity;
import com.example.ccydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by ccy(17022) on 2018/6/23 下午2:45
 */
public class NestedScrollActivity extends BaseActivity {


    @BindView(R.id.tv2)
    TextView t2;

    private float lastX,lastY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested);
        ButterKnife.bind(this);
    }

    @OnTouch(R.id.behavior_tv)
    public boolean onTouch(View v, MotionEvent ev){
        float x,y;
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            lastX = ev.getX();
            lastY = ev.getY();
        }
        if(ev.getAction() == MotionEvent.ACTION_MOVE){
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            lp.leftMargin = (int) (lp.leftMargin + (ev.getX() - lastX));
            lp.topMargin = (int) (lp.topMargin + (ev.getY() - lastY));
            v.setLayoutParams(lp);
            lastX = ev.getX();
            lastY = ev.getY();
        }

        return true;
    }

    @OnClick(R.id.b1)
    void b1(View v){
//        ViewCompat.offsetTopAndBottom(t2,100);
        t2.offsetTopAndBottom(100);
        Log.d("ccy","get ty = " + t2.getTranslationY() );
        ViewGroup.LayoutParams lp = t2.getLayoutParams();
    }

}
