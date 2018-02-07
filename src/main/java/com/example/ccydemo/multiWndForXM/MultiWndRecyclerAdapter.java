package com.example.ccydemo.multiWndForXM;

import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.ccydemo.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by ccy on 2017-12-20.
 */

public class MultiWndRecyclerAdapter extends RecyclerView.Adapter<MultiWndRecyclerAdapter.ViewHolder> {

    private RecyclerView recyclerView;
    private final List<View> views;
    private int spanCount;
    private int parentHeight;
    private int selectedId;

    public MultiWndRecyclerAdapter(RecyclerView recyclerView,List<View> views,int initSelectedId, int spanCount, int parentHeight) {
        this.recyclerView = recyclerView;
        this.views = views;
        this.selectedId = initSelectedId;
        this.spanCount = spanCount;
        this.parentHeight = parentHeight;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.multi_wnd_item, parent, false);
        int height = parentHeight / spanCount;
        v.getLayoutParams().height = height;
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ViewGroup parent = (ViewGroup) holder.itemView;
        parent.removeAllViews();
        if(views.get(position).getParent() != null){
            ((ViewGroup)views.get(position).getParent()).removeAllViews();
        }
        parent.addView(views.get(position), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        parent.setBackgroundResource(R.drawable.stroke_selected);
        parent.setSelected(selectedId == position);
    }

    @Override
    public int getItemCount() {
        return views.size();
    }


    public void onSwap(int from , int to){
        Collections.swap(views,from,to);
        notifyItemMoved(from,to);
    }

    public int getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(int newSelectedId) {
        ViewHolder viewHolder1 = (ViewHolder) recyclerView.findViewHolderForLayoutPosition(selectedId);
        if(viewHolder1 != null){
            viewHolder1.itemView.setSelected(false);
        }
        ViewHolder viewHolder2 = (ViewHolder) recyclerView.findViewHolderForLayoutPosition(newSelectedId);
        if(viewHolder2 != null){
            viewHolder2.itemView.setSelected(true);
        }

        this.selectedId = newSelectedId;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
