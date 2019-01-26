package com.example.ccydemo.douyin;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.douyinloadingview.DYLoadingView;
import com.example.ccydemo.BaseActivity;
import com.example.ccydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ccy(17022) on 2018/9/3 上午10:50
 */
public class DouYinActivity extends BaseActivity {
    @BindView(R.id.dy1)
    DYLoadingView dy1;
    @BindView(R.id.dy2)
    DYLoadingView dy2;
    @BindView(R.id.dy3)
    DYLoadingView dy3;
    @BindView(R.id.dy4)
    DYLoadingView dy4;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.act_douyin);
        ButterKnife.bind(this);
//        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//            getWindow().setStatusBarColor(0xffff0000);
//            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        WindowManager.LayoutParams attributes = getWindow().getAttributes();
//        attributes.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | attributes.flags);
//        setSupportActionBar(toolbar);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//            WindowManager.LayoutParams attr = getWindow().getAttributes();
//            attr.flags = (attr.flags | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//
//        getStatusBarHeight();
//
//        addStatusBarView(0xffff0000);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.d("ccy", "onResume : " + getIntent().getAction());
    }

    @OnClick(R.id.b1)
    void start() {
        dy1.start();
        dy2.start();
        dy4.start();
        dy3.start();
    }

    @OnClick(R.id.b2)
    void stop() {
        dy1.stop();
        dy2.stop();
        dy4.stop();
        dy3.stop();
    }

    @OnClick(R.id.b3)
    void test() {
//        dy4.setColors(0xffff0000,0xff00ff00,0xff000000);
        dy4.setDuration(500, 0);
        dy4.setRadius(dy4.getRadius1(), dy4.getRadius2(), 20);
        dy4.setScales(3f, .5f);
        dy4.setStartEndFraction(0.5f, 0.5f);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
            float result2 = getResources().getDimension(resourceId);
            Log.d("ccy",result + ";" + result2 + ";" + px2dp(result2));
        }
        return result;
    }

    public float px2dp(float px){
        return px / getResources().getDisplayMetrics().density;
    }

    private void addStatusBarView(int color){
        View view = new View(this);
        view.setBackgroundColor(color);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getStatusBarHeight());
        ViewGroup decorView = (ViewGroup) findViewById(android.R.id.content);
        decorView.addView(view,params);
    }

}
