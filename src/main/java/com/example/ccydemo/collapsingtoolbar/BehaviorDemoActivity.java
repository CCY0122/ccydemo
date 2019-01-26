package com.example.ccydemo.collapsingtoolbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import com.example.ccydemo.DragAndSwipe.SimpleRvAdapter;
import com.example.ccydemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccy(17022) on 2018/10/25 上午11:35
 */
public class BehaviorDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.behavior_demo_act);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add("this is " + i);
        }
        SimpleRvAdapter adapter = new SimpleRvAdapter(this,data);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setNestedScrollingEnabled(false);
        //fixme 经打印，这样recyclerview的复用机制没用了。所以平时不要用这种方式嵌套

    }
}
