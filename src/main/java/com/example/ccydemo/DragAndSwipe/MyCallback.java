package com.example.ccydemo.DragAndSwipe;

import android.app.Service;
import android.os.Vibrator;
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
        int flag = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(flag, 0);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //fixme 瀑布流拖拽交换位置，与第1个item交换时，列表有无限上移bug
        if(target.getAdapterPosition() == 0){
            return false;
        }
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
        return false;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if(actionState == ItemTouchHelper.ACTION_STATE_DRAG && viewHolder != null && viewHolder.itemView != null){
            Vibrator vib = (Vibrator) viewHolder.itemView.getContext().getSystemService(Service.VIBRATOR_SERVICE);//震动70毫秒
            vib.vibrate(70);
        }

        Log.d("ccy","selectdChanged"+"action="+actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        Log.d("ccy","clearView");
    }
}
