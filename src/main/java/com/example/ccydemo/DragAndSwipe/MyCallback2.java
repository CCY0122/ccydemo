package com.example.ccydemo.DragAndSwipe;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

/**
 * Created by XMuser on 2017-01-17.
 */

public class MyCallback2 extends ItemTouchHelper.SimpleCallback {
    private RecyclerView.Adapter adapter;

    public MyCallback2(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }
    public void setAdapter(RecyclerView.Adapter adapter){
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Log.d("ccy","simple onMovie");
//        ((SimpleRvAdapter)adapter).onDrag(viewHolder.getLayoutPosition(),target.getLayoutPosition());
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.d("ccy","simple onSwiped===="+direction);
//        ((SimpleRvAdapter)adapter).onSwiped(viewHolder.getLayoutPosition());
    }
}
