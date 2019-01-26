package com.example.ccydemo.collapsingtoolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ccydemo.DragAndSwipe.MyCallback;
import com.example.ccydemo.DragAndSwipe.SimpleRvAdapter;
import com.example.ccydemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ccy(17022) on 2018/10/10 上午9:49
 */
public class CollapsingToolBarActivity extends AppCompatActivity {

    @BindView(R.id.co_vp)
    ViewPager vp;

    @BindView(R.id.co_coll)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.co_toolbar)
    Toolbar toolbar;

    @BindView(R.id.co_appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.co_big_banner)
    FrameLayout bigBanner;

    @BindView(R.id.co_tab_tv)
    TextView tab;
    private ArrayList<View> views;

    @BindView(R.id.toolbar_child)
    FrameLayout toolbarChild;
    private int initMarginTop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.collapsing_act);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags = (attr.flags | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

//        addStatusBarView(getResources().getColor(R.color.colorPrimary));

        ButterKnife.bind(this);
        initViewPager();


        toolbarChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CollapsingToolBarActivity.this,"点击了小banner",Toast.LENGTH_SHORT).show();
            }
        });
//        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) toolbarChild.getLayoutParams();
//        initMarginTop = params.topMargin;
        Log.d("ccy","40dp = " + dp2px(40) + ";150dp = " + dp2px(150) + ";appbar total range = " + appBarLayout.getTotalScrollRange());
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                Log.d("ccy","getTotalScrollRange = " + appBarLayout.getTotalScrollRange() + ";verticalOffset = " + verticalOffset);
                float fraction = Math.abs(verticalOffset)*1.0f /  Math.abs(appBarLayout.getTotalScrollRange());
                toolbar.setAlpha(fraction);
                bigBanner.setAlpha(1 - fraction);
//                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) toolbarChild.getLayoutParams();
//                params.topMargin = (int) (initMarginTop*(1-fraction));
//                //int转换导致精度问题修正
//                if(fraction <= 0){
//                    params.topMargin =  initMarginTop;
//                }
//                if(fraction >= 1){
//                    params.topMargin = 0;
//                }
//                toolbarChild.setLayoutParams(params);

            }
        });

    }

    @OnClick(R.id.co_tab_tv)
    void tab(){
        startActivity(new Intent(this,BehaviorDemoActivity.class));
    }


    public void initViewPager() {
        views = new ArrayList<>();
        views.add(getRecycler());
        views.add(getRecycler());
        vp.setAdapter(new ViewPagerAdapter(views));
    }

    public View getRecycler(){
        RecyclerView recyc = (RecyclerView) LayoutInflater.from(this).inflate(R.layout.simple_recycler, vp, false);
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            if(i%3 ==0 ){
                datas.add("ccy" + i);
            }else {
                datas.add("" + i);
            }
        }
        SimpleRvAdapter adapter = new SimpleRvAdapter(this,datas);
        recyc.setAdapter(adapter);
        recyc.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
//        recyc.setLayoutManager(new GridLayoutManager(this,2));
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyCallback(adapter));
//        itemTouchHelper.attachToRecyclerView(recyc);
        return recyc;
    }


    public static class ViewPagerAdapter extends PagerAdapter {


        private final List<View> views;

        public ViewPagerAdapter(List<View> views) {
            this.views = views;
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

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    public float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
            float result2 = getResources().getDimension(resourceId);
            Log.d("ccy",result + ";" + result2 + ";" + px2dp(result2));
        }
        return result;
    }

    public float px2dp(float px){
        return px / getResources().getDisplayMetrics().density;
    }

    private void addStatusBarView(int color){
        View view = new View(this);
        view.setBackgroundColor(color);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getStatusBarHeight());
        ViewGroup decorView = (ViewGroup) findViewById(android.R.id.content);
        decorView.addView(view,params);
    }
}
