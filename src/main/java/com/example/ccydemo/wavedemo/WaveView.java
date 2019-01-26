package com.example.ccydemo.wavedemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.ccydemo.R;

/**
 * Created by chenchaoyong on 2018/4/28.
 */

public class WaveView extends View {

    //数据
    private int waveCount;//控件内可见波的最大数量
    private float waveWidth; //一个波的波长，根据waveCount计算得出
    private float waveHeight;
    private long waveDuration;//波浪速度（ms）
    private int waveColor;
    private float waveBaseLineY; //波浪Y轴基线

    //绘制
    private Paint mPaint;
    private Path mPath;
    private ValueAnimator animator;


    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(context,attrs);
        initView();
    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(waveColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(dp2px(1));
        mPath = new Path();
    }

    private void initAttrs(Context context,AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WaveView);
        waveCount = ta.getInt(R.styleable.WaveView_waveCount,1);
        waveColor = ta.getColor(R.styleable.WaveView_waveColor,0x00000000);
        waveHeight = ta.getDimension(R.styleable.WaveView_waveHeight,dp2px(15));
        waveDuration = ta.getInt(R.styleable.WaveView_waveDuration,3000);
        waveBaseLineY = ta.getDimension(R.styleable.WaveView_waveBaseLineY,waveHeight / 2 + dp2px(10));
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wsize = MeasureSpec.getSize(widthMeasureSpec);
        int wmode = MeasureSpec.getMode(widthMeasureSpec);
        int hsize = MeasureSpec.getSize(heightMeasureSpec);
        int hmode = MeasureSpec.getMode(heightMeasureSpec);

        if (wmode != MeasureSpec.EXACTLY) {
            wsize = (int) dp2px(200);
        }
        if (hmode != MeasureSpec.EXACTLY) {
            hsize = (int) (2 * waveHeight + dp2px(30));
        }
        resolveSize(wsize, widthMeasureSpec);
        resolveSize(hsize, heightMeasureSpec);
        setMeasuredDimension(wsize, hsize);

        initDrawData();

    }

    /**
     * 这里不是通过动画进度计算一个偏移量(或sin值)来动态生成path的，这样每次Invalidate时就需要重新生成path，比较耗资源
     * 这里是只画一条波浪path,然后在动画期间通过scroll来偏移整个控件来实现波浪效果
     */
    private void initDrawData() {
        waveWidth = getMeasuredWidth() / (waveCount * 1.0f);

        mPath.reset();
        mPath.moveTo(-getMeasuredWidth(), waveBaseLineY);

        float startX = -getMeasuredWidth();

        float controlX, controlY, endX, endY;
        for (int i = 0; i < waveCount * 4; i++) {
            controlX = startX + (waveWidth / 4);
            controlY = waveBaseLineY + waveHeight * (i % 2 == 0 ? 1 : -1);//偶数波峰奇数波谷
            endX = startX + waveWidth / 2;
            endY = waveBaseLineY;

            mPath.quadTo(controlX,
                    controlY,
                    endX,
                    endY);

            startX = endX;
        }
        //为了能填充波浪颜色 波浪的下半部分封闭，
        mPath.lineTo(getMeasuredWidth(), waveBaseLineY);
        mPath.lineTo(getMeasuredWidth(), getMeasuredHeight());
        mPath.lineTo(-getMeasuredWidth(), getMeasuredHeight());
        mPath.close();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (oldh != 0)
            initDrawData();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawWave(canvas);
    }

    private void drawWave(Canvas canvas) {

        canvas.save();
        canvas.drawPath(mPath, mPaint);
        canvas.restore();

    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
    }


    //setter getter

    public void start() {
        if (animator != null && animator.isStarted()) {
            return;
        }
        if (animator == null) {
            animator = ValueAnimator.ofFloat(0.0f, 1.0f);
            animator.setDuration(waveDuration);
            animator.setInterpolator(new LinearInterpolator());
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setRepeatMode(ValueAnimator.RESTART);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float fraction = animation.getAnimatedFraction();
                    float dx = getMeasuredWidth() * fraction;
                    scrollTo((int) -dx, 0);
                    postInvalidate();
                }
            });
        }
        animator.start();
    }

    public void stop() {
        if (animator != null && animator.isStarted()) {
            animator.cancel();
        }
    }


    public float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
