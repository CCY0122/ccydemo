package com.example.ccydemo.DragAndSwipe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ccydemo.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by XMuser on 2017-01-16.
 */

public class SimpleRvAdapter extends RecyclerView.Adapter<SimpleRvAdapter.ViewHolder> {

    private Context context;
    private List<String> data;
    private ItemTouchHelper itemTouchHelper;

    public SimpleRvAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    public SimpleRvAdapter(Context context, List<String> data, ItemTouchHelper itemTouchHelper) {
        this(context, data);
        this.itemTouchHelper = itemTouchHelper;
    }

    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position).contains("ccy")) {
            return 1;
        }
        return super.getItemViewType(position);
    }

    @Override
    public SimpleRvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("SimpleRvAdapter","onCreateViewHolder");
        if (viewType == 1) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_text_img, parent, false);
            return new ViewHolder2(v);

        }
        View v = LayoutInflater.from(context).inflate(R.layout.rv_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.d("SimpleRvAdapter","onBindViewHolder");
        holder.tv.setText(data.get(position) + "");
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                View content = LayoutInflater.from(v.getContext()).inflate(R.layout.item_text,null);
//                content.setBackgroundColor(0x99000000);
//                TextView tv = (TextView) content.findViewById(R.id.item_tv);
//                tv.setText("!\n!!!!!!!!!text = " + data.get(holder.getAdapterPosition()));
//                PopupWindow popupWindow = new PopupWindow(content,200,500);
//                popupWindow.setTouchable(true);
//                popupWindow.setOutsideTouchable(true);
//                popupWindow.setFocusable(true);
//                popupWindow.showAsDropDown(holder.itemView);
                Toast.makeText(context, "adapter pos = " + holder.getAdapterPosition() + ";layout pos = " + holder.getLayoutPosition(), Toast.LENGTH_SHORT).show();
            }
        });
//        holder.tv.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(event.getAction() == MotionEvent.ACTION_DOWN && itemTouchHelper != null){
//                    itemTouchHelper.startDrag(holder);
//                }
//                return false;
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void onDrag(int from, int to) {
        //不能简单的Swap(from,to),对于网格布局来讲，并不是互换两个item，而是from和to之间的所有item到偏移一个位置
        if (from < to) {
            for (int i = from; i < to; i++) {
                Collections.swap(data, i, i + 1);
            }
        } else {
            for (int i = from; i > to; i--) {
                Collections.swap(data, i, i - 1);
            }
        }
//        String ori = data.get(from);
//        data.remove(from);
//        data.add(to,ori);
        notifyItemMoved(from, to);
    }

    public void onSwiped(int pos) {
        data.remove(pos);
        notifyItemRemoved(pos);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.rv_item_tv);
        }
    }

    class ViewHolder2 extends ViewHolder {

        public ViewHolder2(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.item_tv);
        }
    }
}
