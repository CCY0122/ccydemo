package com.example.ccydemo.EventBusDemo;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.ccydemo.BaseActivity;
import com.example.ccydemo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ccy on 2017-07-27.
 */

public class EventBusAct1 extends BaseActivity {

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.vp)
    ViewPager vp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventbus_act);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initVp();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        EventBus.getDefault().unregister(this);
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initVp() {
        final List<CustomFragment> frgs = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            CustomFragment f =  CustomFragment.getInstance(i,"fragment"+i);
            frgs.add(f);
        }

        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return frgs.get(position);
            }

            @Override
            public int getCount() {
                return frgs.size();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageReceived(final CustomBean bean){
        Log.d("eventBus","main act event invoke");
        Log.d("eventBus","thread = " +Thread.currentThread().getName());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
            tv.setText(bean.id+"="+bean.str);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageReceived(final ChildBean bean){
        Log.d("eventBus","main act child event invoke");
    }

    @OnClick(R.id.btn)
    public void b1(){
        EventBus.getDefault().post(new CustomBean(10,"from main act"));
    }

    @OnClick(R.id.btn2)
    public void b2(){
        startActivity(new Intent(this,EventBusAct2.class));
    }
}
