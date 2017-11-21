package com.example.ccydemo.EventBusDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.ccydemo.BaseActivity;
import com.example.ccydemo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ccy on 2017-07-27.
 */

public class EventBusAct2 extends BaseActivity {

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.btn)
    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventbus_act2);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void onMsgReceived(CustomBean bean){
        tv.setText(bean.id+"="+bean.str);
    }

    @OnClick(R.id.btn)
    public void b(){
        EventBus.getDefault().postSticky(new CustomBean(100,"from another act"));
    }
}
