package com.example.ccydemo.multitypeDemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ccydemo.R;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by ccy on 2017-11-20.
 */

public class HorizontalViewBinder extends ItemViewBinder<HorizontalBean,HorizontalViewBinder.ViewHolder> {


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View v = inflater.inflate(R.layout.simple_recycler,parent,false);
        return new ViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull HorizontalBean item) {
        holder.setData(item.resIds);
    }

    private int dp2px(Context c, int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,c.getResources().getDisplayMetrics());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rv;
        SimpleRvAdapter adapter;
        public ViewHolder(View itemView) {
            super(itemView);
            rv = (RecyclerView) itemView.findViewById(R.id.simple_recycler);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext(),LinearLayoutManager.HORIZONTAL,false);
            rv.setLayoutManager(linearLayoutManager);
            adapter = new SimpleRvAdapter(new ArrayList<Integer>());
            rv.setAdapter(adapter);
        }

        public void setData(List<Integer> data){
            adapter.setData(data);
            adapter.notifyDataSetChanged();
        }
    }
}
