package com.example.ccydemo.ZxingDemo;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ccydemo.R;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Permission;


/**
 * Created by XMuser on 2017-02-06.
 * 利用对zxing封装好的开源库
 */

public class ZxingByOpen extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 1;
    private static final int REQUEST_ALBUM= 2;

    private String[] permissionCamera = {Manifest.permission.CAMERA};
    private String[] permissionExternal = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private Button openBtn;
    private Button albumBtn;
    private Button perCameraBtn;
    private Button perExternalBtn;
    private TextView reslutTv;
    private ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zxingopen_act);
        ZXingLibrary.initDisplayOpinion(this);

        openBtn = (Button) findViewById(R.id.zxingopen_open);
        albumBtn = (Button) findViewById(R.id.zxingopen_album);
        reslutTv = (TextView) findViewById(R.id.zxingopen_result);
        img = (ImageView) findViewById(R.id.zxingopen_img);
        perCameraBtn = (Button) findViewById(R.id.zxingopen_per_camera);
        perExternalBtn = (Button) findViewById(R.id.zxingopen_per_external);
        openBtn.setOnClickListener(this);
        albumBtn.setOnClickListener(this);
        perCameraBtn.setOnClickListener(this);
        perExternalBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.zxingopen_open:
                openZxing();
                break;
            case R.id.zxingopen_album:
                openAlbum();
                break;
            case R.id.zxingopen_per_camera:
                askPermission(permissionCamera);
                break;
            case R.id.zxingopen_per_external:
                askPermission(permissionExternal);
                break;
        }
    }

    private void openAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_ALBUM);
    }

    private void askPermission(String[] permissions) {
        if (ContextCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, 1);
        }else {
            Toast.makeText(this,"permission already successed",Toast.LENGTH_LONG).show();
        }
    }

    private void openZxing() {
//        Intent intent = new Intent(this, CaptureActivity.class);
        Intent intent = new Intent(this, MyCaptureActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE ){
            if(data != null) {
                Bundle b = data.getExtras();
                if (b != null) {
                    if(b.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS){
                        reslutTv.setText(""+b.getString(CodeUtils.RESULT_STRING));
                    }else {
                        reslutTv.setText("Failed !");
                    }
                    return;
                }
            }else {
                reslutTv.setText("empty !");
            }
        }
        if(requestCode == REQUEST_ALBUM){
            if(data != null){
                Uri uri = data.getData();
                ContentResolver cr =getContentResolver();
                try {
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(cr,uri);
                    CodeUtils.analyzeBitmap(uri.getPath(), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            reslutTv.setText(""+result);
                            img.setImageBitmap(mBitmap);
                            Toast.makeText(ZxingByOpen.this,"sucess",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            Toast.makeText(ZxingByOpen.this,"failed",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); //需不需要？
        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"permission successed",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,"permission denied",Toast.LENGTH_LONG).show();
            }
        }

    }
}
