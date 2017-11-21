package com.example.ccydemo.gifDemo;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.bumptech.glide.gifencoder.AnimatedGifEncoder;
import com.example.ccydemo.Util.Util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccy on 2017-11-06.
 * 将多张图片合成为gif的工具类
 * （利用Glide的AnimatedGifEncoder）
 */

public class GifMaker {

    private static final int FAILED = 1;
    private static final int SUCCESS = 2;
    private static final int UPDATE = 3;

    private OnGifMakerListener listener;

    public interface OnGifMakerListener {

        /**
         * 转换成功
         *
         * @param path gif路径
         */
        void onSuccess(String path);


        /**
         * 转换失败
         *
         * @param e 失败原因
         */
        void onFailed(Exception e);

        /**
         * 转换进度
         *
         * @param currentFrame 当前完成转换的帧数
         * @param frameCount   总帧数
         */
        void onProgressUpdate(int currentFrame, int frameCount);
    }

    public void setOnGifMakerListener(OnGifMakerListener l) {
        this.listener = l;
    }

    public GifMaker() {

    }

    public GifMaker(OnGifMakerListener l) {
        this.listener = l;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    if (listener != null) {
                        listener.onSuccess((String) msg.obj);
                    }
                    break;
                case FAILED:
                    if (listener != null) {
                        listener.onFailed((Exception) msg.obj);
                    }
                    break;
                case UPDATE:
                    if (listener != null) {
                        listener.onProgressUpdate(msg.arg1, msg.arg2);
                    }
                    break;
            }
        }
    };

    /**
     * 生成gif
     * @param images 图片路径集合，它们的大小要一样
     * @param filePath 生成的gif路径（/xxx/xx.gif)
     * @param requestWidth 图片压缩宽
     * @param requsetHeight 图片压缩高
     */
    public void makeGif(final List<String> images, final String filePath, final int requestWidth, final int requsetHeight) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Bitmap> bmps = new ArrayList<>();
                for (String img : images) {
                    Bitmap bmp = Util.decodeBitmapFromFile(img, requestWidth, requsetHeight);  //注意压缩后的bitmap宽高并不一定是requestWidth、requsetHeight
                    if (bmp != null) {
                        Log.d("ccy","bmp w = " + bmp.getWidth()+";h = " + bmp.getHeight());
                        bmps.add(bmp);
                    }
                }

                makeGif(bmps, filePath);
            }
        }).start();
    }


    /**
     * 生成gif
     * @param bmps 图片集合,它们的大小要一样
     * @param filePath 生成的gif路径（/xxx/xx.gif)
     */
    public void makeGif(List<Bitmap> bmps, String filePath) {

        if (!checkFrameSize(bmps)) {
            handler.obtainMessage(FAILED, new IllegalArgumentException("图片的分辨率不一致")).sendToTarget();
            return;
        }
        ByteArrayOutputStream baos = null;
        FileOutputStream fos = null;
        try{
            baos = new ByteArrayOutputStream();
            AnimatedGifEncoder gifEncoder = new AnimatedGifEncoder();
            gifEncoder.start(baos);
            gifEncoder.setRepeat(0);  //0即无线循环播放
            gifEncoder.setDelay(200);
            for (int i = 0; i < bmps.size(); i++) {
                handler.obtainMessage(UPDATE, i, bmps.size()).sendToTarget();
                gifEncoder.addFrame(bmps.get(i));  //若后续图片大小不一致，将报错(默认将第一帧图的大小作为gif的大小）
            }
            gifEncoder.finish();

            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
            fos = new FileOutputStream(file);
            baos.writeTo(fos);
        }catch (Exception e){
            handler.obtainMessage(FAILED,e).sendToTarget();
            e.printStackTrace();
        }finally {
            try {
                baos.flush();
                fos.flush();
                baos.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        handler.obtainMessage(SUCCESS, filePath).sendToTarget();
    }



    /**
     * 检查集合里bmp大小是否一样
     *
     * @param bmps
     * @return
     */
    private boolean checkFrameSize(List<Bitmap> bmps) {
        int lastWidth = 0;
        int lastHeight = 0;
        for (int i = 0; i < bmps.size(); i++) {
            Bitmap bmp = bmps.get(i);
            if (i == 0) {
                lastWidth = bmp.getWidth();
                lastHeight = bmp.getHeight();
            }
            if (bmp.getWidth() != lastWidth || bmp.getHeight() != lastHeight) {
                return false;
            }
        }
        return true;
    }

}
