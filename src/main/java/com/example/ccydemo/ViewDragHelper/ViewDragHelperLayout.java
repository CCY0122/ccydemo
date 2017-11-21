package com.example.ccydemo.ViewDragHelper;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * Created by XMuser on 2017-06-19.
 */

public class ViewDragHelperLayout extends LinearLayout {

    private static final String TAG ="ccy" ;
    private ViewDragHelper drag;
    private TextView t1;
    private Button b1;
    private TextView t2;

    public ViewDragHelperLayout(Context context) {
        this(context,null);
    }

    public ViewDragHelperLayout(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public ViewDragHelperLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        drag = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public void onViewDragStateChanged(int state) {
                super.onViewDragStateChanged(state);
                Log.d(TAG,"onViewDragStateChanged state =  "+ state);
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
//                Log.d(TAG,"onViewPositionChanged");
            }

            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
                Log.d(TAG,"onViewCaptured " );
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if(releasedChild == t1){
                    drag.settleCapturedViewAt(0,0);
                    invalidate();
                }
            }

            @Override
            public void onEdgeTouched(int edgeFlags, int pointerId) {
                super.onEdgeTouched(edgeFlags, pointerId);

            }

            @Override
            public boolean onEdgeLock(int edgeFlags) {
//                return edgeFlags != ViewDragHelper.EDGE_BOTTOM;
                return true;
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
                Log.d(TAG,"onEdgeDragStarted,edge = " + edgeFlags+";pointerId = " + pointerId);
                drag.captureChildView(t2,pointerId);
            }

            @Override
            public int getOrderedChildIndex(int index) {
//                Log.d(TAG,"getOrderedChildIndex = " + index);
                return super.getOrderedChildIndex(index);
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return 1; //?????为什么为1也可以任意范围移动？
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return 1;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
               if(left < -child.getMeasuredWidth()/2){
                   left = -child.getMeasuredWidth()/2;
               }
               if(left > getMeasuredWidth()-child.getMeasuredWidth()/2){
                   left = getMeasuredWidth()-child.getMeasuredWidth()/2;
               }
//               Log.d(TAG,"left = " + left);
               return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }
        });
//        drag.setEdgeTrackingEnabled(ViewDragHelper.EDGE_BOTTOM|ViewDragHelper.EDGE_RIGHT|ViewDragHelper.EDGE_LEFT|ViewDragHelper.EDGE_TOP);
          drag.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        t1 = (TextView) getChildAt(0);
        t2 = (TextView) getChildAt(1);
        b1 = (Button) getChildAt(2);
        t1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"t1",Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        drag.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return drag.shouldInterceptTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if(drag.continueSettling(true)){
            invalidate();
        }
    }

}
