package com.example.ccydemo.RecyclerViewHeader;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ccydemo.R;

import java.util.List;

/**
 * Created by XMuser on 2017-06-20.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0XFFFF;
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Bean> data;


    public RecyclerAdapter(Context context,List<Bean> data){
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        wrapData();
    }

    private void wrapData() {
        String lastDate = "";
        String currDate;
        for (int i = 0; i < data.size(); i++) {
            currDate = data.get(i).date;
            if(currDate != lastDate){
                Bean b = new Bean();
                b.type = TYPE_HEADER;
                b.date = currDate;
                data.add(i,b);
            }
            lastDate = data.get(i).date;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER){
            TextView tv = new TextView(context);
            tv.setTag(TYPE_HEADER);
            return new ViewHolder(tv);
        }else {
            View v = layoutInflater.inflate(R.layout.recycler_view_header_item,parent,false);
            return new ViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_HEADER){
            TextView tv = (TextView) holder.itemView;
            tv.setText(data.get(position).date);
        }else{
            ImageView iv = (ImageView) holder.itemView;
            iv.setImageResource(data.get(position).resId);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            final GridLayoutManager manager = (GridLayoutManager) layoutManager;
            final int spanCount = manager.getSpanCount();
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if(getItemViewType(position) == TYPE_HEADER){
                        return spanCount;
                    }
                    return 1;
                }
            });
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            int tag = itemView.getTag() == null?-1:(int) itemView.getTag();
            if(tag == TYPE_HEADER){
                TextView tv = (TextView) itemView;
                //处理header...
            }else {
                //处理普通...
            }
        }
    }
}
