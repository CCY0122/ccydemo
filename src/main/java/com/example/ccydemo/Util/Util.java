package com.example.ccydemo.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by XMuser on 2017-02-06.
 */

public class Util {
    public static Bitmap decodeBitmapFromFile(String filePath, int reqWidth, int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath,options);
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath,options);
    }

    public static Bitmap decodeBitmapFromRes(Context ctx,int resId, int reqWidth, int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(ctx.getResources(),resId,options);
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(ctx.getResources(),resId,options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {

        // Raw height and width of image
        if(reqWidth == 0 || reqHeight == 0){
            return 1;
        }

        final int height = options.outHeight;

        final int width = options.outWidth;

        int inSampleSize = 1;

//        if (height > reqHeight || width > reqWidth) {
//            final int halfHeight = height / 2;
//            final int halfWidth = width / 2;
//            while((halfHeight / inSampleSize) >= reqHeight
//                    && (halfWidth / inSampleSize) >= reqWidth){
//                inSampleSize *= 2;
//            }
//        }
        if(height > reqHeight || width > reqWidth){
            int wRatio = (int) Math.ceil( width * 1.0f / reqWidth);
            int hRatio = (int) Math.ceil(height * 1.0f / reqHeight );
            inSampleSize = Math.max(wRatio,hRatio);
        }

        return inSampleSize;

    }


}
