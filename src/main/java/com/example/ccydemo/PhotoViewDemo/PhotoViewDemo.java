package com.example.ccydemo.PhotoViewDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.ccydemo.R;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by XMuser on 2017-01-20.
 */

public class PhotoViewDemo extends AppCompatActivity {
    private PhotoView photoView;
    private ImageView imageView;
    private PhotoViewAttacher photoViewAttacher;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_view);
        photoView = (PhotoView) findViewById(R.id.p_1);
        imageView = (ImageView) findViewById(R.id.p_2);
        photoView.setImageResource(R.drawable.q);
        imageView.setImageResource(R.drawable.z);
        photoViewAttacher = new PhotoViewAttacher(imageView);
    }
}
