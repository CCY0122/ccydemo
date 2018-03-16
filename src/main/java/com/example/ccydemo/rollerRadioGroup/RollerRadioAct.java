package com.example.ccydemo.rollerRadioGroup;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ccydemo.BaseActivity;
import com.example.ccydemo.R;
import com.example.ccydemo.Util.Util;

import java.util.ArrayList;
import java.util.List;

import ccy.rollerradiogroup.RollerRadioGroup;

/**
 * Created by ccy on 2017-11-29.
 */

public class RollerRadioAct extends BaseActivity {

    private RollerRadioGroup rrg;
    private RollerRadioGroup rrg2;
    private Button b1;
    private TextView t1;
    private TextView t2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roller_act);
        rrg = (RollerRadioGroup) findViewById(R.id.rrg1);
        rrg2 = (RollerRadioGroup) findViewById(R.id.rrg2);
        List<String> texts = new ArrayList<>();
        texts.add("123");
        texts.add("测试");
        texts.add("哈哈");
        texts.add("开关");
        texts.add("定时");
        texts.add("三个");
        texts.add("一二");
        texts.add("123");
        texts.add("测试");
        texts.add("哈哈");
        texts.add("开关");
        texts.add("定时");
        texts.add("三个");
        texts.add("一二");

        rrg.setData(texts, texts.size() - 1);
        rrg2.setData(texts);

        Button b1 = (Button) findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                rrg.setSelectedId("测试");
//                rrg.setNormalColor(0xffff0000);
//                rrg.setSelectedColor(0xffff0000);
//                rrg.setSelectedSize(50);
//                rrg.setNormalSize(100);
//                rrg.setShaderColor(0x44ff0000);
                rrg.setSelectedColor(0xffff0000);
//                rrg.setShowEdgeLine(false);
            }
        });

        rrg.setOnRollerListener(new RollerRadioGroup.OnRollerListener() {
            @Override
            public void onItemSelected(RollerRadioGroup v, int selectedId, int lastSelectedId) {
                Log.d("ccy", "new = " + selectedId + ";old = " + lastSelectedId);
            }
        });

        int a = 1;
        Log.d("ccy",""+test(1));
        Log.d("ccy",""+test(2));
        Log.d("ccy",""+test(3));
        Log.d("ccy",""+test(4));

        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        ll.getLayoutTransition().setDuration(6000);
        b1 = (Button) findViewById(R.id.b1);
        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.setVisibility(t1.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                t2.setVisibility(t2.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        });
    }

    private boolean test(int a) {
        switch (a) {
            case 1:
            case 2:
            case 3:
                return true;
            default:
                return false;
        }
    }
}
