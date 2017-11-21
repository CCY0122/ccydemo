package com.example.ccydemo.recyclerviewDiffutilDemo;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.util.Log;

import java.util.List;

/**
 * Created by ccy on 2017-11-13.
 */

public class DiffUtilCallback extends DiffUtil.Callback {

    private List<SimpleBean> oldDatas;
    private List<SimpleBean> newDatas;

    public DiffUtilCallback(List<SimpleBean> oldDatas, List<SimpleBean> newDatas){
        this.oldDatas = oldDatas;
        this.newDatas = newDatas;
    }

    @Override
    public int getOldListSize() {
        return oldDatas == null ? 0 : oldDatas.size();
    }

    @Override
    public int getNewListSize() {
        return newDatas == null ? 0 : newDatas.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
       return oldDatas.get(oldItemPosition).id == newDatas.get(newItemPosition).id;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        SimpleBean oldBean = oldDatas.get(oldItemPosition);
        SimpleBean newBean = newDatas.get(newItemPosition);
        if(!oldBean.text.equals(newBean.text)
                || !oldBean.subText.equals(newBean.subText)){
            return  false;
        }
        //测试：如果这里没判断icon是否相同，当实际icon改变时是不是不会触发更新？
//        if(oldBean.icon != newBean.icon){
//            return false
//        }
        return true;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
