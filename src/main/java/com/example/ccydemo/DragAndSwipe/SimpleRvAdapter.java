package com.example.ccydemo.DragAndSwipe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

    public SimpleRvAdapter(Context context , List<String> data){
        this.context = context;
        this.data = data;
    }
    public SimpleRvAdapter(Context context , List<String> data , ItemTouchHelper itemTouchHelper){
        this(context,data);
        this.itemTouchHelper = itemTouchHelper;
    }
    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper){
        this.itemTouchHelper = itemTouchHelper;
    }
    @Override
    public SimpleRvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.rv_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int pos = holder.getAdapterPosition();
        final int pos_2 = position;
        final int pos_3 = holder.getLayoutPosition();
        holder.tv.setText(data.get(pos)+"");
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,pos+"--"+pos_2+"--"+pos_3,Toast.LENGTH_SHORT).show();
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

    public void onDrag(int from , int to){
        Collections.swap(data,from,to);
        notifyItemMoved(from,to);
    }
    public void onSwiped(int pos){
        data.remove(pos);
        notifyItemRemoved(pos);
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.rv_item_tv);
        }
    }
}
