package com.example.ccydemo.sliderDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ccydemo.BaseActivity;
import com.example.ccydemo.R;
import com.r0adkll.slidr.Slidr;

/**
 * Created by ccy on 2017-12-20.
 */

public class SliderAct2 extends BaseActivity {
    Button btn;
    ViewGroup viewGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slider_act);
        Slidr.attach(this);
        btn = (Button) findViewById(R.id.btn);
    }
}
