package com.example.ccydemo.recyclerviewDiffutilDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.example.ccydemo.BaseActivity;
import com.example.ccydemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ccy on 2017-11-13.
 */

public class DiffUtilAct extends BaseActivity {

    @BindView(R.id.diff_rv)
    RecyclerView rv;

    private List<SimpleBean> datas = new ArrayList<>();
    private List<SimpleBean> newDatas = new ArrayList<>();
    private SimpleRvAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diffutil_act);
        ButterKnife.bind(this);

        createDatas(datas);
        adapter = new SimpleRvAdapter(datas);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

    }

    private void createDatas(List<SimpleBean> mdatas) {
        mdatas.clear();
        for (int i = 0; i < 5; i++) {
            SimpleBean bean = new SimpleBean(i,"asdasdada = " + i,"qweqweqweqwe",R.drawable.cloudy);
            mdatas.add(bean);
        }
    }

    @OnClick(R.id.diff_b1)
    public void textInsert(){
        newDatas = new ArrayList<>(datas);
        newDatas.add(randomPos(),new SimpleBean(20,"insert!!!","lalala",R.drawable.sun));
        diffNotify();
    }

    @OnClick(R.id.diff_b2)
    public void textDelete(){
        newDatas = new ArrayList<>(datas);
        newDatas.remove(randomPos());
        newDatas.remove(randomPos());
        diffNotify();
    }

    @OnClick(R.id.diff_b3)
    public void texeChange(){
        newDatas = new ArrayList<>(datas);
        newDatas.set(randomPos(),new SimpleBean(randomPos(),"change","!!!!",R.drawable.thunder));
        diffNotify();
    }

    private int randomPos(){
        return (int)(Math.random()*newDatas.size() - 1);
    }

    @OnClick(R.id.diff_b4)
    public void reset(){
//        createDatas(newDatas);
//        diffNotify();
        //asdasd
    }

    private void diffNotify(){

        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtilCallback(datas,newDatas));
        result.dispatchUpdatesTo(adapter);

        datas = newDatas;
        adapter.setDatas(datas);
    }
}
