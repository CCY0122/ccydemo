package com.example.ccydemo.DragAndSwipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.example.ccydemo.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv;
    private ItemTouchHelper itemTouchHelper;
    private ItemTouchHelper.Callback callback;
    private List<String> data = new ArrayList<>();
    private SimpleRvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("ccy","ccy");

        rv = (RecyclerView) findViewById(R.id.recycler);
        for (int i = 0; i < 30; i++) {
            data.add(i + "===");
        }
        initRv();
    }

    private void initRv() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());
        adapter = new SimpleRvAdapter(this , data);
        callback = new MyCallback(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(rv);
//        adapter.setItemTouchHelper(itemTouchHelper);
        rv.setAdapter(adapter);
//
//        int flag_1 = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
//        int flag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
//        callback = new MyCallback2(flag,flag_1);
////        ((MyCallback2)callback).setAdapter(adapter);
//        itemTouchHelper = new ItemTouchHelper(callback);
//        itemTouchHelper.attachToRecyclerView(rv);
//        rv.setAdapter(adapter);
    }
}
