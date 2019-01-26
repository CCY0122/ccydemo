package com.example.ccydemo.wavedemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.ccydemo.BaseActivity;
import com.example.ccydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WaveActivity extends BaseActivity {

    @BindView(R.id.w)
    WaveView waveView;
    @BindView(R.id.w2)
    WaveView waveView2;
    @BindView(R.id.w3)
    WaveView waveView3;
    @BindView(R.id.w4)
    WaveView waveView4;
    @BindView(R.id.b1)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.b1)
    void b1(View v){
        waveView.start();
        waveView2.start();
        waveView3.start();
        waveView4.start();
    }
    @OnClick(R.id.b2)
    void b2(){
        waveView.stop();
        waveView2.stop();
        waveView3.stop();
        waveView4.stop();
    }

}
