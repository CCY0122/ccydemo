package com.example.ccydemo.rectLoadingView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.ccydemo.BaseActivity;
import com.example.ccydemo.R;

import ccy.rectloadingview.RectLoadingView;
import ccy.rectloadingview.animcontroller.RandomAnimController;
import ccy.rectloadingview.animcontroller.WaveAnimController;

/**
 * Created by ccy on 2017-11-22.
 */

public class RectLoadingAct extends BaseActivity {

    RectLoadingView v1;
    RectLoadingView v2;
    RectLoadingView v3;
    RectLoadingView v4;
    Button b1;
    Button b2;
    Button b3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rect_loading_act);

        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v4.startAnim();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v4.stopAnim(false);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //测试各种setter

//                v3.setDuration(2000);
//                v3.setMaxRectHeight(20);
                v3.setRectColor(0x44ff0000);
//                v3.setRectCount(4);
//                v3.setRoundMode(false);
            }
        });
        v1 = (RectLoadingView) findViewById(R.id.rect1);
        v1.startAnim();

        v2 = (RectLoadingView) findViewById(R.id.rect2);
        v2.setAnimController(new RandomAnimController());
        v2.startAnim();

        v3 = (RectLoadingView) findViewById(R.id.rect3);
        v3.setAnimController(new WaveAnimController(v3.getRectCount() / 4));
        v3.startAnim();

        v4 = (RectLoadingView) findViewById(R.id.rect4);
//        v4.setAnimController(new WaveAnimController(v3.getRectCount()/4,true,0.3f,1.0f));
//        v4.startAnim();

        final WaveAnimController animController = new WaveAnimController();
        v4.post(new Runnable() {
            @Override
            public void run() {
                animController.createAnim(v4);
                animController.startAnim();
            }
        });
    }
}
