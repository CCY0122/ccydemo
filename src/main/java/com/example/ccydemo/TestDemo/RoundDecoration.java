package com.example.ccydemo.TestDemo;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ccy(17022) on 2018/12/5 下午6:54
 */
public class RoundDecoration extends RecyclerView.ItemDecoration {


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State
            state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = 30;
        outRect.right = 30;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        Paint paint = new Paint();
        paint.setColor(0xff00ff00);

        float originTop = parent.getChildAt(2).getTop() ;
        float lastTop = parent.getChildAt(3).getBottom() ;
        float height = lastTop - originTop;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            c.drawRoundRect(10, parent.getChildAt(2).getTop(), parent.getRight() - 10, parent.getChildAt(3).getBottom(), 20, 20, paint);
        }

    }
}
