package com.example.ccydemo.brvah;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ccydemo.BaseActivity;
import com.example.ccydemo.R;
import com.example.ccydemo.matisse.MatisseTestAct;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ccy(17022) on 2018/7/6 上午10:35
 */
public class BRVAHActivity extends BaseActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;

    private BRVAHAdapter adapter;
    private List<SectionBean> datas = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brvah);
        ButterKnife.bind(this);

        SectionBean bean = new SectionBean(new Bean("hh",R.drawable.sun));
        SectionBean bean1 = new SectionBean(new Bean("hsdh",R.drawable.rain));
        SectionBean bean4 = new SectionBean(true,"lalala");
        SectionBean bean2 = new SectionBean(new Bean("hhqweqwe",R.drawable.cloudy));
        SectionBean bean3 = new SectionBean(new Bean("hhqweqwe",R.drawable.sun_cloud));
        SectionBean bean5 = new SectionBean(new Bean("hhqweqwe",R.drawable.sun_cloud));
        bean5.isHeader = true;
        datas.add(bean);
        datas.add(bean1);
        datas.add(bean4);
        datas.add(bean2);
        datas.add(bean3);
        datas.add(bean5);

        adapter = new BRVAHAdapter(datas);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);




        ////泛型获取测试
        SubClassTest s = new SubClassTest();
        Type type = s.getClass().getGenericSuperclass();
        GenericTest(type);


        //
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BRVAHActivity.this, MatisseTestAct.class));
            }
        });
    }

    //泛型获取
    private void GenericTest(Type type){
        if(type instanceof ParameterizedType){
            ParameterizedType t = (ParameterizedType) type;
            Log.d("ccy","type = " + t);
            Type[] types = t.getActualTypeArguments();
            Log.d("ccy","sub type size = " + types.length);
            for (int i = 0; i < types.length; i++) {
                if(types[i] instanceof Class){
                    Class temp = (Class) types[i];
                    Log.d("ccy","i = " + i + ";class = "+temp.getName());
                }else if(types[i] instanceof ParameterizedType){
                    GenericTest(types[i]);
                }
            }
        }
    }

}
