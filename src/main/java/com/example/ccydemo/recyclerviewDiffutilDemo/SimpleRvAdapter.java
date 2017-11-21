package com.example.ccydemo.recyclerviewDiffutilDemo;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ccydemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ccy on 2017-11-13.
 */

public class SimpleRvAdapter extends RecyclerView.Adapter<SimpleRvAdapter.ViewHolder>  {

    private List<SimpleBean> datas = new ArrayList<>();

    public SimpleRvAdapter(List<SimpleBean> datas){
        setDatas(datas);
    }

    public void setDatas(List<SimpleBean> datas){
        this.datas = datas;
        //不要调用notifyDataSetChanged(),使用DiffUtil代替
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_rv_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SimpleBean bean = datas.get(position);
        holder.img.setImageResource(bean.icon);
        holder.tv1.setText(bean.text);
        holder.tv2.setText(bean.subText);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.rv_item_img)
        ImageView img;
        @BindView(R.id.rv_item_tv1)
        TextView tv1;
        @BindView(R.id.rv_item_tv2)
        TextView tv2;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"pos = " + getAdapterPosition() +";id = " + datas.get(getAdapterPosition()).id,Toast.LENGTH_LONG).show();
                }
            });
        }

    }
}
