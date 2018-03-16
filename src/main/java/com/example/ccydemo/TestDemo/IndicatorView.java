package com.example.ccydemo.TestDemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ccydemo.R;

/**
 * Created by ccy on 2018-02-09.
 */

public class IndicatorView extends LinearLayout {

    private ViewPager viewPager; //绑定的viewPager
    private int itemCount = 1; //item数量
    private CharSequence[] titles; //各item对应标题
    private float itemWidth; //item宽度
    private float circleRadius = dp2px(2.5f);

    private int currentPosition = 0;
    private float positionOffset = 0.0f;
    private float indicatorTranslation = 0.0f;

    private Paint paint;


    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOrientation(HORIZONTAL);
        setPadding(getPaddingLeft(),
                getPaddingTop(),
                getPaddingRight(),
                (int) (getPaddingBottom() + 3 * circleRadius));

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
    }

    /**
     * 与ViewPager绑定
     */
    public void setupWithViewPager(final ViewPager viewPager) {
        post(new Runnable() {
            @Override
            public void run() {

                IndicatorView.this.viewPager = viewPager;
                PagerAdapter adapter = viewPager.getAdapter();
                if (adapter == null) {
                    throw new RuntimeException("先给viewPager设置Adapter");
                }
                itemCount = adapter.getCount();
                titles = new String[itemCount];
                for (int i = 0; i < itemCount; i++) {
                    titles[i] = adapter.getPageTitle(i);
                    if (titles[i] == null) {
                        Log.e("ccy", "警告：没有复写adapter.getPageTitle(Position)");
                    }
                }
                IndicatorView.this.currentPosition = viewPager.getCurrentItem();
                IndicatorView.this.positionOffset = 0.0f;
                viewPager.addOnPageChangeListener(pageChangeListener);

                removeAllViews();
                setWeightSum(itemCount);
                for (int i = 0; i < itemCount; i++) {
                    final int in = i;
                    TextView textView = createTextView();
                    textView.setText(titles[in] == null ? "" : titles[in]);
                    LayoutParams params = new LayoutParams(0, LayoutParams.MATCH_PARENT);
                    params.weight = 1;
                    addView(textView, params);
                    textView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewPager.setCurrentItem(in);
                        }
                    });
                }
                requestLayout();
            }

        });
    }

    /**
     * 这里定制TextView样式
     */
    private TextView createTextView() {
        TextView textView = new TextView(getContext());
        textView.setTextSize(14);
        textView.setGravity(Gravity.CENTER);
        textView.setClickable(true);
        textView.setTextColor(getResources().getColorStateList(R.color.state_color));
        return textView;
    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            IndicatorView.this.currentPosition = position;
            IndicatorView.this.positionOffset = positionOffset;
            invalidate();
        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < IndicatorView.this.getChildCount(); i++) {
                IndicatorView.this.getChildAt(i).setSelected(false);
            }
            if (IndicatorView.this.getChildAt(position) != null) {
                IndicatorView.this.getChildAt(position).setSelected(true);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initData();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (viewPager != null) {
            canvas.save();
            indicatorTranslation = (currentPosition + positionOffset) * itemWidth + itemWidth / 2.0f;
            canvas.drawCircle(indicatorTranslation,
                    getMeasuredHeight() - 2 * circleRadius,
                    circleRadius,
                    paint);
            canvas.restore();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (oldh != 0 || oldw != 0) {
            initData();
        }
    }

    private void initData() {
        if (itemCount > 0) {
            itemWidth = (getMeasuredWidth() * 1.0f) / itemCount;
        }
        Log.d("ccy", "item width 1 = " + itemWidth +
                ";item width 2 = " + (getChildCount() > 0 ? getChildAt(0).getMeasuredWidth() : "no child"));
    }


    private float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private float sp2px(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }
}
