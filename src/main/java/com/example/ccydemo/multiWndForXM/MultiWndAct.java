package com.example.ccydemo.multiWndForXM;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ccydemo.BaseActivity;
import com.example.ccydemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ccy on 2017-12-20.
 */

public class MultiWndAct extends BaseActivity {

    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.b1)
    Button b1;

    private List<View> vpViews = new ArrayList<>();
    private List<MultiWndRecyclerAdapter> adapters = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multi_wnd_act);
        ButterKnife.bind(this);
        List<View> views1 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            TextView tv = new TextView(this);
            tv.setText(""+i);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundResource(R.drawable.stroke_selected);
            final int in = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ccy","click = " + in);
                    v.setSelected(true);
                }
            });
            views1.add(tv);
        }
        initWnd(views1);

        List<View> views2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TextView tv = new TextView(this);
            tv.setText(""+i);
            tv.setGravity(Gravity.CENTER);
            final int in = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ccy","click = " + in);
                    adapters.get(1).setSelectedId(in);
                }
            });
            views2.add(tv);
        }
        initWnd(views2);


        initViewPager(vpViews);
    }

    private void initViewPager(List<View> vpViews) {
        VpAdapter adapter = new VpAdapter(vpViews);
        vp.setAdapter(adapter);
    }

    private void initWnd(List<View> views){
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        int spanCount = (int) Math.ceil(Math.sqrt(views.size()));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,spanCount);
        MultiWndRecyclerAdapter adapter = new MultiWndRecyclerAdapter(recyclerView,views,0,spanCount,vp.getLayoutParams().height);
        SwapTouchHelper swapTouchHelper = new SwapTouchHelper(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swapTouchHelper);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        adapters.add(adapter);
        vpViews.add(recyclerView);
    }



    class VpAdapter extends PagerAdapter{

        private final List<View> views;

        public VpAdapter(List<View> views){
            this.views = views;
        }

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
            Log.d("ccy","instantiateItem");
            container.addView(views.get(position), ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.d("ccy", "destroyItem");
           container.removeView(views.get(position));
        }
    }
}
