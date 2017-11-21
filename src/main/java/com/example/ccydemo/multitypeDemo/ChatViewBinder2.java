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
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by ccy on 2017-11-20.
 */

public class ChatViewBinder2 extends ItemViewBinder<ChatBean,ChatViewBinder2.ViewHolder> {


    @NonNull
    @Override
    protected ChatViewBinder2.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View v = inflater.inflate(R.layout.multi_chat_item2,parent,false);
        return new ChatViewBinder2.ViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatViewBinder2.ViewHolder holder, @NonNull ChatBean item) {
        holder.tv.setText(item.content);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.multi_item_tv)
        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
