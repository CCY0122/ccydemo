package com.example.ccydemo.dragAndDropDemo;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ccydemo.BaseActivity;
import com.example.ccydemo.R;
import com.example.ccydemo.Util.Util;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ccy on 2017-11-17.
 */

public class DragAndDropAct extends BaseActivity {

    @BindView(R.id.drag_fl_1)
    FrameLayout f1;
    @BindView(R.id.drag_fl_2)
    FrameLayout f2;
    @BindView(R.id.drag_out)
    FrameLayout f3;
    @BindView(R.id.drag_tv_1)
    TextView tv1;
    @BindView(R.id.drag_tv_2)
    TextView tv2;
    @BindView(R.id.drag_img_1)
    ImageView img1;
    @BindView(R.id.drag_img_2)
    ImageView img2;
    @BindView(R.id.drag_img_3)
    ImageView img3;
    @BindView(R.id.drag_ll_1)
    LinearLayout l1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drag_and_drop);
        ButterKnife.bind(this);
//        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/GifDemo/mgif.gif");
//        Glide.with(this).load(file).asGif().into(img1);
        initViewDragListener(); //初始化要接收拖动事件的view的监听器
        initDragViews();  //初始化能拖动的view
    }

    private void initViewDragListener() {
        f1.setOnDragListener(f1DragLs);
        f2.setOnDragListener(f2DragLs);
        f3.setOnDragListener(f3DragLs);
    }

    private void initDragViews() {
        img1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                ClipData.Item item = new ClipData.Item("我是一个雨天图标");
                ClipData clipData = new ClipData("雨天", new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
                View.DragShadowBuilder shadowBuild = new View.DragShadowBuilder(v); //直接使用默认的单参数构造方法
                v.startDrag(clipData, shadowBuild, null, 0);
                v.setVisibility(View.INVISIBLE); //开始拖动的时候，把自己隐藏掉
                return true;
            }
        });

        img2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //测试一下ClipData.Item里传入text/Intent/Uri三个数据，但是ClipData中mimeTypes只有MIMETYPE_TEXT_PLAIN的话，最后结束时能不能获取到这三个数据，还是说只能获取text了？
                //测试结果：能获取
                Intent intent = new Intent();
                intent.putExtra("test", "我是intent里的数据");
                ClipData.Item item = new ClipData.Item("我是一个晴天图标", intent, Uri.parse("www.baidu.com"));
                ClipData clipData = new ClipData("晴天", new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
                v.startDrag(clipData, new Img2ShadowBuilder(v), null, 0);
                v.setVisibility(View.INVISIBLE);
                return true;
            }
        });

        img3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData.Item item = new ClipData.Item("我是海贼王图标");
                ClipData clipData = new ClipData("海贼王", new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
                v.startDrag(clipData, new Img3ShadowBuilder(v), null, 0);
                v.setVisibility(View.INVISIBLE);
                return true;
            }
        });

        //test
//        img1.setOnDragListener(new View.OnDragListener() {
//            @Override
//            public boolean onDrag(View v, DragEvent event) {
//                Log.d("ccy", "img1 receive action = " + event.getAction());
//                return true;
//            }
//        });

    }

    private View.OnDragListener f1DragLs = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d("ccy","f1:接收到start事件");
                    if (event.getClipDescription().getLabel().equals("雨天")) {
                        v.setBackgroundColor(0x440000ff);
                        return true;//返回true，后续事件才会继续接收
                    }
                    return false;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundColor(0x4400ff00);
                    return false;  //测试该return
                case DragEvent.ACTION_DRAG_LOCATION:
                    Log.d("ycc", "f1:有视图拖到我的内部了, x=" + event.getX() + ";y = " + event.getY());
                    return false;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(0x440000ff);
                    return true;
                case DragEvent.ACTION_DROP:
                    ClipData data = event.getClipData();
                    Toast.makeText(DragAndDropAct.this,"f1:接收到："+data.getItemAt(0).getText(),Toast.LENGTH_SHORT).show();
                    return data.getDescription().getLabel().equals("雨天");
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d("ccy","f1:接收到end事件");
                    v.setBackgroundColor(0xffffffff);
                    Log.d("ccy","f1:result = " + event.getResult());
                    img1.setVisibility(View.VISIBLE);
                    img2.setVisibility(View.VISIBLE);
                    img3.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    };

    private View.OnDragListener f2DragLs = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d("ccy","f2:接收到start事件");
                    if(event.getClipDescription().getLabel().equals("晴天")){
                        v.setBackgroundColor(0x440000ff);
                        return true;//返回true，后续事件才会继续接收
                    }
                    return false;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundColor(0x4400ff00);
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    Log.d("ycc", "f2:有视图拖到我的内部了, x=" + event.getX() + ";y = " + event.getY());
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(0x440000ff);
                    return false;
                case DragEvent.ACTION_DROP:
                    ClipData data = event.getClipData();
                    Toast.makeText(DragAndDropAct.this,"f2:接收到："+data.getItemAt(0).getText(),Toast.LENGTH_SHORT).show();
                    if(data.getItemAt(0).getIntent() != null){
                        Log.d("ccy","有intent信息：" +data.getItemAt(0).getIntent().getStringExtra("test"));
                    }
                    if(data.getItemAt(0).getUri() != null){
                        Log.d("ccy","有Uri信息：" + data.getItemAt(0).getUri().toString());
                    }
                    return data.getDescription().getLabel().equals("晴天");

                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d("ccy","f2:接收到end事件");
                    v.setBackgroundColor(0xffffffff);
                    Log.d("ccy","f2:result = " + event.getResult());
                    return true;

            }
            return false;
        }
    };

    private View.OnDragListener f3DragLs = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d("ccy","f3:接收到start事件");
                    if(event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)){
                        v.setBackgroundColor(0x440000ff);
                        return true;//返回true，后续事件才会继续接收
                    }
                    return false;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d("ccy","f3 enter");
                    return false;
                case DragEvent.ACTION_DRAG_LOCATION:
                    Log.d("ycc","f3 location ,x = " + event.getX() + ";y = " + event.getY());
                    return false;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d("ccy","f3 exited");
                    return false;
                case DragEvent.ACTION_DROP:
                    Log.d("ccy","f3 drop");
                    return false;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d("ccy","f3 ended,result = " + event.getResult());
                    return true;
            }
            return false;
        }
    };


    private class Img2ShadowBuilder extends View.DragShadowBuilder {


        public Img2ShadowBuilder(View view) {
            super(view);
        }

        @Override
        public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
            View v = getView();  //获取传入的view
            if (v != null) { //需要判空，看源码可知它是弱引用

                int width = v.getWidth();
                int height = v.getHeight();

                //outShadowSize的x、y代表了拖动图标的宽和高,这里让拖动视图和原来一样大
                outShadowSize.set(width,height);
                //outShadowTouchPoint的x、y代表了手指触摸点相对拖动视图的位置，这里即让拖动视图位于手指的正上方并偏移一定距离
                outShadowTouchPoint.set(outShadowSize.x / 2, outShadowSize.y + 100);
            }
        }

        //不修改，默认会画出传入的view
        @Override
        public void onDrawShadow(Canvas canvas) {
            super.onDrawShadow(canvas);
        }
    }

    private class Img3ShadowBuilder extends View.DragShadowBuilder {

        private Bitmap bmp;

        public Img3ShadowBuilder(View view) {
            super(view);
            bmp = Util.decodeBitmapFromRes(DragAndDropAct.this, R.drawable.z, 150, 150);
        }

        @Override
        public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
            View v = getView();  //获取传入的view
            if (v != null) { //需要判空，看源码可知它是弱引用

                int width = v.getWidth();
                int height = v.getHeight();

                //outShadowSize的x、y代表了拖动视图的宽和高,这里让拖动视图为原来的2倍
                outShadowSize.set(2*width,2*height);
                //outShadowTouchPoint的x、y代表了手指触摸点相对拖动视图的位置，这里即让拖动视图位于手指的正左方并偏移一定距离
                outShadowTouchPoint.set(outShadowSize.x + 100 , outShadowSize.y / 2);
            }
        }

        //画一个自己想要显示的拖动视图
        @Override
        public void onDrawShadow(Canvas canvas) {
            canvas.drawBitmap(bmp, null, new Rect(0, 0, canvas.getWidth(), canvas.getHeight()), null);
        }
    }


}
