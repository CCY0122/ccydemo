package com.example.ccydemo.sliderDemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.ccydemo.BaseActivity;
import com.example.ccydemo.R;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;

/**
 * Created by ccy on 2017-12-20.
 */

public class SliderAct1 extends BaseActivity {
    Button btn;
    ViewGroup viewGroup;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slider_act);
        SlidrConfig config =new  SlidrConfig.Builder()
                .build();
        Slidr.attach(this,config);
        viewGroup = (ViewGroup)findViewById(R.id.layoutRoot);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SliderAct1.this,SliderAct2.class));
            }
        });

    }
}
