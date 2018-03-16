package com.example.ccydemo.EventBusDemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ccydemo.DragAndSwipe.MainActivity;
import com.example.ccydemo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ccy on 2017-07-27.
 */

public class CustomFragment extends Fragment {

    @BindView(R.id.tv1) TextView tv1;
    @BindView(R.id.tv2) TextView tv2;
    @BindView(R.id.b1)
    Button b1;
    @BindView(R.id.b4)
    Button b4;

    private static String TAG = "eventBus";
    private String msg;
    private int id;

    public static CustomFragment getInstance(int id,String msg){
        CustomFragment fragment = new CustomFragment();
        Bundle bundle = new Bundle();
        bundle.putString("msg",msg);
        bundle.putInt("id",id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        msg = getArguments().getString("msg");
        id = getArguments().getInt("id");
        Log.d(TAG,"oncreate" + id);
    }

    public int getmId() {
        return id;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        Log.d(TAG,"onstart" + id);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        Log.d(TAG,"onstop" + id);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"on create View" + id);
       View v = inflater.inflate(R.layout.simple_fragment_item,container,false);
        ButterKnife.bind(this,v);
        tv1.setText(msg);
        return v;
    }

    @Subscribe( threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CustomBean customBean){
       String currentThread =  Thread.currentThread().getName();
        Log.d("eventBus","fragment event invoke");
        Log.d("eventBus","thread = " + currentThread+";msg = " + msg);
        tv2.setText(customBean.id+"="+customBean.str);
    }

    @OnClick(R.id.b1)
    public void b1(){
        EventBus.getDefault().postSticky(new CustomBean(1,"post event to another frg from " + msg));
        getFragmentManager().beginTransaction().remove(this).commit();
    }


    @OnClick(R.id.b4)
    public void b4(){  //测试发送子类bean其接受父bean的事件是否会被调用
        EventBus.getDefault().post(new ChildBean(4,"can inherit child post to parent event? " + msg));
    }
    //测试结果：可以。发送对应bean的子类也能调用其父bean的订阅事件



    //生命周期测试
    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.d(TAG,"onAttachFragment" + id);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG,"onAttach" + id);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG,"onViewCreated" + id);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG,"onActivityCreated" + id);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"onPause" + id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG,"onDestroyView" + id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy" + id);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG,"onDetach" + id);
    }
}
