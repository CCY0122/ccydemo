package com.example.ccydemo.BadgeAndSlideMenu;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuAdapter;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuView;
import com.example.ccydemo.BaseActivity;
import com.example.ccydemo.R;
import com.example.ccydemo.Util.Util;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by XMuser on 2017-03-03.
 */

public class BadgeAndSlidMenu extends BaseActivity {
    private TextView tv;
    private ImageView iv;
    private SwipeMenuListView lv;
    private List<String> data;

    private SwipeMenuCreator creator = new SwipeMenuCreator() {
        @Override
        public void create(SwipeMenu menu) {
            SwipeMenuItem item = new SwipeMenuItem(getApplicationContext());
            item.setTitle("删除");
            item.setTitleSize(20);
            item.setWidth(350);
            item.setTitleColor(0xff000000);
            item.setBackground(new ColorDrawable(0xffff0000));
            menu.addMenuItem(item);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.badge_swipe);
        getSupportActionBar().hide();

        data = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            data.add(i+"");
        }

        tv = (TextView) findViewById(R.id.tv1);
        iv = (ImageView) findViewById(R.id.iv1);
        lv = (SwipeMenuListView) findViewById(R.id.lv1);

        new QBadgeView(this).bindTarget(tv).setBadgeNumber(123);
        new QBadgeView(this).bindTarget(iv).setBadgeNumber(-1);

        lv.setMenuCreator(creator);
        lv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Toast.makeText(BadgeAndSlidMenu.this,position+"",Toast.LENGTH_LONG).show();
                return false;
            }
        });
        lv.setAdapter(new mAdapter());
        lv.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);



    }

    class mAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if(convertView == null){
                convertView = LayoutInflater.from(BadgeAndSlidMenu.this).inflate(R.layout.rv_item,parent,false);
                vh = new ViewHolder();
                vh.badge = new QBadgeView(BadgeAndSlidMenu.this).bindTarget(
                        convertView.findViewById(R.id.rv_item_tv));
//                vh.tv = (TextView) convertView.findViewById(R.id.rv_item_tv);
                convertView.setTag(vh);
            }else {
                vh = (ViewHolder) convertView.getTag();
            }
//            vh.tv.setText(position+"");
            vh.badge.setBadgeNumber(position);
            ((TextView)vh.badge.getTargetView()).setText(position+"");

            return convertView;
        }

        class ViewHolder{
            Badge badge;
            TextView tv;
        }
    }

}
