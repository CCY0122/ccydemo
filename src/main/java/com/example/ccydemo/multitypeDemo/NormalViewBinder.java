package com.example.ccydemo.multitypeDemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ccydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by ccy on 2017-11-20.
 */

public class NormalViewBinder extends ItemViewBinder<NormalBean,NormalViewBinder.ViewHolder> {

    private OnNormalClickLs ls;

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View v = inflater.inflate(R.layout.multi_normal_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull NormalBean item) {
        holder.textView.setText("pos = "+getPosition(holder)+";id = " + item.id+";content = " + item.content);
    }

    public interface OnNormalClickLs{
        void normalClick(View v,int pos);
    }
    public void setOnNormalClickLs(OnNormalClickLs l){
        this.ls = l;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.multi_item_tv)
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        @OnClick(R.id.multi_item_tv)
        void clickTv(View v){
            if(ls != null){
                ls.normalClick(v,getAdapterPosition());
            }
        }
    }
}
