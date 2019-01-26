package com.example.ccydemo.DragAndSwipe;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);

            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        rv = (RecyclerView) findViewById(R.id.recycler);
        for (int i = 0; i < 30; i++) {
            data.add(i + "===");
        }
        initRv();
    }

    private void initRv() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
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
