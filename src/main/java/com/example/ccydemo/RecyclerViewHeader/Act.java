package com.example.ccydemo.RecyclerViewHeader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ccydemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XMuser on 2017-06-20.
 */

public class Act extends AppCompatActivity {

    private List<Bean> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_header);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(manager);
        GridLayoutManager manager1 = new GridLayoutManager(this,4);
        rv.setLayoutManager(manager1);
        initData();
        RecyclerAdapter adapter = new RecyclerAdapter(this,data);
        rv.setAdapter(adapter);
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Bean b = new Bean();
            b.date = "20160101";
            b.resId = R.drawable.a;
            data.add(b);
        }
        for (int i = 0; i < 8; i++) {
            Bean b = new Bean();
            b.date = "20160202";
            b.resId = R.drawable.q;
            data.add(b);
        }
        for (int i = 0; i < 1; i++) {
            Bean b = new Bean();
            b.date = "20160303";
            b.resId = R.drawable.z;
            data.add(b);
        }
        for (int i = 0; i < 5; i++) {
            Bean b = new Bean();
            b.date = "20160404";
            b.resId = R.drawable.q;
            data.add(b);
        }
        for (int i = 0; i < 11; i++) {
            Bean b = new Bean();
            b.date = "20160505";
            b.resId = R.drawable.a;
            data.add(b);
        }
    }
}
