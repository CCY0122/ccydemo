package com.example.ccydemo.multitypeDemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.ccydemo.BaseActivity;
import com.example.ccydemo.R;
import com.google.zxing.client.result.AddressBookParsedResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.Linker;
import me.drakeet.multitype.MultiTypeAdapter;
import me.drakeet.multitype.MultiTypeAsserts;

/**
 * Created by ccy on 2017-11-20.
 */

public class MultitypeAct extends BaseActivity {
    private static final int GRID_SPAN_COUNT = 3;

    @BindView(R.id.multi_rv)
    RecyclerView recyclerView;
    @BindView(R.id.multi_vp)
    ViewPager viewPager;


    private Items items;
    private MultiTypeAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multitype_act);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        initRecyclerView();

        initData();

        initViewPager();

    }

    //测试clipChildren实现gallery效果
    private void initViewPager() {
        final List<View> views = new ArrayList<>();
        final List<Integer> data = new ArrayList<>();
        data.add(R.drawable.cloudy);
        data.add(R.drawable.sun);
        data.add(R.drawable.rain);
        data.add(R.drawable.thunder);
        data.add(R.drawable.cloudy);
        data.add(R.drawable.sun);
        data.add(R.drawable.rain);
        data.add(R.drawable.thunder);
        viewPager.setOffscreenPageLimit(3);
        for (int i = 0; i < data.size(); i++) {
            View v = LayoutInflater.from(this).inflate(R.layout.simple_big_imageview,viewPager,false);
            ImageView img = (ImageView) v.findViewById(R.id.simple_big_imageview_item);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setImageResource(data.get(i));
            views.add(v);
        }

        viewPager.setAdapter(new PagerAdapter() {

            @Override
            public int getCount() {
                return views.size();
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
        viewPager.setCurrentItem(3);
    }


    private void initRecyclerView() {
        initLayoutManager();
        initAdapter();
    }

    private void initLayoutManager() {
        //linear布局
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);

        //Grid布局
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, GRID_SPAN_COUNT);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (items.get(position) instanceof NormalBean) {
                    return 1;
                }
                return GRID_SPAN_COUNT;  //默认占满，就跟linearLayoutManager一样
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    private void initAdapter() {
        adapter = new MultiTypeAdapter();

        //测试同一个类型的ViewBinder多次注册的影响
        //结论：会覆盖
        NormalViewBinder normalViewBinder1 = new NormalViewBinder();
        normalViewBinder1.setOnNormalClickLs(new NormalViewBinder.OnNormalClickLs() {
            @Override
            public void normalClick(View v, int pos) {
                Log.d("ccy", "click from **First** normalViewBinder1,pos = " + pos);
            }
        });
        NormalViewBinder normalViewBinder2 = new NormalViewBinder();
        normalViewBinder2.setOnNormalClickLs(new NormalViewBinder.OnNormalClickLs() {
            @Override
            public void normalClick(View v, int pos) {
                Log.d("ccy", "click from **Second** normalViewBinder1,pos = " + pos);
            }
        });
        adapter.register(NormalBean.class, normalViewBinder1);
        adapter.register(NormalBean.class, normalViewBinder2);


        //测试一对多
        adapter.register(ChatBean.class).to(new ChatViewBinder1(), new ChatViewBinder2()).withLinker(new Linker<ChatBean>() {
            @Override
            public int index(int position, @NonNull ChatBean chatBean) {
                return chatBean.type == ChatBean.LEFT ? 0 : 1;
            }
        });

        //测试插入水平recyclerview和一对多
        adapter.register(HorizontalBean.class).to(new HeadViewBinder(), new HorizontalViewBinder()).withLinker(new Linker<HorizontalBean>() {
            @Override
            public int index(int position, @NonNull HorizontalBean horizontalBean) {
                return position == 0 ? 0 : 1;
            }
        });


        recyclerView.setAdapter(adapter);
    }


    private void initData() {
        int[] icons = {R.drawable.cloudy, R.drawable.a, R.drawable.q, R.drawable.sun, R.drawable.rain, R.drawable.thunder,R.drawable.cloudy,R.drawable.a, R.drawable.q};
        items = new Items();

        //头部
        HorizontalBean headBean = new HorizontalBean();
        headBean.resIds = new ArrayList<>();
        for (int i = 0; i < icons.length; i++) {
            headBean.resIds.add(icons[i]);
        }
        items.add(headBean);


        //普通
        for (int i = 0; i < 10; i++) {
            NormalBean bean = new NormalBean(i, "i am " + i);
            items.add(bean);
        }

        //聊天
        String[] chats = {"你好", "很高兴认识你", "哈哈哈哈233333333333", "excuse me?", "长长啊飒飒大师阿达四大洒水多群二无若企鹅王大大"};
        for (int i = 0; i < 8; i++) {
            ChatBean bean = new ChatBean(i % 2 == 0 ? ChatBean.LEFT : ChatBean.RIGHT, chats[i % (chats.length)]);
            items.add(bean);
        }

        //横向recycler
        HorizontalBean bean = new HorizontalBean();
        bean.resIds = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            bean.resIds.add(icons[i % (icons.length)]);
        }
        items.add(bean);


        adapter.setItems(items);
        adapter.notifyDataSetChanged();
    }


}
