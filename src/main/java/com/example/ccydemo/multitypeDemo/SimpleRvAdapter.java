package com.example.ccydemo.multitypeDemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ccydemo.R;

import java.util.List;

/**
 * Created by ccy on 2017-11-20.
 */

public class SimpleRvAdapter extends RecyclerView.Adapter<SimpleRvAdapter.ViewHolder> {

    private List<Integer> data;

    public SimpleRvAdapter(List<Integer> data){
        this.data = data;
    }
    public void setData(List<Integer> data){
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_image_view,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.img.setImageResource(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.simple_image_item);
        }

        private int dp2px(Context c, int dp){
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,c.getResources().getDisplayMetrics());
        }
    }
}
