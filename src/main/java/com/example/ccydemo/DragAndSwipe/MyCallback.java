package com.example.ccydemo.DragAndSwipe;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by XMuser on 2017-01-16.
 */

public class MyCallback extends ItemTouchHelper.Callback {

    private SimpleRvAdapter adapter;

    public MyCallback(SimpleRvAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int flag_1 = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int flag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(flag, flag_1);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        adapter.onDrag(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        Log.d("ccy", "onMove");
        return true;
    }

    @Override
    public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
//        adapter.onDrag(viewHolder.getAdapterPosition(), target.getAdapterPosition());
//        adapter.onDrag(fromPos,toPos);
        Log.d("ccy","onMoved");
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.onSwiped(viewHolder.getAdapterPosition());
        Log.d("ccy", "onSwiped");
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return super.isLongPressDragEnabled();
    }


    @Override
    public boolean isItemViewSwipeEnabled() {
        return super.isItemViewSwipeEnabled();
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        Log.d("ccy","selectdChanged"+"action="+actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        Log.d("ccy","clearView");
    }
}
