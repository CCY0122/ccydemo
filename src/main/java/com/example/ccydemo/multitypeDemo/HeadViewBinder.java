package com.example.ccydemo.multitypeDemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.example.ccydemo.R;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by ccy on 2017-11-20.
 */

public class HeadViewBinder extends ItemViewBinder<HorizontalBean,HeadViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View v = inflater.inflate(R.layout.simple_view_pager,parent,false);
        return new ViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull HorizontalBean item) {
        holder.setDatas(holder.itemView.getContext(),item.resIds);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewPager vp;
        List<Integer> data = new ArrayList<>();
        List<View> views = new ArrayList<>();

        public ViewHolder(View itemView) {
            super(itemView);
            vp = (ViewPager) itemView.findViewById(R.id.simple_viewpager_item);
            vp.setOffscreenPageLimit(3);
        }


        public void setDatas(Context c,List<Integer> datas){
            this.data = datas;
            initVpViews(c);
            vp.setAdapter(new PagerAdapter() {

                @Override
                public int getCount() {
                    return data.size();
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    container.addView(views.get(position));
                    return views.get(position);
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView(views.get(position));
                }
            });
        }

        private void initVpViews(Context context) {

            for (int i = 0; i < data.size(); i++) {
                View v = LayoutInflater.from(context).inflate(R.layout.simple_big_imageview,vp,false);
                ImageView img = (ImageView) v.findViewById(R.id.simple_big_imageview_item);
                img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                img.setImageResource(data.get(i));
                views.add(v);
            }
        }
    }
}
