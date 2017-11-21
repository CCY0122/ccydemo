package com.example.ccydemo.gifDemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.gifencoder.AnimatedGifEncoder;
import com.example.ccydemo.BaseActivity;
import com.example.ccydemo.R;
import com.example.ccydemo.Util.Util;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ccy on 2017-11-06.
 */

public class GifAct extends BaseActivity {

    @BindView(R.id.permission)
    Button permission;
    @BindView(R.id.album)
    Button openAlbum;
    @BindView(R.id.clear_album)
    Button clearImg;
    @BindView(R.id.gif_path)
    TextView gifPath;
    @BindView(R.id.to_gif)
    Button toGif;
    @BindView(R.id.gif_result)
    TextView gifResult;
    LocalAlbumHelper albumHelper;
    List<String> imgs = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gif_act);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.permission)
    public void permission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            Toast.makeText(this, "权限已申请", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.album)
    public void openAlbum() {
        LocalAlbumHelper.openLocalAlbum(this, 1);
    }

    @OnClick(R.id.to_gif)
    public void toGif() {
        if (imgs.size() > 0) {
//            try {
//                makeGif(imgs);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            GifMaker gifMaker = new GifMaker(new GifMaker.OnGifMakerListener() {
                @Override
                public void onSuccess(String path) {
                    gifResult.append("转换结束：" + path+"\n");
                }

                @Override
                public void onFailed(Exception e) {
                    gifResult.append("转换失败： "+ e.getMessage()+"\n");
                }

                @Override
                public void onProgressUpdate(int currentFrame, int frameCount) {
                    gifResult.append("转换进度： "+ currentFrame + "/" + frameCount+"\n");
                }
            });
            gifResult.setText("");
            gifMaker.makeGif(imgs,new File(Environment.getExternalStorageDirectory().getPath() + "/GifDemo/ccy.gif").getPath(),270,480);

        }
    }

    @OnClick(R.id.clear_album)
    public void clearImage() {
        imgs.clear();
        gifPath.setText("");
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            imgs.add(LocalAlbumHelper.handleImage(this, data));
            gifPath.setText("");
            for (String path : imgs) {
                gifPath.append(path + "\n");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "权限申请成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "权限申请失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
