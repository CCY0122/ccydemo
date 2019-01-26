package com.example.ccydemo.TestDemo;

import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.douyinloadingview.DYLoadingView;
import com.example.ccydemo.BaseActivity;
import com.example.ccydemo.DragAndSwipe.SimpleRvAdapter;
import com.example.ccydemo.EventBusDemo.CustomFragment;
import com.example.ccydemo.R;
import com.example.ccydemo.recyclerviewDiffutilDemo.SimpleBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ccy on 2018-02-02.
 */

public class TestAct extends BaseActivity {


    DialogTest d;
    String[] s = {"1", "2"};
    IndicatorView indicatorView;
    ViewPager vp;
    String[] vps = {"123", "s", "qweqw", "哈哈常常长长长"};
    List<View> views = new ArrayList<>();
    PasswordView pwdv;
    TextView pwdt;
    RecyclerView rv1;

    private ImageView img;
    @BindView(R.id.dyl)
     DYLoadingView dyl;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_act);
        ButterKnife.bind(this);
        d = new DialogTest();
        indicatorView = (IndicatorView) findViewById(R.id.indicator);
        rv1 = (RecyclerView) findViewById(R.id.rv1);
        vp = (ViewPager) findViewById(R.id.vp);
        for (int i = 0; i < vps.length; i++) {
            TextView tv = new TextView(this);
            tv.setText(vps[i]);
            tv.setGravity(Gravity.CENTER);
            views.add(tv);
        }
        vp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position), ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
                return views.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(views.get(position));
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return vps[position];
            }

        });
        indicatorView.setupWithViewPager(vp);


        List<SimpleBean> b1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            SimpleBean bean1 = new SimpleBean();
            bean1.id = (int) (Math.random() * 10);
            bean1.text = "123";
            b1.add(bean1);
        }
        List<SimpleBean> b2 = new ArrayList<>(b1);
        SimpleBean bean2 = new SimpleBean();
        bean2.id = -1;
        bean2.text = "123";
        b2.add(bean2);
        Log.d("ccy", "b1 = " + b1.size() + ";b2 = " + b2.size());
        Collections.sort(b2, new Comparator<SimpleBean>() {
            @Override
            public int compare(SimpleBean o1, SimpleBean o2) {
                if (o1.id > o2.id) {
                    return 1;
                } else if (o1.id == o2.id) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });

        pwdv = (PasswordView) findViewById(R.id.pwdv);
        pwdt = (TextView) findViewById(R.id.pwdt);
        pwdv.setPasswordListener(new PasswordView.PasswordListener() {
            @Override
            public void passwordChange(String changeText) {
                pwdt.setText(changeText);
            }

            @Override
            public void passwordComplete() {
                Toast.makeText(TestAct.this, "complete", Toast.LENGTH_LONG).show();
            }

            @Override
            public void keyEnterPress(String password, boolean isComplete) {
                Toast.makeText(TestAct.this, ("pwd = " + password + ";is complete = " + isComplete), Toast.LENGTH_LONG).show();
            }
        });

        img = (ImageView) findViewById(R.id.b1);


        List<String> da = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            da.add("this is " + i);
        }
        SimpleRvAdapter adapter = new SimpleRvAdapter(this,da);
        rv1.setAdapter(adapter);
        rv1.setLayoutManager(new LinearLayoutManager(this));
        rv1.addItemDecoration(new RoundDecoration());
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    @OnClick(R.id.b1)
    void openDialog(View v) {
        dyl.start();
        d.show(getSupportFragmentManager(),null);
    }

}

